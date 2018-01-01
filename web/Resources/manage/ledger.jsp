<%--
  Created by IntelliJ IDEA.
  User: Joshua_123
  Date: 18/12/2017
  Time: 15:16
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
            <li id="daily-tab" class="active"><a data-toggle="pill" href="#daily-table">Daily</a></li>
            <li id="weekly-tab"><a data-toggle="pill" href="#weekly-table">Weekly</a></li>
            <li id="monthly-tab"><a data-toggle="pill" href="#monthly-table">Monthly</a></li>
            <li id="yearly-tab"><a data-toggle="pill" href="#yearly-table">Yearly</a></li>
        </ul>

        <div class="tab-content">
            <div id="daily-table" class="display-tab tab-pane fade active in">
                <div class="col-md-12">
                    <ul class="search-tab nav nav-pills navbar-right">
                        <li class="form-inline">
                            <form id="search-daily" action="" method="get">
                                <div class="input-group">
                                    <input name="search-daily" class="form-control" type="date">
                                    <div class="input-group-btn">
                                        <button class="search-btn btn btn-default">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </li>
                    </ul>
                </div>

                <table id="daily-body" class="table head table-striped">
                    <thead>
                    <tr>
                        <th class="col-sm-1">Date</th>
                        <th class="col-sm-1">Value</th>
                        <th class="col-sm-1">Quantity</th>
                        <th class="col-sm-1">Discount</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

                <div class="col-md-12">
                    <ul class="pager">
                        <li id="daily-previous" class="previous"><a href="">Previous</a></li>
                        <li id="daily-count" class="count"></li>
                        <li id="daily-next" class="next"><a href="">Next</a></li>
                    </ul>
                </div>
            </div>

            <div id="weekly-table" class="display-tab tab-pane fade">
                <div class="col-md-12">
                    <ul class="search-tab nav nav-pills navbar-right">
                        <li class="form-inline">
                            <form id="search-weekly" action="" method="get">
                                <div class="input-group">
                                    <input name="search-weekly" class="form-control" type="week">
                                    <div class="input-group-btn">
                                        <button class="search-btn btn btn-default">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </li>
                    </ul>
                </div>

                <table id="weekly-body" class="table head table-striped">
                    <thead>
                    <tr>
                        <th class="col-sm-1">Date</th>
                        <th class="col-sm-1">Value</th>
                        <th class="col-sm-1">Quantity</th>
                        <th class="col-sm-1">Discount</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

                <div class="col-md-12">
                    <ul class="pager">
                        <li id="weekly-previous" class="previous"><a href="">Previous</a></li>
                        <li id="weekly-count" class="count"></li>
                        <li id="weekly-next" class="next"><a href="">Next</a></li>
                    </ul>
                </div>
            </div>

            <div id="monthly-table" class="display-tab tab-pane fade">
                <div class="col-md-12">
                    <ul class="search-tab nav nav-pills navbar-right">
                        <li class="form-inline">
                            <form id="search-monthly" action="" method="get">
                                <div class="input-group">
                                    <input name="search-monthly" class="form-control" type="month">
                                    <div class="input-group-btn">
                                        <button class="search-btn btn btn-default">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </li>
                    </ul>
                </div>

                <table id="monthly-body" class="table head table-striped">
                    <thead>
                    <tr>
                        <th class="col-sm-1">Date</th>
                        <th class="col-sm-1">Value</th>
                        <th class="col-sm-1">Quantity</th>
                        <th class="col-sm-1">Discount</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

                <div class="col-md-12">
                    <ul class="pager">
                        <li id="monthly-previous" class="previous"><a href="">Previous</a></li>
                        <li id="monthly-count" class="count"></li>
                        <li id="monthly-next" class="next"><a href="">Next</a></li>
                    </ul>
                </div>
            </div>

            <div id="yearly-table" class="display-tab tab-pane fade">
                <div class="col-md-12">
                    <ul class="search-tab nav nav-pills navbar-right">
                        <li class="form-inline">
                            <form id="search-yearly" action="" method="get">
                                <div class="input-group">
                                    <input name="search-yearly" class="form-control" type="text">
                                    <div class="input-group-btn">
                                        <button class="search-btn btn btn-default">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </li>
                    </ul>
                </div>

                <table id="yearly-body" class="table head table-striped">
                    <thead>
                    <tr>
                        <th class="col-sm-1">Date</th>
                        <th class="col-sm-1">Value</th>
                        <th class="col-sm-1">Quantity</th>
                        <th class="col-sm-1">Discount</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

                <div class="col-md-12">
                    <ul class="pager">
                        <li id="yearly-previous" class="previous"><a href="">Previous</a></li>
                        <li id="yearly-count" class="count"></li>
                        <li id="yearly-next" class="next"><a href="">Next</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <script>
        getTransaction("/api/transaction/daily/getall?page=0", 0);
        getTransaction("/api/transaction/weekly/getall?page=0", 0);
        getTransaction("/api/transaction/monthly/getall?page=0", 0);
        getTransaction("/api/transaction/yearly/getall?page=0", 0);

    </script>

</body>
</html>
