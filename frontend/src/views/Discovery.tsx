import BookCard from '../components/BookCard';
import useAssetInstance from "../hooks/AssetInstance/useAssetInstance.ts";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import BookCardPlaceholder from "../components/BookCardPlaceholder.tsx";

const DiscoveryView =  () => {





    const {t} = useTranslation();


    const {handleAllAssetInstances} = useAssetInstance();

    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [booksPerPage, setBooksPerPage] = useState(1);
    const [totalPages, setTotalPages] = useState("?");

    let books = Array.from({ length: booksPerPage }, (_, index) => (
        <BookCardPlaceholder key={index} />
    ));
    const previousPage = () => {
        currentPage > 1 ? setCurrentPage(currentPage - 1) : {}
    }
    const nextPage = () => {
        setCurrentPage(currentPage + 1)
    }
    const clearSearch = () => {
        setCurrentPage(1)
    }

    useEffect(()=>{
        const fetchData = async () => {
            const books = await handleAllAssetInstances(currentPage, booksPerPage)
            setData(books)
            setLoading(false)
        };
        fetchData();
    }, [currentPage, booksPerPage])

    return (
        <>
            <div className="main-class">

                <div className="container">
                    <div className="row height d-flex justify-content-center align-items-center">
                        <div className="mt-4" >
                            <div className="form">
                                <i className="fa fa-search"></i>
                                <input type="text" className="form-control form-input"
                                       placeholder={t('discovery.searchbar.placeholder')} id="search-bar"
                                       />
                            </div>
                        </div>
                    </div>
                </div>


                <div className="container-row">
                    <div className="container-column" style={{flex: '0 0 15%', margin: '10px'}}>

                        <div className="btn-group mx-2 mb-4 mt-2" role="group">
                            <button type="button" className="btn btn-light dropdown-toggle" data-bs-toggle="dropdown"
                                    aria-expanded="false" style={{backgroundColor: "rgba(255, 255, 255, 0.3)"}}>
                                {t('discovery.sorting.sort_by')}
                            </button>
                            <ul className="dropdown-menu">
                                <li><a className="dropdown-item" id="mostRecent">{t('discovery.sorting.most_recent')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent">{t('discovery.sorting.least_recent')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent">{t('discovery.sorting.title_asc')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent">{t('discovery.sorting.title_des')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent">{t('discovery.sorting.author_asc')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent">{t('discovery.sorting.author_des')}</a></li>
                            </ul>
                        </div>

                        <h5>{t('discovery.filters.language')}</h5>
                        <ul>
                            <ul style={{maxHeight: '200px', overflowY: 'scroll'}}>

                                <input className="form-check-input" type="checkbox" value=""/>
                                <label className="form-check-label languageLabel"
                                       id="language-${status.index}-label"><span
                                    className="d-inline-block text-truncate"
                                    style={{maxWidth: '150px'}}>text</span></label>
                                <br/>

                            </ul>
                        </ul>
                        <h5>{t('discovery.filters.physical_condition')}</h5>
                        <ul>
                            <ul style={{maxHeight: '200px', overflowY: 'scroll'}}>

                                <input className="form-check-input" type="checkbox" value=""
                                       id="physicalCondition-${status.index}"/>
                                <label className="form-check-label physicalConditionLabel"

                                       id="physicalCondition-${status.index}-label"
                                >
                                        <span className="d-inline-block text-truncate" style={{maxWidth: '150px'}}>
                                        text
                                        </span>
                                </label>
                                <br/>

                            </ul>
                        </ul>

                        <h5>{t('discovery.filters.book_rating')}</h5>
                        <div style={{width: '90%', margin: '10px auto'}}>
                            <label className="form-label d-flex justify-content-center" id="customRange3Id">?★ -
                                ?★</label>
                            <input type="range" className="form-range custom-range" min="1" max="5" step="1"
                                   id="customRange3" value="${actualMinRating}"/>
                        </div>

                        <div className="container-row-wrapped"
                             style={{marginTop: '10px', marginBottom: '25px', width: '100%'}}>
                            <input className="btn btn-light mx-2" type="submit" value={t('discovery.filters.btn.apply')}
                                   id="submit-filter" style={{margin: '10px', width: '100px'}}/>
                            <Link to="/discovery">
                                <input type="button" className="btn btn-outline-dark mx-2"
                                       value={t('discovery.filters.btn.clear')} style={{margin: '10px', width: '100px'}}/>
                            </Link>
                        </div>
                    </div>

                    <div className="container-column" style={{flex: '0 1 85%'}}>
                        { /* If books is empty show message and btn action to clear filters */
                            !loading && data.length === 0 ? (
                                    <div className="mb-2">
                                        <div className="container-row-wrapped" style={{width: '100%'}}>
                                            <h1>{t('discovery.no_books.title')}</h1>
                                        </div>
                                        <div className="container-row-wrapped mt-3" style={{width: '100%'}}>
                                            <button type="button" className="btn btn-outline-secondary btn-lg" onClick={clearSearch}>
                                                {t('discovery.no_books.btn')}
                                            </button>
                                        </div>
                                    </div>
                            ) : (
                                <>
                                    <div className="container-row-wrapped"
                                         style={{
                                             margin: '20px auto',
                                             paddingTop: '20px',
                                             backgroundColor: "rgba(255, 255, 255, 0.3)",
                                             borderRadius: '20px',
                                             width: '90%'
                                         }}>
                                        {
                                            data.length === 0 ? books : data.map((book, index) => (<BookCard key={index} book={book}/> ))
                                        }
                                    </div>
                                    <div className="container-row-wrapped"
                                         style={{marginTop: '25px', marginBottom: '25px', width: '100%'}}>
                                        <div>
                                            <nav aria-label="Page navigation example">
                                                <ul className="pagination justify-content-center align-items-center">
                                                    <li className="page-item">
                                                        <button type="button"
                                                                className="btn mx-5 pagination-button"
                                                                id="previousPageButton"
                                                                style={{borderColor: "rgba(255, 255, 255, 0)"}}
                                                                onClick={previousPage}
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
                                                                className="btn mx-5 pagination-button"
                                                                id="nextPageButton"
                                                                style={{borderColor: "rgba(255, 255, 255, 0)"}}
                                                                onClick={nextPage}
                                                        >
                                                            {t('discovery.pagination.next')}
                                                            <i className="bi bi-chevron-right"></i>
                                                        </button>
                                                    </li>
                                                </ul>
                                            </nav>
                                        </div>
                                    </div>
                                </>
                            )
                        }
                    </div>
                </div>
            </div>
        </>
    );
};

export default DiscoveryView;
