import {api, api_} from "../api/api.ts";
import {useEffect, useState} from "react";
import {UserDetailsApi} from "../../contexts/authContext.tsx";
import {extractId, LendingApi} from "../assetInstance/useUserAssetInstances.ts";
// @ts-ignore
import photoPlaceholder from "../../../public/static/profile_placeholder.jpeg";
import {extractTotalPages} from "../assetInstance/useAssetInstance.ts";

function parseLinkHeader(header) {
    if (!header || header.length === 0) {
        return {};
    }

    // Split parts by comma and parse each part into a named link
    return header.split(',').reduce((links, part) => {
        const section = part.split(';');
        if (section.length !== 2) {
            throw new Error("section could not be split on ';'");
        }

        const url = section[0].replace(/<(.*)>/, '$1').trim();
        const name = section[1].replace(/rel="(.*)"/, '$1').trim();

        links[name] = url;

        return links;
    }, {});
}

const useLendings = () => {

    const [lendings, setLendings] = useState([])
    const [totalPages, setTotalPages] = useState(1)
    const [currentPage, setCurrentPage] = useState(1)
    const PAGE_SIZE = 1
    const [asset_, setAsset_] = useState(null)
    const getLendings = async (asset, newPage = 1) => {
        if(asset === undefined || asset === null || asset.assetinstance === undefined) return


        const lendingsResponse = await api.get("/lendings",{ params: {
            assetInstanceId: asset.assetinstanceid,
            page: newPage,
            itemsPerPage: PAGE_SIZE,
        }})

        //@ts-ignore
        const linkHeader: any = lendingsResponse.headers.get("Link");
        const totalPages = extractTotalPages(linkHeader);
        setTotalPages(totalPages);

        const lendings = lendingsResponse.data

        const mappedLendings = lendings.map(async (lending: LendingApi) => {
            const user: UserDetailsApi = (await api_.get(lending.borrowerUrl)).data
            let image = photoPlaceholder
            if(user.image !== undefined) {
                 image = (await api_.get(user.image, {params: {size: "CUADRADA"}})).data
            }

            return {
                startDate: lending.lendDate,
                endDate: lending.devolutionDate,
                userName: user.userName,
                userImage: image,
                id: extractId(lending.selfUrl),
                state: lending.state
            }
        })

        const lendings_ = await Promise.all(mappedLendings)
        await setLendings(lendings_)
    }

    const changePage = async (page) => {
        await getLendings(asset_, page)
        setCurrentPage(page);
    };

    return {
        lendings, totalPages, currentPage, changePage, getLendings, setAsset_
    }
}

export default useLendings;