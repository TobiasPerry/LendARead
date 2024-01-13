import {useState} from "react";
import {useParams} from "react-router-dom";
import {AssetApi, AssetInstanceApi, LendingApi} from "./useUserAssetInstances.ts";
import {api, api_} from "../api/api.ts";

const useUserAssetInstance = (location, id) => {

    const queryParams = new URLSearchParams(location.search);
    const isLending = queryParams.get('isLending');
    const [assetDetails, setAssetDetails] = useState({})

    const fetchUserAssetDetails2 = async () => {
        const assetinstace: AssetInstanceApi = (await api.get(`/assetInstances/${id}`)).data
        const asset: AssetApi = (await api_.get(assetinstace.assetReference)).data
        const lang  = (await api_.get(asset.language)).data

        return {
            title: asset.title,
            author: asset.author,
            condition: assetinstace.physicalCondition,
            language: lang.name,
            isbn: asset.isbn,
            imageUrl: assetinstace.imageReference,
            isReservable: assetinstace.reservable
        }
    }

    const fetchUserLendingDetails = async () => {
        const lending: LendingApi = (await api.get(`lendings/${id}`)).data
        return {
           ...lending
        }
    }

    const fetchUserAssetDetails = async () => {
        if(isLending)
            return {...await fetchUserLendingDetails(), ...await fetchUserLendingDetails()}
        else
            return await fetchUserAssetDetails2()
    }

    //need to add it to the api or do it here
    const hasActiveLendings = async () => {
        return true
    }


    return {
        assetDetails, fetchUserAssetDetails, isLending
    }
}

export default useUserAssetInstance;