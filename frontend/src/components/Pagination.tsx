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
            <nav aria-label="Page navigation example">
                <ul className="pagination justify-content-center align-items-center">
                    <li className="page-item">
                        <button type="button"
                                className={`btn mx-5 pagination-button ${currentPage <= 1 ? 'disabled' : ''}`}
                                id="previousPageButton"
                                style={{borderColor: "rgba(255, 255, 255, 0)"}}
                                onClick={() => changePage(currentPage - 1)}
                                data-testid="previous-btn"
                        >
                            <i className="bi bi-chevron-left"></i>
                            {t('discovery.pagination.previous')}
                        </button>
                    </li>

                    <li>
                        {currentPage} / {totalPages}
                    </li>

                    <li className="page-item">
                        <button type="button"
                                className={`btn mx-5 pagination-button ${currentPage >= totalPages ? 'disabled' : ''}`}
                                id="nextPageButton"
                                style={{borderColor: "rgba(255, 255, 255, 0)"}}
                                onClick={() => changePage(currentPage + 1)}
                                data-testid="next-btn"
                        >
                            {t('discovery.pagination.next')}
                            <i className="bi bi-chevron-right"></i>
                        </button>
                    </li>
                </ul>
            </nav>
        }
        </div>
    );
}

export default Pagination;
