<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="S" uri="/struts-tags" %>

<html>

<head>
    <style type="text/css">
        .center{
            text-align:center;
        }
        .advancedreprint{
            padding-top:20px;
        }
        .info{
            border-bottom: 2px solid black;
        }
        .search{
            padding:10px;
        }
        a.link:link{
            color:#0000ff;
            text-decoration: none;
        }
        a.link:visited{
            color: #0000ff;
            text-decoration: none;
        }
        .link{
            padding: 5px;
            font-size: 20px;
        }
        .bttn {
            box-shadow:inset 0px 1px 0px 0px #ffffff;
            background:linear-gradient(to bottom, #ededed 5%, #dfdfdf 100%);
            background-color:#ededed;
            border-radius:6px;
            border:1px solid #dcdcdc;
            display:inline-block;
            cursor:pointer;
            color:#777777;
            font-family:Arial;
            font-size:15px;
            font-weight:bold;
            padding:6px 12px;
            text-decoration:none;
            text-shadow:0px 1px 0px #ffffff;
        }
        .bttn:hover {
            background:linear-gradient(to bottom, #dfdfdf 5%, #ededed 100%);
            background-color:#dfdfdf;
        }
        .bttn:active {
            position:relative;
            top:1px;
        }
        *{
            font-family: Helvetica;
        }

    </style>
    <link href="/wms/css/packslip.css" rel="stylesheet" type="text/css">
    <!-- DataTables CSS -->
    <link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">

    <!-- jQuery -->
    <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"></script>

    <!-- DataTables -->
    <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/black-tie/jquery-ui.min.css">

    <script>
        $(document).ready(function(){
            $('#detailedData').dataTable(
                {
                    "aoColumnDefs":[{"bVisible":false,"aTargets":[0,7,8,11]}]
                }
            );
            $(".shipMethod").change(function(){
                if($(".shipMethod").val().length==0){
                    $('#detailedData').dataTable().fnFilter($(".shipMethod").val(),2,0,0);
                } else{
                    $('#detailedData').dataTable().fnFilter("^"+$(".shipMethod").val()+"$",2,1,1);
                }
            });
            $(".sla").change(function(){
                $('#detailedData').dataTable().fnFilter($(".sla").val(),3,0,1);
            });
            $(".sku").keyup(function(){
                $('#detailedData').dataTable().fnFilter($(".sku").val(),4,0,0);
            });
            $(".unit").change(function(){
                if($(".unit").val().length ==0){
                    $('#detailedData').dataTable().fnFilter($(".unit").val(),5,0,0);
                } else{
                    $('#detailedData').dataTable().fnFilter("(^|\\D)"+$(".unit").val()+"(\\D|$)",5,1,0);
                }
            });
            $(".groupName").change(function(){
                $('#detailedData').dataTable().fnFilter($(".groupName").val(),6,0,0);
            });
            $(".description").keyup(function(){
                $('#detailedData').dataTable().fnFilter($(".description").val(),7,0,0);
            });
            $(".aisles").change(function(){
                $('#detailedData').dataTable().fnFilter($(".aisles").val(),8,0,0);
            });
    $(".boxTypes").change(function(){
    $('#detailedData').dataTable().fnFilter($(".boxTypes").val(),11,0,0);
    });
            $(".clients").change(function(){
                $('#detailedData').dataTable().fnFilter($(".clients").val(),9,0,1);
            });
            $(".zones").change(function(){
                $('#detailedData').dataTable().fnFilter($(".zones").val(),10,0,1);
            });            
            $.fn.dataTableExt.oApi.fnResetAllFilters = function (oSettings, bDraw/*default true*/) {
                for(iCol = 0; iCol < oSettings.aoPreSearchCols.length; iCol++) {
                    oSettings.aoPreSearchCols[ iCol ].sSearch = '';
                }
                oSettings.oPreviousSearch.sSearch = '';
                if(typeof bDraw === 'undefined') bDraw = true;
                if(bDraw) this.fnDraw();
            }

            var skuList =[];
            <s:iterator value="skusInPrintQueue">
                skuList.push('<s:property/>')
            </s:iterator>
            $("#sku").autocomplete({
                source:skuList
            })  ;
        }) ;

        function clearSearch(){
            $(".sla").val('');
            $(".clients").val('');
            $(".zones").val('');
            $(".shipMethod").val('');
            $(".groupName").val('');
            $(".sku").val('');
            $(".unit").val('');
            $(".description").val('');
            $(".aisles").val('');
    $(".boxTypes").val('');
            $(".unitsPerBatch").val();
            $("#detailedData").dataTable().fnResetAllFilters(true);
        }

        // Sean 5/1/2020 case 789594
        function printFiltered(){
            var filteredrows =$('#detailedData').dataTable()._('tr',{"filter": "applied"});
            var ids="";
            var targetSum = 0;
            if ($("#unitsPerBatch").val() !== ""){
                targetSum = $("#unitsPerBatch").val();
                ids = getUnitBatchOrderIds(targetSum, filteredrows);
            }else{
                for (var i=0; i< filteredrows.length; i++){
                    ids = ids.concat(filteredrows[i][0],',');
                }
            }
            // alert(ids);
            printOrders(ids);
        }
        function getUnitBatchOrderIds(targetSum, rows){
            var ids="";
            var countUnits=0;
            for (var i=0; i< rows.length; i++){
                if (countUnits < targetSum){
                    countUnits = countUnits + parseInt(rows[i][5]);
                    ids = ids.concat(rows[i][0],',');
                }
            }
            return ids;
        }
        function printOrders(ids){
            var printer = $(".printer").val();
            var sortRack = $('.sort').prop('checked');
            $.post("<s:url forceAddSchemeHostAndPort="true" value="doAdvancedPrint.action"/>",{sortRack:sortRack,printer:printer,orderIds:ids.slice(0,-1)},function(data,status){
                alert( data );
                $('#refresh')[0].click();
            });
        }

    </script>
</head>

<body>
<div class="wrapper">
    <div class="header">
        Advanced Printing for Packing Slips for <s:property value="clientName"/>
        Total units: <s:property value="getTotalUnits()"/>
        <s:if test="%{clientId == 'red'}">
            <br>
            <a href="/wms/packSlipRelease/advancedPrint.action?facility=<s:property value="facility"/>&clientId=blue" class="link">Switch to Non Reds</a>
        </s:if>
        <s:if test="%{clientId == 'blue'}">
            <br>
            <a href="/wms/packSlipRelease/advancedPrint.action?facility=<s:property value="facility"/>&clientId=red" class="link">Switch to REDS!!!!</a>
        </s:if>
        <div class="ref">
           <a href="/wms/packSlipRelease/advancedPrint.action?facility=<s:property value="facility"/>&clientId=<s:property value="clientId"/>" class="link refresh" id="refresh">Refresh </a>
        </div>
    </div>
    <div class="messages">
        <s:actionmessage/>
        <S:actionerror/>
    </div>

    <div class="advancedreprint">
        <div class="search">
            Use the info below to filter data  <br>
            <s:select list="shipMethods" id="shipMethods" headerKey="" headerValue="Ship Method" cssClass="shipMethod"/>
            <s:select list="slas" id="sla" headerKey="" headerValue="SLA" cssClass="sla"/>
            <s:label for="sku">SKU:</s:label><s:textfield name="sku" id="sku" cssClass="sku"/>
            <s:select list="units" id="unit" headerKey="" headerValue="UNITS" cssClass="unit"/>
            <s:select list="groupNames" headerKey="" headerValue="Group Name" cssClass="groupName"/>
            <s:select list="clients" id="clients" headerKey="" headerValue="CLIENTS" cssClass="clients"/>
            <s:select list="zones" id="zones" headerKey="" headerValue="ZONE" cssClass="zones"/>
            <s:label for="description">Description:</s:label><s:textfield name="description" id="description" cssClass="description"/>
            <s:select list="aisles" id="aisles" headerKey="" headerValue="AISLES" cssClass="aisles"/>
            <button class="bttn" onclick="clearSearch()">Clear Filter</button>

            <br>
            <div class="printButton">
                <s:label for="unitsPerBatch">Units per batch:</s:label>
                <s:textfield name="unitsPerBatch" id="unitsPerBatch" cssClass=""/>
    <s:select list="boxTypes" id="boxTypes" headerKey="" headerValue="BOX Type" cssClass="boxTypes"/>
    <br>

                <s:select list="singlePrinters" cssClass="printer" headerKey="" headerValue="Select Printer..." id="printers"/>
                <button class = "bttn" onclick="printFiltered()">Print Filtered Orders</button>
                <input type="checkbox" class="sort">Sort By Rack
            </div>
        </div>
        <s:if test="detailedReprintData != null">
            <table id="detailedData">
                <thead>
                    <th>Order Id</th>
                    <th>Order Number</th>
                    <th>Ship Method</th>
                    <th>SLA</th>
                    <th>FingerPrint</th>
                    <th>Units</th>
                    <th>GroupName</th>
                    <th>Description</th>
                    <th>Aisles</th>
                    <th>Clients</th>
                    <th>Zone</th>
                <th>Box Type</th>
                </thead>

                <s:iterator value="detailedReprintData">
                    <tr>
                        <td><s:property value="orderId"/> </td>
                        <td><s:property value="orderNum"/> </td>
                        <td><s:property value="shipMethod"/> </td>
                        <td><s:property value="SLA"/> </td>
                        <td><s:property value="fingerprint"/> </td>
                        <td><s:property value="units"/></td>
                        <td><s:property value="groupName"/> </td>
                        <td><s:property value="description"/></td>
                        <td><s:property value="aisles"/></td>
                        <td><s:property value="client"/></td>
                        <td><s:property value="zones"/></td>
                        <td><s:property value="boxType"/></td>
                    </tr>
                </s:iterator>
            </table>
        </s:if>
    </div>
</div>

</body>
</html>