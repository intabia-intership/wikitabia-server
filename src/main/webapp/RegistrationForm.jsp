<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: dmudryi
  Date: 13.12.2021
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/Index.jsp"><h1>WIKITABIA</h1></a>
<br><br>
<h3>Регистрация</h3>
<br><br>
<form:form action="${pageContext.request.contextPath}/user/create" method="post" modelAttribute="user">
    <form:input path="firstName"/> Имя <br>
    <form:errors path="firstName"/>
    <br><br>
    <form:input path="lastName"/> Фамилия <br>
    <form:errors path="lastName"/>
    <br><br>
    <form:input path="login"/> Логин <br>
    <form:errors path="login"/>
    <br><br>
    <form:input path="password"/> Пароль <br>
    <form:errors path="password"/>
    <br><br>
    <input type="submit" value="ok">
</form:form>
</body>
</html>
