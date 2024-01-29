import {api} from "../api/api.ts";
import {extractId} from "./useUserAssetInstances.ts";

const userUserAssetInstanceOptions = (fetchUserAssetDetails) => {

    const editAssetVisbility = async (asset: any) => {
        await api.patch(asset.assetinstance.selfUrl, { status:  asset.assetinstance.status === "PUBLIC" ? "PRIVATE" : "PUBLIC"},
            {
                headers: {"Content-type": "multipart/form-data"}
            })
        await fetchUserAssetDetails()
    }

    const editAssetReservability = async (asset: any) => {

        await api.patch(asset.assetinstance.selfUrl, {isReservable: !asset.isReservable},
            {
                headers: {"Content-type": "multipart/form-data"
                }
            })

        await fetchUserAssetDetails()
    }

    const editAsset = async (asset: any) => {

        //working on image
        const image = asset.image

        try {
            const response: any = await api.post("/images", {image: image}, {headers: {"Content-type": "multipart/form-data"}})

            const imageId = extractId(response.headers.get("Location"))

            console.log(imageId)
            await api.patch(asset.assetinstance.selfUrl, {
                    imageId: imageId,
                    status: asset.status,
                    isReservable: asset.isReservable,
                    maxDays: asset.maxDays,
                    description: asset.description,
                    physicalCondition: asset.physicalCondition
                },
                {
                    headers: {
                        "Content-type": "application/vnd.assetInstance.v1+json"
                    }
                })
            await fetchUserAssetDetails()
        } catch (e) {

        }
    }


    return {
        editAssetVisbility, editAssetReservability, editAsset
    }
}

export default userUserAssetInstanceOptions;