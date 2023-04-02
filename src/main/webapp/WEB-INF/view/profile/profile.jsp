
<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 30.03.2023
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/person">People</a> |
        <a href="${pageContext.request.contextPath}/profile/logout" style="color: red">Logout</a>
        <hr/>
    </header>
    <h3>INFO</h3>
    <p>Login: ${sessionScope.userName}</p>
    <p>User status: <c:choose>
                        <c:when test="${sessionScope.getOrDefault('isAdmin', false) == true}">admin</c:when>
                        <c:when test="${sessionScope.getOrDefault('isAdmin', false) == false}">user</c:when>
                     </c:choose></p>
    <h3>SETTINGS</h3>
    <a href="${pageContext.request.contextPath}/profile/password">change password</a>
    <c:if test="${sessionScope.getOrDefault('isAdmin', false) == true}">
        <h3>ACTIONS</h3>
        <a href="${pageContext.request.contextPath}/profile/get_permission">New admin</a> |
        <a href="${pageContext.request.contextPath}/profile/delete_user">Delete users</a>
    </c:if>
</body>
</html>
