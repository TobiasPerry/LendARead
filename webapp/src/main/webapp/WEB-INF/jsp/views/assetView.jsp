<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 4/2/23
  Time: 7:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Book Info</title>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">
</head>

<body data-path="${path}" class=" body-class">

    <jsp:include page="../components/navBar.jsp"/>
    <div class="main-class">
        <div style="background-color: #FFFFFF; margin: 50px; border-radius: 20px; padding: 20px">
            <div class="container-row-wrapped">
                <img src="<c:url value="/getImage/${imageId}"/>" class="mx-5" alt="Book cover" style="height: 600px; width: 400px; object-fit: cover">
                <div class="w-25 h-25 mx-5">
                    <h1><c:out value="${name}"/></h1>
                    <h3>by <c:out value="${author}"/></h3>
                    <h3>Idioma: <c:out value="${language}"/></h3>
                    <h5>ISBN: <c:out value="${isbn}"/></h5>
                    <h5>Estado: <c:out value="${physicalCondition}"/></h5>
                </div>

                <div class="w-25 h-25 mx-5" style="border: 2px solid #E3DDE0; border-radius: 10px; padding: 20px">
                    <p style="margin-bottom: -5px">Código postal</p>
                    <h3><c:out value="${locationPC}"/></h3>

                    <p style="margin-bottom: -5px">Localidad</p>
                    <h3><c:out value="${location}"/></h3>

                    <p style="margin-bottom: -5px">Provincia</p>
                    <h3><c:out value="${province}"/></h3>

                    <p style="margin-bottom: -5px">País</p>
                    <h3><c:out value="${country}"/></h3>

                    <div class="text-center mt-5">
                        <a type="button" class="btn btn-primary"  href="<c:url value="/borrowAssetView?id=${id}&imageId=${imageId}"/>">Pedir prestado este libro!</a>
                    </div>

                </div>
            </div>
        </div>
    </div>
</body>
</html>
