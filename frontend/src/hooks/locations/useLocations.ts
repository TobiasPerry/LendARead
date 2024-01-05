import {Api} from "../api/api.ts";
import {LocationType} from "../../views/user/Locations.tsx";

const useLocations = () => {

    const editLocation = (location: any) => {

    }

    const deleteLocation = (location: any) => {

    }

    const getLocations = async (userId: number) : Array<any>=> {
        try {
            const response = await Api.get(`/locations?userId=${user}`)
            console.log(response)
            return []
        } catch {

            return []
        }

    }

    const addLocation = async (location: LocationType) => {
        console.log("posting the location", location)
        try {
            const response = await Api.post(`/locations?userId=${user}`, {location})
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