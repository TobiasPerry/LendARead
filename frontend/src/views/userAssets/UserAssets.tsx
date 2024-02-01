import {useState, useEffect, useContext} from 'react';
import { useTranslation } from 'react-i18next';
import MyBooksTable from '../../components/userAssets/MyBooksTable.tsx';
import LendedBooksTable from "../../components/userAssets/LendedBooksTable.tsx";
import {useLocation, useNavigate} from "react-router-dom";
import {AuthContext} from "../../contexts/authContext.tsx";

const UserAssetsView = () => {

    const { t } = useTranslation();
    const navigate = useNavigate();
    const location = useLocation();
    const [table, setTable] = useState('owned_books');
    const {userDetails} = useContext(AuthContext)

    // Parse the query parameters
    const searchParams = new URLSearchParams(location.search);
    const tableQueryParam = searchParams.get('table');

    // Set the table from the URL on component mount
    useEffect(() => {
        if (tableQueryParam) {
            setTable(tableQueryParam);
        }
    }, [tableQueryParam]);

    const handleTableChange = (newTable) => {
        setTable(newTable);
        // Update the URL without refreshing the page
        navigate(`?table=${newTable}`, { replace: true });
    };


    const handleRowClicked = (book, state) => {
        navigate(`/userBook/${book.id}?state=${state}`)
    }

    return (
        <div className="main-class" style={{height: "100%", margin: 0}}>
            <div style={{ padding: "40px"}}>
                <div style={{backgroundColor: "#f9f9f9", padding: "30px", borderRadius: "20px"}}>
            <h1 style={{marginBottom: "20px"}}>{t('greeting', { userEmail: userDetails.userName })}</h1>
            <div style={{ display: 'flex', flexDirection: 'row', marginBottom: "20px" }}>
                <div style={{ flex: 1 }}>
                    <div className="list-group" style={{ width: "150px" }}>
                        <button
                            onClick={() => handleTableChange('owned_books')}
                            className={`list-group-item list-group-item-action button-select ${table === 'my_books' ? 'button-select-active' : ''}`}
                            style={{ fontWeight: table === 'owned_books' ? 'bold' : 'normal' }}>
                            {t('my_books')}
                        </button>
                        <button
                            onClick={() => handleTableChange('lended_books')}
                            className={`list-group-item list-group-item-action button-select ${table === 'lended_books' ? 'button-select-active' : ''}`}
                            style={{ fontWeight: table === 'lended_books' ? 'bold' : 'normal' }}>
                            {t('lended_books')}
                        </button>
                        <button
                            onClick={() => handleTableChange('borrowed_books')}
                            className={`list-group-item list-group-item-action button-select ${table === 'borrowed_books' ? 'button-select-active' : ''}`}
                            style={{ fontWeight: table === 'borrowed_books' ? 'bold' : 'normal' }}>
                            {t('borrowed_books')}
                        </button>
                    </div>
                </div>
                <div style={{ flex: 5 }}>
                    {table === 'owned_books' && <MyBooksTable handleRowClicked={handleRowClicked} />}
                    {table === 'lended_books' && <LendedBooksTable isLender={true} handleRowClicked={handleRowClicked} />}
                    {table === 'borrowed_books' && <LendedBooksTable isLender={false} handleRowClicked={handleRowClicked} />}
                </div>
            </div>
            </div>
        </div>
        </div>
    );
};

export default UserAssetsView;
