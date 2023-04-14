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
        <form:form modelAttribute="borrowAssetForm" method="post" action="${borrowAssetUrl}">
          <div>
            <form:errors path="postalCode" cssClass="error" element="p"/>
            <label>Postal Code:
              <form:input path="postalCode" placeholder="Postal Code"/>
            </label>
          </div>
          <div>
            <form:errors path="locality" cssClass="error" element="p"/>
            <label>Locality:
              <form:input path="locality" placeholder="Locality"/>
            </label>
          </div>
          <div>
            <form:errors path="province" cssClass="error" element="p"/>
            <label>Province:
              <form:input path="province" placeholder="Province"/>
            </label>
          </div>
          <div>
            <form:errors path="country" cssClass="error" element="p"/>
            <label>Country:
              <form:input path="country" placeholder="Country"/>
            </label>
          </div>
          <div>
            <form:errors path="email" cssClass="error" element="p"/>
            <label>Email:
              <form:input path="email" placeholder="Email"/>
            </label>
          </div>
          <div>
            <form:errors path="name" cssClass="error" element="p"/>
            <label>Name:
              <form:input path="name" placeholder="Name"/>
            </label>
          </div>
          <div>
            <form:errors path="message" cssClass="error" element="p"/>
            <label>Message:
              <form:input path="message" placeholder="Message"/>
            </label>
          </div>
          <button type="submit">Submit</button>
        </form:form>
      </div>
    </div>
  </div>
</div>
</body>
