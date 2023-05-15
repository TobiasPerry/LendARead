<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<th>
    <form action="/sortUserHomeAssets" method="get" class="sort-form">
        <input type="hidden" name="table" value="${param.table}" />
        <input type="hidden" name="attribute" value="${param.attribute}" />
        <input type="hidden" name="filterValue" value="${param.filterValue}">
        <input type="hidden" name="filterAtribuite" value="${param.filterAtribuite}">
        <input type="hidden" name="direction" value="${param.sortAttribute ? 'desc' : 'asc'}" />
        <button type="submit" class="sort-button<c:if test='${param.sortAttribute}'> sort-button-selected</c:if>">
            <spring:message code="${param.title}"/>
            <i class="fas fa-arrow-<c:out value='${param.sortAttribute ? "up" : "down"}' />"></i>
        </button>
    </form>
</th>
