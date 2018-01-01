<%--
  Created by IntelliJ IDEA.
  User: Anyatasia
  Date: 21/12/2017
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div class="inner-content container-fluid">
        <div id="customer-table" class="display-tab">
            <div class="col-md-12">
                <ul class="search-tab nav nav-pills navbar-right">
                    <li>
                        <form id="search-customer" class="form-inline" action="" method="post">
                            <div class="input-group">
                                <input id="search-customer-name" name="search-customer" class="form-control" type="text" placeholder="search-customer">
                                <div class="input-group-btn">
                                    <button class="search-btn btn btn-default">
                                        <i class="glyphicon glyphicon-search"></i>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </li>
                    <li><a href="#" data-toggle="modal" data-target="#add-customer"><span class="glyphicon glyphicon-plus"></span> Add Customer</a></li>
                </ul>
            </div>

            <table id="customer-body" class="table head table-striped table-responsive">
                <thead>
                <tr>
                    <th class="col-sm-3">Full Name</th>
                    <th class="col-sm-2">Email</th>
                    <th class="col-sm-2">Phone</th>
                    <th class="col-sm-2">Points</th>
                    <th class="col-sm-3">Action</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>

            <div class="col-md-12">
                <ul class="pager">
                    <li id="customer-previous" class="previous"><a href="">Previous</a></li>
                    <li id="customer-count" class="count"></li>
                    <li id="customer-next" class="next"><a href="">Next</a></li>
                </ul>
            </div>
        </div>
    </div>

    <div id="add-customer" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h3 class="modal-title">Customer form</h3>
                </div>
                <div class="modal-body">
                    <div class="form-center form-horizontal">
                        <div class="form-group">
                            <label class="control-label col-sm-offset-2 col-sm-2" for="input-customer-name">Fullname:</label>
                            <div class="col-sm-6">
                                <input class="form-control" id="input-customer-name" type="text" placeholder="Fullname">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-offset-2 col-sm-2" for="input-email">Email:</label>
                            <div class="col-sm-6">
                                <input class="form-control" id="input-email" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-offset-2 col-sm-2" for="input-phone">Phone:</label>
                            <div class="col-sm-6">
                                <input class="form-control" id="input-phone" type="text">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="customer-submit" class="btn btn-success">Submit</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        setTimeout(getdata("/api/customer/getall?page=0",0,10), 5000);

        $("#customer-submit").click(function () {
            var data={};
            var url="/api/customer/insert";
            data.id=null;
            data.fullname= $("#input-customer-name").val();
            data.email= $("#input-email").val();
            data.phone= $("#input-phone").val();
            data.points= 0;
            console.log(data);
            insertdata(url, JSON.stringify(data));
        });

        $("#search-customer").on('change keyup', function () {
            console.log($(this).serialize());
            getdata('/api/customer/search?'+$(this).serialize()+"&page=0",0,10);
        });

    </script>
</body>
</html>