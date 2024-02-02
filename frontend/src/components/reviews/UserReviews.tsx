import ProfileReviewCard from "./ProfileReviewCard.tsx";
import Pagination from "../Pagination.tsx";
import {ReviewApi} from "../../hooks/reviews/useReviews.ts";

const UserReviews = ({reviews, userName, user, changePage, currentPage, totalPages}) => {
    return (
        <>
            {(reviews.length > 0
                    ? reviews.map((review: ReviewApi, index) =>
                        <ProfileReviewCard key={index} review={review} user={user} userName={userName}/>
                    )
                    : <p>No Lender reviews yet</p>
            )}
            <Pagination changePage={changePage} currentPage={currentPage} totalPages={totalPages}/>
        </>
  );
}

export default UserReviews;