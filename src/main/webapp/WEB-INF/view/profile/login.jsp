<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 25.03.2023
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/profile/login" method="post">
    <p>Register</p>
    <input type="text" name="login"> <br/>
    <p>Password</p>
    <input type="password" name="password">
    <input type="submit">
</form>
<p>If you don't register before -> <a href="${pageContext.request.contextPath}/profile/register">register</a></p>
</body>
</html>