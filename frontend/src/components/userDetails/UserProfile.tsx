import {useContext} from "react";
import {AuthContext} from "../../contexts/authContext.tsx";
import {useTranslation} from "react-i18next";

const UserProfile = ({ isCurrent }) => {
    const { t } = useTranslation();
    //asumo por ahora que es current
    const {userDetails} = useContext(AuthContext);


    return (
        <div className="user-profile-card" style={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center', // Align children center
            justifyContent: 'center', // Center children vertically
        }}>
            <div className="profile-image-container">
                <img
                    src={userDetails.image}
                    alt={`${userDetails.userName} profile`}
                    style={{
                        width: '100px',
                        height: '100px',
                        borderRadius: '50%',
                        objectFit: 'cover',
                        border: '3px solid white',
                        boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
                    }}
                />
            </div>
            <h3 className="mt-2">{userDetails.userName}</h3>
            {/* Display the rating and lender/borrower status */}
            <div>
                <span>{t('borrower')} {userDetails.ratingAsBorrower} ★</span>
                <span> - </span>
                <span>{t('lender')} {userDetails.ratingAsLender} ★</span>
            </div>
        </div>
    );
};

export default UserProfile;