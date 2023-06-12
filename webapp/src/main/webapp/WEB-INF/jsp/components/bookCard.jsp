
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div class="card text-white card-has-bg click-col" style="background-image:url(<c:url value="/getImage/${param.imageId}"/>);
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
        height: 400px; margin: 15px;width: 18rem; object-fit: cover">

    <a href="<c:url value="/info/${param.id}"/>" style="text-decoration: none">

        <img class="card-img d-none" src="<c:url value="/getImage/${param.imageId}"/>" alt="<c:out value="${param.bookTitle}"/>">

        <div class="card-img-overlay d-flex flex-column">
            <div class="card-body">
                <small class="card-meta mb-2 text-truncate"><c:out value="${param.bookAuthor}"/></small>
                <h3 class="card-title mt-0 text-white truncate-3-lines"><c:out value="${param.bookTitle}"/></h3>
                <small class="text-white"><i class="bi bi-book-half text-white"></i> <spring:message code="enum.${param.physicalCondition}"/> </small>
            </div>
            <div class="card-footer">
                <div class="media">
                    <c:if test="${param.userPhoto != -1}">
                        <img class="mr-3 rounded-circle" src="<c:url value="/getImage/${param.userPhoto}"/>" alt="Generic placeholder image" style="width:50px; height: 50px">
                    </c:if>
                    <c:if test="${param.userPhoto == -1}">
                        <img class="mr-3 rounded-circle" src="<c:url value="/static/images/profilePicPlaceholder.png"/>" alt="Generic placeholder image" style="width:50px; height: 50px">
                    </c:if>
                    <div class="media-body">
                        <h6 class="my-0 text-white d-block text-truncate"><c:out value="${param.user}"/></h6>
                        <small class="text-white truncate-3-lines"> <c:out value="${param.locality}"/>, <c:out value="${param.province}"/>, <c:out value="${param.country}"/> </small>
                    </div>
                </div>
            </div>
        </div>

    </a>

</div>