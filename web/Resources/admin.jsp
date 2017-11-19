<%--
  Created by IntelliJ IDEA.
  User: Joshua_123
  Date: 29/10/2017
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap-3.3.7/dist/css/bootstrap.min.css">
    <script src="../js/jquery-3.2.1.js"></script>
    <script src="../css/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
    <title>Title</title>
</head>
<body>
    <nav class="navbar">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="">Bajuku POS</a>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="">Hello, <c:out value="${user}"/></a>
                    <ul class="dropdown-menu">
                        <li><a href=""></a></li>
                    </ul>
                </li>
                <li><a href=""><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3">
                <ul class="nav nav-stacked">
                    <li class="active"><a href="">Ledger</a></li>
                    <li><a href="#user-tab">User</a></li>
                    <li><a href="#customer-tab">Customer</a></li>
                    <li><a href="#product-tab">Product</a></li>
                    <li><a href="#log-tab">Log-file</a></li>
                </ul>
            </div>
            <div class="tab-content col-md-9">
                <div id="user-tab"></div>
                <div id="customer-tab"></div>
                <div id="product-tab" class="tab-pane">
                    <c:import url="product.jsp"/>
                </div>
                <div id="log-tab"></div>
             </div>
        </div>
    </div>

    <footer class="container-fluid text-center">
        <span>Footer</span>
    </footer>

</body>
</html>
