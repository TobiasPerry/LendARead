<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="profile-review-card">
    <div class="profile-review-card-user">
        <div class="profile-review-card-img-wrapper">
            <img src="<c:url value="/getImage/${param.imgSrc}"/>" alt="Reviewer Profile Picture"/>
        </div>
        <div class="profile-review-card-info">
            <a class="text-clickable" href="<c:url value="/user/${param.userId}"/> ">
                <h4 class="profile-review-card-username">
                    <c:out value="${param.reviewer}"/>
                </h4>
            </a>
            <h6 class="profile-review-card-userroles grey-text">
                <spring:message key="borrower"/>
                <c:if test="${param.role == 'LENDER'}">
                    - <spring:message key="lender"/>
                </c:if>
            </h6>
        </div>
    </div>
    <div class="profile-review-card-review">
        <i class="fas fa-solid fa-quote-left"></i>
        <div class="profile-review-card-review-text">
            <c:out value="${param.review}"/>
        </div>
        <i class="fas fa-solid fa-quote-right"></i>
    </div>
</div>