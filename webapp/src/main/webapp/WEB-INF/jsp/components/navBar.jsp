<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="<c:url value="/static/css/navBar.css"/>" rel="stylesheet"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<nav class="navbar navbar-expand-lg" style="height: 75px; background-color: #111711" data-bs-theme="dark">
    <div class="container-fluid">
        <a href="<c:url value="/"/>" class="nav-icon"><img src="<c:url value="/static/images/logo-claro.png"/>" alt="Lend a read logo" style="width: 200px"></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse d-flex justify-content-end" id="navbarSupportedContent">
            <ul class="navbar-nav mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link navItem" id="home" aria-current="page" href="<c:url value="/"/>"><spring:message code="navBar.home" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navItem"  id="addAsset" aria-current="page" href="<c:url value="/addAssetView"/>"><spring:message code="navBar.borrow" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navItem" id="userHome"  aria-current="page" href="<c:url value='/userHome'/>">
                        <i class="fas fa-user"></i>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navItem" id="logout"  aria-current="page" href="<c:url value='/logout'/>">
                        <i class="fas fa-sign-out-alt"></i>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

