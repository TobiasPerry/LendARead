import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import Location from "../locations/Location.tsx";
import useLocationAsset from "../../hooks/locations/useLocation.ts";

const BookStatus = ({asset}) => {
    const { t } = useTranslation();
    const {getLocation, location} = useLocationAsset()
    useEffect(() => { getLocation(asset).then()}, [asset])

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
            <h3 className="card-title">{t('statusLabel')}:{asset.lending.state}</h3>
        </div> }
            {/* User asset instance*/}
            {(asset !== undefined && asset.lending === undefined) &&
                <div style={styles}>
                    <h3>{t("statusLabel")}</h3>
                    <h5 className="card-title">{t('maxDays')}:{asset.maxDays}</h5>
                    <h5>{t("location")} : {location.country}, {location.province}, {location.locality}, {location.zipcode}</h5>
                </div>
            }
        </>
    );
};

export default BookStatus;
