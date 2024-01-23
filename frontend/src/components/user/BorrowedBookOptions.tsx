import { useTranslation } from 'react-i18next';
import useUserBorrowedBooksOptions from "../../hooks/assetInstance/useUserBorrowedBooksOptions.ts";
import {useState} from "react";
import CancelModal from "../modals/CancelModal.tsx";

const BorrowedBookOptions = ({asset, fetchUserAssetInstance}) => {
    const { t } = useTranslation();
    const {cancelBorrowedBook} = useUserBorrowedBooksOptions(asset, fetchUserAssetInstance);
    const [cancelModal, setCancelModal] = useState(false)

    return (
        <div className="loan-options card p-5">
            <h5 className="card-title">{t('options')}:</h5>
            <p className="card-text">{t('loanRequestSent')}</p>
            <button className="btn btn-danger" onClick={() => setCancelModal(true)}>{t('cancelLending')}</button>
            <CancelModal asset={asset} handleSubmitModal={cancelBorrowedBook} showModal={cancelModal} handleCloseModal={() => setCancelModal(false)} />
        </div>
    );
};

export default BorrowedBookOptions;
