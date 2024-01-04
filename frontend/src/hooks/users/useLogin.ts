
import {Api} from "../api/api.ts";
import {jwtDecode} from 'jwt-decode';


const useLogin = () => {
    const login = async (email: string, password: string, rememberMe: boolean = false, path: string = "/assets") => {
        try {

            const response: any = await Api.get(path,
                {
                     "Authorization": "Basic " + btoa(`${email}:${password}`)
                },
                rememberMe
            );

            const token = response.headers.get('jwt')
            const decoded = jwtDecode(token);
            console.log('token', token);
            console.log('decoded', decoded);

            // const userId = decoded.userId;
            // console.log('userId', userId);

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
