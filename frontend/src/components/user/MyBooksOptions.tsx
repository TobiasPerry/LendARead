import React from 'react';
import { useTranslation } from 'react-i18next';
import {isPublic} from "./LendedBooksOptions.tsx";
import ChangeStatusModal from "../modals/ChangeStatusModal.tsx";

function AssetOptionsMenu({ asset, haveActiveLendings }) {
    const { t } = useTranslation();

    const handleSetPublic = async () => {

    }

    const handleSetPrivate = async () => {

    }

    return (
        <div style={{
            backgroundColor: '#f0f5f0',
            padding: '10px',
            borderRadius: '20px',
            width: '50%',
            display: "flex",
            alignContent: "center",
        }} className="flex-column">
        <h3>Lended Book actions</h3>
        <div className="options-menu m-auto">
            {!(asset.reservable && haveActiveLendings) && (
                <button id="privatePublicBtn" className="btn btn-green" >
                    {isPublic(asset.status) ? (
                        <>
                            <i className="fas fa-eye-slash fa-lg" onClick={handleSetPrivate} ></i> {t('userBookDetails.makePrivate')}
                        </>
                    ) : (
                        <>
                            <i className="fas fa-eye fa-lg" onClick={handleSetPublic} ></i> {t('userBookDetails.makePublic')}
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
                    {asset.reservable ? (
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
      <ChangeReservabilityModal asset={asset} /> */}
            <ChangeStatusModal asset={asset} />
        </div>
        </div>
    );
}

export default AssetOptionsMenu;
