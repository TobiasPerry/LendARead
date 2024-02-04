import {useTranslation} from "react-i18next";
import {useContext, useEffect, useRef, useState} from "react";
import {AuthContext} from "../../contexts/authContext.tsx";
import {Link} from "react-router-dom";
import useUserDetails from "../../hooks/assetInstance/useUserDetails.ts";
import UserRating from "../userAssets/UserRatingDiv.tsx";

const UserProfileRefactor = ({userDetails }) => {
    const { t } = useTranslation();

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
                    <UserRating userDetails={userDetails} />
                </div>
            </div>
        </div>
    );
};

export default UserProfileRefactor;