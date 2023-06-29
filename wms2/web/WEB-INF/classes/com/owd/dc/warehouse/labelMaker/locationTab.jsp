<%@ taglib prefix="s" uri="/struts-tags" %>

<html>

<head>

</head>
<body>

	<div dojoType="dijit.layout.BorderContainer"  id="tabedMainContainer<s:property value="locId"/>" design="headline" style="width:90%">
    <div id="tabheader<s:property value="locId"/>" dojoType="dijit.layout.ContentPane" region="top">
       Location is <s:property value="locName"/>&nbsp;
        Quick filter:
        <select id = "select<s:property value="locId"/>" name="select<s:property value="locId" />" dojoType="dojox.form.DropDownSelect"
                onchange="loadLocTabSelect(<s:property value="locId"/>,'select<s:property value="locId"/>')">
               <option value="">Select locaiton to filter</option>
                <s:iterator value="childLocList.entrySet()">
                <option value="<s:property value="key"/>"><s:property value="value"/></option>
                    </s:iterator>

            </select>
          <button id="button<s:property value="locId"/>" dojoType="dijit.form.Button" onclick="printDirect('<s:property value="locId"/>','<s:property value="locName"/>')">Print Direct Children</button>
    </div>
    <div id="tabcenter<s:property value="locId"/>" dojoType="dijit.layout.ContentPane" region="center" class="overflow">
     <div id="form<s:property value="locId"/>" dojoType="dijit.form.Form" action="wms2/labelMaker/printRange.action">
        <s:iterator value="childLocList.entrySet()">
               <input type="checkbox" dojotype="dijit.form.CheckBox" id="locToPrint[<s:property value="key"/>]" name="locToPrint[<s:property value="key"/>]"/> 
            <a href="javascript:loadLocTab('<s:property value="locId"/>','<s:property value="key"/>','<s:property value="value"/>');" ><s:property value="value"/></a><br>
           
                    </s:iterator>
         <button id="print<s:property value="locId"/>" dojoType="dijit.form.Button" onclick="printRange(<s:property value="locId"/>)">Print Selected</button>
          <button id="printTree<s:property value="locId"/>" dojoType="dijit.form.Button" onclick="printTree(<s:property value="locId"/>)">Print Tree Selected</button>
       </div>
    </div>






    </div>



</body>
</html>