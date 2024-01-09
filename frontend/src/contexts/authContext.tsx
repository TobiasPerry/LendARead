import {useState} from "react";
import React from "react";
import {jwtDecode} from "jwt-decode";
import {api, api_} from "../hooks/api/api";

export const AuthContext = React.createContext({
    isLoggedIn: false,
    logout: () => {
    },
    login: async (email: string, password: string, rememberMe: boolean = false, path: string = "/assets")  => {
        return false
    },
    user: -1,
});

const AuthContextProvider = (props) => {
    const isInLocalStorage = localStorage.hasOwnProperty("userAuthToken");
    const [isLoggedIn, setLoggedIn] = useState(isInLocalStorage || sessionStorage.hasOwnProperty("userAuthToken"));
    const token = isInLocalStorage ?localStorage.getItem("userAuthToken") : sessionStorage.getItem("userAuthToken")
    const [authKey, setAuthKey] = useState(token);

    api.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;
    api_.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;

    const [user, setUser] = useState(-1);

    const extractUserId = (jwt: string): number => {
        //@ts-ignore
        const decoded = jwtDecode(jwt).userReference;
        const pattern = /\/(\d+)(?=\/?$)/;
        const match = decoded.match(pattern);
        const out = match ? match[1] : -1;
        return out
    }

    const handleJWT = (jwt: string, rememberMe): boolean => {
        if(jwt === undefined || jwt === null)
            return false

        if(rememberMe)
            localStorage.setItem("userAuthToken", jwt)
        else
            sessionStorage.setItem("userAuthToken", jwt)

        setUser(extractUserId(jwt))
        setAuthKey(jwt);
        setLoggedIn(true);
        api.defaults.headers.common['Authorization'] = `Bearer ${jwt}`;
        api_.defaults.headers.common['Authorization'] = `Bearer ${jwt}`;
        return true
    }

    const logout = () => {
        localStorage.removeItem("userAuthToken");
        sessionStorage.removeItem("userAuthToken");
        setLoggedIn(false);
        setAuthKey('');
        setUser(-1);
    }


    const login = async (email: string, password: string, rememberMe: boolean = false, path: string = "/assets"): Promise<boolean> => {
        try {

            const response: any = await api.get(path,
                {
                    headers: { "Authorization": "Basic " + btoa(`${email}:${password}`) }
                }
            );

            return handleJWT(response.headers.get('jwt'), rememberMe)
        } catch (error) {
            console.log("error raised", error);
            return false;
        }
    };



    return <AuthContext.Provider
        value={{
            isLoggedIn,
            login,
            logout,
            user,
        }}>{props.children}</AuthContext.Provider>
}

export default AuthContextProvider;