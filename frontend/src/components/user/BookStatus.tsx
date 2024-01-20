import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import Location from "../locations/Location.tsx";
import useLocationAsset from "../../hooks/locations/useLocation.ts";

const BookStatus = ({asset}) => {
    const { t } = useTranslation();
    const {getLocation, location} = useLocationAsset()
    useEffect(() => { getLocation(asset).then(); console.log(asset)}, [asset])

    const styles = {
        backgroundColor: '#f0f5f0',
        borderRadius: '20px',
        padding: "20px"
    }
    return (
        <>
        {/*Lended/Borrowed asset instance*/}
        {!(asset === undefined || asset.lending === undefined) &&
        <div style={styles}>
            <h3 className="card-title">{t('statusLabel')}: {asset.lending.state}</h3>
        </div> }
            {/* User asset instance*/}
            {(asset !== undefined && asset.lending === undefined) &&
                <div style={styles}>
                    <h3>{t("details")}</h3>
                    <h5 ><strong>{t('maxDays')}</strong>: {asset.maxDays}</h5>
                    <h5><strong>{t("location")}</strong>: {location.country}, {location.province}, {location.locality}, {location.zipcode}</h5>
                    <h5><strong>{t("visibility")}</strong>: {t(`${asset.status}`)}</h5>
                </div>
            }
        </>
    );
};

export default BookStatus;
