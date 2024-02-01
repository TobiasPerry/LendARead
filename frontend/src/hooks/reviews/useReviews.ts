import {useState} from "react";

const useReviews = () => {

    const [lenderReviews, setLenderReviews] = useState([]);
    const [borrowerReviews, setBorrowerReviews] = useState([]);


    const fetchReviews = async () => {

    }

    return {
        lenderReviews, borrowerReviews, fetchReviews
    }
}

export default useReviews;