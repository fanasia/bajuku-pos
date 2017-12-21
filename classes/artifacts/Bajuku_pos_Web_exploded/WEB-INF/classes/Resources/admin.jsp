<%--
  Created by IntelliJ IDEA.
  User: Joshua_123
  Date: 29/10/2017
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap-3.3.7/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/admin.css">
    <script src="../js/jquery-3.2.1.js"></script>
    <script src="../css/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
    <script src="../js/admin.js"></script>
    <title>Bajuku-Admin</title>
</head>
<body>
    <c:import url="header.jsp"/>

    <div class="content container-fluid">
        <div class="row">
            <div class="sidebar col-md-3">
                <ul class="nav nav-stacked nav-pills" id="admin-tab">
                    <li id="profile-tab" class="navbar-header">
                        <h3>Profile</h3>
                        <span>ID ${id}: ${user}</span>
                    </li>
                    <li class="active"><a data-toggle="pill" href="#ledger-tab"><span class="icon glyphicon glyphicon-book"></span> Ledger</a></li>
                    <li><a data-toggle="pill" href="#user-tab"><span class="icon glyphicon glyphicon-user"></span> User</a></li>
                    <li><a data-toggle="pill" href="#customer-tab"><span class="icon glyphicon glyphicon-user"></span> Customer</a></li>
                    <li><a data-toggle="pill" href="#product-tab"><span class="icon glyphicon glyphicon-gift"></span> Product</a></li>
                    <li><a data-toggle="pill" href="#log-tab"><span class="icon glyphicon glyphicon-th-list"></span> Log-file</a></li>
                </ul>
            </div>
            <div class="tab-content col-md-9">
                <div id="ledger-tab" class="tab-pane fade in active">
                    <c:import url="manage/ledger.jsp"/>
                </div>
                <div id="user-tab" class="tab-pane fade">
                    <c:import url="manage/user.jsp"/>
                </div>
                <div id="customer-tab" class="tab-pane fade">
                    <c:import url="manage/customer.jsp"/>
                </div>
                <div id="product-tab" class="tab-pane fade">
                    <c:import url="manage/product.jsp"/>
                </div>
                <div id="log-tab" class="tab-pane fade">
                    <c:import url="manage/logfile.jsp"/>
                </div>
             </div>
        </div>
    </div>

    <footer class="container-fluid text-center">
        <span>Created by Joshua Ivan Andjaya & Fayya Nadhira Anyatasia</span>
    </footer>

    <script>
        //refresh on selected tabs
        if(window.location.hash!==null) {
            $("a[href='"+window.location.hash+"']").tab("show");
        }
        $("#admin-tab>li").click(function () {
            window.location.hash= $(this)[0].firstChild.hash;
        });

        //edit-btn action
        $("tbody").on("click", ".edit-btn", function () {
            var tr= $(this).parent().parent();

            tr.addClass("hidden");
            var form= "<tr class='form-inline edit-form' data-id='"+tr.data('id')+"'>";
            var i = 1;
            $.each(tr.children(), function (key, value) {
                //checks className
                var cls= value.className.split(' ');
                if(cls[1]==='selection'){
                    form+="<td id='edit-selection' class='"+cls[0]+"'></td>";
                }
                else if(cls[1]==='non-editable'){
                    form+="<td class='"+cls+"'>"+value.innerHTML+"</td>";
                }
                else if(cls[1]==='action'){
                    form+="<td class='"+cls[0]+"'>" +
                        "<button class='btn confirm-edit'><i class='glyphicon glyphicon-ok'></i></button>" +
                        "<button class='btn cancel-edit'><i class='glyphicon glyphicon-remove'></i></button>" +
                        "</td>";
                }
                else {
                    form += "<td class='" + cls + "'><input id=input-" + i + " value='" + value.innerHTML + "'></td>";
                }
                i++;
            });
            form+="</tr>";
            tr.after(form);
        });

        //remove edit form
        $("tbody").on("click", ".cancel-edit", function () {
            $(".hidden").removeClass("hidden");
            $(this).parent().parent().remove();
        });

        //confirm edit form
        $("tbody").on("click", ".confirm-edit", function () {
            var data={};
            var service= $(this).closest("table").attr("id").split('-');
            var id= $(this).parent().parent().data("id");
            data.id = id;
            data.fullname, data.email, data.phone, data.points;
            if(service[0]==='customer') {
                $(this).closest('tr').find('input').each(function(){
                    if($(this).attr("id") == "input-1") data.fullname = $(this).val();
                    if($(this).attr("id") == "input-2") data.email = $(this).val();
                    if($(this).attr("id") == "input-3") data.phone = $(this).val();
                    if($(this).attr("id") == "input-4") data.points = parseInt($(this).val());
                });
                console.log(data);
                updatedata("/api/customer/update", JSON.stringify(data));
            }
            else if(service[0]==='user'){
                //disini ya josh
            }
            $(".hidden").removeClass("hidden");
            $(this).parent().parent().remove();
        });

        //delete-btn action
        $("tbody").on("click",".delete-btn", function () {
            if(confirm("Are you sure? You can't undo this delete")){
                var service= $(this).closest("table").attr("id").split('-');
                var id= $(this).parent().parent().data("id");
                deletedata("/api/"+service[0]+"/delete?id="+id, id);
            }
        });

    </script>

</body>
</html>