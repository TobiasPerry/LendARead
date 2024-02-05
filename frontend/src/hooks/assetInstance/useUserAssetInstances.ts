// useAssetInstances.js
import {useContext, useState} from 'react';
import {api, api_} from '../api/api.ts';
import authContext, {AuthContext} from "../../contexts/authContext.tsx";
import {extractTotalPages} from "./useAssetInstance.ts";
import {useTranslation} from "react-i18next";

export const checkFinished = (asset) => {
    return asset !== undefined && asset.lendingStatus === "FINISHED"
}

export const checkCanceled = (asset) => {
    return asset !== undefined && asset.lendingStatus === "CANCELED"
}

export const checkRejected = (asset) => {
    return asset !== undefined && asset.lendingStatus === "REJECTED"
}
export const extractId = (url: string): string => {
    const pattern = /\/(\d+)(?=\/?$)/;
    const match = url.match(pattern);
    return  match ? match[1] : "";
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
    id:number
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
    physicalCondition: "PHYSICAL_CONDITION"
}

const statusAdapterApi = {
    private: "PRIVATE",
    public: "PUBLIC",
    all: "ALL"
}

const lendingStatusAdapterApi = {
    pending: "ACTIVE",
    delivered: "DELIVERED",
    canceled: "CANCELED",
    rejected: "REJECTED",
    finished: "FINISHED"
}
const useUserAssetInstances = (initialSort = { column: 'title', order: 'DESCENDING' }) => {
    const PAGE_SIZE = 3

    const {user} = useContext(AuthContext)
    const [filter, setFilter] = useState('all');
    const [sort, setSort] = useState(initialSort);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [books, setBooks] = useState([])
    const [isLoading, setIsLoading] = useState(false)
    const [error, setError] = useState({state: false, text: ""})
    const {t} = useTranslation()

    const fetchLendings =  async (newPage: number, newSort: any, newFilter: string, isLender: boolean) => {

        setIsLoading(true)
        const queryparams = {
            'page': newPage,
            'itemsPerPage': PAGE_SIZE,
            'sortDirection': newSort.order,
        }
        setIsLoading(true)
        try {
            const queryparams = {
                'page': newPage,
                'itemsPerPage': PAGE_SIZE,
                'sortDirection': newSort.order,
            }

            if (isLender)
                queryparams['lenderId'] = user
            else
                queryparams['borrowerId'] = user

            if (lendingStatusAdapterApi[`${newFilter}`] !== undefined)
                queryparams['state'] = lendingStatusAdapterApi[`${newFilter}`]

            if (sortAdapterApi[`${newSort.column}`] !== undefined && newSort.column !== "user")
                queryparams['sort'] = sortAdapterApi[`${newSort.column}`]

            if (newSort.column === "user") {
                if (isLender)
                    queryparams['sort'] = 'LENDER_USER'
                else
                    queryparams['sort'] = 'BORROWER_USER'
            }


            const lendings = await api.get(`/lendings`, {
                params: queryparams
            })

            setTotalPages(extractTotalPages(lendings.headers["link"]))
            if (lendings.data.length == 0|| lendings.request.status === 204) {
                setIsLoading(false)
                setBooks([])
                return;
            }

            const lendedBooksPromises = lendings.data.map(async (lending: LendingApi) => {
                try {
                    const assetinstance: AssetInstanceApi = (await api_.get(lending.assetInstance)).data;
                    const asset: AssetApi = (await api_.get(assetinstance.assetReference)).data;
                    const userReference = !isLender ? lending.lenderUrl : lending.borrowerUrl;
                    const user = (await api_.get(userReference)).data;

                    return {
                        imageUrl: assetinstance.imageReference,
                        title: asset.title,
                        start_date: lending.lendDate,
                        return_date: lending.devolutionDate,
                        user: user.userName,
                        physicalCondition: assetinstance.physicalCondition,
                        id: lending.id,
                        lendingStatus: lending.state
                    };
                } catch (error) {
                    return null;
                }
            });

            const lendedBooks = await Promise.all(lendedBooksPromises);
            const validLendedBooks = lendedBooks.filter(book => book !== null);
            setIsLoading(false)
            setBooks(validLendedBooks)
        } catch (error) {
            setError({state: true, text: t("errors.failedToFetchLendings")})
            setIsLoading(false)
            setBooks([])
        }

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

        try {
            const assetinstances = await api.get(`/assetInstances`, params )

            setTotalPages(extractTotalPages(assetinstances.headers["link"]))

            if (assetinstances.request.status === 204 || assetinstances.data.length == 0) {
                setIsLoading(false)
                setBooks([]);
                return;
            }

            const booksRetrieved = await Promise.all(assetinstances.data.map(async (assetinstance) => {

                const [assetResponse] = await Promise.all([
                    api_.get(assetinstance.assetReference),
                ]);
                const languageResponse = await api_.get(assetResponse.data.language);


                const asset: AssetApi = assetResponse.data;
                const lang = languageResponse.data;

                return {
                    title: asset.title,
                    author: asset.author,
                    language: lang.name,
                    state: assetinstance.status,
                    physicalCondition: assetinstance.physicalCondition,
                    imageUrl: assetinstance.imageReference,
                    id: assetinstance.id
                };
            }));

            setBooks(booksRetrieved)
            setIsLoading(false)
        } catch (error) {
            setError({state: true, text: t("errors.failedToFetchBooks")})
            setIsLoading(false)
            setBooks([]);
        }


    };

    const changePageMyBooks = async (newPage: number) => {
        await fetchMyBooks(newPage, sort, filter);
        setCurrentPage(newPage);
    };
    const changePageLendings = async (newPage: number, isLender) => {
        await fetchLendings(newPage, sort, filter, isLender);
        setCurrentPage(newPage);
    };

    return { setFilter, filter, applyFilterAndSort: fetchMyBooks, sort, setSort, currentPage, changePageMyBooks, changePageLendings, totalPages, books, setBooks, fetchLendings, isLoading, setCurrentPage, error};
};

export default useUserAssetInstances;
