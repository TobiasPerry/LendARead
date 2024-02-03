import axios from 'axios';
import Qs from 'qs';
import {jwtDecode} from "jwt-decode";

let baseUrl = `${import.meta.env.VITE_APP_BASE_URL}${import.meta.env.VITE_APP_BASE_PATH}`

if(import.meta.env.VITE_APP_BASE_URL === undefined && import.meta.env.VITE_APP_BASE_PATH === undefined) {
    baseUrl = 'http://localhost:8080/';
    console.log("Using default base url: " + baseUrl)
}
// Axios instance with baseURL
const api = axios.create({
    baseURL: baseUrl + "api",
    timeout: 5000,
    paramsSerializer: params => Qs.stringify(params, { arrayFormat: 'repeat' })
});

// Axios instance without baseURL
const api_ = axios.create({
    timeout: 5000,
    paramsSerializer: params => Qs.stringify(params, { arrayFormat: 'repeat' })
});


const refreshToken = async () => {
    const rememberMe = localStorage.getItem("rememberMe");
    const refreshToken = rememberMe ? localStorage.getItem("refreshToken") : sessionStorage.getItem('refreshToken');

    if (!refreshToken) {
        console.log("No refresh token found");
        return null;
    }

    try {
        const decoded = jwtDecode(refreshToken);
        const currentTime = Date.now() / 1000;

        if (decoded.exp < currentTime) {
            console.log("Refresh token expired");
            return null;
        }

        // Proceed with token refresh
        const response = await axios.post('/api/refresh-token', { refreshToken });
        const { token } = response.data;

        if (rememberMe) localStorage.setItem('userAuthToken', token);
        else sessionStorage.setItem('userAuthToken', token);

        return token;
    } catch (error) {
        console.error("Error decoding refresh token", error);
        return null;
    }
}

api.interceptors.response.use(response => {
    return response;
}, async (error) => {
    const originalRequest = error.config;

    // Check if the response is a token expired error
    if (error.response.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true;

        const newToken = await refreshToken();

        axios.defaults.headers.common['Authorization'] = `Bearer ${newToken}`;
        originalRequest.headers['Authorization'] = `Bearer ${newToken}`;

        return api(originalRequest);
    }

    return Promise.reject(error);
});

api_.interceptors.response.use(response => {
    return response;
}, async (error) => {
    const originalRequest = error.config;

    // Check if the response is a token expired error
    if (error.response.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true;

        const newToken = await refreshToken();

        axios.defaults.headers.common['Authorization'] = `Bearer ${newToken}`;
        originalRequest.headers['Authorization'] = `Bearer ${newToken}`;
        console.log("refreshed the token, if you login again all working!!")
        return api_(originalRequest);
    }

    return Promise.reject(error);
});



export { api, api_ };
