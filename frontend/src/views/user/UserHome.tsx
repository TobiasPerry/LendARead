import {useState, useEffect, useContext} from 'react';
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

    const handleTableChange = (newTable: any) => {
        setTable(newTable);
        // Additional logic if needed
    };

    return (
        <div>
            <div  style={{width: '1000px', margin: 'auto', paddingBottom: '50px', paddingTop: '50px'}}>
                <h1>{t('greeting', { userEmail: 'user@example.com' })}</h1>
                <div style={{ display: 'flex', flexDirection: 'row' }}>
                    <div style={{ flex: 1, marginRight: '20px' }}>
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
                    <div style={{ flex: 3 }}>
                            {table === 'my_books' && <MyBooksTable userAssets={userAssets} />}
                            {/*{table === 'lended_books' && <LendedBooksTable isLender={isLender} userAssets={userAssets} />}*/}
                            {/*{table === 'borrowed_books' && <BorrowedBooksTable userAssets={userAssets} />}*/}
                        </div>
                    </div>
                </div>
            {/*<DeleteBookModal modalType={modalType} assetId={assetId} />*/}
        </div>
    );
};

export default UserHomeView;
