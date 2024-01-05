
import {Api} from "../api/api.ts";

const useAssetInstance = () => {

    const handleAllAssetInstances = async () => {

        try {
            const data = await Api.get("/assetInstances")
            // const body = await data.json()
            // console.log(body)
            return data
        } catch (error){
            console.log("Error")
            return null;
        }
    }
    return {
        handleAllAssetInstances
    };
}

export default useAssetInstance;