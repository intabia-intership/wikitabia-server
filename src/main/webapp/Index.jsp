<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>WIKITABIA</h1>
<br><br>
<h4>Resources CRUD</h4>
<a href="${pageContext.request.contextPath}/resource/view" methods="post">Просмотр ресурсов</a>
<br><br>
<a href="${pageContext.request.contextPath}/resource/create" methods="get">Создание ресурса</a>
<br><br>
<a href="${pageContext.request.contextPath}/resource/update" methods="get">Обновить ресурс</a>
<br><br>
<a href="${pageContext.request.contextPath}/resource/delete" methods="get">Удалить ресурс</a>
<br><br>
<h4>Users CRUD</h4>
<br>
<a href="${pageContext.request.contextPath}/user/create" methods="get">Создание пользователя</a>
<br><br>
<a href="${pageContext.request.contextPath}/user/update" methods="get">Обновить пользователя</a>
<br><br>
<a href="${pageContext.request.contextPath}/user/delete">Удалить пользователя</a>

</body>
</html>
