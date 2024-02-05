import {useState} from "react";
import {emptyUserDetails} from "../../contexts/authContext.tsx";
import {api, api_} from "../api/api.ts";
// @ts-ignore
import photoHolder from "../../../public/static/profile_placeholder.jpeg"
import {useTranslation} from "react-i18next";
const useUserDetails = () => {

    const [userDetails, setUserDetails] = useState(emptyUserDetails)
    const {t} = useTranslation()
    const [error, setError] = useState({state: false, text: ""})
    const [notFound, setNotFound] = useState(false)

    const retrieveUserDetails = async (userId: string | number) => {
        try {
            const userDetails = (await api.get(`/users/${userId}`)).data
            if(userDetails.image) {
                const image = (await api_.get(userDetails.image)).data
                if(image !== undefined)
                    return {...userDetails, image: userDetails.image}
            } else {
                return {...userDetails, image: photoHolder}
            }
        } catch (e) {
            setError({state: true, text: t("errors.failedToFetchUserDetails")} )
            return emptyUserDetails
        }

    }
    const getUserDetails = async (userId: string | number) => {
        const res = await retrieveUserDetails(userId);
        if(res === emptyUserDetails){
            setNotFound(true)
        }else{
            setUserDetails(res)
        }
    }

    return {
        userDetails, getUserDetails, retrieveUserDetails, notFound, error
    }
}

export default useUserDetails;