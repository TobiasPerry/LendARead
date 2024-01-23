import { useTranslation } from 'react-i18next';
import useUserBorrowedBooksOptions from "../../hooks/assetInstance/useUserBorrowedBooksOptions.ts";

const BorrowedBookOptions = ({asset, fetchUserAssetInstance}) => {
    const { t } = useTranslation();
    const {cancelBorrowedBook} = useUserBorrowedBooksOptions(asset, fetchUserAssetInstance);

    return (
        <div className="loan-options card p-5">
            <h5 className="card-title">{t('options')}:</h5>
            <p className="card-text">{t('loanRequestSent')}</p>
            <button className="btn btn-danger" onClick={cancelBorrowedBook}>{t('cancelLending')}</button>
        </div>
    );
};

export default BorrowedBookOptions;
