import "../styles/profileReview.css"
import useUserDetails from "../../hooks/assetInstance/useUserDetails.ts";
import {useEffect} from "react";
import {extractId} from "../../hooks/assetInstance/useUserAssetInstances.ts";
import {Link} from "react-router-dom";
import StarsReviews from "../viewAsset/StarsReviews.tsx";

const ProfileReviewCard = ({review, setCurrentReview}) => {
    const rating = review.rating;

    return (
        <div className="row d-flex justify-content-center" style={{ width: '450px' }} onClick={() => setCurrentReview(review)}>
            <div className="my-2">
                <div className="card" style={{ borderRadius: '30px' }}>
                    <div className="card-body m-3">
                        <div className="row">
                            <div className="col-lg-4 justify-content-center align-items-center">
                                <img src={`${review.reviewerDetails.image}`}
                                     className="rounded-circle img-fluid shadow-1" alt="avatar" width="50"
                                     height="50"/>
                                <p className="fw-bold lead mb-2"><strong>{review.reviewerDetails.userName}</strong></p>
                            </div>
                            <div className="col-lg-8">
                                <StarsReviews rating={rating}/>
                                <p className="fw-light mb-4 ellipsis-text">
                                    {review.review}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProfileReviewCard;
