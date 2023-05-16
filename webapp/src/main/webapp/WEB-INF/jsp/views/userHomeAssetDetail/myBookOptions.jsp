<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">

<div style=" ">
    <button id="privatePublicBtn" class="btn-green" type="submit">
        <c:choose>
            <c:when test="${asset.assetState.isPublic()}">
                <i class="fas fa-eye fa-lg"></i> <spring:message code="public"/>
            </c:when>
            <c:otherwise>
                <i class="fas fa-eye-slash fa-lg"></i> <spring:message code="private"/>
            </c:otherwise>
        </c:choose>
    </button>
    <button id="deleteBtn" class="btn-red-outline" style="margin-top: 5px" type="submit">
        <i class="fas fa-trash" ></i>
        <spring:message code="delete" />
    </button>
</div>

<jsp:include page="deleteBookModal.jsp">
    <jsp:param name="asset" value="${asset}"/>
</jsp:include>
<jsp:include page="changeStatusModal.jsp">
    <jsp:param name="asset" value="${asset}"/>
</jsp:include>



