import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import '../styles/LocationsModal.css'; // Import the external CSS file

const NewLenderModal = ({ handleSave, location, showModal, handleClose }) => {
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
        <div className={`modal ${showModal ? 'show' : ''}`} role="dialog" aria-labelledby="modalTitle" >
            <div className="modal-dialog" style={{borderRadius: "25px"}} role="document" onClick={(e) => e.stopPropagation()}>
                <div className="modal-content">
                <div>
                    <div className="modal-header-centered mb-2">
                        <div className="icon-box">
                            <i className="fas fa-book-reader"/>
                        </div>
                        <h2 className="modal-title mt-3" id="modal-title">
                            {t('addAsset.newLender.title')}
                        </h2>
                        <small className="d-flex justify-content-center">{t('addAsset.newLender.subtitle')}</small>
                    </div>
                    <div className="modal-body">
                        <form onSubmit={handleSubmit}>
                            {/* Name Field */}
                            <div className="form-group">
                                <label htmlFor="name-modal">{t('addAssetView.nameLabel')}</label>
                                <input type="text" className="form-control" name="name" id="name-modal"
                                       value={formData.name} onChange={handleChange}
                                       placeholder={t("addAssetView.nameLabel")}/>
                                {formErrors.name && <div className="error">{formErrors.name}</div>}
                            </div>

                            <div className="d-flex flex-1 flex-row gap-3">
                            {/* Locality Field */}
                            <div className="form-group">
                                <label htmlFor="locality-modal">{t('addAssetView.localityLabel')}</label>
                                <input type="text" className="form-control" name="locality" id="locality-modal"
                                       value={formData.locality} onChange={handleChange}
                                       placeholder={t("addAssetView.localityLabel")}/>
                                {formErrors.locality && <div className="error">{formErrors.locality}</div>}
                            </div>

                            {/* Province Field */}
                            <div className="form-group">
                                <label htmlFor="province-modal">{t('addAssetView.provinceLabel')}</label>
                                <input type="text" className="form-control" name="province" id="province-modal"
                                       value={formData.province} onChange={handleChange}
                                       placeholder={t("addAssetView.provinceLabel")}/>
                                {formErrors.province && <div className="error">{formErrors.province}</div>}
                            </div>
                            </div>

                            <div className="d-flex flex-1 flex-row gap-3">

                            {/* Country Field */}
                            <div className="form-group">
                                <label htmlFor="country-modal">{t('addAssetView.countryLabel')}</label>
                                <input type="text" className="form-control" name="country" id="country-modal"
                                       value={formData.country} onChange={handleChange}
                                placeholder={t("addAssetView.countryLabel")}/>
                                {formErrors.country && <div className="error">{formErrors.country}</div>}
                            </div>

                            {/* Zipcode Field */}
                            <div className="form-group">
                                <label htmlFor="zipcode-modal">{t('addAssetView.zipcodeLabel')}</label>
                                <input type="text" className="form-control" name="zipcode" id="zipcode-modal"
                                       value={formData.zipcode} onChange={handleChange}
                                placeholder={t("addAssetView.zipcodeLabel")}/>
                                {formErrors.zipcode && <div className="error">{formErrors.zipcode}</div>}
                            </div>
                            </div>

                            {/* Hidden Field for selfUrl */}
                            <input type="hidden" name="selfUrl" value={formData.selfUrl}/>


                            {/* Submit Button */}
                            <button className="submit-button" type="submit">
                                {t('save')}
                            </button>


                        </form>

                    </div>
                </div>
                    <button style={{position: "absolute", top: "0px", right: "0px", background: "none", color: "black", cursor: "pointer"}} onClick={handleClose}>
                        <i className="fas fa-times fa-lg"></i>
                    </button>
                </div>

            </div>
        </div>
    );

};

export default NewLenderModal;
