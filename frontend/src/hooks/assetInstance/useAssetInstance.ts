import {api, api_} from "../api/api.ts";
import {Simulate} from "react-dom/test-utils";
import canPlayThrough = Simulate.canPlayThrough;

export const extractTotalPages = (linkHeader) => {
    if(!linkHeader){
        return 1;
    }

    const links = linkHeader.split(',').map(a => a.split(';'));
    const lastLink = links.find(link => link[1].includes('rel="last"'));
    if (lastLink) {
        const lastPageUrl = lastLink[0].trim().slice(1, -1);
        const urlParams = new URLSearchParams(lastPageUrl);
        const lastPage = urlParams.get('page');
        return parseInt(lastPage, 10);
    }
    return 1;
};


export interface language {
    code: string;
    name: string;
}

// Define the interface for the data object
export interface AssetData {
    title: string;
    author: string;
    language: language;
    image: string;
    physicalCondition: string;
    userImage: string;
    userName: string;
    isbn: string;
    location: {
        zipcode: string;
        locality: string;
        province: string;
        country: string;
    },
    reviews: any;
    rating_assetInstance: number;
    rating_as_lender: number;
    description: string;
    reservable: boolean;
    maxLendingDays: number;
    // Add other properties as needed
}


const useAssetInstance = () => {

    const handleGetLanguages = async (isUsed : boolean  = true, pageSize : number = 20) => {
        let base_url = `/languages?itemsPerPage=${pageSize}`
        if(isUsed){
            base_url += `&isUsed`
        }

        let currentPage = 1
        const languages : language[] = []
        const data = await api.get(base_url + `&page=${currentPage}`)
        // Read the amount of pages
        const pages = extractTotalPages(data.headers["link"])

        const body : language[] = data.data
        languages.push(...body)
        currentPage++

        do{
            const page_url = base_url + `&page=${currentPage}`
            const data_page = await api.get(page_url)
            const body_page : language[] = data_page.data
            languages.push(...body_page)
            currentPage++
        }while (currentPage <= pages);

        languages.sort((a, b) => {
            const a_to_lower = a.name.toLowerCase()
            const b_to_lower = b.name.toLowerCase()
            if(a_to_lower < b_to_lower){
                return -1
            }else if(a_to_lower > b_to_lower){
                return 1
            }
            return 0
        })

        return languages
    }

    const handleAllAssetInstances = async (page, itemsPerPage, sort, sortDirection, search, languages: string[], physicalConditions: string[], minRating) => {

        try {
            let url = `/assetInstances?page=${page}&itemsPerPage=${itemsPerPage}&sort=${sort}&sortDirection=${sortDirection}&minRating=${minRating}`
            languages.forEach((l) => {url += `&languages=${l}`})
            physicalConditions.forEach((p) => {url += `&physicalConditions=${p}`})
            if (search !== "") {
                url += `&search=${search}`
            }

            const data = await api.get(url)
            const pages = extractTotalPages(data.headers["link"])
            const body = data.data

            // Array para almacenar todas las promesas de las llamadas en paralelo
            const promises = body.map(async (assetInstance) => {
                // Usar Promise.all() para hacer llamadas en paralelo
                const [response_asset,  response_location, response_user] = await Promise.all([
                    api_.get(assetInstance.assetReference),
                    api_.get(assetInstance.locationReference),
                    api_.get(assetInstance.userReference)
                ]);

                const [body_asset, body_location, body_user] = [
                    response_asset.data,
                    response_location.data,
                    response_user.data
                ];

                const tmp = assetInstance.selfUrl.match(/\/(\d+)$/);
                const num = tmp ? parseInt(tmp[1], 10) : null;
                const tmp_user = body_user.selfUrl.match(/\/(\d+)$/);
                const num_user = tmp_user ? parseInt(tmp_user[1], 10) : null;

                const book = {
                    title: body_asset.title,
                    author: body_asset.author,
                    language: body_asset.language,
                    assetInstanceNumber: num,
                    image: assetInstance.imageReference,
                    physicalCondition: assetInstance.physicalCondition,
                    userImage: body_user.image,
                    userName: body_user.userName,
                    userNum: num_user,
                    country: body_location.country,
                    province: body_location.province,
                    locality: body_location.locality
                };

                return book;
            });

            // Esperar a que todas las promesas se resuelvan
            const books = await Promise.all(promises);

            return { books, pages };
        } catch (error) {
            console.log("Error:", error);
            return null;
        }

    }

    const handleAssetInstance = async (assetInstanceNumber, reviewsCount): Promise<AssetData> => {
        try{
            const response_instance = await api.get(`/assetInstances/${assetInstanceNumber}`);
            const body_instance =  response_instance.data
            const response_asset = await api_.get(body_instance.assetReference, undefined)
            const response_reviews = await api_.get(body_instance.reviewsReference + `?itemsPerPage=${reviewsCount}`, undefined);
            const response_location = await api_.get(body_instance.locationReference, undefined);
            const response_user = await api_.get(body_instance.userReference, undefined);
            const body_asset = response_asset.data
            const body_reviews = response_reviews.data
            const body_location =  response_location.data
            const body_user =  response_user.data
            const response_language = await api_.get(body_asset.language, undefined)
            const body_language = response_language.data

            return {
                author: body_asset.author,
                image: body_instance.imageReference,
                isbn: body_asset.isbn,
                language: body_language,
                location: body_location,
                physicalCondition: body_instance.physicalCondition,
                title: body_asset.title,
                userImage: body_user.image,
                userName: body_user.userName,
                rating_assetInstance: body_instance.rating.toFixed(1),
                rating_as_lender: body_user.ratingAsLender.toFixed(1),
                reviews: body_reviews,
                description: body_instance.description,
                reservable: body_instance.reservable,
                maxLendingDays: body_instance.maxLendingDays
            };
        }catch (e){
            console.log("error");
            return null;
        }
    }

    const handleGetReservedDays = async (assetInstanceId) => {
        try{
            const currentDate = new Date();
            const year = currentDate.getFullYear();
            const month = String(currentDate.getMonth() + 1).padStart(2, '0');
            const day = String(currentDate.getDate()).padStart(2, '0');
            const res = await api.get(
                `/lendings?assetInstanceId=${assetInstanceId}&state=ACTIVE&state=DELIVERED&state=REJECTED&endAfter=${year}-${month}-${day}`
            )
            const body = res.data
            const reservedDays = []
            body.forEach((value) => {
                const [year_s, month_s, day_s] = value.lendDate.split('-').map(Number)
                const [year_e, month_e, day_e] = value.devolutionDate.split('-').map(Number)
                reservedDays.push({start: new Date(year_s, month_s - 1, day_s), end: new Date(year_e, month_e - 1, day_e)})
            })
            return reservedDays
        }catch (e){
            return null;
        }
    }

    const handleSendLendingRequest = async (body) => {
        try{
            const res = await api.post(
                '/lendings',
                body,
                {
                    headers:{
                        "Content-Type": "application/vnd.assetInstanceLending.v1+json"
                    }
                }
            )
            return res
        }catch (e){
            console.error("Error: " + e);
            return null;
        }
    }

    return {
        handleAllAssetInstances,
        handleAssetInstance,
        handleGetLanguages,
        handleSendLendingRequest,
        handleGetReservedDays
    };
}

export default useAssetInstance;
