
/**
 * TODO: DEBERIA ESTAR EN UN CONTEXT? ME PUEDE LLEGAR A JODER MUCHAS INSTANCIAS DE ESTO.
 * */
export class Api {
    private static token: string = "";
    private static authenticated: Boolean = false;

    static get baseUrl() {
        return "http://127.0.0.1:8082/api";
    }

    static get timeout() {
        return 60 * 1000;
    }

    static saveToken(token: string, remember: boolean) {
        this.token = token;
        if (remember) {
            localStorage.setItem('apiToken', token);
            console.log("saved token")
        } else {
            sessionStorage.setItem('apiToken', token);
        }
    }

    static retrieveToken() {
        if(this.authenticated) return
        const token = localStorage.getItem('apiToken') || sessionStorage.getItem('apiToken');
        if (token) {
            this.token = token;
            this.authenticated = true;
        }
    }

    static handleNewToken(method: string, headers: any, rememberMe: boolean) {

        if (method === "GET" && headers.has('JWT')) {
            console.log('saving token!!')
            Api.saveToken(headers.get('JWT'), rememberMe);
        }
    }

    static async fetch(url: any, init: any = {}, rememberMe: boolean = false) {
        if (!this.token) {
            this.retrieveToken();
        }

        if (this.authenticated) {
            if (!init.headers) init.headers = {};
            init.headers['Authorization'] = `Bearer ${this.token}`;
        }

        const controller = new AbortController();
        init.signal = controller.signal;
        const timer = setTimeout(() => controller.abort(), Api.timeout);

        try {
            const response = await fetch(this.baseUrl + url, init);
            this.handleNewToken(init.method, response.headers, rememberMe);
            return response;
        } catch (error: any) {
            if (error.code) throw error;
        } finally {
            clearTimeout(timer);
        }
    }
    static async get(url: string, headers?: any, rememberMe: boolean = false) {
        return await Api.fetch(
             url ,
            {
                method: "GET",
                headers: headers,
            },
            rememberMe
        );
    }

    static async post(url: string, data: object, rememberMe: boolean = false, headers?: any) {
        return await Api.fetch(
            url,
            {
                method: "POST",
                headers: headers,
                body: JSON.stringify(data),
            },
            rememberMe
        );
    }

    static async put(url: string , data: object,headers?: any) {
        return await Api.fetch(
            url,
            {
                method: "PUT",
                headers: headers,
                body: JSON.stringify(data),
            },

        );
    }

    static async delete(url: string) {
        return await Api.fetch(
            url,
            {
                method: "DELETE",
            },

        );
    }
}

