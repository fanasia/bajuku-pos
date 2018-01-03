var initiateCart= function () {
    var cart= localStorage.getItem('cart');
    if(cart!==null){
        return cart.split('#');
    }
    return [];
};

var initiateQuantity= function () {
    var qty= localStorage.getItem('cartQty');
    if(qty!==null){
        return qty.split('#');
    }
    return [];
};

//search product in cart by id
var checkCart= function(cart, cartQty){
    if(cart.length>0) {
        $.ajax({
            type: 'GET',
            url: "/api/product/getById",
            data: {id: JSON.stringify(cart)},
            success: function (data) {
                var subtotal=0;
                $.each(data, function (key, val) {
                    $("#cart-body table").children("tbody").append(
                        "<tr data-id='" + val.id + "'>" +
                        "<td class='col-sm-3'>" + val.fullname + "</td>" +
                        "<td class='col-sm-3'><input class='form-control' type='number' value='" + cartQty[key] + "' max='" + val.stock + "'></td>" +
                        "<td class='col-sm-4'>" +
                        "<div class='input-group'>" +
                        "<span class='input-group-addon'>IDR</span>\n" +
                        "<input class='form-control' type='number' value='" + val.price + "' disabled>" +
                        "</div>" +
                        "</td>" +
                        "<td class='action-btn col-sm-2'><button class='remove-btn btn btn-danger'><span class='glyphicon glyphicon-trash'></span></button></td>" +
                        "</tr>"
                    );
                    subtotal+=(parseFloat(val.price)*parseInt(cartQty[key]));
                });
                calculateCart(subtotal);
            },
            error: function (status) {
                console.log(status);
            }
        });
    }
};

var updateLocalStorage= function () {
    //push null
    cart.splice(0, 0, 'null');
    cartQty.splice(0, 0, 'null');

    localStorage.setItem("cart", cart.join('#'));
    localStorage.setItem("cartQty", cartQty.join('#'));

    //remove null
    cart.splice(0, 1);
    cartQty.splice(0, 1);
};

var calculateCart= function (price) {
    var subtotal= parseFloat(price);
    if(subtotal!==0) {
        subtotal += parseFloat($("#subtotal-input").data('value'));
    }
    $("#subtotal-input").html(subtotal.toLocaleString('en-us',
        {style: 'currency', currency: 'IDR', minimumFractionDigits: 2})).
    data('value', subtotal);

    $("#total-input").html(subtotal.toLocaleString('en-us',
        {style: 'currency', currency: 'IDR', minimumFractionDigits: 2})).
    data('value', subtotal);

    //update points
    $("#transaction-total").html($("#total-input").clone());
    $("#transaction-points").html($("#total-input").data('value')*0.001).css({"color":"blue"});
};

var addDiscount= function () {
    var total= $("#subtotal-input").data('value');
    total-=parseFloat($("#discount-value").val()*10);

    $("#total-input").html(total.toLocaleString('en-us',
        {style: 'currency', currency: 'IDR', minimumFractionDigits: 2})).
    data('value', total);

    //update points
    $("#transaction-total").html($("#total-input").clone());
    $("#transaction-points").html($("#total-input").data('value')*0.001).css({"color":"blue"});
};

var getcategories= function () {
    $.ajax({
        type: 'GET',
        url: '/api/categories/getall?limit=0&page=0',
        dataType: 'JSON',
        success: function (data) {
            $.each(data.array, function (key, val) {
                $("#filter-categories").append(
                "<option value='"+val.id+"'>"+val.fullname+"</option>"
                );
            });
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var getcontent= function (url, page) {
    var str_stock;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'JSON',
        success: function (data) {
            $("#product-container").empty();
            if(data.array.length>0){
                $.each(data.array, function (key, val) {
                    (val.stock===0)?
                        str_stock="<span class='stock' style='color:red' data-stock:"+val.stock+">Not in stock</span><br>" :
                        str_stock="<span class='badge stock'>"+val.stock+"</span><br>";

                    $("#product-container").append(
                        "<div class='product-info col-md-3 col-sm-4' data-id='"+val.id+"'>" +
                        "<a onclick='return false;'>"+val.fullname+"</a><br>"+
                        str_stock+
                        "<span class='price'>"+val.price+"</span>" +
                        "</div>"
                    );
                });
            }
            else {
                $("#product-container").append(
                    "<div class='col-md-12 text-center'>No data has been inserted<br></div>"
                )
            }

            $("#cashier-count").html(
                ((12*page)+1)+"-"+
                (++page*12)+" of "+
                data.count
            );

            var prev = page - 1 <= 0 ? 0 : page - 1;
            var next = page + 1 > Math.ceil(data.count / 12) ?  page-1 : page;

            url= url.substr(0, url.indexOf("page"));
            $("#cashier-previous").children("a").attr('href',url+'page='+prev);
            $("#cashier-next").children("a").attr('href',url+'page='+next);
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var getSearchMember= function (url) {
    $.ajax({
        type: "GET",
        url: url+"&page=0",
        contentType: "application/json",
        dataType: "JSON",
        success: function (data) {
            $("#customer-dropdown").empty();
            if(data.array.length>0){
                $.each(data.array, function (key, val) {
                    $("#customer-dropdown").append(
                        "<li class='list-customer list-group-item' data-id='"+val.id+"' data-points='"+val.points+"'>" +
                        "<a onclick='return false'>"+val.fullname+" - "+ val.phone +"</a></li>"
                    );
                });
            }
            else{
                $("#customer-dropdown").empty().append(
                    "<li class='disabled'><a href=''>No membership found..</a></li>"
                );
            }
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var insertTransaction= function (cart, cartQty, customer_id, discount, points) {
    $.ajax({
        type: "POST",
        url: "/api/transaction/insert",
        contentType: "application/x-www-form-urlencoded",
        data: {id: JSON.stringify(cart),
            quantity: JSON.stringify(cartQty),
            customer_id: JSON.stringify(customer_id),
            discount: JSON.stringify(discount),
            points: JSON.stringify(points)
        },
        success: function (status) {
            console.log(status);
            $("#status-submit").html(status);
            $("#cart-body").empty();
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var insertMember= function (url, object) {
    $.ajax({
        type:"POST",
        url: url,
        data: {data: object},
        contentType: "application/x-www-form-urlencoded",
        success: function (status) {
            console.log(status);
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var createPage= function (request) {
    window.open('http://localhost:8080/print/receipt?'+request, '_blank');
};