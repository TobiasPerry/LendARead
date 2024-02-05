import {api} from "../api/api.ts";
import {extractId} from "./useUserAssetInstances.ts";
import types from "../api/types.ts";
import {useState} from "react";
import {useTranslation} from "react-i18next";

const userUserAssetInstanceOptions = (fetchUserAssetDetails) => {

    const {t} = useTranslation()
    const [error, setError] = useState({status: false, text: ""})

    const editAssetVisbility = async (asset: any) => {
        try {
            const response = await api.patch(asset.assetinstance.selfUrl, {status: asset.assetinstance.status === "PUBLIC" ? "PRIVATE" : "PUBLIC"},
                {
                    headers: {"Content-type": types.VND_ASSET_INSTANCE}
                })
            await fetchUserAssetDetails()
        } catch (e) {
           setError({status: true, text: t("errors.failedPatchAsset")})
        }
    }

    const editAssetReservability = async (asset: any) => {

        try {
            const response = await api.patch(asset.assetinstance.selfUrl, {isReservable: !asset.isReservable},
                {
                    headers: {"Content-type": types.VND_ASSET_INSTANCE
                    }
                })
            await fetchUserAssetDetails()
        } catch (e) {
            setError({status: true, text: t("errors.failedPatchAsset")})
        }


    }

    const postImage = async (image) => {
        try {
            const response: any = await api.post("/images", {image: image}, {headers: {"Content-type": "multipart/form-data"}})
            return extractId(response.headers.get("Location"))
        } catch (e) {
           setError({status: true, text: t(t("errors.failedPostImage"))})
        }

    }
    const editAsset = async (asset: any, originalAsset: any) => {

        //working on image
        const image = asset.image

        let data = {
            status: asset.status,
            isReservable: asset.isReservable,
            maxDays: asset.maxDays,
            description: asset.description,
            physicalCondition: asset.physicalCondition
       }

        if(image !== undefined && image !== null) {
            // @ts-ignore
            data = {...data, imageId: await postImage(image)}
        }

        try {
            const response = await api.patch(originalAsset.assetinstance.selfUrl, data,
                {
                    headers: {
                        "Content-type": types.VND_ASSET_INSTANCE
                    }
                })
            await fetchUserAssetDetails()
        } catch (e) {
            setError({status: true, text: t("errors.failedPatchAsset")})
        }


    }


    return {
        editAssetVisbility, editAssetReservability, editAsset, error
    }
}

export default userUserAssetInstanceOptions;