var getdata= function (url, page) {
    var str= url.split("/");
    console.log(str[2]);

    $.ajax({
        type:"GET",
        url: url,
        contentType: "application/json",
        success: function (data) {
           if(data.length>0){
               $.each(data,function (key, val) {
                   if(str[2]==='user') {
                       $(".user-body").append(
                           "<tr data-id='"+val.id+"'>" +
                           "<td>" + val.fullname + "</td>" +
                           "<td>" + val.username + "</td>" +
                           "<td>" + new Date(val.log_time).toLocaleString() +"</td>" +
                           "<td>" + val.user_role + "</td>" +
                           "<td>" +
                           "<button class='edit-btn btn btn-default'><i class='glyphicon glyphicon-pencil'></i></button>" +
                           "<button class='delete-btn btn btn-danger'><i class='glyphicon glyphicon-trash'></i></button>" +
                           "</td>" +
                           "</tr>"
                       );
                   }
                   else if(str[2]==='product'){
                       $(".product-body").append(
                           "<tr data-id='"+val.id+"'>" +
                           "<td>"+val.fullname+"</td>" +
                           "<td>"+val.price+"</td>" +
                           "<td>"+val.stock+"</td>" +
                           "<td>"+val.mapping+"</td>" +
                           "<td>" +
                           "<button class='edit-btn btn btn-default'><i class='glyphicon glyphicon-pencil'></i></button>" +
                           "<button class='delete-btn btn btn-danger'><i class='glyphicon glyphicon-trash'></i></button>" +
                           "</td>" +
                           "</tr>"
                       );
                   }
                   else if(str[2]==='categories'){
                       $(".categories-body").append(
                           "<tr data-id='"+val.id+"'>" +
                           "<td>"+ val.fullname +"</td>" +
                           "<td>" +
                           "<button class='edit-btn btn btn-default'><i class='glyphicon glyphicon-pencil'></i></button>" +
                           "<button class='delete-btn btn btn-danger'><i class='glyphicon glyphicon-trash'></i></button>" +
                           "</td>" +
                           "</tr>"
                       );
                   }
                   else if(str[2]==='customer'){
                       $(".customer-body").append();
                   }
                   else if(str[2]==='logfile'){
                       $(".logfile-body").append(
                           "<tr data-id='"+val.id+"'>" +
                           "<td>"+ val.action +"</td>" +
                           "<td>"+ val.alter_time +"</td>" +
                           "<td>"+ val.alter_description +"</td>" +
                           "</tr>"
                       );
                   }
               });
           }
           else{
               $("."+str[2]+"-body").append(
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
        },
        error: function (status) {
            console.log(status);
        }
    });

} ;

var deletedata= function (url, id){
    $.ajax({
        type: "DELETE",
        url: url+"?id="+id,
        dataType: "json",
        contentType: "application/json",
        success: function (status) {
            console.log(status);
        },
        error: function (status) {
            console.log(status);
        }
    });

};

var updatedata= function (url, object) {

};

$("#product-submit").click(function (e) {
   var data={};
   var url= "/api/product/insert";
   data.id= null;
   data.fullname= $("#input-name").val();
   data.category_id= $("#input-categories").val();
   data.price= parseFloat($("#input-price").val());
   data.stock= parseInt($("#input-stock").val());
   data.mapping= $("#input-mapping").val();

   console.log(data);
});

$("#categories-submit").click(function (e) {
    var data={};
    var url= "api/categories/insert";
    data.id= null;
    data.fullname= $("#input-cat-name").val();

    console.log(data);
});

$("#user-submit").click(function (e) {

});

$("li").click(function () {
    var page=0;//reset page counter
    window.location.hash= $(this)[0].firstChild.hash;
});
