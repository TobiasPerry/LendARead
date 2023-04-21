<%@	taglib	prefix="c"	uri="http://java.sun.com/jstl/core_rt"%>
<%@	taglib	prefix="spring"	uri="http://www.springframework.org/tags"%>
<html>
<body>
<c:url	value="/login"	var="loginUrl"	/>
<form	action="${loginUrl}"	method="post">
    <div>
        <label>Username:
        <input	type="text" name="email"/>
        </label>
    </div>
    <div>
        <label>Password:
        <input	name="password"	type="password"/>
        </label>
    </div>
    <div>
        <label>
            <input name="rememberme" type="checkbox">
            Remember Me!
        </label>
    </div>
    <div>
        <input	type="submit"	value="Login!"/>
    </div>

</form>
</body>
</html>