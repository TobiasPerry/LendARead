<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 4/2/23
  Time: 7:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Info</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">
</head>
<body class="lendView">

    <jsp:include page="../components/navBar.jsp"/>
    <div class="container my-5">
        <h1 class="text-center mb-3"><c:out value="Título"/></h1>
        <div class="d-flex">
            <img src="https://i.pinimg.com/originals/d4/2e/d7/d42ed7bf30a4c1a6a201565f0bc61190.jpg" class="w-25 h-25 mx-5" alt="Book cover">
            <div class="mx-4">
                <h3>Información del libro</h3>
                <div>
                    <h4>Título</h4>
                    <p><c:out value="Mistborn"/></p>

                    <h4>Autor</h4>
                    <p><c:out value="Brandon Sanderson"/></p>
                
                    <h4>Idioma</h4>
                    <p><c:out value="Español"/></p>
                
                    <h4>ISBN</h4>
                    <p><c:out value="9780765311788"/></p>

                    <h4>Estado</h4>
                    <p><c:out value="Bueno"/></p>
                </div>
            </div>
            <div class="mx-4">
                <h3>Ubicación del libro</h3>
                <div>
                    <h4>Código postal</h4>
                    <p><c:out value="1425"/></p>
                    
                    <h4>Localidad</h4>
                    <p><c:out value="CABA"/></p>
                    
                    <h4>Provincia</h4>
                    <p><c:out value="CABA"/></p>

                    <h4>País</h4>
                    <p><c:out value="Argentina"/></p>
                </div>
            </div>
        </div>
        <div class="text-center mt-5">
            <button type="button" class="btn btn-primary">Pedir prestado este libro!</button>
        </div>
    </div>
</body>
</html>
