import useLendings from "../../hooks/lendings/useLendings.ts";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";
import "../styles/myBookActiveLendings.css";
import Pagination from "../Pagination.tsx";
import {useEffect} from "react";
import { useNavigate } from 'react-router-dom';
import Snackbar from "../SnackBar.tsx";
import {extractDate} from "../../hooks/api/types.ts";

const MyBookActiveLendings = ({ asset }) => {
    const navigate = useNavigate();
    const { lendings, currentPage, changePage, totalPages, getLendings, setAsset_, error} = useLendings();
    const { t } = useTranslation();

    useEffect(() => {
        try {
            getLendings(asset).then();
            setAsset_(asset)
        } catch (e) {
            
        }
    }, [asset]);

    const handleNavigation = (lendingId) => {
        navigate(`/userBook/${lendingId}?state=lended`);
    };


    return (
        <>
            {lendings.length ? (
                <div className="lendings-container">
                    <h3 className="lendings-title">{t('active_loans')}</h3>
                    {lendings.map((lending, index) => (
                        <div
                            key={index}
                            className="lending-item"
                            onClick={() => handleNavigation(lending.id)}
                            role="button"
                            tabIndex={0}
                        >
                            <div className="user-info">
                                <img src={lending.userImage} alt={lending.userName} className="user-image" />
                                <div className="user-name">{lending.userName}</div>
                            </div>
                            <div className="d-flex flex-row lending-dates">
                                <div className="user-name">{lending.state}</div>
                                <div className="start-date">{t('from')} {t('date', extractDate(lending.startDate))}</div>
                                <div className="end-date">{t('until')} {t('date', extractDate(lending.endDate))}</div>
                            </div>
                        </div>
                    ))}
                    <Pagination currentPage={currentPage} changePage={changePage} totalPages={totalPages} />
                </div>
            ) : null}
            {error.state && <Snackbar message={error.text} />}
        </>

    );
};

export default MyBookActiveLendings;