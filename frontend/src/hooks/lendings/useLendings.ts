import {api, api_} from "../api/api.ts";
import {useEffect, useState} from "react";
import {UserDetailsApi} from "../../contexts/authContext.tsx";
import {extractId, LendingApi} from "../assetInstance/useUserAssetInstances.ts";
// @ts-ignore
import photoPlaceholder from "../../../public/static/profile_placeholder.jpeg";

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
    const [totalPages, setTotalPages] = useState(0)
    const [links, setLinks] = useState({ first: null, next: null, last: null, prev: null });
    const [currentPage, setCurrentPage] = useState(0)
    const PAGE_SIZE = 1

    const getLendings = async (asset, url = `/lendings?itemsPerPage=${PAGE_SIZE}`) => {
        if(asset === undefined || asset === null || asset.assetinstance === undefined) return


        const lendingsResponse = await api.get(url,{ params: {
            assetInstanceId: asset.assetinstanceid,
        }})

        //@ts-ignore
        const linkHeader: any = lendingsResponse.headers.get("Link");
        const parsedLinks = parseLinkHeader(linkHeader);
        setLinks(parsedLinks);
        const lendings = lendingsResponse.data

        const mappedLendings = lendings.map(async (lending: LendingApi) => {
            const user: UserDetailsApi = (await api_.get(lending.borrowerUrl)).data
            let image = photoPlaceholder
            if(user.image !== undefined) {
                 image = (await api_.get(user.image)).data
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
        setLendings(lendings_)
    }

    const changePage = async (page) => {
        let url;
        if (page === 0) {
            url = links.first;
        } else if (page > currentPage) {
            url = links.next;
        } else if (page < currentPage) {
            url = links.prev;
        }

        if (url) {
            await getLendings(url);
            setCurrentPage(page);
        }
    };

    return {
        lendings, totalPages, currentPage, changePage, getLendings
    }
}

export default useLendings;