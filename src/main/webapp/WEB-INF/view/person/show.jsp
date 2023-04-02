<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 20.03.2023
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>show</title>
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/person">People</a> |
        <a href="${pageContext.request.contextPath}/profile">Profile</a>
        <hr/>
    </header>
    <p>id: ${person.id}</p>
    <p>name: ${person.name}</p>
    <p>phone: ${person.phone}</p>
    <p>age: ${person.age}</p>
    <p>email: ${person.email}</p>
    <c:if test="${sessionScope.getOrDefault('isAdmin', false) == true}">
        <hr/>
        <a href="${pageContext.request.contextPath}/person/${person.id}/edit">Edit</a>
        <form:form method="post" action="${pageContext.request.contextPath}/person/${person.id}" modelAttribute="person">
            <input type="hidden" name="_method" value="delete">
            <input type="submit" value="Delete">
        </form:form>
    </c:if>
</body>
</html>
