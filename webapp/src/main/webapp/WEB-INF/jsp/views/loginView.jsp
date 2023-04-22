<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>

    <title>Log In</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/login.css"/>" rel="stylesheet"/>
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro.ico'/>" type="image/x-icon">
</head>

<body>
<%--<c:url	value="/login"	var="loginUrl"	/>--%>
<%--<form	action="${loginUrl}"	method="post">--%>
<%--    <div>--%>
<%--        <label>Username:--%>
<%--        <input	type="text" name="email"/>--%>
<%--        </label>--%>
<%--    </div>--%>
<%--    <div>--%>
<%--        <label>Password:--%>
<%--        <input	name="password"	type="password"/>--%>
<%--        </label>--%>
<%--    </div>--%>
<%--    <div>--%>
<%--        <label>--%>
<%--            <input name="rememberme" type="checkbox">--%>
<%--            Remember Me!--%>
<%--        </label>--%>
<%--    </div>--%>
<%--    <div>--%>
<%--        <input	type="submit"	value="Login!"/>--%>
<%--    </div>--%>

<%--</form>--%>



<section class="vh-100">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-6 text-black">

                <div class="px-5 ms-xl-4">
                    <span class="h1 fw-bold mb-0">Logo</span>
                </div>

                <div class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">

                    <c:url	value="/login"	var="loginUrl"	/>
                    <form action="${loginUrl}" method="post" style="width: 23rem;">

                        <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Log in</h3>

                        <div class="form-outline mb-4">
                            <label>Username:
                                <input	type="text" name="email"/>
                            </label>
                        </div>

                        <div class="form-outline mb-4">
                            <label>Password:
                                <input	name="password"	type="password"/>
                            </label>
                        </div>

                        <div class="form-outline mb-4">
                            <label>
                                <input name="rememberme" type="checkbox">
                                Remember Me!
                            </label>
                        </div>

                        <div class="pt-1 mb-4">
                            <input	type="submit"	value="Login!"/>
                        </div>

                    </form>

                </div>

            </div>
            <div class="col-sm-6 px-0 d-none d-sm-block">
                <img src="<c:url value="/static/images/login-bg.jpg"/>"
                     alt="Login image" class="w-100 vh-100" style="object-fit: cover; object-position: left;"/>
            </div>
        </div>
    </div>
</section>

</body>
</html>