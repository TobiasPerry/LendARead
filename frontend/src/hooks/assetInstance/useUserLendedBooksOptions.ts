import {api, api_} from "../api/api";
import {isActive, isDelivered} from "../../components/userAssets/LendedBooksOptions";
import {LendingApi} from "./useUserAssetInstances";
import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../../contexts/authContext";
import types from "../api/types.ts";
import {useTranslation} from "react-i18next";

const useUserLendedBooksOptions = (fetchUserAssetInstance, asset) => {

    const [canConfirmLending, setCanConfirmLending] = useState(true)
    const [canReview, setCanReview] = useState(true)
    const {t} = useTranslation()
    const [error, setError] = useState({state: false, text: ""})

    useEffect(() => {
        if(asset !== undefined && asset.lending !== undefined) {
            checkCanConfirmLending().then()
            checkCanReview().then()
        }
    }, [asset])

    const checkCanConfirmLending = async () => {
        try {
            const lendings: Array<LendingApi> = (await api.get(`/lendings`, {params: {assetInstanceId: asset.assetinstanceid}})).data
            const deliveredLendings = lendings.filter((lending: LendingApi) => isDelivered(lending.state))
            const canConfirm = deliveredLendings.length === 0
            setCanConfirmLending(canConfirm)
        } catch (e) {
            setError({state: true, text: t("errors.failedToCheckCanConfirmLending")})
            setCanConfirmLending(false)
        }
    }

    const checkCanReview = async () => {
        try {
            const ans = !asset.lending.hasOwnProperty('borrowerReviewUrl')
            setCanReview(ans)
        } catch (e) {
           setError({state: true, text: t("errors.failedCheckCanReview")})
           setCanReview(false)
        }
    }
    const updateState = async (url, state) => {
        try {
            await api_.patch(url, {state: state},
                {
                    headers: {
                        "Content-type": types.VND_ASSET_INSTANCE_LENDING_STATE
                    }
                })
            await fetchUserAssetInstance()
        } catch (e) {
            setError({state: true, text: t("errors.failedToUpdateState")})
        }
    }

    const rejectLending = async (asset) => {
        await updateState(asset.lending.selfUrl, "REJECTED")
    }

    const confirmLending = async (asset) => {
        await updateState(asset.lending.selfUrl, "DELIVERED")
    }

    const returnLending = async (asset) => {
        await updateState(asset.lending.selfUrl, "FINISHED")
    }

    return {
        rejectLending, confirmLending, returnLending, canConfirmLending, canReview, error
    }
}

export default useUserLendedBooksOptions;