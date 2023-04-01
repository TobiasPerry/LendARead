<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: lauti
  Date: 3/30/2023
  Time: 11:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <c:choose >
                        <c:when test='${path == "/"}'>
                            <a class="nav-link active" aria-current="page" href="/">Home</a>
                        </c:when>
                        <c:otherwise>
                            <a class="nav-link " aria-current="page" href="/">Home</a>
                        </c:otherwise>
                    </c:choose>

                </li>
                <li class="nav-item">
                    <c:choose >
                        <c:when test='${path == "/lend"}'>
                            <a class="nav-link active" aria-current="page" href="/addAssetView">Lend</a>
                        </c:when>
                        <c:otherwise>
                            <a class="nav-link " aria-current="page" href="/addAssetView">Lend</a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>
            <form class="d-flex" role="search">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>