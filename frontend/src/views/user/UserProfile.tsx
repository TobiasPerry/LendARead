import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';

// Assume these are imported from their respective component files
import UserProfile from '../../components/userDetails/UserProfile';
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

    // @ts-ignore
    return (
        <div className="main-class">
        <div  style={{
            width: '80%',
            backgroundColor: '#f0f5f0',
            maxHeight: '1000px',
            minHeight: '600px',
            margin: 'auto',
            paddingTop: '60px',
            borderRadius: '60px',
        }}>
            {/* @ts-ignore */}
            {/*<UserProfile isCurrent={isCurrent} />*/}
            <div className="review-tabs d-flex justify-content-center mt-3">
                <div style={tabStyle('borrower')} onClick={() => setActiveTab('borrower')}>
                    {t('borrower_reviews')}
                </div>
                <div style={tabStyle('lender')} onClick={() => setActiveTab('lender')}>
                    {t('lender_reviews')}
                </div>
            </div>
            {/*<ReviewList*/}
            {/*    reviews={activeTab === 'borrower' ? borrowerReviews : lenderReviews}*/}
            {/*    activeTab={activeTab}*/}
            {/*/>*/}
        </div>
        </div>
    );
};
export default UserProfileView;
