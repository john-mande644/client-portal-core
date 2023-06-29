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
                "ajax": "/wms/receive/loadASNSearchableItems.action",
                dataSrc: '',
                "columns": [
                    {data:"asnId",
                        "render": function ( data, type, row ) {
                            return '<a href="/wms/receive/loadAsn.action?employeeId=<s:property value="employeeId"/>&employeeName=<s:property value="employeeName"/>&asnId=' +data+'">Load ASN!!</a>';
                        },
                        "targets": 0},
                    { "data": "asnId" },
                    { "data": "clientRef" },
                    { "data": "clientPo" },
                    { "data": "companyName" },
                    { "data": "createdDate"},
                    { "data": "expectDate" },
                    { "data": "groupName" },
                    { "data": "allInventoryNum" },
                    { "data": "allTitle" },
                    { "data": "allDescription" }


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
    <s:form action="start">

        <s:submit value="Cancel Search"></s:submit>
    </s:form>
</div>
<table id="example" class="display" cellspacing="0" width="100%">

    <thead>
    <tr>
        <th>ASN??</th>
        <th>ID</th>
        <th>Client Ref</th>
        <th>Client PO</th>
        <th>Client</th>
        <th>Created</th>
        <th>Expected</th>
        <th>Group</th>
        <th>Skus</th>
        <th>Titles</th>
        <th>Descriptions</th>

    </tr>
    <tr><td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>

    </tr>
    </thead>

</table>



</body>








</html>


