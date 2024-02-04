import {Link} from "react-router-dom";
import useUserDetails from "../../hooks/assetInstance/useUserDetails.ts";
import {useEffect, useState} from "react";
import {extractId} from "../../hooks/assetInstance/useUserAssetInstances.ts";
import {useTranslation} from "react-i18next";
import UserRating from "./UserRatingDiv.tsx";

const UserLink = ({asset, state}) => {

    const [userId, setUserId] = useState("")

    const {t} = useTranslation()
    useEffect(() => {
        if(asset !== undefined && asset.lending !== undefined) {
            const userId_ = state === "lended" ? extractId(asset.lending.borrowerUrl) : extractId(asset.lending.lenderUrl)
            // @ts-ignore
            setUserId(userId_)
            getUserDetails(userId_).then()
        }
    }, [asset])

    const {userDetails, getUserDetails} = useUserDetails()

    return (
        <Link to={`/user/${userId}`}>
            <div>
            <img src={userDetails.image} className="rounded-circle" width="30" height="30" alt="logo"/>
            <span>{userDetails.userName}</span>
              <UserRating userDetails={userDetails}/>
            </div>
        </Link>
    );
}

export default UserLink;