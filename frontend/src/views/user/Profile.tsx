import { useTranslation } from "react-i18next";
import {useState} from "react";
import "../../index.css";
import "../styles/profile.css"
import "../styles/addAsset.css"

// Create an User Type
type User = {
    name: string,
    borrowerRating: number,
    lenderRating: number,
    behavior: "LENDER" | "BORROWER"
}

const ProfileView = () => {
    const { t } = useTranslation();
    const [isCurrentUser, setIsCurrentUser] = useState(true); // Change to False and check with AuthContext

    const MOCK_profile_picture_src = '/static/user-placeholder.jpeg'

    const MOCK_user: User = {
        name: "John Doe",
        borrowerRating: 4.5,
        lenderRating: 4.5,
        behavior: "LENDER"
    }

    const renderRating = (behavior: "LENDER" | "BORROWER", rating: number) => {
        if (behavior == "LENDER") {
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
                    <div className="user-info">
                        <h1>{MOCK_user.name}</h1> 
                        <p className="grey-text">
                        {renderRating("BORROWER", MOCK_user.borrowerRating)}
                        {renderRating("LENDER", MOCK_user.lenderRating)}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProfileView;
