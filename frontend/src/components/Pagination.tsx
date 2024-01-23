import {useTranslation} from "react-i18next";

const Pagination = ({totalPages, changePage, currentPage, }) => {

    const { t } = useTranslation();

    const paginationStyle = {
        color: "gray",
        border: 'none',
        borderRadius: '20px'
    }

    return (
        <div>
        {totalPages > 1 &&
        <nav aria-label="Page navigation example" className="d-flex justify-content-center">
            <ul className="pagination">
                {currentPage > 1 && (
                    <li className="page-item">
                        <a className="page-link"   style={paginationStyle} onClick={() => changePage(currentPage - 1)}>{t('previous')}</a>
                    </li>
                )}
                <li className="page-item">
                    <span className="page-link"  style={paginationStyle}>{`${currentPage} / ${totalPages}`}</span>
                </li>
                {currentPage < totalPages && (
                    <li className="page-item">
                        <a className="page-link"  style={paginationStyle} onClick={() => changePage(currentPage + 1)}>{t('next')}</a>
                    </li>
                )}
            </ul>
        </nav>
        }
        </div>
    );
}

export default Pagination;
