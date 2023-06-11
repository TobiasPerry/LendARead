
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">
<link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">

<style>
  .form-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    opacity: 0.8;
    border-radius: 15px;
    padding: 20px;
  }
  .form-group {
    flex: 0 0 calc(50% - 10px);
    margin-bottom: 10px;
  }
  .title-input {
    font-size: 1.5em;
    font-weight: bold;
    border: none;
    outline: none;
    background-color: transparent;
  }

</style>

<div class="modal fade" id="borrowUser" data-bs-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="${param.modalTitle}" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content rounded-3 border-0 shadow" >
      <div class="modal-header border-0" style="text-align: center;">
        <div class="icon-box">
          <i class="fas fa-book-reader"></i>
        </div>
        <h3 class="modal-title w-100 mt-2"><c:out value="${param.modalTitle}" /></h3>
      </div>
      <c:url value="/defaultLocation" var="defaultLocation" />
      <form:form action="${defaultLocation}" modelAttribute="locationForm" method="post">
        <div class="modal-body text-center py-0 border-0">
          <p class="mb-4"><c:out value="${param.text}" /></p>
          <div class="form-container">
            <spring:message code="addAssetView.titleLabel" var="titleLabel"/>
            <label for="name" class="title-input"><c:out value="${titleLabel}" /></label>
            <form:input path="name" id="name-modal" class="form-control" value=""/>
            <form:errors path="name" cssClass="text-danger small" element="small"/>

            <div class="form-group">
              <label for="zipcode"><spring:message code="addAssetView.placeholders.zipcode"/> </label>
              <form:input path="zipcode"  id="zipcode-modal" class="form-control"  value=""/>
              <form:errors path="zipcode" cssClass="text-danger small" element="small"/>

            </div>
            <div class="form-group">
              <label for="locality"><spring:message code="addAssetView.localityLabel"/> </label>
              <form:input id="locality-modal" path="locality" class="form-control"  value=""/>
              <form:errors path="locality" cssClass="text-danger small" element="small"/>

            </div>
            <div class="form-group">
              <label for="province"><spring:message code="addAssetView.placeholders.province"/> </label>
              <form:input id="province-modal" path="province"  class="form-control"  value=""/>
              <form:errors path="province" cssClass="text-danger small" element="small"/>

            </div>
            <div class="form-group">
              <label for="country"><spring:message code="addAssetView.countryLabel"/></label>
              <form:input path="country" id="country-modal" class="form-control"  value=""/>
              <form:errors path="country" cssClass="text-danger small" element="small"/>

            </div>
          </div>
          <form:input path="id" type="hidden" name="id" value="${location.id == null ? -1 : location.id}"/>
        </div>
        <div class="modal-footer border-0">
          <a class="btn btn-outline-dark mx-2 rounded-pill px-4 py-2" href="<c:url value="/discovery"/>">
            <spring:message code="addAssetView.buttonNoThanks"/>
          </a>
          <button type="submit" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
            <spring:message code="addAssetView.changeRole" />
          </button>
        </div>
      </form:form>
    </div>
  </div>
</div>


<script>
  const borrowUserModal = '${borrowerUser}' === 'true';
  if (borrowUserModal ) {
    new bootstrap.Modal($('#borrowUser')).show();

  }

</script>
