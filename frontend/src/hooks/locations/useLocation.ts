import {useState} from "react";
import {LocationApi} from "../../views/user/Locations.tsx";
import {api_} from "../api/api.ts";

const useLocationAsset = () => {

    const [location, setLocation] = useState<LocationApi>(
        { name: "",
        province: "",
        country: "",
        locality: "",
        zipcode: 0,
        selfUrl:""
    })
    const getLocation = async (locationUrl) => {
        const location: LocationApi = (await api_.get(locationUrl.assetinstance.locationReference)).data
        setLocation(location)
    }

    return {
        getLocation, location
    }
}

export default useLocationAsset;