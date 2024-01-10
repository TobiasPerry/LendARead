// useAssetInstances.js
import {useContext, useState} from 'react';
import {api, api_} from "../api/api.ts";
import authContext, {AuthContext} from "../../contexts/authContext.tsx";

export interface AssetApi {
    author: string,
    isbn: string,
    language: string,
    selfUrl: string,
    title: string
}

export interface LendingApi {
    assetInstance: string,
    devolutionDate: string,
    lendDate: string,
    selfUrl: string,
    state: string,
    userReference: string,
    userReviews: Array<any>
}

export interface AssetInstanceApi {
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

   const fetchLendings =  async (newPage: number, newSort: any, newFilter: string, isLender: boolean) => {

       const queryparams = {
           'page': newPage,
           'itemsPerPage': PAGE_SIZE,
       }

       if(isLender)
           queryparams['lenderId'] = user
       else
           queryparams['borrowerId'] = user

       const lendings = await api.get(`/lendings`,
           {
               params: queryparams
           })

       console.log('lendings',lendings.data)
   }


    const fetchMyBooks =  async (newPage: number, newSort: any, newFilter: string) => {

        const assetinstances = await api.get(`/assetInstances?userId=${user}`,
            {
            params: {
                'page': newPage,
                'itemsPerPage': PAGE_SIZE,
                'sort': sortColumnsApi[`${newSort.column}`] === undefined ? "" : sortColumnsApi[`${newSort.column}`],
                'sortDirection': newSort.order,
            }
        })

        setTotalPages(extractTotalPages(assetinstances.headers["link"]))

        const booksRetrieved = []
        for (const assetinstance of assetinstances.data) {
            const assetResponse = await api_.get(assetinstance.assetReference)
            const asset: AssetApi = assetResponse.data

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

        setBooks(booksRetrieved)
    };

    // Function to change the page
    const changePage = (newPage: number) => {
        setCurrentPage(newPage);
        // Re-apply filter and sort whenever page changes
        fetchMyBooks(newPage, sort, filter, books);
    };


    return { setFilter, filter, applyFilterAndSort: fetchMyBooks, sort, setSort, currentPage, changePage, totalPages, books, setBooks, fetchLendings};
};

export default useUserAssetInstances;