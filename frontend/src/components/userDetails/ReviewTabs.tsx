const ReviewTabs = ({ activeTab, handleTabSwitch }) => {
    const tabStyle = activeTabName => ({
        cursor: 'pointer',
        borderBottom: activeTab === activeTabName ? '2px solid #000' : 'none',
    });

    return (
        <div className="review-tabs">
            <div style={tabStyle('borrower')} onClick={() => handleTabSwitch('borrower')}>
                Reseñas Prestatario
            </div>
            <div style={tabStyle('lender')} onClick={() => handleTabSwitch('lender')}>
                Reseñas Prestamista
            </div>
        </div>
    );
};

export default ReviewTabs;