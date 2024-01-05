import {Api} from "../api/api.ts";
import {LocationType} from "../../views/user/Locations.tsx";

const useLocations = () => {

    const editLocation = (location: any) => {

    }

    const deleteLocation = (location: any) => {

    }

    const getLocations = async (userId: any) => {
        try {
            const response = await Api.get(`/locations?userId=${userId}`);
            console.log(response);
            // @ts-ignore
            return []
        } catch (error) {
            return []
        }
    };

    const addLocation = async (location: LocationType) => {
        console.log("posting the location", location)
        try {
            const response = await Api.post(`/locations`, {...location}, false, {"Content-Type": "application/vnd.location.v1+json"})
            console.log(response)
        } catch (e) {
            console.log('error', e)
        }
    }

    return {
        editLocation, deleteLocation, getLocations, addLocation
    }
}

export default useLocations