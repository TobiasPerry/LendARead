import {useContext, useEffect, useState} from "react";
import { useTranslation } from "react-i18next";
import ProfileReviewCard from "../../components/reviews/ProfileReviewCard";
import "../../index.css";
import "../styles/profile.css"
import "../styles/addAsset.css"
import {AuthContext} from "../../contexts/authContext.tsx";
import useReviews from "../../hooks/reviews/useReviews.ts";

const ProfileView = ({isCurrentUser, id, profileDetails}) => {

    const { t } = useTranslation();
    const {userImage} = useContext(AuthContext)
    const [selectedTab, setSelectedTab] = useState("lender_reviews")

    const {lenderReviews, borrowerReviews, fetchReviews} = useReviews()


    useEffect(() => {
        fetchReviews().then()
    }, [id])

    const renderRating = (behavior: "LENDER" | "BORROWER", rating: number) => {
        if (behavior == "LENDER") {
            if (profileDetails.role != "LENDER") {
                return <></>
            }
            return (
                <>
                    {t('userProfile.lender')}
                    <span className="user-role-stars">
                        {rating <= 0 ? (
                            "-.- ") : (
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
                        {rating <= 0 ? (
                            "-.- ") : (
                            rating
                        )}
                        ★</span>
                </>
            )
        }
    }

    const renderTabContent = (reviews: any) => {
        const reviews_with_usernames: any = []
        reviews.forEach((review: any)  => {
            reviews_with_usernames.push({
                ...review,
                reviewer: "John Doe"
            })
        })
        return (
            <div></div>
            // <ProfileReviewCard review={MOCK_reviews} />
        )
    }



    return (
        <div className="main-class">
            <div className="user-container">
                <div className="info-container w-100 mt-10" id="user-info">
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
                        <h1>{profileDetails.userName}</h1>
                        <p className="grey-text">
                            {renderRating("BORROWER", profileDetails.ratingAsBorrower)}
                            {renderRating("LENDER", profileDetails.ratingAsLender)}
                        </p>
                    </div>
                    <hr />
                    <div className="tabs-container">
                        <ul className="nav nav-tabs" id="user-tab" role="tablist">
                                <li className="nav-item" role="presentation">
                                    <button
                                        className="nav-link black-text"
                                        type="button"
                                        role="tab"
                                        onClick={(_) => setSelectedTab("lender_reviews")}
                                        aria-selected={selectedTab === "lender_reviews"}>Lender Reviews</button>
                                </li>
                            <li className="nav-item" role="presentation">
                                <button
                                    className="nav-link black-text"
                                    type="button"
                                    role="tab"
                                    onClick={(_) => setSelectedTab("borrower_reviews")}
                                    aria-selected={selectedTab === "borrower_reviews"}>Borrower Reviews</button>
                            </li>
                        </ul>
                    </div>
                    <div className="tab-content" >
                        {selectedTab === "lender_reviews" &&
                            (lenderReviews.length > 0
                                ? lenderReviews.map((review) => <ProfileReviewCard review={review} />)
                                : <p>No reviews yet</p>
                        )}
                        {selectedTab === "borrower_reviews" &&
                            (borrowerReviews.length > 0
                                ? borrowerReviews.map((review) => <ProfileReviewCard review={review} />)
                                : <p>No reviews yet</p>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProfileView;
