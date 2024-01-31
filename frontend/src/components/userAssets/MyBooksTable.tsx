import { useTranslation } from 'react-i18next';
import useAssetInstances from '../../hooks/assetInstance/useUserAssetInstances.ts';
import {useContext, useEffect} from "react";
import LoadingAnimationWhite from "../LoadingAnimationWhite.tsx";
import Pagination from "../Pagination.tsx";
import {Helmet} from "react-helmet";

const MyBooksTable = ({handleRowClicked}) => {
    const { t } = useTranslation();
    const {
        setFilter,
        filter,
        applyFilterAndSort,
        sort,
        setSort,
        currentPage,
        changePageMyBooks,
        totalPages,
        books,
        isLoading
    } = useAssetInstances();



    useEffect(() => {
        applyFilterAndSort(currentPage, sort, filter).then( )
    }, [])


    const handleFilterChange = async(newFilter: string) => {
        setFilter(newFilter);
        await applyFilterAndSort(currentPage, sort, newFilter);
    };

    const handleSortChange = async (column: string) => {
        const isAsc = sort.column === column && sort.order === 'ASCENDING';
        const newSort = { column, order: isAsc ? 'DESCENDING' : 'ASCENDING' }
        setSort(newSort);
        await applyFilterAndSort(currentPage, newSort, filter);
    };

    const renderSortIcon = (column: string) => {
        return sort.column === column ? (sort.order === 'ASCENDING' ? '↑' : '↓') : '';
    };

    const buttonStyle = (filter_: string) => {
        return {
                backgroundColor: filter === filter_ ? "#7f8d7f" : '#d2e1d2',
                color: 'white',
                border: 'gray',
                fontWeight: 'bold'
            }
    }



    return (
        <>
            <Helmet>
                <meta charSet="utf-8" />
                <title>My Books Table</title>
                <link rel="canonical" href="http://mysite.com/example" />
            </Helmet>
            {isLoading ?
                <LoadingAnimationWhite /> :
        <div className="container">
            <div className="d-flex justify-content-between align-items-center">
                <h2 className="m-1">{t('my_books')}</h2>
                <div className="btn-group">
                <button style={buttonStyle('all')} onClick={() => handleFilterChange('all')} className="btn btn-outline-primary">{t('all')}</button>
                <button style={buttonStyle('private')}  onClick={() => handleFilterChange('private')} className="btn btn-outline-primary">{t('private')}</button>
                <button style={buttonStyle('public')}  onClick={() => handleFilterChange('public')} className="btn btn-outline-primary">{t('public')}</button>
            </div>
            </div>
            <table className="table table-hover mt-2 mb-3">
                <thead className="table-light">
                <tr>
                    <th scope="col" onClick={() => handleSortChange('image')}>{t('image')} {renderSortIcon('image')}</th>
                    <th scope="col" onClick={() => handleSortChange('title')}>{t('title')} {renderSortIcon('title')}</th>
                    <th scope="col" onClick={() => handleSortChange('author')}>{t('author')} {renderSortIcon('author')}</th>
                    <th scope="col" onClick={() => handleSortChange('language')}>{t('language')} {renderSortIcon('language')}</th>
                    <th scope="col" onClick={() => handleSortChange('state')}>{t('state')} {renderSortIcon('state')}</th>
                </tr>
                </thead>
                <tbody>
                {books.length === 0 ? (
                    <tr>
                        <td colSpan={5} className="text-center">
                            <h5>{t('no_books_available')}</h5>
                        </td>
                    </tr>
                ) : (
                    books.map((book, index) => (
                        <tr key={index} onClick={() => handleRowClicked(book, "owned")} style={{ cursor: "pointer" }}>
                            <td>
                                <img style={{ height: '125px', width: '75px', objectFit: 'cover' }} src={book.imageUrl} alt={book.title} />
                            </td>
                            <td>{book.title}</td>
                            <td>{book.author}</td>
                            <td>{book.language}</td>
                            <td>{t(`${book.physicalCondition}`)}</td>
                        </tr>
                    ))
                )}
                </tbody>
            </table>
           <Pagination totalPages={totalPages} changePage={changePageMyBooks} currentPage={currentPage} />
        </div> }
        </>
    );
};

export default MyBooksTable;
