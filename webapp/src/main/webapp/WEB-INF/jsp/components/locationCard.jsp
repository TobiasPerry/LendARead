<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="info-container m-3" style="max-width: 300px; min-width: 300px; height: 300px;">
    <c:url value="/deleteLocation" var="deleteUrl"/>
    <form:form action="${deleteUrl}" method="post">
        <input type="hidden" name="id" value="${location.id == null ? -1 : location.id}">
        <button type="submit" class="btn btn-danger delete-location">
            <i class="fas fa-trash-alt"></i>
        </button>
    </form:form>

    <c:url var="editUrl" value="/editLocation" />
    <form:form action="${editUrl}" method="post" modelAttribute="locationForm">
        <div class="field-group">
            <spring:message code="book_name" var="titlePH"/>
            <label for="name${location.id}" class="form-label">${titlePH}</label>
            <form:input path="name" id="name${location.id}" class="form-control" disabled="true" value="${location.name}"/>
            <c:if test="${location.id == locationIdError || param.showError}">
                <form:errors path="name" cssClass="text-danger small" element="small"/>
            </c:if>


            <div class="d-flex justify-content-between">
                <div class="field">
                    <spring:message code="addAssetView.localityLabel" var="localityLabel"/>
                    <label for="locality${location.id}" class="form-label">${localityLabel}</label>
                    <form:input path="locality" id="locality${location.id}" class="form-control" disabled="true" value="${location.locality}"/>
                    <c:if test="${location.id == locationIdError || param.showError}">
                        <form:errors path="locality" cssClass="text-danger small" element="small"/>
                    </c:if>

                </div>
                <div class="field">
                    <spring:message code="addAssetView.provinceLabel" var="provinceLabel"/>
                    <label for="province${location.id}" class="form-label">${provinceLabel}</label>
                    <form:input path="province" id="province${location.id}" class="form-control" disabled="true" value="${location.province}"/>
                    <c:if test="${location.id == locationIdError || param.showError}">
                        <form:errors path="province" cssClass="text-danger small" element="small"/>
                    </c:if>

                </div>
            </div>
            <div class="d-flex justify-content-between">
                <div class="field">
                    <spring:message code="addAssetView.countryLabel" var="countryLabel"/>
                    <label for="country${location.id}" class="form-label">${countryLabel}</label>
                    <form:input path="country" id="country${location.id}" class="form-control" disabled="true" value="${location.country}"/>
                    <c:if test="${location.id == locationIdError || param.showError}">
                        <form:errors path="country" cssClass="text-danger small" element="small"/>
                    </c:if>
                </div>
                <div class="field">
                    <spring:message code="addAssetView.zipcodeLabel" var="zipcodeLabel"/>
                    <label for="zipcode${location.id}" class="form-label">${zipcodeLabel}</label>
                    <form:input path="zipcode" id="zipcode${location.id}" class="form-control" disabled="true" value="${location.zipcode}"/>
                    <c:if test="${location.id == locationIdError || param.showError}">
                        <form:errors path="zipcode" cssClass="text-danger small" element="small"/>
                    </c:if>

                </div>
            </div>
            <input type="hidden" name="id" value="${location.id == null ? -1 : location.id}">
        </div>
        <div class="mt-3 form-button-container">
            <button type="button" class="edit-button btn btn-outline-success mx-1">Edit</button>
            <input type="submit" class="save-button btn btn-green mx-1 d-none" value="Save"/>
        </div>
    </form:form>
</div>
