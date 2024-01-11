import { useTranslation } from 'react-i18next';
import useAssetInstances from '../../hooks/assetInstance/useUserAssetInstances.ts';
import {useContext, useEffect} from "react";
import {AuthContext} from "../../contexts/authContext.tsx"; // Path to your custom hook

const MyBooksTable = () => {
    const { t } = useTranslation();
    const { setFilter, filter, applyFilterAndSort, sort, setSort, currentPage, changePage, totalPages, books} = useAssetInstances();

    useEffect(() => {
        applyFilterAndSort(currentPage, sort, filter)
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
        if(filter === filter_)
            return {
                backgroundColor:  "#7f8d7f",
                color: 'white',
                border: 'gray',
                fontWeight: 'bold'
            }
        else
            return {
            backgroundColor:  '#d2e1d2',
            color: 'white',
            border: 'gray',
            fontWeight: 'bold'
        }

    }

    const paginationStyle = {
        color: "gray",
        border: 'none',
        borderRadius: '20px'
    }

    return (
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
                        <td className="text-center">{t('no_books_available')}</td>
                    </tr>
                ) : (
                    books.map((book, index) => (
                        <tr key={index}>
                            <td>
                                <img style={{height: '125px', width: '75px', objectFit: 'cover'}} src={book.imageUrl} alt={book.title}/>
                            </td>
                            <td>{book.title}</td>
                            <td>{book.author}</td>
                            <td>{book.language}</td>
                            <td>{t(`${book.state}`)}</td>
                        </tr>
                    ))
                )}
                </tbody>
            </table>
                <nav aria-label="Page navigation example" className="d-flex justify-content-center">
                    <ul className="pagination">
                        {currentPage > 1 && (
                            <li className="page-item">
                                <a className="page-link"   style={paginationStyle} onClick={() => changePage(currentPage - 1)}>{t('previous')}</a>
                            </li>
                        )}
                        <li className="page-item">
                            <span className="page-link"  style={paginationStyle}>{`${currentPage} / ${totalPages}`}</span>
                        </li>
                        {currentPage < totalPages && (
                            <li className="page-item">
                                <a className="page-link"  style={paginationStyle} onClick={() => changePage(currentPage + 1)}>{t('next')}</a>
                            </li>
                        )}
                    </ul>
                </nav>
        </div>
    );
};

export default MyBooksTable;
