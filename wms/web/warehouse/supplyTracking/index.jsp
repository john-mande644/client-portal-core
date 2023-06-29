<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Supply Tracking Editor</title>
    <link href="/wms/warehouse/supplyTracking/supplies.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        @import "/dojo-release-1.6.0/dijit/themes/tundra/tundra.css";
        @import "/dojo-release-1.6.0/dojo/resources/dojo.css";
        @import "http://ajax.googleapis.com/ajax/libs/dojo/1.6/dijit/themes/claro/claro.css";
        @import "http://ajax.googleapis.com/ajax/libs/dojo/1.6/dijit/themes/claro/document.css";
        @import "http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojox/grid/enhanced/resources/claro/EnhancedGrid.css";
        @import "http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css";

        body, html {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0
        }

        #header {
            margin: 0;
        }

        .testClass {
            background-color: #00aaee;
        }

    </style>

    <!-- load the dojo toolkit base -->
    <script type="text/javascript" src="/dojo-release-1.6.1-src/dojo/dojo.js"
            djConfig="parseOnLoad:true, isDebug:true"></script>

    <script type="text/javascript">
        dojo.require("dijit.layout.AccordionContainer");
        dojo.require("dijit.layout.BorderContainer");
        dojo.require("dijit.layout.TabContainer");
        dojo.require("dijit.form.Button");
        dojo.require("dijit.form.CheckBox");
        dojo.require("dijit.form.ComboBox");

        dojo.require("dojo.parser");
        dojo.require("dijit.form.TextBox");
        dojo.require("dijit.TitlePane");
        dojo.require("dijit.form.Form");
        dojo.require("dijit.layout.ContentPane");
        dojo.require("dijit.form.FilteringSelect");
        dojo.require("dojo.data.ItemFileReadStore");
        dojo.require("dojo.data.ItemFileWriteStore");
        dojo.require("dojo.store.Memory");
        dojo.require("dojox.form.DropDownSelect");
        dojo.require("dojox.grid.EnhancedGrid");
        dojo.require("dojox.grid.enhanced.plugins.Filter");
        dojo.require("dojox.grid.enhanced.plugins.Selector");


        var groupStore = new dojo.data.ItemFileReadStore({
                    data:{
                                "identifier":"id",
                                  "items":[
                                <s:iterator value="groupTypes">
                                      {'id':"<s:property/>"},
                                </s:iterator>
                                  ]
                            }
                 }) ;
        var countStore = new dojo.data.ItemFileReadStore({
                    data:{
                                "identifier":"id",
                                  "items":[
                                <s:iterator value="countTypes">
                                      {'id':"<s:property/>"},
                                </s:iterator>
                                  ]
                            }
                 }) ;
        var emailStore = new dojo.data.ItemFileReadStore({
                    data:{
                                "identifier":"id",
                                  "items":[
                                <s:iterator value="emails">
                                      {'id':"<s:property/>"},
                                </s:iterator>
                                  ]
                            }
                 }) ;
        var facStore = new dojo.data.ItemFileReadStore({
            data:{
                        "identifier":"id",
                          "items":[
                        <s:iterator value="facilities">
                              {'id':"<s:property/>"},
                        </s:iterator>
                          ]
                    }
         }) ;
    </script>
</head>
<body class="tundra">

