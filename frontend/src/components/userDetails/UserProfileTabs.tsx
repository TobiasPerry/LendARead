
const UserProfileTabs = ({selectedTab, setSelectedTab}) => {

    return (
        <ul className="nav nav-tabs" id="user-tab" role="tablist">
            <li className="nav-item" role="presentation">
                <button
                    className="nav-link black-text"
                    type="button"
                    role="tab"
                    onClick={(_) => setSelectedTab("lender_reviews")}
                    aria-selected={selectedTab === "lender_reviews"}>Lender Reviews</button>
            </li>
            <li className="nav-item" role="presentation">
                <button
                    className="nav-link black-text"
                    type="button"
                    role="tab"
                    onClick={(_) => setSelectedTab("borrower_reviews")}
                    aria-selected={selectedTab === "borrower_reviews"}>Borrower Reviews</button>
            </li>
        </ul>
    )
}

export default UserProfileTabs;