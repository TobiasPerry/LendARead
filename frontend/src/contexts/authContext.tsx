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
    const token = isInLocalStorage ? JSON.parse(localStorage.getItem("userAuthToken")) : JSON.parse(sessionStorage.getItem("userAuthToken"))
    const [authKey, setAuthKey] = useState(token);

    api.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userAuthToken")) || JSON.parse(sessionStorage.getItem("userAuthToken")) || ''}`;
    api_.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;

    const extractUserId = (jwt: string): number => {
        console.log(jwt)
        const decoded = jwtDecode(jwt).userReference;
        const pattern = /\/(\d+)(?=\/?$)/;
        const match = decoded.match(pattern);
        const out = match ? match[1] : -1;
        console.log(out)
        return out
    }

    const [user, setUser] = useState(-1);

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

            if(response.headers.has('jwt')) {
                setUser(extractUserId(response.headers.get('jwt')))
                setAuthKey(authKey);
                setLoggedIn(true);
                api.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;
                api_.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;
                return true
            }

            return false
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