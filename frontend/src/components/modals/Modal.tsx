import '../styles/LocationsModal.css'
function Modal({
                   showModal = true,
                   handleCloseModal = () => {},
                   handleSubmitModal = () => {},
                   errorType = false,
                    title = "",
                    subtitle = "",
                    btnText = "",
                    icon= ""
}) {

    return (
        <>
            <div className={`modal ${showModal ? 'show' : ''}`}  role="dialog" aria-labelledby="modalTitle">
                <div className="modal-dialog modal-content" style={{
                    backgroundColor: '#f0f5f0',
                    borderRadius: '20px',
                }}>
                    <div className="modal-header">
                        <div className="icon-box" style={errorType ? {backgroundColor: "#D45235"} : {backgroundColor: "#16df7e"}}>
                            <i className={icon}></i>
                        </div>
                        <h2 className="modal-title" id="modalTitle">
                            {title}
                        </h2>

                        <button onClick={handleCloseModal} className="btn btn-secondary" name="close">
                            <i className="fas fa-window-close fa-lg"></i>
                        </button>
                    </div>
                    <div className="modal-body">
                        <p>
                            {subtitle}
                        </p>
                    </div>
                    <button type="submit" className={`btn ${errorType ? 'btn-red' : 'btn-green'}`} onClick={handleSubmitModal}>
                        {btnText}
                    </button>

                </div>
            </div>
        </>
    );
}

export default Modal;
