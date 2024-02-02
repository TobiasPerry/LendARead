import {useContext, useState} from "react";
import {api} from "../api/api.ts";
import {AuthContext} from "../../contexts/authContext.tsx";
import {extractTotalPages} from "../assetInstance/useAssetInstance.ts";
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
    const [currentPageLenderReviews, setPageLenderReviews] = useState(1)
    const [currentPageBorrowerReviews, setPageBorrowerReviews] = useState(1)
    const [totalPagesLenderReviews, setTotalPagesLenderReviews] = useState(1)
    const [totalPagesBorrowerReviews, setTotalPagesBorrowerReviews] = useState(1)

    const {user} = useContext(AuthContext)
    const PAGE_SIZE = 2

    const fetchLenderReviews = async (page: number ) => {
        try {
            const lenderReviewsResponse = await api.get(`/users/${user}/lender_reviews/`, {params: {"itemsPerPage": PAGE_SIZE, "page": page}})

            //@ts-ignore
            const linkHeader: any = lenderReviewsResponse.headers.get("Link");
            const totalPages = extractTotalPages(linkHeader);
            setTotalPagesLenderReviews(totalPages);

            const lenderReviewsData = lenderReviewsResponse.data
            setLenderReviews(lenderReviewsData)
        } catch (e) {

        }
    }

    const fetchBorrowerReviews = async (page: number ) => {
        try {
            const borrowerReviewsResponse = await api.get(`/users/${user}/borrower_reviews/`, {params: {"itemsPerPage": PAGE_SIZE, "page": page}})

            //@ts-ignore
            const linkHeader: any = borrowerReviewsResponse.headers.get("Link");
            const totalPages = extractTotalPages(linkHeader);
            setTotalPagesBorrowerReviews(totalPages);

            const borrowerReviewsData = borrowerReviewsResponse.data
            setBorrowerReviews(borrowerReviewsData)
        } catch (e) {
            console.log(e)
        }
    }

    const fetchReviews = async () => {
        await fetchLenderReviews(currentPageLenderReviews)
        await fetchBorrowerReviews(currentPageBorrowerReviews)
    }

    const changePageLenderReviews = async (newPage: number) => {
        await fetchLenderReviews(newPage)
        await setPageLenderReviews(newPage)
    }

    const changePageBorrowerReviews = async (newPage: number) => {
        await fetchBorrowerReviews(newPage)
        await setPageBorrowerReviews(newPage)
    }

    return {
        lenderReviews,
        borrowerReviews,
        fetchReviews,
        currentPageBorrowerReviews,
        currentPageLenderReviews,
        totalPagesBorrowerReviews,
        totalPagesLenderReviews,
        changePageLenderReviews,
        changePageBorrowerReviews
    }
}

export default useReviews;