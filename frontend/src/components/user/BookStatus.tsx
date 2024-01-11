import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';

const BookStatus = () => {
    const { t } = useTranslation();
    const [loanStatus, setLoanStatus] = useState(null);

    useEffect(() => {
        // Simulate fetching loan status
        const fetchLoanStatus = async () => {
            // Replace with actual fetch call
            const status = {
                dueDate: '2024-01-24',
                username: 'martinippo'
            };
            setLoanStatus(status);
        };

        fetchLoanStatus();
    }, []);

    if (!loanStatus) {
        return <div>Loading...</div>;
    }

    return (
        <div className="loan-status card p-5">
            <h5 className="card-title">{t('status')}:</h5>
            <p className="card-text">{t('dueDate')}: {loanStatus.dueDate}</p>
            <p className="card-text">{loanStatus.username}</p>
        </div>
    );
};

export default BookStatus;
