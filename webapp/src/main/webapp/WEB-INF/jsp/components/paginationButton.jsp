<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div>
  <nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
      <c:choose>
        <c:when test="${param.previous == false}">
          <li class="page-item disabled">
            <a class="page-link"><spring:message code="paginationButton.previous" /></a>
          </li>
        </c:when>
        <c:otherwise>
          <li class="page-item">
            <a class="page-link" href="<c:url value="/discovery/${param.page - 1}"/>"><spring:message code="paginationButton.previous" /></a>
          </li>
        </c:otherwise>
      </c:choose>

      <c:choose>
        <c:when test="${param.next == false}">
          <li class="page-item disabled">
            <a class="page-link"><spring:message code="paginationButton.next" /></a>
          </li>
        </c:when>
        <c:otherwise>
          <li class="page-item">
            <a class="page-link" href="<c:url value="/discovery/${param.page + 1}"/>"><spring:message code="paginationButton.next" /></a>
          </li>
        </c:otherwise>
      </c:choose>

    </ul>
  </nav>
</div>