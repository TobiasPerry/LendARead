import {useState} from "react";
import {useParams} from "react-router-dom";
import {AssetApi, AssetInstanceApi} from "./useUserAssetInstances.ts";
import {api, api_} from "../api/api.ts";

const useUserAssetInstance = (location, id) => {

    const queryParams = new URLSearchParams(location.search);
    const isLending = queryParams.get('isLending');
    const [assetDetails, setAssetDetails] = useState({})

    const fetchUserAssetDetails = async () => {
        const assetinstace: AssetInstanceApi = (await api.get(`/assetInstances/${id}`)).data
        const asset: AssetApi = (await api_.get(assetinstace.assetReference)).data
        const lang  = (await api_.get(asset.language)).data

        setAssetDetails( {
            title: asset.title,
            author: asset.author,
            condition: assetinstace.physicalCondition,
            language: lang.name,
            isbn: asset.isbn,
            imageUrl: assetinstace.imageReference
        })
    }

    return {
        assetDetails, fetchUserAssetDetails
    }
}

export default useUserAssetInstance;