import React, {useEffect, useState} from "react";
import {jwtDecode} from "jwt-decode";
import {api, api_} from "../hooks/api/api";
// @ts-ignore
import defaultUserPhoto from "/static/user-placeholder.jpeg";
import {useTranslation} from "react-i18next";

export interface UserDetailsApi {
    email: string | undefined,
    image: string
    rating: number
    ratingAsBorrower: number
    ratingAsLender: number
    role: string
    selfUrl: string
    telephone: string
    userName: string
}

export const emptyUserDetails: UserDetailsApi = {
    email: "",
    image: "",
    rating: 0 ,
    ratingAsBorrower: 0,
    ratingAsLender: 0,
    role: "",
    selfUrl: "",
    telephone: "",
    userName: "",
}
export const AuthContext = React.createContext({
    isLoggedIn: false,
    logout: () => {
    },
    login: async (email: string, password: string, rememberMe: boolean = false, path: string = "/assets")  => {
        return false
    },
    handleChangePassword: async (email: string, verficationCode: string, password: string, repeatedPassword: string) => {
      return ""
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
    const isInSessionStorage = sessionStorage.hasOwnProperty("userAuthToken");
    const [isLoggedIn, setLoggedIn] = useState(isInLocalStorage || isInSessionStorage);
    const token = isInLocalStorage ? localStorage.getItem("userAuthToken") : sessionStorage.getItem("userAuthToken")
    const [authKey, setAuthKey] = useState(token);

    const {t} = useTranslation()

    api.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;
    api_.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;




    useEffect(() => {
        if(isLoggedIn || isInLocalStorage)
            handleJWT(token)
    }, [token])

    const extractUserId = (jwt: string): number => {
        //@ts-ignore
        const decoded = jwtDecode(jwt).userReference;
        const pattern = /\/(\d+)(?=\/?$)/;
        const match = decoded.match(pattern);
        return match ? match[1] : -1
    }

    const [user, setUser] = useState(() => {
       try {
           return extractUserId(token)
       } catch (e) {
           if(isLoggedIn) {
               return -1
           }
       }
    });
    const [userImage, setUserImage] = useState(defaultUserPhoto);
    const [userDetails, setUserDetails] = useState( emptyUserDetails);

    const handleJWT = async (jwt: string, rememberMe = false)  => {
        if(jwt === undefined || jwt === null)
            return false

        if(rememberMe)
            localStorage.setItem("userAuthToken", jwt)
        else
            sessionStorage.setItem("userAuthToken", jwt)

        await setUser(extractUserId(jwt))
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


    const getUserDetails = async (id: number) => {
        return (await api.get(`/users/${id}`)).data
    }
    const storeUserDetails = async (id: number) => {
        const userDetails = await getUserDetails(id)
        if(userDetails.image !== null && userDetails.image !== undefined) {
            setUserImage(userDetails.image)
        }

        setUserDetails(userDetails)
    }

    // For path to login we use the root as a default
    const login = async (email: string, password: string, rememberMe: boolean = false, path: string = "/"): Promise<boolean> => {
        try {

            const response: any = await api.get(path,
                {
                    headers: { "Authorization": "Basic " + btoa(`${email}:${password}`) }
                }
            );

            const res = await handleJWT(response.headers.get('x-jwt'), rememberMe)
            return res
        } catch (error) {
            console.log(error);
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

        try {

            logout()

            //login with verification code using base64
            const response: any = await api.get("/assets",
                {
                    headers: { "Authorization": "Basic " + btoa(`${email}:${verficationCode}`) }
                }
            );


            const jwt = response.headers.get('x-jwt');
            const userId_ = extractUserId(jwt)

            if(userId_ === -1) {
                return t('changePassword.invalidVerificationCode')
            }

            api.defaults.headers.common['Authorization'] = `Bearer ${jwt}`;
            api_.defaults.headers.common['Authorization'] = `Bearer ${jwt}`;

            const response2 = await api.patch(
                `/users/${userId_}`,
                {
                    password: password
                },
                {
                    headers: {
                        'Content-Type': 'application/vnd.user.v1+json'
                    }
                }
            )

            return "true";
        } catch (e) {
            return t('changePassword.somethingWentWrong')
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