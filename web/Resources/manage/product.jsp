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
    <title>Title</title>
</head>
<body>
    <div class="inner-content container-fluid">
        <ul class="nav nav-tabs nav-justified">
            <li id="product-inner-tab" class="active"><a data-toggle="pill" href="#product-table">Products</a></li>
            <li id="categories-inner-tab"><a data-toggle="pill" href="#categories-table">Categories</a></li>
        </ul>

        <div class="tab-content">
            <div id="product-table" class="display-tab tab-pane fade active in">
                <div class="col-md-12">
                    <ul class="search-tab nav nav-pills navbar-right">
                        <li class="form-inline">
                            <form id="search-product" action="" method="get">
                                <div class="input-group">
                                    <span class="input-group-addon" id="filter-categories">
                                    </span>
                                    <input id="search-product-name" name="search-product" class="form-control" type="text" placeholder="search-product">
                                    <div class="input-group-btn">
                                        <button class="search-btn btn btn-default">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </li>
                        <li><a href="#" data-toggle="modal" data-target="#add-product"><span class="glyphicon glyphicon-plus"></span> Add product</a></li>
                    </ul>
                </div>

                <table id="product-body" class="table table-striped table-responsive">
                    <thead>
                    <tr>
                        <th class="col-sm-3">Product name</th>
                        <th class="col-sm-2">Categories</th>
                        <th class="col-sm-2">Price</th>
                        <th class="col-sm-1">Stock</th>
                        <th class="col-sm-2">Location</th>
                        <th class="col-sm-2">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

                <div class="col-md-12">
                    <ul class="pager">
                        <li id="product-previous" class="previous"><a href="">Previous</a></li>
                        <li id="product-count" class="count"></li>
                        <li id="product-next" class="next"><a href="">Next</a></li>
                    </ul>
                </div>
            </div>

            <div id="categories-table" class="display-tab tab-pane fade">
                <div class="col-md-12">
                    <ul class="search-tab nav nav-pills navbar-right">
                        <li class="form-inline">
                            <form id="search-categories" action="" method="get">
                                <div class="input-group">
                                        <input id="search-categories-name" name="search-categories" class="form-control" type="text" placeholder="search-categories">
                                        <div class="input-group-btn">
                                            <button class="search-btn btn btn-default">
                                                <i class="glyphicon glyphicon-search"></i>
                                            </button>
                                        </div>
                                 </div>
                            </form>
                        </li>
                        <li><a href="#" data-toggle="modal" data-target="#add-categories"><span class="glyphicon glyphicon-plus"></span> Add categories</a></li>
                    </ul>
                </div>

                <table id="categories-body" class="table head table-striped table-responsive">
                    <thead>
                    <tr>
                        <th class="col-sm-1">Categories name</th>
                        <th class="col-sm-1">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

                <div class="col-md-12">
                    <ul class="pager">
                        <li id="categories-previous" class="previous"><a href="">Previous</a></li>
                        <li id="categories-count" class="count"></li>
                        <li id="categories-next" class="next"><a href="">Next</a></li>
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
                                        <input class="form-control" id="input-name" type="text" placeholder="Fullname">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-offset-2 col-sm-2" for="input-price">Price: </label>
                                    <div class="col-sm-6">
                                        <input class="form-control" id="input-price" type="number">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-offset-2 col-sm-2" for="input-stock">Stock: </label>
                                    <div class="col-sm-6">
                                        <input class="form-control" id="input-stock" type="number">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-offset-2 col-sm-2" for="input-categories">Categories:</label>
                                    <div class="col-sm-4">
                                        <select class="form-control select-categories" id="input-categories" name="select-categories">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-offset-2 col-sm-2" for="input-mapping">Mapping:</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" id="input-mapping" type="text" placeholder="Mapping">
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
                                    <label class="control-label col-sm-offset-2 col-sm-2" for="input-cat-name">Fullname:</label>
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
        setTimeout(getdata("/api/product/getall?limit=7&page=0", 0, 7), 5000);
        setTimeout(getdata("/api/categories/getall?limit=7&page=0",0, 7), 5000);

        //categories for selection
        setTimeout(getcategories(), 5000);

        $("#input-categories").clone().appendTo("#filter-categories").empty().css({
            "border":"none", "background-color":"transparent"
        }).removeClass("form-control");

        $("#categories-submit").click(function () {
            var data={};
            var url= "/api/categories/insert";
            data.id= null;
            data.fullname= $("#input-cat-name").val();

            console.log(data);
            insertdata(url, JSON.stringify(data));
        });

        $("#product-submit").click(function () {
           var data={};
           var url= "/api/product/insert";
           data.id=null;
           data.fullname= $("#input-name").val();
           data.category_id= parseInt($("#input-categories").val());
           data.price= parseFloat($("#input-price").val());
           data.stock= parseInt($("#input-stock").val());
           data.mapping= $("#input-mapping").val();

           console.log(data);
           insertdata(url, JSON.stringify(data));
        });

        //search action
        $("#search-product").on('change keyup', function () {
            console.log($(this).serialize());
            getdata("/api/product/search?"+$(this).serialize()+"&limit=7&page=0",0,7);
        });

        $("#search-categories").keyup(function () {
            console.log($(this).serialize());
            getdata("/api/categories/search?"+$(this).serialize()+"&limit=7&page=0",0,7);
        });

    </script>

</body>
</html>
