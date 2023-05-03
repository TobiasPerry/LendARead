<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>

<head>

    <title><spring:message code="auth.login"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/login.css"/>" rel="stylesheet"/>
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro.ico'/>" type="image/x-icon">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Overpass:wght@700&display=swap" rel="stylesheet">

</head>

<body style="overflow-y: hidden;">

<section class="vh-100">
    <div class="container-fluid">
        <div class="row">
            <div class="d-flex flex-column justify-content-center align-items-center text-black main-class col-sm-6">
                <div class="px-5 ms-xl-4 mt-10">
                    <img src="<c:url value='/static/images/logo-oscuro.png' />" alt="Lend a read logo" style="width: 300px">
                </div>

                <div class="d-flex flex-column justify-content-center align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">
                    <c:url value="/login" var="loginUrl" />
                    <form action="${loginUrl}" method="post" style="width: 23rem;">
                        <h2 class=" mb-3 pb-3 text-center" style="letter-spacing: 1px;"><spring:message code="auth.login" /></h2>

                        <div class="form-outline mb-4" style="width: 100%">
                            <label style="width: 100%"><spring:message code="auth.email" />
                                <input class="form-control" type="text" name="email" />
                            </label>
                        </div>

                        <div class="form-outline mb-4">
                            <label style="width: 100%"><spring:message code="auth.password" />
                                <input class="form-control" name="password" type="password" />
                            </label>
                            <c:if test="${param.error != null}">
                                <label style="color:red;"><spring:message code="auth.loginError"/></label>
                            </c:if>
                        </div>

                        <div class="form-outline mb-4 text-center">
                            <label class="form-check-label">
                                <input class="form-check-input" name="rememberme" type="checkbox">
                                <spring:message code="auth.rememberMe" />
                            </label>
                        </div>

                        <div class="pt-1 mb-4 text-center">
                            <input class="btn btn-light" type="submit" value="<spring:message code="auth.logInBtn"/>" />
                        </div>

                        <div class="pt-1 mb-4 text-center">
                            <a href="<c:url value="/register"/>" class="text-muted"><spring:message code="auth.doNotHaveAccount" /></a>
                        </div>
                        <div class="pt-1 mb-4 text-center">
                            <a href="<c:url value="/forgotPassword"/>" class="text-muted"><spring:message code="auth.forgotPassword" /></a>
                        </div>
                    </form>
                </div>

            </div>
            <div class="col-sm-6 px-0 d-none d-sm-block">
                <img src="<c:url value='/static/images/login-bg.jpg' />" alt="Login image" class="w-100 vh-100" style="object-fit: cover; object-position: left;" />
            </div>
        </div>
    </div>
</section>


</body>
</html>