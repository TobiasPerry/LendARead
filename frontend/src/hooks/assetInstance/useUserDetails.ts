import {useState} from "react";
import {emptyUserDetails} from "../../contexts/authContext.tsx";
import {api} from "../api/api.ts";

const useUserDetails = () => {

    const [userDetails, setUserDetails] = useState(emptyUserDetails)

    const getUserDetails = async (userId: number) => {
        const userDetails = (await api.get(`/users/${userId}`)).data
    }

    return {
        userDetails, getUserDetails
    }
}

export default useUserDetails;