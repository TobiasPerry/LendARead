import {useTranslation} from "react-i18next";
import UserReviews from "../reviews/UserReviews.tsx";
import useLendings from "../../hooks/lendings/useLendings.ts";
import {useEffect} from "react";
import useReviews from "../../hooks/reviews/useReviews.ts";

const LendingsReviews = ({asset}) => {

    const {t} = useTranslation();
    const {lendingReviews, fetchLendingReviews} = useReviews();

    useEffect(()=> {
        if(asset !== undefined && asset.lending !== undefined)
            fetchLendingReviews(asset.lending).then()
    }, [asset])

    return (
        <>
        {
                <div className="lendings-container">
                    <h3 className="lendings-title">{t('reviews_text')}</h3>
                    <UserReviews totalPages={1} changePage={() => {}} currentPage={1} reviews={lendingReviews} />
                </div>
        }
        </>
    )
}

export default LendingsReviews;