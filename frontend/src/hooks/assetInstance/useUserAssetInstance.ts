import {useContext, useState} from "react";
import {AssetApi, AssetInstanceApi, extractId, LendingApi} from "./useUserAssetInstances.ts";
import {api, api_} from "../api/api.ts";
import {AuthContext, UserDetailsApi} from "../../contexts/authContext.tsx";
import {useTranslation} from "react-i18next";

const useUserAssetInstance = (location, id) => {

    const queryParams = new URLSearchParams(location.search);
    const state = queryParams.get('state');
    const [assetDetails, setAssetDetails] = useState({title: ""})
    const [hasActiveLendings, setHasActiveLendings] = useState(false)
    const [isLoading, setIsLoading] = useState(false)
    const [isOwner, setIsOwner] = useState(false)
    const [error, setError] = useState({state: false, text: ""})
    const [hasLendingsNotFinished, setHasLendingsNotFinished] = useState(false)
    const {user} = useContext(AuthContext)
    const {t} = useTranslation()

    const checkIsOwner = async (user: string, lending: LendingApi, assetinstance: AssetInstanceApi) => {
        if(state === "lended") {
            const user_: UserDetailsApi = (await api.get(lending.lenderUrl)).data
            const isOwner = extractId(user_.selfUrl) === user
            setIsOwner(isOwner)
        } else if (state === "borrowed") {
            const user_: UserDetailsApi = (await api.get(lending.borrowerUrl)).data
            const isOwner = extractId(user_.selfUrl) === user
            setIsOwner(isOwner)
        } else if (state === "owned") {
            const user_: UserDetailsApi = (await api.get(assetinstance.userReference)).data
            const isOwner = extractId(user_.selfUrl) === user
            setIsOwner(isOwner)
        } else {
            setIsOwner(false)
        }
    }

    const getLendingsForAssetInstance = async (assetInstanceId) => {
        try{
            // Ask if there's any lending that is active or delivered
            const res = await api.get(`/lendings?assetInstanceId=${assetInstanceId}&state=ACTIVE&state=DELIVERED`)
            // If the response is not empty, is because there's a lending activer or delivered
            if (res.status === 204) return false; // No content
            return(res.data !== null && res.data !== undefined && res.data.length > 0)
        }catch (e){
            return false;
        }
    }

    const fetchUserAssetDetails = async () => {
        await setIsLoading(true)

        try {
            let assetinstace: any = {}
            let lending: any = null

            if (state === "lended" || state === "borrowed") {
                const lending_ = (await api.get(`/lendings/${id}`)).data
                assetinstace = (await api_.get(lending_.assetInstance)).data
                if (lending_.state == "ACTIVE")
                    setHasActiveLendings(true)
                lending = lending_
            } else {
                assetinstace = (await api.get(`/assetInstances/${id}`)).data
                setHasLendingsNotFinished(await getLendingsForAssetInstance(id))
            }

            await checkIsOwner(user, lending, assetinstace)
            const asset: AssetApi = (await api_.get(assetinstace.assetReference)).data
            const lang = (await api_.get(asset.language)).data


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


            if (state === "lended" || state === "borrowed") { // @ts-ignore
                await setAssetDetails({...assetDetails_, lending: lending, lendingid: extractId(lending.selfUrl)})
            } else
                await setAssetDetails(assetDetails_)

            setIsLoading(false)
        } catch (e) {
            setError({state: true, text: t("errors.failedFetchingAssetDetails")})
            setIsLoading(false)
        }
    }

    const deleteAssetInstance = async (asset: any) => {
        try {
            await api.delete(asset.selfUrl)
        } catch (e) {
            setError({state: true, text: t("errors.failedDeletingAssetDetails")})
        }
    }


    return {
        assetDetails, fetchUserAssetDetails, state, hasActiveLendings, deleteAssetInstance, isLoading, isOwner, error, hasLendingsNotFinished
    }
}

export default useUserAssetInstance;