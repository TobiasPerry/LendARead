import {useTranslation} from "react-i18next";
import {useContext, useEffect, useRef, useState} from "react";
import {AuthContext} from "../../contexts/authContext.tsx";
import {Link} from "react-router-dom";
import useUserDetails from "../../hooks/assetInstance/useUserDetails.ts";

const UserProfileRefactor = ({user }) => {
    const { t } = useTranslation();
    const {userDetails, getUserDetails} = useUserDetails()

    useEffect(() => {
        getUserDetails(user).then()
    }, [user])

    return (
        <div>
            <div className="position-relative">
                <div className="user-profile-cell">
                    <img className="user-profile-picture" src={userDetails.image} alt="user profile" />
                </div>
            </div>
            <div className="d-flex flex-row">
                <div className="user-info-profile">
                    <h1>{userDetails.userName}</h1>
                    <p className="grey-text">
                        <>
                            { userDetails.role === "LENDER" &&
                                <>
                                    {t('userProfile.lender')}
                                    <span className="user-role-stars">
                                        {userDetails.ratingAsLender <= 0 ? (
                                            "-.- ") : (
                                            Math.round(userDetails.ratingAsLender * 10) / 10
                                        )}
                                        ★ </span>
                                </>
                            }
                        </>
                        <>
                            {t('userProfile.borrower')}
                            <span className="user-role-stars">
                            {userDetails.ratingAsBorrower <= 0 ? (
                                "-.- ") : (
                                Math.round(userDetails.ratingAsBorrower * 10) / 10
                            )}
                                ★</span>
                        </>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default UserProfileRefactor;