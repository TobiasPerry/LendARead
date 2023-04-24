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
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Overpass:400,700|Roboto:400,700" rel="stylesheet">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #D0DCD0;
            color: #2B3B2B;
        }
        h1, h2 {
            font-family: 'Overpass', sans-serif;
            color: #111711;
        }
        .container {
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        .table-title{
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
            padding: 15px;
            border-radius: 20px;
            flex: 1;
            min-width: 800px;
            margin: 0 10px 20px;
        }
        th, td {
            padding: 10px;
            margin: 10px;
            text-align: center;
            border-radius: 10px;
        }
        th {
            background-color: #2B3B2B;
            opacity: 0.8;
            color: #D1E9C3;
        }
        button {
            background-color: #2B3B2B;
            color: #D1E9C3;
            border: none;
            cursor: pointer;
            padding: 6px;
        }

        .button-status {
            border-radius: 10px;
            opacity: 0.6;
        }
        .button-status:hover {opacity: 1;}

        .image {
            width: 150px;
        }

        .icon-style {
            color: white;
        }
        .dropdown-toggle::after {
            display: none;
        }
        .image-container {
            position: relative;
            display: inline-block;
        }
        .promo-box {
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
            border-radius: 20px;
            padding: 15px;
            flex: 1;
            margin: 0 10px 20px;
            text-align: center;
            background-color: #D1E9C3;
        }

        .promo-box h2 {
            margin-top: 0;
            font-size: 1.5em;
        }

        .promo-box p {
            font-size: 1.1em;
            margin: 1em 0;
        }
        .button-select {
            background-color: inherit;
            border: none;
        }

        .button-select:hover {opacity: 0.75;}
    </style>
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
                        <form action="/changeTable" method="post">
                            <input type="hidden" name="type" value="my_books">
                            <button type="submit" class="list-group-item list-group-item-action button-select">My Books</button>
                        </form>
                        <form action="/changeTable" method="post">
                            <input type="hidden" name="type" value="lended_books">
                            <button type="submit" class="list-group-item list-group-item-action button-select">Lended Books</button>
                        </form>
                        <form action="/changeTable" method="post">
                            <input type="hidden" name="type" value="borrowed_books">
                            <button type="submit" class="list-group-item list-group-item-action button-select">Borrowed Books</button>
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
                                                        <form action="/deleteAsset?id=${asset.id}" method="post" style="display:inline;">
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
                                                <button class="button-status" type="submit">${asset.assetState.public() ? 'Public' : 'Private'}</button>
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
                                                    <%--          <th><spring:message code="description" /></th>--%>
                                                <th><spring:message code="expected_retrieval_date" /></th>
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
                                                        <%--            <td>${asset.book.description}</td>--%>
                                                        <%--            <td>${asset.expectedRetrievalDate}</td>--%>
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
                                    <form action="/becomeLender" method="post">
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
                                            <%--              <td>${asset.returnDate}</td>--%>
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
