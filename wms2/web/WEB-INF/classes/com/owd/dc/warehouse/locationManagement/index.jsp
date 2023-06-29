<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
   <!-- <sx:head debug="false" cache="true" compressed="true" parseContent="false"/> -->

<!-- begin css and javascript for dojo -->
	<style type="text/css">
	@import "/dojo-release-1.3.1/dijit/themes/tundra/tundra.css";
	@import "/dojo-release-1.3.1/dojo/resources/dojo.css";
	 body, html { width:100%; height:100%; margin:0; padding:0 }


   #header {
margin:0;
}
	</style>

	<!-- load the dojo toolkit base -->
	<script type="text/javascript" src="/dojo-release-1.3.1/dojo/dojo.js"
	    djConfig="parseOnLoad:true, isDebug:true"></script>

	<script type="text/javascript">
			dojo.require("dijit.layout.AccordionContainer");
			dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.form.Button");
dojo.require("dijit.TitlePane");
 dojo.require("dojo.parser");
        dojo.require("dojox.data.QueryReadStore");
        dojo.require("dojo.data.api.Read");
        dojo.require("dojo.data.api.Identity");
       dojo.require("dijit.Tree");
          dojo.require("dijit.Dialog");
              dojo.require("dijit.Tooltip");
       dojo.require("dijit.layout.TabContainer");
         dojo.require("dijit.form.TextBox");
        dojo.require("dijit.form.CheckBox");
           dojo.require("dojox.layout.ContentPane");
        
    </script>

    <script type="text/javascript">
        function addMobile(type,parentIdForMobile,typeName){
          dijit.byId('qty').attr('value',1);
            dijit.byId('locType').attr('value',type);
            dijit.byId('parentIdForMobile').attr('value',parentIdForMobile);
          qtyForm = dijit.byId('qtyForm');
            qtyForm.show();
            console.log("testign");
        }
        function processMobile(){
            console.log("in process");
             console.log(dijit.byId("qty").value);
            console.log(dijit.byId("locType").value);
             printAndCreateMobile(dijit.byId("locType").value,dijit.byId('parentIdForMobile').value,dijit.byId("qty").value)


        }


        function printAndCreateMobile(locType,parentIdForMobile,qtyy){
             console.log(qtyy);

               var req2 =	dojo.xhrGet( {
        // The following URL must match that used to test the server.
        url: "/wms2/locationAdd/addMobile.action",
                  content: {locType:locType,parentId:parentIdForMobile,qty:qtyy},

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
	console.log("This is the first callback");
	console.log(response.message);
	if(response.error.length>0){
		console.log("error");
		return new Error(response.error);

	}
        alert(response.message);
    return response;
});

req2.addErrback(function(response){
	console.log("Errororororor");
	console.log(response.message);

	return response;
});
      return false;

            }

    </script>

  <!-- end dojo -->

</head>
<title>Location Management</title>
<body class="tundra">

    <!--   <s:url var="treeUrl" namespace="/locationLookup" action="treeLookup" />
    <sx:tree  href="%{#treeUrl}?root=6" /> -->
<div id="main" dojoType="dijit.layout.BorderContainer" liveSplitters="false" design="sidebar" style="width:100%; height:100%" >
<h1 id="header" dojoType="dijit.layout.ContentPane" region="top">Location Mangement</h1>
	<div dojoType="dijit.layout.AccordionContainer"
			minSize="20" style="width: 300px;" id="leftAccordion" region="leading" splitter="true" duration="200">
<div dojoType="dijit.layout.AccordionPane" title="Static">


    <script type="text/javascript">
        dojo.addOnLoad(function(){
           dojo.declare('treeModel', dijit.tree.ForestStoreModel,{
              
               getChildren: function(parentItem, complete_cb, error_cb){
                   if(parentItem.root)
                   return this.inherited(arguments);

                   var id = this.store.getValue(parentItem, 'id');

                   this.store.fetch({query:{nodeId:id},onComplete: complete_cb, onError: error_cb});
                  return parentItem;
                   
               },
               mayHaveChildren: function(item){
                   if(item.root) return true;
                   if(this.store.getValue(item, 'hasChildren') == true){

                       return true;
                   }
                   return false;
                   
               }
           })

       var store = new dojox.data.QueryReadStore({
           url:'/wms2/locationLookup/treeLookup.action'
           
       });
       var model = new treeModel({
           store: store,
           rootLabel:'Fixed Locations'
       },'store');

       var tree = new dijit.Tree({model: model, onClick: function(item){
            console.dir(item);
           var main =         dijit.byId("mainInterface");

          dijit.byId("tree")._expandNode(tree._itemNodeMap[store.getValue(item,"id")]);
        main.href = "/wms2/locationManagement/byId.action?locId="+store.getValue(item,"id");
         main.refresh();

       }},'tree');

        tree.startup();
            
        })
       function loadChildOnClick(parentId,childId){
          var parent = dijit.byId("tree")._itemNodeMap[parentId];

           
           
          var child = dijit.byId("tree")._itemNodeMap[childId];
          parent.setSelected(false);
           child.setSelected();

           dijit.byId("tree").onClick(child.item)  ;
           

       }
     
    </script>

        <div id="tree"> </div> 

            </div>
			<div dojoType="dijit.layout.AccordionPane" title="Mobile">
				 <button dojoType="dijit.form.Button"  onClick='addMobile("10","8","Bins")'>
DC 1 Create Bins
                                </button>

                 <button dojoType="dijit.form.Button"
                                        onClick='addMobile("10","36521","Bins")'>
DC 6 Create Bins
                                </button>

                <button dojoType="dijit.form.Button"
                        onClick='addMobile("10","94101","Bins")'>
                    DC 7 Create Bins
                </button>
			</div>
				<div dojoType="dijit.layout.AccordionPane" title="Extra">
					extra
				dfafdsfa
			</div>

		</div>
	<div id="mainInterface" dojoType="dojox.layout.ContentPane"  region="center" splitter="true">


	</div>



	</div><!--end Border Container -->

    <div dojoType="dijit.Dialog" title="Number to Create" id="qtyForm">
       How many would you like to create?
        <input dojoType="dijit.form.TextBox" type="text" name="qty" id="qty">
        <input dojoType="dijit.form.TextBox" type="hidden" name="locType" id="locType">
        <input dojoType="dijit.form.TextBox" type="hidden" id="parentIdForMobile" name="parentIdForMobile">
        <button dojoType="dijit.form.Button" type="submit" onClick="processMobile()">
                           OK
                       </button>
                       <button dojoType="dijit.form.Button" type="button" onClick="dijit.byId('qtyForm').hide();">
                           Cancel
                       </button>


    </div>
</body>
</html>