import React, {useEffect, useState} from "react";
import {jwtDecode} from "jwt-decode";
import {api, api_} from "../hooks/api/api";
// @ts-ignore
import defaultUserPhoto from "/static/user-placeholder.jpeg";
import {useTranslation} from "react-i18next";
import {extractId} from "../hooks/assetInstance/useUserAssetInstances.ts";
import Vnd from "../hooks/api/types.ts";

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
    login: async (email: string, password: string, rememberMe: boolean = false, path: string = "/assets") => {
        return false
    },
    handleChangePassword: async (email: string, verficationCode: string, password: string, repeatedPassword: string) => {
        return ""
    },
    handleForgotPassword: async (email: string) => {
        return false
    },
    uploadUserImage: async (image: any) => {

    },
    changeUserRole: async () => {
    },
    user: "",
    userDetails: {
        email: "",
        image: "",
        rating: 0,
        ratingAsBorrower: 0,
        ratingAsLender: 0,
        role: "",
        selfUrl: "",
        telephone: "",
        userName: "",
    },
    userImage: "",
    smallUserImage: "",
});

export const logoutStorages = () => {
    localStorage.removeItem("userAuthToken");
    sessionStorage.removeItem("userAuthToken");
    localStorage.removeItem("refreshToken");
    sessionStorage.removeItem("refreshToken");
    localStorage.removeItem("rememberMe");
    sessionStorage.removeItem("rememberMe");
    window.location.reload();
}
const AuthContextProvider = (props) => {
    const isInLocalStorage = localStorage.hasOwnProperty("userAuthToken") && localStorage.hasOwnProperty("refreshToken");
    const isInSessionStorage = sessionStorage.hasOwnProperty("userAuthToken") && sessionStorage.hasOwnProperty("refreshToken");
    const [isLoggedIn, setLoggedIn] = useState(isInLocalStorage || isInSessionStorage);
    const token = isInLocalStorage ? localStorage.getItem("userAuthToken") : sessionStorage.getItem("userAuthToken")
    const refreshToken = isInLocalStorage ? localStorage.getItem("refreshToken") : sessionStorage.getItem("refreshToken")
    const [authKey, setAuthKey] = useState(token);

    const { i18n } = useTranslation();
    const currentLanguage = i18n.language;
    const {t} = useTranslation()

    api.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;
    api_.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;




    useEffect(() => {
        if(isLoggedIn || isInLocalStorage)
            handleJWT(token, refreshToken).then()
    }, [token, refreshToken])

    const extractUserId = (jwt: string): string => {
        try {
            //@ts-ignore
            const decoded = jwtDecode(jwt).userReference;
            const pattern = /\/(\d+)(?=\/?$)/;
            const match = decoded.match(pattern);
            return match ? match[1] : ""
        } catch (e) {
            return ""
        }

    }

    const [user, setUser] = useState(() => {
       try {
           return extractUserId(token)
       } catch (e) {
           if(isLoggedIn) {
               return ""
           }
       }
    });
    const [userImage, setUserImage] = useState(defaultUserPhoto);
    const [smallUserImage, setSmallUserImage] = useState(defaultUserPhoto);
    const [userDetails, setUserDetails] = useState( emptyUserDetails);

    const handleJWT = async (jwt: string, refreshToken: string, rememberMe = false)  => {
        if(jwt === undefined || jwt === null)
            return false

        localStorage.setItem("rememberMe", String(rememberMe))

        if(rememberMe) {
            localStorage.setItem("userAuthToken", jwt)
            localStorage.setItem("refreshToken", refreshToken)
        } else {
            sessionStorage.setItem("userAuthToken", jwt)
            sessionStorage.setItem("refreshToken", refreshToken)
        }

        await setUser(extractUserId(jwt))
        storeUserDetails(extractUserId(jwt))
        setAuthKey(jwt);
        setLoggedIn(true);
        api.defaults.headers.common['Authorization'] = `Bearer ${jwt}`;
        api_.defaults.headers.common['Authorization'] = `Bearer ${jwt}`;
        return true
    }

    const logout = () => {

        logoutStorages()
        setLoggedIn(false);
        setAuthKey('');
        setUser("");
    }


    const getUserDetails = async (id: string) => {
        return (await api.get(`/users/${id}`)).data
    }

    const uploadUserImage = async(image: any) => {
        try {
            const response: any = await api.post("/images", {image: image}, {headers: {"Content-type": "multipart/form-data"}})
            const imageId = extractId(response.headers.get("Location"))
            const userResponse = await api.patch(`/users/${user}`, {imageId: imageId}, {headers: {"Content-type": Vnd.VND_USER}})
        } catch (e) {
            
        }
        await storeUserDetails(user)
    }
    const storeUserDetails = async (id: string) => {
        const userDetails = await getUserDetails(id)
        if(userDetails.image !== null && userDetails.image !== undefined) {
            setUserImage(userDetails.image)
            setSmallUserImage(userDetails.image + '?size=PORTADA')
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

            const res = await handleJWT(response.headers.get('x-jwt'), response.headers.get('x-refresh-token'), rememberMe)
            return res
        } catch (error) {
            console.error(error);
            return false;
        }
    };

    const handleForgotPassword = async (email: string) => {
        try {
            await api.post('/users', {email: email}, {headers: {'Content-Type': Vnd.VND_RESET_PASSWORD}})
            return true;
        } catch (error) {
            return false;
        }
    }

    const handleChangePassword = async (email: string, verficationCode: string, password: string, repeatedPassword: string) => {

        try {
            //login with verification code using base64
            const response: any = await api.get("/assets",
                {
                    headers: { "Authorization": "Basic " + btoa(`${email}:${verficationCode}`) }
                }
            );


            const jwt = response.headers.get('x-jwt');
            const userId_ = extractUserId(jwt)

            const savedCredentials = await handleJWT(response.headers.get('x-jwt'), response.headers.get('x-refresh-token'), false)

            if(userId_ === "" || !savedCredentials) {
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
                        'Content-Type': Vnd.VND_USER
                    }
                }
            )

            return "true";
        } catch (e) {
            return t('changePassword.somethingWentWrong')
        }
    }

    const changeUserRole = async () => {
        userDetails.role = "LENDER"
    }


    return <AuthContext.Provider
        value={{
            isLoggedIn,
            login,
            logout,
            user,
            userDetails,
            userImage,
            smallUserImage,
            handleForgotPassword,
            handleChangePassword,
            uploadUserImage,
            changeUserRole
        }}>{props.children}</AuthContext.Provider>
}

export default AuthContextProvider;
