import {useTranslation} from "react-i18next";
import UserReviews from "../reviews/UserReviews.tsx";
import useLendings from "../../hooks/lendings/useLendings.ts";
import  {useEffect} from "react";
import useReviews from "../../hooks/reviews/useReviews.ts";
import Snackbar from "../SnackBar.tsx";

const LendingsReviews = ({asset}) => {

    const {t} = useTranslation();
    const {lendingReviews, fetchLendingReviews, error} = useReviews();

    useEffect(()=> {
        if(asset !== undefined && asset.lending !== undefined)
            fetchLendingReviews(asset.lending).then()
    }, [asset])

    return (
        <>
        {lendingReviews.length > 0 &&
                <div className="lendings-container">
                    <h3 className="lendings-title">{t('reviews_text')}</h3>
                    <UserReviews totalPages={1} changePage={() => {}} currentPage={1} reviews={lendingReviews} />
                </div>
        }
            {error.status && <Snackbar message={error.text} />}
        </>
    )
}

export default LendingsReviews;