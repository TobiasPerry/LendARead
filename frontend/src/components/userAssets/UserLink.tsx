import {Link} from "react-router-dom";
import useUserDetails from "../../hooks/assetInstance/useUserDetails.ts";
import {useEffect, useState} from "react";

const UserLink = ({lending, state}) => {

    useEffect(() => {
        if(lending) {
            const userId = state === "lended" ? lending.borrowerUrl : lending.lenderUrl
            getUserDetails(userId).then()
        }
    }, [lending])

    const {userDetails, getUserDetails} = useUserDetails()

    return (
        <Link to="/user">
            <img src={userDetails.image} className="rounded-circle" width="30" height="30" alt="logo"/>
            <span>{userDetails.userName}</span>
        </Link>
    );
}

export default UserLink;