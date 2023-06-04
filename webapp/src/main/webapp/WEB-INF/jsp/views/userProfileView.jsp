<%--
  Created by IntelliJ IDEA.
  User: pedro
  Date: 5/31/23
  Time: 9:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><c:out value="Profile ${user.name}" escapeXml="true"/></title>


    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro-bg.ico'/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/static/css/userProfile.css"/>"/>
    <script src="<c:url value="/static/javaScript/utils.js"/>" defer></script>
</head>
<body>
<jsp:include page="../components/navBar.jsp"/>
<div class="main-class w-100 h-100">
    <div class="user-grid h-100">
        <div class="user-profile-cell">
            <label for="uploadImage" class="user-profile-picture-container">
                <img class="user-profile-picture" id="bookImage"
                     src="https://e0.pxfuel.com/wallpapers/383/587/desktop-wallpaper-anya-spyxfamily-waifu-cute.jpg"/>
                <svg xmlns="http://www.w3.org/2000/svg" id="user-profile-picture-text" width="16" height="16"
                     fill="currentColor"
                     class="user-profile-picture-text bi bi-pencil-square" viewBox="0 0 16 16">
                    <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"></path>
                    <path fill-rule="evenodd"
                          d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"></path>
                </svg>
            </label>
        </div>
        <div class="user-info info-container">
            <div class="user-role">
                <div class="medium grey-text"><spring:message code="borrower"/></div>
                <c:if test="${user.behavior == 'LENDER'}">
                    <div class="medium grey-text">&nbsp-&nbsp</div>
                    <div class="medium grey-text"><spring:message code="lender"/></div>
                </c:if>
            </div>
            <div class="user-name big">
                <c:out value="${user.name}"/>
            </div>
            <div class="user-ratings">
                <div class="user-rating-title medium grey-text">
                    <spring:message code="userProfile.ratings"/>
                </div>
                <div class="user-ratings-wrapper">
                    <div class="user-rating-wrapper">
                        <div class="small grey-text"><spring:message code="borrower"/></div>
                        <div class="rating">
                            <% for (int i = 0; i < 5; i++) { %>
                            <i class="fas fa-star d-inline-block star"></i>
                            <% } %>
                        </div>
                    </div>
                    <c:if test="${user.behavior == 'LENDER'}">
                        <div class="user-rating-wrapper">
                            <div class="small grey-text"><spring:message code="lender"/></div>
                            <div class="rating">
                                <% for (int i = 0; i < 5; i++) { %>
                                <i class="fas fa-star d-inline-block star"></i>
                                <% } %>
                            </div>
                        </div>
                    </c:if>
                    <div class="spacer">

                    </div>
                </div>
            </div>
        </div>
        <div class="user-locations">
            User Locations
        </div>
        <div class="user-reviews">
            User Reviews
        </div>
        <input type="file" accept="image/*" name="file" id="uploadImage" style="display:none;"
               onchange="previewImage()"/>
    </div>
</div>

</body>
</html>
