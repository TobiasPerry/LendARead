<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg" style="height: 75px; background-color: #111711" data-bs-theme="dark">
    <div class="container-fluid">
        <a href="<c:url value="/"/>"><img src="<c:url value="/static/images/logo-claro.png"/>" alt="Lend a read logo" style="width: 200px"></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link navItem" id="home" aria-current="page" href="<c:url value="/"/>">Inicio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link navItem"  id="addAsset" aria-current="page" href="<c:url value="/addAssetView"/>">Prestar libro</a>
                </li>
            </ul>

        </div>
    </div>
</nav>

