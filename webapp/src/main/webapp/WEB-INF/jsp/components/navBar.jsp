<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!-- jQuery library -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Popper JS library -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.9.3/umd/popper.min.js"></script>

<!-- Bootstrap JS library -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.1.3/js/bootstrap.min.js"></script>



<link href="<c:url value="/static/css/navBar.css"/>" rel="stylesheet"/>
<script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<nav class="navbar navbar-expand-lg" style="background-color: #111711" data-bs-theme="dark">
    <div class="container-fluid">
        <a href="<c:url value="/"/>" class="nav-icon"><img src="<c:url value="/static/images/logo-claro.png"/>" alt="Lend a read logo" style="width: 200px"></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse d-lg-flex justify-content-lg-end" id="navbarSupportedContent">
            <ul class="navbar-nav mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link navItem" id="home" aria-current="page" href="<c:url value="/discovery"/>"><spring:message code="navBar.home" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navItem"  id="addAsset" aria-current="page" href="<c:url value="/addAssetView"/>"><spring:message code="navBar.borrow" /></a>
                </li>
                <security:authorize access="isAuthenticated()">
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
                </security:authorize>
                <security:authorize access="!isAuthenticated()">
                <li class="nav-item">
                    <a class="nav-link navItem" id="login"  aria-current="page" href="<c:url value='/login'/>">
                        <spring:message code="auth.login"/>
                    </a>
                </li>
                </security:authorize>
            </ul>
        </div>
    </div>
</nav>

