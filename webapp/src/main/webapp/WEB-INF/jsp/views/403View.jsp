<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><spring:message code="403.title" /></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro-bg.ico'/>" type="image/x-icon">

    <link rel="stylesheet" href="<c:url value="/static/css/403View.css"/>">
</head>
<body>

<section id="hero" style="background-color: #D0DCD0; padding-bottom: 100px; ">
    <div class="container" style="text-align: start">


        <div class="text-container">
                <h1 class="mb-4"><spring:message code="403.title" /></h1>
                <p class="mb-4"><spring:message code="403.text" /></p>
                <a href="<c:url value="/"/>" class="btn btn-primary mt-3" style="background-color: #2B3B2B;border-color: #2B3B2B"><spring:message code="403.button" /></a>
        </div>

    </div>
</section>

</body>
</html>