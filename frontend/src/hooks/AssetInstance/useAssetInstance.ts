import {Api} from "../api/api.ts";
import {Simulate} from "react-dom/test-utils";

const useAssetInstance = () => {

    const handleAllAssetInstances = async (page = 1, itemsPerPage = 12) => {
        const books = []

        try {
            const data = await Api.get(`/assetInstances?page=${page}&itemsPerPage=${itemsPerPage}`)
            const body = await data.json()


            for (const assetInstance of body) {
                const response_asset = await Api.get(assetInstance.assetReference, undefined, undefined, true)
                const response_instance = await Api.get(assetInstance.selfUrl, undefined, undefined, true)
                const response_location = await Api.get(assetInstance.locationReference, undefined, undefined, true)
                const response_user = await Api.get(assetInstance.userReference, undefined, undefined, true)
                const body_asset = await response_asset.json()
                const body_instance = await response_instance.json()
                const body_location = await response_location.json()
                const body_user = await response_user.json()
                const book = {
                    title: body_asset.title,
                    author: body_asset.author,
                    language: body_asset.language,
                    image: body_instance.imageReference,
                    physicalCondition: body_instance.physicalCondition,
                    userImage: body_user.image,
                    userName: body_user.userName,
                    country: body_location.country,
                    province: body_location.province,
                    locality: body_location.locality
                }
                books.push(book)
            }

            return books
        } catch (error){
            console.log("Error")
            return null;
        }
    }

    const handleAssetInstance = async (assetInstanceNumber) => {
        try{
            const response_instance = await Api.get(`/assetInstances/${assetInstanceNumber}`);
            const body_instance = await response_instance.json()
            const response_asset = await Api.get(body_instance.assetReference, undefined, undefined, true)
            const response_reviews = await Api.get(body_instance.reviewsReference, undefined, undefined, true);
            const response_location = await Api.get(body_instance.locationReference, undefined, undefined, true);
            const response_user = await Api.get(body_instance.userReference, undefined, undefined, true);
            const body_asset = await response_asset.json();
            const body_reviews = await response_reviews.json();
            const body_location = await response_location.json();
            const body_user = await response_user.json();

            return {
                title: body_asset.title,
                author: body_asset.author,
                physicalCondition: body_instance.physicalCondition,
                language: body_asset.language,
                isbn: body_asset.isbn,
                image: body_instance.imageReference,
                description: body_instance.description,
                userName: body_user.userName,
                userImage: body_user.image,
                location: body_location,
                reviews: body_reviews,
            };
        }catch (e){
            console.log("error");
            return null;
        }
    }

    return {
        handleAllAssetInstances,
        handleAssetInstance
    };
}

export default useAssetInstance;