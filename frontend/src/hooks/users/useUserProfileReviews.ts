import {useState} from "react";

const useUserProfileReviews = () => {

    const [loading, setLoading] = useState<boolean>(false)
    const [lenderReviews, setLenderReviews] = useState<[]>([])
    const [borrowerReviews, setBorrowerReviews] = useState<[]>([])

    return {
        loading, lenderReviews, borrowerReviews
    }
}

export default useUserProfileReviews;