import useLendings from "../../hooks/lendings/useLendings.ts";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";
import "../styles/myBookActiveLendings.css";
import Pagination from "../Pagination.tsx";
import {useEffect} from "react";
import { useNavigate } from 'react-router-dom';

const MyBookActiveLendings = ({ asset }) => {
    const navigate = useNavigate();
    const { lendings, currentPage, changePage, totalPages, getLendings, setAsset_ } = useLendings();
    const { t } = useTranslation();

    useEffect(() => {
        getLendings(asset).then();
        setAsset_(asset)
    }, [asset]);

    const handleNavigation = (lendingId) => {
        navigate(`/userBook/${lendingId}?state=lended`);
    };


    return (
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
                        <div className="start-date">{t('from')} {lending.startDate}</div>
                        <div className="end-date">{t('until')} {lending.endDate}</div>
                    </div>
                </div>
            ))}
            <Pagination currentPage={currentPage} changePage={changePage} totalPages={totalPages} />
        </div>
    );
};

export default MyBookActiveLendings;