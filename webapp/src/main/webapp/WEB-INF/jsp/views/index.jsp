<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="index.head.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/bookCardIndex.css"/>" rel="stylesheet"/>
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro.ico'/>" type="image/x-icon">
    <link href="<c:url value="/static/css/index.css"/>" rel="stylesheet"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Overpass:wght@700&display=swap" rel="stylesheet">
    <style>

    </style>
</head>

<body data-path="${path}" class = "body-class">
<jsp:include page="../components/navBar.jsp"/>
<jsp:include page="../components/snackbarComponent.jsp"/>

<section id="hero" style="background-color: #D0DCD0; padding-bottom: 100px; ">
    <div class="container" style="text-align: start">
        <div class="text-container">
            <h1><spring:message code="index.mainText"/></h1>
            <h2><spring:message code="index.subText"/></h2>

            <div class="d-flex justify-content-center mt-4">
                <a href="<c:url value="/discovery"/>" class="btn-get-started scrollto" style="text-decoration: none"><spring:message code="index.button"/></a>
            </div>
        </div>
        <div class="image-container">
            <img src="<c:url value='/static/images/indexPhoto.svg'/>" class="img-fluid animated" alt="">
        </div>
    </div>
</section>

<c:if test="${books.size() > 0}">
    <section style="background-color: #FFFFFF; margin-top: 100px">
        <div class="container-row-wrapped">
            <h1>Recently add books</h1>
        </div>
        <div class="container-row-wrapped" style="margin: 20px auto; padding-top: 20px; background-color: #FFFFFF; border-radius: 20px; width: 90%">
            <c:forEach var="book" items="${books}">
                <% request.setCharacterEncoding("utf-8"); %>
                <jsp:include page="../components/bookCard.jsp">
                    <jsp:param name="id" value="${book.id}"/>
                    <jsp:param name="bookTitle" value="${book.book.name}"/>
                    <jsp:param name="bookAuthor" value="${book.book.author}"/>
                    <jsp:param name="imageId" value="${book.imageId}"/>
                </jsp:include>
            </c:forEach>
        </div>
    </section>
</c:if>
</body>

</html>
