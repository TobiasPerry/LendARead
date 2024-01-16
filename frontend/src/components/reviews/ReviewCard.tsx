import "../styles/starRating.css"
const ReviewCard = ({
                        title = "",
                        placeholder  = "",
                        error_stars  = "",
                        error_description  = ""
                    }) => {
    return (
        <>
            <div style={{backgroundColor: '#f0f5f0', borderRadius: '20px', margin: '20px', padding: '20px'}}>
                <h2>
                    {title}
                </h2>
                <div className="rating-wrapper">

                    <input type="radio" id="lender-5-star-rating" name="star-rating" value="5"/>
                    <label htmlFor="lender-5-star-rating" className="star-rating" id="star5-lender">
                        <i className="fas fa-star d-inline-block star"></i>
                    </label>


                    <input type="radio" id="lender-4-star-rating" name="star-rating" value="4"/>
                    <label htmlFor="lender-4-star-rating" className="star-rating star"
                           id="star4-lender">
                        <i className="fas fa-star d-inline-block star"></i>
                    </label>


                    <input type="radio" id="lender-3-star-rating" name="star-rating" value="3"/>
                    <label htmlFor="lender-3-star-rating" className="star-rating star"
                           id="star3-lender">
                        <i className="fas fa-star d-inline-block star"></i>
                    </label>


                    <input type="radio" id="lender-2-star-rating" name="star-rating" value="2"/>
                    <label htmlFor="lender-2-star-rating" className="star-rating star"
                           id="star2-lender">
                        <i className="fas fa-star d-inline-block star"></i>
                    </label>


                    <input type="radio" id="lender-1-star-rating" name="star-rating" value="1"/>
                    <label htmlFor="lender-1-star-rating" className="star-rating star"
                           id="star1-lender">
                        <i className="fas fa-star d-inline-block star"></i>
                    </label>
                </div>
                <p className="error">
                    {error_stars}
                </p>
                <textarea className="form-control" aria-label="With textarea" id="review-area"
                          placeholder={placeholder}></textarea>
                <p className="error">
                    {error_description}
                </p>
            </div>
        </>
    )
}

export default ReviewCard;