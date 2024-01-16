import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';

const EditAssetInstanceModal = ({ handleSave, assetInstance, showModal, handleClose }) => {
    const { t } = useTranslation();
    const [imagePreview, setImagePreview] = useState(assetInstance.imageUrl);
    const [formData, setFormData] = useState({
        physicalCondition: '',
        maxDays: '',
        description: '',
        locationId: '',
        isReservable: false,
        status: '',
        image: null
    });
    const [formErrors, setFormErrors] = useState({physicalCondition: '',
        maxDays: '',
        description: '',
        locationId: '',
        isReservable: false,
        status: '',
        image: null
    });

    useEffect(() => {
        setFormData({
            physicalCondition: assetInstance.physicalCondition || '',
            maxDays: assetInstance.maxDays || '',
            description: assetInstance.description || '',
            locationId: assetInstance.locationId || '',
            isReservable: assetInstance.isReservable || false,
            status: assetInstance.status || '',
            image: null
        });
    }, [assetInstance]);

    const validateForm = () => {
        let errors = { physicalCondition: "", maxDays: "", description: "", locationId: "", status: ""};

        if (!["ASNEW", "FINE", "VERYGOOD", "GOOD", "FAIR", "POOR", "EXLIBRARY", "BOOKCLUB", "BINDINGCOPY"].includes(formData.physicalCondition)) {
            errors.physicalCondition = t('physicalConditionValidationError');
        }

        if (parseInt(formData.maxDays) < 1) {
            errors.maxDays = t('maxDaysValidationError');
        }

        if (formData.description.length > 1000) {
            errors.description = t('descriptionValidationError');
        }

        if (parseInt(formData.locationId) < 0) {
            errors.locationId = t('locationIdValidationError');
        }

        if (!["PRIVATE", "PUBLIC"].includes(formData.status)) {
            errors.status = t('statusValidationError');
        }

        return errors;
    };

    const handleChange = (e) => {
        const { name, value, type, checked, files } = e.target;
        setFormData({
            ...formData,
            [name]: type === 'file' ? files[0] : type === 'checkbox' ? checked : value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const errors = validateForm();
        if (Object.keys(errors).length === 0) {
            handleSave(formData);
        } else {
            // @ts-ignore
            setFormErrors(errors);
        }
    };


    const handleImageChange = (e) => {
        if (e.target.files && e.target.files[0]) {
            let img = e.target.files[0];
            setFormData({ ...formData, image: img });
            setImagePreview(URL.createObjectURL(img));
        }
    };

    return (
        <div className={`modal ${showModal ? 'show' : ''}`} role="dialog" aria-labelledby="modalTitle" >
            <div className="modal-dialog modal-content" role="document"  style={{
                backgroundColor: '#f0f5f0',
                borderRadius: '20px',
                width: "1000px"
            }}>
                    <div className="modal-header">
                        {/* Modal Header Content */}
                        <h2 className="modal-title" id="modalTitle">{t('editAsset')}</h2>
                        <button type="button" className="close" onClick={handleClose} aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        <form onSubmit={handleSubmit}>
                            <div className="d-flex flex-row">
                            {/* Image Upload and Preview */}
                            <div className="image-wrapper" style={{padding: "20px 0px"}}>
                                <label htmlFor="image-modal" className={`image-container position-relative ${formErrors.image ? 'image-border-error' : ''}`}>
                                    <img src={imagePreview || 'default-placeholder-image-path'} alt="Book Cover" className="img-fluid" id="bookImage" style={{minWidth: '100px', height: 'auto', maxHeight: '1200px', objectFit: 'cover'}} />
                                    <div className="img-hover-text">
                                        <i className="fas fa-pencil-alt" style={{color: '#D1E9C3'}}></i>
                                        {t('editAssetView.changeImage')}
                                    </div>
                                </label>
                                <input type="file" accept="image/*" name="file" id="image-modal" style={{display:'none'}} onChange={handleImageChange} />
                                {formErrors.image && <div className="error">{formErrors.image}</div>}
                            </div>
                            <div style={{padding: "0 20px", width: "500px"}}>
                            {/* Physical Condition Dropdown */}
                            <div className="form-group">
                                <label htmlFor="physicalCondition-modal">{t('physicalConditionLabel')}</label>
                                <select className="form-control" name="physicalCondition" id="physicalCondition-modal" value={formData.physicalCondition} onChange={handleChange}>
                                    <option value="ASNEW">{t('ASNEW')}</option>
                                    <option value="VERYGOOD">{t('VERYGOOD')}</option>
                                    <option value="GOOD">{t('GOOD')}</option>
                                    <option value="FINE">{t('FINE')}</option>
                                    <option value="FAIR">{t('FAIR')}</option>
                                    <option value="POOR">{t('POOR')}</option>
                                    <option value="EXLIBRARY">{t('EXLIBRARY')}</option>
                                    <option value="BOOKCLUB">{t('BOOKCLUB')}</option>
                                    <option value="BINDINGCOPY">{t('BINDINGCOPY')}</option>
                                </select>
                                {formErrors.physicalCondition && <div className="error">{formErrors.physicalCondition}</div>}
                            </div>

                            {/* Max Days Input */}
                            <div className="form-group">
                                <label htmlFor="maxDays-modal">{t('maxDaysLabel')}</label>
                                <input type="number" className="form-control" name="maxDays" id="maxDays-modal" value={formData.maxDays} onChange={handleChange} />
                                {formErrors.maxDays && <div className="error">{formErrors.maxDays}</div>}
                            </div>

                            {/* Description Textarea */}
                            <div className="form-group">
                                <label htmlFor="description-modal">{t('descriptionLabel')}</label>
                                <textarea className="form-control" name="description" id="description-modal" value={formData.description} onChange={handleChange}></textarea>
                                {formErrors.description && <div className="error">{formErrors.description}</div>}
                            </div>



                            {/* Is Reservable Checkbox */}
                            <div className="form-group">
                                <label htmlFor="isReservable-modal">{t('isReservableLabel')}</label>
                                <input
                                    type="checkbox"
                                    className="form-control"
                                    name="isReservable"
                                    id="isReservable-modal"
                                    checked={formData.isReservable}
                                    onChange={handleChange}
                                />
                            </div>


                                    {/* Status Field */}
                            <div className="form-group">
                                <label htmlFor="status-modal">{t('statusLabel')}</label>
                                <select className="form-control" name="status" id="status-modal" value={formData.status} onChange={handleChange}>
                                    <option value="PRIVATE">{t('private')}</option>
                                    <option value="PUBLIC">{t('public')}</option>
                                </select>
                                {formErrors.status && <div className="error">{formErrors.status}</div>}
                            </div>


                            {/* Submit Button */}
                            <div className="form-group">
                                <button className="submit-button btn btn-primary" type="submit">
                                    {t('save')}
                                </button>
                            </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
        </div>
    );
};

export default EditAssetInstanceModal;
