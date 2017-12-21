<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Joshua_123
  Date: 25/11/2017
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                    <a class="dropdown-toggle" data-toggle="${(role=='admin')?'dropdown':'hidden'}" href="#">Hello, <c:out value="${user}"/></a>
                    <ul class="dropdown-menu">
                        <c:if test="${fn:contains(pageContext.request.requestURI, '/cashier.jsp')}">
                            <li><a href="<c:url value="${(role=='admin')?'admin.jsp':'#'}"/>">Go to admin</a></li>
                        </c:if>
                        <c:if test="${fn:contains(pageContext.request.requestURI, '/admin.jsp')}">
                            <li><a href="<c:url value="${(role=='admin')?'cashier.jsp':'#'}"/>">Go to cashier</a></li>
                        </c:if>
                    </ul>
                </li>
                <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
        </div>
    </nav>
</body>
</html>
