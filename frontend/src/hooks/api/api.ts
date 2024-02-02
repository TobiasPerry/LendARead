import axios from 'axios';
import Qs from 'qs';

let baseUrl = `${import.meta.env.VITE_APP_BASE_URL}${import.meta.env.VITE_APP_BASE_PATH}`

if(import.meta.env.VITE_APP_BASE_URL === undefined && import.meta.env.VITE_APP_BASE_PATH === undefined) {
    baseUrl = 'http://localhost:8080/';
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

export { api, api_ };
