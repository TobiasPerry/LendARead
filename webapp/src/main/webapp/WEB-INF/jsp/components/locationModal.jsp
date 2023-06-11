<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="${param.modalTitle}" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content rounded-3 border-0 shadow">
      <div class="modal-header border-0" style="text-align: center">
        <div class="icon-box">
          <i class="fas fa-map-marked-alt"></i>
        </div>
        <h3 class="modal-title w-100 mt-2"><c:out value="${param.modalTitle}" /></h3>
      </div>
      <div class="modal-body text-center py-0 border-0">
        <form:form action="${param.editUrl}" method="post" modelAttribute="locationForm">
          <div class="field-group">
            <spring:message code="addAssetView.nameLabel" var="nameLabel"/>
            <label class="form-label" for="name-modal">${nameLabel}</label>
            <form:input path="name" id="name-modal" class="form-control" value=""/>
            <form:errors path="name" cssClass="text-danger small" element="small"/>

            <div class="d-flex justify-content-between">
              <div class="field">
                <spring:message code="addAssetView.localityLabel" var="localityLabel"/>
                <label class="form-label" for="locality-modal">${localityLabel}</label>

                <form:input id="locality-modal" path="locality" class="form-control"  value=""/>
                <form:errors path="locality" cssClass="text-danger small" element="small"/>
              </div>
              <div class="field">
                <spring:message code="addAssetView.provinceLabel" var="provinceLabel"/>
               <label class="form-label" for="province-modal">${provinceLabel}</label>
                <form:input id="province-modal" path="province"  class="form-control"  value=""/>
                <form:errors path="province" cssClass="text-danger small" element="small"/>
              </div>
            </div>
            <div class="d-flex justify-content-between">
              <div class="field">
                <spring:message code="addAssetView.countryLabel" var="countryLabel"/>
                <label class="form-label" for="country-modal">${countryLabel}</label>

                <form:input path="country" id="country-modal" class="form-control"  value=""/>
                  <form:errors path="country" cssClass="text-danger small" element="small"/>
              </div>
              <div class="field">
                <spring:message code="addAssetView.zipcodeLabel" var="zipcodeLabel"/>
                <label class="form-label" for="zipcode-modal">${zipcodeLabel}</label>

                <form:input path="zipcode"  id="zipcode-modal" class="form-control"  value=""/>
                <form:errors path="zipcode" cssClass="text-danger small" element="small"/>
              </div>
            </div>
            <input type="hidden" name="id" value="-1">
          </div>
          <div style=" width: 100%; text-align: center; padding-top: 20px;">
            <input type="submit" class="btn btn-green  px-4 py-2" value="Save"/>
          </div>
        </form:form>
      </div>
    </div>
  </div>
</div>

<script>

</script>