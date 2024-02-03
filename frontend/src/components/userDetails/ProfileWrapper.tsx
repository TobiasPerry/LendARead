import { useEffect, useContext, useRef } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import ProfileView from '../../views/user/Profile.tsx';
import { AuthContext } from '../../contexts/authContext.tsx';


const ProfileWrapper = () => {
    const { id } = useParams();

    useEffect(() => {
        // if (!id || isNaN(Number(id)) ) {
        //     if (!user || user === -1 || user === undefined) {
        //         redirectLogin.current = true;
        //         navigate('/login')
        //     } else {
        //         navigate(`/user/${user}`)
        //     }
        // }
        //
        // if (user && id && user == Number(id)) {
        //     isCurrentUser.current = true;
        // }
    }, [id])

    return (
        <ProfileView />
    )

}

export default ProfileWrapper;
