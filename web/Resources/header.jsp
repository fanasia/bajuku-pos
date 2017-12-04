<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Joshua_123
  Date: 25/11/2017
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:set var="user" value="${user}"/>
    <nav class="navbar">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="">Bajuku POS</a>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="">Hello, <c:out value="${user}"/></a>
                    <c:if test="${role=='admin'}">
                        <c:set var="link" value="admin.jsp"/>
                    </c:if>
                    <ul class="dropdown-menu" ${(role=='admin')?'display':'hidden'}>
                        <li><a href="${link}"><span class="glyphicon glyphicon-usd"></span> Go to cashier</a></li>
                    </ul>
                </li>
                <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
        </div>
    </nav>
</body>
</html>
