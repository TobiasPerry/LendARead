import {useState} from "react";
import {AssetApi, AssetInstanceApi, LendingApi} from "./useUserAssetInstances.ts";
import {api, api_} from "../api/api.ts";

const useUserAssetInstance = (location, id) => {

    const queryParams = new URLSearchParams(location.search);
    const state = queryParams.get('state');
    const [assetDetails, setAssetDetails] = useState({})
    const [hasActiveLendings, setHasActiveLendings] = useState(false)

    const fetchUserAssetDetails = async () => {
        let assetinstace: AssetInstanceApi = (await api.get(`/assetInstances/${id}`)).data
        let lending = {}

        if(state === "lended" || state === "borrowed") {
            const lending_ = await api.get(`/lendings/${id}`)
            lending = lending_.data

            //@ts-ignore
            assetinstace = (await api_.get(lending.assetInstance)).data
            //@ts-ignore
            if(lending.state == "ACTIVE")
                setHasActiveLendings(true)
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
    }

    const deleteAssetInstance = async (asset: any) => {
        await api.delete(asset.selfUrl)
    }


    return {
        assetDetails, fetchUserAssetDetails, state, hasActiveLendings, deleteAssetInstance
    }
}

export default useUserAssetInstance;