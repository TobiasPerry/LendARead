import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import '../styles/LocationsModal.css'; // Import the external CSS file

const LocationModal = ({ handleSave, location, showModal, handleClose }) => {
    const { t } = useTranslation();
    const [formData, setFormData] = useState({
        name: '',
        locality: '',
        province: '',
        country: '',
        zipcode: '',
        selfUrl: '',
    });
    const [formErrors, setFormErrors] = useState({
        name: '',
        locality: '',
        province: '',
        country: '',
        zipcode: '',
    });

    useEffect(() => {
        setFormData({
            name: location.name || '',
            locality: location.locality || '',
            province: location.province || '',
            country: location.country || '',
            zipcode: location.zipcode || '',
            selfUrl: location.selfUrl || '',
        });
    }, [location]);

    const validateForm = () => {
        let errors = {
            name: '',
            locality: '',
            province: '',
            country: '',
            zipcode: '',
        };

        // Validation for zipcode (alphanumeric, 1-100 characters)
        if (!formData.zipcode.match(/^[a-zA-Z0-9]+$/) || formData.zipcode.length < 1 || formData.zipcode.length > 100) {
            errors.zipcode = t('zipcodeValidationError');
        } else {
            errors.zipcode = ''
        }

        // Validation for locality (1-100 characters)
        if (formData.locality.length < 1 || formData.locality.length > 100) {
            errors.locality = t('localityValidationError');
        } else {
            errors.locality = ''
        }

        // Validation for province (4-100 characters)
        if (formData.province.length < 4 || formData.province.length > 100) {
            errors.province = t('provinceValidationError');
        } else {
            errors.province = ''
        }

        // Validation for country (4-100 characters)
        if (formData.country.length < 4 || formData.country.length > 100) {
            errors.country = t('countryValidationError');
        } else {
            errors.country = ''
        }

        // Validation for name (1-100 characters)
        if (formData.name.length < 1 || formData.name.length > 100) {
            errors.name = t('nameValidationError');
        } else {
            errors.name = ''
        }

        return errors;
    };


    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const errors = validateForm();
        if (errors.country === "" && errors.zipcode === "" && errors.province === "" && errors.locality === "" && errors.name === "") {
            handleSave(formData);
            setFormData({ name: "", locality: "", province: "", country: "", zipcode: "", selfUrl: "" });
        } else {
            setFormErrors(errors);
        }
    };
    return (
        <div className={`modal ${showModal ? 'show' : ''}`} role="dialog" aria-labelledby="modalTitle">
            <div className="modal-dialog" role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h2 className="modal-title" id="modal-title">
                            {t('modalTitle')}
                        </h2>
                        <button type="button" className="close-button" onClick={handleClose} aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        <form onSubmit={handleSubmit}>
                            {/* Name Field */}
                            <div className="form-group">
                                <label htmlFor="name-modal">{t('addAssetView.nameLabel')}</label>
                                <input type="text" className="form-control" name="name" id="name-modal" value={formData.name} onChange={handleChange} />
                                {formErrors.name && <div className="error">{formErrors.name}</div>}
                            </div>

                            {/* Locality Field */}
                            <div className="form-group">
                                <label htmlFor="locality-modal">{t('addAssetView.localityLabel')}</label>
                                <input type="text" className="form-control" name="locality" id="locality-modal" value={formData.locality} onChange={handleChange} />
                                {formErrors.locality && <div className="error">{formErrors.locality}</div>}
                            </div>

                            {/* Province Field */}
                            <div className="form-group">
                                <label htmlFor="province-modal">{t('addAssetView.provinceLabel')}</label>
                                <input type="text" className="form-control" name="province" id="province-modal" value={formData.province} onChange={handleChange} />
                                {formErrors.province && <div className="error">{formErrors.province}</div>}
                            </div>

                            {/* Country Field */}
                            <div className="form-group">
                                <label htmlFor="country-modal">{t('addAssetView.countryLabel')}</label>
                                <input type="text" className="form-control" name="country" id="country-modal" value={formData.country} onChange={handleChange} />
                                {formErrors.country && <div className="error">{formErrors.country}</div>}
                            </div>

                            {/* Zipcode Field */}
                            <div className="form-group">
                                <label htmlFor="zipcode-modal">{t('addAssetView.zipcodeLabel')}</label>
                                <input type="text" className="form-control" name="zipcode" id="zipcode-modal" value={formData.zipcode} onChange={handleChange} />
                                {formErrors.zipcode && <div className="error">{formErrors.zipcode}</div>}
                            </div>

                            {/* Hidden Field for selfUrl */}
                            <input type="hidden" name="selfUrl" value={formData.selfUrl} />

                            {/* Submit Button */}
                            <button className="submit-button" type="submit">
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
