<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 25.03.2023
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Login</title>
    <!-- стиль не работает -->
    <link href="<c:url value="/static/style.css" />" rel="stylesheet">
</head>
<body>
<form:form method="post"
           action="${pageContext.request.contextPath}/profile/login"
           commandName="profileLogin"
           modelAttribute="profileLogin">
    <form:errors path="*" cssClass="errorblock" element="div"/>
    <label for="login">Login</label> <br/>
    <input type="text" name="login" id="login"/> <br/>
    <label for="password">Password</label> <br/>
    <input type="password" name="password" id="password"/>
    <input type="submit">
</form:form>
<p>If you don't register before -> <a href="${pageContext.request.contextPath}/profile/register">register</a></p>
</body>
</html>