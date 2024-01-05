import { useState } from 'react';
import { useTranslation } from 'react-i18next';

const Location = ({ handleEdit, handleDelete, location, locationIdError, showError }: any) => {
    const { t } = useTranslation();
    const [formData, setFormData] = useState({
        name: location.name || '',
        locality: location.locality || '',
        province: location.province || '',
        country: location.country || '',
        zipcode: location.zipcode || '',
        id: location.id || -1,
    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(formData);
        // Handle form submission here
    };

    const hasError = (field) => {
        return (location.id === locationIdError && showError) || (formData[field] === '');
    };

    const formStyle = {
        maxWidth: '300px',
        minWidth: '300px',
        minHeight: '300px',
        margin: '1rem'
    };

    const inputStyle = {
        width: '100%',
        padding: '0.5rem',
        marginBottom: '1rem',
        border: '1px solid #ced4da',
        borderRadius: '0.25rem',
        // backgroundColor: hasError ? '#f8d7da' : ''
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
        <form onSubmit={handleSubmit} style={formStyle}>
            {/* Name Field */}
            <div className="form-group m-2">
                <label htmlFor={`name${location.id}`} style={labelStyle}>{t('addAssetView.titleLabel')}</label>
                <input
                    type="text"
                    name="name"
                    id={`name${location.id}`}
                    value={formData.name}
                    onChange={handleChange}
                    style={inputStyle}
                    disabled
                />
            </div>
            {/* Locality Field */}

            <div className="d-flex flex-row">
            <div className="form-group m-2">
                <label htmlFor={`locality${location.id}`} style={labelStyle}>{t('addAssetView.localityLabel')}</label>
                <input
                    type="text"
                    name="locality"
                    id={`locality${location.id}`}
                    value={formData.locality}
                    onChange={handleChange}
                    style={inputStyle}
                    disabled
                />
            </div>

            {/* Province Field */}
            <div className="form-group m-2">
                <label htmlFor={`province${location.id}`} style={labelStyle}>{t('addAssetView.provinceLabel')}</label>
                <input
                    type="text"
                    name="province"
                    id={`province${location.id}`}
                    value={formData.province}
                    onChange={handleChange}
                    style={inputStyle}
                    disabled
                />
            </div>

            </div>
            <div className="d-flex flex-row">
            {/* Country Field */}
            <div className="form-group m-2">
                <label htmlFor={`country${location.id}`} style={labelStyle}>{t('addAssetView.countryLabel')}</label>
                <input
                    type="text"
                    name="country"
                    id={`country${location.id}`}
                    value={formData.country}
                    onChange={handleChange}
                    style={inputStyle}
                    disabled
                />
            </div>
            {/* Zipcode Field */}
            <div className="form-group m-2">
                <label htmlFor={`zipcode${location.id}`} style={labelStyle}>{t('addAssetView.zipcodeLabel')}</label>
                <input
                    type="text"
                    name="zipcode"
                    id={`zipcode${location.id}`}
                    value={formData.zipcode}
                    onChange={handleChange}
                    style={inputStyle}
                    disabled
                />
            </div>
            </div>
            <input type="hidden" name="id" value={formData.id} />
            <div className="d-flex justify-content-center">
            <button  type="submit" style={buttonStyle} onClick={handleEdit}>
                {t('edit')}
            </button>
            <button type="submit" style={buttonStyle} onClick={handleDelete}>
                {t('delete')}
            </button></div>
        </form>
        </div>
    );
};

export default Location;
