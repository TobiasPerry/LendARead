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

    return {
        cancelBorrowedBook
    }
}

export default useUserBorrowedBooksOptions;