<div class="wrapper1">
    <span class="title">Supply Tracking Admin</span>
    <span class="errors"><s:actionerror/></span>
    <span class="messages"><s:actionmessage/></span>
    <div class="top">

        <input type="text" name="invId" id="invId" dojoType="dijit.form.TextBox"/>
        <button id="btn" data-dojo-type="dijit.form.Button" data-dojo-props="
          onClick:function(){
                console.log('lalalal');
                checkId();
          }">Enter in Id to add
        </button>

    </div>
    <div id="addto" data-dojo-type="dijit.Dialog" style="display: none">
        <span class="bold"> Please fill out the data below</span><br>
        <span class="bold"> You will be adding:</span><br>
        <span class="bold"> Sku: </span><span id=sku>sku</span><br>
       <span class="bold"> Description:</span> <span id="description">desc</span><br>

        <div class="from" dojoType="dijit.form.Form" id="newForm" jsid="newForm" encType="multipart/form-data"
             action="/wms/supplies/save.action">
            <script type="dojo/method" event="onSubmit">
                dojo.xhrPost({
                form:dojo.byId("newForm"),
                handleAs:json,
                error: function(){
                console.log("ouch");
                alert("Something went wrong");
                },
                handle:function(response){
                console.log("good");
                goodSave(response);
                }
                });
                return false;
            </script>
            <input type="hidden" id="inventoryId" name="stb.inventoryId">
            <label for="groupType" class="bold">Group: </label>
            <select id="groupType" dojoType="dijit.form.ComboBox" name="stb.groupType">
                <s:iterator value="groupTypes">
                    <option><s:property/></option>
                </s:iterator>
            </select>
            <br>
            <label for="countType" lass="bold">Count Type: </label>
            <select id="countType" dojoType="dijit.form.ComboBox" name="stb.countType">
                <s:iterator value="countTypes">
                    <option><s:property/></option>
                </s:iterator>
            </select>
            <br>
            <label for="notificationEmail" class="bold">Notification Email: </label>
            <select id="notificationEmail" dojoType="dijit.form.ComboBox" name="stb.email">
                <s:iterator value="emails">
                    <option><s:property/></option>
                </s:iterator>
            </select>
            <br>
            <label for="threshold" lass="bold">Notification Threshold: </label>
            <input  id="threshold" name="stb.threshold"/>
            <br>
            <label for="sortOrder" class="bold">Sort Order: </label>
            <input  id="sortOrder" name="stb.sort_order"/>


            <br>
            <label for="facility" class="bold">Facility: </label>
            <select id="facility" dojoType="dijit.form.ComboBox" name="stb.facility">
                <s:iterator value="facilities">
                    <option><s:property/></option>
                </s:iterator>
            </select>

            <button dojoType="dijit.form.Button" type="submit" name="submitButton"
                    value="Submit">Submit
            </button>
        </div>

    </div>
    <div id="gridContainer" dojoType="dijit.layout.ContentPane" style="width: 100%; height: 500px;">

    </div>
    <span class="instuctions">Double click a cell to edit. Edits are auto saves when you leave the field</span>
</div>
</body>

