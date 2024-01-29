import ReviewCard from './ReviewCard';
import {useTranslation} from "react-i18next";

const ReviewList = ({ reviews, activeTab }) => {

    const { t } = useTranslation();

    return (
        <div className="review-list">
            {reviews.length > 0 && reviews.list.map((review, index) => (
                <ReviewCard key={index} review={review} />
            ))}
            {reviews.length === 0 && (
                <div> {t("no_reviews")} </div>
            )}
            {/* Pagination controls */}
            {/* ... */}
        </div>
    );
};

export default ReviewList;