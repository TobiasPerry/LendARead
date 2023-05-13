<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">

<div>
    <button id="privatePublicBtn" class="btn-green" type="submit">
        <c:choose>
            <c:when test="${asset.assetState.isPublic()}">
                <spring:message code="public"/>
            </c:when>
            <c:otherwise>
                <spring:message code="private"/>
            </c:otherwise>
        </c:choose>
    </button>

    <button id="deleteBtn" class="btn-red-outline" type="submit">
        <spring:message code="delete" />
    </button>
</div>

<jsp:include page="deleteBookModal.jsp">
    <jsp:param name="asset" value="${asset}"/>
</jsp:include>
<jsp:include page="changeStatusModal.jsp">
    <jsp:param name="asset" value="${asset}"/>
</jsp:include>



