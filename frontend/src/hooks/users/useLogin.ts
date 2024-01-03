
import {Api} from "../api/api.ts";

const useLogin = () => {
    const login = async (email: string, password: string, rememberMe: boolean = false, path: string = "/assets") => {
        try {

            const response: any = await Api.get(path,
                {
                     "Authorization": "Basic " + btoa(`${email}:${password}`)
                },
                rememberMe
            );


            return response.headers.has('jwt')
        } catch (error) {
            console.log("error raised", error);
            return false;
        }
    };

    return {
      login
    };
};

export default useLogin;
