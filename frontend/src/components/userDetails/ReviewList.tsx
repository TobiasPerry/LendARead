import ReviewCard from './ReviewCard';
import {useTranslation} from "react-i18next";

const ReviewList = ({ reviews, activeTab }) => {
    const { t } = useTranslation();

    return (
        <div className="review-list">
            {reviews.length > 0 ? (
                reviews.list.map((review, index) => (
                    <ReviewCard key={index} review={review} />
                ))
            ) : (
                <div className="text-center">
                    {t('no_reviews')}
                </div>
            )}
            {/* Add Pagination controls */}
        </div>
    );
};


export default ReviewList;