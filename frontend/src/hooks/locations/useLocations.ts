import {Api} from "../api/api.ts";

const useLocations = () => {

    const editLocation = (location: any) => {

    }

    const deleteLocation = (location: any) => {

    }

    const getLocations = (userId: number): Array<any> => {
        try {
            const response = await Api.get(`/locations?userId=${user}`)
            console.log(response)
        } catch {

            return []
        }

    }

    const addLocation = (location: any, userId: number) => {

    }

    return {
        editLocation, deleteLocation, getLocations, addLocation
    }
}

export default useLocations