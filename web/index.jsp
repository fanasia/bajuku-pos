<%--
  Created by IntelliJ IDEA.
  User: Joshua_123
  Date: 15/10/2017
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Bajuku POS-online POS system</title>
      <link rel="stylesheet" type="text/css" href="css/bootstrap-3.3.7/dist/css/bootstrap.min.css">
      <link rel="stylesheet" href="css/index.css">
      <script src="css/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
  </head>
  <body>
    <c:set var="display" value="hidden"/>
    <div id="form-login" class="container">
        <h1>User Login</h1>
        <form class="form-signin" action="/login" method="post">
            <label for="username-input">Username</label>
            <input type="text" class="form-control" id="username-input" name="username" autocomplete="off">
            <label for="password-input">Password</label>
            <input type="password" class="form-control" id="password-input" name="password">

            <button class="btn btn-success btn-block">Login</button>
        </form>

        <c:if test="${not empty error}">
            <c:set var="display" value="error"/>
        </c:if>

        <div class="alert alert-danger alert-dismissable ${display}">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <c:out value="${error}"/>
        </div>
    </div>

  </body>
</html>
