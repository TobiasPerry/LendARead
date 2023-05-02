<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Home Page</title>
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
<div class="main-class">
    <div class="container my-5">
        <h1><spring:message code="greeting" /></h1>
        <div class="row">
            <div class="col-md-4">
                <div class="list-group">
                    <div class="list-group">
                        <form action="/changeTable" method="get">
                            <input type="hidden" name="type" value="my_books">
                            <button type="submit" class="list-group-item list-group-item-action button-select"><spring:message code="my_books" /></button>
                        </form>
                        <form action="/changeTable" method="get">
                            <input type="hidden" name="type" value="lended_books">
                            <button type="submit" class="list-group-item list-group-item-action button-select"><spring:message code="lended_books" /></button>
                        </form>
                        <form action="/changeTable" method="get">
                            <input type="hidden" name="type" value="borrowed_books">
                            <button type="submit" class="list-group-item list-group-item-action button-select"><spring:message code="borrowed_books" /></button>
                        </form>
                    </div>

                </div>
            </div>
            <div class="col-md-8">
                <c:choose>
                    <c:when test="${table == 'my_books'}">
                    <div class="container">
                        <div class="table-title">
                            <h2><spring:message code="my_books" /></h2>
                            <table>
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

                                                <div class="dropdown" style="position: absolute; bottom: 10px; right: 10px;">
                                                    <button class="btn btn-link dropdown-toggle p-0" type="button" id="dropdownMenuButton" onclick="toggleDropdown(event)">
                                                        <i class="fas fa-ellipsis-v icon-style"></i>
                                                    </button>
                                                    <div class="dropdown-menu" id="dropdownMenu" aria-labelledby="dropdownMenuButton" style="display: none;">
                                                        <form action="/deleteAsset/${asset.id}" method="post" style="display:inline;">
                                                            <button class="dropdown-item" type="submit">Delete</button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>

                                        <td>${asset.book.name}</td>
                                        <td>${asset.book.author}</td>
                                        <td>${asset.book.language}</td>
                                            <%--              <td>${asset.description}</td>--%>
                                        <td>
                                            <form action="/changeStatus?id=${asset.id}" method="post">
                                                <button class="button-status" type="submit">${asset.assetState.isPublic() ? 'Public' : 'Private'}</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:when test="${table == 'lended_books'}">
                        <c:choose>
                            <c:when test="${isLender}">
                                    <div class="table-title">
                                        <h2><spring:message code="lended_books" /></h2>
                                        <table>
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
                            <table>
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
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<spring:message code="userHomeView.changeAssetStatus.title" var="modalTitle" />
<spring:message code="userHomeView.changeAssetStatus.text" var="modalText" />
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/userHomeModal.jsp">
    <jsp:param name="modalTitle" value="${modalTitle}"/>
    <jsp:param name="text" value="${modalText}"/>
</jsp:include>

</body>
</html>
<script>
    function toggleDropdown(event) {
        event.stopPropagation(); // Prevent the click event from bubbling up
        var dropdownMenu = document.getElementById("dropdownMenu");
        if (dropdownMenu.style.display === "none") {
            dropdownMenu.style.display = "block";
        } else {
            dropdownMenu.style.display = "none";
        }
    }

    // Close the dropdown menu if clicked outside
    document.addEventListener('click', function () {
        var dropdownMenu = document.getElementById("dropdownMenu");
        dropdownMenu.style.display = "none";
    });
</script>
