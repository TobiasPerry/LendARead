import {useState} from "react";
import {AssetApi, AssetInstanceApi, LendingApi} from "./useUserAssetInstances.ts";
import {api, api_} from "../api/api.ts";

const useUserAssetInstance = (location, id) => {

    const queryParams = new URLSearchParams(location.search);
    const isLending = queryParams.get('isLending');
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
            language: lang.name,
            isbn: asset.isbn,
            imageUrl: assetinstace.imageReference,
            isReservable: assetinstace.reservable,
            status: assetinstace.status,
            id: id,
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
            await setAssetDetails({...assetDetails_, ...lending})

        console.log(assetDetails_)
        await setAssetDetails(assetDetails_)
    }

    const editAssetVisbility = async (asset: any) => {
        console.log('current state', asset.status)
        const data = {...asset.assetinstance}
        data.status = data.status === "PUBLIC" ? "PRIVATE" : "PUBLIC"

        await api.patch(`/assetInstances/${asset.id}`, data,
            {
                headers: {"Content-type": "multipart/form-data"}
            })
        await fetchUserAssetDetails()
    }

    const editAssetReservability = async (asset: any) => {
        console.log('current reservable', asset.assetinstance.reservable)
        const data = {...asset.assetinstance}
        data.reservable = !data.reservable

        await api.patch(`/assetInstances/${asset.id}`, data,
            {headers: {"Content-type": "multipart/form-data"}})
        await fetchUserAssetDetails()
    }

    const deleteAssetInstance = async (asset: any) => {
        await api.delete(`/assetInstances/${asset.id}`)
    }

    return {
        assetDetails, fetchUserAssetDetails, isLending, hasActiveLendings, editAssetVisbility, editAssetReservability, deleteAssetInstance
    }
}

export default useUserAssetInstance;