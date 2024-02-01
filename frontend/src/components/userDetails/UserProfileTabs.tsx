import {t} from "i18next";

const UserProfileTabs = ({selectedTab, setSelectedTab}) => {

    return (
        <ul className="nav nav-tabs" id="user-tab" role="tablist">
            <li className="nav-item" role="presentation">
                <button
                    className="nav-link black-text"
                    type="button"
                    role="tab"
                    onClick={(_) => setSelectedTab("lender_reviews")}
                    aria-selected={selectedTab === "lender_reviews"}>{t("lender_reviews")}</button>
            </li>
            <li className="nav-item" role="presentation">
                <button
                    className="nav-link black-text"
                    type="button"
                    role="tab"
                    onClick={(_) => setSelectedTab("borrower_reviews")}
                    aria-selected={selectedTab === "borrower_reviews"}>{t("borrower_reviews")}</button>
            </li>
        </ul>
    )
}

export default UserProfileTabs;