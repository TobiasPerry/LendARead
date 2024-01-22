// useAssetInstances.js
import {useContext, useState} from 'react';
import {api, api_} from "../api/api.ts";
import authContext, {AuthContext} from "../../contexts/authContext.tsx";

export const checkFinished = (asset) => {
    return asset !== undefined && asset.lendingStatus === "FINISHED"
}
export const checkRejected = (asset) => {
    return asset !== undefined && asset.lendingStatus === "REJECTED"
}
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
    lenderUrl: string,
    borrowerUrl: string
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

const sortAdapterApi = {
    title: "TITLE",
    author: "AUTHOR_NAME",
    language: "LANGUAGE",
    state: "STATE",
    start_date: "LENDDATE",
    return_date: "DEVOLUTIONDATE",
}

const statusAdapterApi = {
    private: "PRIVATE",
    public: "PUBLIC",
}

const lendingStatusAdapterApi = {
    pending: "ACTIVE",
    delivered: "DELIVERED",
    canceled: "CANCELED", //new
    rejected: "REJECTED",
    finished: "FINISHED"
}
const useUserAssetInstances = (initialSort = { column: 'title', order: 'ASCENDING' }) => {
    const PAGE_SIZE = 10

    const {user} = useContext(AuthContext)
    const [filter, setFilter] = useState('all');
    const [sort, setSort] = useState(initialSort);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [books, setBooks] = useState([])
    const [isLoading, setIsLoading] = useState(false)

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

    const extractId = (url: string) => {
        const pattern = /\/(\d+)(?=\/?$)/;
        const match = url.match(pattern);
        return  match ? match[1] : -1;
    }
   const fetchLendings =  async (newPage: number, newSort: any, newFilter: string, isLender: boolean) => {

        setIsLoading(true)
       const queryparams = {
           'page': newPage,
           'itemsPerPage': PAGE_SIZE,
           'sortDirection': newSort.order,
       }

       if(isLender)
           queryparams['lenderId'] = user
       else
           queryparams['borrowerId'] = user

       if(lendingStatusAdapterApi[`${newFilter}`] !== undefined)
           queryparams['state'] =  lendingStatusAdapterApi[`${newFilter}`]

       if(sortAdapterApi[`${newSort.column}`] !== undefined && newSort.column !== "user")
           queryparams['sort'] = sortAdapterApi[`${newSort.column}`]

       if(newSort.column === "user") {
           if(isLender)
               queryparams['sort'] = 'LENDER_USER'
           else
               queryparams['sort'] = 'BORROWER_USER'
       }


       const lendings = await api.get(`/lendings`, {
               params: queryparams
       })


       const lendedBooksPromises = lendings.data.map(async (lending: LendingApi) => {
           try {
               console.log('lending', lending);
               const assetinstance: AssetInstanceApi = (await api_.get(lending.assetInstance)).data;
               const asset: AssetApi = (await api_.get(assetinstance.assetReference)).data;
               const userReference = isLender ? lending.lenderUrl : lending.borrowerUrl;
               const user = (await api_.get(userReference)).data;

               return {
                   imageUrl: assetinstance.imageReference,
                   title: asset.title,
                   start_date: lending.lendDate,
                   return_date: lending.devolutionDate,
                   user: user.userName,
                   state: assetinstance.physicalCondition,
                   id: extractId(lending.selfUrl),
                   lendingStatus: lending.state
               };
           } catch (error) {
               console.error("Error in processing lending:", lending, error);
               return null;
           }
       });

       const lendedBooks = await Promise.all(lendedBooksPromises);
       const validLendedBooks = lendedBooks.filter(book => book !== null);


       setBooks(validLendedBooks)
        setIsLoading(false)
   }


    const fetchMyBooks =  async (newPage: number, newSort: any, newFilter: string) => {

        setIsLoading(true)
        const params = {
            params: {
                'userId': user,
                'page': newPage,
                'itemsPerPage': PAGE_SIZE,
                'sortDirection': newSort.order,
            }
        }

        if(statusAdapterApi[`${newFilter}`] !== undefined)
            params.params['status'] =  statusAdapterApi[`${newFilter}`]

        if(sortAdapterApi[`${newSort.column}`] !== undefined)
            params.params['sort'] = sortAdapterApi[`${newSort.column}`]

        const assetinstances = await api.get(`/assetInstances`, params )

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
                imageUrl: assetinstance.imageReference,
                id: extractId(assetinstance.selfUrl)
            })
        }

        setBooks(booksRetrieved)
        setIsLoading(false)
    };

    const changePage = async (newPage: number) => {
        setCurrentPage(newPage);
        await fetchMyBooks(newPage, sort, filter);
    };


    return { setFilter, filter, applyFilterAndSort: fetchMyBooks, sort, setSort, currentPage, changePage, totalPages, books, setBooks, fetchLendings, isLoading};
};

export default useUserAssetInstances;