<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="formElementsBorrow" scope="session" class="ar.edu.itba.paw.webapp.presentation.FormServiceBorrowAssetView"/>

<head>
  <title>Pedir Prestado Libro</title>
  <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
</head>
<body>

<jsp:include page="../components/navBar.jsp"/>
<div class="container my-5">

    <jsp:include page="../components/snackbarComponent.jsp" />

  <h1 class="text-center mb-5">Quieres pedir prestado un libro?</h1>
  <div class="p-4 rounded lendView" >
    <div class="row">

      <div class="col-md-6 d-flex flex-column align-items-center">
        <div class="image-container position-relative" style="width: 100%; height: 100%;">
          <img src="https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T1/images/I/61aFldsgAmL._AC_UF1000,1000_QL80_.jpg" alt="Book Cover" class="img-fluid" id="bookImage" style="width: 100%; height: 100%; object-fit: contain;">
        </div>
      </div>

      <div class="col-md-6">
        <form action="" method="post">
          <ul class="list-unstyled">
            <li><strong>Ingresa tu ubicacion</strong> <input type="text" name="title" class="form-control" value="" /></li>
            <li><strong>Informacion para retirarlo </strong> <input type="text" class="form-control" value="" /></li>
            <li><strong>ISBN:</strong> <input type="text" class="form-control" value="" /></li>
            <li><strong>Ubicación:</strong> <input type="text" class="form-control" value="" /></li>
            <li><strong>Mail:</strong> <input type="text" class="form-control" value="" /></li>
            <li><strong>Mensaje para Retirarlo:</strong> <input type="text" class="form-control" value="" /></li>
          <button type="submit" class="btn btn-primary mt-3">Pedir prestado </button>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
