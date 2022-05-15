<%--
  Created by IntelliJ IDEA.
  User: dmudryi
  Date: 13.12.2021
  Time: 16:47
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
<form method="post" action="${pageContext.request.contextPath}/authorisation">
    <label>
        <input type="text" name="login"> Введите логин
    </label>
    <br><br>
    <label>
        <input type="text" name="password"> Введите пароль
    </label>
    <br><br>
    <button type="submit">Ок</button>
</form>
<br><br>
<a href="${pageContext.request.contextPath}/user/create" methods="get"><h3>Зарегистрироваться</h3></a>
</body>
</html>
