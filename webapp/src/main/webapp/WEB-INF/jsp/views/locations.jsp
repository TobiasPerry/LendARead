<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro-bg.ico'/>" type="image/x-icon">
  <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
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
  <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
  <script src="<c:url value="/static/javaScript/addAssetView.js"/>" defer></script>
  <script src="<c:url value="/static/javaScript/utils.js"/>" defer></script>
  <link href="<c:url value="/static/css/addAssetView.css"/>" rel="stylesheet"/>
  <script src="<c:url value="/static/javaScript/addAssetForm.js"/>" defer></script>
  <title><spring:message code="addAssetView.locationInfo"/></title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">

</head>



<jsp:include page="../components/navBar.jsp"/>

<body class="body-class">
<div class="main-class" style="display: flex; justify-content: center; align-items: center; flex-direction: column;">
  <div class="container">
    <h2 style="padding: 20px;"><spring:message code="addAssetView.locationInfo"/></h2>
    <div class="locations-grid d-flex flex-wrap" style="background-color: #D0DCD0; border-radius: 20px; padding: 20px;">
      <c:forEach var="location" items="${locations}" >
        <div class="info-container m-3" style="max-width: 300px; min-width: 300px; min-height: 300px">
          <c:url var="editUrl" value="/editLocation" />
          <form:form action="${editUrl}" method="post" modelAttribute="locationForm">
            <div class="field-group">
              <form:input path="name" id="name${location.id}" class="form-control title-input" disabled="true" value="${location.name}"/>
              <c:if test="${location.id == locationIdError}">
                <form:errors path="name" cssClass="text-danger small" element="small"/>
              </c:if>

              <div class="d-flex justify-content-between">
                <div class="field">
                  <spring:message code="addAssetView.localityLabel" var="localityLabel"/>
                  <label for="locality${location.id}" class="form-label">${localityLabel}</label>
                  <form:input path="locality" id="locality${location.id}" class="form-control" disabled="true" value="${location.locality}"/>
                  <c:if test="${location.id == locationIdError}">
                  <form:errors path="locality" cssClass="text-danger small" element="small"/>
                  </c:if>

                </div>
                  <div class="field">
                    <spring:message code="addAssetView.provinceLabel" var="provinceLabel"/>
                    <label for="province${location.id}" class="form-label">${provinceLabel}</label>
                    <form:input path="province" id="province${location.id}" class="form-control" disabled="true" value="${location.province}"/>
                    <c:if test="${location.id == locationIdError}">
                    <form:errors path="province" cssClass="text-danger small" element="small"/>
                    </c:if>

                  </div>
              </div>
              <div class="d-flex justify-content-between">
                <div class="field">
                  <spring:message code="addAssetView.countryLabel" var="countryLabel"/>
                  <label for="country${location.id}" class="form-label">${countryLabel}</label>
                  <form:input path="country" id="country${location.id}" class="form-control" disabled="true" value="${location.country}"/>
                  <c:if test="${location.id == locationIdError}">
                  <form:errors path="country" cssClass="text-danger small" element="small"/>
                  </c:if>
                </div>
                <div class="field">
                  <spring:message code="addAssetView.zipcodeLabel" var="zipcodeLabel"/>
                  <label for="zipcode${location.id}" class="form-label">${zipcodeLabel}</label>
                  <form:input path="zipcode" id="zipcode${location.id}" class="form-control" disabled="true" value="${location.zipcode}"/>
                  <c:if test="${location.id == locationIdError}">
                  <form:errors path="zipcode" cssClass="text-danger small" element="small"/>
                  </c:if>

                </div>
              </div>
              <input type="hidden" name="id" value="${location.id == null ? -1 : location.id}">
            </div>
          <div style=" width: 100%; text-align: center; padding-top: 20px;">
            <input type="submit" class="save-button btn btn-green mx-1 d-none" value="Save"/>
          </div>
          </form:form>
          <div class="mt-3 form-button-container d-flex justify-content-around">
            <c:url value="/deleteLocation" var="deleteUrl"/>
            <form:form action="${deleteUrl}" method="post" class="d-inline-block">
              <input type="hidden" name="id" value="${location.id == null ? -1 : location.id}">
              <button type="submit" class="btn btn-danger delete-location">
                <i class="fas fa-trash-alt"></i>
              </button>
            </form:form>
            <div>
              <button type="button" class="edit-button btn btn-green mx-1" style="opacity: 0.6">Edit</button>
            </div>
          </div>
        </div>
      </c:forEach>
      <c:if test="${locationIdError == -1 }">
        <jsp:include page="../components/locationCard.jsp">
          <jsp:param name="location" value="${location}"/>
          <jsp:param name="showError" value="${true}"/>
        </jsp:include>
      </c:if>
      <c:if test="${locations.size() <= 5 && locationIdError != -1 && hasErrors != true}">
      <div class="info-container m-3 add-new-location btn-icon add-button" style="max-width: 300px; min-width: 300px; height: 300px;">
        <div class="d-flex justify-content-center align-items-center" style="height: 100%;">
          <i class="bi bi-plus-lg"></i>
        </div>
      </div>
      </c:if>
    </div>
  </div>
</div>
</body>
</html>

<script>
  $(function() {
    $('body').on('click', '.edit-button', function() {
      $(this).closest('.info-container').find('input[type="text"]').prop('disabled', false);
      $(this).addClass('d-none');
      $(this).closest('.info-container').find('.delete-location').addClass('d-none'); // Added this line
      $(this).closest('.info-container').find('.save-button').removeClass('d-none');
      $('.add-button').prop('disabled', true);
    });


    $('body').on('click', '.save-button', function() {
      $('.add-button, .delete-location').prop('disabled', false);
    });

    $(".add-new-location").click(function() {
      let newCard = `
        <jsp:include page="../components/locationCard.jsp">
         <jsp:param name="location" value="${location}"/>
         <jsp:param name="showError" value="${false}"/>
         <jsp:param name="hasErrors" value="${false}"/>
       </jsp:include>`;

      $(this).before(newCard);
      $(this).addClass('d-none');
    });
  });
</script>
