import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import Location from "../locations/Location.tsx";
import useLocationAsset from "../../hooks/locations/useLocation.ts";
import UserLink from "./UserLink.tsx";

const BookStatus = ({asset, state}) => {
    const { t } = useTranslation();
    const {getLocation, location} = useLocationAsset()

    useEffect(() => {
            getLocation(asset).then();
            console.log(asset)
    }, [asset])

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
            <h4 className="card-title mb-1"><strong> {t('statusLabel')}:</strong> {t(`${asset.lending.state.toLowerCase()}`)}</h4>
            <h5> <strong>{t("lending_period_from")}: </strong> {asset.lending.lendDate} {t("to")} {asset.lending.devolutionDate}</h5>
            <UserLink asset={asset} state={state}/>
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
