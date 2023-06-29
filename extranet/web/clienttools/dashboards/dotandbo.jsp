<%@ page import="com.owd.hibernate.HibernateSession" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Dot&Bo Fulfillment Dashboard</title>

    <!-- Bootstrap Core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="css/simple-sidebar.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>



    <%

        NumberFormat pctFormat = NumberFormat.getPercentInstance();
        //pctFormat.setMinimumFractionDigits(0);

        String in_curr_rcvs="";
        String in_oldest_rcv="";
        String in_avg_rcv="";

        String[] in_rcvs = new String[3];
        String[] in_units = new String[3];
        String[] in_ncrs = new String[3];


        String[] out_released = new String[2];
        String[] out_shipped = new String[2];
        String[] out_remains = new String[2];
        String[] out_late = new String[2];


        String[] out_kpi_yesterday = new String[2];
        String[] out_kpi_seven = new String[2];
        String[] out_kpi_thirty = new String[2];
        String[] out_kpi_ninety = new String[2];
        String[] out_kpi_all = new String[2];


        ResultSet rs1 = HibernateSession.getResultSet("select count(*),convert(varchar,min(created_on),101),avg(datediff(hour,created_on,getdate())) from receive where client_fkey=525 and created_by='pending'");
        while(rs1.next())
        {
            in_curr_rcvs = rs1.getString(1);
            in_oldest_rcv = rs1.getString(2);
            in_avg_rcv = rs1.getString(3);
        }

        rs1.close();
        ResultSet rs2 = HibernateSession.getResultSet("select  count(distinct(receive_id)), sum(qty), sum(is_asn_found)\n" +
                "from owd_receive r\n" +
                "join (select sum(quantity) as qty, receive_fkey from owd_receive_item group by receive_fkey ) as ri on ri.receive_fkey=receive_id\n" +
                "left outer join receive rr on 'OWDRCV-'+convert(varchar,rr.id)=transaction_num\n" +
                "where type='Receive' \n" +
                "and datediff(day,r.created_date,getdate())=1 and r.client_fkey=525");

        while(rs2.next())
        {
            in_rcvs[0] = rs2.getString(1);
            in_units[0] = rs2.getString(2);
            in_ncrs[0] = rs2.getString(3);
        }
        rs2.close();
        ResultSet rs3 = HibernateSession.getResultSet("select  count(distinct(receive_id)), sum(qty), sum(is_asn_found)\n" +
                "from owd_receive r\n" +
                "join (select sum(quantity) as qty, receive_fkey from owd_receive_item group by receive_fkey ) as ri on ri.receive_fkey=receive_id\n" +
                "left outer join receive rr on 'OWDRCV-'+convert(varchar,rr.id)=transaction_num\n" +
                "where type='Receive' \n" +
                "and datediff(day,r.created_date,getdate())<=7 and r.client_fkey=525");
        while(rs3.next())
        {
            in_rcvs[1] = rs3.getString(1);
            in_units[1] = rs3.getString(2);
            in_ncrs[1] = rs3.getString(3);
        }
        rs3.close();
        ResultSet rs4 = HibernateSession.getResultSet("select  count(distinct(receive_id)), sum(qty), sum(is_asn_found)\n" +
                "from owd_receive r\n" +
                "join (select sum(quantity) as qty, receive_fkey from owd_receive_item group by receive_fkey ) as ri on ri.receive_fkey=receive_id\n" +
                "left outer join receive rr on 'OWDRCV-'+convert(varchar,rr.id)=transaction_num\n" +
                "where type='Receive' \n" +
                "and datediff(day,r.created_date,getdate())<=30 and r.client_fkey=525");
        while(rs4.next())
        {
            in_rcvs[2] = rs4.getString(1);
            in_units[2] = rs4.getString(2);
            in_ncrs[2] = rs4.getString(3);
        }

        rs4.close();
        ResultSet rs5 = HibernateSession.getResultSet("\n" +
                "select  \n" +
                " case when  isnull(carr_service,'LTL') like '%LTL%' then 'LTL' else 'Parcel' end as type, \n" +
                " count(distinct(order_id)),\n" +
                "sum( CASE WHEN order_status='Shipped' then 1 else 0 end),  \n" +
                " count(distinct(order_id))-sum( CASE WHEN order_status='Shipped' then 1 else 0 end),\n" +
                " sum(case when  isnull(carr_service,'LTL') like '%LTL%' then case when convert(datetime,convert(varchar,getdate(),101))>s2.nextbusinessday then 1 else 0 end\n" +
                " else case when convert(datetime,convert(varchar,getdate(),101))>s.nextbusinessday then 1 else 0 end \n" +
                " end)\n" +
                "from owd_order o (NOLOCK) \n" +
                "join owd_order_ship_info (NOLOCK)  on order_id=owd_order_ship_info.order_fkey \n" +
                "join shipdays  s (NOLOCK)  \n" +
                "        join shipdays s2 (NOLOCK) on s2.shipdate=convert(datetime,convert(varchar,s.nextbusinessday,101)) \n" +
                "        on s.shipdate=convert(datetime,convert(varchar,o.post_date,101)) \n" +
                " where  o.client_fkey=525 and ((o.order_status='At Warehouse' and o.is_void=0) or\n" +
                " (o.shipped_on=convert(smalldatetime,convert(varchar,getdate(),101))))\n" +
                "group by\n" +
                "case when  isnull(carr_service,'LTL') like '%LTL%' then 'LTL' else 'Parcel' end ");
        while(rs5.next())
        {
            if("LTL".equals(rs5.getString(1)))
            {

                out_released[1] = rs5.getString(2);
                out_shipped[1] = rs5.getString(3);
                out_remains[1] = rs5.getString(4);
                out_late[1] = rs5.getString(5);


            }   else
            {
                out_released[0] = rs5.getString(2);
                out_shipped[0] = rs5.getString(3);
                out_remains[0] = rs5.getString(4);
                out_late[0] = rs5.getString(5);
            }

        }
        rs5.close();
        ResultSet rs6 = HibernateSession.getResultSet("(select 'LTL' as type, "
                +" ( select round(sum(ontimepct*orders_shipped)/sum(orders_shipped),2)"
                +" from dotbo_kpis k2 where datediff(day,shipped_on,getdate())=1 and k2.type='LTL') as yesterday,"
                +""
                +" ( select round(sum(ontimepct*orders_shipped)/sum(orders_shipped),2)"
                +" from dotbo_kpis k2 where datediff(day,shipped_on,getdate())<=7 and k2.type='LTL') as seven,"
                +""
                +" ( select round(sum(ontimepct*orders_shipped)/sum(orders_shipped),2)"
                +" from dotbo_kpis k2 where datediff(day,shipped_on,getdate())<=30 and k2.type='LTL') as thirty,"
                +""
                +" ( select round(sum(ontimepct*orders_shipped)/sum(orders_shipped),2)"
                +" from dotbo_kpis k2 where datediff(day,shipped_on,getdate())<=90 and k2.type='LTL') as ninety,"
                +""
                +" ( select round(sum(ontimepct*orders_shipped)/sum(orders_shipped),2)"
                +" from dotbo_kpis k2 where  k2.type='LTL') as 'all')"
                +" union"
                +" ("
                +" select 'Parcel', "
                +" ( select round(sum(ontimepct*orders_shipped)/sum(orders_shipped),2)"
                +" from dotbo_kpis k2 where datediff(day,shipped_on,getdate())=1 and k2.type='Parcel') as yesterday,"
                +""
                +" ( select round(sum(ontimepct*orders_shipped)/sum(orders_shipped),2)"
                +" from dotbo_kpis k2 where datediff(day,shipped_on,getdate())<=7 and k2.type='Parcel') as seven,"
                +""
                +" ( select round(sum(ontimepct*orders_shipped)/sum(orders_shipped),2)"
                +" from dotbo_kpis k2 where datediff(day,shipped_on,getdate())<=30 and k2.type='Parcel') as thirty,"
                +""
                +" ( select round(sum(ontimepct*orders_shipped)/sum(orders_shipped),2)"
                +" from dotbo_kpis k2 where datediff(day,shipped_on,getdate())<=90 and k2.type='Parcel') as ninety,"
                +""
                +" ( select round(sum(ontimepct*orders_shipped)/sum(orders_shipped),2)"
                +" from dotbo_kpis k2 where  k2.type='Parcel') as 'all'"
                +""
                +" )"
                +"");
        while(rs6.next())
        {
            if("LTL".equals(rs6.getString(1)))
            {

                out_kpi_yesterday[1] = rs6.getString(2);
                out_kpi_seven[1] = rs6.getString(3);
                out_kpi_thirty[1] = rs6.getString(4);
                out_kpi_ninety[1] = rs6.getString(5);
                out_kpi_all[1] = rs6.getString(6);


            }   else
            {
                out_kpi_yesterday[0] = rs6.getString(2);
                out_kpi_seven[0] = rs6.getString(3);
                out_kpi_thirty[0] = rs6.getString(4);
                out_kpi_ninety[0] = rs6.getString(5);
                out_kpi_all[0] = rs6.getString(6);
            }

        }
        rs6.close();

        ResultSet rs7 = HibernateSession.getResultSet("select  convert(varchar,shipped_on,101),ISNULL((select ontimepct from dotbo_kpis k2 where k2.shipped_on=dotbo_kpis.shipped_on and type='LTL'),null),\n" +
                "                 ISNULL((select ontimepct from dotbo_kpis k2 where k2.shipped_on=dotbo_kpis.shipped_on and type='Parcel') ,null)\n" +
                "                 from dotbo_kpis where datediff(day,shipped_on,getdate())<60 and type='Parcel' order by convert(varchar,shipped_on,101) asc");

        StringBuilder chartdata = new StringBuilder();
        chartdata.append("{\"cols\": [");
        chartdata.append("{\"id\":\"a\",\"label\":\"Date\",\"pattern\":\"\",\"type\":\"string\"},");
        chartdata.append("{\"id\":\"b\",\"label\":\"LTL\",\"pattern\":\"\",\"type\":\"number\"},");
        chartdata.append("{\"id\":\"c\",\"label\":\"Parcel\",\"pattern\":\"\",\"type\":\"number\"},");
        chartdata.append("{\"id\":\"d\",\"label\":\"KPI Goal\",\"pattern\":\"\",\"type\":\"number\"}");
        chartdata.append("],");
        chartdata.append("\"rows\": [");

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();

        String lastDate  =   "01/01/1970";


        int rows = 0;

        while(rs7.next())
        {

            if(rows==0)
            {
                c.setTime(df.parse(rs7.getString(1)));
                c.add(Calendar.DATE, -1);
                chartdata.append("{\"c\":[{\"v\":\""+df.format(c.getTime())+"\",\"f\":null},{\"v\":null,\"f\":null},{\"v\":null,\"f\":null} ,{\"v\":0.98,\"f\":null} ]}");
                rows++;
            }
                chartdata.append(",");

            chartdata.append("{\"c\":[{\"v\":\""+rs7.getString(1)+"\",\"f\":null},{\"v\":"+rs7.getString(2)+",\"f\":null},{\"v\":"+rs7.getString(3)+",\"f\":null} ,{\"v\":0.98,\"f\":null} ]}");
             lastDate =  rs7.getString(1);
        }
        chartdata.append(",");
        c.setTime(df.parse(lastDate));
        c.add(Calendar.DATE, 1);
        chartdata.append("{\"c\":[{\"v\":\""+df.format(c.getTime())+"\",\"f\":null},{\"v\":null,\"f\":null},{\"v\":null,\"f\":null} ,{\"v\":0.98,\"f\":null} ]}");

        chartdata.append("]}");
        rs7.close();

    %>
    <script type="text/javascript">

        // Load the Visualization API and the piechart package.
        google.load('visualization', '1.0', {'packages':['corechart','line']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.setOnLoadCallback(drawChart);

        // Callback that creates and populates a data table,
        // instantiates the pie chart, passes in the data and
        // draws it.
        function drawChart() {

            // Create the data table.
            var data = new google.visualization.DataTable(<%= chartdata.toString() %>);

            // Set chart options
            var options = {'title':'60 Day Outbound KPI History',
                'width':900,
                'height':500};

            // Instantiate and draw our chart, passing in some options.
            var chart = new google.charts.Line(document.getElementById('chart_div'));
            chart.draw(data, google.charts.Line.convertOptions(options));

        }
    </script>
</head>
<body>

<div id="wrapper">

    <!-- Sidebar -->
    <!--   <div id="sidebar-wrapper">
           <ul class="sidebar-nav">
               <li class="sidebar-brand">
                   <a href="#">
                       Dashboard
                   </a>
               </li>
               <li>
                   <a href="#">Dashboard</a>
               </li>
               <li>
                   <a href="#">Shortcuts</a>
               </li>
               <li>
                   <a href="#">Overview</a>
               </li>
               <li>
                   <a href="#">Events</a>
               </li>
               <li>
                   <a href="#">About</a>
               </li>
               <li>
                   <a href="#">Services</a>
               </li>
               <li>
                   <a href="#">Contact</a>
               </li>
           </ul>
       </div>-->
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <h1><img src="http://d2c65cykxd49oj.cloudfront.net/v3/images/logo.png.pagespeed.ce.UX1qsBkPmu.png"></h1>
                    <table class="table table-condensed">
                        <thead>
                        <tr bgcolor="black">
                            <td colspan="12"><p style="color:white;font-size:18px;height:16px;">DC Pulse - Inbound</p></td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr  bgcolor="orange">
                            <td class="text-left" colspan="3" style="border-right:  solid #000000;"><B>Current Receipts</B></td>
                            <td class="text-left" colspan="3" style="border-right:  solid #000000;"><B>Yesterday</B></td>
                            <td class="text-left" colspan="3" style="border-right:  solid #000000;"><B>Last 7 Days</B></td>
                            <td class="text-left" colspan="3" ><B>Last 30 Days</B></td>

                        </tr>
                        <tr>
                            <td>Pending</td>
                            <td>Oldest</td>
                            <td style="border-right:  solid #000000;">Avg Age</td>
                            <td>Total Rcvs</td>
                            <td>Total Units</td>
                            <td style="border-right:  solid #000000;">Total NCR</td>
                            <td>Total Rcvs</td>
                            <td>Total Units</td>
                            <td style="border-right:  solid #000000;">Total NCR</td>
                            <td>Total Rcvs</td>
                            <td>Total Units</td>
                            <td>Total NCR</td>
                        </tr>
                        <tr>
                            <td><%= in_curr_rcvs%></td>
                            <td><%= in_oldest_rcv%></td>
                            <td style="border-right:  solid #000000;"><%= in_avg_rcv %> hours</td>
                            <td><%= in_rcvs[0] %></td>
                            <td><%= in_units[0] %></td>
                            <td style="border-right:  solid #000000;"><%= in_ncrs[0] %></td>
                            <td><%= in_rcvs[1] %></td>
                            <td><%= in_units[1] %></td>
                            <td style="border-right:  solid #000000;"><%= in_ncrs[1] %></td>
                            <td><%= in_rcvs[2] %></td>
                            <td><%= in_units[2] %></td>
                            <td ><%= in_ncrs[2] %></td>
                        </tr>
                        </tbody></table>
                    <p></p>
                    <table class="table table-condensed">
                        <thead>
                        <tr bgcolor="black">
                            <td colspan="8"><p style="color:white;font-size:18px;height:16px;">DC Pulse - Outbound</p></td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr  bgcolor="orange">
                            <td class="text-left" colspan="4" style="border-right:  solid #000000;"><B>Parcel</B></td>
                            <td class="text-left" colspan="4" ><B>LTL</B></td>

                        </tr>
                        <tr>
                            <td>Released</td>
                            <td>Shipped</td>
                            <td>Remaining</td>
                            <td style="border-right:  solid #000000;">Late (1 day KPI)</td>
                            <td>Released</td>
                            <td>Shipped</td>
                            <td>Remaining</td>
                            <td >Late (2 day KPI)</td>
                        </tr>
                        <tr>
                            <td><%= out_released[0] %></td>
                            <td><%= out_shipped[0] %></td>
                            <td><%= out_remains[0] %></td>
                            <td style="border-right:  solid #000000;"><%= out_late[0] %></td>
                            <td><%= out_released[1] %></td>
                            <td><%= out_shipped[1] %></td>
                            <td><%= out_remains[1] %></td>
                            <td><%= out_late[1] %></td>
                        </tr>
                        </tbody></table>
                    <p></p>
                    <table class="table table-condensed">
                        <thead>
                        <tr bgcolor="black">
                            <td colspan="10"><p style="color:white;font-size:18px;height:16px;">DC KPIs - Outbound</p></td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr  bgcolor="orange">
                            <td class="text-left" colspan="5" style="border-right:  solid #000000;"><B>Parcel</B></td>
                            <td class="text-left" colspan="5" ><B>LTL</B></td>

                        </tr>
                        <tr>
                            <td style="text-align:center">Yesterday</td>
                            <td style="text-align:center">7 Day</td>
                            <td style="text-align:center">30 Day</td>
                            <td style="text-align:center">90 Day</td>
                            <td style="border-right:  solid #000000;text-align:center";">All</td>
                            <td style="text-align:center">Yesterday</td>
                            <td style="text-align:center">7 Day</td>
                            <td style="text-align:center">30 Day</td>
                            <td style="text-align:center">90 Day</td>
                            <td style="text-align:center">All</td>
                        </tr>
                        <tr>
                            <td style="text-align:center;font-size:18px"><%= out_kpi_yesterday[0]==null?"N/A":""+pctFormat.format(Double.parseDouble(out_kpi_yesterday[0])) %></td>
                            <td style="text-align:center;font-size:18px"><%= out_kpi_seven[0]==null?"N/A":""+pctFormat.format(Double.parseDouble(out_kpi_seven[0])) %></td>
                            <td style="text-align:center;font-size:18px"><%= out_kpi_thirty[0]==null?"N/A":""+pctFormat.format(Double.parseDouble(out_kpi_thirty[0])) %></td>
                            <td style="text-align:center;font-size:18px"><%= out_kpi_ninety[0]==null?"N/A":""+pctFormat.format(Double.parseDouble(out_kpi_ninety[0])) %></td>
                            <td style="text-align:center;font-size:18px;border-right:  solid #000000;"><%= out_kpi_all[0]==null?"N/A":""+pctFormat.format(Double.parseDouble(out_kpi_all[0])) %></td>
                            <td style="text-align:center;font-size:18px"><%= out_kpi_yesterday[1]==null?"N/A":""+pctFormat.format(Double.parseDouble(out_kpi_yesterday[1])) %></td>
                            <td style="text-align:center;font-size:18px"><%= out_kpi_seven[1]==null?"N/A":""+pctFormat.format(Double.parseDouble(out_kpi_seven[1])) %></td>
                            <td style="text-align:center;font-size:18px"><%= out_kpi_thirty[1]==null?"N/A":""+pctFormat.format(Double.parseDouble(out_kpi_thirty[1])) %></td>
                            <td style="text-align:center;font-size:18px"><%= out_kpi_ninety[1]==null?"N/A":""+pctFormat.format(Double.parseDouble(out_kpi_ninety[1])) %></td>
                            <td style="text-align:center;font-size:18px"><%= out_kpi_all[1]==null?"N/A":""+pctFormat.format(Double.parseDouble(out_kpi_all[1])) %></td>
                        </tr>
                        </tbody></table>
                    <!--<a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle Menu</a>-->
                </div>
            </div>
        </div>
    </div>
    <!-- /#page-content-wrapper -->
    <center><div id="chart_div"></div></center>

</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js" ></script>

<!-- Bootstrap Core JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<!-- Menu Toggle Script -->
<!--<script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
</script>-->

</body>

</html>
