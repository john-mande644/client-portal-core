<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
     <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
 </head>
<body>

<span class="error"><s:actionerror/> </span>
<s:actionmessage/>
 <a href="/wms/locationManagement/start.action" class="button">Back</a>
<hr>
Location Name:  <span class="label"><s:property value="lib.name"/> </span> <br>
Location Pick String: <span class="label"><s:property value="lib.pickString" escape="false"/></span><br>
Pick Priority: <span class="label"><s:property value="lib.priority" escape="false"/> </span>  <br>


Inventory in Location:
    <s:iterator value="lib.inventoryPrimaryLocationList">
    <table class="lmtable">
<thead>
<th>
    ID
</th>
<th>
    Num
    </th>

<tr>
    <td><s:property value="inventoryId"/></td>
     <td><s:property value="inventoryNum"/></td>
   </tr><tr>
    <th>


</th>
<th>

</th>
    </tr><tr>
     <td></td>
    <td></td>
    </tr><tr>
     <td>
      <!--   <s:if test="primary == false">
             <s:form action="setPrimary">
                 <s:hidden name="location"/>
                 <s:hidden name="inventoryId" value="%{inventoryId}"/>
                 <s:submit value="Set As Primary"/>

             </s:form>
         </s:if>-->
     </td>
     <td>
         <s:form action="removeInventory">
             <s:hidden name="location"/>
              <s:hidden name="inventoryId" value="%{inventoryId}"/>
                 <s:submit value="Remove Inventory" onclick="return confirmRemove()"/>
         </s:form>
     </td>

</tr>
  <tr><td colspan="2"><hr></td></tr>
</thead>
  </table>
</s:iterator>

 <table>
   <tr>
       <td>
           <s:form action="moveLocation">
              <s:hidden name="location"/>
               <s:submit value="Move Location" onclick="return confirmMove()"/>
           </s:form>
       </td>
       <td><s:if test="lib.inventoryPrimaryLocationList.size > 0">
         <s:form action="clearInventory">
             <s:hidden name="location"/>
              <s:hidden name="inventoryId" value="%{inventoryId}"/>
                 <s:submit value="Clear Location" onclick="return confirmClear()"/>
         </s:form>
  </s:if></td>
   </tr>
 </table>


  <script type="text/javascript">
  function confirmRemove()
{
return confirm("Are you sure you want to Remove This Item from this location?");

}
 function confirmClear()
{
return confirm("Are you sure you want to clear all inventory from this location?");

}
  function confirmMove()
{
return confirm("Are you sure you want to move this Location?");

}
  </script>
        </body>
</html>
