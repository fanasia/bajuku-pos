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
    <link rel="stylesheet" href="../css/admin.css">
    <script src="../js/jquery-3.2.1.js"></script>
    <script src="../css/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
    <script src="../js/admin.js"></script>
    <title>Title</title>
</head>
<body>
    <c:import url="header.jsp"/>

    <div class="content container-fluid">
        <div class="row">
            <div class="sidebar col-md-3">
                <ul class="nav nav-stacked nav-pills">
                    <li class="active"><a data-toggle="pill" href="#ledger-tab"><span class="icon glyphicon glyphicon-book"></span> Ledger</a></li>
                    <li><a data-toggle="pill" href="#user-tab"><span class="icon glyphicon glyphicon-user"></span> User</a></li>
                    <li><a data-toggle="pill" href="#customer-tab"><span class="icon glyphicon glyphicon-user"></span> Customer</a></li>
                    <li><a data-toggle="pill" href="#product-tab"><span class="icon glyphicon glyphicon-gift"></span> Product</a></li>
                    <li><a data-toggle="pill" href="#log-tab"><span class="icon glyphicon glyphicon-th-list"></span> Log-file</a></li>
                </ul>
            </div>
            <div class="tab-content col-md-9">
                <div id="ledger-tab" class="tab-pane fade in active"></div>
                <div id="user-tab" class="tab-pane">
                    <c:import url="user.jsp"/>
                </div>
                <div id="customer-tab" class="tab-pane"></div>
                <div id="product-tab" class="tab-pane">
                    <c:import url="product.jsp"/>
                </div>
                <div id="log-tab" class="tab-pane">
                    <c:import url="logfile.jsp"/>
                </div>
             </div>
        </div>
    </div>

    <footer class="container-fluid text-center">
        <span>Footer</span>
    </footer>

    <script>
        $("li").click(function () {
            console.log($(this)[0].firstChild.hash);
        });
    </script>

</body>
</html>
