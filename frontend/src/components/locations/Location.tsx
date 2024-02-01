import { useTranslation } from 'react-i18next';
import LocationModal from "../modals/LocationModal.tsx";

const Location = ({ handleEdit, handleDelete, location }: any) => {
    const { t } = useTranslation();

    const textStyle = {
        padding: '0.5rem',
        marginBottom: '1rem',
        border: '1px solid #ced4da',
        borderRadius: '0.25rem',
        backgroundColor: '#f8f9fa', // light background to mimic input field
        fontSize: '15px',
    };



    const labelStyle = {
        marginBottom: '0.5rem',
        fontWeight: 'bold',
    };


    return (

        <div key={location.id} className="info-container m-3" style={{ width: '350px', height: '350px' }}>
            <div className="form-group m-2">
                <div style={{...labelStyle, fontSize: "30px"}}>{location.name ? location.name : ""}</div>
            </div>
            <div className="d-flex flex-column justify-content-center">
                <div className="d-flex flex-row">
                    <div className="form-group m-2 flex-1">
                        <label style={labelStyle}>{t('addAssetView.localityLabel')}</label>
                        <div style={textStyle}>{location.locality}</div>
                    </div>
                    <div className="form-group m-2 flex-1">
                        <label style={labelStyle}>{t('addAssetView.provinceLabel')}</label>
                        <div style={textStyle}>{location.province}</div>
                    </div>
                </div>
                <div className="d-flex flex-row">
                    <div className="form-group m-2 flex-1">
                        <label style={labelStyle}>{t('addAssetView.countryLabel')}</label>
                        <div style={textStyle}>{location.country}</div>
                    </div>
                    <div className="form-group m-2 flex-1">
                        <label style={labelStyle}>{t('addAssetView.zipcodeLabel')}</label>
                        <div style={textStyle}>{location.zipcode}</div>
                    </div>
                </div>
                <div className="d-flex justify-content-center gap-2">
                    <button  type="submit" className="btn-green btn-mini" style={{width: "10px", padding: "1px"}} onClick={handleEdit}>
                        {t('edit')}
                    </button>
                    <button type="submit" className="btn-red btn-mini" onClick={handleDelete}>
                        {t('delete')}
                    </button>
                </div>
            </div>
        </div>
    );
}

export default Location;
