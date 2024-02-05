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
import UserProfileExternal from "../../components/userDetails/UserProfileExternal.tsx";
import useUserDetails from "../../hooks/assetInstance/useUserDetails.ts";
import NotFound from "../NotFound.tsx";
import LoadingAnimation from "../../components/LoadingAnimation.tsx";
import Snackbar from "../../components/SnackBar.tsx";

const ProfileView = () => {

    const {id} = useParams()
    const { t } = useTranslation();
    const {userImage,  user} = useContext(AuthContext)
    const [selectedTab, setSelectedTab] = useState("lender_reviews")
    const [isCurrentUser, setIsCurrentUser] = useState(false)
    const {getUserDetails, notFound, userDetails} = useUserDetails()
    const [loading, setLoading] = useState(true)

    const {
        lenderReviews,
        borrowerReviews,
        currentPageLenderReviews,
        currentPageBorrowerReviews,
        totalPagesLenderReviews,
        totalPagesBorrowerReviews,
        changePageLenderReviews,
        changePageBorrowerReviews,
        fetchReviews,
        error
    } = useReviews()

    useEffect(() => {
        if(user !== undefined && id !== undefined && id === user){
            setIsCurrentUser(true)
            setLoading(false)
        }else{
            setIsCurrentUser(false)
            getUserDetails(id).then(()=>{
                setLoading(false)
            })
        }
        if(id !== undefined)
            fetchReviews(id).then();

    }, [id, user])


    return (
        <>
            {
                loading ? (
                    <LoadingAnimation/>
                ) : (
                    <>
                        {
                            notFound ? (
                                <NotFound/>
                            ) : (
                                <div className="main-class">
                                    <div className="user-container">
                                        <div className="info-container w-100 mt-10" id="user-info">
                                            {isCurrentUser && <UserProfile/>}
                                            {!isCurrentUser && <UserProfileExternal userDetails={userDetails}/>}
                                            <hr/>
                                            <div className="tabs-container">
                                                <UserProfileTabs selectedTab={selectedTab} setSelectedTab={setSelectedTab}/>
                                            </div>
                                            <div className="tab-content">
                                                {selectedTab === "lender_reviews" &&
                                                    <UserReviews
                                                        reviews={lenderReviews}
                                                        changePage={(page: number) => changePageLenderReviews(page, id)}
                                                        currentPage={currentPageLenderReviews}
                                                        totalPages={totalPagesLenderReviews}/>
                                                }
                                                {selectedTab === "borrower_reviews" &&
                                                    <UserReviews
                                                        reviews={borrowerReviews}
                                                        changePage={(page: number) => changePageBorrowerReviews(page, id)}
                                                        currentPage={currentPageBorrowerReviews}
                                                        totalPages={totalPagesBorrowerReviews}/>
                                                }
                                            </div>
                                        </div>
                                    </div>
                                    {error.status && <Snackbar message={error.text} error={true} /> }
                                </div>
                            )
                        }
                    </>
                )
            }
        </>
    )
}

export default ProfileView;
