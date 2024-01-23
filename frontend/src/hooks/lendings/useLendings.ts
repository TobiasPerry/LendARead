import {api} from "../api/api.ts";
import {useEffect, useState} from "react";

const useLendings = ({asset}) => {

    const [lendings, setLendings] = useState([])

    useEffect(() => {
        getLendings().then()
    }, [asset])

    const getLendings = async () => {
        const lendings = (await api.get(`/lendings`,{ params: {assetInstanceId: asset.assetinstanceid}} )).data
        setLendings(lendings)
    }

    return {
        lendings
    }
}

export default useLendings;