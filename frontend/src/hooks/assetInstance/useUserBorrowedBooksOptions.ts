import {api_} from "../api/api.ts";
import {useEffect, useState} from "react";
import types from "../api/types.ts";
import {useTranslation} from "react-i18next";

const useUserBorrowedBooksOptions = (asset, fetchUserAssetInstance) => {

    const [canReview, setCanReview] = useState(true)
    const {t} = useTranslation()
    const [error, setError] = useState({state: false, text: ""})

    useEffect(() => {
        if(asset !== undefined && asset.lending !== undefined)
            canReview_().then()
    }, [asset])

    const cancelBorrowedBook = async() => {
        try {
            await api_.patch(asset.lending.selfUrl, {state: "CANCEL"},
                {
                    headers: {
                        "Content-type": types.VND_ASSET_INSTANCE_LENDING_STATE
                    }
                })
            await fetchUserAssetInstance()
        } catch (e) {
            setError({state: true, text: t("errors.cancelBorrowedBook")})
        }
    }

    const canReview_ = async () => {
        try {
            const ans = !asset.lending.hasOwnProperty('lenderReviewUrl')
            setCanReview(ans)
        } catch (e) {
            setError({state: true, text: t("errors.failedCheckCanReview")})
            setCanReview(false)
        }
    }

    return {
        cancelBorrowedBook, canReview, error
    }
}

export default useUserBorrowedBooksOptions;