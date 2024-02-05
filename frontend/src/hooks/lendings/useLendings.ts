import {api, api_} from "../api/api.ts";
import {useEffect, useState} from "react";
import {UserDetailsApi} from "../../contexts/authContext.tsx";
import {extractId, LendingApi} from "../assetInstance/useUserAssetInstances.ts";
// @ts-ignore
import photoPlaceholder from "../../../public/static/profile_placeholder.jpeg";
import {extractTotalPages} from "../assetInstance/useAssetInstance.ts";
import {useTranslation} from "react-i18next";

const useLendings = () => {

    const [lendings, setLendings] = useState([])
    const [totalPages, setTotalPages] = useState(1)
    const [currentPage, setCurrentPage] = useState(1)
    const PAGE_SIZE = 3
    const [asset_, setAsset_] = useState(null)
    const {t} = useTranslation()
    const [error, setError] = useState({state: false, text: ""})

    const getLendings = async (asset, newPage = 1) => {
        if(asset === undefined || asset === null || asset.assetinstance === undefined) return

        try {
            const lendingsResponse = await api.get("/lendings", {
                params: {
                    assetInstanceId: asset.assetinstanceid,
                    page: newPage,
                    itemsPerPage: PAGE_SIZE,
                }
            })

        //@ts-ignore
        const linkHeader: any = lendingsResponse.headers.get("Link");
        const totalPages = extractTotalPages(linkHeader);
        setTotalPages(totalPages);
        if (lendingsResponse.status === 204) {
            setLendings([])
            return
        }
        const lendings = lendingsResponse.data

            const mappedLendings = lendings.map(async (lending: LendingApi) => {
                const user: UserDetailsApi = (await api_.get(lending.borrowerUrl)).data
                let image = user.image === undefined ? photoPlaceholder : user.image + "?size=CUADRADA"

                return {
                    startDate: lending.lendDate,
                    endDate: lending.devolutionDate,
                    userName: user.userName,
                    userImage: image,
                    id: lending.id,
                    state: lending.state
                }
            })

            const lendings_ = await Promise.all(mappedLendings)
            await setLendings(lendings_)
        } catch (e) {
            setError({state: true, text: t("errors.failedToFetchLendings")})
            setLendings([])
        }
    }

    const changePage = async (page) => {
        await getLendings(asset_, page)
        setCurrentPage(page);
    };

    return {
        lendings, totalPages, currentPage, changePage, getLendings, setAsset_, error
    }
}

export default useLendings;