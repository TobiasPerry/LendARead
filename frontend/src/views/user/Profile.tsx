import { useState } from "react";
import { useTranslation } from "react-i18next";
import ProfileReviewCard from "../../components/reviews/ProfileReviewCard";
import "../../index.css";
import "../styles/profile.css"
import "../styles/addAsset.css"

const ProfileView = (props: any) => {
    const { t } = useTranslation();
    const isCurrentUser = props.isCurrentUser;
    const id = props.id;
    const profileDetails = props.profileDetails;
    const [selectedTab, setSelectedTab] = useState(0);
    const [lenderReviews, setLenderReviews] = useState([]);
    const [borrowerReviews, setBorrowerReviews] = useState([]);

    const tabs = [
        "Lender Reviews",
        "Borrower Reviews"
    ]

    const [userNames, setUserNames] = useState([])


    const MOCK_profile_picture_src = '/static/user-placeholder.jpeg'
    const MOCK_reviews = [
        {
            reviwer: 'http://example.com/user/1',
            lending: "http://example.com/lending/1",
            rating: 3,
            review: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s."
        },
        {
            reviwer: 'http://example.com/user/2',
            lending: "http://example.com/lending/1",
            rating: 2,
            review: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s."
        },
        {
            reviwer: 'http://example.com/user/1',
            lending: "http://example.com/lending/1",
            rating: 1,
            review: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s."
        },
        {
            reviwer: 'http://example.com/user/1',
            lending: "http://example.com/lending/1",
            rating: 4,
            review: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s."
        },
        {
            reviwer: 'http://example.com/user/2',
            lending: "http://example.com/lending/1",
            rating: 5,
            review: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s."
        }
    ]

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

    const renderTabTitle = (title: string, index: number) => {
        return (
            <li key={index} className="nav-item" role="presentation">
                <button className="nav-link black-text" type="button" role="tab" onClick={(_) => setSelectedTab(index)} aria-selected={index == 0}>{title}</button>
            </li>
        )
    }

    const renderTabNav = () => {
        return (
            <ul className="nav nav-tabs" id="user-tab" role="tablist">
                {tabs.map((tab, index) => {
                    return renderTabTitle(tab, index)
                })}
            </ul>
        )
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
            <ProfileReviewCard review={mock_review} />
        )
    }



    return (
        <div className="main-class">
            <div className="user-container">
                <div className="info-container w-100 mt-10" id="user-info">
                    <div className="position-relative">
                        <div className="user-profile-cell">
                            <img className="user-profile-picture" src={MOCK_profile_picture_src} alt="user profile picture" />
                            {isCurrentUser ? (
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
                    <hr />
                    <div className="tabs-container">
                        {renderTabNav()}
                    </div>
                    <div className="tab-content" >
                        {
                            renderTabContent(MOCK_reviews)
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProfileView;
