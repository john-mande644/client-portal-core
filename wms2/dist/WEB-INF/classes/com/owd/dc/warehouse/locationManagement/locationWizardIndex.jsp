<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
 <head>
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

          dojo.require("dijit.Dialog");
              dojo.require("dijit.Tooltip");
       dojo.require("dijit.layout.TabContainer");
         dojo.require("dijit.form.TextBox");
        dojo.require("dijit.form.CheckBox");
           dojo.require("dojox.layout.ContentPane");
            dojo.require("dojo.lfx.*");
     function showDialogwizard(id,textField){


            dijit.byId(id).show();

			}

    </script>
 </head>

<body class="tundra">
<div class="errors"><s:actionerror/></div>

      <div class="WizardButtons">
          Please select the location type you would like to create.
          <br>
           <s:iterator value="allowedChildren.entrySet()">

                                <button dojoType="dijit.form.Button" iconClass="plusIcon" id="wizard<s:property value="key"/>"
                                        onClick='showDialogwizard("wizdiag-<s:property value="key"/>","wiztxt-<s:property value="key"/>")'>



                                    <s:property value="value"/>
                                </button>

                            <div id="wizdiag-<s:property value="key"/>" dojoType="dijit.Dialog"
                                         title="Enter new locations info" style="display:none;">
                                        Please enter info for the new <s:property value="value"/>s:
                                        <br>
                                        <s:form action="locationWizardAddToBean">
                                         <s:label for="doSequence" theme="simple">Do Sequence??</s:label><s:checkbox name="doSequence" theme="simple"/>
                                          <div id="sequence" class="sequence">            <br>
                                         <s:label for="numberToDo" theme="simple">How Many <s:property value="value"/>s?</s:label>

                                              <s:textfield name="numberToDo" theme="simple"/> <br>
                                          Starting From?     <s:textfield name="startNumber" theme="simple"/>    <br>
                                          </div>
                                          <div id="nosequence" class="nosequence">
                                              List of locations seperated by comma   <br>
                                           <s:textarea name="nameList" rows="15" cols="50" theme="simple"/>


                                          </div>
                                            <s:hidden name="type" value="%{key}"/>
                                             <s:hidden name="StartParentId" value="%{StartParentId}"/>
                                              <s:hidden name="typeString" value="%{value}"/>
                                            <s:submit/>

                                        </s:form>
                                <span id="note">(<s:property value="value"/> will be appended to names)</span><br>


                                        </span>

                                    </div>
                        </s:iterator>
      </div>
<div class="WizardBottom">

    <s:if test="canProcess">

    <s:iterator value="locationBeansList" status="itStatus">

           <div class="beanItem">
               <div class="itemData">
              These <s:property value="typeString"/>'s will be created.<br>
               <s:iterator value="locationsToCreateList">
                <s:property />,
               </s:iterator>
                   </div>
               <div class="removeButton">
                      <s:form action="locationWizardRemoveFromBean" theme="simple">
                    <s:hidden name="removeIndex" value="%{#itStatus.index}"/>


                         <s:submit value="Remove" theme="simple"/>
                   </s:form>
                   </div>

           </div>

    </s:iterator>
        <s:form action="locationWizardClearBean" theme="simple">
            <s:submit value="Clear Locations" theme="simple"/>
        </s:form>
        <s:form action="locationWizardCreateLocations" theme="simple">
            <s:submit value="Create Locations" theme="simple"/>
        </s:form>
    </s:if>


</div>
</body>
</html>