import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import Location from "../locations/Location.tsx";
import useLocationAsset from "../../hooks/locations/useLocation.ts";

const BookStatus = ({asset}) => {
    const { t } = useTranslation();
    const {getLocation, location} = useLocationAsset()
    useEffect(() => { getLocation(asset).then()}, [asset])

    return (
        <>
        {/*Lended/Borrowed asset instance*/}
        {!(asset === undefined || asset.lending === undefined) &&
        <div className="loan-status card p-5">
            <h5 className="card-title">{t('status')}:{asset.lending.state}</h5>
        </div> }
            {/* User asset instance*/}
            {(asset !== undefined && asset.lending === undefined) &&
                <div className="loan-status card p-5">
                    <h3>{t("status")}</h3>
                    <h5 className="card-title">{t('maxDays')}:{asset.maxDays}</h5>
                    <h5>{t("location")} : {location.country}, {location.province}, {location.locality}, {location.zipcode}</h5>
                </div>
            }
        </>
    );
};

export default BookStatus;
