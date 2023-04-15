<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/index.css"/>" rel="stylesheet"/>
    <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
</head>
<body data-path="${path}" class = "body-class" >
<!-- Esto va a cambiar es un mockUp -->
<jsp:include page="../components/navBar.jsp"/>
<div class="main-class">

        <div class="container-row-wrapped" style="margin-top: 50px">
            <h1>Available books</h1>
        </div>

        <div class="container-row-wrapped" style="margin-top: 50px">

            <c:if test="${books.size() > 0}">
                <c:forEach var="book" items="${books}">
                    <jsp:include page="../components/bookCard.jsp">
                        <jsp:param name="id" value="${book.id}"/>
                        <jsp:param name="bookTitle" value="${book.book.name}"/>
                        <jsp:param name="bookAuthor" value="${book.book.author}"/>
                        <jsp:param name="bookAvailability" value="X"/>
                        <jsp:param name="imageId" value="${book.imageId}"/>
                    </jsp:include>
                </c:forEach>
            </c:if>

            <c:if test="${books.size() <= 0}">
                <h1>No books to show</h1>
            </c:if>
        </div>

</div>
</body>
</html>
