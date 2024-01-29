import {useContext} from "react";
import {AuthContext} from "../../contexts/authContext.tsx";
import {useTranslation} from "react-i18next";

const UserProfile = ({ isCurrent }) => {
    const { t } = useTranslation();
    //asumo por ahora que es current
    const {userDetails, userImage} = useContext(AuthContext);


    return (
        <div className="user-profile-card" style={{
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'center',
        }}>
            <div className="profile-image-container">
                <img
                    src={userImage}
                    alt={`${userDetails.userName} profile`}
                    style={{
                        width: '100px',
                        borderRadius: '50%',
                        objectFit: 'cover',
                        border: '3px solid white',
                        boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
                        marginRight: '10px',
                    }}
                />
            </div>
            <div className="d-flex flex-column">
            <h3 className="mt-2">{userDetails.userName}</h3>
            {/* Display the rating and lender/borrower status */}
            <div>
                <span>{t('borrower')} {userDetails.ratingAsBorrower} ★</span>
                <span> - </span>
                <span>{t('lender')} {userDetails.ratingAsLender} ★</span>
            </div> </div>
        </div>
    );
};

export default UserProfile;