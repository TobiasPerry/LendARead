import {Api} from "../api/api.ts";
import {LocationType} from "../../views/user/Locations.tsx";

const useLocations = () => {

    const editLocation = (location: any) => {

    }

    const deleteLocation = (location: any) => {

    }

    const getLocations = async (userId: any) => {
        try {
            const response = await Api.get(`/locations?userId=2`);
            // @ts-ignore
            return await response.json()
        } catch (error) {
            return []
        }
    };

    //need to add constraints to the form in jsx, like countyr more than 3 chars!
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