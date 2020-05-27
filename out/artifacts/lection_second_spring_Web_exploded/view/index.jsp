<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>--%>
<html>
<head>
    <title>Main page</title>
</head>
<body>
    <h3>Hello World!!</h3>
    <c:forEach var="user" items="${users}">
        ${user.id}
        ${user.username}
    </c:forEach>
    This user is saved ${isSaved} ${username}
    This user by ID ${userById.username}
    <form action="${pageContext.request.contextPath}/api/v1/main/user/save" method="post">
        <input type="text" name="username" placeholder="Insert your username">
        <input type="password" name="password" placeholder="Insert your password">
        <input type="number" name="age" placeholder="Insert your age">
        <input type="submit" value="Registration">
    </form>
</body>
</html>