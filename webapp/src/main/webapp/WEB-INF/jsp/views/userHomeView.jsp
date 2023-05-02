<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <title><spring:message code="userHome.head.title"/></title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/userHomeView.css"/>" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Overpass:400,700|Roboto:400,700" rel="stylesheet">
</head>

<body data-path="${path}" class = "body-class">

<jsp:include page="../components/navBar.jsp"/>
<div class="container">
    <div class="container-flex">
        <h1> <spring:message code="greeting" arguments="${userEmail}" /></h1>
        <div class="row">
            <div class="sidebar">
                <div class="list-group">
                    <div class="list-group">
                        <form action="/changeTable" method="get">
                            <input type="hidden" name="type" value="my_books">
                            <button type="submit" class="list-group-item list-group-item-action button-select <c:if test='${table == "my_books"}'>button-select-active</c:if>"><spring:message code="my_books" /></button>
                        </form>
                        <form action="/changeTable" method="get">
                            <input type="hidden" name="type" value="lended_books">
                            <button type="submit" class="list-group-item list-group-item-action button-select <c:if test='${table == "lended_books"}'>button-select-active</c:if>"><spring:message code="lended_books" /></button>
                        </form>
                        <form action="/changeTable" method="get">
                            <input type="hidden" name="type" value="borrowed_books">
                            <button type="submit" class="list-group-item list-group-item-action button-select <c:if test='${table == "borrowed_books"}'>button-select-active</c:if>"><spring:message code="borrowed_books" /></button>
                        </form>
                    </div>

                </div>
            </div>
            <div class="content">
                <c:choose>
                    <c:when test="${table == 'my_books'}">
                        <div class="table-title">
                            <h2><spring:message code="my_books" /></h2>
                            <div class="table-container">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th><spring:message code="image" /></th>
                                        <th><spring:message code="book_name" /></th>
                                        <th><spring:message code="author" /></th>
                                        <th><spring:message code="language" /></th>
                                            <%--            <th><spring:message code="description" /></th>--%>
                                        <th><spring:message code="status" /></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${userAssets.myBooks}" var="asset">
                                        <tr>
                                            <td>
                                                <div class="image-container">
                                                    <img class="image" src="<c:url value='/getImage/${asset.imageId}'/>" alt="${asset.book.name}" />
                                                    <form action="/deleteAssetModal?assetId=${asset.id}" method="post" style="display:inline;">
                                                        <button class="btn btn-link p-0 icon-delete" type="submit">
                                                            <i class="fas fa-trash icon-style"></i>
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
                                            <td>${asset.book.name}</td>
                                            <td>${asset.book.author}</td>
                                            <td>${asset.book.language}</td>
                                                <%--              <td>${asset.description}</td>--%>
                                            <td>
                                                <form action="/showChangeVisibilityModal?assetId=${asset.id}" method="post">
                                                    <button class="button-status" type="submit">
                                                        <c:choose>
                                                            <c:when test="${asset.assetState.isPublic()}">
                                                                <spring:message code="public" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <spring:message code="private" />
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${table == 'lended_books'}">
                        <c:choose>
                            <c:when test="${isLender}">
                                <div class="table-title">
                                    <h2><spring:message code="lended_books" /></h2>
                                    <div class="table-container">
                                        <table class="table">
                                            <thead>
                                            <tr>
                                                <th><spring:message code="image" /></th>
                                                <th><spring:message code="book_name" /></th>
                                                <th><spring:message code="author" /></th>
                                                <th><spring:message code="isbn" /></th>
                                                <th><spring:message code="language" /></th>
                                                <th><spring:message code="expected_retrieval_date" /></th>
                                                <th><spring:message code="borrower_name" /></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${userAssets.lendedBooks}" var="asset">
                                                <tr>
                                                    <td> <img class="image" src="<c:url value="/getImage/${asset.imageId}"/>"  alt="${asset.book.name}" /></td>
                                                    <td>${asset.book.name}</td>
                                                    <td>${asset.book.author}</td>
                                                    <td>${asset.book.isbn}</td>
                                                    <td>${asset.book.language}</td>
                                                    <td>${asset.dueDate}</td>
                                                    <td>${asset.borrower}</td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="promo-box">
                                    <h2><spring:message code="become_lender.title" /></h2>
                                    <p><spring:message code="become_lender.subtitle" /></p>
                                    <form action="/changeRole" method="post">
                                        <button type="submit" class="button-status"><spring:message code="become_lender.button" /></button>
                                    </form>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${table == 'borrowed_books'}">
                        <div class="table-title">
                            <h2><spring:message code="borrowed_books" /></h2>
                            <div class="table-container">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th><spring:message code="image" /></th>
                                        <th><spring:message code="book_name" /></th>
                                        <th><spring:message code="author" /></th>
                                        <th><spring:message code="return_date" /></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${userAssets.borrowedBooks}" var="asset">
                                        <tr>
                                            <td> <img class="image" src="<c:url value="/getImage/${asset.imageId}"/>"  alt="${asset.book.name}" /></td>
                                            <td>${asset.book.name}</td>
                                            <td>${asset.book.author}</td>
                                            <td>${asset.dueDate}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        </div>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>

<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/userHomeModal.jsp">
    <jsp:param name="modalType" value="${modalType}"/>
    <jsp:param name="assetId" value="${assetId}"/>
</jsp:include>

</body>
</html>
<script>
    function toggleDropdown(event) {
        event.stopPropagation();
        var dropdownMenu = document.getElementById("dropdownMenu");
        if (dropdownMenu.style.display === "none") {
            dropdownMenu.style.display = "block";
        } else {
            dropdownMenu.style.display = "none";
        }
    }

    document.addEventListener('click', function () {
        var dropdownMenu = document.getElementById("dropdownMenu");
        dropdownMenu.style.display = "none";
    });
</script>
