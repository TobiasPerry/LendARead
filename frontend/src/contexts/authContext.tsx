import { createContext, useState } from 'react';
import {Api} from "../hooks/api/api.ts";
import {jwtDecode} from "jwt-decode";

export const AuthContext = createContext({login:  async (email: string, password: string, rememberMe: boolean = false, path: string = "/assets"): Promise<boolean> => { return false}, user: -1, logout: () => {}});

export const AuthProvider = (props: any) => {

    const [user, setUser] = useState(-1);

    const extractUserId = (jwt: string): number => {
        console.log(jwt)
        const decoded = jwtDecode(jwt).userReference;
        const pattern = /\/(\d+)(?=\/?$)/;
        const match = decoded.match(pattern);
        const out = match ? match[1] : -1;
        console.log(out)
        return out
    }
    const login = async (email: string, password: string, rememberMe: boolean = false, path: string = "/assets"): Promise<boolean> => {
        try {

            const response: any = await Api.get(path,
                {
                    "Authorization": "Basic " + btoa(`${email}:${password}`)
                },
                rememberMe
            );

            setUser(extractUserId(response.headers.get('jwt')))


            return response.headers.has('jwt')
        } catch (error) {
            console.log("error raised", error);
            return false;
        }
    };

    const logout = () => {
        // Implement logout logic here
    };

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {props.children}
        </AuthContext.Provider>
    );
};
