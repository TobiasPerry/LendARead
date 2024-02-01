import {useState} from "react";
import {emptyUserDetails} from "../../contexts/authContext.tsx";
import {api, api_} from "../api/api.ts";
// @ts-ignore
import photoHolder from "../../../public/static/profile_placeholder.jpeg"
const useUserDetails = () => {

    const [userDetails, setUserDetails] = useState(emptyUserDetails)

    const getUserDetails = async (userId: string | number) => {
        const userDetails = (await api.get(`/users/${userId}`)).data
        if(userDetails.image) {
            const image = (await api_.get(userDetails.image)).data
            if(image !== undefined)
                setUserDetails({...userDetails, image: image})
        } else {
            setUserDetails({...userDetails, image: photoHolder})
        }
    }

    return {
        userDetails, getUserDetails
    }
}

export default useUserDetails;