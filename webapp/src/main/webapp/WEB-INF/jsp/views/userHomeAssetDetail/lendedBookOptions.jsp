<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>
<c:if test="${asset.assetState.isPending()}">
    <h6>The book is currently: </h6>
    <spring:message code="userHomeView.pending"/>
    <button id="confirmAssetBtn" class="btn-green" type="submit">
        <spring:message code="userHomeView.verifyBook"/>
    </button>
</c:if>

<c:if test="${asset.assetState.isBorrowed()}">
    <h6>The book is currently: </h6>
    <spring:message code="userHomeView.inProgress"/>
    <button id="returnAssetBtn" class="btn-green" type="submit">
        <spring:message code="userHomeView.return"/>
    </button>
</c:if>
</div>
<jsp:include page="returnModal.jsp">
    <jsp:param name="asset" value="${asset}"/>
</jsp:include>
<jsp:include page="confirmModal.jsp">
    <jsp:param name="asset" value="${asset}"/>
</jsp:include>