<script>
    function goodsave(response) {
        console.log(response);
    }
    function checkId() {
        console.log(dijit.byId('invId').get('value'));
        var invId = dijit.byId('invId').get('value');
        var req = dojo.xhrGet({
            url:"/wms/supplies/checkId.action",
            handleAs:"json",
            timeout:20000,
            content:{invId:invId},
            load:function (responseObject, ioArgs) {
                // Now you can just use the object
                console.dir(responseObject);  // Dump it to the console
                console.log(responseObject.message);
                console.log(responseObject.error.length);


                return responseObject;
            }
        });

        req.addCallback(function (response) {
            console.log("This is the first callback");
            console.log(response.message);
            if (response.error.length > 0) {
                console.log("error");
                return new Error(response.error);


            }
            dojo.byId('sku').innerHTML = response.inventoryNum;
            dojo.byId('description').innerHTML = response.description;
            dijit.byId('addto').show();
            dojo.byId('inventoryId').value = response.invId;

            return response;
        });


        req.addErrback(function (response) {
            console.log("Errororororor");
            console.log(response.message);
            alert(response.message);
            dojo.byId('invId').select();
            return response;
        });

    }
    function loadInventory() {

        var req = dojo.xhrGet({
            // The following URL must match that used to test the server.
            url:"/wms/supplies/getItems.action",
            handleAs:"json",
            timeout:20000,
            load:function (responseObject, ioArgs) {
                // Now you can just use the object
                console.dir(responseObject);  // Dump it to the console
                console.log(responseObject.message);
                console.log(responseObject.error.length);


                return responseObject;
            }

        });

        req.addCallback(function (response) {
            console.log("This is the first callback");
            //console.log(response.message);
            if (response.error.length > 0) {
                console.log("error");
                return new Error(response.error);


            }
            if (dijit.byId('grid') != null) {
                dijit.byId('grid').destroy();
            }
            var layout = [
                {
                    field:"inventoryId",
                    datatype:"number"
                },
                {
                    field:"inventoryNum",
                    datatype:"string",
                    name:"Sku",
                    width:"200px",
                    autoComplete:true
                },
                {field:"description",
                    datatype:"string",
                    width:"250px",
                                        editable:true

                },
                {
                    field:"groupType",
                    datatype:"string",
                    name:"Grouping",
                    editable:true,
                    type: dojox.grid.cells._Widget, widgetClass: dijit.form.ComboBox, widgetProps: { store:groupStore,searchAttr:'id'} ,

                    autoComplete:true


                },

                {
                    field:"countType",
                    datatype:"string",
                    name:"Count Type",
                    type: dojox.grid.cells._Widget, widgetClass: dijit.form.ComboBox, widgetProps: { store:countStore,searchAttr:'id'} ,

                                        editable:true


                },
                {
                    field:"email",
                    datatype:"string",
                    name:"Notification Email",
                    width:"150px",
                    type: dojox.grid.cells._Widget, widgetClass: dijit.form.ComboBox, widgetProps: { store:emailStore,searchAttr:'id'} ,

                                        editable:true

                },
                {
                    field:"threshold",
                    datatype:"number",
                    name:"Notification Threshold",
                                        editable:true

                },
                {
                    field:"sort_order",
                    datatype:"number",
                    name:"Sort Order",
                                        editable:true

                },

                {
                    field:"facility",
                    datatype:"string",
                    name:"Facility",
                    type: dojox.grid.cells._Widget, widgetClass: dijit.form.ComboBox, widgetProps: { store:facStore,searchAttr:'id'} ,
                                        editable:true

                },
                {
                    field:"active",
                     cellType:"dojox.grid.cells.Bool",
                    name:"Is Active",


                                        editable:true

                }


            ];
            var store = new dojo.data.ItemFileWriteStore({
                data:response
            });
            store.onSet = function(item,attr,oldValue,newValue){
                console.log(item);
                console.log(attr);
                console.log(oldValue);
                console.log(newValue);
                 // saving set to server
                 if(oldValue != newValue){
                var req = dojo.xhrGet({
                            url:"/wms/supplies/update.action",
                            handleAs:"json",
                            timeout:20000,
                            content:{'stb.inventoryNum':item.inventoryNum,
                                       'stb.inventoryId':item.inventoryId,
                                        'stb.description':item.description,
                                        'stb.active':item.active,
                                        'stb.countType':item.countType,
                                        'stb.email':item.email,
                                        'stb.facility':item.facility,
                                        'stb.groupType':item.groupType,
                                        'stb.sort_order':item.sort_order,
                                        'stb.threshold':item.threshold},
                            load:function (responseObject, ioArgs) {
                                // Now you can just use the object
                                console.dir(responseObject);  // Dump it to the console
                                console.log(responseObject.message);
                                console.log(responseObject.error.length);


                                return responseObject;
                            }
                        });

                        req.addCallback(function (response) {
                            console.log("This is the first callback");
                            console.log(response.message);
                            if (response.error.length > 0) {
                                console.log("error");
                                return new Error(response.error);


                            }


                            return response;
                        });


                        req.addErrback(function (response) {
                            console.log("Errororororor");
                            console.log(response.message);
                            alert(response.message);

                            return response;
                        });
                 }
                //End saveing set to server
            }
            var grid = new dojox.grid.EnhancedGrid({
                id:'grid',
                store:store,
                structure:layout,
                rowSelector:"20px",

                plugins:{
                    filter:{
                        //Show the closeFilterbarButton at the filter bar

                        //Set the maximum rule count to 5
                        ruleCount:5,
                        //Set the name of the items
                        itemsName:"Inventory"
                    },
                    selector:{
                        row:"single",
                        col:false,
                        cell:false
                    }
                }
            });
            grid.placeAt('gridContainer');
            grid.startup();

            return response;
        });

        req.addErrback(function (response) {
            console.log("Errororororor");
            console.log(response.message);

            return response;
        });


        return false;


    }
    dojo.addOnLoad(function () {
        loadInventory();
    });
</script>
</html>