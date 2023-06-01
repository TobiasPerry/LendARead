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
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">
</head>
<body>
<jsp:include page="../components/navBar.jsp"/>
<div class="main-class w-100 h-100">
    <div class="user-main">
        <img class="user-profile-pic"/>
        <div class="user-info">
            <div class="user-role">Borrower - Lender</div>
            <div class="user-name"><c:out value="${user.name}"/></div>
            <div class="user-email"><c:out value="${user.email}"/></div>
            <%-- MAYBE USER DESCRIPTION? --%>
        </div>
    </div>
    <div class="user-ratings">
        <div class="user-reviews-title">Latest Lending Reviews</div>
        <div class="lender-ratings"></div>
        <div class="user-reviews-title">Latest Reviews</div>
        <div class="borrower-ratings"></div>
    </div>
</div>

</body>
</html>
