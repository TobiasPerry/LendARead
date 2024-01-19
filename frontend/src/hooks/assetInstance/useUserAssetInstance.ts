import {useState} from "react";
import {AssetApi, AssetInstanceApi, LendingApi} from "./useUserAssetInstances.ts";
import {api, api_} from "../api/api.ts";

const useUserAssetInstance = (location, id) => {

    const queryParams = new URLSearchParams(location.search);
    const isLending = queryParams.get('isLending') === "true";
    const [assetDetails, setAssetDetails] = useState({})
    const [hasActiveLendings, setHasActiveLendings] = useState(false)

    const fetchUserAssetDetails = async () => {
        const assetinstace: AssetInstanceApi = (await api.get(`/assetInstances/${id}`)).data
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

        let lending: Array<LendingApi>= []
        try {
             const lending_  = await api.get(`/lendings/${id}`)
            lending = lending_.data
            if(lending.filter((lending: LendingApi) => lending.state === "ACTIVE").length > 0)
                setHasActiveLendings(true)
        } catch (e) {

        }

        if(isLending)
            await setAssetDetails({...assetDetails_, lending: lending})
        else
            await setAssetDetails(assetDetails_)
    }

    const deleteAssetInstance = async (asset: any) => {
        await api.delete(asset.selfUrl)
    }


    return {
        assetDetails, fetchUserAssetDetails, isLending, hasActiveLendings, deleteAssetInstance
    }
}

export default useUserAssetInstance;