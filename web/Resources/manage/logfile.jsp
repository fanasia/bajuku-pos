<%--
  Created by IntelliJ IDEA.
  User: Joshua_123
  Date: 27/11/2017
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div class="inner-content container-fluid">
        <div id="log-table" class="display-tab">
            <div class="col-md-12">
                <ul class="search-tab nav nav-pills navbar-right">
                    <li class="form-inline">
                        <form id="search-log" action="" method="get">
                            <div class="input-group">
                                    <input id="search-log-date" name="search-log" class="form-control" type="date">
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

            <table id="log-body" class="table head table-striped table-responsive">
                <thead>
                <tr>
                    <th class="col-sm-1">User</th>
                    <th class="col-sm-1">Action</th>
                    <th class="col-sm-1">Time</th>
                    <th class="col-sm-1">Description</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>

            <div class="col-md-12">
                <ul class="pager">
                    <li id="log-previous" class="previous"><a href="">Previous</a></li>
                    <li id="log-count" class="count"></li>
                    <li id="log-next" class="next"><a href="">Next</a></li>
                </ul>
            </div>
        </div>
    </div>

    <script>
        setTimeout(getdata('/api/log/getall?page=0',0, 10), 5000);

        $("#search-log").change(function () {
            console.log($("#search-log").serialize());
            getdata('/api/log/search?'+$(this).serialize()+"&page=0",0,10);
        });
    </script>
</body>
</html>
