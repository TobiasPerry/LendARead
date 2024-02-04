import ProfileReviewCard from "./ProfileReviewCard.tsx";
import Pagination from "../Pagination.tsx";
import {ReviewApi} from "../../hooks/reviews/useReviews.ts";
import ReviewCardModal from "../modals/ReviewCardModal.tsx";
import {useState} from "react";
import {useTranslation} from "react-i18next";

const UserReviews = ({reviews,changePage, currentPage, totalPages}) => {

    const [showModal, setShowModal] = useState(false)
    const {t} = useTranslation()
    const [currentReview, setCurrentReview] = useState({
        lending: "",
        rating: 0,
        review: "",
        reviewer: "",
        selfUrl:"",
        image: "",
        reviewerDetails: {
            image: ""
        },
        reviewerId: ""
    })

    const handleClickedReview = async (review) => {
        await setCurrentReview(review)
        setShowModal(true)
    }

    return (
        <div>
        <div className="d-flex flex-row gap-3 mb-4 flex-wrap">
            {(reviews.length > 0
                    ? reviews.map((review: ReviewApi, index) =>
                        <ProfileReviewCard key={index} review={review} clickedReview={handleClickedReview}/>
                    )
                    : <div style={{
                        textAlign: "center",
                        width: "100%",
                        marginTop: "20px",
                        marginBottom: "20px",
                        fontWeight: "bold",
                    }}>
                        {t('no_reviews')}
                      </div>
            )}
        </div>
        <Pagination changePage={changePage} currentPage={currentPage} totalPages={totalPages}/>
        <ReviewCardModal showModal={showModal} review={currentReview} handleCloseModal={() => setShowModal(false)} />
        </div>
  );
}

export default UserReviews;