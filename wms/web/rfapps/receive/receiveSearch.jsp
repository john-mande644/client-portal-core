<%@ taglib prefix="s" uri="/struts-tags" %>

<html><head>
<link href="/wms/css/packslip.css" rel="stylesheet" type="text/css">
<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">

<!-- jQuery -->
<script type="text/javascript" charset="utf8" src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js"></script>

<!-- DataTables -->
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>


    <script type="text/javascript">
        $(document).ready(function() {
            $('#example').DataTable( {
                "processing": true,
                "ajax": "/wms/receive/loadSearchableItems.action?asnId=<s:property value="asnId"/>",
                dataSrc: '',
                "columns": [
                    {data:"inventoryFkey",
                        "render": function ( data, type, row ) {
                        return '<a href="/wms/receive/loadSku.action?asnId=<s:property value="asnId"/>&receiveId=<s:property value="receiveId"/>&employeeId=<s:property value="employeeId"/>&employeeName=<s:property value="employeeName"/>&scannedSkuId=' +data+'">Receive Item!!</a>';
                    },
                        "targets": 0},
                    { "data": "inventoryFkey" },
                    { "data": "inventory_num" },
                    { "data": "title" },
                    { "data": "description" },
                    { "data": "expected" }


                ]
            } );

            $('#example tbody').on( 'click', 'button', function () {
                var data = table.row( $(this).parents('tr') ).data();
                var s = "/wms/receive/loadSku.action?asnId=<s:property value="asnId"/>&receiveId=<s:property value="receiveId"/>&employeeId=<s:property value="employeeId"/>&employeeName=<s:property value="empoyeeName"/>&scannedSkuId=" +data[1];
               console.log(s);
                window.location.href=s;


            } );

        } );
    </script>
</head>




<body>

<div>
    <s:form action="loadAsn">
        <s:hidden name="asnId"/>
        <s:submit label="Cancel Search"></s:submit>
    </s:form>
</div>
<table id="example" class="display" cellspacing="0" width="100%">

    <thead>
    <tr>
        <th>Receive??</th>
        <th>Inventory ID</th>
        <th>SKU</th>
        <th>Title</th>
        <th>Desc</th>
        <th>Expected</th>

    </tr>
    <tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>
    </thead>

</table>



</body>








</html>


