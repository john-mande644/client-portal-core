<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>

<head>
    <title>Vendor Compliance Labels</title>
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.2/jsgrid.min.css" />
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.2/jsgrid-theme.min.css" />

    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.2/jsgrid.min.js"></script>

</head>

<body>
Please select the vendor which you want to work with: <s:form action="loadVendors" theme="simple"><s:select list="vendors" listKey="value" listValue="key" name="loadVendor"/>
<s:submit label="Load Vendor"></s:submit>
</s:form>
<br>
<s:if test="clients != null">
    Please select the Clients which you want to work with: <s:form action="loadClients" theme="simple"><s:select list="clients" listKey="value" listValue="key" name="loadClient"/>
    <s:hidden name="loadVendor"/>
    <s:submit label="Load Client"></s:submit>
</s:form>

</s:if>

<s:if test="gridData != null">
    <div id="jsGrid"></div>
    <s:form action="print" theme="simple">
        Select printer: <s:select list="printers" listKey="key" listValue="value" name="printer"/> Qty:
        <s:textfield name="qty"/>
        <button onclick="printSelected()">Print Selected</button>
    </s:form>


    <script>
        var clients = <s:property value="gridData" escape="false"/>

                $("#jsGrid").jsGrid({
                    width: "100%",
                    height: "400px",
                    datatype: 'jsonstring',
                    inserting: false,
                    editing: false,
                    sorting: true,
                    paging: true,
                    filtering:true,
                    selecting:true,

                    data: clients,

                    fields: [
                        {itemTemplate: function(_, item) {
                            return $("<input>").attr("type", "checkbox")
                                    .prop("checked", $.inArray(item, selectedItems) > -1)
                                    .on("change", function () {
                                        $(this).is(":checked") ? selectItem(item) : unselectItem(item);
                                    });
                        },
                            align: "center",
                            width: 50
                        },
                        { name: "owdSku", type: "text", title: "Sku", width: 75 },
                        { name: "vendorSku", type: "text", title:"Vendor Sku",width: 75 },
                        { name: "owdId", type: "text", title:"OWD ID",width: 50 },
                        { name: "vendorDesc",type: "text", title:"Vendor Desc",width: 150 }

                    ]
                });

        var selectedItems = [];

        var selectItem = function(item) {
            selectedItems.push(item);
        };

        var unselectItem = function(item) {
            selectedItems = $.grep(selectedItems, function(i) {
                return i !== item;
            });
        };
        var printSelected = function(){


            return false;
        }
    </script>
</s:if>



</body>
</html>