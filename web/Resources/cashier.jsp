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
            <div id="left-col" class="col-md-5 col-sm-5 col-xs-12">
                <!-- Left -->
                <div class="col-md-12 dropdown">
                    <form id="search-customer" class="form-inline">
                        <span class="symbol glyphicon glyphicon-user"></span>
                        <div class="form-group has-feedback">
                                <input class="form-control" name="search-customer" type="text">
                                <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        </div>
                        <ul id="customer-dropdown" class="list-group dropdown-menu">
                            <%--template members--%>
                        </ul>
                    </form>
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
                            <tbody id="cart-inner-body">
                            <%--template items--%>
                            </tbody>
                        </table>
                    </div>
                    <div id="cart-info" class="well col-md-12">
                        <table class="table table-bordered">
                            <tr>
                                <td class="col-md-4">Subtotal</td>
                                <td id="subtotal-input" data-value="0"></td>
                            </tr>
                            <tr>
                                <td class="col-md-4">Discount</td>
                                <td id="discount-input"></td>
                            </tr>
                            <tr>
                                <td class="col-md-4">Total</td>
                                <td id="total-input"></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

            <div id="right-col" class="col-md-7 col-sm-7 col-xs-12">
                <!-- Right -->
                <div class="col-md-12 col-sm-12">
                    <ul class="nav">
                        <li class="nav navbar-left">
                            <form id="search-product" class="form-inline" action="" method="get">
                                <div class="input-group">
                                <span class="input-group-addon">
                                    <select id="filter-categories" name="select-categories">
                                    </select>
                                </span>
                                    <input name="search-product" class="form-control" type="text" placeholder="search-product">
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

                <div id="product-container" class="well col-md-12 col-sm-12 col-xs-12">
                    <%--template product--%>
                </div>
                <div class="col-md-12">
                    <ul class="pager">
                        <li id="cashier-previous" class="previous"><a href="">Previous</a></li>
                        <li id="cashier-count" class="count"></li>
                        <li id="cashier-next" class="next"><a href="">Next</a></li>
                    </ul>
                </div>
                <div class="col-md-12">
                    <button class="reset-btn btn btn-block btn-warning">Reset</button>
                    <button class="continue-btn btn btn-block btn-success" data-toggle="modal" data-target="#transaction-detail">Continue</button>
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
                <div class="modal-body">
                    <div id="transaction-info" class="form-center form-horizontal">
                        <div class="form-group">
                            <label class="control-label col-md-2 col-md-offset-2">Total: </label>
                            <div class="col-md-4">
                                <span id="transaction-total"></span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-md-2 col-md-offset-2" for="payment-input">Payment: </label>
                            <div class="col-md-4">
                                <input id="payment-input" class="form-control" type="text" >
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="transaction-submit" class="btn btn-success">Confirm</button>
                </div>
            </div>
        </div>
    </div>

    <footer class="container-fluid text-center">
        <span>&copy;2017 Bajuku-POS</span>
    </footer>

    <script>
        var cart= initiateCart();
        var cartQty= initiateQuantity();
        calculateCart(0);
        //remove null from array
        cart.splice(0,1);
        cartQty.splice(0,1);

        setTimeout(getcontent('/api/product/getall?limit=12&page=0',0), 5000);
        getcategories();
        checkCart(cart, cartQty);

        $(".reset-btn").click(function () {
            if(confirm("Are you sure?")){
                localStorage.clear();
                calculateCart(0);
                $("#cart-body").empty();
            }
        });
        
        $("#product-container").on('click', '.product-info', function () {
            var id= $(this).data('id');
            if($(this).children(".stock").html()==="Not in stock"){
                alert("Product not in stock!");
                return;
            }
            else {
                if (cart.indexOf(id.toString()) === -1 || cart === null) {
                    var qty = prompt("Insert products quantity: ");

                    if (qty.trim().length === 0 || qty.length === 0) {
                        alert("Quantity can\'t be empty");
                        return;
                    }
                    else if (isNaN(qty)) {
                        alert("Input a number!");
                    }
                    else if (parseInt(qty) > parseInt($(this).children('.stock').html())) {
                        alert("Quantity input can\'t exceed product in stock");
                    }
                    else {
                        $("#cart-body table").children("tbody").append(
                            "<tr data-id='" + $(this).data('id') + "'>" +
                            "<td class='col-sm-3'>" + $(this).children("a").html() + "</td>" +
                            "<td class='col-sm-3'><input class='form-control' type='number' value='" + parseInt(qty) + "' max='" + $(this).children(".stock").html() + "'></td>" +
                            "<td class='col-sm-4'>" +
                            "<div class='input-group'>" +
                            "<span class='input-group-addon'>IDR</span>\n" +
                            "<input class='form-control' type='number' value='" + parseInt($(this).children(".price").html()) + "' disabled>" +
                            "</div>" +
                            "</td>" +
                            "<td class='action-btn col-sm-2'><button class='remove-btn btn btn-danger'><span class='glyphicon glyphicon-trash'></span></button></td>" +
                            "</tr>"
                        );

                        //update cart-id
                        cart.push($(this).data('id').toString());
                        var newitem = '#' + $(this).data('id');
                        localStorage.setItem("cart", localStorage.getItem("cart") + newitem);

                        //update cart-quantity
                        cartQty.push(qty);
                        var newQty = '#' + qty;
                        localStorage.setItem("cartQty", localStorage.getItem("cartQty") + newQty);

                        //update subtotal
                        var price = parseFloat($(this).children('.price').html()) * parseInt(qty);
                        calculateCart(price);
                    }
                }
                else {
                    alert("Product already in the cart!");
                }
            }
        });

        $("#cart-inner-body").on('click', '.remove-btn', function () {
            if(confirm('Remove this product from cart?')){
                var id= $(this).parent().parent().data('id');
                var decrease= $(this).parent().siblings().eq(1).children().eq(0).val();
                var price= $(this).parent().siblings().eq(2).children().eq(0).children().eq(1).val();

                $(this).parent().parent().remove();

                //update localStorage
                cart.splice(cart.indexOf(id),1);
                cartQty.splice(cartQty.indexOf(id),1);
                console.log(cart+" "+cartQty);

                calculateCart(-(decrease*price));
            }
        });

        $("input[name=search-customer]").keyup(function () {
            if($(this).val().length>0){
                console.log(($("#search-customer").serialize()));
                getSearchMember("/api/customer/getSearch?"+$("#search-customer").serialize());
                $("#customer-dropdown").slideDown(500);
            }
            else
                $("#customer-dropdown").slideUp(500);
        });

        $("#customer-dropdown").on('click','.list-customer', function (e) {
            e.preventDefault();
            $("#customer-info").html(
                "<h5 style='float:left; margin-right: 20px'>"+$(this).html()+" </h5>" +
                "<h5>points: "+$(this).data('points')+"</h5>"
            );
            $("#discount-input").html(
                "<input type='number' value='"+$(this).data('points')+"' max="+parseInt($(this).data('points'))+"></input>" +
                "<span style='margin-left: 20px; color:red'>- "+$(this).data('points')+"</span>"
            );
            $("#customer-dropdown").slideUp(500);
        });

        $("#transaction-submit").click(function () {
            if(cart.length===0){
                alert('You haven\'t pick a product!');
                return;
            }
            insertTransaction(cart, cartQty, null);
        });

        //paging previous-next
        $(".previous, .next").click(function (e) {
            e.preventDefault();
            var link= $(this).children('a').attr('href');
            getContent(link, link.substring(link.length, link.lastIndexOf('=')+1));
        });

    </script>
</body>
</html>