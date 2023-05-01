<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/bookCard.css"/>" rel="stylesheet"/>
    <title>Lend a read</title>
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro.ico'/>" type="image/x-icon">
    <link href="<c:url value="/static/css/index.css"/>" rel="stylesheet"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Overpass:wght@700&display=swap" rel="stylesheet">
    <style>
        svg {
            display: inline-block;
            position: absolute;
            top: 0;
            left: 0;
        }
        #hero {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #D0DCD0;
        }

        #hero .container {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-wrap: wrap;
        }

        #hero .text-container {
            flex: 1;
            text-align: center;
            padding: 20px;
        }

        #hero .image-container {
            flex: 1;
            text-align: center;
            padding: 20px;
        }

        #hero img {
            max-width: 100%;
            height: auto;
        }

        @media only screen and (max-width: 768px) {
            #hero {
                flex-direction: column;
                height: auto;
            }

            #hero .container {
                flex-direction: column;
            }

            #hero .text-container {
                order: 2;
                padding: 0 20px;
            }

            #hero .image-container {
                order: 1;
                padding: 0 20px;
            }
        }
    </style>
</head>

<body data-path="${path}" class = "body-class" style="background-color: #D0DCD0">
<jsp:include page="../components/navBar.jsp"/>
<jsp:include page="../components/snackbarComponent.jsp"/>

<section id="hero">
    <div class="container">
        <div class="text-container">
            <h1><spring:message code="index.mainText"/></h1>
            <h2><spring:message code="index.subText"/></h2>
            <div class="d-flex justify-content-center mt-4">
                <a href="<c:url value="/discovery/1"/>" class="btn-get-started scrollto"><spring:message code="index.button"/></a>
            </div>
        </div>
        <div class="image-container">
            <img src="<c:url value='/static/images/indexPhoto.svg'/>" class="img-fluid animated" alt="">
        </div>
    </div>
</section>
</body>

</html>
