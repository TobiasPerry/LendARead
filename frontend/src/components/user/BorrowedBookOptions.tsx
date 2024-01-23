import { useTranslation } from 'react-i18next';
import useUserBorrowedBooksOptions from "../../hooks/assetInstance/useUserBorrowedBooksOptions.ts";
import {useState} from "react";
import CancelModal from "../modals/CancelModal.tsx";
import {isCancel} from "axios";
import {isCanceled, isFinished} from "./LendedBooksOptions.tsx";

const BorrowedBookOptions = ({asset, fetchUserAssetInstance}) => {
    const { t } = useTranslation();
    const {cancelBorrowedBook} = useUserBorrowedBooksOptions(asset, fetchUserAssetInstance);
    const [cancelModal, setCancelModal] = useState(false)

    return (
        <div style={{
            backgroundColor: '#f0f5f0',
            padding: '25px',
            borderRadius: '20px',
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
        }} className="flex-column">
            {!isCanceled(asset.lending.state) &&
                <div>
                    <h3 style={{ alignSelf: 'flex-start', width: '100%' }}>{t('borrowed_book_actions')}</h3>
                    <p className="card-text">{t('loanRequestSent')}</p>
                    <button className="btn btn-red-outline" onClick={() => setCancelModal(true)}>{t('userHomeView.cancelButton')}</button>
                    <CancelModal asset={asset} handleSubmitModal={cancelBorrowedBook} showModal={cancelModal} handleCloseModal={() => setCancelModal(false)} />
                </div>
            }
        </div>
    );
};

export default BorrowedBookOptions;
