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
    <title>Cashier</title>
    <link rel="stylesheet" href="../css/bootstrap-3.3.7/dist/css/bootstrap.min.css">
    <script src="../js/jquery-3.2.1.js"></script>
    <script src="../css/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../css/cashier.css">
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

    <c:set var="customer" value="No member"/>
    <div class="container-fluid">
        <div class="row">
            <div id="left-col" class="col-md-5 col-xs-7">
                <!-- Left -->
                <div class="col-md-12 form-inline">
                    <span class="symbol glyphicon glyphicon-user"></span>
                    <input class="form-control" type="text">
                    <button class="btn btn-success">Search</button>
                </div>
                <div id="customer-info" class="col-md-12">
                    <c:out value="${customer}"/>
                </div>
                <div id="cart">
                    <table id="cart-header" class="table">
                        <thead>
                            <tr>
                                <th class="col-sm-3">Name</th>
                                <th class="col-sm-3">Quantity</th>
                                <th class="col-sm-4">Price</th>
                                <th class="col-sm-2">Action</th>
                            </tr>
                        </thead>
                    </table>
                    <div id="cart-body">
                        <table class="table">
                            <tbody>
                            <tr>
                                <td class="col-sm-3">T-Shirt</td>
                                <td class="col-sm-3"><input class="form-control" type="number"></td>
                                <td class="col-sm-4">
                                    <div class="input-group">
                                        <span class="input-group-addon">IDR</span>
                                        <input class="form-control" type="number" value="150000" disabled>
                                    </div>
                                </td>
                                <td class="remove-btn col-sm-2"><button class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div id="cart-info" class="well col-md-12">
                </div>
                <div class="col-md-12">
                    <button class="btn btn-block btn-link">Reset</button>
                    <button class="btn btn-block btn-success">Continue</button>
                </div>
            </div>

            <div class="col-md-7 col-xs-7">
                <!-- Right -->
                <div class="col-md-9 col-xs-9 form-inline">
                    <div class="form-group">
                        <span class="symbol glyphicon glyphicon-search"></span>
                        <select class="form-control">
                            <option value=""></option>
                        </select>
                        <input class="form-control" type="text">
                        <button class="btn btn-success">Search</button>
                    </div>
                </div>
                <div class="col-md-3 col-xs-3">
                    <button class="btn btn-default" data-toggle="modal" data-target="#add-member"><span class="glyphicon glyphicon-plus"></span></button>
                    <button class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span></button>
                </div>

                <div id="add-member" class="modal fade" role="dialog">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h3 class="modal-title">Customer membership</h3>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">

                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success">Submit</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="product-container" class="well col-md-12">
                    <div class="product-info col-md-3">
                        <a href="">PRODUCT1</a>
                    </div>
                    <div class="product-info col-md-3">
                        <a href="">PRODUCT2</a>
                    </div>
                    <div class="product-info col-md-3">
                        <a href="">PRODUCT3</a>
                    </div>
                    <div class="product-info col-md-3">
                        <a href="">PRODUCT4</a>
                    </div>
                </div>

            </div>
        </div>

        <footer class="container-fluid text-center">
            <span>Footer</span>
        </footer>
    </div>
</body>
</html>
