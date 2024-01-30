import {Link} from "react-router-dom";
import useUserDetails from "../../hooks/assetInstance/useUserDetails.ts";
import {useEffect} from "react";

const UserLink = ({userId}) => {

    useEffect(() => {
        if(userId)
            getUserDetails(userId).then()
    }, [userId])

    const {userDetails, getUserDetails} = useUserDetails()

    return (
        <Link to="/user">
            <img src={userDetails.image} className="rounded-circle" width="30" height="30" alt="logo"/>
            <span>{userDetails.userName}</span>
        </Link>
    );
}

export default UserLink;