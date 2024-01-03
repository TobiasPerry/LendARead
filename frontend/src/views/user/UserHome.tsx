import  { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import MyBooksTable from '../../components/user/MyBooksTable.tsx';
const UserHomeView = () => {
    const { t } = useTranslation();
    const [table, setTable] = useState('my_books');
    const [userAssets, setUserAssets] = useState([]); // Assuming this is some data you fetch
    const [isLender, setIsLender] = useState(false); // Adjust according to your logic
    const [modalType, setModalType] = useState(''); // Set accordingly
    const [assetId, setAssetId] = useState(''); // Set accordingly

    // Example of fetching data, adjust this to suit your actual data fetching
    useEffect(() => {
        // Fetch data here and update state
    }, []);

    const handleTableChange = (newTable) => {
        setTable(newTable);
        // Additional logic if needed
    };

    return (
        <div>
            <div className="container">
                <div className="container-flex">
                    <h1>{t('greeting', { userEmail: 'user@example.com' })}</h1> {/* Replace with actual user email */}
                    <div className="row">
                        <div className="sidebar table-selector">
                            <div className="list-group">
                                <button onClick={() => handleTableChange('my_books')} className={`list-group-item list-group-item-action button-select ${table === 'my_books' ? 'button-select-active' : ''}`}>
                                    {t('my_books')}
                                </button>
                                <button onClick={() => handleTableChange('lended_books')} className={`list-group-item list-group-item-action button-select ${table === 'lended_books' ? 'button-select-active' : ''}`}>
                                    {t('lended_books')}
                                </button>
                                <button onClick={() => handleTableChange('borrowed_books')} className={`list-group-item list-group-item-action button-select ${table === 'borrowed_books' ? 'button-select-active' : ''}`}>
                                    {t('borrowed_books')}
                                </button>
                            </div>
                        </div>
                        <div className="content">
                            {table === 'my_books' && <MyBooksTable userAssets={userAssets} />}
                            {/*{table === 'lended_books' && <LendedBooksTable isLender={isLender} userAssets={userAssets} />}*/}
                            {/*{table === 'borrowed_books' && <BorrowedBooksTable userAssets={userAssets} />}*/}
                        </div>
                    </div>
                </div>
            </div>

            {/*<DeleteBookModal modalType={modalType} assetId={assetId} />*/}
        </div>
    );
};

export default UserHomeView;
