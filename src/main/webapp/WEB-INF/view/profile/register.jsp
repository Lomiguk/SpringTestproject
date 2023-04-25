<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 25.03.2023
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Register</title>
    <!-- стиль не работает -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<form:form method="post"
           action="${pageContext.request.contextPath}/profile/register"
           command="profileRegister"
           modelAttribute="profileRegister">
    <form:errors path="*" cssClass="errorblock" element="div"/>
    <label for="login">Login: </label> <br/>
    <input type="text" name="login" id="login" /> <br/>
    <label for="password_F">Password:</label> <br/>
    <input type="password" name="passwordF" id="password_F"/><br/>
    <label for="password_S">Repeat Password:</label> <br/>
    <input type="password" name="passwordS" id="password_S"/><br/>
    <input type="submit">
</form:form>
<p>Already register before? -> <a href="${pageContext.request.contextPath}/profile/login">login</a></p>
</body>
</html>
