// useAssetInstances.js
import {useContext, useState} from 'react';
import {api, api_} from "../api/api.ts";
import authContext, {AuthContext} from "../../contexts/authContext.tsx";

interface Asset {
    author: string,
    isbn: string,
    language: string,
    selfUrl: string,
    title: string
}

interface AssetInstance {
    assetReference : string,
    description : string,
    imageReference: string,
    locationReference: string
    maxLendingDays: number,
    physicalCondition: string,
    rating: number,
    reservable: boolean,
    reviewsReference: string,
    selfUrl: string,
    status: string,
    userReference: string
}

const sortColumnsApi = {
    title: "TITLE_NAME",
    author: "AUTHOR_NAME",
}

const useUserAssetInstances = (initialSort = { column: 'title', order: 'ASCENDING' }) => {
    const PAGE_SIZE = 1

    const {user} = useContext(AuthContext)
    const [filter, setFilter] = useState('all');
    const [sort, setSort] = useState(initialSort);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [books, setBooks] = useState([])

    const extractTotalPages = (linkHeader) => {
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

    const applyFilterAndSort =  async (newPage: number, newSort: any, newFilter: string, books: any) => {
        const assetinstances = await api.get(`/assetInstances?userId=${user}`,
            {
            params: {
                'page': newPage,
                'itemsPerPage': PAGE_SIZE,
                'sort': sortColumnsApi[`${newSort.column}`] === undefined ? "" : sortColumnsApi[`${newSort.column}`],
                'sortDirection': newSort.order,
            }
        })
        console.log('assetinstance', assetinstances.data)

        setTotalPages(extractTotalPages(assetinstances.headers["link"]))

        const booksRetrieved = []
        for (const assetinstance of assetinstances.data) {
            const assetResponse = await api_.get(assetinstance.assetReference)
            const asset: Asset = assetResponse.data

            const languageResponse = await api_.get(asset.language)
            const lang = languageResponse.data
            booksRetrieved.push({
                title: asset.title,
                author: asset.author,
                language: lang.name,
                state: assetinstance.physicalCondition,
                imageUrl: assetinstance.imageReference
            })
        }

        console.log(booksRetrieved)
        setBooks(booksRetrieved)
    };

    // Function to change the page
    const changePage = (newPage: number) => {
        setCurrentPage(newPage);
        // Re-apply filter and sort whenever page changes
        applyFilterAndSort(newPage, sort, filter, books);
    };


    return { setFilter, filter, applyFilterAndSort, sort, setSort, currentPage, changePage, totalPages, books, setBooks};
};

export default useUserAssetInstances;