import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';

// Assume these are imported from their respective component files
import UserProfile from '../../components/userDetails/UserProfile';
import ReviewTabs from '../../components/userDetails/ReviewTabs';
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

    return (
        <div className="container mt-4">
            <UserProfile isCurrent={isCurrent} />
            <ReviewTabs activeTab={activeTab} handleTabSwitch={handleTabSwitch} />
            <ReviewList
                reviews={activeTab === 'borrower' ? borrowerReviews : lenderReviews}
                activeTab={activeTab}
            />
        </div>
    );
};

export default UserProfileView;
