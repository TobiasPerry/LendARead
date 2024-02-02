import {useContext, useEffect, useState} from "react";
import { useTranslation } from "react-i18next";
import "../../index.css";
import "../styles/profile.css"
import "../styles/addAsset.css"
import {AuthContext} from "../../contexts/authContext.tsx";
import useReviews, {ReviewApi} from "../../hooks/reviews/useReviews.ts";
import UserProfile from "../../components/userDetails/UserProfile.tsx";
import UserProfileTabs from "../../components/userDetails/UserProfileTabs.tsx";
import UserReviews from "../../components/reviews/UserReviews.tsx";
import {useParams} from "react-router-dom";

const ProfileView = () => {

    const {id} = useParams()
    const { t } = useTranslation();
    const {userImage, userDetails, user} = useContext(AuthContext)
    const [selectedTab, setSelectedTab] = useState("lender_reviews")
    const [isCurrentUser, setIsCurrentUser] = useState(false)

    const {
        lenderReviews,
        borrowerReviews,
        currentPageLenderReviews,
        currentPageBorrowerReviews,
        totalPagesLenderReviews,
        totalPagesBorrowerReviews,
        changePageLenderReviews,
        changePageBorrowerReviews,
        fetchReviews
    } = useReviews()

    useEffect(() => {
        setIsCurrentUser(user !== undefined && id !== undefined && id === user)
        fetchReviews(user).then()
    }, [id, user])


    return (
        <div className="main-class">
            <div className="user-container">
                <div className="info-container w-100 mt-10" id="user-info">
                   <UserProfile isCurrentUser={isCurrentUser}/>
                    <hr />
                    <div className="tabs-container">
                    <UserProfileTabs selectedTab={selectedTab} setSelectedTab={setSelectedTab}/>
                    </div>
                    <div className="tab-content" >
                        {selectedTab === "lender_reviews" &&
                          <UserReviews
                              reviews={lenderReviews}
                              changePage={changePageLenderReviews}
                              currentPage={currentPageLenderReviews}
                              totalPages={totalPagesLenderReviews}/>
                        }
                        {selectedTab === "borrower_reviews" &&
                            <UserReviews
                                reviews={borrowerReviews}
                                changePage={changePageBorrowerReviews}
                                currentPage={currentPageBorrowerReviews}
                                totalPages={totalPagesBorrowerReviews}/>
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProfileView;
