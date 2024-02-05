import "../styles/profileReview.css"
import useUserDetails from "../../hooks/assetInstance/useUserDetails.ts";
import {useEffect} from "react";
import {extractId} from "../../hooks/assetInstance/useUserAssetInstances.ts";
import {Link} from "react-router-dom";
import StarsReviews from "../viewAsset/StarsReviews.tsx";

const ProfileReviewCard = ({review, clickedReview}) => {

    return (
        <div className="row d-flex justify-content-center" style={{ width: '450px', cursor: "pointer"}} onClick={() => clickedReview(review)}>
            <div className="my-2">
                <div className="card" style={{ borderRadius: '30px' }}>
                    <div className="card-body m-3">
                        <div className="row">
                            <div className="col-lg-4 justify-content-center align-items-center">
                                <h5 style={{fontWeight: "lighter"}}>{review.type}</h5>
                                <img src={review.reviewerDetails.image}
                                     className="rounded-circle img-fluid shadow-1" alt="avatar"
                                     style={{
                                         objectFit: 'cover', width: '50px', height: '50px'
                                     }}
                                />
                                <p className="fw-bold lead mb-2"><strong>{review.reviewerDetails.userName}</strong></p>
                            </div>
                            <div className="col-lg-8">
                                <StarsReviews rating={review.rating}/>
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
