import { useTranslation } from 'react-i18next';

const BorrowedBookOptions = () => {
    const { t } = useTranslation();

    // Replace with actual cancel logic
    const cancelLending = () => {
        console.log('Lending cancelled');
    };

    return (
        <div className="loan-options card p-5">
            <h5 className="card-title">{t('options')}:</h5>
            <p className="card-text">{t('loanRequestSent')}</p>
            <button className="btn btn-danger" onClick={cancelLending}>{t('cancelLending')}</button>
        </div>
    );
};

export default BorrowedBookOptions;
