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
        selfUrl: location.selfUrl || -1,
    });

    // Styles
    const labelStyle = {
        marginBottom: '0.5rem',
        fontWeight: 'bold',
    };

    const formControlStyle = {
        width: '100%',
        padding: '10px',
        margin: '5px',
        border: '1px solid #ced4da',
        borderRadius: '0.25rem',
        background: 'white',
        color: 'black'
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
        fontWeight: 'bold',
        color: 'black'
    };

    const handleChange = (e: any) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e: any) => {
        e.preventDefault();
        handleSave(formData);
        setFormData({name: "", province: "", country: "", locality: "", zipcode: "", id: -1})
    };

    const formGroupStyleWithMargin = {
        ...formControlStyle,
        marginRight: '15px', // Adds space between form groups in the same row
    };

    const buttonStyle = {
        backgroundColor: '#16df7e',
        color: 'white',
        padding: '0.5rem 1rem',
        border: 'none',
        borderRadius: '0.25rem',
        cursor: 'pointer',
        margin: '5px'
    };

    return (
        <div style={modalStyle} tabIndex="-1" role="dialog" aria-labelledby="modalTitle">
            <div style={modalDialogStyle} role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h2 className="modal-title" id="modal-title">
                            <i className="fas fa-map-marked-alt" style={{ marginRight: '10px' }}></i>
                            {t('modalTitle')}
                        </h2>
                        <button type="button" style={closeButtonStyle} onClick={handleClose} aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        <form onSubmit={handleSubmit} className="d-flex flex-column justify-content-center">
                            <div className="form-group" style={{ marginBottom: '15px' }}>
                                <label htmlFor="name-modal" style={labelStyle}>{t('addAssetView.nameLabel')}</label>
                                <input type="text" style={formControlStyle} name="name" id="name-modal" value={formData.name} onChange={handleChange} />
                            </div>
                            <div className="d-flex flex-row" style={{ marginBottom: '15px' }}>
                                <div className="form-group flex-1" style={{ marginRight: '15px' }}>
                                    <label htmlFor="locality-modal" style={labelStyle}>{t('addAssetView.localityLabel')}</label>
                                    <input type="text" style={formGroupStyleWithMargin} name="locality" id="locality-modal" value={formData.locality} onChange={handleChange} />
                                </div>
                                <div className="form-group flex-1">
                                    <label htmlFor="province-modal" style={labelStyle}>{t('addAssetView.provinceLabel')}</label>
                                    <input type="text" style={formControlStyle} name="province" id="province-modal" value={formData.province} onChange={handleChange} />
                                </div>
                            </div>
                            <div className="d-flex flex-row" style={{ marginBottom: '15px' }}>
                                <div className="form-group flex-1" style={{ marginRight: '15px' }}>
                                    <label htmlFor="country-modal" style={labelStyle}>{t('addAssetView.countryLabel')}</label>
                                    <input type="text" style={formGroupStyleWithMargin} name="country" id="country-modal" value={formData.country} onChange={handleChange} />
                                </div>
                                <div className="form-group flex-1">
                                    <label htmlFor="zipcode-modal" style={labelStyle}>{t('addAssetView.zipcodeLabel')}</label>
                                    <input type="text" style={formControlStyle} name="zipcode" id="zipcode-modal" value={formData.zipcode} onChange={handleChange} />
                                </div>
                            </div>
                            <input type="hidden" name="id" value={formData.selfUrl} />
                            <button className="btn btn-primary" type="submit" style={{...buttonStyle, width: '100px', margin: "5px auto"}}>
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
