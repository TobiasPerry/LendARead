import {useContext, useState} from "react";
import {api} from "../api/api.ts";
import {AuthContext} from "../../contexts/authContext.tsx";
export interface ReviewApi {
    lending: string,
    rating: number,
    review: string,
    reviewer: string,
    selfUrl: string
}

const useReviews = () => {

    const [lenderReviews, setLenderReviews] = useState([]);
    const [borrowerReviews, setBorrowerReviews] = useState([]);
    const {user} = useContext(AuthContext)


    const fetchReviews = async () => {
        try {
            const lenderReviewsData: Array<ReviewApi> = (await api.get(`/users/${user}/lender_reviews`)).data
            setLenderReviews(lenderReviewsData)
            const borrowerReviewsData: Array<ReviewApi> = (await api.get(`/users/${user}/borrower_reviews`)).data
            setBorrowerReviews(borrowerReviewsData)
        } catch (e) {
            console.log(e)
        }
    }

    return {
        lenderReviews, borrowerReviews, fetchReviews
    }
}

export default useReviews;