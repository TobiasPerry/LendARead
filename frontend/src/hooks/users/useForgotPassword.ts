import {api} from "../api/api.ts";

const useForgotPassword = () => {

    const handleForgotPassword = async (email: string) => {
        try {
            await api.post('/users', {email: email}, {headers: {'Content-Type': 'application/vnd.resetPassword.v1+json'}})
            return true;
        } catch (error) {
            return false;
        }
    }

    return {
        handleForgotPassword
    }
}

export default useForgotPassword;