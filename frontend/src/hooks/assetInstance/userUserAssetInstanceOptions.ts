import {api} from "../api/api.ts";

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

        const response = (await api.post("/images", {image: image}, {headers: {"Content-type": "multipart/form-data"}})).data

        console.log(response)

        await api.patch(asset.assetinstance.selfUrl, {status: asset.status, isReservable: asset.isReservable, maxDays: asset.maxDays, description: asset.description, physicalCondition: asset.physicalCondition},
            {
                headers: {"Content-type": "application/vnd.assetInstance.v1+json"
                }
            })
        await fetchUserAssetDetails()

    }


    return {
        editAssetVisbility, editAssetReservability, editAsset
    }
}

export default userUserAssetInstanceOptions;