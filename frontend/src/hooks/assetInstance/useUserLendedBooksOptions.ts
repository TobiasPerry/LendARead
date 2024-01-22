import {api_} from "../api/api.ts";
import {isActive} from "../../components/user/LendedBooksOptions.tsx";

const useUserLendedBooksOptions = (fetchUserAssetInstance) => {

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
        //chequeo no tiene otro DELIVERED
        // const lendings = (await api_.get(asset.assetinstance.selfUrl))
        await updateState(asset.lending.selfUrl, "DELIVERED")
    }

    const returnLending = async (asset) => {
        await updateState(asset.lending.selfUrl, "FINISHED")
    }

    return {
        rejectLending, confirmLending, returnLending
    }
}

export default useUserLendedBooksOptions;