import { api } from '../api/api';
import {useTranslation} from "react-i18next";
import {useState} from "react";


const useLanguages = () => {

    const {t} = useTranslation()
    const [error, setError] = useState({state: false, text: ""})

    const getAllLanguages = async () => {
        try {
            const response = await api.get(`/languages`)
            if (response.status !== 200) {
                setError({state: true, text: t("errors.failedToFetchLanguages") + response})
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
                setError({state: true, text: t("errors.failedToFetchLanguages") + response})
                return []
            }
            return response.data
        } catch (error) {
            return []
        }
    }
    return {
        getAllLanguages,
        getLanguage,
        error
    };
}

export default useLanguages;
