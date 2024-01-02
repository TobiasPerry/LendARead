
import {Api} from "../api/api.ts";

const useLogin = () => {

    const login = async (email: string, password: string) => {

        try {
            //esto no estaria funcando, literal no hace bien el exchange con la api,
            //tambien puede ser porque no hay una sola instancia de Api
            const response: any = await Api.get("/users",
                    {"Authorization": "Basic " + btoa(`${email}:${password}`)})
            console.log('Logging in with', email, password);
            console.log('response', response);
            return true;

        } catch (error) {
            console.log("error")
            return false
        }
    };

    return {
      login
    };
};

export default useLogin;
