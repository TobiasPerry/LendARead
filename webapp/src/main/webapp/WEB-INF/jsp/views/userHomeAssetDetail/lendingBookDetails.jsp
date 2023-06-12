<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title><spring:message code="userAssetDetailView.title"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/userHomeView.css"/>" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Overpass:400,700|Roboto:400,700" rel="stylesheet">
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro-bg.ico'/>" type="image/x-icon">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link href="<c:url value="/static/css/assetView.css"/>" rel="stylesheet"/>
</head>
<body data-path="${path}" class="body-class">

<jsp:include page="../../components/navBar.jsp">
    <jsp:param name="showSearchbar" value="true"/>
</jsp:include>

<div>
    <div class="back-button d-flex align-items-center">
        <a href="<c:url value='/userHomeReturn' />" class="btn-breadcrumb">
            <i class="fas fa-arrow-left"></i>
        </a>
        <h2 class="textOverflow mb-0 ml-2 mr-2"><spring:message code="userAssetDetailView.${table}"/></h2>

        <c:choose>
            <c:when test="${lending.active.isRejected}">
                <div style="background-color: darkred; color: white; border-radius: 25px; padding: 10px; display: inline-block; font-weight: bold; text-transform: uppercase; margin-left: 5px"
                     data-bs-toggle="tooltip" data-bs-placement="top" title="This lending is archived">
                    <spring:message code="userHomeView.rejected"/>
                </div>
            </c:when>
            <c:when test="${lending.active.isFinished}">
                <div style="background-color: darkgray; color: white; border-radius: 25px; padding: 10px; display: inline-block; font-weight: bold; text-transform: uppercase; margin-left: 5px"
                     data-bs-toggle="tooltip" data-bs-placement="top" title="This lending is archived">
                    <spring:message code="userHomeView.finished"/>
                </div>
            </c:when>
        </c:choose>
    </div>
    <div class="main-class"
         style="display: flex; justify-content: center;align-items: center;flex-direction: column;">
        <div class="container-row-wrapped"
             style="display: flex; flex-direction: row; align-items: flex-start; justify-content: center;">
            <div style="background-color: #f0f5f0; border-radius: 20px; margin: 20px; padding: 20px; max-width: 50%; min-width: 650px">
                <div style="display: flex; flex-flow: row; width: 100%; justify-content: start;">
                    <img src="<c:url value="/getImage/${lending.assetInstance.image.id}"/>" class="mx-3"
                         alt="Book cover"
                         style="height: 500px; width: 300px; object-fit: cover; border-radius: 10px">
                    <div class="mx-2" style="min-width: 400px">
                        <h1 class="textOverflow" style="max-width: min-content " title="<c:out
                                value="${lending.assetInstance.book.name} "/>"><c:out
                                value="${lending.assetInstance.book.name} "/></h1>
                        <h3 class="textOverflow"
                            style="max-width: min-content;">
                            <spring:message
                                    code="assetView.by"/>
                            <c:out
                                    value="${lending.assetInstance.book.author}"/></h3>
                        <h6>
                            <i>
                                <u><spring:message code="enum.${lending.assetInstance.physicalCondition}"/></u>
                            </i>
                        </h6>
                        <h6 style="color: #7d7c7c"><spring:message code="assetView.language"/>: <c:out
                                value="${lending.assetInstance.book.language}"/></h6>
                        <h6 style="color: #7d7c7c"><spring:message code="assetView.isbn"/>: <c:out
                                value="${lending.assetInstance.book.isbn}"/></h6>

                    </div>

                </div>

            </div>
            <div style="display: flex; flex-direction: column; justify-content: space-between; width: 300px;">
                <div style="background-color: #f0f5f0; border-radius: 20px; margin: 20px; padding: 20px; width: 300px;">
                    <h1 class="textOverflow"><spring:message code="statusChange"/>: <c:out value=""/></h1>
                    <c:choose>
                        <c:when test="${table == 'lended_books'}">
                            <h6 style="color: #7d7c7c; font-weight: bold"><spring:message code="return_date"/>: <c:out
                                    value="${lending.devolutionDate}"/></h6>
                            <h6 style="color: #7d7c7c; font-weight: bold"><spring:message code="borrower_name"/>: <c:out
                                    value="${lending.userReference.name}"/></h6>
                            <c:if test="${lending.active.isRejected}">
                                <h6 style="color: #7d7c7c; font-weight: bolder"><spring:message
                                        code="userHomeView.rejected"/></h6>
                            </c:if>
                            <c:if test="${lending.active.isFinished}">
                                <h6 style="color: #7d7c7c; font-weight: bolder"><spring:message
                                        code="userHomeView.finished"/></h6>
                            </c:if>
                        </c:when>
                        <c:when test="${table == 'borrowed_books'}">
                            <h6 style="color: #7d7c7c; font-weight: bold"><spring:message code="return_date"/>: <c:out
                                    value="${lending.devolutionDate}"/></h6>
                            <h6 style="color: #7d7c7c; font-weight: bold"><spring:message code="owner_name"/>: <c:out
                                    value="${lending.assetInstance.owner.name}"/></h6>
                        </c:when>
                    </c:choose>
                </div>
                <c:if test="${!(table == 'borrowed_books' || (table == 'lended_books' && ( lending.active.getIsFinished() || lending.active.getIsRejected())))}">
                    <div style="background-color: #f0f5f0; border-radius: 20px; margin: 20px; padding: 20px; width: 300px;">
                        <h1 class="textOverflow"><spring:message code="userAssetDetailView.options"/></h1>
                        <c:choose>
                            <c:when test="${table == 'lended_books'}">
                                <jsp:include page="lendedBookOptions.jsp">
                                    <jsp:param name="lending" value="${lending}"/>
                                </jsp:include>
                            </c:when>
                            <c:when test="${table == 'borrowed_books'}">
                                <jsp:include page="borrowedBookOptions.jsp">
                                    <jsp:param name="lending" value="${lending}"/>
                                </jsp:include>
                            </c:when>
                        </c:choose>
                    </div>
                </c:if>
            </div>

        </div>
        <c:if test="${lending.active.isFinished && lending.assetInstanceReview != null && lending.userReviews != null}">
        <div class="container-row" style="justify-content: center">
            <div class="card"  style="background-color: #f0f5f0; border-radius: 20px;">
                <div class="card-body">
                    <h1 class="textOverflow"><spring:message code="lendingBookDetails.review" /></h1>

                    <div class="container-column">
                        <c:forEach items="${lending.userReviews}" var="review" >
                            <c:if test="${review.reviewer.id == lending.userReference.id}">
                                <h3><spring:message code="lendingBookDetails.borrowerReview"/> </h3>
                            </c:if>
                            <c:if test="${review.reviewer.id == lending.assetInstance.owner.id}">
                                <h3><spring:message code="lendingBookDetails.ownerReview"/> </h3>
                            </c:if>
                            <jsp:include page="../../components/reviewCard.jsp">
                                <jsp:param name="review" value="${review.review}"/>
                                <jsp:param name="reviewer" value="${review.reviewer.name}"/>
                                <jsp:param name="rating" value="${review.rating}"/>
                            </jsp:include>
                        </c:forEach>
                        <c:if test="${lending.assetInstanceReview != null}">
                        <h3><spring:message code="lendingBookDetails.assetReview"/> </h3>
                        <jsp:include page="../../components/reviewCard.jsp">
                            <jsp:param name="review" value="${lending.assetInstanceReview.review}"/>
                            <jsp:param name="reviewer" value="${lending.assetInstanceReview.reviewer.name}"/>
                            <jsp:param name="rating" value="${lending.assetInstanceReview.rating}"/>
                        </jsp:include>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        </c:if>
    </div>
</div>
</body>
</html>
