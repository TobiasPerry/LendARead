
import {Api} from "./api/api";

const useAssetInstance = () => {

    const handleAllAssetInstances = async () => {

        try {
            const data = await Api.get("/assetInstances/1")
            console.log(data)
            return data
        }catch (error){
            console.log("Error")
            return null;
        }
    }
    return {
        handleAllAssetInstances
    };
}

export default useAssetInstance;