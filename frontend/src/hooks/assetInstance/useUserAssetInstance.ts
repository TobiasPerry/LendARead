import {useContext, useState} from "react";
import {AssetApi, AssetInstanceApi, extractId, LendingApi} from "./useUserAssetInstances.ts";
import {api, api_} from "../api/api.ts";
import {AuthContext, UserDetailsApi} from "../../contexts/authContext.tsx";

const useUserAssetInstance = (location, id) => {

    const queryParams = new URLSearchParams(location.search);
    const state = queryParams.get('state');
    const [assetDetails, setAssetDetails] = useState({
        title: ""
    })
    const [hasActiveLendings, setHasActiveLendings] = useState(false)
    const [isLoading, setIsLoading] = useState(false)
    const [isOwner, setIsOwner] = useState(false)
    const {user} = useContext(AuthContext)

    const checkIsOwner = async (user: string, lending: LendingApi, assetinstance: AssetInstanceApi) => {
        if(state === "lended") {
            console.log("lending", lending)
            const user_: UserDetailsApi = (await api.get(lending.lenderUrl)).data
            console.log("user_", user_)
            const isOwner = extractId(user_.selfUrl) === user
            setIsOwner(isOwner)
        } else if (state === "borrowed") {
            const user_: UserDetailsApi = (await api.get(lending.borrowerUrl)).data
            const isOwner = extractId(user_.selfUrl) === user
            setIsOwner(isOwner)
        } else if (state === "owned") {
            console.log("assetinstance", assetinstance)
            const user_: UserDetailsApi = (await api.get(assetinstance.userReference)).data
            console.log("userId", extractId(user_.selfUrl))
            console.log("user logged", user)
            const isOwner = extractId(user_.selfUrl) === user
            setIsOwner(isOwner)
        } else {
            setIsOwner(false)
        }
    }

    const fetchUserAssetDetails = async () => {
        await setIsLoading(true)
        let assetinstace : any = {}
        let lending: any = null

        if(state === "lended" || state === "borrowed") {
            const lending_ = (await api.get(`/lendings/${id}`)).data
            assetinstace = (await api_.get(lending_.assetInstance)).data
            if(lending_.state == "ACTIVE")
                setHasActiveLendings(true)
            lending = lending_
        } else {
            assetinstace = (await api.get(`/assetInstances/${id}`)).data
        }

        await checkIsOwner(user, lending, assetinstace)
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
            id: id, //wtf this does
            assetinstanceid: extractId(assetinstace.selfUrl),
            maxDays: assetinstace.maxLendingDays,
            assetinstance: assetinstace,
            selfUrl: assetinstace.selfUrl
        }


        if(state === "lended" || state === "borrowed") { // @ts-ignore
                await setAssetDetails({...assetDetails_, lending: lending, lendingid: extractId(lending.selfUrl)})
        } else
            await setAssetDetails(assetDetails_)

        await setIsLoading(false)
    }

    const deleteAssetInstance = async (asset: any) => {
        await api.delete(asset.selfUrl)
    }


    return {
        assetDetails, fetchUserAssetDetails, state, hasActiveLendings, deleteAssetInstance, isLoading, isOwner
    }
}

export default useUserAssetInstance;