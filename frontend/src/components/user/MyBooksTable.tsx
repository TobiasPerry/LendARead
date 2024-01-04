import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import SortButton from './SortButton'; // Create a component for sorting
import FilterButton from './FilterButton'; // Create a component for filtering

const MyBooksTable = ({ userAssets }: any) => {
    const { t } = useTranslation();

    // Assuming you have a way to fetch or receive these properties
    const [sortAttribute, setSortAttribute] = useState('');
    const [filterValue, setFilterValue] = useState('');
    const [filterAttribute, setFilterAttribute] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1); // Set accordingly based on your data

    const isLender = true

    useEffect(() => {
        // Fetch or update data here as necessary
    }, [sortAttribute, filterValue, currentPage]);

    return (
        <div>
            {isLender ? (
                <div className="table-title">
                    {/* Table Header */}
                    <h2>{t('my_books')}</h2>
                    <div>
                        {/* Filter and Sort Buttons */}
                        <FilterButton title="filterOption.all" buttonText="userHomeView.all" />
                        <FilterButton title="filterOption.public" buttonText="userHomeView.public" />
                        <FilterButton title="filterOption.private" buttonText="userHomeView.private" />
                    </div>
                    <table className="table">
                        <thead>
                        <tr>
                            <th>{t('image')}</th>
                            <SortButton title="book_name" />
                            <SortButton title="author" />
                            <SortButton title="language" />
                            <SortButton title="userDetailView.state" />
                        </tr>
                        </thead>
                        <tbody>
                        {userAssets.map((asset, index) => (
                            <tr key={index} className="table-row-clickable" onClick={() => window.location.href = `/myBookDetails/${asset.id}`}>
                                <td>
                                    <img style={{ height: '125px', width: '75px', objectFit: 'cover' }} src={`/getImage/${asset.image.id}`} alt={asset.book.name} />
                                </td>
                                <td>{asset.book.name}</td>
                                <td>{asset.book.author}</td>
                                <td>{asset.book.language}</td>
                                <td>{t(`enum.${asset.assetState}`)}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    {userAssets.length === 0 && (
                        <div className="container-row-wrapped" style={{ marginTop: '50px', width: '100%' }}>
                            <h4>{t('discovery.noBooks')}</h4>
                        </div>
                    )}
                </div>
            ) : (
                <div className="promo-box">
                    <h2>{t('become_lender.title')}</h2>
                    <p>{t('become_lender.subtitle')}</p>
                    <a href="/addAssetView" className="button-status" style={{ color: '#2B3B2B', fontWeight: 'bold' }}>
                        {t('become_lender.button')}
                    </a>
                </div>
            )}


        </div>
    );
};

export default MyBooksTable;
