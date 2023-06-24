<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 4/2/23
  Time: 7:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><c:out value="${assetInstance.book.name}"/> <spring:message
            code="assetView.by"/> <c:out
            value="${assetInstance.book.author}"/>
    </title>
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro-bg.ico'/>" type="image/x-icon">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"></script>

    <%--    Icons--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
          integrity="sha384-afdagadgdagdagda" crossorigin="anonymous">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script src="<c:url value="/static/javaScript/map.js"/>"></script>
    <script src="<c:url value="/static/javaScript/assetView.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/static/css/assetView.css"/>">

</head>
<body data-path="${path}" class=" body-class" data-maxDays="<c:out value="${assetInstance.maxDays}" /> ">
<jsp:include page="../components/navBar.jsp"/>

<div class="main-class" style="display: flex; justify-content: center;align-items: center;flex-direction: column;">
    <div style="background-color: #f0f5f0; margin: 50px; border-radius: 20px; padding: 20px; width: 50%">
        <div style="display: flex; flex-flow: row; width: 100%; justify-content: start;">
            <img src="<c:url value="/getImage/${assetInstance.image.id}"/>" alt="Book cover"
                 style="margin-left: 0; margin-right: 50px; height: 500px; width: 300px; object-fit: cover; border-radius: 10px">
            <div class="mx-2">

                <h1 class="textOverflow" title="<c:out value="${assetInstance.book.name}"/>"><c:out
                        value="${assetInstance.book.name} "/></h1>


                <h3 class="textOverflow" id="authorClick" data-author="<c:out value="${assetInstance.book.author}"/>">
                    <spring:message
                            code="assetView.by"/>
                    <span class="text-clickable">
                        <c:out value="${assetInstance.book.author}"/>
                    </span>
                </h3>
                <h6 id="physicalConditionClick" class="text-clickable"
                    data-physicalcondition="<c:out value="${assetInstance.physicalCondition}"/>">
                    <i><u><spring:message code="enum.${assetInstance.physicalCondition}"/></u></i>
                </h6>

                <h6 id="languageClick" data-language="<c:out value="${assetInstance.book.language}"/>"
                    style="color: #7d7c7c">
                    <spring:message code="assetView.language"/>: <span class="text-clickable"> <c:out
                        value="${assetInstance.book.language}"/></span>
                </h6>

                <h6 style="color: #7d7c7c"><spring:message code="assetView.isbn"/>: <c:out
                        value="${assetInstance.book.isbn}"/></h6>

                <c:url var="discovery" value="/discovery"/>
                <form:form modelAttribute="searchFilterSortForm" method="get" action="${discovery}" id="formSearch">
                </form:form>

                <security:authorize access="isAuthenticated()">
                    <c:url var="borrowAsset" value="/requestAsset/${assetInstance.id}"/>
                    <form:form modelAttribute="borrowAssetForm" method="post"
                               action="${borrowAsset}" enctype="multipart/form-data" id="form" accept-charset="utf-9">
                        <jsp:include page="../components/calendar.jsp"/>
                        <input class="btn btn-green" type="submit"
                               value="<spring:message code="assetView.borrowButton"/>">
                    </form:form>
                </security:authorize>
                <security:authorize access="!isAuthenticated()">
                    <a class="btn-green" href="<c:url value="/login"/>"
                       style="text-decoration: none; text-align: center"><spring:message
                            code="assetView.borrowButtonNotLoggedIn"/></a>
                </security:authorize>
            </div>
        </div>
    </div>

    <div class="container-row" style="min-width: 50%; width: fit-content; margin-bottom: 20px">

        <div class="container-column" style="flex: 0 0 100%">
            <div class="card" style="background-color:#e3e6e3;height: fit-content; border-radius: 25px">
                <div class="card-body">
                    <h5 class="card-title" style="text-align: center"><spring:message
                            code="assetView.locationTitle"/></h5>
                    <p class="card-text" style="margin-bottom: -5px"><spring:message code="assetView.zipcode"/></p>
                    <h3 class="textOverflow"><c:out value="${assetInstance.location.zipcode}"/></h3>

                    <p class="card-text" style="margin-bottom: -5px"><spring:message code="assetView.locality"/></p>
                    <h3 class="textOverflow"><c:out value="${assetInstance.location.locality}"/></h3>

                    <p class="card-text" style="margin-bottom: -5px"><spring:message code="assetView.province"/></p>
                    <h3 class="textOverflow"><c:out value="${assetInstance.location.province}"/></h3>

                    <p class="card-text" style="margin-bottom: -5px"><spring:message code="assetView.country"/></p>
                    <h3 class="textOverflow"><c:out value="${assetInstance.location.country}"/></h3>
                </div>
            </div>
        </div>

    </div>
    <div class="container-row" style="width: 50%; margin-bottom: 20px">
        <div class="container-column" style="flex: 0 0 100%">
            <div class="card" style="background-color:#e3e6e3;height: fit-content; border-radius: 25px">
                <div class="card-body">
                    <h5 class="card-title" style="text-align: center"><spring:message
                            code="assetView.description"/></h5>
                    <c:if test="${hasDescription}">
                        <p style="word-wrap: break-word; word-break: break-word; max-height: 200px; overflow-y: auto;">
                            <c:out value="${assetInstance.description}"/>
                        </p>
                    </c:if>
                    <c:if test="${!hasDescription}">
                        <h1 class="text-muted text-center mt-5"><i class="bi bi-x-circle"></i></h1>
                        <h6 class="text-muted text-center mb-5"><spring:message
                                code="assetView.noDescription"/></h6>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <div class="container-row" style="width: 50%; margin-bottom: 20px">
        <div class="container-column" style="flex: 0 0 100%">
            <div class="card p-2" style="background-color:#e3e6e3;height: fit-content; border-radius: 25px;">
                <div class="container-row">
                    <div class="" style="flex-grow: 1; display: flex; justify-content: center;align-items: center;"
                         id="scrollspyRating">
                        <div class="container-column">
                            <c:if test="${hasReviews}">
                                <div class="container-row" style="justify-content: center !important;">
                                    <h1>
                                        <span id="rating-value"><c:out
                                                value="${assetInstanceReviewAverage}"/></span><small>/5</small>
                                    </h1>
                                </div>
                                <div class="container-row mb-2" style="justify-content: center !important;"
                                     id="stars-container" data-rating="<c:out value="${assetInstanceReviewAverage}"/>">
                                        <%--Stars will be added via JS based on the rating--%>
                                </div>
                                <div class="user-profile-reviews-pane">

                                <c:forEach var="review" items="${assetInstanceReviewPage.list}">
                                    <jsp:include page="../components/reviewCardProfile.jsp">
                                        <jsp:param name="review" value="${review.review}"/>
                                        <jsp:param name="userId" value="${review.reviewer.id}"/>
                                        <jsp:param name="reviewer" value="${review.reviewer.name}"/>
                                        <jsp:param name="role" value="${review.reviewer.behavior}"/>
                                        <jsp:param name="imgSrc"
                                                   value="${review.reviewer.profilePhoto == null ? -1 : review.reviewer.profilePhoto.id}"/>
                                    </jsp:include>
                                </c:forEach>
                                </div>
                                <div class="container-row-wrapped"
                                     style="margin-top: 25px; margin-bottom: 25px; width: 100%;">
                                    <div>
                                        <nav aria-label="Page navigation example">
                                            <ul class="pagination justify-content-center align-items-center">
                                                <li class="page-item">
                                                    <button type="button"
                                                            class="btn mx-5 pagination-button ${assetInstanceReviewPage.currentPage != 1 ? "" : "disabled"}"
                                                            id="previousPageButton"
                                                            style="border-color: rgba(255, 255, 255, 0)"
                                                            onclick="window.location.href = '<c:url
                                                                    value="/info/${assetInstance.id}?reviewPage=${assetInstanceReviewPage.currentPage - 1}#scrollspyRating"/>'"
                                                    >
                                                        <i class="bi bi-chevron-left"></i> <spring:message
                                                            code="paginationButton.previous"/>
                                                    </button>
                                                </li>

                                                <li>
                                                    <c:out value="${assetInstanceReviewPage.currentPage}"/> / <c:out
                                                        value="${assetInstanceReviewPage.totalPages}"/>
                                                </li>

                                                <li class="page-item">
                                                    <button type="button"
                                                            class="btn mx-5 pagination-button ${assetInstanceReviewPage.currentPage < assetInstanceReviewPage.totalPages ? "" : "disabled"}"
                                                            id="nextPageButton"
                                                            style="border-color: rgba(255, 255, 255, 0)"
                                                            onclick="window.location.href = '<c:url
                                                                    value="/info/${assetInstance.id}?reviewPage=${assetInstanceReviewPage.currentPage + 1}#scrollspyRating"/>'"
                                                    >
                                                        <spring:message code="paginationButton.next"/> <i
                                                            class="bi bi-chevron-right"></i>
                                                    </button>
                                                </li>
                                            </ul>
                                        </nav>
                                    </div>
                                </div>

                            </c:if>
                            <c:if test="${!hasReviews}">
                                <h1 class="text-muted text-center mt-5"><i class="bi bi-x-circle"></i></h1>
                                <h6 class="text-muted text-center mb-5"><spring:message
                                        code="assetView.noReviews"/></h6>
                            </c:if>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

