import { useTranslation } from 'react-i18next';
import useAssetInstances from '../../hooks/assetInstance/useUserAssetInstances.ts';
import {useContext, useEffect} from "react";
import LoadingAnimationWhite from "../LoadingAnimationWhite.tsx";
import Pagination from "../Pagination.tsx";
import {Helmet} from "react-helmet";
import LoadingWrapper from "../LoadingWrapper.tsx";
import Snackbar from "../SnackBar.tsx";

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
        isLoading,
        setCurrentPage,
        error
    } = useAssetInstances();



    useEffect(() => {
        applyFilterAndSort(currentPage, sort, filter).then( )
    }, [])


    const handleFilterChange = async(newFilter: string) => {
        setFilter(newFilter);
        setCurrentPage(1);
        await applyFilterAndSort(1, sort, newFilter);
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
      <LoadingWrapper isLoading={isLoading} documentTitle={t('my_books')} isWhiteAnimation={true}>
        <div className="container">
            <div className="d-flex justify-content-between align-items-center">
                <h2 className="m-1">{t('my_books')}</h2>
                <div className="btn-group">
                <button style={buttonStyle('all')} onClick={() => handleFilterChange('all')} className="btn btn-outline-primary">{t('all')}</button>
                <button style={buttonStyle('private')}  onClick={() => handleFilterChange('private')} className="btn btn-outline-primary">{t('private')}</button>
                <button style={buttonStyle('public')}  onClick={() => handleFilterChange('public')} className="btn btn-outline-primary">{t('public')}</button>
            </div>
            </div>

            {books.length !== 0 ? (
                    <table className="table table-hover mt-2 mb-3">
                        <thead className="table-light">
                        <tr>
                            <th scope="col" onClick={() => handleSortChange('image')}>{t('image')}</th>
                            <th scope="col" onClick={() => handleSortChange('title')}>{t('title')} {renderSortIcon('title')}</th>
                            <th scope="col" onClick={() => handleSortChange('author')}>{t('author')} {renderSortIcon('author')}</th>
                            <th scope="col" onClick={() => handleSortChange('language')}>{t('language')} {renderSortIcon('language')}</th>
                            <th scope="col" onClick={() => handleSortChange('physicalCondition')}>{t('physicalCondition')} {renderSortIcon('physicalCondition')}</th>
                        </tr>
                        </thead>
                        <tbody>
                        {books.map((book, index) => (
                            <tr key={index} onClick={() => handleRowClicked(book, "owned")} style={{ cursor: "pointer" }}>
                                <td>
                                    <img style={{ height: '125px', width: '75px', objectFit: 'cover' }} src={book.imageUrl} alt={book.title} />
                                </td>
                                <td>{book.title}</td>
                                <td>{book.author}</td>
                                <td>{book.language}</td>
                                <td style={{minWidth: "200px", alignContent: "center"}}>{t(`${book.physicalCondition}`)}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                ) :
                    <div style={{
                        textAlign: "center",
                        width: "100%",
                        marginTop: "20px",
                        marginBottom: "20px",
                        fontWeight: "bold",
                    }}>
                        {t('no_books')}
                    </div>
            }
            <Pagination totalPages={totalPages} changePage={changePageMyBooks} currentPage={currentPage} />
        </div>
          {error.state && <Snackbar message={error.text} />}
      </LoadingWrapper>
    );
};

export default MyBooksTable;
