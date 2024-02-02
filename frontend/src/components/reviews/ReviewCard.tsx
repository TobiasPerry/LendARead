import "../styles/starRating.css"
const ReviewCard = ({
        title = "",
        placeholder  = "",
        error_stars  = "",
        error_description  = "",
        showError_stars = false,
        showError_review = false,
        type = "",
        handleRating = (value) => {},
        handleReview = (value) => {}
        }) => {
    return (
        <>
            <div style={{backgroundColor: '#f0f5f0', borderRadius: '20px', margin: '20px', padding: '20px'}}>
                <h2>
                    {title}
                </h2>
                <div className="rating-wrapper">

                    <input type="radio" id={`${type}-5-star-rating`} name={`${type}-star-rating`} value="5"
                           onClick={() => {handleRating(5)}}/>
                    <label htmlFor={`${type}-5-star-rating`} className="star-rating"
                           id={`star5-${type}`}>
                        <i className="fas fa-star d-inline-block star"></i>
                    </label>


                    <input type="radio" id={`${type}-4-star-rating`} name={`${type}-star-rating`} value="4"
                           onClick={() => {handleRating(4)}}/>
                    <label htmlFor={`${type}-4-star-rating`} className="star-rating star"
                           id={`star4-${type}`}>
                        <i className="fas fa-star d-inline-block star"></i>
                    </label>


                    <input type="radio" id={`${type}-3-star-rating`} name={`${type}-star-rating`} value="3"
                           onClick={() => {handleRating(3)}}/>
                    <label htmlFor={`${type}-3-star-rating`} className="star-rating star"
                           id={`star3-${type}`}>
                        <i className="fas fa-star d-inline-block star"></i>
                    </label>


                    <input type="radio" id={`${type}-2-star-rating`} name={`${type}-star-rating`} value="2"
                           onClick={() => {handleRating(2)}}/>
                    <label htmlFor={`${type}-2-star-rating`} className="star-rating star"
                           id={`star2-${type}`}>
                        <i className="fas fa-star d-inline-block star"></i>
                    </label>


                    <input type="radio" id={`${type}-1-star-rating`} name={`${type}-star-rating`} value="1"
                           onClick={() => {handleRating(1)}}/>
                    <label htmlFor={`${type}-1-star-rating`} className="star-rating star"
                           id={`star1-${type}`}>
                        <i className="fas fa-star d-inline-block star"></i>
                    </label>
                </div>
                <p className="error">
                    {showError_stars ? error_stars : ''}
                </p>
                <textarea className="form-control" aria-label="With textarea" id="review-area"
                          placeholder={placeholder}
                          onChange={(event) => {handleReview(event.target.value)}}
                >
                </textarea>
                <p className="error">
                    {showError_review ? error_description : ''}
                </p>
            </div>
        </>
    )
}

export default ReviewCard;