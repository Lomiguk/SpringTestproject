<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 25.03.2023
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/profile/register" method="post">
    <p>Login: </p>
    <input type="text" name="login"> <br/>
    <p>Password:</p>
    <input type="password" name="password_1"><br/>
    <p>Repeat Password:</p>
    <input type="password" name="password_2">
    <input type="submit">
</form>
<p>Already register before? -> <a href="${pageContext.request.contextPath}/profile/login">login</a></p>
</body>
</html>
