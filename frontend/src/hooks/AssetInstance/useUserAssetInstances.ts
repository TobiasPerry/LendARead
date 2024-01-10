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

const useUserAssetInstances = (initialSort = { column: 'title', order: 'asc' }) => {
    const {user} = useContext(AuthContext)
    const [filter, setFilter] = useState('all');
    const [sort, setSort] = useState(initialSort);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10); // Or whatever default you prefer
    const [totalPages, setTotalPages] = useState(20); // To be set when data is fetched
    const [books, setBooks] = useState([])


    const applyFilterAndSort =  async (newPage: number, newSort: any, newFilter: string, books: any) => {
        const assetinstances = await api.get(`/assetInstances?userId=${user}`)
        console.log('assetinstance', assetinstances.data)

        const booksRetrieved = []
        for (const assetinstance of assetinstances.data) {
           const asset: Asset = await api_.get(assetinstance.assetReference).data
            console.log('asset', asset)
            booksRetrieved.push({
                title: asset.title,

            })
        }

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