import { createContext, useState } from 'react';
import {Api} from "../hooks/api/api.ts";
import {jwtDecode} from "jwt-decode";

export const AuthContext = createContext({login:  async (_: string, _: string, _: boolean = false, _: string = "/assets"): Promise<boolean> => { return false}, user: -1, logout: () => {}});

export const AuthProvider = (props: any) => {

    const [user, setUser] = useState(-1);

    const login = async (email: string, password: string, rememberMe: boolean = false, path: string = "/assets"): Promise<boolean> => {
        try {

            const response: any = await Api.get(path,
                {
                    "Authorization": "Basic " + btoa(`${email}:${password}`)
                },
                rememberMe
            );


            const token = response.headers.get('jwt')

            console.log('token', token);

            const decoded = jwtDecode(token);
            console.log('decoded', decoded);



             // const userId = decoded.userId;
             // console.log('userId', userId);

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
