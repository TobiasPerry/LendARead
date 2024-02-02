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

const ProfileView = ({isCurrentUser, id}) => {

    const { t } = useTranslation();
    const {userImage, userDetails, user} = useContext(AuthContext)
    const [selectedTab, setSelectedTab] = useState("lender_reviews")
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
        fetchReviews().then()
    }, [id])


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
                              user={user}
                              userName={userDetails.userName}
                              changePage={changePageLenderReviews}
                              currentPage={currentPageLenderReviews}
                              totalPages={totalPagesLenderReviews}/>
                        }
                        {selectedTab === "borrower_reviews" &&
                            <UserReviews
                                reviews={borrowerReviews}
                                user={user}
                                userName={userDetails.userName}
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
