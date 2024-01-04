import  { useState } from 'react';
import { useTranslation } from 'react-i18next';
import useAssetInstances from '../../hooks/AssetInstance/useUserAssetInstances.ts'; // Path to your custom hook

const MyBooksTable = () => {
    const { t } = useTranslation();
    const [books, setBooks] = useState([]); // This will be your books data
    const { setFilter, filter, applyFilterAndSort, sort, setSort } = useAssetInstances(setBooks);

    const handleFilterChange = (newFilter: string) => {
        setFilter(newFilter);
        applyFilterAndSort(sort, newFilter, books);
    };

    const handleSortChange = (column: string) => {
        const isAsc = sort.column === column && sort.order === 'asc';
        const newSort = { column, order: isAsc ? 'desc' : 'asc' }
        setSort(newSort);
        applyFilterAndSort(newSort, filter, books);
    };

    const renderSortIcon = (column: string) => {
        return sort.column === column ? (sort.order === 'asc' ? '↑' : '↓') : '';
    };

    return (
        <div className="container mt-3">
            <h2>{t('borrowed_books')}</h2>
            <div className="btn-group mb-3" role="group">
                <button onClick={() => handleFilterChange('all')} className="btn btn-outline-primary">{t('all')}</button>
                <button onClick={() => handleFilterChange('pending')} className="btn btn-outline-primary">{t('pending')}</button>
                <button onClick={() => handleFilterChange('delivered')} className="btn btn-outline-primary">{t('delivered')}</button>
                <button onClick={() => handleFilterChange('overdue')} className="btn btn-outline-primary">{t('overdue')}</button>
                <button onClick={() => handleFilterChange('rejected')} className="btn btn-outline-primary">{t('rejected')}</button>
                <button onClick={() => handleFilterChange('finished')} className="btn btn-outline-primary">{t('finished')}</button>
            </div>
            <table className="table table-hover">
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
                        <td colSpan="5" className="text-center">{t('no_books_available')}</td>
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
            <nav aria-label="Page navigation example">
                <ul className="pagination">
                    <li className="page-item"><a className="page-link" href="#">{t('previous')}</a></li>
                    {/* Add page numbers here */}
                    <li className="page-item"><a className="page-link" href="#">{t('next')}</a></li>
                </ul>
            </nav>
        </div>
    );
};

export default MyBooksTable;
