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

  </style>
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
                       "aoColumnDefs":[
                           {"bVisible":false,"aTargets":[0]},
                           {"bVisible":false,"aTargets":[5]}

                       ]
                    }

            );

            $(".client").change(function(){
                $('#detailedData').dataTable().fnFilter($(".client").val(),2,0,1);
            });
            $(".sla").change(function(){
                $('#detailedData').dataTable().fnFilter($(".sla").val(),4,0,1);
            });
            $(".shipMethod").change(function(){
                if($(".shipMethod").val().length==0){
                    $('#detailedData').dataTable().fnFilter($(".shipMethod").val(),3,0,0);
                } else{
                    $('#detailedData').dataTable().fnFilter("^"+$(".shipMethod").val()+"$",3,1,1);
                }

            });
            $(".sku").keyup(function(){

               $('#detailedData').dataTable().fnFilter($(".sku").val(),5,0,0);
            });
            $(".groupName").change(function(){

                $('#detailedData').dataTable().fnFilter($(".groupName").val(),6,0,0);
            });
            $.fn.dataTableExt.oApi.fnResetAllFilters = function (oSettings, bDraw/*default true*/) {
                for(iCol = 0; iCol < oSettings.aoPreSearchCols.length; iCol++) {
                    oSettings.aoPreSearchCols[ iCol ].sSearch = '';
                }
                oSettings.oPreviousSearch.sSearch = '';

                if(typeof bDraw === 'undefined') bDraw = true;
                if(bDraw) this.fnDraw();
            }

        })
       function clearSearch(){
           $(".sla").val('');
           $(".client").val('');
           $(".shipMethod").val('');
           $(".groupName").val('');
           $(".sku").val('');
           $("#detailedData").dataTable().fnResetAllFilters(true);
       }

        function printFiltered(){
            var filteredrows = $('#detailedData').dataTable()._('tr',{"filter": "applied"});
            var ids="";
            for ( var i = 0; i < filteredrows.length; i++ ) {
                ids = ids.concat(filteredrows[i][0],',');

            };

            console.log(ids.slice(0,-1));
            var printer = $(".printer").val();
            $.post("<s:url forceAddSchemeHostAndPort="true" value="groupDetailPrint.action"/>",{printer:printer,orderIds:ids.slice(0,-1)},function(data,status){
                alert( data );
            });
        }
    </script>
</head>

<body>
<div class="wrapper">
   <div class="header">
       Group Reprint of Non Picked Printed Slips
         <div class="refresh">
             <a href="/wms/packSlipRelease/groupReprint.action?facility=<s:property value="facility"/>"><button value="Refresh">Refresh</button> </a>
         </div>
   </div>
    <div class="messages">
    <s:actionmessage/>
<S:actionerror/>
</div>
    <div class="info">


   <table class="reprint">
    <th>Client</th>
       <th>Non Picked Count</th>
       <th>Action</th>
       <th>Slips long forgotten<br>(Slips before today)</th>
       <th>Action</th>
    <s:iterator value="clientList">
       <tr>
       <td><s:property value="clientName"></s:property></td>
       <td class="center"> <s:property value="count"/> </td>
        <td><a href="/wms/packSlipRelease/doGroupReprint.action?facility=<s:property value="facility"/>&clientId=<s:property value="clientId"/>">RePrint All</a></td>
       <td class="center"><s:property value="oldCount"/></td>
        <td><a href="/wms/packSlipRelease/doGroupReprint.action?facility=<s:property value="facility"/>&clientId=<s:property value="clientId"/>&oldOnesOnly=yes">RePrint old</a></td>
         </tr>
    </s:iterator>
   </table>
    </div>

    <div class="advancedreprint">
      <div class="search">
          Use the info below to filter data  <br>

          <s:select list="clients" id="client" headerValue="Client" headerKey="" cssClass="client"/>
          <s:select list="slas" id="sla" headerKey="" headerValue="SLA" cssClass="sla"/>
          <s:select list="shipMethods" id="shipMethods" headerKey="" headerValue="Ship Method" cssClass="shipMethod"/>
          <s:select list="groupNames" headerKey="" headerValue="Group Name" cssClass="groupName"/>
          <s:label for="sku">Sku:</s:label><s:textfield name="sku" id="sku" cssClass="sku"/>
           <button onclick="clearSearch()">Clear</button>
          <br>
            <s:select list="singlePrinters" cssClass="printer"/>
          <button onclick="printFiltered()">Re-print Filtered Orders</button>
      </div>
     <s:if test="detailedReprintData != null">
         <table id="detailedData">
             <thead>
             <th>Order Id</th>
             <th>Order Number</th>
             <th>Client</th>
             <th>Ship Method</th>
             <th>SLA</th>
             <th>FingerPrint</th>
             <th>GroupName</th>
             <th>Status</th>
             </thead>

             <s:iterator value="detailedReprintData">
                 <tr>
                     <td><s:property value="orderId"/> </td>
                     <td><s:property value="orderNum"/> </td>
                     <td><s:property value="client"/> </td>
                     <td><s:property value="shipMethod"/> </td>
                     <td><s:property value="SLA"/> </td>
                     <td><s:property value="fingerprint"/> </td>
                     <td><s:property value="groupName"/> </td>
                     <td><s:property value="packed"/> </td>
                 </tr>

             </s:iterator>



         </table>




     </s:if>
    </div>
</div>















</body>
</html>