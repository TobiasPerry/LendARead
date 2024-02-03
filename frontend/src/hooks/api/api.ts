import axios from 'axios';
import Qs from 'qs';

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
    const refreshToken = localStorage.getItem('refreshToken');

    const response = await axios.post('/api/refresh-token', { refreshToken });
    const { token } = response.data;

    localStorage.setItem('userAuthToken', token);
    return  token;
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


export { api, api_ };
