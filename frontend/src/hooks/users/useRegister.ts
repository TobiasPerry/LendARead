import {api } from '../api/api.ts';
import {getErrorMsg} from "../assetInstance/useAssetInstance.ts";

const useRegister = () => {
    const register = async (email: string, password: string, repeatPassword: string, name: string) => {
        try {
            const userData = {
                "userName": "",
                "telephone": "",
                "email": email,
                "password": password,
                "repeatPassword": repeatPassword,
                "name": name
            };

            const response = await api.post('/users', userData,{ headers: { 'Content-Type': 'application/vnd.user.v1+json' }});

            // @ts-ignore
            return {
                successfulRegister: true,
                errorMessage: ""
            };
            // return false;

        } catch (error) {
            const errorMsg = getErrorMsg(error.response)
            return {
                successfulRegister: false,
                errorMessage: errorMsg
            };
        }
    };

    return {
        register
    };
};

export default useRegister;
