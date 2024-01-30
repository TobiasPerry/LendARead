import { useTranslation } from 'react-i18next';
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
                        <h2 className="modal-title" id="modalTitle">
                            {t('userHomeView.verifyLendingTitle')}
                        </h2>

                        <button onClick={handleCloseModal} className="btn btn-secondary">
                            <i className="fas fa-tick fa-lg"></i>
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
