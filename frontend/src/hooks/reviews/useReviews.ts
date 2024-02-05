import {useContext, useState} from "react";
import {api, api_} from "../api/api.ts";
import {AuthContext} from "../../contexts/authContext.tsx";
import {extractTotalPages} from "../assetInstance/useAssetInstance.ts";
import useUserDetails from "../assetInstance/useUserDetails.ts";
import {extractId} from "../assetInstance/useUserAssetInstances.ts";
import photoHolder from "../../../public/static/profile_placeholder.jpeg";
import {useTranslation} from "react-i18next";
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
    const [lendingReviews, setLendingReviews] = useState([])

    const [currentPageLenderReviews, setPageLenderReviews] = useState(1)
    const [currentPageBorrowerReviews, setPageBorrowerReviews] = useState(1)
    const [totalPagesLenderReviews, setTotalPagesLenderReviews] = useState(1)
    const [totalPagesBorrowerReviews, setTotalPagesBorrowerReviews] = useState(1)
    const [error, setError] = useState({status: false, text: ""})
    const {t} = useTranslation()


    const {retrieveUserDetails} = useUserDetails()
    const PAGE_SIZE = 3


    const fetchLendingReviews = async (lending: any) => {

        const reviews = []

        if(lending.hasOwnProperty('borrowerReviewUrl')) {
            try {
                const borrowerReview = await api.get(lending.borrowerReviewUrl)
                const borrowerReviewDetails = await retrieveUserDetails(extractId(borrowerReview.data.reviewer))
                reviews.push({...borrowerReview.data, reviewerDetails: borrowerReviewDetails, reviewerId: extractId(borrowerReview.data.reviewer), type: t("BORROWER")})
            } catch (e) {
               setError({status: true, text: t("errors.failedToFetchBorrowerReview")} )
            }

        }

        if(lending.hasOwnProperty('lenderReviewUrl')) {
            try {
                const lenderReview = await api.get(lending.lenderReviewUrl)
                const lenderReviewDetails = await retrieveUserDetails(extractId(lenderReview.data.reviewer))
                reviews.push({...lenderReview.data, reviewerDetails: lenderReviewDetails, reviewerId: extractId(lenderReview.data.reviewer), type: t("LENDER")})
            } catch (e) {
                setError({status: true, text: t("errors.failedToFetchLenderReview")} )
            }

        }

        if(lending.hasOwnProperty('assetInstanceReview')) {
            try {
                const assetInstanceReview = await api.get(lending.assetInstanceReview)
                const assetInstanceReviewDetails = await retrieveUserDetails(extractId(assetInstanceReview.data.reviewer))
                reviews.push({
                    ...assetInstanceReview.data,
                    reviewerDetails: assetInstanceReviewDetails,
                    reviewerId: extractId(assetInstanceReview.data.reviewer),
                    type: t("ASSET_INSTANCE")
                })
            } catch (e) {
                setError({status: true, text: t("errors.failedToFetchAssetInstanceReview")} )
            }
        }
        setLendingReviews(reviews)
    }

    const fetchLenderReviews = async (page: number, user:string) => {
        try {
            const lenderReviewsResponse = await api.get(`/users/${user}/lender_reviews/`, {params: {"itemsPerPage": PAGE_SIZE, "page": page}})

            //@ts-ignore
            const linkHeader: any = lenderReviewsResponse.headers.get("Link");
            const totalPages = extractTotalPages(linkHeader);
            setTotalPagesLenderReviews(totalPages);
            if (lenderReviewsResponse.status === 204) {
                setLenderReviews([])
                return
            }
            const lenderReviewsPromises = lenderReviewsResponse.data.map(async (review: ReviewApi) => {
                const reviewerId = extractId(review.reviewer)
                return {
                    ...review,
                    reviewerDetails: await retrieveUserDetails(reviewerId),
                    reviewerId: reviewerId
                }
            })
            const lenderReviewsData = await Promise.all(lenderReviewsPromises)
            setLenderReviews(lenderReviewsData)
        } catch (e) {
            setError({status: true, text: t("errors.failedToFetchLenderReviews")})
            setLenderReviews([])
        }
    }

    const fetchBorrowerReviews = async (page: number, user: string) => {
        try {
            const borrowerReviewsResponse = await api.get(`/users/${user}/borrower_reviews/`, {params: {"itemsPerPage": PAGE_SIZE, "page": page}})

            //@ts-ignore
            const linkHeader: any = borrowerReviewsResponse.headers.get("Link");
            const totalPages = extractTotalPages(linkHeader);
            setTotalPagesBorrowerReviews(totalPages);
            if (borrowerReviewsResponse.status === 204) {
                setBorrowerReviews([])
                return
            }
            const borrowerReviewsPromises = borrowerReviewsResponse.data.map(async (review: ReviewApi) => {
                const reviewerId = extractId(review.reviewer)
                return {
                    ...review,
                    reviewerDetails: await retrieveUserDetails(reviewerId),
                    reviewerId: reviewerId
                }
            })
            const borrowerReviewsData = await Promise.all(borrowerReviewsPromises)
            setBorrowerReviews(borrowerReviewsData)
        } catch (e) {
            setError({status: true, text: t("errors.failedToFetchBorrowerReviews")})
            setBorrowerReviews([])
        }
    }

    const fetchReviews = async (user: string) => {
        await fetchLenderReviews(currentPageLenderReviews, user)
        await fetchBorrowerReviews(currentPageBorrowerReviews, user)
    }

    const changePageLenderReviews = async (newPage: number, user: string) => {
        await fetchLenderReviews(newPage, user)
        await setPageLenderReviews(newPage)
    }

    const changePageBorrowerReviews = async (newPage: number, user: string) => {
        await fetchBorrowerReviews(newPage, user)
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
        changePageBorrowerReviews,
        lendingReviews,
        fetchLendingReviews,
        error
    }
}

export default useReviews;