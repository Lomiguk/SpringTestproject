<%@ page import="com.lomiguk.springapp.model.priofile.Profile" %><%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 02.04.2023
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Delete user</title>
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/person">People</a> |
        <a href="${pageContext.request.contextPath}/profile">Profile</a>
        <hr/>
    </header>
    <form action="${pageContext.request.contextPath}/profile/admin/prof/get_profiles_for_delete" method="post">
        <label for="userLogin">User login</label>
        <input type="text" name="userLogin" id="userLogin"/>
        <input type="submit" value="поиск">
    </form>
    <!-- Тут вместо c:choose лучше наверное было бы уйти в Java блок и переменную зафигачить -->
    <c:forEach items="${profiles}" var="profileVar">
        <p>
             ${profileVar.id} ${profileVar.login} (<c:choose>  <c:when test="${profileVar.isAdmin == true}">
                                                                    admin
                                                               </c:when>
                                                               <c:when test="${profileVar.isAdmin == false}">
                                                                    user
                                                               </c:when></c:choose>)

            <form action="${pageContext.request.contextPath}/profile/admin/prof/delete" method="post">
                <input type="hidden" value="${profileVar.id}" name="userId" id="userId">
                <input type="submit" value="delete">
            </form>
        </p>
    </c:forEach>
</body>
</html>
