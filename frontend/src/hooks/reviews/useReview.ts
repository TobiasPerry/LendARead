import {api, api_} from "../api/api.ts";

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

const useReview = () => {

    const handleGetLendingInfoForLender = async (lendingNumber) => {
        try {
            // get the lending
            const url = `/lendings/${lendingNumber}`
            const data = await api.get(url)
            const body = data.data

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

            return {
                book: {
                    title: body_asset.title,
                    author: body_asset.author,
                    userName: body_lender.userName,
                    userImage: body_lender.image,
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
            }

        }catch (e){
            console.log("Error: " + e)
            return null
        }
    }

    const handleGetLendingInfoForBorrower = async (lendingNumber) => {
        try {
            // get the lending
            const url = `/lendings/${lendingNumber}`
            const data = await api.get(url)
            const body = data.data

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

            const tmp_assetInstance = body_assetInstance.selfUrl.match(/\/(\d+)$/);
            const num_assetInstance = tmp_assetInstance ? parseInt(tmp_assetInstance[1], 10) : null
            const tmp_lender = body_lender.selfUrl.match(/\/(\d+)$/);
            const num_lender = tmp_lender ? parseInt(tmp_lender[1], 10) : null


            return {
                book: {
                    title: body_asset.title,
                    author: body_asset.author,
                    userName: body_lender.userName,
                    userImage: body_lender.image,
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
                }
            }

        }catch (e){
            console.log("Error")
            return null
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
                        'Content-Type': 'application/vnd.userLenderReview.v1+json'
                    }
                }
            );
            const asset_instance_review_response = await api.post(
                `/assetInstances/${assetInstanceNum}/reviews`,
                body_assetInstance,
                {
                    headers: {
                        'Content-Type': 'application/vnd.assetInstanceReview.v1+json'
                    }
                }
            );
            console.log(lender_user_review_response)
            console.log(asset_instance_review_response)
            return {
                userReviewResponse: lender_user_review_response,
                assetInstanceReviewResponse: asset_instance_review_response
            };
        }catch (e){
            console.log("error: " + e)
            return null;
        }
    }

    const handleSendLenderReview = async (body_userReview: body_review, userNum) => {
        try {
            console.log(userNum)
            const borrower_user_review_response = await api.post(
                `/users/${userNum}/borrower_reviews`,
                body_userReview,
                {
                    headers: {
                        'Content-Type': 'application/vnd.userBorrowerReview.v1+json'
                    }
                }
            )
            console.log(borrower_user_review_response)
            return borrower_user_review_response;
        }catch (e){
            console.log("error: " + e)
            return null;
        }
    }

    return{
        handleGetLendingInfoForLender,
        handleGetLendingInfoForBorrower,
        handleSendBorrowerReview,
        handleSendLenderReview
    };
}

export default useReview;