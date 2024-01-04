// useAssetInstances.js
import { useState } from 'react';

const useUserAssetInstances = (setBooks: any, initialSort = { column: 'title', order: 'asc' }) => {
    const [filter, setFilter] = useState('all');
    const [sort, setSort] = useState(initialSort);

    const applyFilterAndSort = (newSort: any, newFilter: string, books: any) => {
        console.log(newSort, newFilter)
    };

    return { setFilter, filter, applyFilterAndSort, sort, setSort };
};

export default useUserAssetInstances;
