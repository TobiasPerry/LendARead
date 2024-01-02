
import {Api} from "./api/api";

const useLogin = () => {

    const login = async (email: string, password: string) => {

        try {
            const response: any = await Api.get("/users",
                    {"Authorization": "Basic " + btoa(`${email}:${password}`)})
            console.log('Logging in with', email, password);
            console.log('response', response);
            return true;

        } catch (error) {
            // Handle errors (e.g., incorrect credentials, network issues)
            console.log("error")
            return false
        }
    };

    return {
      login
    };
};

export default useLogin;
