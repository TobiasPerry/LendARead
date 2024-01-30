import {Link} from "react-router-dom";
import useUserDetails from "../../hooks/assetInstance/useUserDetails.ts";
import {useEffect, useState} from "react";
import {extractId} from "../../hooks/assetInstance/useUserAssetInstances.ts";

const UserLink = ({asset, state}) => {

    useEffect(() => {
        if(asset !== undefined && asset.lending !== undefined) {
            const userId = state === "lended" ? asset.lending.borrowerUrl : asset.lending.lenderUrl
            getUserDetails(extractId(userId)).then()
        }
    }, [asset])

    const {userDetails, getUserDetails} = useUserDetails()

    return (
        <Link to="/user">
            <img src={userDetails.image} className="rounded-circle" width="30" height="30" alt="logo"/>
            <span>{userDetails.userName}</span>
        </Link>
    );
}

export default UserLink;