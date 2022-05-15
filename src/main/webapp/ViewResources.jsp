<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="resources" scope="request" type="java.util.List"/>
<jsp:useBean id="pageRefs" scope="request" type="java.util.List"/>
<jsp:useBean id="tags" scope="request" type="java.util.List"/>

<%--
  Created by IntelliJ IDEA.
  User: dmudryi
  Date: 01.12.2021
  Time: 13:49
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

<table>
    <c:forEach var="resource" items="${resources}">

        <c:out value="${resource.name}     "/>
        <c:out value="${resource.url}     "/>
        <c:out value="${resource.createdAt}     "/>
        <c:forEach var="resourceTag" items="${resource.tags}">
                <c:out value="${resourceTag.name}  "/>
        </c:forEach>
        <br>
    </c:forEach>
    <c:forEach var="pageRef" items="${pageRefs}">
        <c:out escapeXml="false" value="${pageRef} "/>
    </c:forEach>
    <br>
    <br>
</table>
<form action="${pageContext.request.contextPath}/resource/view" method="get">
    Отоброжаемые поля
    <br>
    <label>
        <select name="size" size="3" multiple>
            <option selected value="10">10</option>
            <option value="20">20</option>
            <option value="30">30</option>
        </select>
    </label>
    <br><br>
    Сортировка
    <br>
    <label>
        <select name="sort" size="2" multiple>
            <option selected value="name">название</option>
            <option value="createdAt">дата создания</option>
        </select>
    </label>
    <br><br>
    <label>
        <input type="text" name="filterByName"/> Найти
    </label>
    <br><br>
    Фильтрация по тегам
    <br>
    <label>
        <select name="filterByTag" size="5" multiple>
            <c:forEach var="tag" items="${tags}">
                <option value="${tag.name}">
                    <c:out value="${tag.name}"/>
                </option>
            </c:forEach>
        </select>
    </label>
    <br><br>
    <input type="submit" value="ok">
</form>

</body>
</html>
