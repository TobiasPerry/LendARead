<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div>
  <nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
      <c:choose>
        <c:when test="${param.previous == false}">
          <li class="page-item disabled">
            <a class="page-link" href="<c:url value="/discovery/${param.page + 1}"/>">Previous</a>
          </li>
        </c:when>
        <c:otherwise>
          <li class="page-item">
            <a class="page-link" href="">Previous</a>
          </li>
        </c:otherwise>
      </c:choose>

      <c:choose>
        <c:when test="${param.next == false}">
          <li class="page-item disabled">
            <a class="page-link">Next</a>
          </li>
        </c:when>
        <c:otherwise>
          <li class="page-item" href="<c:url value="/discovery/${param.page - 1}"/>">
            <a class="page-link">Next</a>
          </li>
        </c:otherwise>
      </c:choose>

    </ul>
  </nav>
</div>