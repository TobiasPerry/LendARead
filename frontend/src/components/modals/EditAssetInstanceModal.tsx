import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';

const EditAssetInstanceModal = ({ handleSave, assetInstance, showModal, handleClose }) => {
    const { t } = useTranslation();
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

    return (
        <div className={`modal ${showModal ? 'show' : ''}`} role="dialog" aria-labelledby="modalTitle">
            <div className="modal-body" style={{
                backgroundColor: '#f0f5f0',
                borderRadius: '20px',
            }}>
                <form onSubmit={handleSubmit}>

                    <div className="form-group">
                        <label htmlFor="locationId-modal">{t('locationIdLabel')}</label>
                        <input type="number" className="form-control" name="locationId" id="locationId-modal" value={formData.locationId} onChange={handleChange} />
                        {formErrors.locationId && <div className="error">{formErrors.locationId}</div>}
                    </div>
                    {/* Is Reservable Checkbox */}
                    <div className="form-group">
                        <label htmlFor="isReservable-modal">{t('isReservableLabel')}</label>
                        <input type="checkbox" className="form-control" name="isReservable" id="isReservable-modal" checked={formData.isReservable} onChange={handleChange} />
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

                    {/* Image Upload Field */}
                    <div className="form-group">
                        <label htmlFor="image-modal">{t('imageLabel')}</label>
                        <input type="file" className="form-control" name="image" id="image-modal" onChange={handleChange} />
                        {formErrors.image && <div className="error">{formErrors.image}</div>}
                    </div>

                    {/* Submit Button */}
                    <button className="submit-button" type="submit">
                        {t('save')}
                    </button>
                </form>
            </div>
        </div>
    );
};

export default EditAssetInstanceModal;
