import {useContext} from "react";
import {AuthContext} from "../../contexts/authContext.tsx";
import {useTranslation} from "react-i18next";

const UserProfile = ({ isCurrentUser }) => {
    const { t } = useTranslation();
    //asumo por ahora que es current
    const {userDetails, userImage} = useContext(AuthContext);

    return (
        <div>
       <div className="position-relative">
                        <div className="user-profile-cell">
                            <img className="user-profile-picture" src={userImage} alt="user profile picture" />
                            {isCurrentUser
                                ?
                                <div className="user-change-picture-container" id="change-profile-pic-btn">
                                    <i className="fas fa-solid fa-camera"></i>
                                </div>
                                :
                                <>
                                </>
                            }
                        </div>
                    </div>
                    <div className="user-info-profile">
                        <h1>{userDetails.userName}</h1>
                        <p className="grey-text">
                            <>
                                { userDetails.role === "LENDER" &&
                                    <>
                                        {t('userProfile.lender')}
                                        <span className="user-role-stars">
                                        {userDetails.ratingAsBorrower <= 0 ? (
                                            "-.- ") : (
                                            userDetails.ratingAsBorrower
                                        )}
                                            ★ </span>
                                    </>
                                }
                            </>
                            <>
                                {t('userProfile.borrower')}
                                <span className="user-role-stars">
                    {userDetails.ratingAsLender <= 0 ? (
                        "-.- ") : (
                        userDetails.ratingAsLender
                    )}
                                    ★</span>
                            </>
                        </p>
                    </div>
    </div>
    );
};

export default UserProfile;