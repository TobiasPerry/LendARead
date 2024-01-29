import {api_} from "../api/api.ts";

const useUserBorrowedBooksOptions = (asset, fetchUserAssetInstance) => {

    const cancelBorrowedBook = async() => {
        await api_.patch(asset.lending.selfUrl, {state: "CANCEL"},
            {
                headers: {
                    "Content-type": "application/vnd.assetInstanceLendingState.v1+json"
                }
            })
       await fetchUserAssetInstance()
    }

    const canReview = async () => {
        console.log('lending id', asset.lending)
        return asset.lending.lenderReviewer !== undefined
    }

    return {
        cancelBorrowedBook, canReview
    }
}

export default useUserBorrowedBooksOptions;