import {api, api_} from "../api/api.ts";
import {Simulate} from "react-dom/test-utils";

const useAssetInstance = () => {

    const handleAllAssetInstances = async (page, itemsPerPage, sort, sortDirection, search) => {
        const books = []

        try {
            let url = `/assetInstances?page=${page}&itemsPerPage=${itemsPerPage}&sort=${sort}&sortDirection=${sortDirection}`
            if(search !== ""){
                url += `&search=${search}`
            }
            console.log(url)
            const data = await api.get(url)

            const body =  data.data


            for (const assetInstance of body) {
                const response_asset = await api_.get(assetInstance.assetReference)
                const response_instance = await api_.get(assetInstance.selfUrl)
                const response_location = await api_.get(assetInstance.locationReference)
                const response_user = await api_.get(assetInstance.userReference)
                const body_asset =  response_asset.data
                const body_instance =  response_instance.data
                const body_location =  response_location.data
                const body_user =  response_user.data

                const tmp = assetInstance.selfUrl.match(/\/(\d+)$/);
                const num = tmp ? parseInt(tmp[1], 10) : null
                // // Check if there's a match, and return the captured number
                //return num ? parseInt(num[1], 10) : null;

                const book = {
                    title: body_asset.title,
                    author: body_asset.author,
                    language: body_asset.language,
                    assetInstanceNumber: num,
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
            const response_instance = await api.get(`/assetInstances/${assetInstanceNumber}`);
            const body_instance =  response_instance.data
            const response_asset = await api_.get(body_instance.assetReference, undefined)
            const response_reviews = await api_.get(body_instance.reviewsReference, undefined);
            const response_location = await api_.get(body_instance.locationReference, undefined);
            const response_user = await api_.get(body_instance.userReference, undefined);
            const body_asset = response_asset.data
            const body_reviews = response_reviews.data
            const body_location =  response_location.data
            const body_user =  response_user.data

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