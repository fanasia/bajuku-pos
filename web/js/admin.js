var getSearch= function (url, page) {
    var str= url.split("/");

    $.ajax({
        type: "GET",
        url: url,
        dataType: "JSON",
        success: function (data) {
            console.log(data);
            if(data.array.length>0) {
                if (str[2] === 'user') {
                    $("#user-body").children("tbody").empty();
                    $.each(data.array, function(key, val){
                        $("#user-body").children("tbody").append(
                            "<tr data-id='" + val.id + "'>" +
                            "<td class='col-sm-3'>" + val.fullname + "</td>" +
                            "<td class='col-sm-2'>" + val.username + "</td>" +
                            "<td class='col-sm-2 non-editable'>" + new Date(val.log_time).toLocaleString() + "</td>" +
                            "<td class='col-sm-2 selection'>" + val.user_role + "</td>" +
                            "<td class='col-sm-3 action'>" +
                            "<button class='edit-btn btn btn-default'><i class='glyphicon glyphicon-pencil'></i></button>" +
                            "<button class='delete-btn btn btn-danger'><i class='glyphicon glyphicon-trash'></i></button>" +
                            "<button class='password-btn btn btn-default'><i class='glyphicon glyphicon-lock'></i></button>" +
                            "</td>" +
                            "</tr>"
                        );
                    });
                }
                else if (str[2] === 'product') {
                    $("#product-body").children("tbody").empty();
                    $.each(data.array,function (key, val) {
                        $("#product-body").children("tbody").append(
                            "<tr data-id='"+val.id+"'>" +
                            "<td class='col-sm-3'>"+val.fullname+"</td>" +
                            "<td class='col-sm-2 selection' data-cat='"+val.category_id+"'></td>" +
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
                else if (str[2] === 'categories') {
                    $("#categories-body").children("tbody").empty();
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
                else if (str[2] === 'customer') {

                }
                else if (str[2] === 'log') {
                    $("#log-body").children("tbody").empty();
                    $.each(data.array, function (key, val) {
                        $("#log-body").children("tbody").append(
                            "<tr data-id='" + val.id + "'>" +
                            "<td class='col-sm-1'>" + val.action + "</td>" +
                            "<td class='col-sm-2'>" + new Date(val.time).toLocaleString() + "</td>" +
                            "<td class='col-sm-2'>" + val.desc + "</td>" +
                            "</tr>"
                        );
                    });
                }
            }
            else{
                $("#"+str[2]+"-body").children("tbody").empty().
                append(
                    "<tr>" +
                    "<td colspan='5'>No data found</td>" +
                    "</tr>"
                );
            }
            $("#"+str[2]+"-count").html(
                ((10*page)+1)+"-"+
                ((page+1)*10)+" of "+
                data.count);

            var prev = page - 1 <= 0 ? 0 : page - 1;
            var next = page + 1 > Math.ceil(data.count / 10) ?  page : page + 1;

            url= url.substr(0, url.indexOf("page"));
            console.log(url);

            $("#"+str[2]+"-previous").children("a").attr('href',url+'page='+prev);
            $("#"+str[2]+"-next").children("a").attr('href',url+'page='+next);
        },
        error: function (status) {
            console.log(status.responseText);
        }
    });
};

var changePassword= function (url) {
    $.ajax({
        type: 'POST',
        url: url,
        success: function () {

        },
        error: function () {

        }
    });
};

var getcategories= function () {
    $.ajax({
        type: 'GET',
        url: '/api/categories/getall',
        contentType: 'application/json',
        success: function (data) {
            $.each(data, function (key, val) {
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

var getdata= function (url, page) {
    var str= url.split("/");
    $.ajax({
        type:"GET",
        url: url,
        contentType: "application/json",
        success: function (data) {
           if(data.length>0){
               if(str[2]==='user') {
                   $.each(data,function (key, val) {
                       $("#user-body").children("tbody").append(
                           "<tr data-id='" + val.id + "'>" +
                           "<td class='col-sm-3'>" + val.fullname + "</td>" +
                           "<td class='col-sm-2'>" + val.username + "</td>" +
                           "<td class='col-sm-2 non-editable'>" + new Date(val.log_time).toLocaleString() + "</td>" +
                           "<td class='col-sm-2 selection'>" + val.user_role + "</td>" +
                           "<td class='col-sm-3 action'>" +
                           "<button class='edit-btn btn btn-default'><i class='glyphicon glyphicon-pencil'></i></button>" +
                           "<button class='delete-btn btn btn-danger'><i class='glyphicon glyphicon-trash'></i></button>" +
                           "<button class='password-btn btn btn-default'><i class='glyphicon glyphicon-lock'></i></button>" +
                           "</td>" +
                           "</tr>"
                       );
                   });
               }
               else if(str[2]==='product'){
                   $.each(data,function (key, val) {
                   $("#product-body").children("tbody").append(
                       "<tr data-id='"+val.id+"'>" +
                       "<td class='col-sm-3'>"+val.fullname+"</td>" +
                       "<td class='col-sm-2 selection' data-cat='"+val.category_id+"'></td>" +
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
                   $.each(data,function (key, val) {
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
                   $.each(data,function (key, val) {
                       $("#customer-body").children("tbody").append();
                   });
               }
               else if(str[2]==='log'){
                   $.each(data,function (key, val) {
                       $("#log-body").children("tbody").append(
                           "<tr data-id='" + val.id + "'>" +
                           "<td class='col-sm-1'>" + val.action + "</td>" +
                           "<td class='col-sm-2'>" + new Date(val.time).toLocaleString() + "</td>" +
                           "<td class='col-sm-2'>" + val.desc + "</td>" +
                           "</tr>"
                       );
                   });
               }
           }
           else{
               $("#"+str[2]+"-body").children("tbody").append(
                   "<tr>"+
                   "<td colspan='5'>No data found</td>" +
                   "</tr>"
               );
           }
        },
        error: function (status) {
           console.log(status);
        }
    });
};

var insertdata= function (url, object) {
    console.log("insert data "+ object);
    $.ajax({
        type:"POST",
        url: url,
        dataType: "json",
        data: {data: object, csrftoken: $(".csrftoken").val()},
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

} ;

var deletedata= function (url){
    $.ajax({
        type: "DELETE",
        url: url,
        dataType: "json",
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
        type: "PUT",
        url: url,
        dataType: "json",
        data: object,
        success: function (status) {
            console.log(status);
        },
        error: function (status) {
            console.log(status);
        }
    });

};
