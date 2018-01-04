<%--
  Created by IntelliJ IDEA.
  User: Joshua_123
  Date: 18/12/2017
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
</head>
<body>
    <div class="inner-content container-fluid">
        <ul class="nav nav-tabs nav-justified">
            <li id="daily-tab" class="active"><a data-toggle="pill" href="#daily-table">Daily</a></li>
            <li id="weekly-tab"><a data-toggle="pill" href="#weekly-table">Weekly</a></li>
            <li id="monthly-tab"><a data-toggle="pill" href="#monthly-table">Monthly</a></li>
            <li id="yearly-tab"><a data-toggle="pill" href="#yearly-table">Yearly</a></li>
        </ul>

        <div class="tab-content">
            <div id="daily-table" class="display-tab tab-pane fade active in">
                <div class="col-md-12">
                    <ul class="search-tab nav nav-pills navbar-right">
                        <li class="form-inline">
                            <form id="search-daily" action="" method="get">
                                <div class="input-group">
                                    <input name="search-ledger" class="form-control" type="date">
                                    <div class="input-group-btn">
                                        <button class="search-btn btn btn-default">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </li>
                    </ul>
                </div>

                <table class="table head table-striped">
                    <thead>
                    <tr>
                        <th class="col-sm-1">Date</th>
                        <th class="col-sm-1">Value</th>
                        <th class="col-sm-1">Quantity</th>
                        <th class="col-sm-1">Discount</th>
                        <th class="col-sm-1">View</th>
                    </tr>
                    </thead>
                </table>
                <div style="overflow-y: scroll; overflow-x:scroll; height: 350px">
                    <table id="daily-body" class="table head table-striped">
                        <tbody>
                        </tbody>
                    </table>
                </div>

                <div class="col-md-12">
                    <ul class="pager">
                        <li id="daily-previous" class="previous"><a href="">Previous</a></li>
                        <li id="daily-count" class="count"></li>
                        <li id="daily-next" class="next"><a href="">Next</a></li>
                    </ul>
                </div>
            </div>

            <div id="weekly-table" class="display-tab tab-pane fade">
                <div class="col-md-12">
                    <ul class="search-tab nav nav-pills navbar-right">
                        <li class="form-inline">
                            <form id="search-weekly" action="" method="get">
                                <div class="input-group">
                                    <input name="search-ledger" class="form-control" type="week">
                                    <div class="input-group-btn">
                                        <button class="search-btn btn btn-default">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </li>
                    </ul>
                </div>

                <table id="weekly-body" class="table head table-striped">
                    <thead>
                    <tr>
                        <th class="col-sm-1">Date</th>
                        <th class="col-sm-1">Value</th>
                        <th class="col-sm-1">Quantity</th>
                        <th class="col-sm-1">Discount</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

                <div class="col-md-12">
                    <ul class="pager">
                        <li id="weekly-previous" class="previous"><a href="">Previous</a></li>
                        <li id="weekly-count" class="count"></li>
                        <li id="weekly-next" class="next"><a href="">Next</a></li>
                    </ul>
                </div>
            </div>

            <div id="monthly-table" class="display-tab tab-pane fade">
                <div class="col-md-12">
                    <ul class="search-tab nav nav-pills navbar-right">
                        <li class="form-inline">
                            <form id="search-monthly" action="" method="get">
                                <div class="input-group">
                                    <input name="search-ledger" class="form-control" type="month">
                                    <div class="input-group-btn">
                                        <button class="search-btn btn btn-default">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </li>
                    </ul>
                </div>

                <table id="monthly-body" class="table head table-striped">
                    <thead>
                    <tr>
                        <th class="col-sm-1">Date</th>
                        <th class="col-sm-1">Value</th>
                        <th class="col-sm-1">Quantity</th>
                        <th class="col-sm-1">Discount</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

                <div class="col-md-12">
                    <ul class="pager">
                        <li id="monthly-previous" class="previous"><a href="">Previous</a></li>
                        <li id="monthly-count" class="count"></li>
                        <li id="monthly-next" class="next"><a href="">Next</a></li>
                    </ul>
                </div>
            </div>

            <div id="yearly-table" class="display-tab tab-pane fade">
                <div class="col-md-12">
                    <canvas id="yearly-chart" width="200" height="50"></canvas>
                </div>
                <table id="yearly-body" class="table head table-striped">
                    <thead>
                    <tr>
                        <th class="col-sm-1">Date</th>
                        <th class="col-sm-1">Value</th>
                        <th class="col-sm-1">Quantity</th>
                        <th class="col-sm-1">Discount</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

                <div class="col-md-12">
                    <ul class="pager">
                        <li id="yearly-previous" class="previous"><a href="">Previous</a></li>
                        <li id="yearly-count" class="count"></li>
                        <li id="yearly-next" class="next"><a href="">Next</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div id="details" class="modal fade" role="dialog">
        <div class="modal-dialog">
             <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Transaction Details</h4>
                </div>
                <div class="modal-body" style="overflow-y: scroll; height: 400px">
                    <table id="details-table" class="table">
                        <tbody>
                        <%--template details--%>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        getTransaction("/api/transaction/daily/getall?interval=day&page=0", 0);
        getTransaction("/api/transaction/weekly/getall?interval=week&page=0", 0);
        getTransaction("/api/transaction/monthly/getall?interval=month&page=0", 0);
        getTransaction("/api/transaction/yearly/getall?interval=year&page=0", 0);

        //search ledger
        $("#search-daily, #search-weekly, #search-monthly").change(function () {
            var interval;
            console.log($(this).serialize());
            if(this.id==='search-daily')
                interval='day';
            else if(this.id==='search-weekly')
                interval='week';
            else
                interval='month';
            getTransaction("/api/transaction/"+this.id.split('-')[1]+"/getsearch?interval="+ interval +"&"+ $(this).serialize().replace('W','')+"&page=0",0);
        });

        //close view more
        $("tbody").on('click', '.close-link-more', function (e) {
            e.preventDefault();
            $("tbody").find('.active').removeClass('active');
            $("tbody").find(".daily-transaction").remove();
            $(this).parent().html("<a class='link-more' href=''>View more</a>");
        });

        //view more
        $("tbody").on('click', ".link-more", function (e) {
            e.preventDefault();
            //removes previous request
            $("tbody").find('.close-link-more').click();

            var date= $(this).parent().parent().children().eq(0).html().split(',')[0];
            $(this).parent().parent().addClass("active");
            getDailyTransaction(date);

        });

        //view details
        $("tbody").on('click', ".link-details", function () {
            console.log($(this).closest('tr').data('id'));
            getDetails('/api/transaction/getdetails?id-details='+$(this).closest('tr').data('id'));
        });

    </script>

</body>
</html>
