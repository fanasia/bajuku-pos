<%--
  Created by IntelliJ IDEA.
  User: Joshua_123
  Date: 25/11/2017
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div class="inner-content container-fluid">
        <div id="user-table" class="display-tab">
            <div class="col-md-12">
                <ul class="search-tab nav nav-pills navbar-right">
                    <li class="form-inline">
                        <div class="input-group">
                            <input id="search-user" name="search-user" class="form-control" type="text" placeholder="search-user">
                            <div class="input-group-btn">
                                <button class="search-btn btn btn-default">
                                    <i class="glyphicon glyphicon-search"></i>
                                </button>
                            </div>
                        </div>
                    </li>
                    <li><a href="" data-toggle="modal" data-target="#add-product"><span class="glyphicon glyphicon-plus"></span> Add user</a></li>
                </ul>
            </div>

            <table class="table head">
                <thead>
                <tr>
                    <th>User name</th>
                    <th>Username</th>
                    <th>Last login</th>
                    <th>Role</th>
                    <th>Action</th>
                </tr>
                </thead>
            </table>
            <table class="table table-striped">
                <tbody class="user-body">
                </tbody>
            </table>
        </div>
    </div>
    <script>
        setTimeout(getdata("/api/user/getall"), 5000);
    </script>

</body>
</html>
