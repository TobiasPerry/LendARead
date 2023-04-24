<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<nav class="navbar navbar-expand-lg" style="height: 50px; background-color: #393E41" data-bs-theme="dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value="/"/>">Lend a Read</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse d-flex justify-content-between" id="navbarSupportedContent">
            <ul class="navbar-nav mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link navItem" id="home" aria-current="page" href="<c:url value="/"/>"><spring:message code="navBar.home" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navItem"  id="addAsset" aria-current="page" href="<c:url value="/addAssetView"/>"><spring:message code="navBar.borrow" /></a>
                </li>
            </ul>
            <a class="nav-link navItem" id="userHome"  aria-current="page" href="<c:url value='/userHome'/>">
                <i class="fas fa-user"></i>
            </a>
        </div>
    </div>
</nav>


