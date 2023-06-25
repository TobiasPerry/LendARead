<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">

<div class="options-menu">
    <c:if test="${!(!asset.getIsReservable() && lendings.size() > 0 )}">
    <button id="privatePublicBtn" class="btn-green" type="submit">
        <c:choose>
            <c:when test="${asset.assetState.isPublic()}">
                <i class="fas fa-eye-slash fa-lg"></i> <spring:message code="userBookDetails.makePrivate"/>
            </c:when>
            <c:otherwise>
                <i class="fas fa-eye fa-lg"></i> <spring:message code="userBookDetails.makePublic"/>
            </c:otherwise>
        </c:choose>
    </button>
    </c:if>
    <a class="btn-green" href="<c:url value="/editAsset/${asset.id}"/>" style="margin-top: 5px;text-decoration: none">
        <i class="fas fa-pencil-alt"></i>
        <spring:message code="edit"/>
    </a>
    <button id="changeIsReservable" class="btn-green" style="margin-top: 5px" type="submit">
        <c:choose>
            <c:when test="${asset.isReservable}">
                <i class="fas fa-calendar-times"></i> <spring:message code="not_reservable"/>
            </c:when>
            <c:otherwise>
                <i class="fas fa-calendar-alt"></i> <spring:message code="reservable"/>
            </c:otherwise>
        </c:choose>
    </button>
    <button id="deleteBtn" class="btn-red" style="margin-top: 5px" type="submit">
        <i class="fas fa-trash"></i>
        <spring:message code="delete"/>
    </button>
</div>

<jsp:include page="deleteBookModal.jsp">
    <jsp:param name="asset" value="${asset}"/>
</jsp:include>
<jsp:include page="changeStatusModal.jsp">
    <jsp:param name="asset" value="${asset}"/>
</jsp:include>
<jsp:include page="changeReservabilityModal.jsp">
    <jsp:param name="asset" value="${asset}"/>
</jsp:include>


