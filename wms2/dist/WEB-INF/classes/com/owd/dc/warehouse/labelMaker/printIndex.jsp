
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Label Maker</title>

	<style type="text/css">
	@import "/dojo-release-1.6.0/dijit/themes/tundra/tundra.css";
	@import "/dojo-release-1.6.0/dojo/resources/dojo.css";
    @import "http://ajax.googleapis.com/ajax/libs/dojo/1.6/dijit/themes/claro/claro.css";
       @import "http://ajax.googleapis.com/ajax/libs/dojo/1.6/dijit/themes/claro/document.css";
       @import "http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojox/grid/enhanced/resources/claro/EnhancedGrid.css";
       @import "http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css";

	 body, html { width:100%; height:100%; margin:0; padding:0 }


   #header {
margin:0;
}
.testClass {
		background-color:#00aaee;
	}
   .overflow{
       overflow:auto;
   }
	</style>

	<!-- load the dojo toolkit base -->
	<script type="text/javascript" src="/dojo-release-1.6.0/dojo/dojo.js"
	    djConfig="parseOnLoad:true, isDebug:true"></script>
     <script type="text/javascript" src="/dojo-release-1.6.0/dojo/owdLabelDojo.js"></script>
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
dojo.require("dojox.form.DropDownSelect");
dojo.require("dojox.grid.EnhancedGrid");
dojo.require("dojox.grid.enhanced.plugins.Filter");
            dojo.require("dojox.grid.enhanced.plugins.Selector");

function printInventoryLabel( printer){

     var qty = dijit.byId('qty').value;

     console.log(qty);

   if( dijit.byId('grid').selection.getSelected().length >0){


             var req2 =	dojo.xhrGet( {
        // The following URL must match that used to test the server.
        url: "/wms2/labelMaker/printInventory.action",
                  content: {printer:printer,qty:qty,id: dijit.byId('grid').store.getValues(dijit.byId('grid').selection.getSelected()[0],"id")[0]},

                 handleAs: "json",
        timeout: 20000,

        load: function(responseObject, ioArgs) {
          // Now you can just use the object
          console.dir(responseObject);  // Dump it to the console
          console.log(responseObject.message);
          console.log(responseObject.error.length);
          return responseObject;
        }

});

req2.addCallback(function(response) {
    dijit.byId('qty').reset();
	console.log("This is the first callback");
	console.log(response.message);
	if(response.error.length>0){
		console.log("error");
		return new Error(response.error);

	}

    return response;
});

req2.addErrback(function(response){
	console.log("Errororororor");
	console.log(response.message);

	return response;
});

   }else{
       alert("Nothing selected to print");
   }

     return false;






            }
