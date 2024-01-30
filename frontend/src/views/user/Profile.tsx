import { useTranslation } from "react-i18next";
import "../../index.css";
import "../styles/profile.css"
import "../styles/addAsset.css"

const ProfileView = (props: any) => {
    const { t } = useTranslation();
    const isCurrentUser = props.isCurrentUser;
    const id = props.id;
    const profileDetails = props.profileDetails;

    const MOCK_profile_picture_src = '/static/user-placeholder.jpeg'

    const renderRating = (behavior: "LENDER" | "BORROWER", rating: number) => {
        if (behavior == "LENDER") {
            if (profileDetails.role != "LENDER") {
                return <></>
            }
            return (
            <>
            {t('userProfile.lender')}
            <span className="user-role-stars">
                { rating <= 0 ? (
                    "-.-" ) : (
                    rating
                )}
                ★ </span>
            </>
            )
        } else {
            return (
            <>
            {t('userProfile.borrower')}
            <span className="user-role-stars">
                { rating <= 0 ? (
                    "-.-" ) : (
                    rating
                )}
                ★ </span>
            </>
            )
        }
    }


    return (
        <div className="main-class">
            <div className="user-container">
                <div className="info-container w-100 mt-10" id="user-info">
                    <div className="position-relative">
                        <div className="user-profile-cell">
                            <img className="user-profile-picture" src={MOCK_profile_picture_src} alt="user profile picture" />
                            { isCurrentUser ? (
                                <div className="user-change-picture-container" id="change-profile-pic-btn">
                                    <i className="fas fa-solid fa-camera"></i>
                                </div>
                                ) : null
                            }
                        </div>
                    </div>
                    <div className="user-info-profile">
                        <h1>{profileDetails.userName}</h1> 
                        <p className="grey-text">
                        {renderRating("BORROWER", profileDetails.ratingAsBorrower)}
                        {renderRating("LENDER", profileDetails.ratingAsLender)}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProfileView;
