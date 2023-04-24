<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Home Page</title>
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
      min-width: 400px;
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

    .image {
      width: 150px;
    }
  </style>
</head>

<body class="body-class">
<div>
  <div >
    <h1><spring:message code="greeting" /></h1>

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
            <th><spring:message code="description" /></th>
            <th><spring:message code="status" /></th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${myBooks}" var="asset">
            <tr>
              <td> <img class="image" src="<c:url value="/getImage/${asset.imageId}"/>"  alt="${asset.book.name}" /></td>
              <td>${asset.book.name}</td>
              <td>${asset.book.author}</td>
              <td>${asset.book.language}</td>
<%--              <td>${asset.description}</td>--%>
              <td>
                <form action="/changeStatus?id=${asset.id}" method="post">
                  <button type="submit">${asset.assetState.canBorrow() ? 'Public' : 'Private'}</button>
                </form>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>

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
          <c:forEach items="${borrowedBooks}" var="asset">
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
        <c:forEach items="${lendedBooks}" var="asset">
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
  </div>
</div>
</body>
</html>