import {api, api_} from "../api/api.ts";
import {extractTotalPages, getErrorMsg} from "../assetInstance/useAssetInstance.ts";
import types from "../api/types.ts";

export interface body_review {
    review,
    rating,
    lendingId
}

export interface Asset_and_borrower_data {
    book: {
        title: string,
        author: string,
        userName: string,
        userImage: string,
        image: string,
        physicalCondition: string,
        country: string,
        province: string,
        locality: string,
        assetInstanceNumber: number
    },
    borrower: {
        userName: string,
        selfUrl: string,
        userId: number
    }
}

export interface Asset_and_lender_data {
    book: {
        title: string,
        author: string,
        userName: string,
        userImage: string,
        image: string,
        physicalCondition: string,
        country: string,
        province: string,
        locality: string,
        assetInstanceNumber: number
    },
    lender: {
        userName: string,
        selfUrl: string,
        userId: number
    }
}

export interface ShowReview {
    text: string,
    userName: string,
    role: string,
    userImage: string,
    rating: number,
    userId: number
}

const useReview = () => {

    const handleGetLendingInfoForLender = async (lendingNumber) => {
        try {
            // get the lending
            const url = `/lendings/${lendingNumber}`
            const data = await api.get(url)
            const body = data.data

            if(body.borrowerReviewUrl !== null && body.borrowerReviewUrl !== undefined){
                return {
                    info: null,
                    exits: true
                }
            }

            if(body.state !== 'FINISHED'){
                return {
                    info: null,
                    exists: false
                }
            }
            // get the assetInstance
            const data_assetInstance = await api_.get(body.assetInstance)
            const body_assetInstance = data_assetInstance.data

            // get the location of the asset
            const data_location = await api_.get(body_assetInstance.locationReference)
            const body_location = data_location.data

            //get the asset
            const data_asset = await api_.get(body_assetInstance.assetReference)
            const body_asset = data_asset.data

            //get the user that borrowed it
            const data_borrower = await api_.get(body.borrowerUrl)
            const body_borrower = data_borrower.data

            // get the user that lent it (owner)
            const data_lender = await api_.get(body_assetInstance.userReference)
            const body_lender = data_lender.data

            const tmp_assetInstance = body_assetInstance.selfUrl.match(/\/(\d+)$/);
            const num_assetInstance = tmp_assetInstance ? parseInt(tmp_assetInstance[1], 10) : null

            const tmp_borrower = body_borrower.selfUrl.match(/\/(\d+)$/);
            const num_borrower = tmp_borrower ? parseInt(tmp_borrower[1], 10) : null

            const tmp_owner = body_lender.selfUrl.match(/\/(\d+)$/);
            const num_owner = tmp_owner ? parseInt(tmp_owner[1], 10) : null

            return {
                info: {
                    book: {
                        title: body_asset.title,
                        author: body_asset.author,
                        userName: body_lender.userName,
                        userImage: body_lender.image,
                        userNum: num_owner,
                        image: body_assetInstance.imageReference,
                        physicalCondition: body_assetInstance.physicalCondition,
                        country: body_location.country,
                        province: body_location.province,
                        locality: body_location.locality,
                        assetInstanceNumber: num_assetInstance
                    },
                    borrower: {
                        userName: body_borrower.userName,
                        selfUrl: body_borrower.selfUrl,
                        userId: num_borrower
                    }
                },
                exists: false
            }

        }catch (e){
            console.error("Error: " + e)
            return {info: null, exits: false};
        }
    }

    const handleGetLendingInfoForBorrower = async (lendingNumber) => {
        try {
            // get the lending
            const url = `/lendings/${lendingNumber}`
            const data = await api.get(url)
            const body = data.data

            if((body.lenderReviewUrl !== null && body.lenderReviewUrl !== undefined) || (body.assetInstanceReview !== null && body.assetInstanceReview !== undefined)){
                return {
                    info: null,
                    exists: true
                }
            }

            if(body.state !== 'FINISHED'){
                return {
                    info: null,
                    exists: false
                }
            }

            // get the assetInstance
            const data_assetInstance = await api_.get(body.assetInstance)
            const body_assetInstance = data_assetInstance.data

            // get the location of the asset
            const data_location = await api_.get(body_assetInstance.locationReference)
            const body_location = data_location.data

            //get the asset
            const data_asset = await api_.get(body_assetInstance.assetReference)
            const body_asset = data_asset.data

            // get the user that lent it (owner)
            const data_lender = await api_.get(body_assetInstance.userReference)
            const body_lender = data_lender.data

            const num_assetInstance =  body_assetInstance.id
            const num_lender = body_lender.id


            return {
                info: {
                    book: {
                        title: body_asset.title,
                        author: body_asset.author,
                        userName: body_lender.userName,
                        userImage: body_lender.image,
                        userNum: num_lender,
                        image: body_assetInstance.imageReference,
                        physicalCondition: body_assetInstance.physicalCondition,
                        country: body_location.country,
                        province: body_location.province,
                        locality: body_location.locality,
                        assetInstanceNumber: num_assetInstance
                    },
                    lender: {
                        userName: body_lender.userName,
                        selfUrl: body_lender.selfUrl,
                        userId: num_lender
                    },
                },
                exists: false
            }

        }catch (e){
            console.error("Error")
            return {info: null, exists: false}
        }
    }

    const handleSendBorrowerReview = async (
        body_userReview: body_review, body_assetInstance: body_review, userNum, assetInstanceNum
    ) => {
        try {
            const lender_user_review_response = await api.post(
                `/users/${userNum}/lender_reviews`,
                body_userReview,
                {
                    headers: {
                        'Content-Type': types.VND_USER_LENDER_REVIEW
                    }
                }
            );
            const asset_instance_review_response = await api.post(
                `/assetInstances/${assetInstanceNum}/reviews`,
                body_assetInstance,
                {
                    headers: {
                        'Content-Type': types.VND_ASSET_INSTANCE_REVIEW
                    }
                }
            );
            return {
                userReviewResponse: lender_user_review_response,
                assetInstanceReviewResponse: asset_instance_review_response,
                errorMessage: ""
            };
        }catch (e){
            const errorMsg = getErrorMsg(e.response)
            console.error("error: " + e)
            return {
                userReviewResponse: null,
                assetInstanceReviewResponse: null,
                errorMessage: errorMsg
            };
        }
    }

    const handleSendLenderReview = async (body_userReview: body_review, userNum) => {
        try {
            const borrower_user_review_response = await api.post(
                `/users/${userNum}/borrower_reviews`,
                body_userReview,
                {
                    headers: {
                        'Content-Type': types.VND_USER_BORROWER_REVIEW
                    }
                }
            )
            return {res: borrower_user_review_response, errorMessage: ""};
        }catch (e){
            const errorMsg = getErrorMsg(e.response)
            return {res: null, errorMessage: errorMsg};
        }
    }

    const handleGetReviewDataForAssetInstance = async (review) => {
        try{
            // I get the response from assetInstance/:id/review/:id so i need to go and get data from the reviewer
            const res = await api_.get(review.reviewer);
            const data = res.data
            return {
                text: review.review,
                rating: review.rating,
                role: data.role,
                userName: data.userName,
                userImage: data.image,
                userId: data.id
            }
        }catch (e) {
            return null;
        }
    }

    const handleGetAllReviewsForBook = async (assetInstanceNumber, itemsPerPage = 3, page = 1) => {
        try{
            const response_instance = await api.get(`/assetInstances/${assetInstanceNumber}`);
            const body_instance =  response_instance.data
            const response_reviews = await api_.get(body_instance.reviewsReference + `?itemsPerPage=${itemsPerPage}&page=${page}`, undefined);
            const pages = extractTotalPages(response_reviews.headers["link"])
            const body_reviews = response_reviews.data;
            const response_asset = await api_.get(body_instance.assetReference)
            const body_asset = response_asset.data

            return {
                title: body_asset.title,
                author: body_asset.author,
                reviews: body_reviews,
                pages: pages
            }
        }catch (e) {
            return null;
        }
    }

    return{
        handleGetLendingInfoForLender,
        handleGetLendingInfoForBorrower,
        handleSendBorrowerReview,
        handleSendLenderReview,
        handleGetReviewDataForAssetInstance,
        handleGetAllReviewsForBook
    };
}

export default useReview;
