import {api, api_} from "../api/api.ts";
import Vnd from "../api/types.ts";
import {useContext, useState} from "react";
import {AuthContext} from "../../contexts/authContext.tsx";
import {useTranslation} from "react-i18next";

export interface LocationApi {
    name: string,
    province: string,
    country: string,
    locality: string,
    zipcode: number,
    selfUrl: string
}
const useLocations = () => {

    const {user} = useContext(AuthContext)
    const { i18n } = useTranslation();
    const [isLoading, setIsLoading] = useState(false)
    const emptyLocation = {name: "", province: "", country: "", locality: "", zipcode: 0, id: -1}
    const [locations, setLocations] = useState([emptyLocation]);
    const [editingLocation, setEditingLocation] = useState(emptyLocation);
    const currentLanguage = i18n.language;

    const editLocation = async (location: any) => {
        try {
            const response = await api_.patch(location.selfUrl, location,
                {
                    headers: {
                        "Content-Type": Vnd.VND_LOCATION,
                        "Accept-Language": currentLanguage
                    }
                }
            )
            // @ts-ignore
            return true
        } catch (error) {
            console.log(error)
            return false
        }
    }

    const deleteLocation = async (location: any) => {
        try {
            const response = await api_.delete(location.selfUrl)
            // @ts-ignore
            return true
        } catch (error) {
            return false
        }
    }

    const getLocations = async (userId: any) => {
        setIsLoading(true)
        try {
            const response = await api.get(`/locations`, {
                headers: {
                    "Accept-Language": currentLanguage
                },
                params: {
                    userId: userId
                }
            })
            setIsLoading(false)
            return response.data
        } catch (error) {
            setIsLoading(false)
            return []
        }
    };

    const addLocation = async (location: LocationApi) => {
        try {
            const response = await api.post('/locations', location, {
                headers: {
                    "Content-Type": Vnd.VND_LOCATION,
                    "Accept-Language": currentLanguage
                }
            });
            return response.status !== 400;
        } catch (e) {
            return false
        }
    }

    const fetchLocation = async () => {
        try {
            const response = await getLocations(user);
            await setLocations(response);
        } catch (error) {
            console.error("Failed to fetch locations:", error);
        }
    }

    return {
        editLocation, deleteLocation, getLocations, addLocation, isLoading, fetchLocation, locations, editingLocation, setEditingLocation, emptyLocation
    }
}

export default useLocations
