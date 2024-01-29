import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';

// Assume these are imported from their respective component files
import UserProfile from '../../components/userDetails/UserProfile';
import ReviewList from '../../components/userDetails/ReviewList';
import useUserProfileReviews from "../../hooks/users/useUserProfileReviews.ts";
const UserProfileView = () => {
    const { t } = useTranslation();
    const [activeTab, setActiveTab] = useState('borrower');
    const { borrowerReviews, lenderReviews } = useUserProfileReviews();

    const isCurrent = true;

    // Adjust tab styles for when they are active
    const tabStyle = activeTabName => ({
        cursor: 'pointer',
        borderBottom: activeTab === activeTabName ? '2px solid #000' : 'none',
        padding: '10px',
        fontWeight: activeTab === activeTabName ? 'bold' : 'normal',
        color: activeTab === activeTabName ? '#000' : '#aaa',
    });

    return (
        <div className="container mt-4">
            <UserProfile isCurrent={isCurrent} />
            <div className="review-tabs d-flex justify-content-center mt-3">
                <div style={tabStyle('borrower')} onClick={() => setActiveTab('borrower')}>
                    {t('borrower_reviews')}
                </div>
                <div style={tabStyle('lender')} onClick={() => setActiveTab('lender')}>
                    {t('lender_reviews')}
                </div>
            </div>
            <ReviewList
                reviews={activeTab === 'borrower' ? borrowerReviews : lenderReviews}
                activeTab={activeTab}
            />
        </div>
    );
};
export default UserProfileView;
