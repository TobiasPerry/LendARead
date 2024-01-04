import { useTranslation } from 'react-i18next';
import useAssetInstances from '../../hooks/AssetInstance/useUserAssetInstances.ts'; // Path to your custom hook

const MyBooksTable = () => {
    const { t } = useTranslation();
    const { setFilter, filter, applyFilterAndSort, sort, setSort, currentPage, changePage, totalPages, books,setBooks } = useAssetInstances();

    const handleFilterChange = (newFilter: string) => {
        setFilter(newFilter);
        applyFilterAndSort(currentPage, sort, newFilter, books);
    };

    const handleSortChange = (column: string) => {
        const isAsc = sort.column === column && sort.order === 'asc';
        const newSort = { column, order: isAsc ? 'desc' : 'asc' }
        setSort(newSort);
        applyFilterAndSort(currentPage, newSort, filter, books);
    };

    const renderSortIcon = (column: string) => {
        return sort.column === column ? (sort.order === 'asc' ? '↑' : '↓') : '';
    };

    return (
        <div className="container mt-3">
            <div className="d-flex justify-content-between align-items-center">
                <h2>{t('borrowed_books')}</h2>
                <div className="btn-group">
                <button onClick={() => handleFilterChange('all')} className="btn btn-outline-primary">{t('all')}</button>
                <button onClick={() => handleFilterChange('pending')} className="btn btn-outline-primary">{t('pending')}</button>
                <button onClick={() => handleFilterChange('delivered')} className="btn btn-outline-primary">{t('delivered')}</button>
                <button onClick={() => handleFilterChange('overdue')} className="btn btn-outline-primary">{t('overdue')}</button>
                <button onClick={() => handleFilterChange('rejected')} className="btn btn-outline-primary">{t('rejected')}</button>
                <button onClick={() => handleFilterChange('finished')} className="btn btn-outline-primary">{t('finished')}</button>
            </div>
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
                <nav aria-label="Page navigation example" className="d-flex justify-content-center">
                    <ul className="pagination">
                        {currentPage > 1 && (
                            <li className="page-item">
                                <a className="page-link" onClick={() => changePage(currentPage - 1)}>{t('previous')}</a>
                            </li>
                        )}
                        <li className="page-item">
                            <span className="page-link">{`${currentPage} / ${totalPages}`}</span>
                        </li>
                        {currentPage < totalPages && (
                            <li className="page-item">
                                <a className="page-link" onClick={() => changePage(currentPage + 1)}>{t('next')}</a>
                            </li>
                        )}
                    </ul>
                </nav>
        </div>
    );
};

export default MyBooksTable;
