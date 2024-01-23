import {api, api_} from "../api/api.ts";
import {isActive, isDelivered} from "../../components/user/LendedBooksOptions.tsx";
import {LendingApi} from "./useUserAssetInstances.ts";
import {useEffect, useState} from "react";

const useUserLendedBooksOptions = (fetchUserAssetInstance, asset) => {

    const [canConfirmLending, setCanConfirmLending] = useState(true)

    useEffect(() => {
        checkCanConfirmLending().then()
    }, [])

    const checkCanConfirmLending = async () => {
        const lendings: Array<LendingApi> = (await api.get(`/lendings`,{ params: {assetInstanceId: asset.assetinstanceid}} )).data
        const deliveredLendings = lendings.filter((lending: LendingApi) => isDelivered(lending.state))
        setCanConfirmLending(deliveredLendings.length === 0)
    }
    const updateState = async (url, state) => {
        await api_.patch(url, {state: state},
            {
                headers: {
                    "Content-type": "application/vnd.assetInstanceLendingState.v1+json"
                }
            })
        await fetchUserAssetInstance()
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
        rejectLending, confirmLending, returnLending, canConfirmLending
    }
}

export default useUserLendedBooksOptions;