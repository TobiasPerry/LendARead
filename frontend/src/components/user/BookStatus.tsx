import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';

const BookStatus = ({asset}) => {
    const { t } = useTranslation();

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
                    <h5 className="card-title">{t('maxDays')}:{asset.maxDays}</h5>
                </div>
            }
        </>
    );
};

export default BookStatus;
