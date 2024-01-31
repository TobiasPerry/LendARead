import React from 'react';
import '../styles/StarsReviews.css'

const StarRating = ({ rating }) => {
    const filledStars = Math.floor(rating);
    const hasHalfStar = rating % 1 !== 0;

    const renderStars = () => {
        const stars = [];

        for (let i = 1; i <= 5; i++) {
            if (i <= filledStars) {
                stars.push(<span key={i} className="gold-star filled"></span>);
            } else if (i === filledStars + 1 && hasHalfStar) {
                stars.push(<span key={i} className="gold-star half-filled"></span>);
            } else {
                stars.push(<span key={i} className="gold-star"></span>);
            }
        }

        return stars;
    };

    return <div className="star-rating">{renderStars()}</div>;
};

export default StarRating;
