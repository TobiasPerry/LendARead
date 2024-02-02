import {api_} from "../api/api.ts";
import {useEffect, useState} from "react";
import types from "../api/types.ts";

const useUserBorrowedBooksOptions = (asset, fetchUserAssetInstance) => {

    const [canReview, setCanReview] = useState(true)
    useEffect(() => {
        if(asset !== undefined && asset.lending !== undefined)
            canReview_().then()
    }, [asset])

    const cancelBorrowedBook = async() => {
        await api_.patch(asset.lending.selfUrl, {state: "CANCEL"},
            {
                headers: {
                    "Content-type": types.VND_ASSET_INSTANCE_LENDING_STATE
                }
            })
       await fetchUserAssetInstance()
    }

    const canReview_ = async () => {
        const ans = !asset.lending.hasOwnProperty('lenderReviewUrl')
        setCanReview(ans)
    }

    return {
        cancelBorrowedBook, canReview
    }
}

export default useUserBorrowedBooksOptions;