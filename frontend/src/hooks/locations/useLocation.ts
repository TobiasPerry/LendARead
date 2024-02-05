import {useState} from "react";
import {api_} from "../api/api";
import {LocationApi} from "./useLocations";
import {useTranslation} from "react-i18next";

const useLocationAsset = () => {

    const [location, setLocation] = useState<LocationApi>(
        { name: "",
        province: "",
        country: "",
        locality: "",
        zipcode: 0,
        selfUrl:""
    })

    const {t} = useTranslation()
    const [error, setError] = useState({state: false, text: ""})

    const getLocation = async (asset) => {
        if(asset && asset.assetinstance && asset.assetinstance.locationReference) {
            try {
                const location: LocationApi = (await api_.get(asset.assetinstance.locationReference)).data
                setLocation(location)
            } catch (e) {
                setLocation({ name: "",
                    province: "",
                    country: "",
                    locality: "",
                    zipcode: 0,
                    selfUrl:""
                })
                setError({state: true, text: t("errors.failedToFetchLocation")})
            }
        }
    }

    return {
        getLocation, location, error
    }
}

export default useLocationAsset;