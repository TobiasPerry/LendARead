import  { useState } from 'react';
import '../styles/LocationsModal.css';
import { useTranslation } from 'react-i18next';
import {isPrivate, isPublic} from "../userAssets/LendedBooksOptions.tsx";

function ChangeStatusModal({ asset, showModal, handleCloseModal, handleSubmitModal }) {
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
                                <i className="fas fa-eye-slash fa-lg"></i>
                            </div>
                            <h2 className="modal-title" id="modalTitle">
                                {t('userHomeView.changeVisibilityTitle')}
                            </h2>

                            <button onClick={handleCloseModal} className="btn btn-secondary">
                                <i className="fas fa-window-close fa-lg"></i>
                            </button>
                        </div>
                        <div className="modal-body">
                            <p>
                                {!isPrivate(asset.status) ? t('userHomeView.changeVisibilityTextPrivate'): t('userHomeView.changeVisibilityTextPublic') }
                            </p>
                        </div>
                            <button type="submit" className="btn btn-green" onClick={handleSubmitModal}>
                                {isPrivate(asset.status) ? t('userHomeView.makePublic'): t('userHomeView.makePrivate') }
                            </button>

                </div>
            </div>
        </>
    );
}

export default ChangeStatusModal;
