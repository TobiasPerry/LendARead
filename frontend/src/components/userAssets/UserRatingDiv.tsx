import {useTranslation} from "react-i18next";

const UserRating = ({userDetails}) => {
    const { t } = useTranslation();

    return (
        <>
            <p >
                <>
                    { userDetails.role === "LENDER" &&
                        <>
                            {t('userProfile.lender')}
                            <span className="user-role-stars">
                                        {userDetails.ratingAsLender <= 0 ? (
                                            "-.- ") : (
                                            Math.round(userDetails.ratingAsLender * 10) / 10
                                        )}
                                ★ </span>
                        </>
                    }
                </>
                <>
                    {t('userProfile.borrower')}
                    <span className="user-role-stars">
                            {userDetails.ratingAsBorrower <= 0 ? (
                                "-.- ") : (
                                Math.round(userDetails.ratingAsBorrower * 10) / 10
                            )}
                        ★</span>
                </>
            </p>
        </>
    )
}

export default UserRating;