</div>

<spring:message code="borrowAssetView.borrowBookModal.title" var="modalTitle"/>
<spring:message code="borrowAssetView.borrowBookModal.text" var="modalText"/>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/modal.jsp">
    <jsp:param name="modalTitle" value="${modalTitle}"/>
    <jsp:param name="text" value="${modalText}"/>
    <jsp:param name="redirectionUrl" value="/discovery"/>
</jsp:include>

</body>
</html>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Assuming assetInstanceReviewAverage contains the average rating value
        var assetInstanceReviewAverage = document.getElementById("stars-container").getAttribute("data-rating");

        // Get the stars-container element
        var starsContainer = document.getElementById("stars-container");

        // Calculate the number of filled stars
        var filledStars = Math.floor(assetInstanceReviewAverage);

        // Calculate the number of half stars
        var halfStar = Math.ceil(assetInstanceReviewAverage) - filledStars;

        // Calculate the number of empty stars
        var emptyStars = 5 - filledStars - halfStar;

        // Create the HTML elements for filled stars
        for (var i = 0; i < filledStars; i++) {
            var filledStar = document.createElement("i");
            filledStar.className = "bi bi-star-fill";
            filledStar.style.fontSize = "30px";
            filledStar.style.color = "#CC9900";
            starsContainer.appendChild(filledStar);
        }

        // Create the HTML element for the half star
        if (halfStar) {
            var halfStar = document.createElement("i");
            halfStar.className = "bi bi-star-half";
            halfStar.style.fontSize = "30px";
            halfStar.style.color = "#CC9900";
            starsContainer.appendChild(halfStar);
        }

        // Create the HTML elements for empty stars
        for (var j = 0; j < emptyStars; j++) {
            var emptyStar = document.createElement("i");
            emptyStar.className = "bi bi-star";
            emptyStar.style.fontSize = "30px";
            emptyStar.style.color = "#CC9900";
            starsContainer.appendChild(emptyStar);
        }

        // Get the rating value element
        var ratingValueElement = document.getElementById("rating-value");

        // Get the current rating value
        var ratingValue = parseFloat(ratingValueElement.textContent);

        // Format the rating value to have one decimal place
        var formattedRatingValue = ratingValue.toFixed(1);

        // Update the rating value element with the formatted value
        ratingValueElement.textContent = formattedRatingValue;

    });
</script>