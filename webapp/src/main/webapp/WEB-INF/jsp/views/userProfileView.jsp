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
</head>
<body>
<jsp:include page="../components/navBar.jsp"/>
<div class="main-class w-100 h-100">
    <div class="user-grid h-100">
        <div class="user-profile-cell">
            <img class="user-profile-picture"
                 src="https://e0.pxfuel.com/wallpapers/383/587/desktop-wallpaper-anya-spyxfamily-waifu-cute.jpg"/>
        </div>
        <div class="user-info">
            <div class="user-role">
                <div>Borrower</div>
                <div>&nbsp-&nbsp</div>
                <div>Lender</div>
            </div>
            <div class="user-name">
                <h1>PedroLender</h1>
            </div>
        </div>
        <div class="user-locations">
            User Locations
        </div>
        <div class="user-reviews">
            User Reviews
        </div>
    </div>
</div>

</body>
</html>
