<%--
  Created by IntelliJ IDEA.
  User: lauti
  Date: 4/30/2023
  Time: 12:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="book-card" style="height: 1%;width: 10%">
  <a href="<c:url value="/info/${param.id}"/>" style="text-decoration: none">
    <div class="card" >
      <c:choose>
        <c:when test="${param.imageId != 0}">
          <img src="<c:url value="/getImage/${param.imageId}"/>" class="card-img-top imagen-card" alt="Shoe Dog cover" style="  width: 100%;height: auto; object-fit: cover">
        </c:when>
        <c:otherwise>
          <img src="https://i.pinimg.com/originals/d4/2e/d7/d42ed7bf30a4c1a6a201565f0bc61190.jpg" class="card-img-top imagen-card" alt="Shoe Dog cover">
        </c:otherwise>
      </c:choose>
      <div class="card-body">
        <h6 class="card-title title-text" ><c:out value="${param.bookTitle}"/></h6>
        <h7 class="card-text" ><c:out value="${param.bookAuthor}"/></h7>
      </div>
    </div>

  </a>
</div>
