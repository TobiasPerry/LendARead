<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="stylesheet" href="<c:url value="/static/css/userHomeView.css"/>">

<div class="d-inline-flex">
    <c:url var="filterUrl" value="/applyFilter"/>
    <form action="${filterUrl}" method="get">
        <input type="hidden" name="table" value="${param.table}">
        <input type="hidden" name="filterValue" value="${param.filterValue}">
        <input type="hidden" name="filterAtribuite" value="${param.filterAtribuite}">
        <button type="submit" data-bs-toggle="tooltip" data-bs-placement="top" title="<spring:message code='${param.title}'/>" class="btn ${param.filterAtribuite == 'lendingStatus' ? 'filter-button-gray' : 'filter-button'} btn-primary ${param.filter == param.filterValue && param.table == param.table ? 'filter-button-selected' : ''}">
            <spring:message code="${param.buttonText}" />
        </button>
    </form>
</div>

