import BookCard from '../components/BookCard';
import useAssetInstance from "../hooks/useAssetInstance.ts";
import {useEffect, useState} from "react";

const DiscoveryView =  () => {


    const [data, setData] = useState("vacio")
    const {handleAllAssetInstances} = useAssetInstance()

    useEffect(()=>{
        const fetchData = async () => {
            const _data = await handleAllAssetInstances();
            setData(_data)
            console.log(data);
          //  setData("algo")
        };
        fetchData();
    })

    return (
        <>
            <div className="main-class">

                <div className="container my-4">
                    <div className="row height d-flex justify-content-center align-items-center">
                        <div className="">
                            <div className="form">
                                <i className="fa fa-search"></i>
                                <input type="text" className="form-control form-input"
                                       placeholder="placeholder" id="search-bar"
                                       value="value"/>
                            </div>
                        </div>
                    </div>
                </div>


                <div className="container-row">
                    <div className="container-column" style={{flex: '0 0 15%', margin: '10px'}}>

                        <div className="btn-group mx-2 mb-4 mt-2" role="group">
                            <button type="button" className="btn btn-light dropdown-toggle" data-bs-toggle="dropdown"
                                    aria-expanded="false" style={{backgroundColor: "rgba(255, 255, 255, 0.3)"}}>
                                text
                            </button>
                            <ul className="dropdown-menu">
                                <li><a className="dropdown-item" id="mostRecent">text</a></li>
                                <li><a className="dropdown-item" id="leastRecent">text</a></li>
                            </ul>
                        </div>

                        <h5>aca: {data}text</h5>
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
                        <h5>text</h5>
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

                        <h5>text</h5>
                        <div style={{width: '90%', margin: '10px auto'}}>
                            <label className="form-label d-flex justify-content-center" id="customRange3Id">?★ -
                                ?★</label>
                            <input type="range" className="form-range custom-range" min="1" max="5" step="1"
                                   id="customRange3" value="${actualMinRating}"/>
                        </div>


                        {/*
                        FORM
                         <c:url value="/discovery" var="discoveryPageUrl"/>
                        <form:form method="get" accept-charset="UTF-8" action="${discoveryPageUrl}"
                                modelAttribute="searchFilterSortForm" id="springForm">
                            <input type="hidden" name="currentPage" id="currentPageID" value="${currentPage}"/>

                            <input type="hidden" name="sort" id="sort" value="${sort}"/>
                            <input type="hidden" name="sortDirection" id="sortDirection" value="${sortDirection}"/>

                            <c:forEach var="language" items="${languagesFiltered}" varStatus="status">
                                <input type="hidden" name="languages[${status.index}]" value="${language}"/>
                            </c:forEach>

                            <c:forEach var="physicalCondition" items="${physicalConditionsFiltered}" varStatus="status">
                                <input type="hidden" name="physicalConditions[${status.index}]" value="${physicalCondition}"/>
                            </c:forEach>

                            <input type="hidden" name="minRating" id="minRating" value="${actualMinRating}"/>
                            <input type="hidden" name="maxRating" id="maxRating" value="${actualMaxRating}"/>

                        </form:form> */}

                        <div className="container-row-wrapped"
                             style={{marginTop: '10px', marginBottom: '25px', width: '100%'}}>
                            <input className="btn btn-light mx-2" type="submit" value="value"
                                   id="submit-filter" style={{margin: '10px', width: '100px'}}/>
                            <a href="/discovery">
                                <input type="button" className="btn btn-outline-dark mx-2"
                                       value="value" style={{margin: '10px', width: '100px'}}/>
                            </a>
                        </div>
                    </div>

                    <div className="container-column" style={{flex: '0 1 85%'}}>

                        <div className="container-row-wrapped placeholder-group"
                             style={{
                                 margin: '20px auto',
                                 paddingTop: '20px',
                                 backgroundColor: "rgba(255, 255, 255, 0.3)",
                                 borderRadius: '20px',
                                 width: '90%'
                             }}>
                            <BookCard/>
                        </div>

                        <div className="container-row-wrapped"
                             style={{
                                 margin: '20px auto',
                                 paddingTop: '20px',
                                 backgroundColor: "rgba(255, 255, 255, 0.3)",
                                 borderRadius: '20px',
                                 width: '90%'
                             }}>
                            <BookCard></BookCard>
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
                                                    style={{borderColor: "rgba(255, 255, 255, 0)"}}>
                                                <i className="bi bi-chevron-left"></i>
                                                text
                                            </button>
                                        </li>

                                        <li>
                                            current / total
                                        </li>

                                        <li className="page-item">
                                            <button type="button"
                                                    className="btn mx-5 pagination-button"
                                                    id="nextPageButton"
                                                    style={{borderColor: "rgba(255, 255, 255, 0)"}}> text <i
                                                className="bi bi-chevron-right"></i>
                                            </button>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                        </div>

                        <div className="container-row-wrapped" style={{marginTop: '50px', width: '100%'}}>
                            <h1>text</h1>
                        </div>
                        <div className="container-row-wrapped" style={{marginTop: '20px', width: '100%'}}>
                            <a href="/discovery">
                                <button type="button" className="btn btn-outline-secondary btn-lg">text</button>
                            </a>
                        </div>

                    </div>
                </div>
            </div>


        </>
    );
};

export default DiscoveryView;
