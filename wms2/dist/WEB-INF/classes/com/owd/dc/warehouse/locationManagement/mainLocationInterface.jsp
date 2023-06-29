<%@ taglib prefix="s" uri="/struts-tags" %>

    <script type="text/javascript">
     

      
        
          function LocationSave(parentId,locType,textField,popId){
            console.log(popId);
              console.log(parentId);
              console.log(locType);
              console.log(textField);
              var n = dojo.byId(textField).value;
             var req =	dojo.xhrGet( {
        // The following URL must match that used to test the server.
        url: "/wms2/locationAdd/addNewLocation.action",
        handleAs: "json",
        timeout: 20000,
        content: {action: 'add',parentId: parentId,name:n, locType:locType},
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
    dijit.byId(popId).hide();
    dojo.byId('newmessage').innerHTML = response.message;
    
     var par = <s:property value="locInfo.Id"/>;
     var mess = dojo.byId('newmessage');
	var anin1 = dojo.fadeIn({node:mess, duration:1000});
	var anin2 = dojo.fadeOut({node:mess, duration:3000});
	dojo.fx.chain([anin1,anin2]).play();
     dijit.byId('tree')._itemNodeMap[par].state = "UNCHECKED";
      dijit.byId('tree')._itemNodeMap[par].collapse();
      dijit.byId('tree')._expandNode(dijit.byId('tree')._itemNodeMap[par])
    return response;
});

req.addErrback(function(response){
	console.log("Errororororor");
	console.log(response.message);
	dojo.byId(popId+'error').innerHTML = response.message;
	return response;
});


            console.log(dijit.byId(textField).attr('value'));
        console.log('helpme');


       return false;
    }

     function showDialogwizard(id,textField){


            dijit.byId(id).show();

			}
    function showDialog(id,textField){
		
		dijit.byId(textField).attr('value','');
         dojo.byId(id+'error').innerHTML='';
            dijit.byId(id).show();

			}
    </script>



<span class="parentlist">Location Tree:   <s:property value="locInfo.parentString"/></span><br>
<span class="locName">Editing Location: <s:property value="locInfo.name"/> </span>


<div dojoType="dijit.layout.TabContainer" style="width:100%;height:90%;">
    <div dojoType="dijit.layout.ContentPane" title="Attributes">

        <div dojoType="dijit.layout.ContentPane" title="locAttribs" style="border:solid 1px;height:250px;margin:5px;">
            Location Attributes<br>

            <s:iterator value="locInfo.locationAttributes">
                <button dojoType="dijit.form.ToggleButton" style="width:100px;"
                                                           <s:if test="value==1">checked</s:if>
                                                           onChange="console.log('toggled button checked='+arguments[0] +<s:property value="id"/>);"
                                                           iconClass="dijitCheckBoxIcon">
                    <s:property value="name"/>
                </button>

                <br>

            </s:iterator>

        </div>
      <!--
        <div dojoType="dijit.layout.ContentPane" id="InvAttribs" title="InvAttribs"
             style="border:solid 1px;height:200px;margin:5px;">
            Location Attributes<br>

            <s:iterator value="locInfo.inventoryAttributes">
                <label for="INV-<s:property value="id"/>"><s:property value="name"/></label>
                <input dojoType="dijit.form.TextBox" id="INV-<s:property value="id"/>" name="<s:property
                    value="id"/>" type="text" value="<s:property value="value"/>"
                <s:if test="locInfo.hasDirectInventory==false"> Disabled >

                    <s:else>
                        >
                    </s:else>
                </s:if>


                <br>

            </s:iterator>

        </div>
           -->
        <s:if test="locInfo.hasDirectInventory==false">
            <span dojoType="dijit.Tooltip" connectId="InvAttribs" style="display:none;">Location Inventory Attributes are disabled because there is no inventory assisnged directly to this location</span>
        </s:if>
    </div>

    <div dojoType="dijit.layout.ContentPane" title="Child Locations">

        <s:if test="locInfo.allowedChildren.size>0">
            <div dojoType="dijit.layout.ContentPane" id="newLoc" style="border:solid 1px">
                Add new location:
                <table class="newLoc">
                    <tr>
                        <s:iterator value="locInfo.allowedChildren.entrySet()">
                            <td>
                                <button dojoType="dijit.form.Button" iconClass="plusIcon"
                                        onClick='showDialog("diag-<s:property value="key"/>","txt-<s:property value="key"/>")'>



                                    <s:property value="value"/>
                                </button>
                            </td>
                            <div id="diag-<s:property value="key"/>" dojoType="dijit.Dialog"
                                         title="Enter new location" style="display:none;">
                                        Please enter a Name for the new <s:property value="value"/>:
                                        <br>
                                        <input id="txt-<s:property value="key"/>" dojoType="dijit.form.TextBox">
                                        <button dojoType="dijit.form.Button"
                                                onClick="return LocationSave(<s:property value="locInfo.Id"/>,<s:property value="key"/>,'txt-<s:property value="key"/>','diag-<s:property value="key"/>');"
                                                type="submit">Save <s:property value="value"/></button> <br>
                                <span id="note">(<s:property value="value"/> will be appended to name)</span><br>        
                                <span id="diag-<s:property value="key"/>error" class="error">
                                          
                                        </span>

                                    </div>
                        </s:iterator>
                    </tr>
                </table>
                <div id="newmessage">&nbsp;</div>
                <div id="locationwizard">
                  <a href="/wms2/locationWizard/wizardstart.action?StartParentId=<s:property value="locInfo.Id"/>&OriginalType=<s:property value="locInfo.locationType"/>" target="_blank">
                        <button dojoType="dijit.form.Button" type="button">
   Use Location Wizard
</button>
                    </a>
                    </div>
                 <div id="externalwizard" dojoType="dijit.Dialog" title="Location Wizard" style="width:90%;height:90%;"
href="/wms2/locationWizard/wizardstart.action?StartParentId=<s:property value="locInfo.Id"/>&OriginalType=<s:property value="locInfo.locationType"/>" >

</div>
            </div>


        </s:if>


        <s:iterator value="locInfo.children.entrySet()">
            <s:property value="key"/>:&nbsp
          <a href="javascript:loadChildOnClick(<s:property value='locInfo.id'/>, <s:property value='key'/>)">
            <s:property value="value"/>
           </a>
            <br>

        </s:iterator>
    </div>

    <div dojoType="dijit.layout.ContentPane" title="Inventory">

        Eventually All Inventory in this location will show up here.


    </div>


</div>

