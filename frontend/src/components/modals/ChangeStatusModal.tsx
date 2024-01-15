import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import {isPrivate, isPublic} from "../user/LendedBooksOptions.tsx";

function ChangeStatusModal({ asset, showModal, handleCloseModal, handleSubmitModal }) {
    const { t } = useTranslation();

    return (
        <>
            <div className={`modal ${showModal ? 'show' : ''}`}  role="dialog" aria-labelledby="modalTitle">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <div className="icon-box">
                                <i className="fas fa-eye-slash fa-lg"></i>
                            </div>
                            <h2 className="modal-title" id="modalTitle">
                                {t('userHomeView.changeVisibilityTitle')}
                            </h2>
                        </div>
                        <div className="modal-body">
                            <p>
                                {!isPrivate(asset.state) ? t('userHomeView.changeVisibilityTextPublic') : t('userHomeView.changeVisibilityTextPrivate')}
                            </p>
                        </div>
                        <div className="modal-footer">
                            <button type="submit" className="btn btn-primary" onClick={handleSubmitModal}>
                                {isPrivate(asset.state) ? t('userHomeView.makePrivate') : t('userHomeView.makePublic')}
                            </button>
                            <button onClick={handleCloseModal} className="btn btn-secondary">
                                Close
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ChangeStatusModal;
