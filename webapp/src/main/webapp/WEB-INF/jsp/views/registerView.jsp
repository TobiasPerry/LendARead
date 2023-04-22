<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>register!</title>
</head>
<body>
<h1>Registration Form</h1>
<c:url	value="/register"	var="registerUrl"	/>
<form:form method="post" action="${registerUrl}" modelAttribute="registerForm">
    <label for="name">Name:</label>
    <form:input type="text" path="name" name="name" required="true"/><br><br>

    <label for="email">Email:</label>
    <form:input type="email" path="email" name="email" required="true"/><br><br>

    <label for="password">Password:</label>
    <form:password path="password"  name="password" required="true"/><br><br>

    <input type="submit" value="Submit">
</form:form>
</body>
</html>
