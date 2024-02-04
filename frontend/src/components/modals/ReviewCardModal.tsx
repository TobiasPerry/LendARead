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
                <div className="modal-dialog modal-content" style={{borderRadius: "30px", minWidth: "300px", maxWidth: "50%"}}>
                    <button onClick={handleCloseModal} style={{
                        position: "absolute",
                        top: "15px",
                        right: "15px"
                    }}>
                        <i className="fas fa-window-close fa-lg"></i>
                    </button>
                        <div className="card-body p-3 ps-0" style={{width: "500px"}} >
                            <div className="d-flex flex-row">
                                <img src={`${review.reviewerDetails.image}`}
                                     className="rounded-circle img-fluid shadow-1"
                                     alt="avatar"
                                     style={{width: "200px", height: "200px", marginRight: "30px", marginLeft: "0px", borderRadius: "50%", objectFit: "cover", margin: "auto"
                                     }}/>
                                <div>
                                    <Link to={`/user/${review.reviewerId}`} onClick={handleCloseModal} >
                                    <h3 className="fw-bold lead mb-2 text-clickable"><strong>{review.reviewerDetails.userName}</strong></h3>
                                    </Link>
                                    <h3 className="fw-bold lead mb-2"><strong>{review.reviewerDetails.email}</strong></h3>
                                    <StarsReviews rating={review.rating}/>
                                    <h5 className="fw-light mb-4" style={{
                                        maxHeight: "200px",
                                        overflowY: "auto"
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