<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 21.03.2023
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add</title>
</head>
<style>
    .error {
        color: #ff0000;
    }

    .errorblock {
        color: #000;
        background-color: #ffEEEE;
        border: 3px solid #ff0000;
        padding: 8px;
        margin: 16px;
    }
</style>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/person">People</a> |
        <a href="${pageContext.request.contextPath}/profile">Profile</a>
        <hr/>
    </header>
    <form:form method="post" action="${pageContext.request.contextPath}/person/add" commandName = "person" modelAttribute="person">
        <form:errors path = "*" cssClass = "errorblock" element = "div" />

        <label for="name">Name: </label>
        <form:input type="text" id="name" path="name"/> <br/>
        <form:errors path = "name" cssClass = "error" /> <br/>

        <label for="age">Age:</label>
        <form:input type="text"  id="age" path="age"/> <br/>
        <form:errors path = "age" cssClass = "error" /> <br/>

        <label for="phone">Phone:</label>
        <form:input type="text" id="phone" path="phone"/> <br/>
        <form:errors path = "phone" cssClass = "error" /> <br/>

        <label for="email">Email:</label>
        <form:input type="text"  id="email" path="email"/> <br/>
        <form:errors path = "email" cssClass = "error" /> <br/>

        <input type="submit" value="Create!"/>
    </form:form>
</body>
</html>
