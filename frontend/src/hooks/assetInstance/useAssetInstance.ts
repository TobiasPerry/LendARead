import { api, api_ } from "../api/api.ts";
import { Simulate } from "react-dom/test-utils";
import canPlayThrough = Simulate.canPlayThrough;
import Vnd from "../api/types.ts";
import types from "../api/types.ts";
import { AssetInstanceApi } from "./useUserAssetInstances.ts";

export const getErrorMsg = (response) => {

    if (response.headers["content-type"] === Vnd.VND_VALIDATION_ERROR) {

        let toReturn = ""
        response.data.errors.forEach((error) => {
            toReturn += `${error.field}: ${error.message}\n`
        })
        return toReturn
    }
    else {
        return response.message
    }
}

export const extractTotalPages = (linkHeader) => {
    if (!linkHeader) {
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
    userId: number;
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

    const handleGetLanguages = async (isUsed: boolean = true, pageSize: number = 20) => {
        let base_url = `/languages?itemsPerPage=${pageSize}`
        if (isUsed) {
            base_url += `&isUsed=true`
        }

        let currentPage = 1
        const languages: language[] = []
        const data = await api.get(base_url + `&page=${currentPage}`)

        // Read the amount of pages
        const pages = extractTotalPages(data.headers["link"])

        const body: language[] = data.data
        languages.push(...body)
        currentPage++

        while (currentPage <= pages) {
            const page_url = base_url + `&page=${currentPage}`
            const data_page = await api.get(page_url)
            const body_page: language[] = data_page.data
            languages.push(...body_page)
            currentPage++
        }

        languages.sort((a, b) => {
            const a_to_lower = a.name.toLowerCase()
            const b_to_lower = b.name.toLowerCase()
            if (a_to_lower < b_to_lower) {
                return -1
            } else if (a_to_lower > b_to_lower) {
                return 1
            }
            return 0
        })

        return languages
    }

    const handleAllAssetInstances = async (page, itemsPerPage, sort, sortDirection, search, languages: string[], physicalConditions: string[], minRating) => {

        // 1) Get all data for the request
        let url = `/assetInstances?page=${page}&itemsPerPage=${itemsPerPage}&sort=${sort}&sortDirection=${sortDirection}&minRating=${minRating}`
        languages.forEach((l) => { url += `&languages=${l}` })
        physicalConditions.forEach((p) => { url += `&physicalConditions=${p}` })
        if (search !== "") {
            url += `&search=${search}`
        }

        // 2) Await the request of the assetInstances
        const response = await api.get(url)
        if (!(response.status >= 200 && response.status < 500)) {
            console.error("Error getting assetInstances: ", response)
            return null;
        }
        const pages = extractTotalPages(response.headers["link"])
        if (response.status === 204) {
            return { books: [], pages };
        }
        const body = response.data

        // 3) For each assetInstance, build the requests it needs
        const booksPromises = []
        const locations: any = {}
        const assets: any = {}
        const users: any = {}
        body.forEach((assetInstance: any) => {
            let asset = null
            if (assetInstance.assetReference in assets) {
                asset = assets[assetInstance.assetReference]
            } else {
                asset = api_.get(assetInstance.assetReference)
                assets[assetInstance.assetReference] = asset
            }

            let loc = null
            if (assetInstance.locationReference in locations) {
                loc = locations[assetInstance.locationReference]
            } else {
                loc = api_.get(assetInstance.locationReference)
                locations[assetInstance.locationReference] = loc
            }

            let user = null
            if (assetInstance.userReference in users) {
                user = users[assetInstance.userReference]
            } else {
                user = api_.get(assetInstance.userReference)
                users[assetInstance.userReference] = user
            }
            const insidePromises = Promise.all([
                assetInstance,
                asset,
                loc,
                user
            ])
            booksPromises.push(insidePromises)
        })

        // 4) Make the pool of requests and call them all
        const books = []
        const results = await Promise.all(booksPromises)
        // 5) Build the books and pages
        for (const bookPromise of results) {
            const bookData = await bookPromise
            const assetInstance = bookData[0]
            let body_asset = { title: "", author: "", language: "" }
            if (bookData[1].status === 200) {
                body_asset = bookData[1].data
            }

            let body_location = { country: "", province: "", locality: "" }
            if (bookData[2].status === 200) {
                body_location = bookData[2].data
            }

            let body_user = { image: "", userName: "", id: "" }
            if (bookData[3].status === 200) {
                body_user = bookData[3].data
            }

            const book = {
                title: body_asset.title,
                author: body_asset.author,
                language: body_asset.language,
                assetInstanceNumber: assetInstance.id,
                image: assetInstance.imageReference,
                physicalCondition: assetInstance.physicalCondition,
                userImage: body_user.image,
                userName: body_user.userName,
                userNum: body_user.id,
                country: body_location.country,
                province: body_location.province,
                locality: body_location.locality
            };

            books.push(book)
        }

        return { books, pages }
    }

    const handleAssetInstance = async (assetInstanceNumber, reviewsCount): Promise<AssetData> => {
        try {
            const response_instance = await api.get(`/assetInstances/${assetInstanceNumber}`);
            const body_instance = response_instance.data
            const response_asset = await api_.get(body_instance.assetReference, undefined)
            const response_reviews = await api_.get(body_instance.reviewsReference + `?itemsPerPage=${reviewsCount}`, undefined);
            const response_location = await api_.get(body_instance.locationReference, undefined);
            const response_user = await api_.get(body_instance.userReference, undefined);
            const body_asset = response_asset.data
            const body_reviews = response_reviews.data
            const body_location = response_location.data
            const body_user = response_user.data
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
                userId: body_user.id,
                rating_assetInstance: body_instance.rating.toFixed(1),
                rating_as_lender: body_user.ratingAsLender.toFixed(1),
                reviews: body_reviews,
                description: body_instance.description,
                reservable: body_instance.reservable,
                maxLendingDays: body_instance.maxLendingDays
            };
        } catch (e) {
            return null;
        }
    }

    const handleGetReservedDays = async (assetInstanceId) => {
        try {

            const currentDate = new Date();
            const year = currentDate.getFullYear();
            const month = String(currentDate.getMonth() + 1).padStart(2, '0');
            const day = String(currentDate.getDate()).padStart(2, '0');
            const url = `/lendings?assetInstanceId=${assetInstanceId}&state=ACTIVE&state=DELIVERED&state=REJECTED&endAfter=${year}-${month}-${day}&itemsPerPage=20`;
            const res = await api.get(url);

            const pages = extractTotalPages(res.headers["link"]);

            const fetchPage = async (page) => {
                const response = await api.get(`${url}&page=${page}`);
                return response.data;
            };

            const promises = [];
            for (let currentPage = 2; currentPage <= pages; currentPage++) {
                promises.push(fetchPage(currentPage));
            }

            const additionalPagesData = await Promise.all(promises);

            const body = res.data.concat(...additionalPagesData);

            const reservedDays = body.flatMap((value) => {
                const [year_s, month_s, day_s] = value.lendDate.split('-').map(Number);
                const [year_e, month_e, day_e] = value.devolutionDate.split('-').map(Number);
                return [{ start: new Date(year_s, month_s - 1, day_s), end: new Date(year_e, month_e - 1, day_e) }];
            });

            return reservedDays;
        } catch (e) {
            return null;
        }
    };


    const handleSendLendingRequest = async (body) => {
        try {
            const res = await api.post(
                '/lendings',
                body,
                {
                    headers: {
                        "Content-Type": types.VND_ASSET_INSTANCE_LENDING
                    }
                }
            )
            const body_res = res.data
            const lendingId = body_res.id
            return {
                lendingId: lendingId,
                error: false,
                errorMessage: ""
            }
        } catch (e) {
            console.error("Error: " + e.response.status);
            const errorMsg = getErrorMsg(e.response);
            return {
                errorMessage: errorMsg,
                error: true,
                lendingId: -1
            };
        }
    }

    const uploadAssetInstanceImage = async (imageFile: any): Promise<number> => {
        const responseImage = await api.post("/images", { image: imageFile }, { headers: { "Content-type": "multipart/form-data" } })
        if (responseImage.status !== 201) {
            console.error("Error uploading image: ", responseImage)
            return -1;
        }
        return parseInt(responseImage.headers.location.split('/').pop());
    }

    const uploadAssetInstance = async (assetInstance: any): Promise<number> => {
        const response = await api.post("/assetInstances", assetInstance, { headers: { "Content-type": "application/vnd.assetInstance.v1+json" } })
        if (response.status !== 201) {
            console.error("Error uploading assetInstance: ", response)
            return -1;
        }
        const assetInstanceId = parseInt(response.data.selfUrl.split('/').pop())
        return assetInstanceId;
    }

    return {
        handleAllAssetInstances,
        handleAssetInstance,
        handleGetLanguages,
        handleSendLendingRequest,
        handleGetReservedDays,
        uploadAssetInstanceImage,
        uploadAssetInstance
    };
}

export default useAssetInstance;
