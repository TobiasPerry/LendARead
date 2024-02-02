import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import '../styles/LocationsModal.css';

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


    const handleClose_ = () => {
        setFormData({ name: "", locality: "", province: "", country: "", zipcode: "", selfUrl: "" });
        setFormErrors({ name: "", locality: "", province: "", country: "", zipcode: "" });
        handleClose()
    }
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
            <div className="modal-dialog modal-content" role="document" style={{
                borderRadius: "40px",
                width: "400px",
                minHeight: "400px",
                backgroundColor: "#f8f9fa",
            }}>
                <div className="modal-header">
                    <button type="button" className="close-button" onClick={handleClose_} aria-label="Close" >
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div className="modal-body">
                    <form onSubmit={handleSubmit}>
                        {/* Name Field */}
                        <div className="d-flex flex-row">
                            <div className="form-group">
                                <input style={{fontSize: "25px",  backgroundColor: "#f8f9fa", fontWeight: "bold", flex: 1}}
                                       type="text" className="form-control" name="name" id="name-modal" value={formData.name} onChange={handleChange} placeholder={t("name")}/>
                                {formErrors.name && <div className="error">{formErrors.name}</div>}
                                </div>
                            </div>

                            <div className="d-flex flex-row gap-3">
                            <div className="form-group">
                                <label htmlFor="locality-modal">{t('addAssetView.localityLabel')}</label>
                                <input type="text" className="form-control" name="locality" id="locality-modal" value={formData.locality} onChange={handleChange} placeholder={t("addAssetView.localityLabel")} />
                                {formErrors.locality && <div className="error">{formErrors.locality}</div>}
                            </div>

                            <div className="form-group">
                                <label htmlFor="province-modal">{t('addAssetView.provinceLabel')}</label>
                                <input type="text" className="form-control" name="province" id="province-modal" value={formData.province} onChange={handleChange} placeholder={t("addAssetView.provinceLabel")}/>
                                {formErrors.province && <div className="error">{formErrors.province}</div>}
                            </div>
                            </div>

                            <div className="d-flex flex-row gap-3">
                            <div className="form-group">
                                <label htmlFor="country-modal">{t('addAssetView.countryLabel')}</label>
                                <input type="text" className="form-control" name="country" id="country-modal" value={formData.country} onChange={handleChange} placeholder={t("addAssetView.countryLabel")}/>
                                {formErrors.country && <div className="error">{formErrors.country}</div>}
                            </div>

                            <div className="form-group">
                                <label htmlFor="zipcode-modal">{t('addAssetView.zipcodeLabel')}</label>
                                <input type="text" className="form-control" name="zipcode" id="zipcode-modal" value={formData.zipcode} onChange={handleChange} placeholder={t("addAssetView.zipcodeLabel")}/>
                                {formErrors.zipcode && <div className="error">{formErrors.zipcode}</div>}
                            </div>
                            </div>

                            <input type="hidden" name="selfUrl" value={formData.selfUrl} />

                            <button className="submit-button" type="submit">
                                {t('save')}
                            </button>
                        </form>
                    </div>
            </div>
        </div>
    );

};

export default LocationModal;
