import {Link} from "react-router-dom";
import useUserDetails from "../../hooks/assetInstance/useUserDetails.ts";
import {useEffect, useState} from "react";
import {extractId} from "../../hooks/assetInstance/useUserAssetInstances.ts";

const UserLink = ({asset, state}) => {

    const [userId, setUserId] = useState("")

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
            <img src={userDetails.image} className="rounded-circle" width="30" height="30" alt="logo"/>
            <span>{userDetails.userName}</span>
        </Link>
    );
}

export default UserLink;