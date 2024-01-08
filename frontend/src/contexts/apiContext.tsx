// import {createContext, useContext, useState} from "react";
// import AuthContext from "./authContext.tsx";
//
// export const ApiContext = createContext({
//     get: async (url: string, headers?: any, rememberMe: boolean = false, fullUrl: boolean = false) => {
//         return
//     },
//     patch: async (url: string, data: object, headers?: any, fullUrl: boolean = false) => {
//
//     },
//
//     put: async (url: string, data: object, headers?: any, fullUrl: boolean = false)  => {
//
//     },
//     del: async (url: string, fullUrl: boolean = false)  => {
//     },
//     post: async (url: string, data: object, rememberMe: boolean = false, headers?: any, fullUrl: boolean = false)  => {
//
//     }
// })
//
//
// export const ApiProvider = (props: any) => {
//
//      const auth  = useContext(AuthContext);
//
//      function  baseUrl() {
//         return "http://127.0.0.1:8080/api";
//     }
//
//    function timeout() {
//         return 60 * 1000;
//     }
//
//     function saveToken(token: string, remember: boolean) {
//         if (remember) {
//             localStorage.setItem('apiToken', token);
//             console.log("saved token")
//         } else {
//             sessionStorage.setItem('apiToken', token);
//         }
//     }
//
//     function retrieveToken() {
//         const token = localStorage.getItem('apiToken') || sessionStorage.getItem('apiToken');
//         if (token) {
//             this.auth.token = token;
//         }
//     }
//
//     function handleNewToken(method: string, headers: any, rememberMe: boolean) {
//         if (method === "GET" && headers.has('JWT')) {
//             console.log('saving token!!')
//             saveToken(headers.get('JWT'), rememberMe);
//         }
//     }
//
//     async function  fetch(url: any, init: any = {}, rememberMe: boolean = false, fullUrl: boolean = false) {
//         //necessary for rememberMe
//         // if(this.auth.token === "")
//             this.retrieveToken()
//
//         if (this.auth.token !== "") {
//             if (!init.headers) init.headers = {};
//             init.headers['Authorization'] = `Bearer ${this.auth.token}`;
//         }
//
//         const controller = new AbortController();
//         init.signal = controller.signal;
//         const timer = setTimeout(() => controller.abort(), timeout());
//
//         try {
//             const response = await fetch( (fullUrl) ? url : baseUrl() + url, init);
//             handleNewToken(init.method, response.headers, rememberMe);
//             return response;
//         } catch (error: any) {
//             if (error.code) throw error;
//         } finally {
//             clearTimeout(timer);
//         }
//     }
//     const get = async (url: string, headers?: any, rememberMe: boolean = false, fullUrl: boolean = false): Promise<any> => {
//         return await fetch(
//              url ,
//             {
//                 method: "GET",
//                 headers: headers,
//             },
//             rememberMe,
//             fullUrl
//         );
//     }
//
//     async function  post(url: string, data: object, rememberMe: boolean = false, headers?: any, fullUrl: boolean = false) {
//         return await fetch(
//             url,
//             {
//                 method: "POST",
//                 headers: headers,
//                 body: JSON.stringify(data),
//             },
//             rememberMe,
//             fullUrl
//         );
//     }
//
//     async function  put(url: string , data: object,headers?: any, fullUrl: boolean = false) {
//         return await fetch(
//             url,
//             {
//                 method: "PUT",
//                 headers: headers,
//                 body: JSON.stringify(data),
//             },
//  false,
//             fullUrl
//         );
//     }
//
//     async function  del(url: string, fullUrl: boolean = false) {
//         return await fetch(
//             url,
//             {
//                 method: "DELETE",
//             },
//             false,
//             fullUrl
//         );
//     }
//
//     async function patch(url: string , data: object,headers?: any, fullUrl: boolean = false) {
//         return await fetch(
//             url,
//             {
//                 method: "PATCH",
//                 headers: headers,
//                 body: JSON.stringify(data),
//             },
//             false,
//             fullUrl
//         );
//     }
//
//     return (
//         <ApiContext.Provider value={{ get, put, patch, del, post }}>
//             {props.children}
//         </ApiContext.Provider>
//     );
// }
//
