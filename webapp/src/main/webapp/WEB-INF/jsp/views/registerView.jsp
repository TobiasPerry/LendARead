<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>register!</title>
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro.ico'/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/login.css"/>" rel="stylesheet"/>
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
                    <c:url	value="/register"	var="registerUrl"	/>
                    <form:form method="post" action="${registerUrl}" modelAttribute="registerForm" style="width: 23rem;">
                    <h2 class="fw-normal mb-3 pb-3 text-center" style="letter-spacing: 1px;">Register</h2>

                    <div class="form-outline mb-4">
                        <label for="name" style="width: 100%">Name
                            <form:input class="form-control" type="text" path="name" name="name" required="true"/>
                        </label>
                    </div>

                    <div class="form-outline mb-4">
                        <label for="email" style="width: 100%">Email
                            <form:input class="form-control" type="email" path="email" name="email" required="true"/>
                        </label>
                    </div>

                    <div class="form-outline mb-4">
                        <label for="password" style="width: 100%">Password
                            <form:password class="form-control" path="password"  name="password" required="true"/>
                        </label>
                        <small id="passwordHelpInline" class="text-muted">
                            <br>Must be 8-20 characters long.
                        </small>
                    </div>

                    <div class="form-outline mb-4 text-center">
                        <input class="btn btn-light" type="submit" value="Submit">
                    </div>

                    <div class="pt-1 mb-4 text-center">
                        <a href="<c:url value="/login"/>" class="text-muted">Already have an account? Log In</a>
                    </div>

                    </form:form>
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

