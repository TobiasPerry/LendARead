import  { useState } from 'react';
import '../styles/LocationsModal.css';
import { useTranslation } from 'react-i18next';
import {isPrivate, isPublic} from "../userAssets/LendedBooksOptions.tsx";

function ChangeReservabilityModal({ asset, showModal, handleCloseModal, handleSubmitModal }) {
    const { t } = useTranslation();

    return (
        <>
            <div className={`modal ${showModal ? 'show' : ''}`}  role="dialog" aria-labelledby="modalTitle">
                <div className="modal-dialog modal-content" style={{
                    backgroundColor: '#f0f5f0',
                    borderRadius: '20px',
                }}>
                    <div className="modal-header">
                        <div className="icon-box" style={{backgroundColor: "#16df7e"}}>
                        {asset.isReservable ? (
                                <i className="fas fa-calendar-times"></i>
                        ) : (
                                <i className="fas fa-calendar-alt"></i>
                        )}
                        </div>
                        <h2 className="modal-title" id="modalTitle">
                            {t('userHomeView.changeReservability')}
                        </h2>

                        <button onClick={handleCloseModal} className="btn btn-secondary">
                            <i className="fas fa-window-close fa-lg"></i>
                        </button>
                    </div>
                    <div className="modal-body">
                        <p>
                            {asset.isReservable ? t('userHomeView.changeReservabilityNo'): t('userHomeView.changeReservabilityYes') }
                        </p>
                    </div>
                    <button type="submit" className="btn btn-green" onClick={handleSubmitModal}>
                        {asset.isReservable ? t('userHomeView.makeReservableNo') : t('userHomeView.makeReservableYes')}
                    </button>
                </div>
            </div>
        </>
    );
}

export default ChangeReservabilityModal;
