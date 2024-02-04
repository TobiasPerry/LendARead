import { api } from '../api/api.ts';


const useLanguages = () => {
    const getAllLanguages = async () => {
        try {
            const response = await api.get(`/languages`)
            if (response.status !== 200) {
                console.error("Error getting languages: ", response)
                return []
            }
            return response.data
        } catch (error) {
            return []
        }
    };

    const getLanguage = async (languageId: string) => {
        try {
            const response = await api.get(`/languages/${languageId}`)
            if (response.status !== 200) {
                console.error("Error getting language: ", response)
                return {}
            }
            return response.data
        } catch (error) {
            return {}
        }
    }
    return {
        getAllLanguages,
        getLanguage
    };
}

export default useLanguages;
