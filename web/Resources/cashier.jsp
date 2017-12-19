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
    <link rel="stylesheet" href="../css/bootstrap-3.3.7/dist/css/bootstrap.min.css">
    <script src="../js/jquery-3.2.1.js"></script>
    <script src="../css/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../css/cashier.css">
    <script src="../js/cashier.js"></script>
    <title>Bajuku-Cashier</title>
</head>
<body>
    <c:import url="header.jsp"/>

    <div class="container-fluid">
        <div class="row">
            <div id="left-col" class="col-md-5 col-xs-7">
                <!-- Left -->
                <div class="col-md-12 form-inline">
                    <span class="symbol glyphicon glyphicon-user"></span>
                    <div class="form-group has-feedback">
                        <div>
                            <input class="form-control" id="search-customer" name="search-customer" type="text">
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        </div>
                    </div>
                </div>
                <div id="customer-info" class="col-md-12">
                    <h4>No member</h4>
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
                                <td class="col-sm-3">PRODUCT1</td>
                                <td class="col-sm-3"><input class="form-control" type="number"></td>
                                <td class="col-sm-4">
                                    <div class="input-group">
                                        <span class="input-group-addon">IDR</span>
                                        <input class="form-control" type="number" value="150000" disabled>
                                    </div>
                                </td>
                                <td class="action-btn col-sm-2"><button class="remove-btn btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div id="cart-info" class="well col-md-12">
                </div>
                <div class="col-md-12">
                    <button class="reset-btn btn btn-block btn-warning">Reset</button>
                    <button class="btn btn-block btn-success" data-toggle="modal" data-target="#transaction-detail">Continue</button>
                </div>
            </div>

            <div id="right-col" class="col-md-7 col-xs-7">
                <!-- Right -->
                <div class="col-md-12 col-sm-12">
                    <ul class="nav">
                        <li class="nav navbar-left">
                            <form class="form-inline" action="" method="get">
                                <div class="input-group">
                                <span class="input-group-addon">
                                    <select name="" id="filter-categories">
                                    </select>
                                </span>
                                    <input id="search-product" name="search-product" class="form-control" type="text" placeholder="search-product">
                                    <div class="input-group-btn">
                                        <button class="search-btn btn btn-default">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </li>
                        <li class="nav navbar-right">
                            <button class="btn btn-default" data-toggle="modal" data-target="#add-member"><span class="glyphicon glyphicon-plus"></span></button>
                            <button class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span></button>
                        </li>
                    </ul>
                </div>

                <div id="product-container" class="well col-md-12">
                    <div class="product-info col-md-3 col-sm-4" data-id="0">
                        <a href="">PRODUCT1</a><br>
                        <span class="badge">5</span><br>
                        <span class="price">1500000</span>
                    </div>
                </div>
                <div class="col-md-12">
                    <ul class="pager">
                        <li class="previous"><a href="">Previous</a></li>
                        <li class="count"></li>
                        <li class="next"><a href="">Next</a></li>
                    </ul>
                </div>
            </div>
        </div>
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

    <div id="transaction-detail" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h3 class="modal-title">Detail Transaction</h3>
                </div>
                <div id="transaction-table" class="modal-body">
                    <table id="transaction-header" class="table">
                        <thead>
                            <tr>
                                <th>Fullname</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Subtotal</th>
                            </tr>
                        </thead>
                    </table>
                    <table id="transaction-body" class="table table-bordered table-responsive">
                        <tbody>
                            <tr>
                                <td>PRODUCT1</td>
                                <td>1000000</td>
                                <td>1</td>
                                <td>1000000</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success">Confirm</button>
                </div>
            </div>
        </div>
    </div>

    <footer class="container-fluid text-center">
        <span>Footer</span>
    </footer>

    <script>
        var cart= initiateCart();
        console.log(cart);

        setTimeout(getcontent(0), 5000);
        setTimeout(getcategories, 1000);

        $(".reset-btn").click(function () {
            if(confirm("Are you sure?")){
                localStorage.clear();
            }
        });
        
        $("#product-container").on('click', '.product-info', function () {
            var id= $(this).data('id');
            var idx;
            if(cart!==null){
                idx= $.inArray(id.toString(), cart);
                console.log(idx);
            }
            if(idx===-1||cart===null){
                var qty= prompt("Insert products quantity: ");
                if(!isNaN(qty)&&qty>0) {
                    $("#cart-body").children("tbody").append(
                        "<tr data-id='" + $(this).data('id') + "'>" +
                        "<td class='col-sm-3'>" + $(this).children("a").html() + "</td>" +
                        "<td class='col-sm-3'><input class='form-control' type='number' value='"+parseInt(qty)+"'></td>" +
                        "<td class='col-sm-4'>" +
                        "<div class='input-group'>" +
                        "<span class='input-group-addon'>IDR</span>\n" +
                        "<input class='form-control' type='number' value='" + parseInt($(this).children(".price").html()) + "' disabled>\n" +
                        "</div>" +
                        "</td>" +
                        "<td class='action-btn col-sm-2'><button class='remove-btn btn btn-danger'><span class='glyphicon glyphicon-trash'></span></button></td>" +
                        "</tr>"
                    );
                    cart.push($(this).data('id').toString());
                    var newitem = '#'+$(this).data('id');
                    localStorage.setItem("cart", localStorage.getItem("cart")+newitem);
                }
                else {
                    alert("Input a number");
                }
            }
            else {
                alert("Product already in the cart!");
            }
        });

        $(document).on('click', '.remove-btn', function () {
            if(confirm("Remove this product from cart?")){
                $(this).parent().parent().remove();
            }
        });
    </script>
</body>
</html>
