import {api, api_} from "../api/api.ts";

export interface Asset_borrower_data {
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
        selfUrl: string
    }
}

export interface Asset_lender_data {
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
        selfUrl: string
    }
}

const useReview = () => {

    const handleGetLendingInfo = async (lendingNumber) => {
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
            const data_borrower = await api_.get(body.userReference)
            const body_borrower = data_borrower.data

            // get the user that lent it (owner)
            const data_lender = await api_.get(body_assetInstance.userReference)
            const body_lender = data_lender.data

            const tmp = body_assetInstance.selfUrl.match(/\/(\d+)$/);
            const num = tmp ? parseInt(tmp[1], 10) : null


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
                    assetInstanceNumber: num
                },
                lender: {
                    userName: body_lender.userName,
                    selfUrl: body_lender.selfUrl
                },
                borrower: {
                    userName: body_borrower.userName,
                    selfUrl: body_borrower.selfUrl
                }
            }

        }catch (e){
            console.log("Error")
        }
    }

    const handleSendBorrowerReview = async (lendingNumber) => {

    }

    const handleSendLenderReview = async (lendingNumber) => {

    }

    return{
        handleGetLendingInfo,
        handleSendBorrowerReview,
        handleSendLenderReview
    };
}

export default useReview;