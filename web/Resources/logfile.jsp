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
                        <div class="input-group">
                            <input id="search-log" name="search-log" class="form-control" type="date">
                            <div class="input-group-btn">
                                <button class="search-btn btn btn-default">
                                    <i class="glyphicon glyphicon-search"></i>
                                </button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>

            <table class="table head">
                <thead>
                <tr>
                    <th>Action</th>
                    <th>Time</th>
                    <th>Description</th>
                </tr>
                </thead>
            </table>
            <table class="table table-stripe">
                <tbody class="log-body">
                </tbody>
            </table>
        </div>
    </div>

</body>
</html>
