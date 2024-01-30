const ReviewCard = ({ review }) => {
    return (
        <div className="review-card">
            <div className="reviewer-name">{review.reviewer.name}</div>
            <div className="review-text">{review.review}</div>
            {/* Additional review details */}
        </div>
    );
};

export default ReviewCard;