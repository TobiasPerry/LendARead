import { useEffect, useContext, useRef } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import ProfileView from '../../views/user/Profile.tsx';
import { AuthContext } from '../../contexts/authContext.tsx';

const ProfileWrapper = () => {
    const { id } = useParams();
    const { user, userDetails } = useContext(AuthContext);
    const navigate = useNavigate();
    const isCurrentUser = useRef(false);

    useEffect(() => {
        if (!id || isNaN(Number(id)) ) {
            if (!user) {
                navigate("/login")
            }
            navigate(`/user/${user}`)
        }

        if (user && id && user == Number(id)) {
            isCurrentUser.current = true;
        }
    }, [user, id])

    return (
        <ProfileView profileDetails={userDetails} id={id} isCurrentUser={isCurrentUser.current}/>
    )

}

export default ProfileWrapper;
