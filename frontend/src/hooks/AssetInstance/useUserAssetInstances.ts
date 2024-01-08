// useAssetInstances.js
import {useContext, useState} from 'react';
import {api} from "../api/api.ts";
import {AuthContext} from "../../contexts/authContext.tsx";

const useUserAssetInstances = (user, initialSort = { column: 'title', order: 'asc' }) => {
    const [filter, setFilter] = useState('all');
    const [sort, setSort] = useState(initialSort);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10); // Or whatever default you prefer
    const [totalPages, setTotalPages] = useState(20); // To be set when data is fetched
    const [books, setBooks] = useState([])


    const applyFilterAndSort =  async (newPage: number, newSort: any, newFilter: string, books: any) => {
        console.log(newSort, newFilter, newPage)
        const response = await api.get(`/assetInstances?userId=${user}`)
        //need to have some assetInstances added first, waiting for frontend of adding assetinstances to be done
        console.log(response)
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

/*

curl --location 'http://localhost:8082/api/assetInstances' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZWxsb0BnbWFpbC5jb20iLCJpYXQiOjE3MDQzOTgwNDAsImV4cCI6MTcwNTAwMjg0MCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQk9SUk9XRVIifV0sInVzZXJSZWZlcmVuY2UiOiJodHRwOi8vcGF3c2VydmVyLml0Lml0YmEuZWR1LmFyL3Bhdy0yMDIzYS0wMy9hcGkvdXNlcnMvMiJ9.rurA9hspHmDZUBmIjmdeW6MigGqRUt4_o8F4xyy8R-o' \
--header 'Accept-Language: es' \
--form 'physicalCondition="ASNEW"' \
--form 'locationId="1"' \
--form 'description="aaaaaaaaaaaaaaaaaaaaaaaaaaaaa"' \
--form 'maxDays="5"' \
--form 'isReservable="true"' \
--form 'image=@"/Users/marcoscilipoti/Downloads/Arborio (611).jpg"' \
--form 'state="PUBLIC"' \
--form 'assetId="3"'
 */