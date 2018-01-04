var getdata= function (url, page, limit) {
    var str= url.split("/");
    $.ajax({
        type:"GET",
        url: url,
        dataType: "JSON",
        success: function (data) {
            $("#"+str[2]+"-body").children("tbody").empty();
            if(data.array.length>0){
                if(str[2]==='user') {
                    $.each(data.array,function (key, val) {
                        $("#user-body").children('tbody').append(
                            "<tr data-id='" + val.id + "'>" +
                            "<td class='col-sm-3'>" + val.fullname + "</td>" +
                            "<td class='col-sm-2'>" + val.username + "</td>" +
                            "<td class='col-sm-2 non-editable'>" + new Date(val.log_time).toLocaleString() + "</td>" +
                            "<td class='col-sm-2 selection'>" + val.user_role + "</td>" +
                            "<td class='col-sm-3 action'>" +
                            "<button class='edit-btn btn btn-default'><i class='glyphicon glyphicon-pencil'></i></button>" +
                            "<button class='delete-btn btn btn-danger'><i class='glyphicon glyphicon-trash'></i></button>" +
                            "<button class='password-btn btn btn-default' data-toggle='modal' data-target='#change-password'><i class='glyphicon glyphicon-lock'></i></button>" +
                            "</td>" +
                            "</tr>"
                        );
                    });
                }
                else if(str[2]==='product'){
                    $.each(data.array,function (key, val) {
                        $("#product-body").children("tbody").append(
                            "<tr data-id='"+val.id+"'>" +
                            "<td class='col-sm-3'>"+val.fullname+"</td>" +
                            "<td class='col-sm-2 selection' data-cat='"+val.category_id+"'>"+val.category_name+"</td>" +
                            "<td class='col-sm-2'>"+val.price+"</td>" +
                            "<td class='col-sm-1'>"+val.stock+"</td>" +
                            "<td class='col-sm-1'>"+val.mapping+"</td>" +
                            "<td class='col-sm-2 action'>" +
                            "<button class='edit-btn btn btn-default'><i class='glyphicon glyphicon-pencil'></i></button>" +
                            "<button class='delete-btn btn btn-danger'><i class='glyphicon glyphicon-trash'></i></button>" +
                            "</td>" +
                            "</tr>"
                        );
                    });
                }
                else if(str[2]==='categories'){
                    $.each(data.array,function (key, val) {
                        $("#categories-body").children("tbody").append(
                            "<tr data-id='" + val.id + "'>" +
                            "<td class='col-sm-1'>" + val.fullname + "</td>" +
                            "<td class='col-sm-1 action'>" +
                            "<button class='edit-btn btn btn-default'><i class='glyphicon glyphicon-pencil'></i></button>" +
                            "<button class='delete-btn btn btn-danger'><i class='glyphicon glyphicon-trash'></i></button>" +
                            "</td>" +
                            "</tr>"
                        );
                    });
                }
                else if(str[2]==='customer'){
                    $.each(data.array, function(key, val){
                        $("#customer-body").children("tbody").append(
                            "<tr data-id='" + val.id + "'>" +
                            "<td class='col-sm-3'>" + val.fullname + "</td>" +
                            "<td class='col-sm-2'>" + val.email + "</td>" +
                            "<td class='col-sm-2'>" + val.phone + "</td>" +
                            "<td class='col-sm-2 numeric'>" + val.points + "</td>" +
                            "<td class='col-sm-3 action'>" +
                            "<button class='edit-btn btn btn-default'><i class='glyphicon glyphicon-pencil'></i></button>" +
                            "<button class='delete-btn btn btn-danger'><i class='glyphicon glyphicon-trash'></i></button>" +
                            "</td>" +
                            "</tr>"
                        );
                    });
                }
                else if(str[2]==='log'){
                    $.each(data.array,function (key, val) {
                        $("#log-body").children("tbody").append(
                            "<tr>" +
                            "<td class='col-sm-1'>" + val.id + "</td>" +
                            "<td class='col-sm-1'>" + val.action + "</td>" +
                            "<td class='col-sm-2'>" + new Date(val.time).toLocaleString() + "</td>" +
                            "<td class='col-sm-2'>" + val.desc + "</td>" +
                            "</tr>"
                        );
                    });
                }
            }
            else{
                $("#"+str[2]+"-body").children("tbody").empty().append(
                    "<tr>"+
                    "<td colspan='6'>No data found</td>" +
                    "</tr>"
                );
            }
            $("#"+str[2]+"-count").html(
                ((limit*page)+1)+"-"+
                (++page*limit)+" of "+
                data.count
            );
            var prev = page - 1 <= 0 ? 0 : page - 2;
            var next = page + 1 > Math.ceil(data.count / limit) ?  page-1 : page;

            url= url.substr(0, url.indexOf("page"));
            $("#"+str[2]+"-previous").children("a").attr('href',url+'page='+prev);
            $("#"+str[2]+"-next").children("a").attr('href',url+'page='+next);
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var getcategories= function () {
    $.ajax({
        type: 'GET',
        url: '/api/categories/getall?limit=0&page=0',
        dataType: "JSON",
        success: function (data) {
            $(".select-categories").empty();
            $.each(data.array, function (key, val) {
                $(".select-categories").append(
                    "<option value='"+ val.id +"'>"+ val.fullname +"</option>"
                );
            });
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var changePassword= function (url, obj) {
    $.ajax({
        type: 'PUT',
        url: url,
        data: JSON.stringify(obj),
        contentType: "application/json",
        success: function (status) {
            console.log(status);
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var insertdata= function (url, object) {
    $.ajax({
        type:"POST",
        url: url,
        data: {data: object, csrftoken: $(".csrftoken").val()},
        contentType: "application/x-www-form-urlencoded",
        success: function (status) {
            console.log(status);
            //reload table
            $("#"+url.split('/')[2]+"-body").children("tbody").empty();
            getdata("/api/"+url.split('/')[2]+"/getall", 0, (url.split('/')[2]==='product')?7:10);
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var deletedata= function (url, id){
    console.log("delete data "+ id);
    $.ajax({
        type: "DELETE",
        url: url,
        contentType: "application/json",
        success: function (status) {
            console.log(status);
            //reload table
            $("#"+url.split('/')[2]+"-body").children("tbody").empty();
            getdata("/api/"+url.split('/')[2]+"/getall",0);
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var updatedata= function (url, object) {
    $.ajax({
        type:"PUT",
        url: url,
        data: object,
        success: function (status) {
            console.log(status);
            (url.split('/')[2]==="product")?
                getdata("/api/product/getall?limit=7&page=0", 0, 10):
                getdata("/api/"+url.split('/')[2]+"/getall?page=0", 0, 10);
        },
        error: function (status) {
            console.log(status);
        }
    });
};

var getTransaction= function (url, page) {
    var str= url.split('/');
    $.ajax({
        type: "GET",
        url: url,
        dataType: "JSON",
        success: function (data) {
            if(data.array.length>0){
                $("#" + str[3] + "-body").children("tbody").empty();
                if(str[3]==='daily'){
                    $.each(data.array, function (key, val) {
                        $("#" + str[3] + "-body").children("tbody").append(
                            "<tr>" +
                            "<td class='col-sm-1' data-time='"+val.time+"'>" + new Date(val.time).toLocaleString() + "</td>" +
                            "<td class='col-sm-1'>" + val.value.toLocaleString() + "</td>" +
                            "<td class='col-sm-1'>" + val.quantity + "</td>" +
                            "<td class='col-sm-1' style='color:red'>" + val.discount + "</td>" +
                            "<td class='col-sm-1 view'><a class='link-more' href=''>View More</a></td>" +
                            "</tr>"
                        );
                    });
                }
                else {
                    $.each(data.array, function (key, val) {
                        $("#" + str[3] + "-body").children("tbody").append(
                            "<tr>" +
                            "<td class='col-sm-1'>" + new Date(val.time).toLocaleString() + "</td>" +
                            "<td class='col-sm-1'>" + val.value.toLocaleString() + "</td>" +
                            "<td class='col-sm-1'>" + val.quantity + "</td>" +
                            "<td class='col-sm-1' style='color: red'>" + val.discount + "</td>" +
                            "</tr>"
                        );
                        //create chart
                        if(str[3]==='yearly'){
                            var years=['2014','2015','2016','2017'], value= [0,0,0,0];
                            years.push(new Date(val.time).getFullYear());
                            value.push(val.value);
                            new Chart(document.getElementById('yearly-chart').getContext('2d'), {
                                type: 'line',
                                data: {
                                    labels: years,
                                    datasets: [{
                                        label: '# of Transactions',
                                        data: value,
                                        backgroundColor: 'rgb(255, 99, 132)',
                                        borderColor: 'rgb(255, 99, 132)'
                                    }]
                                }
                            });
                        }
                    });
                }
            }
            else{
                $("#"+str[3]+"-body").children("tbody").empty().append(
                    "<tr><td colspan='5'>No data found</td></tr>"
                )
            }
            $("#"+str[3]+"-count").html(
                ((7*page)+1)+"-"+
                (++page*7)+" of "+
                data.count
            );
            var prev = page - 1 <= 0 ? 0 : page - 2;
            var next = page + 1 > Math.ceil(data.count / 7) ?  page-2 : page;

            url= url.substr(0, url.indexOf("page"));
            $("#"+str[2]+"-previous").children("a").attr('href',url+'page='+prev);
            $("#"+str[2]+"-next").children("a").attr('href',url+'page='+next);
        },
        error: function (status) {
            console.log(status);
        }
    })
};

var getDailyTransaction= function (date) {
    $.ajax({
        type: "GET",
        url: '/api/transaction/daily/gettransaction?view-date='+date,
        dataType: 'JSON',
        success: function (data, status) {
            $.each(data.array, function (key, val) {
                $("#daily-body .active").after(
                    "<tr class='daily-transaction' style='background-color:#CCFFFF' data-id='"+val.id+"'>" +
                    "<td class='col-sm-1' data-time='"+val.time+"'>" + new Date(val.time).toLocaleString() + "</td>" +
                    "<td class='col-sm-1'>" + val.value.toLocaleString() + "</td>" +
                    "<td class='col-sm-1'>" + val.quantity + "</td>" +
                    "<td class='col-sm-1' style='color:red'>" + val.discount + "</td>" +
                    "<td class='col-sm-1'><a class='link-details' href='' style='color:#0D3349' data-toggle='modal' data-target='#details'>View details</a></td>" +
                    "</tr>"
                )
            });
            $(".active").children(".view").html("<a class='close-link-more' href=''>Close more</a>");
        },
        error: function (status) {
            console.log(status);
        }

    })
};

var getDetails= function (url) {
    $.ajax({
        type:"GET",
        url: url,
        dataType: "JSON",
        success: function (data) {
            console.log(data);
            $("#details-table").children('tbody').empty()
            $.each(data.array, function (key, val) {
                $("#details-table").children('tbody').append(
                    "<tr>" +
                    "<td class='col-sm-1'>Customer id: "+val.customer_id+"</td>" +
                    "<td data-id="+val.product_id+" class='col-sm-1'>"+val.product_fullname+"</td>" +
                    "<td class='col-sm-1'>"+val.quantity+"x</td>" +
                    "<td class='col-sm-1'>"+val.price +"</td>" +
                    "</tr>"
                );
            })
        },
        error: function (status) {
            
        }
    });
};