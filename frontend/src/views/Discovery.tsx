import BookCard from '../components/BookCard';
import useAssetInstance, {language} from "../hooks/assetInstance/useAssetInstance.ts";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import BookCardPlaceholder from "../components/BookCardPlaceholder.tsx";

const SORT_TYPES = {
    AUTHOR: "AUTHOR_NAME",
    TITLE: "TITLE_NAME",
    RECENT: "RECENT"
};

const SORT_DIRECTIONS = {
    ASC: "ASCENDING",
    DES: "DESCENDING"
};


const physical_conditions = ["ASNEW", "FINE", "VERYGOOD", "GOOD", "FAIR", "POOR", "EXLIBRARY", "BOOKCLUB", "BINDINGCOPY"]
const DiscoveryView =  () => {

    const {t} = useTranslation();

    const sortingi18n = (sort, sortDirection) => {
        switch (sort){
            case SORT_TYPES.AUTHOR:
                return (sortDirection === SORT_DIRECTIONS.ASC) ? t("discovery.sorting.author_asc") : t("discovery.sorting.author_des")
            case SORT_TYPES.TITLE:
                return (sortDirection === SORT_DIRECTIONS.ASC) ? t("discovery.sorting.title_asc") : t("discovery.sorting.title_des")
            case SORT_TYPES.RECENT:
                return (sortDirection === SORT_DIRECTIONS.ASC) ? t("discovery.sorting.least_recent") : t("discovery.sorting.most_recent")
            default:
                return ""
        }
    }

    const {handleAllAssetInstances, handleGetLanguages} = useAssetInstance();

    const [languages, setLanguages] = useState([])
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [booksPerPage, setBooksPerPage] = useState(1);
    const [totalPages, setTotalPages] = useState("?");

    // Filters and sorting
    const [sort, setSort] = useState(SORT_TYPES.RECENT);
    const [sortDirection, setSortDirection] = useState(SORT_DIRECTIONS.DES);
    const [physicalConditions_filters, setPhysicalConditions_filters] = useState([])
    const [languages_filters, setLanguages_filters] = useState([])
    const [search, setSearch] = useState("");


    let placeholder_books = Array.from({ length: booksPerPage }, (_, index) => (
        <BookCardPlaceholder key={index} />
    ));

    const clickSearch = (event) => {
        // Get the value of the search input - Need to be an HTMLInputElement so that TS knows it has the value property
        const searchInput = document.getElementById('search-bar') as HTMLInputElement;
        if (searchInput) {
            setSearch(searchInput.value)
        }
    };
    const handleSearch = (event) => {
        if (event.key === 'Enter'){
            setSearch(event.target.value)
            setCurrentPage(1)
        }
    }

    const clickPhysicalCondition = (physicalCondition : string) => {
        if(!physicalConditions_filters.includes(physicalCondition)) {
            setPhysicalConditions_filters((prevState) => [...prevState, physicalCondition])
        }else{
            const new_filters: string[] = physicalConditions_filters.filter((str) => str !== physicalCondition)
            setPhysicalConditions_filters(new_filters)
        }
    }

    const clickLanguages = (language : language) => {
        if(!languages_filters.includes(language.code))
            setLanguages_filters((prevState) => [...prevState, language.code])
        else{
            const new_filters : string[] = languages_filters.filter((item) => item !== language.code)
            setLanguages_filters(new_filters)
        }
    }

    const previousPage = () => {
        currentPage > 1 ? setCurrentPage(currentPage - 1) : {}
    }
    const nextPage = () => {
        setCurrentPage(currentPage + 1)
    }
    const clearSearch = () => {
        setSearch("");
        setCurrentPage(1);
        setPhysicalConditions_filters([])
        setLanguages_filters([])
        // Clear the value of the search input
        const searchInput = document.getElementById('search-bar')  as HTMLInputElement;
        if (searchInput) {
            searchInput.value = '';
        }
    }

    const fetchData = async () => {
        setLoading(true)
        setData([])
        const books = await handleAllAssetInstances(currentPage, booksPerPage, sort, sortDirection, search, languages_filters, physicalConditions_filters)
        setData(books)
        const languages = await handleGetLanguages(false)
        setLanguages(languages)
        setLoading(false)
    };
    useEffect(()=>{
        document.title = t('discovery.title')
        fetchData();
        // for when it unmounts
        return () => {
            document.title = "Lend a Read"
        }
    }, [currentPage, booksPerPage, sort, sortDirection, search, languages_filters, physicalConditions_filters])

    return (
        <>
            <div className="main-class">
                <div className="container">
                    <div className="row height d-flex justify-content-center align-items-center">
                        <div className="mt-4" >
                            <div className="form">
                                <div className="input-group mb-3">
                                    <input type="text" className="form-control form-input"
                                           placeholder={t('discovery.searchbar.placeholder')} id="search-bar"
                                           onKeyPress={handleSearch}
                                           />
                                    <button className="btn btn-light" type="button" onClick={clickSearch}>
                                        <i className="bi bi-search bi-lg"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div className="container-row">
                    <div className="container-column" style={{flex: '0 0 15%', margin: '10px'}}>

                        <div className="btn-group mx-2 mb-4 mt-2" role="group">
                            <button type="button" className="btn btn-light dropdown-toggle" data-bs-toggle="dropdown"
                                    aria-expanded="false" style={{backgroundColor: "rgba(255, 255, 255, 0.3)"}}>
                                {t('discovery.sorting.sort_by')} {sortingi18n(sort, sortDirection)}
                            </button>
                            <ul className="dropdown-menu">
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {setSort(SORT_TYPES.RECENT); setSortDirection(SORT_DIRECTIONS.DES)}}>{t('discovery.sorting.most_recent')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {setSort(SORT_TYPES.RECENT); setSortDirection(SORT_DIRECTIONS.ASC)}}>{t('discovery.sorting.least_recent')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {setSort(SORT_TYPES.TITLE); setSortDirection(SORT_DIRECTIONS.ASC)}}>{t('discovery.sorting.title_asc')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {setSort(SORT_TYPES.TITLE); setSortDirection(SORT_DIRECTIONS.DES)}}>{t('discovery.sorting.title_des')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {setSort(SORT_TYPES.AUTHOR); setSortDirection(SORT_DIRECTIONS.ASC)}}>{t('discovery.sorting.author_asc')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {setSort(SORT_TYPES.AUTHOR); setSortDirection(SORT_DIRECTIONS.DES)}}>{t('discovery.sorting.author_des')}</a></li>
                            </ul>
                        </div>

                        <h5>{t('discovery.filters.language')}</h5>
                        <ul>

                            <ul className="list-group" style={{maxHeight: '200px', overflowY: 'scroll'}}>
                                {
                                    languages.map((language, item) => (
                                        <>
                                            <li className="list-group-item m-1 clickable"
                                                style={
                                                    languages_filters.includes(language.code) ?
                                                        {backgroundColor: 'rgba(255,255,255,0.9)'} : {backgroundColor: 'rgba(255,255,255,0.3)'}
                                                }
                                                onClick={() => {clickLanguages(language)}}
                                            >
                                                <span className="d-inline-block text-truncate"
                                                      style={{maxWidth: '100px'}}>
                                                    {language.name}
                                                </span>
                                            </li>
                                        </>
                                    ))
                                }
                            </ul>
                        </ul>
                        <h5>{t('discovery.filters.physical_condition')}</h5>
                        <ul>
                            <ul className="list-group" style={{maxHeight: '200px', overflowY: 'scroll'}}>
                                {
                                    physical_conditions.map((physical_condition, item) => (
                                        <>
                                            <li className="list-group-item m-1 clickable"
                                                style={
                                                    physicalConditions_filters.includes(physical_condition) ? {backgroundColor: 'rgba(255,255,255,0.9)'} : {backgroundColor: 'rgba(255,255,255,0.3)'}
                                                }
                                                onClick={() => {clickPhysicalCondition(physical_condition)}}
                                            >
                                                <span className="d-inline-block text-truncate"
                                                      style={{maxWidth: '100px'}}>
                                                    {t(physical_condition)}
                                                </span>
                                            </li>
                                        </>
                                        )
                                    )
                                }
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
                            {/*<input className="btn btn-light mx-2" type="submit" value={t('discovery.filters.btn.apply')}*/}
                            {/*       id="submit-filter" style={{margin: '10px', width: '100px'}}/>*/}
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
                                            data.length === 0 ? placeholder_books : data.map((book, index) => (<BookCard key={index} book={book}/> ))
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
