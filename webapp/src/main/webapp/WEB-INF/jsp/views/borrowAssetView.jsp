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

  <h1 class="text-center mb-5">Quieres pedir prestado un libro?</h1>
  <div class="p-4 rounded lendView" >
    <div class="row">

      <div class="col-md-6 d-flex flex-column align-items-center">
        <div class="image-container position-relative" style="width: 100%; height: 100%;">
          <img src="https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T1/images/I/61aFldsgAmL._AC_UF1000,1000_QL80_.jpg" alt="Book Cover" class="img-fluid" id="bookImage" style="width: 100%; height: 100%; object-fit: contain;">
        </div>
      </div>

      <div class="col-md-6">
        <c:url var="borrowAssetUrl" value="/borrowAsset?id=${id}"/>
        <form:form modelAttribute="borrowAssetForm" method="post" action="${borrowAssetUrl}" class="container">
          <div class="row">
            <div class="col-md-6 mb-3">
              <label for="postalCode" class="form-label">Postal Code:</label>
              <form:input path="postalCode" id="postalCode" placeholder="Postal Code" class="form-control"/>
              <form:errors path="postalCode" cssClass="text-danger small" element="small"/>
            </div>
            <div class="col-md-6 mb-3">
              <label for="locality" class="form-label">Locality:</label>
              <form:input path="locality" id="locality" placeholder="Locality" class="form-control"/>
              <form:errors path="locality" cssClass="text-danger small" element="small"/>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6 mb-3">
              <label for="province" class="form-label">Province:</label>
              <form:input path="province" id="province" placeholder="Province" class="form-control"/>
              <form:errors path="province" cssClass="text-danger small" element="small"/>
            </div>
            <div class="col-md-6 mb-3">
              <label for="country" class="form-label">Country:</label>
              <form:input path="country" id="country" placeholder="Country" class="form-control"/>
              <form:errors path="country" cssClass="text-danger small" element="small"/>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6 mb-3">
              <label for="email" class="form-label">Email:</label>
              <form:input path="email" id="email" placeholder="Email" class="form-control"/>
              <form:errors path="email" cssClass="text-danger small" element="small"/>
            </div>
            <div class="col-md-6 mb-3">
              <label for="name" class="form-label">Name:</label>
              <form:input path="name" id="name" placeholder="Name" class="form-control"/>
              <form:errors path="name" cssClass="text-danger small" element="small"/>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12 mb-3">
              <label for="message" class="form-label">Message:</label>
              <form:input path="message" id="message" placeholder="Message" class="form-control"/>
              <form:errors path="message" cssClass="text-danger small" element="small"/>
            </div>
          </div>
          <button type="submit" class="btn btn-primary">Submit</button>
        </form:form>

      </div>
    </div>
  </div>
</div>
</body>
