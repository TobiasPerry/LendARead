<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <title><spring:message code="userHome.head.title"/></title>

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
    <link href="<c:url value="/static/css/userHomeView.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>

    <link href="https://fonts.googleapis.com/css?family=Overpass:400,700|Roboto:400,700" rel="stylesheet">
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro-bg.ico'/>" type="image/x-icon">
    <script src="<c:url value="/static/javaScript/userHomeView.js"/>" defer></script>
</head>

<body data-path="${path}" class="body-class">


<jsp:include page="../../components/navBar.jsp">
    <jsp:param name="showSearchbar" value="true"/>
</jsp:include>

<div class="container">
    <div class="container-flex">
        <h1><spring:message code="greeting" arguments="${userEmail}"/></h1>
        <div class="row">
            <div class="sidebar table-selector">
                <div class="list-group">
                    <div class="list-group">
                        <form action="<c:url value="/userHomeTab" />" method="get">
                            <input type="hidden" name="type" value="my_books">
                            <button type="submit"
                                    class="list-group-item list-group-item-action button-select <c:if test='${table == "my_books"}'>button-select-active</c:if>">
                                <spring:message code="my_books"/></button>
                        </form>
                        <form action="<c:url value="/userHomeTab" />" method="get">
                            <input type="hidden" name="type" value="lended_books">
                            <button type="submit"
                                    class="list-group-item list-group-item-action button-select <c:if test='${table == "lended_books"}'>button-select-active</c:if>">
                                <spring:message code="lended_books"/></button>
                        </form>
                        <form action="<c:url value="/userHomeTab" />" method="get">
                            <input type="hidden" name="type" value="borrowed_books">
                            <button type="submit"
                                    class="list-group-item list-group-item-action button-select <c:if test='${table == "borrowed_books"}'>button-select-active</c:if>">
                                <spring:message code="borrowed_books"/></button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="content">
                <c:choose>
                    <c:when test="${table == 'my_books'}">
                        <% request.setCharacterEncoding("utf-8"); %>
                        <jsp:include page="myBooksTable.jsp">
                            <jsp:param name="userAssets" value="${userAssets}"/>
                        </jsp:include>
                    </c:when>
                    <c:when test="${table == 'lended_books'}">
                        <% request.setCharacterEncoding("utf-8"); %>
                        <jsp:include page="lendedBooksTable.jsp">
                            <jsp:param name="isLender" value="${isLender}"/>
                            <jsp:param name="userAssets" value="${userAssets}"/>
                        </jsp:include>
                    </c:when>
                    <c:when test="${table == 'borrowed_books'}">
                        <% request.setCharacterEncoding("utf-8"); %>
                        <jsp:include page="borrowedBooksTable.jsp">
                            <jsp:param name="userAssets" value="${userAssets}"/>
                        </jsp:include>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>

        <% request.setCharacterEncoding("utf-8"); %>
    <jsp:include page="../userHomeAssetDetail/deleteBookModal.jsp">
        <jsp:param name="modalType" value="${modalType}"/>
        <jsp:param name="assetId" value="${assetId}"/>
    </jsp:include>

</body>
</html>