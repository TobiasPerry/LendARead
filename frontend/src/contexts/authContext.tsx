import React, {useState} from "react";
import {jwtDecode} from "jwt-decode";
import {api, api_} from "../hooks/api/api";
// @ts-ignore
import defaultUserPhoto from "../../public/static/user-placeholder.jpeg";

export interface UserDetailsApi {
    email: string
    image: string
    rating: number
    ratingAsBorrower: number
    ratingAsLender: number
    role: string
    selfUrl: string
    telephone: string
    userName: string
}
export const AuthContext = React.createContext({
    isLoggedIn: false,
    logout: () => {
    },
    login: async (email: string, password: string, rememberMe: boolean = false, path: string = "/assets")  => {
        return false
    },
    handleChangePassword: async (email: string, verficationCode: string, password: string, repeatedPassword: string) => {
      return false
    },
    handleForgotPassword: async (email: string) => {
        return false
    },
        user: -1,
    userDetails: {
        email: "",
        image: "",
        rating: 0 ,
        ratingAsBorrower: 0,
        ratingAsLender: 0,
        role: "",
        selfUrl: "",
        telephone: "",
        userName: "",
    },
    userImage: ""
});

const AuthContextProvider = (props) => {
    const isInLocalStorage = localStorage.hasOwnProperty("userAuthToken");
    const [isLoggedIn, setLoggedIn] = useState(isInLocalStorage || sessionStorage.hasOwnProperty("userAuthToken"));
    const token = isInLocalStorage ?localStorage.getItem("userAuthToken") : sessionStorage.getItem("userAuthToken")
    const [authKey, setAuthKey] = useState(token);

    api.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;
    api_.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;

    const [user, setUser] = useState(-1);
    const [userImage, setUserImage] = useState(defaultUserPhoto);
    const [userDetails, setUserDetails] = useState({
        email: "",
        image: "",
        rating: 0 ,
        ratingAsBorrower: 0,
        ratingAsLender: 0,
        role: "",
        selfUrl: "",
        telephone: "",
        userName: "",
    })

    const extractUserId = (jwt: string): number => {
        //@ts-ignore
        const decoded = jwtDecode(jwt).userReference;
        const pattern = /\/(\d+)(?=\/?$)/;
        const match = decoded.match(pattern);
        return match ? match[1] : -1
    }

    const handleJWT = (jwt: string, rememberMe): boolean => {
        if(jwt === undefined || jwt === null)
            return false

        if(rememberMe)
            localStorage.setItem("userAuthToken", jwt)
        else
            sessionStorage.setItem("userAuthToken", jwt)

        setUser(extractUserId(jwt))
        storeUserDetails(extractUserId(jwt))
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

    const storeUserDetails = async (id: number) => {
        const userDetails: UserDetailsApi = (await api.get(`/users/${id}`)).data
        console.log(userDetails)
        const image = (await api_.get(userDetails.image)).data
        if(image)
            setUserImage(image)
        setUserDetails(userDetails)
    }

    const login = async (email: string, password: string, rememberMe: boolean = false, path: string = "/assets"): Promise<boolean> => {
        try {

            const response: any = await api.get(path,
                {
                    headers: { "Authorization": "Basic " + btoa(`${email}:${password}`) }
                }
            );

            return handleJWT(response.headers.get('x-jwt'), rememberMe)
        } catch (error) {
            console.log("error raised", error);
            return false;
        }
    };

    const handleForgotPassword = async (email: string) => {
        try {
            await api.post('/users', {email: email}, {headers: {'Content-Type': 'application/vnd.resetPassword.v1+json'}})
            return true;
        } catch (error) {
            return false;
        }
    }

    const handleChangePassword = async (email: string, verficationCode: string, password: string, repeatedPassword: string) => {
        if(password !== repeatedPassword)
            return false;

        try {
        } catch (e) {

        }
    }


    return <AuthContext.Provider
        value={{
            isLoggedIn,
            login,
            logout,
            user,
            userDetails,
            userImage,
            handleForgotPassword,
            handleChangePassword
        }}>{props.children}</AuthContext.Provider>
}

export default AuthContextProvider;