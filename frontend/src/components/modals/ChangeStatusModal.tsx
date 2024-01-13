import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import {isPrivate, isPublic} from "../user/LendedBooksOptions.tsx";

function ChangeStatusModal({ asset }) {
    const { t } = useTranslation();
    const [showModal, setShowModal] = useState(false);

    const handleShowModal = () => setShowModal(true);
    const handleCloseModal = () => setShowModal(false);

    return (
        <>
            <button id="privatePublicBtn" className="btn btn-green" onClick={handleShowModal}>
                {isPublic(asset.state) ? t('userHomeView.makePrivate') : t('userHomeView.makePublic')}
            </button>

            <div className={`modal ${showModal ? 'show' : ''}`}  role="dialog" aria-labelledby="modalTitle">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h2 className="modal-title" id="modalTitle">
                                {t('userHomeView.changeVisibilityTitle')}
                            </h2>
                        </div>
                        <div className="modal-body">
                            <p>
                                {isPrivate(asset.state) ? t('userHomeView.changeVisibilityTextPublic') : t('userHomeView.changeVisibilityTextPrivate')}
                            </p>
                        </div>
                        <div className="modal-footer">
                            <form action={`/changeStatus/${asset.id}`} method="post">
                                <button type="submit" className="btn btn-primary">
                                    {isPrivate(asset.state) ? t('userHomeView.changeVisibityPublic.button') : t('userHomeView.changeVisibityPrivate.button')}
                                </button>
                            </form>
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
