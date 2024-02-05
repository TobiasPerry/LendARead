import { useTranslation } from 'react-i18next';
import '../styles/LocationsModal.css';
import '../styles/ReviewCardModal.css';

import {isPrivate, isPublic} from "../userAssets/LendedBooksOptions.tsx";
import StarsReviews from "../viewAsset/StarsReviews.tsx";
import {Link} from "react-router-dom";
import {useEffect} from "react";

function ReviewCardModal({ review, showModal, handleCloseModal }) {
    const { t } = useTranslation();

    return (
        <>
            <div className={`modal ${showModal ? 'show' : ''}`} role="dialog" aria-labelledby="modalTitle">
                <div className="modal-dialog modal-content custom-modal">
                    <button onClick={handleCloseModal} className="close-button">
                        <i className="fas fa-window-close fa-lg"></i>
                    </button>
                    <div className="card-body">
                        <div className="review-container">
                            <img src={`${review.reviewerDetails.image}`}
                                 className="reviewer-image"
                                 alt="avatar"/>
                            <div className="review-details" style={{
                                whiteSpace: "normal",
                                wordWrap: "break-word",
                                overflowWrap: "break-word"
                            }}>
                                <Link to={`/user/${review.reviewerId}`} onClick={handleCloseModal} >
                                    <h3 className="reviewer-name"><strong>{review.reviewerDetails.userName}</strong></h3>
                                </Link>
                                <h5 style={{fontWeight: "lighter"}}>{review.type}</h5>
                                <h3 className="reviewer-email"><strong>{review.reviewerDetails.email}</strong></h3>
                                <StarsReviews rating={review.rating}/>
                                <h5 style={{
                                    maxHeight: "300px",
                                    overflowY: "auto",
                                    wordWrap: "break-word",
                                }}>
                                    {review.review}
                                </h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}


export default ReviewCardModal;
