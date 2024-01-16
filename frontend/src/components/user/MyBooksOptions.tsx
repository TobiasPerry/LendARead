import {useEffect, useState} from 'react';
import { useTranslation } from 'react-i18next';
import {isPublic} from "./LendedBooksOptions.tsx";
import ChangeStatusModal from "../modals/ChangeStatusModal.tsx";
import ChangeReservabilityModal from "../modals/ChangeReservabilityModal.tsx";
import DeleteModal from "../modals/DeleteModal.tsx";
import EditAssetInstanceModal from "../modals/EditAssetInstanceModal.tsx";

function AssetOptionsMenu({ asset, haveActiveLendings, editAssetVisbility, handleDelete, editAssetReservability, editAsset}) {
    const { t } = useTranslation();

    const [showModalVisibility, setShowModalVisibility] = useState(false);
    const [showModalEdit, setShowModalEdit] = useState(false);
    const [showModalDelete, setShowModalDelete] = useState(false);
    const [showModalReservable, setShowModalReservable] = useState(false)

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
    const handleEditAsset = async (editedAset) => {
        setShowModalEdit(true);
        await editAsset(editedAset)
    }

    useEffect(() => {
        console.log(asset)
    }, [asset])

    return (
        <div style={{
            backgroundColor: '#f0f5f0',
            padding: '10px',
            borderRadius: '20px',
            display: "flex",
            alignContent: "center",
        }} className="flex-column">
        <h3>Visibility</h3>
            <div className="d-flex flex-row">
            {!(asset.reservable && haveActiveLendings) && (
                <button id="privatePublicBtn" className="btn btn-green m-1" onClick={() => setShowModalVisibility(true)}>
                    {isPublic(asset.status) ? (
                        <>
                            <i className="fas fa-eye-slash fa-lg"  ></i> {t('userBookDetails.makePrivate')}
                        </>
                    ) : (
                        <>
                            <i className="fas fa-eye fa-lg" ></i> {t('userBookDetails.makePublic')}
                        </>
                    )}
                </button>
            )}

            {!haveActiveLendings && (
                <button id="changeIsReservable" className="btn btn-green m-1" style={{ marginTop: '5px' }} onClick={() => setShowModalReservable(true)}>
                    {asset.isReservable ? (
                        <>
                            <i className="fas fa-calendar-times"></i> {t('userHomeView.makeNotReservable')}
                        </>
                    ) : (
                        <>
                            <i className="fas fa-calendar-alt"></i> {t('userHomeView.makeReservable')}
                        </>
                    )}
                </button>
            )}
        </div>

            <div style={{
                backgroundColor: '#f0f5f0',
                padding: '10px',
                borderRadius: '20px',
                display: "flex",
                alignContent: "center",
                marginTop: "20px"
            }} className="flex-column">
                <h3>Settings</h3>
            <div className="d-flex flex-row">
                <button className="btn btn-green m-1"  style={{ marginTop: '5px', textDecoration: 'none' }} onClick={() => setShowModalEdit(true)}>
                    <i className="fas fa-pencil-alt"></i>
                    {t('edit')}
                </button>
                <button id="deleteBtn" className="btn btn-red m-1" style={{ marginTop: '5px' }} onClick={() => setShowModalDelete(true)}>
                    <i className="fas fa-trash"></i>
                    {t('delete')}
                </button>
            </div>
            </div>
            <ChangeStatusModal handleSubmitModal={handleSubmitVisibilityModal} asset={asset} showModal={showModalVisibility} handleCloseModal={() => setShowModalVisibility(false)} />
            <ChangeReservabilityModal handleSubmitModal={handleSubmitReservabilityModal} asset={asset} showModal={showModalReservable} handleCloseModal={() => setShowModalReservable(false)} />
            <DeleteModal handleSubmitModal={handleDeleteModal} asset={asset} showModal={showModalDelete} handleCloseModal={() => setShowModalDelete(false)} />
            <EditAssetInstanceModal handleSave={handleEditAsset} showModal={showModalEdit} assetInstance={asset} handleClose={() => setShowModalEdit(false)} />
        </div>
    );
}

export default AssetOptionsMenu;
