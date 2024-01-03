
// import {Api} from "../api/api.ts";

const useLogin = () => {
    const login = async (email: string, password: string, path: string = "/assets") => {
        try {
            const response: any = await fetch("http://127.0.0.1:8082/api" + path, {
                method: "GET",
                headers: {
                    "Authorization": "Basic " + btoa(`${email}:${password}`)
                }
            });

            console.log('Logging in with', email, password);
            console.log('response', response);

            // Correct way to access a specific header
            const jwt = response.headers.get('jwt');
            console.log('jwt', jwt);

            // Iterate through the headers and log each one
            response.headers.forEach((value: any, name: any) => {
                console.log(`${name}: ${value}`);
            });

            // Assuming the response is JSON
            const responseBody = await response.json();
            console.log('body', responseBody);

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
