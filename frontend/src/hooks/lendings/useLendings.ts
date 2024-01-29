import {api, api_} from "../api/api.ts";
import {useEffect, useState} from "react";
import {UserDetailsApi} from "../../contexts/authContext.tsx";
import {extractId, LendingApi} from "../assetInstance/useUserAssetInstances.ts";
// @ts-ignore
import photoPlaceholder from "../../../public/static/profile_placeholder.jpeg";

const useLendings = () => {

    const [lendings, setLendings] = useState([])
    const [totalPages, setTotalPages] = useState(0)
    const [currentPage, setCurrentPage] = useState(0)
    const PAGE_SIZE = 10

    const getLendings = async (asset) => {
        if(asset === undefined) return

        const params = {
            assetInstanceId: asset.assetinstanceid,
            'itemsPerPage': PAGE_SIZE,
        }
        
        const lendings = (await api.get(`/lendings`,{ params: params } )).data

        const mappedLendings = lendings.map(async (lending: LendingApi) => {
            const user: UserDetailsApi = (await api_.get(lending.borrowerUrl)).data
            let image = photoPlaceholder
            if(user.image) {
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

    const changePage = (page) => {

    }

    return {
        lendings, totalPages, currentPage, changePage, getLendings
    }
}

export default useLendings;