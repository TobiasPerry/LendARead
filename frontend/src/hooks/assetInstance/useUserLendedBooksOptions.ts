import {api_} from "../api/api.ts";
import {isActive} from "../../components/user/LendedBooksOptions.tsx";

const useUserLendedBooksOptions = (fetchUserAssetInstance) => {

    const rejectLending = async (asset) => {
        const url = asset.lending.selfUrl
        console.log("rejected lending", url)
        await api_.patch(url, {state: "DELIVERED"},
            {
                headers: {
                    "Content-type": "application/vnd.assetInstanceLendingState.v1+json"
                }
            })
        await fetchUserAssetInstance()
    }

    const confirmLending = async (asset) => {

    }

    const returnLending = async (asset) => {

    }

    return {
        rejectLending, confirmLending, returnLending
    }
}

export default useUserLendedBooksOptions;