import {useState} from "react";
import {api_} from "../api/api.ts";
import {LocationApi} from "./useLocations.ts";

const useLocationAsset = () => {

    const [location, setLocation] = useState<LocationApi>(
        { name: "",
        province: "",
        country: "",
        locality: "",
        zipcode: 0,
        selfUrl:""
    })
    const getLocation = async (asset) => {
        if(asset && asset.assetinstance && asset.assetinstance.locationReference) {
            const location: LocationApi = (await api_.get(asset.assetinstance.locationReference)).data
            setLocation(location)
        }
    }

    return {
        getLocation, location
    }
}

export default useLocationAsset;