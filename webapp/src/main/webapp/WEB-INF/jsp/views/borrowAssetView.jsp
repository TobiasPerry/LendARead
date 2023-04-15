<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="formElementsBorrow" scope="session" class="ar.edu.itba.paw.webapp.form.FormServiceBorrowAssetView"/>

<head>
  <title>Pedir Prestado Libro</title>
  <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
</head>
<body>

<jsp:include page="../components/navBar.jsp"/>
<div class="container my-5">

    <jsp:include page="../components/snackbarComponent.jsp" />

  <h1 class="text-center mb-5">Quieres pedir prestado este libro? </h1>
  <div class="p-4 rounded lendView" >
    <div class="row">

      <div class="col-md-6 d-flex flex-column align-items-center">
        <div class="image-container position-relative" style="width: 100%; height: 100%;">
          <img src="data:image/jpeg;base64,${bookImage}" alt="Book Cover" class="img-fluid" id="bookImage" style="width: 100%; height: 100%; object-fit: contain;">
        </div>
      </div>

      <div class="col-md-6">
        <c:url var="borrowAssetUrl" value="/borrowAsset?id=${id}&imageId=${imageId}"/>
        <form:form modelAttribute="borrowAssetForm" method="post" action="${borrowAssetUrl}" class="container">

          <div>
            <h3> Contacto: </h3>
          <div class="row">
            <div class="col-md-6 mb-3">
              <label for="email" class="form-label">Email:</label>
              <form:input path="email" id="email" placeholder="Email" class="form-control"/>
              <form:errors path="email" cssClass="text-danger small" element="small"/>
            </div>
            <div class="col-md-6 mb-3">
              <label for="name" class="form-label">Nombre:</label>
              <form:input path="name" id="name" placeholder="Name" class="form-control"/>
              <form:errors path="name" cssClass="text-danger small" element="small"/>
            </div>
          </div>
          </div>
            <div >
              <h3>Mensaje:</h3>
              <form:input path="message" id="message" placeholder="Message" class="form-control"/>
              <form:errors path="message" cssClass="text-danger small" element="small"/>
            </div>
        <button type="submit" class="btn btn-primary m-8">Pedir prestado</button>

      </div>

        </form:form>

      </div>
    </div>
  </div>
</div>
</body>
