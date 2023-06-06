<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<div class="info-container m-3" style="max-width: 300px; min-width: 300px; height: 250px;">--%>
<%--    <form action="location/edit" method="post">--%>
<%--        <div class="d-flex justify-content-end">--%>
<%--            <button type="button" class="btn btn-danger delete-location" data-location-id="${location.getId()}">--%>
<%--                <i class="fas fa-trash-alt"></i>--%>
<%--            </button>--%>
<%--        </div>--%>
<%--        <div class="field-group">--%>
<%--            <div class="d-flex justify-content-between">--%>
<%--                <div class="field">--%>
<%--                    <spring:message code="addAssetView.localityLabel" var="localityLabel"/>--%>
<%--                    <spring:message code="addAssetView.placeholders.city" var="localityPH"/>--%>
<%--                    <label for="locality${location.getId()}" class="form-label">${localityLabel}</label>--%>
<%--                    <input type="text" name="locality" id="locality${location.getId()}" placeholder="${localityPH}"--%>
<%--                           class="form-control" value="${location.locality}" disabled/>                </div>--%>
<%--                <div class="field">--%>
<%--                    <spring:message code="addAssetView.provinceLabel" var="provinceLabel"/>--%>
<%--                    <spring:message code="addAssetView.placeholders.province" var="provincePH"/>--%>
<%--                    <label for="province${location.getId()}" class="form-label">${provinceLabel}</label>--%>
<%--                    <input type="text" name="province" id="province${location.getId()}" placeholder="${provincePH}"--%>
<%--                           class="form-control" value="${location.province}" disabled/>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="d-flex justify-content-between">--%>
<%--                <div class="field">--%>
<%--                    <spring:message code="addAssetView.countryLabel" var="countryLabel"/>--%>
<%--                    <spring:message code="addAssetView.placeholders.country" var="countryPH"/>--%>
<%--                    <label for="country${location.getId()}" class="form-label">${countryLabel}</label>--%>
<%--                    <input type="text" name="country" id="country${location.getId()}" placeholder="${countryPH}"--%>
<%--                           class="form-control" value="${location.country}" disabled/>--%>
<%--                </div>--%>
<%--                <div class="field">--%>
<%--                    <spring:message code="addAssetView.zipcodeLabel" var="zipcodeLabel"/>--%>
<%--                    <spring:message code="addAssetView.placeholders.zipcode" var="zipcodePH"/>--%>
<%--                    <label for="zipcode${location.getId()}" class="form-label">${zipcodeLabel}</label>--%>
<%--                    <input type="text" name="zipcode" id="zipcode${location.getId()}" placeholder="${zipcodePH}"--%>
<%--                           class="form-control" value="${location.zipcode}" disabled/>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <input type="hidden" name="id" value="${location.getId()}">--%>
<%--        </div>--%>
<%--        <div class="mt-3 form-button-container">--%>
<%--            <button type="button" class="edit-button btn btn-outline-success mx-1">Edit</button>--%>
<%--            <input type="submit" class="save-button btn btn-green mx-1 d-none" value="Save"/>--%>
<%--        </div>--%>
<%--    </form>--%>
<%--</div>--%>

<div class="info-container m-3" style="max-width: 300px; min-width: 300px; height: 250px;">
    <form action="edit" method="post">
        <div class="d-flex justify-content-between">
            <div>
                <spring:message code="addAssetView.locationNameLabel" var="locationNameLabel"/>
                <input type="text" name="name" id="name${location.getId()}" class="form-control" value="${location.name}" disabled/>
            </div>
            <button type="button" class="btn btn-danger delete-location" data-location-id="${location.getId()}">
                <i class="fas fa-trash-alt"></i>
            </button>
        </div>

        <div class="field-group">
            <div class="d-flex justify-content-between">
                <div class="field">
                    <spring:message code="addAssetView.localityLabel" var="localityLabel"/>
                    <spring:message code="addAssetView.placeholders.city" var="localityPH"/>
                    <label for="locality${location.getId()}" class="form-label">${localityLabel}</label>
                    <input type="text" name="locality" id="locality${location.getId()}" placeholder="${localityPH}"
                           class="form-control" value="${location.locality}" disabled/>                </div>
                <div class="field">
                    <spring:message code="addAssetView.provinceLabel" var="provinceLabel"/>
                    <spring:message code="addAssetView.placeholders.province" var="provincePH"/>
                    <label for="province${location.getId()}" class="form-label">${provinceLabel}</label>
                    <input type="text" name="province" id="province${location.getId()}" placeholder="${provincePH}"
                           class="form-control" value="${location.province}" disabled/>
                </div>
            </div>
            <div class="d-flex justify-content-between">
                <div class="field">
                    <spring:message code="addAssetView.countryLabel" var="countryLabel"/>
                    <spring:message code="addAssetView.placeholders.country" var="countryPH"/>
                    <label for="country${location.getId()}" class="form-label">${countryLabel}</label>
                    <input type="text" name="country" id="country${location.getId()}" placeholder="${countryPH}"
                           class="form-control" value="${location.country}" disabled/>
                </div>
                <div class="field">
                    <spring:message code="addAssetView.zipcodeLabel" var="zipcodeLabel"/>
                    <spring:message code="addAssetView.placeholders.zipcode" var="zipcodePH"/>
                    <label for="zipcode${location.getId()}" class="form-label">${zipcodeLabel}</label>
                    <input type="text" name="zipcode" id="zipcode${location.getId()}" placeholder="${zipcodePH}"
                           class="form-control" value="${location.zipcode}" disabled/>
                </div>
            </div>
            <input type="hidden" name="id" value="${location.getId()}">
        </div>
        <input type="hidden" name="id" value="${location.getId()}">
        <div class="mt-3 form-button-container">
            <button type="button" class="edit-button btn btn-outline-success mx-1">Edit</button>
            <input type="submit" class="save-button btn btn-green mx-1 d-none" value="Save"/>
        </div>
    </form>
</div>
