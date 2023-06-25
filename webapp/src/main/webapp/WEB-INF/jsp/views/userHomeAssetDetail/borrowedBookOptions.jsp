<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">
<%--  <c:if test="${asset.assetState.isBorrowed()}">--%>
<%--    <button id="returnAssetBtn" class="btn-green" type="submit">--%>
<%--      <spring:message code="userHomeView.return"/>--%>
<%--    </button>--%>
<%--  </c:if>--%>
<%--<jsp:include page="returnModal.jsp">--%>
<%--  <jsp:param name="asset" value="${asset}"/>--%>
<%--</jsp:include>--%>
<c:if test="${lending.assetInstance.assetState.isPending()}">
    <div class="options-menu">
        <spring:message code="borrowedBookOption.pendingInfo"/>
        <button id="cancelAssetBtn" class="btn btn-red mt-3" type="submit">Cancel Lending</button>
    </div>
    <jsp:include page="cancelModal.jsp">
        <jsp:param name="lending" value="${lending}"/>
    </jsp:include>
</c:if>