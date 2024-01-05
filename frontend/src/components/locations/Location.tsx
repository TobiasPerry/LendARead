import { useState } from 'react';
import { useTranslation } from 'react-i18next';

const Location = ({ handleEdit, handleDelete, location, locationIdError, showError }: any) => {
    const { t } = useTranslation();

    const textStyle = {
        padding: '0.5rem',
        marginBottom: '1rem',
        border: '1px solid #ced4da',
        borderRadius: '0.25rem',
        backgroundColor: '#f8f9fa', // light background to mimic input field
    };

    const labelStyle = {
        marginBottom: '0.5rem',
        fontWeight: 'bold'
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

        <div key={location.id} className="info-container m-3" style={{ maxWidth: '600px', minWidth: '300px', minHeight: '300px' }}>
            <div className="form-group m-2">
                <label style={labelStyle}>{t('addAssetView.titleLabel')}</label>
                <div style={textStyle}>{location.name}</div>
            </div>
            <div className="d-flex flex-row">
                <div className="form-group m-2">
                    <label style={labelStyle}>{t('addAssetView.localityLabel')}</label>
                    <div style={textStyle}>{location.locality}</div>
                </div>
                <div className="form-group m-2">
                    <label style={labelStyle}>{t('addAssetView.provinceLabel')}</label>
                    <div style={textStyle}>{location.province}</div>
                </div>
            </div>
            <div className="d-flex flex-row">
                <div className="form-group m-2">
                    <label style={labelStyle}>{t('addAssetView.countryLabel')}</label>
                    <div style={textStyle}>{location.country}</div>
                </div>
                <div className="form-group m-2">
                    <label style={labelStyle}>{t('addAssetView.zipcodeLabel')}</label>
                    <div style={textStyle}>{location.zipcode}</div>
                </div>
            </div>
            <div className="d-flex justify-content-center">
                <button  type="submit" style={buttonStyle} onClick={handleEdit}>
                    {t('edit')}
                </button>
                <button type="submit" style={buttonStyle} onClick={handleDelete}>
                    {t('delete')}
                </button></div>
        </div>
    );
};

export default Location;
