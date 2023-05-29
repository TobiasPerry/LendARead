<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">
<c:if test="${!lending.active.getIsRejected() && !lending.active.getIsFinished()}">
    <div>
        <c:if test="${lending.assetInstance.assetState.isPending()}">
            <h6 style="color: #7d7c7c; font-weight: bold"><spring:message code="userHomeView.minText"/>
                <spring:message code="userHomeView.pending"/>
            </h6>
            <button id="confirmAssetBtn" class="btn-green" type="submit">
                <spring:message code="userHomeView.verifyBook"/>
            </button>
            <button id="rejectAssetBtn" class="btn-red-outline mt-2" type="submit">
                <spring:message code="userHomeView.rejectAssetTitle"/>
            </button>

        </c:if>
        <c:if test="${lending.assetInstance.assetState.isBorrowed()}">
            <h6 style="color: #7d7c7c; font-weight: bold"><spring:message code="userHomeView.minText"/>
                <spring:message code="userHomeView.inProgress"/>
            </h6>
            <button id="returnAssetBtn" class="btn-green" type="submit">
                <spring:message code="userHomeView.confirmReturn"/>
            </button>
        </c:if>
    </div>
</c:if>

<jsp:include page="returnModal.jsp">
    <jsp:param name="lending" value="${lending}"/>
</jsp:include>
<jsp:include page="confirmModal.jsp">
    <jsp:param name="lending" value="${lending}"/>
</jsp:include>
<jsp:include page="rejectModal.jsp">
    <jsp:param name="lending" value="${lending}"/>
</jsp:include>
