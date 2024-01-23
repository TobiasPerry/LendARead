import {api} from "../api/api.ts";
import {useEffect, useState} from "react";

const useLendings = () => {

    const [lendings, setLendings] = useState([])
    const [totalPages, setTotalPages] = useState(0)
    const [currentPage, setCurrentPage] = useState(0)



    const getLendings = async (asset) => {
        if(asset === undefined) return

        const params = {
            assetInstanceId: asset.assetinstanceid,

        }
        const lendings = (await api.get(`/lendings`,{ params: params } )).data
        console.log('lendings', lendings)
        setLendings(lendings)
    }

    const changePage = (page) => {

    }

    return {
        lendings, totalPages, currentPage, changePage, getLendings
    }
}

export default useLendings;