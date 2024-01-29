import {useContext} from "react";
import {AuthContext} from "../../contexts/authContext.tsx";

const UserProfile = ({ isCurrent }) => {
    const profilePicStyle = {
        width: '100px',
        height: '100px',
        borderRadius: '50%',
        objectFit: 'cover',
        border: '3px solid white',
        boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
    };

    //asumo por ahora que es current
    const {userDetails} = useContext(AuthContext);

    return (
        <div className="user-profile-card">
            <div className="profile-image-container">
                <img
                    src={userDetails.image}
                    alt={`${userDetails.userName} profile`}
                    style={profilePicStyle}
                />
            </div>
            <h3 className="mt-2">{userDetails.name}</h3>
            {/* Display the rating and lender/borrower status */}
            {/* ... */}
        </div>
    );
};

export default UserProfile;