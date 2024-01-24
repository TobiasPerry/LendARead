import useLendings from "../../hooks/lendings/useLendings.ts";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";
import "../styles/myBookActiveLendings.css";
import Pagination from "../Pagination.tsx";
import {useEffect} from "react";

const MyBookActiveLendings = ({asset}) => {
    const {lendings, currentPage, changePage, totalPages, getLendings} = useLendings();
    const {t} = useTranslation();

    useEffect(() => {
        getLendings(asset).then()
    }, [asset])

    return (
        <div className="lendings-container">
            <h3 className="lendings-title">{t('active_loans')}</h3>
            {lendings.map((lending, index) => (
                <Link to={`/userBook/${lending.id}?state=lended`} key={index} className="lending-item">
                    <div className="user-info">
                        <img src={lending.userImage} alt={lending.userName} className="user-image" />
                        <div className="user-name">{lending.userName}</div>
                    </div>
                    <div className="d-flex flex-row lending-dates">
                        <div className="start-date">{t('from')} {lending.startDate}</div>
                        <div className="end-date">{t('until')} {lending.endDate}</div>
                    </div>
                </Link>
            ))}
            <Pagination currentPage={currentPage} changePage={changePage} totalPages={totalPages} />
        </div>
    );
}

export default MyBookActiveLendings;