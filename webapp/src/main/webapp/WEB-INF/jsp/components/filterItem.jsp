<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h5>${param.filterName}</h5>
<ul>
  <c:forEach var="filterAction" items="${param.filterActions}">
    <% request.setCharacterEncoding("utf-8"); %>
    <li>${param.filterName}</li>
  </c:forEach>
</ul>