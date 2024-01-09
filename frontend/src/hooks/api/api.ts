import axios from 'axios';
import Qs from 'qs';

// Axios instance with baseURL
const api = axios.create({
    baseURL: "http://127.0.0.1:8080/api",
    timeout: 5000,
    paramsSerializer: params => Qs.stringify(params, { arrayFormat: 'repeat' })
});

// Axios instance without baseURL
const api_ = axios.create({
    timeout: 5000,
    paramsSerializer: params => Qs.stringify(params, { arrayFormat: 'repeat' })
});

export { api, api_ };
