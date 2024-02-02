import { useTranslation } from 'react-i18next';
import '../styles/LocationsModal.css';

import {isPrivate, isPublic} from "../userAssets/LendedBooksOptions.tsx";
import StarsReviews from "../viewAsset/StarsReviews.tsx";

function ReviewCardModal({ review, showModal, handleCloseModal}) {
    const { t } = useTranslation();

    return (
        <>
            <div className={`modal ${showModal ? 'show' : ''}`}  role="dialog" aria-labelledby="modalTitle">
                <div className="modal-dialog modal-content">
                    <div className="row d-flex justify-content-center" style={{ width: '450px' }}>
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
            </div>
            </div>
        </>
    );
}

export default ReviewCardModal;