function printTree( formid){

     var form = "form"+formid;
         var p = dijit.byId(form).attr('value');
     console.log(p);
     console.dir(p);
      var print = dijit.byId('printer')._getSelectedOptionsAttr().value;
             var req =	dojo.xhrGet( {
        // The following URL must match that used to test the server.
        url: "/wms2/labelMaker/printTree.action",
                  content: {printer:print},
                 form: form,
                 handleAs: "json",
        timeout: 20000,

        load: function(responseObject, ioArgs) {
          // Now you can just use the object
          console.dir(responseObject);  // Dump it to the console
          console.log(responseObject.message);
          console.log(responseObject.error.length);
          return responseObject;
        }

});

req.addCallback(function(response) {
	console.log("This is the first callback");
	console.log(response.message);
	if(response.error.length>0){
		console.log("error");
		return new Error(response.error);

	}

    return response;
});

req.addErrback(function(response){
	console.log("Errororororor");
	console.log(response.message);

	return response;
});
     return false;






            }
 function printRange( formid){

     var form = "form"+formid;
         var p = dijit.byId(form).attr('value');
     console.log(p);
     console.dir(p);
      var print = dijit.byId('printer')._getSelectedOptionsAttr().value;
             var req =	dojo.xhrGet( {
        // The following URL must match that used to test the server.
        url: "/wms2/labelMaker/printRange.action",
                  content: {printer:print},
                 form: form,
                 handleAs: "json",
        timeout: 20000,

        load: function(responseObject, ioArgs) {
          // Now you can just use the object
          console.dir(responseObject);  // Dump it to the console
          console.log(responseObject.message);
          console.log(responseObject.error.length);
          return responseObject;
        }

});

req.addCallback(function(response) {
	console.log("This is the first callback");
	console.log(response.message);
	if(response.error.length>0){
		console.log("error");
		return new Error(response.error);

	}

    return response;
});

req.addErrback(function(response){
	console.log("Errororororor");
	console.log(response.message);

	return response;
});
     return false;






            }
 function loadInventory(){
     var clientId = dijit.byId('client').get('value');
    var req =	dojo.xhrGet( {
        // The following URL must match that used to test the server.
        url: "wms2/labelMaker/getInventory.action",
        handleAs: "json",
        timeout: 20000,
        content: {clientFkey:clientId},
        load: function(responseObject, ioArgs) {
          // Now you can just use the object
          console.dir(responseObject);  // Dump it to the console
          console.log(responseObject.message);
          console.log(responseObject.error.length);


          return responseObject;
        }

});

req.addCallback(function(response) {
	console.log("This is the first callback");
	//console.log(response.message);
	if(response.error.length>0){
		console.log("error");
		return new Error(response.error);


	}
    if (dijit.byId('grid') != null){
        dijit.byId('grid').destroy();
    }
    var layout = [{
        field:"id",
        datatype:"number"
    },
        {
            field:"sku",
            datatype:"string",
            name:"Sku",
            width:"200px",
            autoComplete:true
        },
        {field:"description",
            datatype:"string",
            width:"300px",
            autoComplete:true

    }] ;
    var store = new dojo.data.ItemFileReadStore({
               data: response
           });

           var grid = new dojox.grid.EnhancedGrid({
               id: 'grid',
               store: store,
               structure: layout,
               rowSelector: "20px",

               plugins: {
                   filter: {
                       //Show the closeFilterbarButton at the filter bar

                       //Set the maximum rule count to 5
                       ruleCount: 5,
                       //Set the name of the items
                       itemsName: "Inventory"
                   }            ,
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

req.addErrback(function(response){
	console.log("Errororororor");
	console.log(response.message);

	return response;
});


     return false;


 }
 function printDirect( id, name){


          console.log(id);
              console.log(name);
           var print = dijit.byId('printer')._getSelectedOptionsAttr().value;
             var req =	dojo.xhrGet( {
        // The following URL must match that used to test the server.
        url: "wms2/labelMaker/printDirectLocation.action",
        handleAs: "json",
        timeout: 20000,
        content: {locId: id,locName: name,printer:print},
        load: function(responseObject, ioArgs) {
          // Now you can just use the object
          console.dir(responseObject);  // Dump it to the console
          console.log(responseObject.message);
          console.log(responseObject.error.length);


          return responseObject;
        }

});

req.addCallback(function(response) {
	console.log("This is the first callback");
	console.log(response.message);
	if(response.error.length>0){
		console.log("error");
		return new Error(response.error);


	}

    return response;
});

req.addErrback(function(response){
	console.log("Errororororor");
	console.log(response.message);

	return response;
});


     return false;






            }


function loadLocTabSelect(parentId,selectId){

    var locId =dijit.byId(selectId)._getSelectedOptionsAttr().value;

    var locName = dijit.byId(selectId)._getSelectedOptionsAttr().label
    loadLocTab(parentId,locId,locName);
    
}
            
 function loadLocTab(parentId,locId,locName){
     var remove = false;

     dojo.forEach(dijit.byId('loclist').getChildren(),function(tab){
         if (tab.id == "tab"+parentId) {
             remove = true;
         }

         if (remove){
         dijit.byId('loclist').removeChild(tab)
         tab.destroyRecursive();
         }

     });

     addTab(locId,locName,parentId);
 }

 function loadFacility(){
     dojo.forEach(dijit.byId('loclist').getChildren(),function(tab){
         dijit.byId('loclist').removeChild(tab)
         tab.destroyRecursive();
     });

     addTab(dijit.byId('facility')._getSelectedOptionsAttr().value,dijit.byId('facility')._getSelectedOptionsAttr().label,0);
 }
 function addTab(locId,locName,tabId){
            
              console.log(locId);
          dijit.byId('loclist').addChild(new dijit.layout.ContentPane({
              title: locName,
              closable:true,
              id: 'tab'+tabId,
              href: "/wms2/labelMaker/locTab.action?locName="+locName+"&locId="+locId})) ;
        dijit.byId('loclist').selectChild(dijit.byId('tab'+tabId));
       return false;
    }
    </script>

</head>
<body class="tundra">

<div dojoType="dijit.layout.BorderContainer" id="mainborder" design="sidebar" style="height:100%;width:100%; border:none" >


<div dojoType="dijit.layout.TabContainer" id="bottomTabs" tabPosition="bottom" tabStrip="true" region="center" splitter="false" >

	<div dojoType="dijit.layout.ContentPane" id="locationTab" title="Locations">

		<div dojoType="dijit.layout.AccordionContainer"
			minSize="20" id="locAccordion" >

		<div dojoType="dijit.layout.ContentPane" id="Static" title="Fixed Locations">

		<!-- border container for loction acordian-->
		<div dojoType="dijit.layout.BorderContainer"  id="fixedborder" design="headline" style="width:90%">
		<div id="fixedheader" dojoType="dijit.layout.ContentPane" region="top">

			Please select Facility to work on: 
            <select id = "facility" name="facility" dojoType="dojox.form.DropDownSelect" onchange="loadFacility()">
                <option value="">Select Facility</option>
                <s:iterator value="facilityMap.entrySet()">
                <option value="<s:property value="key"/>"><s:property value="value"/></option>
                    </s:iterator>

            </select>
            <span class="printselect">Select Printer to print to: 
                 <select id="printer" name="printer" dojoType="dojox.form.DropDownSelect">
                <s:iterator value="printerMap.entrySet()">

                <option value="<s:property value="key"/>"><s:property value="value"/></option>


                </s:iterator>

            </select>
            </span>
        </div>
            <!-- tab containter for location filterig-->
        <div id="loclist" dojoType="dijit.layout.TabContainer" tabPosition="left-h"    region="center" >

            </div>
            <!-- end tab container -->


	</div><!-- end location borderContainter -->

        </div>
		<div dojoType="dijit.layout.ContentPane" id="Mobile" title="Mobile locations">
			Mobile Locations
		</div>

	</div>

	</div>

	<div dojoType="dijit.layout.ContentPane" id="inventoryTab" title="Inventory">
	<span class="clientselect">Please select Client to get Items for:
                 <select id="client" name="client" dojoType="dijit.form.FilteringSelect"
                         placeHolder="Select Client" autocomplete="true" selectOnClick="true"
                         maxHeight="250" onchange="loadInventory()">
                <s:iterator value="clientMap.entrySet()">
                <option value="<s:property value="value"/>"><s:property value="key"/></option>
                    </s:iterator>

            </select>
            </span>
           <div id="gridContainer" dojoType="dijit.layout.ContentPane" style="width: 100%; height: 400px;">

</div>


          Number of Labels to print:  <input type="text" dojoType="dijit.form.TextBox" name="qty" id="qty" value="1">
            <div dojoType="dijit.layout.BorderContainer" id="printerborder" style=" height:150px;width:300px;" nested="true">
            <div dojoType="dijit.layout.TabContainer" id="printbuttonTabs" tabPosition="top" tabStrip="true"  region="center">

                       <s:iterator value="printerTabMap.entrySet()">
                        <div dojoType="dijit.layout.ContentPane" id="pTab<s:property value="key"/>" title="<s:property value="key"/>">
                            <s:iterator value="value">
                                 <button dojoType="dijit.form.Button" iconClass="plusIcon" onClick='printInventoryLabel("<s:property value="key"/>")'><s:property value="value"/>
                                </button>
                            </s:iterator>


                        </div>

                       </s:iterator>

             </div>
                </div>


	</div>


</div>

</div>
<s:property value="defaultLocation"/>

</body>
</html>
