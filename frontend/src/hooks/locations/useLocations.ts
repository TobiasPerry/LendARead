import {api, api_} from "../api/api.ts";
import {LocationType} from "../../views/user/Locations.tsx";

const useLocations = () => {

    const editLocation = async (location: any) => {
        console.log(location)
        try {
            const response = await api_.patch(location.selfUrl, location,
                {
                    headers: {"Content-Type": "application/vnd.location.v1+json"}
                }
            )
            console.log(response)
            // @ts-ignore
            return true
        } catch (error) {
            return false
        }
    }

    const deleteLocation = async (location: any) => {
        try {
            const response = await api_.delete(location.selfUrl)
            console.log(response)
            // @ts-ignore
            return true
        } catch (error) {
            return false
        }
    }

    const getLocations = async (userId: any) => {
        try {
            const response = await api.get(`/locations?userId=2`);
            return await response.data
        } catch (error) {
            return []
        }
    };

    //need to add constraints to the form in jsx, like countyr more than 3 chars!
    const addLocation = async (location: LocationType) => {
        try {
            const response = api.post('/locations', location, {
                headers: { "Content-Type": "application/vnd.location.v1+json" }
            });
        } catch (e) {
            return false
        }
    }

    return {
        editLocation, deleteLocation, getLocations, addLocation
    }
}

export default useLocations