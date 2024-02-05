import axios, {AxiosInstance} from 'axios';
import Qs from 'qs';
import {jwtDecode} from "jwt-decode";
import {useContext} from "react";

let baseUrl = `${import.meta.env.VITE_APP_BASE_URL}${import.meta.env.VITE_APP_BASE_PATH}`

if(import.meta.env.VITE_APP_BASE_URL === undefined && import.meta.env.VITE_APP_BASE_PATH === undefined) {
    baseUrl = 'http://localhost:8080/';
}

const api = axios.create({
    baseURL: baseUrl + "/api",
    timeout: 10000,
    paramsSerializer: params => Qs.stringify(params, { arrayFormat: 'repeat' })
});

const api_ = api



export { api, api_ };
