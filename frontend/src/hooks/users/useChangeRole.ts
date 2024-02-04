import { api } from '../api/api.ts';
import {useContext, useState} from 'react';
import { AuthContext } from '../../contexts/authContext.tsx';
import {useTranslation} from "react-i18next";

const useChangeRole = () => {
    // @ts-ignore
    const { changeUserRole } = useContext(AuthContext)
    const [error, setError] = useState({status: false, text: ""})
    const {t} = useTranslation()

    const makeLender = async (userId: string): Promise<boolean> => {
        const response = await api.patch(`/users/${userId}`,
            { "role": "LENDER" },
            { headers: { "Content-Type": "application/vnd.user.v1+json" } })

        if (response.status !== 204) {
            setError({status: true, text: t("errors.failedToChangeRole")})
            return false
        }
        await changeUserRole()
        return true
    }
    return { makeLender, error }
}

export default useChangeRole;
