import { useState } from 'react';
import { useTranslation } from 'react-i18next';

const LocationModal = ({ handleSave, location, showModal, handleClose }: any) => {
    const { t } = useTranslation();
    const [formData, setFormData] = useState({
        name: location.name || '',
        locality: location.locality || '',
        province: location.province || '',
        country: location.country || '',
        zipcode: location.zipcode || '',
        id: location.id || -1,
    });

    const handleChange = (e: any) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e: any) => {
        e.preventDefault();
        handleSave(formData);
    };

    const modalStyle = {
        display: showModal ? 'flex' : 'none',
        position: 'fixed',
        top: 0,
        left: 0,
        width: '100%',
        height: '100%',
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        alignItems: 'center',
        justifyContent: 'center',
        zIndex: 1050
    };

    const modalDialogStyle = {
        backgroundColor: 'white',
        borderRadius: '5px',
        padding: '20px',
        maxWidth: '500px',
        width: '100%'
    };

    const closeButtonStyle = {
        cursor: 'pointer',
        border: 'none',
        background: 'none',
        fontSize: '1.5rem',
        fontWeight: 'bold'
    };

    const formControlStyle = {
        width: '100%',
        padding: '10px',
        margin: '5px 0',
        border: '1px solid #ced4da',
        borderRadius: '0.25rem'
    };

    return (
        <div style={modalStyle} tabIndex="-1" role="dialog" aria-labelledby="modalTitle">
            <div style={modalDialogStyle} role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id="modal-title">
                            <i className="fas fa-map-marked-alt" style={{ marginRight: '10px' }}></i>
                            {t('modalTitle')}
                        </h5>
                        <button type="button" style={closeButtonStyle} onClick={handleClose} aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        <form onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label htmlFor="name-modal">{t('addAssetView.nameLabel')}</label>
                                <input
                                    type="text"
                                    style={formControlStyle}
                                    name="name"
                                    id="name-modal"
                                    value={formData.name}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="locality-modal">{t('addAssetView.localityLabel')}</label>
                                <input
                                    type="text"
                                    style={formControlStyle}
                                    name="locality"
                                    id="locality-modal"
                                    value={formData.locality}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="province-modal">{t('addAssetView.provinceLabel')}</label>
                                <input
                                    type="text"
                                    style={formControlStyle}
                                    name="province"
                                    id="province-modal"
                                    value={formData.province}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="country-modal">{t('addAssetView.countryLabel')}</label>
                                <input
                                    type="text"
                                    style={formControlStyle}
                                    name="country"
                                    id="country-modal"
                                    value={formData.country}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="zipcode-modal">{t('addAssetView.zipcodeLabel')}</label>
                                <input
                                    type="text"
                                    style={formControlStyle}
                                    name="zipcode"
                                    id="zipcode-modal"
                                    value={formData.zipcode}
                                    onChange={handleChange}
                                />
                            </div>
                            <input type="hidden" name="id" value={formData.id} />
                            <button type="submit" className="btn btn-primary" onClick={() => handleSave(formData)}>
                                {t('save')}
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LocationModal;
