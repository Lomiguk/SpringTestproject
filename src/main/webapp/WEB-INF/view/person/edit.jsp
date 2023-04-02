<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 21.03.2023
  Time: 23:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/person">People</a> |
        <a href="${pageContext.request.contextPath}/profile">Profile</a>
        <hr/>
    </header>
    <form:form method="post" action="${pageContext.request.contextPath}/person/${person.id}" modelAttribute="person">
        <input type="hidden" name="_method" value="patch">
        <label for="name">Name: </label>
        <form:input type="text" id="name" path="name" value="${person.name}"/> <br/>
        <label for="age">Age:</label>
        <form:input type="text"  id="age" path="age" value="${person.age}"/> <br/>
        <label for="phone">Phone:</label>
        <form:input type="tel" id="phone" path="phone" value="${person.phone}"/> <br/>
        <label for="email">Email:</label>
        <form:input type="text"  id="email" path="email" value="${person.email}"/> <br/>
        <input type="submit" value="Update!"/>
    </form:form>
</body>
</html>
