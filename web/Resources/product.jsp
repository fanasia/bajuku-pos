<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Joshua_123
  Date: 04/11/2017
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../css/product.css">
    <title>Title</title>
</head>
<body>
    <div class="inner-content container-fluid">
        <ul class="nav nav-tabs nav-justified">
            <li id="product-tab" class="active">
                <a data-toggle="pill" href="#product-table">Products</a>
            </li>
            <li id="categories-tab">
                <a data-toggle="pill" href="#categories-table">Categories</a>
            </li>
        </ul>

        <div class="tab-content">
            <div id="product-table" class="display-tab tab-pane fade in active">
                <div class="col-md-12">
                    <ul class="search-tab nav nav-pills navbar-right">
                        <li class="form-inline">
                            <div class="input-group">
                                <input id="search-product" name="search-product" class="form-control" type="text" placeholder="search-product">
                                <div class="input-group-btn">
                                    <button class="search-btn btn btn-default">
                                        <i class="glyphicon glyphicon-search"></i>
                                    </button>
                                </div>
                            </div>
                        </li>
                        <li><a href="" data-toggle="modal" data-target="#add-product"><span class="glyphicon glyphicon-plus"></span> Add product</a></li>
                    </ul>
                </div>

                <table class="table head">
                    <thead>
                    <tr>
                        <th>Product name</th>
                        <th>Price</th>
                        <th>Stock</th>
                        <th>Location</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                </table>
                <table class="table table-striped">
                    <tbody class="product-body">
                    </tbody>
                </table>
            </div>

            <div id="categories-table" class="display-tab tab-pane fade">
                <div class="col-md-12">
                    <ul class="search-tab nav nav-pills navbar-right">
                        <li class="form-inline">
                            <div class="input-group">
                                <input id="search-categories" name="search-categories" class="form-control" type="text" placeholder="search-categories">
                                <div class="input-group-btn">
                                    <button class="search-btn btn btn-default">
                                        <i class="glyphicon glyphicon-search"></i>
                                    </button>
                                </div>
                             </div>
                        </li>
                        <li><a href="" data-toggle="modal" data-target="#add-categories"><span class="glyphicon glyphicon-plus"></span> Add categories</a></li>
                    </ul>
                </div>

                <table class="table head">
                    <thead>
                    <tr>
                        <th>Categories name</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                </table>
                <table class="table table-striped">
                    <tbody class="categories-body">
                    </tbody>
                </table>

                <div class="col-md-12">
                    <ul class="pager">
                        <li class="previous"><a href="">Previous</a></li>
                        <li class="count"></li>
                        <li class="next"><a href="">Next</a></li>
                    </ul>
                </div>
            </div>

            <div id="add-product" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h3 class="modal-title">Product form</h3>
                        </div>
                        <div class="modal-body">
                            <div class="form-center form-horizontal">
                                <div class="form-group">
                                    <label class="control-label col-sm-offset-2 col-sm-2" for="input-name">Fullname: </label>
                                    <div class="col-sm-6">
                                        <input class="form-control" id="input-name" name="product-name" type="text" placeholder="Fullname">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-offset-2 col-sm-2" for="input-price">Price: </label>
                                    <div class="col-sm-6">
                                        <input class="form-control" id="input-price" name="product-price" type="number">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-offset-2 col-sm-2" for="input-stock">Stock: </label>
                                    <div class="col-sm-6">
                                        <input class="form-control" id="input-stock" name="product-stock" type="number">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-offset-2 col-sm-2" for="input-categories">Categories:</label>
                                    <div class="col-sm-6">
                                        <select class="form-control" name="product-categories" id="input-categories">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-offset-2 col-sm-2" for="input-mapping">Mapping:</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" id="input-mapping" name="product-mapping" type="text" placeholder="Mapping">
                                    </div>
                                </div>
                                <input class="hidden csrftoken" name="csrftoken" type="text" value="${csrftoken}" >
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button id="product-submit" class="btn btn-success">Submit</button>
                        </div>
                    </div>
                </div>
            </div>
            <div id="add-categories" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h3 class="modal-title">Categories form</h3>
                        </div>
                        <div class="modal-body">
                            <div class="form-center form-horizontal">
                                <div class="form-group">
                                    <label class="control-label col-sm-offset-2 col-sm-2" for="input-cat-name">Fullname</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" id="input-cat-name" type="text" placeholder="Fullname">
                                    </div>
                                </div>
                                <input class="hidden csrftoken" name="csrftoken" type="text" value="${csrftoken}" >
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button id="categories-submit" class="btn btn-success">Submit</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        setTimeout(getdata("/api/product/getall", 0), 5000);
        setTimeout(getdata("/api/categories/getall",0), 5000);

        $("#categories-submit").click(function () {
            var data={};
            var url= "/api/categories/insert";
            data.id= null;
            data.fullname= $("#input-cat-name").val();

            console.log(data);
            insertdata(url, JSON.stringify(data));
        });
    </script>

</body>
</html>
