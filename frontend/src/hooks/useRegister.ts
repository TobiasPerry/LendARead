import { Api } from './api/api';

const useRegister = () => {
    const register = async (email: string, password: string, repeatPassword: string, name: string) => {
        try {
            const userData = {
                "userName": "",
                "telephone": "",
                "email": email,
                "password": password,
                "repeatPassword": repeatPassword,
                "name": name
            };

            const response = await Api.post('/users', userData, false,{ 'Content-Type': 'application/vnd.user.v1+json' });
            console.log('Register response', response);

            return response.status === 200;

        } catch (error) {
            console.error('Register error', error);
            return false;
        }
    };

    return {
        register
    };
};

export default useRegister;
