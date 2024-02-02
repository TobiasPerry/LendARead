import ProfileReviewCard from "./ProfileReviewCard.tsx";
import Pagination from "../Pagination.tsx";
import {ReviewApi} from "../../hooks/reviews/useReviews.ts";

const UserReviews = ({reviews,changePage, currentPage, totalPages}) => {
    return (
        <div>
        <div className="d-flex flex-row gap-3 mb-4">
            {(reviews.length > 0
                    ? reviews.map((review: ReviewApi, index) =>
                        <ProfileReviewCard key={index} review={review} />
                    )
                    : <p>No Lender reviews yet</p>
            )}
        </div>
        <Pagination changePage={changePage} currentPage={currentPage} totalPages={totalPages}/>
        </div>
  );
}

export default UserReviews;