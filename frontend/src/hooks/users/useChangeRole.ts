import { api } from '../api/api.ts';
import { useContext } from 'react';
import { AuthContext } from '../../contexts/authContext.tsx';

const useChangeRole = () => {
    const { changeUserRole } = useContext(AuthContext)
    const makeLender = async (userId: string): Promise<boolean> => {
        const response = await api.patch(`/users/${userId}`,
            { "role": "LENDER" },
            { headers: { "Content-Type": "application/vnd.user.v1+json" } })

        if (response.status !== 204) {
            console.error("Error making user lender: ", response)
            return false
        }
        changeUserRole()
        return true
    }
    return { makeLender }
}

export default useChangeRole;
