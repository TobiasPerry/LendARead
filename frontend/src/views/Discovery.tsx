import BookCard from '../components/BookCard';
import useAssetInstance, {language} from "../hooks/assetInstance/useAssetInstance.ts";
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import BookCardPlaceholder from "../components/BookCardPlaceholder.tsx";
import "./styles/discovery.css"
import Spinner from "../components/Spinner.tsx";
import Pagination from "../components/Pagination.tsx";
import {Helmet} from "react-helmet";
import {useLocation, useNavigate} from "react-router-dom";

export const SORT_TYPES = {
    AUTHOR: "AUTHOR_NAME",
    TITLE: "TITLE",
    RECENT: "RECENT"
};

export const SORT_DIRECTIONS = {
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
    const [loadingData, setLoadingData] = useState(true);
    const [loadingLanguages, setLoadingLanguages] = useState(true);


    // Read the query params sent form other views (like view asset)
    const searchParams = new URLSearchParams(window.location.search)
    const searchParam = searchParams.get('search')
    const languageParams = searchParams.getAll('language')
    const physicalConditionParams = searchParams.getAll('physicalCondition')
    const minRatingParam = searchParams.get('minRating')
    const sortParam = searchParams.get('sortBy')
    const sortDirectionParam = searchParams.get('sortDirection')
    const pageParam = searchParams.get('page')
    const booksPerPageParam = searchParams.get('pageSize')

    // Filters and sorting
    const [sort, setSort] = useState((sortParam !== null && sortParam !== undefined) ? sortParam : SORT_TYPES.RECENT);
    const [sortDirection, setSortDirection] = useState((sortDirectionParam !== null && sortDirectionParam !== undefined) ? sortDirectionParam : SORT_DIRECTIONS.DES);
    const [physicalConditions_filters, setPhysicalConditions_filters] = useState((physicalConditionParams !== null && physicalConditionParams !== undefined) ? physicalConditionParams : [])
    const [languages_filters, setLanguages_filters] = useState((languageParams !== null && languageParams !== undefined) ? languageParams : [])
    const [minRating, setMinRating] = useState((minRatingParam !== null && minRatingParam !== undefined) ? parseInt(minRatingParam, 10): 1)
    const [search, setSearch] = useState((searchParam !== null && searchParam !== undefined) ? searchParam : "");
    const [inputValue, setInputValue] = useState(search);

    const [currentPage, setCurrentPage] = useState((pageParam !== null && pageParam !== undefined) ? parseInt(pageParam, 10) : 1);
    const [booksPerPage, setBooksPerPage] = useState((booksPerPageParam !== null && booksPerPageParam !== undefined) ? parseInt(booksPerPageParam, 10): 12);
    const [totalPages, setTotalPages] = useState(0);

    let placeholder_books = Array.from({ length: booksPerPage }, (_, index) => (
        <BookCardPlaceholder key={index} />
    ));

    const clickSearch = (event) => {
        if(inputValue !== ""){
            setSearch(inputValue)
            setCurrentPage(1)
        }
    };
    const handleSearch = (event) => {
        if (event.key === 'Enter'){
            setSearch(event.target.value)
            setCurrentPage(1)
        }
    }

    const changeRating = (event) => {
        setMinRating(parseInt(event.target.value, 10))
        setCurrentPage(1)
    }

    const clickPhysicalCondition = (physicalCondition : string) => {
        if(!physicalConditions_filters.includes(physicalCondition)) {
            setPhysicalConditions_filters((prevState) => [...prevState, physicalCondition])
        }else{
            const new_filters: string[] = physicalConditions_filters.filter((str) => str !== physicalCondition)
            setPhysicalConditions_filters(new_filters)
        }
        setCurrentPage(1)
    }

    const clickLanguages = (language : language) => {
        if(!languages_filters.includes(language.code))
            setLanguages_filters((prevState) => [...prevState, language.code])
        else{
            const new_filters : string[] = languages_filters.filter((item) => item !== language.code)
            setLanguages_filters(new_filters)
        }
        setCurrentPage(1)
    }

    const changePage = (page: number) => {
        setCurrentPage(page)
    }

    const location = useLocation()
    const navigate = useNavigate()
    const updateQueryParams = () => {

        const newSearch = new URLSearchParams()
        languages_filters.forEach((l) => {newSearch.append('language', l)})
        physicalConditions_filters.forEach((p) => {newSearch.append('physicalCondition', p)})
        newSearch.set('minRating', minRating.toString())
        newSearch.set('sortBy', sort)
        newSearch.set('sortDirection', sortDirection)
        newSearch.set('page', currentPage.toString())
        newSearch.set('pageSize', booksPerPage.toString())
        if(search !== "") newSearch.set('search', search)

        navigate({
            ...location,
            search: newSearch.toString(),
        });
    }

    const clearSearch = () => {
        setSearch("");
        setInputValue("");
        setCurrentPage(1);
        setPhysicalConditions_filters([])
        setLanguages_filters([])
        setMinRating(1)
        // Clear the value of the search input
        const searchInput = document.getElementById('search-bar')  as HTMLInputElement;
        if (searchInput) {
            searchInput.value = '';
        }
    }

    const fetchData = async () => {
        setLoadingData(true)
        setData([])
        const response = await handleAllAssetInstances(currentPage, booksPerPage, sort, sortDirection, search, languages_filters, physicalConditions_filters, minRating)
        if(response !== null && response !== undefined) {
            const {books, pages} = response;
            setTotalPages(pages)
            setData(books)
        }
        setLoadingData(false)
    };

    const fetchLanguages = async () => {
        setLoadingLanguages(true)
        const languages = await handleGetLanguages(true)
        setLanguages(languages)
        setLoadingLanguages(false)
    }

    // When search and filters change

    useEffect(()=>{
        fetchData().then();
        updateQueryParams()
    }, [currentPage, booksPerPage, sort, sortDirection, search, languages_filters, physicalConditions_filters, minRating])

    // When the page loads
    useEffect(() => {
        fetchLanguages().then();
    }, []);

    return (
        <>
            <Helmet>
                <meta charSet="utf-8"/>
                <title>{t('discovery.title')}</title>
            </Helmet>
            <div className="main-class">
                <div className="container">
                    <div className="row height d-flex justify-content-center align-items-center">
                        <div className="mt-4" >
                            <div className="form">
                                <div className="input-group mb-3">
                                    <input type="text" className="form-control form-input"
                                           placeholder={t('discovery.searchbar.placeholder')} id="search-bar"
                                           value={inputValue}
                                           onChange={(e) => setInputValue(e.target.value)}
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
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {
                                    setSort(SORT_TYPES.RECENT);
                                    setSortDirection(SORT_DIRECTIONS.DES)
                                }}>{t('discovery.sorting.most_recent')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {
                                    setSort(SORT_TYPES.RECENT);
                                    setSortDirection(SORT_DIRECTIONS.ASC)
                                }}>{t('discovery.sorting.least_recent')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {
                                    setSort(SORT_TYPES.TITLE);
                                    setSortDirection(SORT_DIRECTIONS.ASC)
                                }}>{t('discovery.sorting.title_asc')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {
                                    setSort(SORT_TYPES.TITLE);
                                    setSortDirection(SORT_DIRECTIONS.DES)
                                }}>{t('discovery.sorting.title_des')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {
                                    setSort(SORT_TYPES.AUTHOR);
                                    setSortDirection(SORT_DIRECTIONS.ASC)
                                }}>{t('discovery.sorting.author_asc')}</a></li>
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {
                                    setSort(SORT_TYPES.AUTHOR);
                                    setSortDirection(SORT_DIRECTIONS.DES)
                                }}>{t('discovery.sorting.author_des')}</a></li>
                            </ul>
                        </div>

                        <div className="btn-group mx-2 mb-4 mt-2" role="group">
                            <button type="button" className="btn btn-light dropdown-toggle" data-bs-toggle="dropdown"
                                    aria-expanded="false" style={{backgroundColor: "rgba(255, 255, 255, 0.3)"}}>
                                {t('discovery.books_per_page.title')} {booksPerPage}
                            </button>
                            <ul className="dropdown-menu">
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {
                                    setBooksPerPage(12)
                                }}>12</a></li>
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {
                                    setBooksPerPage(24)
                                }}>24</a></li>
                                <li><a className="dropdown-item" id="mostRecent" onClick={() => {
                                    setBooksPerPage(36)
                                }}>36</a></li>
                            </ul>
                        </div>

                        <h5>{t('discovery.filters.language')}</h5>
                        <ul>

                            <ul className="list-group" style={{maxHeight: '200px', overflowY: 'scroll'}}>
                                {
                                    loadingLanguages ? (
                                        <Spinner/>
                                    ) : (
                                        <>
                                            {languages.map((language, item) => (
                                                <div key={item}>
                                                    <li className="list-group-item m-1 clickable"
                                                        style={
                                                            languages_filters.includes(language.code) ?
                                                                {backgroundColor: 'rgba(255,255,255,0.9)'} : {backgroundColor: 'rgba(255,255,255,0.3)'}
                                                        }
                                                        onClick={() => {
                                                            clickLanguages(language)
                                                        }}
                                                    >
                                                <span className="d-inline-block text-truncate"
                                                      style={{maxWidth: '100px'}}>
                                                    {language.name}
                                                </span>
                                                    </li>
                                                </div>
                                            ))}
                                        </>
                                    )

                                }
                            </ul>
                        </ul>
                        <h5>{t('discovery.filters.physical_condition')}</h5>
                        <ul>
                            <ul className="list-group" style={{maxHeight: '200px', overflowY: 'scroll'}}>
                                {
                                    physical_conditions.map((physical_condition, item) => (
                                            <div key={item}>
                                                <li className="list-group-item m-1 clickable"
                                                    style={
                                                        physicalConditions_filters.includes(physical_condition) ? {backgroundColor: 'rgba(255,255,255,0.9)'} : {backgroundColor: 'rgba(255,255,255,0.3)'}
                                                    }
                                                    onClick={() => {
                                                        clickPhysicalCondition(physical_condition)
                                                    }}
                                                >
                                                <span className="d-inline-block text-truncate"
                                                      style={{maxWidth: '100px'}}>
                                                    {t(physical_condition)}
                                                </span>
                                                </li>
                                            </div>
                                        )
                                    )
                                }
                            </ul>
                        </ul>

                        <h5>{t('discovery.filters.book_rating')}</h5>
                        <div style={{width: '90%', margin: '10px auto'}}>
                            <label className="form-label d-flex justify-content-center" id="customRange3Id">
                                {minRating}★ - 5★
                            </label>
                            <input type="range" className="form-range custom-range" min="1" max="5" step="1"
                                   id="customRange3"
                                   onChange={changeRating}
                                   value={minRating}
                            />
                        </div>

                        <div className="container-row-wrapped"
                             style={{marginTop: '10px', marginBottom: '25px', width: '100%'}}>
                            <input type="button" className="btn btn-outline-dark mx-2"
                                   value={t('discovery.filters.btn.clear')} style={{margin: '10px', width: '100px'}}
                                   onClick={clearSearch}
                            />
                        </div>
                    </div>

                    <div className="container-column" style={{flex: '0 1 85%'}}>
                        { /* If books is empty show message and btn action to clear filters */
                            !loadingData && data.length === 0 ? (
                                <div className="mb-2">
                                    <div className="container-row-wrapped" style={{width: '100%'}}>
                                        <h1>{t('discovery.no_books.title')}</h1>
                                    </div>
                                    <div className="container-row-wrapped mt-3" style={{width: '100%'}}>
                                        <button type="button" className="btn btn-outline-secondary btn-lg"
                                                onClick={clearSearch}>
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
                                            data.length === 0 ? placeholder_books : data.map((book, index) => (
                                                <BookCard key={index} book={book}/>))
                                        }
                                    </div>
                                    <div className="container-row-wrapped"
                                         style={{marginTop: '25px', marginBottom: '25px', width: '100%'}}>
                                       <Pagination currentPage={currentPage} changePage={changePage} totalPages={totalPages}/>
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
