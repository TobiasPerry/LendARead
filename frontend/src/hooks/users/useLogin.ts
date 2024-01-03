
import {Api} from "../api/api.ts";

const useLogin = () => {
    const login = async (email: string, password: string, rememberMe: boolean = false, path: string = "/assets") => {
        try {
            const responseBare: any = await fetch("http://127.0.0.1:8082/api" + path, {
                method: "GET",
                headers: {
                    "Authorization": "Basic " + btoa(`${email}:${password}`)
                }
            });

            const response: any = await Api.get(path,
                {
                     "Authorization": "Basic " + btoa(`${email}:${password}`)
                },
                rememberMe
            );


            console.log('response', response);

            const jwt = response.headers.get('jwt');
            console.log('jwt Api', jwt);

            const jwt2 = responseBare.headers.get('jwt');
            console.log('jwt Barebones', jwt2);


            return true;
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
