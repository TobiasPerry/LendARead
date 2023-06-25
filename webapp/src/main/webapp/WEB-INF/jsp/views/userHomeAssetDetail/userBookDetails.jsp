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
        <c:if test="${asset.isBorrowedInstance}">

            <c:choose>
                <c:when test="${asset.lendingState.isRejected}">
                    <div style="background-color: darkred; color: white; border-radius: 25px; padding: 10px; display: inline-block; font-weight: bold; text-transform: uppercase; margin-left: 5px"
                         data-bs-toggle="tooltip" data-bs-placement="top" title="This lending is archived">
                        <spring:message code="userHomeView.rejected"/>
                    </div>
                </c:when>
                <c:when test="${asset.lendingState.isFinished}">
                    <div style="background-color: darkgray; color: white; border-radius: 25px; padding: 10px; display: inline-block; font-weight: bold; text-transform: uppercase; margin-left: 5px"
                         data-bs-toggle="tooltip" data-bs-placement="top" title="This lending is archived">
                        <spring:message code="userHomeView.finished"/>
                    </div>
                </c:when>
            </c:choose>
        </c:if>
    </div>
    <div class="main-class">
        <div class="asset-details-row">
            <div class="island asset-instance-info">
                <img src="<c:url value="/getImage/${asset.image.id}"/>" class="mx-3" alt="Book cover">
                <div class="asset-instance-text mx-2">
                    <h1 class="textOverflow"><c:out
                            value="${asset.book.name} "/>"><c:out
                            value="${asset.book.name} "/></h1>
                    <h3 class="textOverflow">
                        <spring:message
                                code="assetView.by"/>
                        <c:out
                                value="${asset.book.author}"/></h3>
                    <h6>
                        <i>
                            <u><spring:message code="enum.${asset.physicalCondition}"/></u>
                        </i>
                    </h6>
                    <h6><spring:message code="assetView.language"/>: <c:out
                            value="${asset.book.language}"/></h6>
                    <h6><spring:message code="assetView.isbn"/>: <c:out
                            value="${asset.book.isbn}"/></h6>
                </div>
            </div>

            <div class="asset-instances-menu-column">
                <div class="status-menu island">
                    <h1 class="textOverflow"><spring:message code="statusChange"/>: <c:out value=""/></h1>
                    <c:choose>
                        <c:when test="${table == 'lended_books'}">
                            <h6><spring:message code="return_date"/>: <c:out
                                    value="${asset.dueDate}"/></h6>
                            <h6><spring:message code="borrower_name"/>: <c:out
                                    value="${asset.owner.name}"/></h6>
                            <c:if test="${asset.lendingState.isRejected}">
                                <h6><spring:message
                                        code="userHomeView.rejected"/></h6>
                            </c:if>
                            <c:if test="${asset.lendingState.isFinished}">
                                <h6><spring:message
                                        code="userHomeView.finished"/></h6>
                            </c:if>
                        </c:when>
                        <c:when test="${table == 'borrowed_books'}">
                            <h6><spring:message code="return_date"/>: <c:out
                                    value="${asset.dueDate}"/></h6>
                            <h6><spring:message code="owner_name"/>: <c:out
                                    value="${asset.owner.name}"/></h6>
                        </c:when>
                        <c:when test="${table == 'my_books'}">
                            <h6><spring:message
                                    code="addAssetView.locationInfo"/> <c:out value="${asset.location}"/></h6>
                            <h6><spring:message code="addAssetView.maxDays"/>:
                                <c:out value="${asset.maxDays}"/></h6>
                        </c:when>
                    </c:choose>
                </div>
                <c:if test="${!(table == 'borrowed_books' || (table == 'lended_books' &&  asset.lendingState.getIsFinished()))}">
                    <div class="options-island island">
                        <h1 class="textOverflow"><spring:message code="userAssetDetailView.options"/></h1>
                        <div class="options-menu-container">
                            <c:choose>
                                <c:when test="${table == 'my_books'}">
                                    <jsp:include page="myBookOptions.jsp">
                                        <jsp:param name="asset" value="${asset}"/>
                                        <jsp:param name="lendings" value="${lendings}"/>
                                    </jsp:include>
                                </c:when>
                                <c:when test="${table == 'lended_books'}">
                                    <jsp:include page="lendedBookOptions.jsp">
                                        <jsp:param name="asset" value="${asset}"/>
                                    </jsp:include>
                                </c:when>
                                <c:when test="${table == 'borrowed_books' && !asset.lendingState.isDelivered()}">
                                    <jsp:include page="borrowedBookOptions.jsp">
                                        <jsp:param name="asset" value="${asset}"/>
                                    </jsp:include>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    <div class="asset-details-row">
        <div class="island future-lending-island">
            <h1>Future Lendings</h1>
            <div class="future-lending-list">

            </div>
        </div>
    </div>
</div>
</body>
</html>
