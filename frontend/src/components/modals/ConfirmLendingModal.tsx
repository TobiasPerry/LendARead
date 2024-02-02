import { useTranslation } from 'react-i18next';
import '../styles/LocationsModal.css';

import {isPrivate, isPublic} from "../userAssets/LendedBooksOptions.tsx";

function ConfirmLendingModal({ asset, showModal, handleCloseModal, handleSubmitModal }) {
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
                            <i className="fas fa-thumbs-up"></i>
                        </div>
                        <h2 className="modal-title" id="modalTitle">
                            {t('userHomeView.verifyLendingTitle')}
                        </h2>

                        <button onClick={handleCloseModal} className="btn btn-secondary">
                            <i className="fas fa-window-close fa-lg"></i>
                        </button>
                    </div>
                    <div className="modal-body">
                        <p>
                            {t('userHomeView.verifyLendingText') }
                        </p>
                    </div>
                    <button type="submit" className="btn btn-green" onClick={handleSubmitModal}>
                        {t('userHomeView.verifyLendingButton')}
                    </button>

                </div>
            </div>
        </>
    );
}

export default ConfirmLendingModal;
