<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
  <title><spring:message code="borrowAssetView.title"/></title>
  <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro.ico'/>" type="image/x-icon">
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
  <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
</head>
<body class="body-class">

<jsp:include page="../components/navBar.jsp"/>
<div class="main-class">
  <div class="container my-5">

    <jsp:include page="../components/snackbarComponent.jsp" />

    <h1 class="text-center mb-5"><spring:message code="borrowAssetView.wantToBorrow"/></h1>
    <div class="p-4 rounded lendView" >
      <div class="display-flex">

        <div class="image-container-wrapper flex-1 d-flex flex-column align-items-center">
          <div class="image-container position-relative" style="width: 100%; height: 100%;">
            <img src="data:image/jpeg;base64,${bookImage}" alt="Book Cover" class="img-fluid" id="bookImage" style="width: 400px; height: 600px; object-fit: cover;">
          </div>
        </div>

        <div class="form-container flex-1">
          <c:url var="borrowAssetUrl" value="/borrowAsset?id=${id}&imageId=${imageId}"/>
          <form:form modelAttribute="borrowAssetForm" method="post" action="${borrowAssetUrl}" class="container">

            <div class="info-container">
              <h3><spring:message code="borrowAssetView.contactInfo"/></h3>
              <div class="field-group">
                <div class="field">
                  <spring:message code="borrowAssetView.emailLabel" var="emailLabel" />
                  <label for="email" class="form-label">${emailLabel}</label>
                  <form:input path="email" id="email" placeholder="${emailLabel}" class="form-control"/>
                  <form:errors path="email" cssClass="text-danger small" element="small"/>
                </div>
                <div class="field">
                  <spring:message code="borrowAssetView.nameLabel" var="nameLabel" />
                  <label for="name" class="form-label">${nameLabel}</label>
                  <form:input path="name" id="name" placeholder="${nameLabel}" class="form-control"/>
                  <form:errors path="name" cssClass="text-danger small" element="small"/>
                </div>
              </div>
            </div>

            <div class="d-flex justify-content-center">
              <spring:message code="borrowAssetView.borrowButton" var="borrowButton" />
              <button type="submit" class="btn btn-primary m-8">${borrowButton}</button>
            </div>
          </form:form>
        </div>

      </div>
    </div>
  </div>
</div>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/modal.jsp">
  <jsp:param name="modalTitle" value="Prestamo confirmado"/>
  <jsp:param name="text" value="El préstamo se ha solicitado con exito, pronto recibirás un email con la información de contacto del dueño del libro."/>
</jsp:include>

</body>
