import {useState} from "react";
import {AssetApi, AssetInstanceApi, LendingApi} from "./useUserAssetInstances.ts";
import {api, api_} from "../api/api.ts";

const useUserAssetInstance = (location, id) => {

    const queryParams = new URLSearchParams(location.search);
    const state = queryParams.get('state');
    const [assetDetails, setAssetDetails] = useState({})
    const [hasActiveLendings, setHasActiveLendings] = useState(false)
    const [isLoading, setIsLoading] = useState(false)

    const fetchUserAssetDetails = async () => {
        setIsLoading(true)
        let assetinstace : any = {}
        let lending: any = {}

        if(state === "lended" || state === "borrowed") {
            const lending_ = await api.get(`/lendings/${id}`)
            lending = lending_.data

            assetinstace = (await api_.get(lending.assetInstance)).data
            if(lending.state == "ACTIVE")
                setHasActiveLendings(true)
        } else {
            assetinstace = (await api.get(`/assetInstances/${id}`)).data
        }

        const asset: AssetApi = (await api_.get(assetinstace.assetReference)).data
        const lang  = (await api_.get(asset.language)).data


        const assetDetails_ = {
            title: asset.title,
            author: asset.author,
            condition: assetinstace.physicalCondition,
            description: assetinstace.description,
            language: lang.name,
            isbn: asset.isbn,
            imageUrl: assetinstace.imageReference,
            isReservable: assetinstace.reservable,
            status: assetinstace.status,
            id: id,
            maxDays: assetinstace.maxLendingDays,
            assetinstance: assetinstace
        }


        if(state === "lended" || state === "borrowed")
            await setAssetDetails({...assetDetails_, lending: lending})
        else
            await setAssetDetails(assetDetails_)

        setIsLoading(false)
    }

    const deleteAssetInstance = async (asset: any) => {
        await api.delete(asset.selfUrl)
    }


    return {
        assetDetails, fetchUserAssetDetails, state, hasActiveLendings, deleteAssetInstance, isLoading
    }
}

export default useUserAssetInstance;