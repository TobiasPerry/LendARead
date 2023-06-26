<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container-row" style="justify-content: start">
    <a href="<c:url value="/user/${assetInstance.owner.id}"/>" style="color: inherit; text-decoration: none;">
        <c:choose>
            <c:when test="${assetInstance.owner.profilePhoto != null}">
                <img class="rounded-circle img-hover-click" style="width: 25px; height: 25px" src="<c:url value="/getImage/${assetInstance.owner.profilePhoto.id}"/>" alt="profile picture"/>
            </c:when>
            <c:otherwise>
                <img class="rounded-circle img-hover-click" style="width: 25px;" src="<c:url value="/static/images/user-placeholder.jpeg"/>" alt="profile picture"/>
            </c:otherwise>
        </c:choose>
    </a>
    <a href="<c:url value="/user/${assetInstance.owner.id}"/>" style="color: inherit; text-decoration: none;">
        <span class="mx-2 text-clickable"><c:out value="${assetInstance.owner.name}"/></span>
    </a>

</div>
