<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 20.03.2023
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <title>All people</title>
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/profile">Profile</a>
        <hr/>
    </header>
    <c:if test="${sessionScope.isAuthorised}">
        Hello <c:if test="${sessionScope.isAdmin}">admin</c:if> ${sessionScope.userName}!
    </c:if>
    <br/>
    <c:forEach items="${people}" var="profileVar">
        <a href="${pageContext.request.contextPath}/person/${profileVar.id}">
                ${profileVar.id} ${profileVar.name} (${profileVar.age})
        </a><br/>
    </c:forEach>
    <br/>
    <hr/>
    <a href="${pageContext.request.contextPath}/person/add">Add person</a>
    <a href="${pageContext.request.contextPath}/profile">Profile</a>
</body>
</html>
