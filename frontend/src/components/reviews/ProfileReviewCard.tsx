import "../styles/profileReview.css"

const ProfileReviewCard = ({review}) => {
    const user = review.reviwer.split("/").pop();
    const lending = review.lending;
    const rating = review.rating;
    const reviewText = review.review;

    const renderStars = (rating: number) => {
        const stars = [];
        for (let i = 0; i < rating; i++) {
            stars.push(<span key={i} className="fa fa-star checked"></span>);
        }
        for (let i = rating; i < 5; i++) {
            stars.push(<span key={i} className="fa fa-star unchecked"></span>);
        }
        return (
            <div className="user-review-card-rating">
                {stars}
            </div>
        )
    }

    return (
        <div className="user-review-card">
            <a href={`/user/${user}`}>
            <div className="user-review-card-img-container">
                <img className="user-review-card-img" src="https://preview.redd.it/if-the-show-gets-released-what-would-happen-if-alastor-gets-v0-ntsn6fk2oe5a1.jpg?auto=webp&s=d473c255d8aa94a4c2c9617786053f1b98c4a010" alt="reviewer profile picture" />
            </div>
            </a>
            <div className="user-review-card-content">
                <div className="user-review-card-header">
                    <a href={`/user/${user}`}>
                        <h2>{user}</h2>
                    </a>
                    {renderStars(rating)}
                </div>
                <p>{reviewText}</p>
            </div>
        </div>
    )
}

export default ProfileReviewCard;
