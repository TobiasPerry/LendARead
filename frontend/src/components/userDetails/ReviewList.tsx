// ReviewList.jsx
import ReviewCard from './ReviewCard';

const ReviewList = ({ reviews, activeTab }) => {
    // Pagination logic and state here
    // ...

    return (
        <div className="review-list">
            {reviews.list.map((review, index) => (
                <ReviewCard key={index} review={review} />
            ))}
            {/* Pagination controls */}
            {/* ... */}
        </div>
    );
};
