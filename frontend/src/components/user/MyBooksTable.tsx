import { useTranslation } from 'react-i18next';
import useAssetInstances from '../../hooks/AssetInstance/useUserAssetInstances.ts';
import {useContext, useEffect} from "react";
import {AuthContext} from "../../contexts/authContext.tsx"; // Path to your custom hook

const MyBooksTable = () => {
    const { t } = useTranslation();
    const { setFilter, filter, applyFilterAndSort, sort, setSort, currentPage, changePage, totalPages, books} = useAssetInstances();

    useEffect(() => {
        applyFilterAndSort(currentPage, sort, filter, books)
    }, [])

    const handleFilterChange = async(newFilter: string) => {
        setFilter(newFilter);
        await applyFilterAndSort(currentPage, sort, newFilter, books);
    };

    const handleSortChange = async (column: string) => {
        const isAsc = sort.column === column && sort.order === 'asc';
        const newSort = { column, order: isAsc ? 'desc' : 'asc' }
        setSort(newSort);
        await applyFilterAndSort(currentPage, newSort, filter, books);
    };

    const renderSortIcon = (column: string) => {
        return sort.column === column ? (sort.order === 'asc' ? '↑' : '↓') : '';
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
        <div className="container mt-3">
            <div className="d-flex justify-content-between align-items-center">
                <h2 className="m-1">{t('borrowed_books')}</h2>
                <div className="btn-group">
                <button style={buttonStyle('all')} onClick={() => handleFilterChange('all')} className="btn btn-outline-primary">{t('all')}</button>
                <button style={buttonStyle('pending')}  onClick={() => handleFilterChange('pending')} className="btn btn-outline-primary">{t('pending')}</button>
                <button style={buttonStyle('delivered')}  onClick={() => handleFilterChange('delivered')} className="btn btn-outline-primary">{t('delivered')}</button>
                <button style={buttonStyle('overdue')}  onClick={() => handleFilterChange('overdue')} className="btn btn-outline-primary">{t('overdue')}</button>
                <button style={buttonStyle('rejected')}  onClick={() => handleFilterChange('rejected')} className="btn btn-outline-primary">{t('rejected')}</button>
                <button style={buttonStyle('finished')}  onClick={() => handleFilterChange('finished')} className="btn btn-outline-primary">{t('finished')}</button>
            </div>
            </div>
            <table className="table table-hover mt-2 mb-3">
                <thead className="table-light">
                <tr>
                    <th scope="col" onClick={() => handleSortChange('image')}>{t('image')} {renderSortIcon('image')}</th>
                    <th scope="col" onClick={() => handleSortChange('title')}>{t('title')} {renderSortIcon('title')}</th>
                    <th scope="col" onClick={() => handleSortChange('startDate')}>{t('start_date')} {renderSortIcon('startDate')}</th>
                    <th scope="col" onClick={() => handleSortChange('returnDate')}>{t('return_date')} {renderSortIcon('returnDate')}</th>
                    <th scope="col" onClick={() => handleSortChange('user')}>{t('user')} {renderSortIcon('user')}</th>
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
                            <td>{/* Place an image here */}</td>
                            <td>{book.title}</td>
                            <td>{book.startDate}</td>
                            <td>{book.returnDate}</td>
                            <td>{book.user}</td>
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
