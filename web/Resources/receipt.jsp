<%--
  Created by IntelliJ IDEA.
  User: Joshua_123
  Date: 03/01/2018
  Time: 18:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <script src="../js/jquery-3.2.1.js"></script>
        <script>
            $(document).ready(function(){findProductById();localStorage.clear();});

            function findProductById() {
                //initiate cart
                var cart= localStorage.getItem('cart').split('#');
                var cartQty= localStorage.getItem('cartQty').split('#');
                cart.splice(0,1);
                cartQty.splice(0,1);

                $.ajax({
                    type: 'GET',
                    url: "/api/product/getById",
                    data: {id: JSON.stringify(cart)},
                    success: function (data) {
                        var subtotal=0;
                        $.each(data, function (key, val) {
                            $('tbody').eq(0).append(
                                "<tr data-id='" + val.id + "'>" +
                                "<td>" + val.fullname + "</td></tr>" +
                                "<tr>" +
                                "<td>" + cartQty[key] + "x</td>" +
                                "<td>" + val.price + "</td>" +
                                "<td>" + val.price * cartQty[key]+"</td>" +
                                "</tr>"
                            );
                            subtotal+=(parseFloat(val.price)*parseInt(cartQty[key]));
                        });
                        $("#subtotal").html(subtotal.toLocaleString());
                        var total= subtotal-parseFloat($("#discount").html());
                        $("#total").html(total.toLocaleString());
                        $("#change").html((parseFloat($("#payment").html())-total).toLocaleString());
                        $("#points").html(total*0.001);
                    },
                    error: function (status) {
                        console.log(status);
                    }
                });
            }

            function printReceipt() {
                document.getElementsByTagName('span')[1].innerHTML= Date();
                document.getElementsByTagName('button')[0].remove();
                var printWindow = window.open('','', 'height=400, width=800');
                printWindow.document.write(document.getElementsByTagName('body')[0].outerHTML);
                printWindow.focus();
                printWindow.print();
            }
        </script>
    </head>
    <body style="font-family: monospace, monospace; margin: 20px 50px ">
        <h3 style="text-align: center">Bajuku pos Receipt</h3>
        <p style="text-align: center">Point of sales system for your Clothing Store</p>
        <hr style="border-style: dashed">

        <p><b>Cashier: </b><span><!--cashier-name--><c:out value="${user}"/> [<b>id: </b> <c:out value="${id}"/>]</span></p>
        <p><b>Date: </b><span><!--transaction-date--></span></p>
        <p><b>Member: </b><span><!--member-name--><c:out value="${customer}"/></span></p>

        <hr style="border-style: dashed">
        <table style="width: 40%" id="list-product">
            <tbody>
            <!--product template-->
            </tbody>
        </table>
        <hr style="border-style: dashed;">

        <table style="width: 30%">
            <tr>
                <td><b>Subtotal </b></td><td>:</td><td><span id="subtotal"></span></td></tr>
            <tr>
                <td><b>Discount </b></td><td>:</td><td><span id="discount"><c:out value="${discount}"/></span></td></tr>
            <tr>
                <td><b>Payment </b></td><td>:</td><td><span id="payment"><c:out value="${payment}"/></span></td></tr>
            <tr>
                <td><b>Total </b></td><td>:</td><td><span id="total"></span></td></tr>
            <tr>
                <td colspan="4"><hr style="border-style: dashed"></td>
            </tr>
            <tr><td><b>Change </b></td><td>:</td><td><span id="change"></span></td></tr>
            <tr><td><b>Points </b></td><td>:</td><td><span id="points"></span></td></tr>
        </table>

        <div style="text-align: center">
            <h4>Thank you for purchasing</h4>
            <p>Store: Central Park, Jakarta<br>
                Telp: 021-7544123<br>
                Note: Purchase product can be replaced under a week</p>
        </div>
    <footer style="text-align: center"><button onclick="printReceipt();">Print</button></footer>
    </body>
</html>