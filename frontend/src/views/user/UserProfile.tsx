import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';

// Assume these are imported from their respective component files
import UserProfile from '../../components/userDetails/UserProfile';
import ReviewList from '../../components/userDetails/ReviewList';
import useUserProfileReviews from "../../hooks/users/useUserProfileReviews.ts";

const UserProfileView = ({  }) => {
    const { t } = useTranslation();
    const [activeTab, setActiveTab] = useState('borrower'); // 'borrower' or 'lender'
    const {borrowerReviews, lenderReviews} = useUserProfileReviews();

    const isCurrent = true;

    const handleTabSwitch = (tab) => {
        setActiveTab(tab);
    };

    const tabStyle = activeTabName => ({
        cursor: 'pointer',
        borderBottom: activeTab === activeTabName ? '2px solid #000' : 'none',
    });

    return (
        <div className="container mt-4">
            <UserProfile isCurrent={isCurrent} />
            <div className="review-tabs">
                <div style={tabStyle('borrower')} onClick={() => handleTabSwitch('borrower')}>
                    Reseñas Prestatario
                </div>
                <div style={tabStyle('lender')} onClick={() => handleTabSwitch('lender')}>
                    Reseñas Prestamista
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
