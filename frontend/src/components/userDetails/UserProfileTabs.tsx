import {t} from "i18next";
import {useTranslation} from "react-i18next";

const UserProfileTabs = ({selectedTab, setSelectedTab}) => {

    const {t} = useTranslation()
    return (
        <ul className="nav nav-tabs" id="user-tab" role="tablist">
            <li className="nav-item" role="presentation">
                <button
                    className="nav-link black-text"
                    type="button"
                    role="tab"
                    onClick={(_) => setSelectedTab("lender_reviews")}
                    style={{fontWeight: selectedTab === "lender_reviews" ? "bold": "normal"}}>{t("userProfile.lender_reviews")}</button>
            </li>
            <li className="nav-item" role="presentation">
                <button
                    className="nav-link black-text"
                    type="button"
                    role="tab"
                    onClick={(_) => setSelectedTab("borrower_reviews")}
                    style={{fontWeight: selectedTab === "borrower_reviews" ? "bold": "normal"}}>{t("userProfile.borrower_reviews")}</button>
            </li>
        </ul>
    )
}

export default UserProfileTabs;