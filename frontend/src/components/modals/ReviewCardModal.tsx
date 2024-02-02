import { useTranslation } from 'react-i18next';
import '../styles/LocationsModal.css';

import {isPrivate, isPublic} from "../userAssets/LendedBooksOptions.tsx";
import StarsReviews from "../viewAsset/StarsReviews.tsx";
import {Link} from "react-router-dom";

function ReviewCardModal({ review, showModal, handleCloseModal}) {
    const { t } = useTranslation();

    return (
        <>
            <div className={`modal ${showModal ? 'show' : ''}`}  role="dialog" aria-labelledby="modalTitle">
                <div className="modal-dialog modal-content" style={{borderRadius: "30px"}}>
                    <button onClick={handleCloseModal} style={{
                        position: "absolute",
                        top: "15px",
                        right: "15px"
                    }}>
                        <i className="fas fa-window-close fa-lg"></i>
                    </button>
                    <div className="my-2">
                        <div className="card-body m-3">
                            <div className="d-flex flex-row">
                                <img src={`${review.reviewerDetails.image}`}
                                     className="rounded-circle img-fluid shadow-1"
                                     alt="avatar"
                                     style={{width: "100px", height: "100px", marginRight: "20px"}}/>
                                <div>
                                    <Link to={`/user/${review.reviewerId}`} >
                                    <h3 className="fw-bold lead mb-2"><strong>{review.reviewerDetails.userName}</strong></h3>
                                    </Link>
                                    <h3 className="fw-bold lead mb-2"><strong>{review.reviewerDetails.email}</strong></h3>
                                    <StarsReviews rating={review.rating}/>
                                    <h5 className="fw-light mb-4">
                                        {review.review}
                                    </h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ReviewCardModal;