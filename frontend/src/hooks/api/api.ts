import axios from 'axios';
import Qs from 'qs';

const baseUrl = `${import.meta.env.VITE_APP_BASE_URL}${import.meta.env.VITE_APP_BASE_PATH}` || 'http://localhost:8080/';
console.log('baseUrl', baseUrl);

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
