<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 4/2/23
  Time: 7:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><spring:message code="assetView.head.title"/></title>
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro.ico'/>" type="image/x-icon">
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
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script src="<c:url value="/static/javaScript/map.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/static/css/assetView.css"/>">
</head>
<body data-path="${path}" class=" body-class">
<jsp:include page="../components/navBar.jsp"/>

<div class="main-class" style="   display: flex; justify-content: center;align-items: center;flex-direction: column;">
    <div style="background-color: #f0f5f0; margin: 50px; border-radius: 20px; padding: 20px;width:fit-content; min-width: 50%">
        <div class="container-row-wrapped">
            <img src="<c:url value="/getImage/${assetInstance.imageId}"/>" class="mx-3" alt="Book cover"
                 style="height: 500px; width: 300px; object-fit: cover">
            <div class="w-25 h-25 mx-5">
                <h1 class="textOverflow"><c:out value="${assetInstance.book.name} "/></h1>
                <h3 class="textOverflow"><spring:message code="assetView.by"/> <c:out
                        value="${assetInstance.book.author}"/></h3>
                <h6>
                    <i>
                        <u><c:out value="${assetInstance.physicalCondition}"/></u>
                    </i>
                </h6>
                <h6 style="color: #7d7c7c"><spring:message code="assetView.language"/>: <c:out
                        value="${assetInstance.book.language}"/></h6>
                <h6 style="color: #7d7c7c"><spring:message code="assetView.isbn"/>: <c:out
                        value="${assetInstance.book.isbn}"/></h6>

                <form action="/requestAsset?assetId=${assetInstance.id}" method="post">
                    <button type="submit" class="btn btn-primary"
                            style="background-color: #2B3B2B;border-color: #D1E9C3">
                        <spring:message code="assetView.borrowButton"/>
                    </button>
                </form>

            </div>

        </div>

    </div>

    <div class="container-row" style="min-width: 50%; width: fit-content; margin-bottom: 20px">

        <div class="container-column" style="flex: 0 0 60%; padding-right: 10px">
            <div id="map" style="width: 100%;height: 300px; border-radius: 25px;"></div>
        </div>
        <input type="hidden"
               value="<c:out value="${assetInstance.location.locality}"/>, <c:out value="${assetInstance.location.province}"/>, <c:out value="${assetInstance.location.country}"/> <c:out value="${assetInstance.location.zipcode}"/>"
               id="address">

        <div class="container-column" style="flex: 0 0 40%; padding-left: 10px">
            <div class="card" style="background-color:#e3e6e3;height: fit-content; border-radius: 25px">
                <div class="card-body">
                    <h5 class="card-title">Donde esta el libro?</h5>
                    <p class="card-text" style="margin-bottom: -5px"><spring:message code="assetView.zipcode"/></p>
                    <h3 class="textOverflow"><c:out value="${assetInstance.location.zipcode}"/></h3>

                    <p class="card-text" style="margin-bottom: -5px"><spring:message code="assetView.locality"/></p>
                    <h3 class="textOverflow"><c:out value="${assetInstance.location.locality}"/></h3>

                    <p class="card-text" style="margin-bottom: -5px"><spring:message code="assetView.province"/></p>
                    <h3 class="textOverflow"><c:out value="${assetInstance.location.province}"/></h3>

                    <p class="card-text" style="margin-bottom: -5px"><spring:message code="assetView.country"/></p>
                    <h3 class="textOverflow"><c:out value="${assetInstance.location.country}"/></h3>
                </div>
            </div>
        </div>

    </div>

    <script
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB41DRUbKWJHPxaFjMAwdrzWzbVKartNGg&callback=initMap&v=weekly"
            defer
    ></script>
</div>

<spring:message code="borrowAssetView.borrowBookModal.title" var="modalTitle"/>
<spring:message code="borrowAssetView.borrowBookModal.text" var="modalText"/>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/modal.jsp">
    <jsp:param name="modalTitle" value="${modalTitle}"/>
    <jsp:param name="text" value="${modalText}"/>
</jsp:include>
</body>
</html>
