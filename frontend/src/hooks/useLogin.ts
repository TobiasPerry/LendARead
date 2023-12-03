
import { useState } from 'react';
import {Api} from "./api/api";

const useLogin = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loginError, setLoginError] = useState(false);
    const [rememberMe, setRememberMe] = useState(false);

    const handleEmailChange = (e: any) => {
        setEmail(e.target.value);
    };

    const handlePasswordChange = (e: any) => {
        setPassword(e.target.value);
    };

    const handleLogin = async (e: any) => {
        e.preventDefault();
        setLoginError(false); // Reset the login error state

        try {
            const response: any = Api.post("/users", {  }, rememberMe,
                {"Authorization": "Basic" + btoa(email + ':' + password)})
            console.log('Logging in with', email, password);
            console.log('response', response);

            // On successful login, redirect or change the app state as needed
        } catch (error) {
            // Handle errors (e.g., incorrect credentials, network issues)
            setLoginError(true);
        }
    };

    return {
        email,
        handleEmailChange,
        password,
        handlePasswordChange,
        handleLogin,
        loginError,
        rememberMe,
        setRememberMe
    };
};

export default useLogin;
