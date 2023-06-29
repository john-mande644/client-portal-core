<%@ taglib prefix="s" uri="/struts-tags" %>

<head>
    <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" charset="utf8" src="/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="/js/jquery.dataTables.editable.js"></script>
    <script type="text/javascript" src="/js/jquery.jeditable.mini.js"></script>
     <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.12.0/jquery.validate.min.js"></script>
    <link rel="stylesheet" href="/jquery-ui-1.10.4.custom/css/ui-darkness/jquery-ui-1.10.4.custom.min.css" />
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>

    <link href="/wms/warehouse/statusScroller/messages.css" rel="stylesheet" type="text/css">


    <script type="text/javascript">
        $(document).ready(function() {
            $('#example').dataTable( {
                 "bJQueryUI":true,
                "sAjaxSource": "/wms/statusScroller/loadTable.action",
                "columns": [
                    {"mDataProp":"id",sClass:"read_only"},
                    { "mDataProp": "message" },
                    { "mDataProp": "priority" },
                    { "mDataProp": "start" },
                    { "mDataProp": "end" },

                    { "mDataProp": "facility"}
                ]
            }).makeEditable({

                    "sUpdateURL": "/wms/statusScroller/updateTable.action",
                        "sDeleteURL":"/wms/statusScroller/deleteRecord.action",
                        "sAddURL":"/wms/statusScroller/addRecord.action"
                    });
        } );
    </script>
</head>

<body>

 <div class="wrapper">
     <div class="header">
        <h1>OWD Status Screen Messages</h1>
     </div>
     <div class="messageTable">
         <table id="example" class="display" cellspacing="0" width="100%">
             <thead>
             <tr>
                 <th>ID</th>
                 <th>Message</th>
                 <th>Priority</th>
                 <th>Start Date</th>
                 <th>End Date</th>

                 <th>Facility</th>
             </tr>
             </thead>

             <tfoot>
             <tr>
                 <th>ID</th>
                 <th>Message</th>
                 <th>Priority</th>
                 <th>Start Date</th>
                 <th>End Date</th>

                 <th>Facility</th>
             </tr>
             </tfoot>
         </table>
         <div class="add_delete_toolbar" />
         <form id="formAddNewRow" action="#" title="Add new Message">
             <label for="message">Message</label>
             <input type="text" name="message" id="message" rel="1" />
             </br>
             <label for="priority">Priority</label>
             <input type="text" name="priority" id="priority" rel="2"/>
             </br>
             <label for="StartDate">Start Date</label>
             <input type="text" name="StartDate" id="StartDate" rel="3"/>
             </br>
             <label for="EndDate">End Date</label>
             <input type="text" name="EndDate" id="EndDate" rel="4"/>
             </br>

             <label for="facility">Facility</label>
             <input type="text" name="facility" id="facility" rel="5"/>
             <input type="hidden" name="id" id="id" rel="0" value="DATAROWID" rel="0" />







         </form>
     </div>
 </div>
</body>

