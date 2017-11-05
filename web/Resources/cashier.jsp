<%--
  Created by IntelliJ IDEA.
  User: Joshua_123
  Date: 29/10/2017
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/bootstrap-3.3.7/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header"></div>
            <ul class="navbar navbar-nav navbar-right">
                <li><span class="glyphicon glyphicon-user"><c:out value="${user}"/></span></li>
                <li><span class="glyphicon glyphicon-log-out">Logout</span></li>
            </ul>
        </div>
    </div>
</body>
</html>
