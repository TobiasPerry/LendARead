
import {Api} from "../api/api.ts";

const useAssetInstance = () => {

    const handleAllAssetInstances = async (page = 1, itemsPerPage = 12) => {
        const books = []

        //     "description": "",
        //     "locationReference": "http://localhost:8080/api/locations/1",
        //     "maxLendingDays": 31,
        //     "rating": 0,
        //     "reservable": true,
        //     "reviewsReference": "http://localhost:8080/api/assetInstances/2/reviews",
        //     "selfUrl": "http://localhost:8080/api/assetInstances/2",
        //     "status": "PUBLIC",
        //     "userReference": "http://localhost:8080/api/users/1"
        // },

        //     "imageReference": "http://localhost:8080/api/assetInstances/2/image",
        //     "assetReference": "http://localhost:8080/api/assets/2",
        //     "physicalCondition": "BINDINGCOPY",
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
    return {
        handleAllAssetInstances
    };
}

export default useAssetInstance;