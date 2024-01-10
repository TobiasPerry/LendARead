import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import "../styles/assetView.css"
import useAssetInstance from "../../hooks/assetInstance/useAssetInstance.ts";
import {AssetData} from "../../hooks/assetInstance/useAssetInstance.ts";
import LoadingAnimation from "../../components/LoadingAnimation.tsx";
import NotFound from "../../components/NotFound.tsx";

const ViewAssetInstance = () => {

    const book : AssetData = {
        title: "",
        author: "",
        language: "",
        image: "",
        physicalCondition: "",
        userImage: "",
        userName: "",
        isbn: "",
        location: {
            zipcode: "",
            locality: "",
            province: "",
            country: "",
        },
        reviews: undefined,
    }

    const { bookNumber } = useParams<{ bookNumber: string}>()

    const {handleAssetInstance} = useAssetInstance()

    const [data, setData] = useState(book)
    const [loading, setLoading] = useState(true)
    const [found, setFound] = useState(false)

    const [hasReviewAsLender, setHasReviewAsLender] = useState(false)
    const [hasUserImage, setHasUserImage] = useState(false)
    const [hasDescription, setHasDescription] = useState(false)
    const [assetInstances, setAssetInstances] = useState([])
    const [ownerRating, setOwnerRating] = useState(3)

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setData(book)
            const res: AssetData = await handleAssetInstance(bookNumber)
            setFound((!(res === null || res === undefined)))
            console.log(res)
            setHasUserImage(true)
            setData(res)
            setLoading(false)
        };
        fetchData()
    }, []);


    return (
        <>
            {
                 loading ? (
                     <LoadingAnimation/>
                 ) : (<>
                     {
                         !found ? (
                             <NotFound></NotFound>
                         ) : (
                             <>
                                 <div className="main-class"
                                      style={{display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column'}}>

                                     <div style={{
                                         backgroundColor: '#f0f5f0',
                                         margin: '50px',
                                         borderRadius: '20px',
                                         padding: '20px',
                                         width: '50%'
                                     }}>
                                         <div style={{display: 'flex', flexFlow: 'row', width: '100%', justifyContent: 'start'}}>
                                             <img src={data.image} alt="Book cover"
                                                  style={{
                                                      marginLeft: '0',
                                                      marginRight: '50px',
                                                      height: '500px',
                                                      width: '300px',
                                                      objectFit: 'cover',
                                                      borderRadius: '10px'
                                                  }}/>
                                             <div className="mx-2">

                                                 <h1 className="textOverflow" title="text">
                                                     {data.title}
                                                 </h1>


                                                 <h3 className="textOverflow" id="authorClick" data-author="data-author">
                                                     text
                                                     <span className="text-clickable">
                                                {data.author}
                                            </span>
                                                 </h3>
                                                 <h6 id="physicalConditionClick" className="text-clickable">
                                                     <i><u>
                                                         {data.physicalCondition}
                                                     </u></i>
                                                 </h6>

                                                 <h6 id="languageClick" data-language="data-language"
                                                     style={{color: '#7d7c7c'}}>
                                                     text: <span className="text-clickable"> {data.language} </span>
                                                 </h6>

                                                 <h6 style={{color: '#7d7c7c'}}>
                                                     text: {data.isbn} </h6>


                                                 <div className="container-row" style={{justifyContent: 'start'}}>
                                                     <Link to="/">
                                                         {/*<a href="<c:url value="/user/${assetInstance.owner.id}"/>"*/}
                                                         {/*   style="color: inherit; text-decoration: none;">*/}
                                                         {
                                                             hasUserImage ? (
                                                                 <img className="rounded-circle img-hover-click"
                                                                      style={{width: '25px', height: '25px'}}
                                                                     // src="<c:url value="/getImage/${assetInstance.owner.profilePhoto.id}"/>"
                                                                      src={data.userImage}
                                                                      alt="profile picture"/>
                                                             ) : (
                                                                 <img className="rounded-circle img-hover-click" style={{width: '25px'}}
                                                                     // src="<c:url value="/static/images/user-placeholder.jpeg"/>"
                                                                      src="/profile_placeholder.jpeg"
                                                                      alt="profile picture"/>
                                                             )
                                                         }
                                                         {/*</a>*/}
                                                     </Link>
                                                     {/*<a href="<c:url value="/user/${assetInstance.owner.id}"/>" style="color: inherit; text-decoration: none;">*/}
                                                     <Link to="/">
                                                         <span className="mx-2 text-clickable">{data.userName}</span>
                                                     </Link>
                                                     {/*</a>*/}
                                                     <p>
                                                         {
                                                             hasReviewAsLender ? (
                                                                 <span className="badge bg-success">
                                               {ownerRating} ★
                                            </span>
                                                             ) : (
                                                                 <span className="badge bg-secondary">-.- ★</span>
                                                             )
                                                         }
                                                     </p>
                                                 </div>

                                                 {/*<security:authorize access="isAuthenticated()">*/}
                                                 {/*    <c:url var="borrowAsset" value="/requestAsset/${assetInstance.id}"/>*/}
                                                 {/*    <form:form modelAttribute="borrowAssetForm" method="post"*/}
                                                 {/*               action="${borrowAsset}" enctype="multipart/form-data" id="form"*/}
                                                 {/*               accept-charset="utf-9">*/}
                                                 {/*        <c:choose>*/}
                                                 {/*            <c:when test="${assetInstance.isReservable}">*/}
                                                 {/*                <jsp:include page="../components/calendar.jsp"/>*/}
                                                 {/*            </c:when>*/}
                                                 {/*            <c:otherwise>*/}
                                                 {/*                <jsp:include page="../components/simpleCalendar.jsp"/>*/}
                                                 {/*            </c:otherwise>*/}
                                                 {/*        </c:choose>*/}
                                                 {/*        <input className="btn btn-green" type="submit"*/}
                                                 {/*               value="text"/>*/}
                                                 {/*    </form:form>*/}
                                                 {/*</security:authorize>*/}
                                                 {/*<security:authorize access="!isAuthenticated()">*/}
                                                 {/*    <a className="btn-green" href="<c:url value="/login"/>"*/}
                                                 {/*       style="text-decoration: none; text-align: center">*/}
                                                 {/*        text*/}
                                                 {/*    </a>*/}
                                                 {/*</security:authorize>*/}
                                             </div>
                                         </div>
                                     </div>

                                     <div className="container-row"
                                          style={{minWidth: '50%', width: 'fit-content', marginBottom: '20px'}}>

                                         <div className="container-column" style={{flex: '0 0 100 %'}}>
                                             <div className="card"
                                                  style={{backgroundColor: '#e3e6e3', height: 'fit-content', borderRadius: '25px'}}>
                                                 <div className="card-body">
                                                     <h5 className="card-title" style={{textAlign: 'center'}}>
                                                         text
                                                     </h5>
                                                     <p className="card-text" style={{marginBottom: '-5px'}}>
                                                         text
                                                     </p>
                                                     <h3 className="textOverflow">
                                                         {data.location.zipcode}
                                                     </h3>

                                                     <p className="card-text" style={{marginBottom: '-5px'}}>
                                                         text
                                                     </p>
                                                     <h3 className="textOverflow">
                                                         {data.location.locality}
                                                     </h3>

                                                     <p className="card-text" style={{marginBottom: '-5px'}}>
                                                         text
                                                     </p>
                                                     <h3 className="textOverflow">
                                                         {data.location.province}
                                                     </h3>

                                                     <p className="card-text" style={{marginBottom: '-5px'}}>
                                                         text
                                                     </p>
                                                     <h3 className="textOverflow">
                                                         {data.location.country}
                                                     </h3>
                                                 </div>
                                             </div>
                                         </div>

                                     </div>
                                     <div className="container-row" style={{width: '50 %', marginBottom: '20px'}}>
                                         <div className="container-column" style={{flex: '0 0 100%'}}>
                                             <div className="card"
                                                  style={{backgroundColor: '#e3e6e3', height: 'fit-content', borderRadius: '25px'}}>
                                                 <div className="card-body">
                                                     <h5 className="card-title" style={{textAlign: 'center'}}>
                                                         text
                                                     </h5>
                                                     {hasDescription ? (
                                                         <p style={{
                                                             wordWrap: 'break-word',
                                                             wordBreak: 'break-word',
                                                             maxHeight: '200px',
                                                             overflowY: 'auto'
                                                         }}>
                                                             text
                                                         </p>
                                                     ) : (
                                                         <>
                                                             <h1 className="text-muted text-center mt-5"><i
                                                                 className="bi bi-x-circle"></i></h1>
                                                             <h6 className="text-muted text-center mb-5">
                                                                 text
                                                             </h6>
                                                         </>
                                                     )
                                                     }
                                                 </div>
                                             </div>
                                         </div>
                                     </div>
                                     {
                                         hasDescription ? (
                                             <div className="container-row" style={{width: '50 %', marginBottom: '20px'}}>
                                                 <div className="container-column" style={{flex: '0 0 100%'}}>
                                                     <div className="card" style={{
                                                         backgroundColor: '#e3e6e3',
                                                         height: 'fit-content',
                                                         borderRadius: '25px'
                                                     }}>
                                                         <div className="card-body">
                                                             <h5 className="card-title" style={{textAlign: 'center'}}>
                                                                 text
                                                             </h5>
                                                             <div className="container-row-wrapped">

                                                                 {/*<c:forEach var="assetInstance" items="${assetInstances}">*/}
                                                                 {/*    <*/}
                                                                 {/*    % request.setCharacterEncoding("utf-8"); %>*/}
                                                                 {/*    <jsp:include page="../components/bookCard.jsp">*/}
                                                                 {/*        <jsp:param name="id" value="${assetInstance.id}"/>*/}
                                                                 {/*        <jsp:param name="bookTitle" value="${assetInstance.book.name}"/>*/}
                                                                 {/*        <jsp:param name="bookAuthor" value="${assetInstance.book.author}"/>*/}
                                                                 {/*        <jsp:param name="imageId" value="${assetInstance.image.id}"/>*/}
                                                                 {/*        <jsp:param name="user" value="${assetInstance.owner.name}"/>*/}
                                                                 {/*        <jsp:param name="userPhoto"*/}
                                                                 {/*                   value="${assetInstance.owner.profilePhoto == null? -1:assetInstance.owner.profilePhoto.id}"/>*/}
                                                                 {/*        <jsp:param name="locality" value="${assetInstance.location.locality}"/>*/}
                                                                 {/*        <jsp:param name="province" value="${assetInstance.location.province}"/>*/}
                                                                 {/*        <jsp:param name="country" value="${assetInstance.location.country}"/>*/}
                                                                 {/*        <jsp:param name="physicalCondition"*/}
                                                                 {/*                   value="${assetInstance.physicalCondition}"/>*/}
                                                                 {/*    </jsp:include>*/}
                                                                 {/*</c:forEach>*/}
                                                             </div>
                                                         </div>
                                                     </div>
                                                 </div>
                                             </div>
                                         ) : (
                                             <></>
                                         )
                                     }


                                     <div className="container-row" style={{width: '50 %', marginBottom: '20px'}}>
                                         <div className="container-column" style={{flex: '0 0 100%'}}>
                                             <div className="card p-2"
                                                  style={{backgroundColor: '#e3e6e3', height: 'fit-content', borderRadius: '25px'}}>
                                                 <div className="container-row">
                                                     <div className=""
                                                          style={{
                                                              flexGrow: '1',
                                                              display: 'flex',
                                                              justifyContent: 'center',
                                                              alignItems: 'center'
                                                          }}
                                                          id="scrollspyRating">
                                                         <div className="container-column">
                                                             <h1 className="text-muted text-center mt-5"><i
                                                                 className="bi bi-x-circle"></i></h1>
                                                             <h6 className="text-muted text-center mb-5">
                                                                 text
                                                             </h6>
                                                             {/*        {*/}
                                                             {/*            hasReviews ? (<></>) : (<></>)*/}
                                                             {/*        }*/}
                                                             {/*        <c:if test="${hasReviews}">*/}
                                                             {/*            <div className="container-row" style="justify-content: center !important;">*/}
                                                             {/*                <h1>*/}
                                                             {/*            <span id="rating-value">text</span><small>/5</small>*/}
                                                             {/*                </h1>*/}
                                                             {/*            </div>*/}
                                                             {/*            <div className="container-row mb-2" style="justify-content: center !important;"*/}
                                                             {/*                 id="stars-container" data-rating="<c:out value="*/}
                                                             {/*                 ${assetInstanceReviewAverage}"/>">*/}
                                                             {/*                <*/}
                                                             {/*                %--Stars will be added via JS based on the rating--%>*/}
                                                             {/*            </div>*/}
                                                             {/*            <div className="user-profile-reviews-pane">*/}

                                                             {/*                <c:forEach var="review" items="${assetInstanceReviewPage.list}">*/}
                                                             {/*                    <jsp:include page="../components/reviewCardProfile.jsp">*/}
                                                             {/*                        <jsp:param name="review" value="${review.review}"/>*/}
                                                             {/*                        <jsp:param name="userId" value="${review.reviewer.id}"/>*/}
                                                             {/*                        <jsp:param name="reviewer" value="${review.reviewer.name}"/>*/}
                                                             {/*                        <jsp:param name="role" value="${review.reviewer.behavior}"/>*/}
                                                             {/*                        <jsp:param name="imgSrc"*/}
                                                             {/*                                   value="${review.reviewer.profilePhoto == null ? -1 : review.reviewer.profilePhoto.id}"/>*/}
                                                             {/*                    </jsp:include>*/}
                                                             {/*                </c:forEach>*/}
                                                             {/*            </div>*/}
                                                             {/*            <div className="container-row-wrapped"*/}
                                                             {/*                 style="margin-top: 25px; margin-bottom: 25px; width: 100%;">*/}
                                                             {/*                <div>*/}
                                                             {/*                    <nav aria-label="Page navigation example">*/}
                                                             {/*                        <ul className="pagination justify-content-center align-items-center">*/}
                                                             {/*                            <li className="page-item">*/}
                                                             {/*                                <button type="button"*/}
                                                             {/*                                        className="btn mx-5 pagination-button ${assetInstanceReviewPage.currentPage != 1 ? "" : "*/}
                                                             {/*                                        disabled"}"*/}
                                                             {/*                                        id="previousPageButton"*/}
                                                             {/*                                        style="border-color: rgba(255, 255, 255, 0)"*/}
                                                             {/*                                        onclick="window.location.href = '<c:url*/}
                                                             {/*                                        value="/info/${assetInstance.id}*/}
                                                             {/*                                ?reviewPage=${assetInstanceReviewPage.currentPage - 1}#scrollspyRating"/>'"*/}
                                                             {/*                                >*/}
                                                             {/*                                <i className="bi bi-chevron-left"></i>*/}
                                                             {/*                                text*/}
                                                             {/*                            </button>*/}
                                                             {/*                        </li>*/}

                                                             {/*                        <li >*/}
                                                             {/*                            <c:out value="${assetInstanceReviewPage.currentPage}"/>*/}
                                                             {/*                            / <c:out*/}
                                                             {/*                            value="${assetInstanceReviewPage.totalPages}"/>*/}
                                                             {/*                        </li>*/}

                                                             {/*                        <li className="page-item">*/}
                                                             {/*                            <button type="button"*/}
                                                             {/*                                    className="btn mx-5 pagination-button ${assetInstanceReviewPage.currentPage < assetInstanceReviewPage.totalPages ? "" : "*/}
                                                             {/*                                    disabled"}"*/}
                                                             {/*                                    id="nextPageButton"*/}
                                                             {/*                                    style="border-color: rgba(255, 255, 255, 0)"*/}
                                                             {/*                                    onclick="window.location.href = '<c:url*/}
                                                             {/*                                        value="/info/${assetInstance.id}*/}
                                                             {/*                            ?reviewPage=${assetInstanceReviewPage.currentPage + 1}#scrollspyRating"/>'"*/}
                                                             {/*                            >*/}
                                                             {/*                            text <i*/}
                                                             {/*                            className="bi bi-chevron-right"></i>*/}
                                                             {/*                        </button>*/}
                                                             {/*                    </li>*/}
                                                             {/*                </ul>*/}
                                                             {/*            </nav>*/}
                                                             {/*        </div>*/}
                                                             {/*    </div>*/}

                                                             {/*</c:if>*/}
                                                             {/*<c:if test="${!hasReviews}">*/}
                                                             {/*    <h1 className="text-muted text-center mt-5"><i className="bi bi-x-circle"></i></h1>*/}
                                                             {/*    <h6 className="text-muted text-center mb-5">*/}
                                                             {/*        text*/}
                                                             {/*    </h6>*/}
                                                             {/*</c:if>*/}
                                                         </div>
                                                     </div>
                                                 </div>

                                             </div>
                                         </div>
                                     </div>

                                 </div>
                             </>
                         )
                     }
                     </>
                )
            }
        </>
    )
}
export default ViewAssetInstance;