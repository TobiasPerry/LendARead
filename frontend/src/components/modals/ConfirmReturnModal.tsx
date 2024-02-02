import { useTranslation } from 'react-i18next';
import '../styles/LocationsModal.css';


function ConfirmReturnModal({ asset, showModal, handleCloseModal, handleSubmitModal }) {
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
                            <i className="fas fa-check"></i>
                        </div>
                        <h2 className="modal-title" id="modalTitle">
                            {t('userHomeView.confirmReturnTitle')}
                        </h2>

                        <button onClick={handleCloseModal} className="btn btn-secondary">
                            <i className="fas fa-window-close fa-lg"></i>
                        </button>
                    </div>
                    <div className="modal-body">
                        <p>
                            {t('userHomeView.confirmReturnText') }
                        </p>
                    </div>
                    <button type="submit" className="btn btn-green" onClick={handleSubmitModal}>
                        {t('userHomeView.confirmReturnButton')}
                    </button>

                </div>
            </div>
        </>
    );
}

export default ConfirmReturnModal;