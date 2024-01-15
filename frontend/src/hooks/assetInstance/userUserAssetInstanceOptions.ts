import {api} from "../api/api.ts";
import {isPublic} from "../../components/user/LendedBooksOptions.tsx";

const userUserAssetInstanceOptions = () => {

    const editAssetVisbility = async (asset: any) => {

        await api.patch(`/assetInstances/${asset.id}`, {state: asset.status === "PUBLIC" ? "PRIVATE" : "PUBLIC" })
    }

    return {
        editAssetVisbility
    }
}

export default userUserAssetInstanceOptions;