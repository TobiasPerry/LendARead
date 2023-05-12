<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<button id="privatePublicBtn" class="btn-green" type="submit">
  <c:choose>
    <c:when test="${asset.assetState.isBorrowed()}">
      <spring:message code="userHomeView.return"/>
    </c:when>
    <c:otherwise>
      <spring:message code="userHomeView.inProgress"/>
    </c:otherwise>
  </c:choose>
</button>
