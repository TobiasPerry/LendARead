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

        await api.patch(asset.assetinstance.selfUrl, {status: asset.status, isReservable: asset.isReservable, maxDays: asset.maxDays, description: asset.description, physicalCondition: asset.physicalCondition},
            {
                headers: {"Content-type": "multipart/form-data"
                }
            })
        await fetchUserAssetDetails()

    }


    return {
        editAssetVisbility, editAssetReservability, editAsset
    }
}

export default userUserAssetInstanceOptions;