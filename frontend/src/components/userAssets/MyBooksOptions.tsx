import {useEffect, useState} from 'react';
import { useTranslation } from 'react-i18next';
import {isPublic} from "./LendedBooksOptions.tsx";
import ChangeStatusModal from "../modals/ChangeStatusModal.tsx";
import ChangeReservabilityModal from "../modals/ChangeReservabilityModal.tsx";
import DeleteModal from "../modals/DeleteModal.tsx";
import EditAssetInstanceModal from "../modals/EditAssetInstanceModal.tsx";
import userUserAssetInstanceOptions from "../../hooks/assetInstance/userUserAssetInstanceOptions.ts";
import "../styles/MyBooksOptions.css";
import Snackbar from "../SnackBar.tsx";
function AssetOptionsMenu({ asset, haveActiveLendings, handleDelete, fetchUserAssetDetails, hasLendingsNotFinished}) {
    const { t } = useTranslation();

    const [showModalVisibility, setShowModalVisibility] = useState(false);
    const [showModalEdit, setShowModalEdit] = useState(false);
    const [showModalDelete, setShowModalDelete] = useState(false);
    const [showModalReservable, setShowModalReservable] = useState(false)
    const {editAssetVisbility, editAssetReservability, editAsset, error} = userUserAssetInstanceOptions(fetchUserAssetDetails)

    // const {editAssetVisbility} = userUserAssetInstanceOptions()
    const handleSubmitVisibilityModal = async () => {
        setShowModalVisibility(false);
        await editAssetVisbility(asset)
    }

    const handleSubmitReservabilityModal = async () => {
        setShowModalReservable(false);
        await editAssetReservability(asset);
    }
    const handleDeleteModal = async () => {
        setShowModalDelete(true);
        await handleDelete(asset)
    }
    const handleEditAsset = async (editedAsset, originalAsset) => {
        setShowModalEdit(true);
        await editAsset(editedAsset, originalAsset)
    }
    return (
        <div style={{
            backgroundColor: '#f0f5f0',
            padding: '10px',
            borderRadius: '20px',
            display: "flex",
            alignContent: "center",
        }} className="flex-column">
            <h3>{t("shortcut")}</h3>
            <div className="d-flex flex-row">
                {!(asset.reservable && haveActiveLendings) && (
                    <>
                        {isPublic(asset.status) ? (
                            <>
                                <button id="privatePublicBtn" className={`btn btn-green m-1`}
                                        onClick={() => setShowModalVisibility(true)}>
                                    <i className="fas fa-eye-slash fa-lg"></i> {t('userBookDetails.makePrivate')}
                                </button>
                            </>
                        ) : (
                            <>
                                <button id="privatePublicBtn"
                                        className={`btn btn-green m-1 ${(!asset.isReservable && hasLendingsNotFinished) ? 'disabled' : ''}`}
                                        onClick={() => setShowModalVisibility(true)}>
                                    <i className="fas fa-eye fa-lg"></i> {t('userBookDetails.makePublic')}
                                </button>
                            </>
                        )}
                    </>
                )}

                {!haveActiveLendings && (
                    <>
                        {asset.isReservable ? (
                            <>
                            <button id="changeIsReservable" className={`btn btn-green m-1 ${(asset.isReservable && hasLendingsNotFinished) ? 'disabled' : ''}`} style={{marginTop: '5px'}}
                                    onClick={() => setShowModalReservable(true)}>
                                <i className="fas fa-calendar-times"></i> {t('userHomeView.makeNotReservable')}
                            </button>
                            </>
                        ) : (
                            <>
                            <button id="changeIsReservable" className="btn btn-green m-1" style={{marginTop: '5px'}}
                                    onClick={() => setShowModalReservable(true)}>
                                <i className="fas fa-calendar-alt"></i> {t('userHomeView.makeReservable')}
                            </button>
                            </>
                        )}
                    </>
                )}
            </div>
            <br/>
            {(!asset.isReservable && hasLendingsNotFinished) ?  <p className="error">{t('userBookDetails.cannotMakeItPublic')}</p> : <></>}
            {(asset.isReservable && hasLendingsNotFinished) ?  <p className="error">{t('userHomeView.cannotMakeItNotReservable')}</p> : <></>}

            <div style={{
                backgroundColor: '#f0f5f0',
                borderRadius: '20px',
                display: "flex",
                alignContent: "center",
                marginTop: "20px"
            }} className="flex-column">
                <h3>{t("settings")}</h3>
                <div className="d-flex flex-row">
                    <button className="btn btn-green m-1" style={{marginTop: '5px', textDecoration: 'none'}}
                            onClick={() => setShowModalEdit(true)}>
                        <i className="fas fa-pencil-alt"></i>
                        {t('edit')}
                    </button>
                    <button id="deleteBtn" className="btn btn-red m-1" style={{marginTop: '5px'}}
                            onClick={() => setShowModalDelete(true)}>
                        <i className="fas fa-trash"></i>
                        {t('delete')}
                    </button>
                </div>
            </div>
             {error.status && <Snackbar message={error.text} /> }
            <ChangeStatusModal handleSubmitModal={handleSubmitVisibilityModal} asset={asset}
                               showModal={showModalVisibility} handleCloseModal={() => setShowModalVisibility(false)}/>
            <ChangeReservabilityModal handleSubmitModal={handleSubmitReservabilityModal} asset={asset}
                                      showModal={showModalReservable}
                                      handleCloseModal={() => setShowModalReservable(false)}/>
            <DeleteModal handleSubmitModal={handleDeleteModal} asset={asset} showModal={showModalDelete}
                         handleCloseModal={() => setShowModalDelete(false)}/>
            <EditAssetInstanceModal handleSave={handleEditAsset} showModal={showModalEdit} assetInstance={asset}
                                    handleClose={() => setShowModalEdit(false)}/>
        </div>
    );
}

export default AssetOptionsMenu;
