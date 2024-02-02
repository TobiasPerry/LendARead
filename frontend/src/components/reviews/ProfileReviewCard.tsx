import "../styles/profileReview.css"
import useUserDetails from "../../hooks/assetInstance/useUserDetails.ts";
import {useEffect} from "react";
import {extractId} from "../../hooks/assetInstance/useUserAssetInstances.ts";
import {Link} from "react-router-dom";

const ProfileReviewCard = ({review}) => {
    const rating = review.rating;


    const renderStars = (rating: number) => {
        const stars = [];
        for (let i = 0; i < rating; i++) {
            stars.push(<span key={i} className="fa fa-star checked fa-sm"></span>);
        }
        for (let i = rating; i < 5; i++) {
            stars.push(<span key={i} className="fa fa-star unchecked fa-sm"></span>);
        }
        return (
            <div className="user-review-card-rating">
                {stars}
            </div>
        )
    }

    console.log("review", review)
    return (
        <div className="user-review-card">
            <Link to={`/user/${review.reviewerId}`}>
            <div className="user-review-card-img-container">
                <img className="user-review-card-img" src={`${review.reviewerDetails.image}`} alt="reviewer profile picture" />
            </div>
            </Link>
            <div className="user-review-card-content">
                <div className="user-review-card-header">
                    <Link to={`/user/${review.reviewerId}`}>
                        <h5>{review.reviewerDetails.userName}</h5>
                    </Link>
                    {renderStars(rating)}
                </div>
                <p style={{fontSize: "18px"}}>{review.review}</p>
            </div>
        </div>
    )
}

export default ProfileReviewCard;
