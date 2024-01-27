import {api} from "../api/api.ts";

const useChangePassword = () => {

    const handleChangePassword = (verficationCode: string, password: string, repeatedPassword: string) => {
        if(password !== repeatedPassword)
            return false;

        try {
        } catch (e) {

        }
    }

    return {
        handleChangePassword
    }
}

export default useChangePassword;