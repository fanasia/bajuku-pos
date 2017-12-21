var initiateCart= function () {
    var cart= localStorage.getItem('cart');
    if(cart!==null){
        return cart.split('#');
    }
    return null;
};

var getcategories= function () {
    $.ajax({
        type: 'GET',
        url: '/api/categories/getall',
        contentType: 'application/json',
        success: function (data) {
            $.each(data, function (key, val) {
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

var getcontent= function (page) {
    $.ajax({
        type: 'GET',
        url: '/api/product/getall',
        contentType: 'application/json',
        success: function (data) {
            if(data.length>0){
                $.each(data, function (key, val) {
                    $("#product-container").append(
                        "<div class='product-info col-md-3 col-sm-4' data-id='"+val.id+"'>" +
                        "<a href=''>"+val.fullname+"</a><br>" +
                        "<span class='badge'>"+val.stock+"</span><br>" +
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
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var getSearch= function (url, page) {
    $.ajax({
        type: 'GET',
        url: url+'?page='+page,
        contentType: 'application/json',
        success: function (data) {
            if(data.length>0){
                $.each(data, function (key, val) {
                    $("#product-container").append(
                        "<div class='product-info col-md-3' data-id='"+val.id+"'>" +
                        "<a href=''>"+val.fullname+"</a><br>" +
                        "<span class='badge'>"+val.stock+"</span><br>" +
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
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var getCartById= function () {


};