<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 31.03.2023
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Password</title>
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/person">People</a> |
        <a href="${pageContext.request.contextPath}/profile">Profile</a>
        <hr/>
    </header>
    <form action="${pageContext.request.contextPath}/profile/password" method="post">
        <label for="oldPassword">Actual password</label>
        <input type="password" name="oldPassword" id="oldPassword">
        <label for="newPassword">New password</label>
        <input type="password" name="newPassword" id="newPassword">
        <input type="submit" value="Change!">
    </form>
</body>
</html>
