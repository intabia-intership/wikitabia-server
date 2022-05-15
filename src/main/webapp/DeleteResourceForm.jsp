<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: dmudryi
  Date: 29.11.2021
  Time: 17:27
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
<form:form action="${pageContext.request.contextPath}/resource/delete" modelAttribute="resource" method="post">
    <form:input path="id"/> Id ресурса
    <br><br>
    <input type="submit" value="ok">
</form:form>
</body>
</html>
