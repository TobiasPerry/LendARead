import React from 'react';
import { useTranslation } from 'react-i18next';

function AssetOptionsMenu({ asset, haveActiveLendings }) {
    const { t } = useTranslation();

    return (
        <div className="options-menu">
            {!(asset.getIsReservable && haveActiveLendings) && (
                <button id="privatePublicBtn" className="btn btn-green" type="submit">
                    {asset.assetState.isPublic ? (
                        <>
                            <i className="fas fa-eye-slash fa-lg"></i> {t('userBookDetails.makePrivate')}
                        </>
                    ) : (
                        <>
                            <i className="fas fa-eye fa-lg"></i> {t('userBookDetails.makePublic')}
                        </>
                    )}
                </button>
            )}
            <a className="btn btn-green" href={`/editAsset/${asset.id}`} style={{ marginTop: '5px', textDecoration: 'none' }}>
                <i className="fas fa-pencil-alt"></i>
                {t('edit')}
            </a>
            {!haveActiveLendings && (
                <button id="changeIsReservable" className="btn btn-green" style={{ marginTop: '5px' }} type="submit">
                    {asset.isReservable ? (
                        <>
                            <i className="fas fa-calendar-times"></i> {t('not_reservable')}
                        </>
                    ) : (
                        <>
                            <i className="fas fa-calendar-alt"></i> {t('reservable')}
                        </>
                    )}
                </button>
            )}
            <button id="deleteBtn" className="btn btn-red" style={{ marginTop: '5px' }} type="submit">
                <i className="fas fa-trash"></i>
                {t('delete')}
            </button>
            {/* Include modal components here */}
            {/* <DeleteBookModal asset={asset} />
      <ChangeStatusModal asset={asset} />
      <ChangeReservabilityModal asset={asset} /> */}
        </div>
    );
}

export default AssetOptionsMenu;
