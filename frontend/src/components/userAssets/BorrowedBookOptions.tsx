import { useTranslation } from 'react-i18next';
import useUserBorrowedBooksOptions from "../../hooks/assetInstance/useUserBorrowedBooksOptions.ts";
import React, {useState} from "react";
import CancelModal from "../modals/CancelModal.tsx";
import {isActive, isCanceled, isDelivered, isFinished, isRejected} from "./LendedBooksOptions.tsx";
import {Link} from "react-router-dom";
import "../styles/MyBooksOptions.css";
import Snackbar from "../SnackBar.tsx";

const BorrowedBookOptions = ({asset, fetchUserAssetInstance}) => {
    const { t } = useTranslation();
    const {cancelBorrowedBook, canReview, error} = useUserBorrowedBooksOptions(asset, fetchUserAssetInstance);
    const [cancelModal, setCancelModal] = useState(false)

    if(asset === undefined || asset.lending === undefined) return (<></>);
    return (
        <div style={{
            backgroundColor: '#f0f5f0',
            padding: '25px',
            borderRadius: '20px',
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
        }} className="flex-column">
            {isActive(asset.lending.state) &&
                <div>
                    <h3 style={{ alignSelf: 'flex-start', width: '100%' }}>{t('borrowed_book_actions')}</h3>
                    <p className="card-text">{t('loanRequestSent')}</p>
                    <button className="btn btn-red-outline" onClick={() => setCancelModal(true)}>{t('userHomeView.cancelButton')}</button>
                    <CancelModal asset={asset} handleSubmitModal={cancelBorrowedBook} showModal={cancelModal} handleCloseModal={() => setCancelModal(false)} />
                </div>
            }
            {isCanceled(asset.lending.state) &&
                <div>
                    <div> {t("canceled_text")} </div>
                </div>
            }
            {isDelivered(asset.lending.state) &&
            <div>
                <div> {t("delivered_text_borrower")} </div>
            </div>
            }
            {isRejected(asset.lending.state) &&
                <div>
                    <div> {t("rejected_text")} </div>
                </div>
            }
            {isFinished(asset.lending.state) && canReview &&
                <div>
                    <h6 style={{color: '#7d7c7c', fontWeight: 'bold', textAlign: 'center', width: "60%", marginTop: "10px"}}>
                        {t('finished_borrower')}
                    </h6>
                    <Link id="returnAssetBtn" className="btn btn-green" style={{marginTop: '10px', alignSelf: 'center'}} to={`/review/borrower/${asset.lendingid}`}>
                        {t('userHomeView.review')}
                    </Link>
                </div>
            }
            {isFinished(asset.lending.state) && !canReview &&
                <div>
                    <div> {t("finished_text")} </div>
                </div>
            }
            {error.state && <Snackbar message={error.text} />}
        </div>
    );
};

export default BorrowedBookOptions;
