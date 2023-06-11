<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="profile-review-card">
    <div class="profile-review-card-user">
        <img src="<c:url value="/getImage/${param.imgSrc}"/>" alt="Reviewer Profile Picture"/>
        <div class="profile-review-card-info">
            <div class="profile-review-card-username">
                <c:out value="${param.reviewer}"/>
            </div>
            <div class="profile-review-card-userroles">
                <spring:message key="borrower"/>
                <c:if test="${param.roles == 'LENDER'}">
                    - <spring:message key="lender"/>
                </c:if>
            </div>
        </div>
    </div>
    <div class="profile-review-card-review">
        <c:out value="${param.review}"/>
    </div>
</div>