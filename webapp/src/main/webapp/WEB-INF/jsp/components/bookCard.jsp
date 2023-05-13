
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="book-card" aria-hidden="true">
    <a href="<c:url value="/info/${param.id}"/>" style="text-decoration: none">
        <div class="card">

            <c:choose>
                <c:when test="${param.imageId != 0}">
                    <img src="<c:url value="/getImage/${param.imageId}"/>" class="card-img-top imagen-card" alt="<c:out value="${param.bookTitle}"/>" style="height: 400px; object-fit: cover"/>
                </c:when>
                <c:otherwise>
                    <img src="https://i.pinimg.com/originals/d4/2e/d7/d42ed7bf30a4c1a6a201565f0bc61190.jpg" class="card-img-top imagen-card" alt="<c:out value="${param.bookTitle}"/> cover" style="height: 400px; object-fit: cover"/>
                </c:otherwise>
            </c:choose>

            <div class="card-title title-text text-center my-3">
                <h2 class="card-title text-truncate mx-2">
                    <c:out value="${param.bookTitle}"/>
                </h2>
                <h5 class="card-text title-text text-truncate mx-2">
                    <span class="col-9"><c:out value="${param.bookAuthor}"/></span>
                </h5>
            </div>

        </div>

    </a>
</div>
