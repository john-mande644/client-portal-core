<%@ page import="com.owd.web.internal.navigation.UIUtilities" %>
<%@ page import="org.apache.logging.log4j.Logger" %>
<%@ page import="org.apache.logging.log4j.LogManager" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="boxcode==null || boxcode.Id == null">
    <s:set name="title" value="%{'Add new box type'}"/>
</s:if>
<s:else>
    <s:set name="title" value="%{'Update box type'}"/>
</s:else>
<%

    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, request.getSession(true).getAttribute("owd_current_location")+" "+(String)pageContext.getAttribute("title"), request);
%>
<html>
<head>
    <link href="<s:url value='/stylesheets/boxcodes.css'/>" rel="stylesheet" type="text/css"/>
    <style>td {
        white-space: nowrap;
    }</style>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>
<s:url id="url" action="list" />
<div id="pagecommands" style="padding-top: 8px;">
    <h3>
        <a class="admin" href="<s:property value="#url"/>">Cancel and return to Box Types List</a>

    </h3>
</div>
</head>
<body>

<s:actionerror/>
<s:actionmessage/>

<s:form action="crud!save.action" method="post" >
    <s:textfield   name="boxcode.name" value="%{boxcode.name}" label="%{getText('boxcode.name')}" size="50"/>
    <s:textarea   name="boxcode.description" value="%{boxcode.description}" label="%{getText('boxcode.description')}" rows="5" cols="40"/>
    <s:select  label="%{getText('boxcode.owdClient')}" name="currentClientId"
              value="%{currentClientId}" list="clients" listKey="clientId" listValue="companyName"
              headerKey="-1" headerValue="Any Client"/>

    <s:select  label="Symphony Brand" name="boxcode.groupName" value="%{boxcode.groupName}" list="groupMap" />

    <s:select  label="%{getText('boxcode.csPackType')}" name="boxcode.type" value="%{boxcode.type}" list="packageTypes" />

    <s:textfield  name="boxcode.cost" value="%{boxcode.cost}" label="%{getText('boxcode.cost')}" size="8"/>
    <s:textfield   name="boxcode.weight" value="%{boxcode.weight}" label="%{getText('boxcode.weight')}" size="8"/>
    <s:textfield   name="boxcode.depth" value="%{boxcode.depth}" label="%{getText('boxcode.depth')}" size="8"/>
    <s:textfield  name="boxcode.width" value="%{boxcode.width}" label="%{getText('boxcode.width')}" size="8"/>
    <s:textfield  name="boxcode.height" value="%{boxcode.height}" label="%{getText('boxcode.height')}" size="8"/>
    <s:textfield name="currentInventoryId" value="%{currentInventoryId}" label="%{getText('boxcode.inventoryId')}" size="12"/>
    <s:select  label="Packaging Type" name="boxcode.packagingType" value="%{boxcode.packagingType}"
               list="packagingType" />
    <s:textfield  name="boxcode.barcode" value="%{boxcode.barcode}" label="Box Barcode"/>
    <s:checkbox name="boxcode.ShowOnBoxSealerStation" value="%{boxcode.ShowOnBoxSealerStation}" label="Show on Box Sealer Stations"/>
    <tr><td></td><td><s:if test="boxcode.owdInventory != null"><b><i>Current SKU:&nbsp;<s:property value="boxcode.owdInventory.inventoryNum"/>&nbsp;-&nbsp;<s:property value="boxcode.owdInventory.description"/></i></b></s:if></td></tr>
    <tr>
        <td colspan=2>
            <div class="CenterTableTitle">Allowed Ship Methods</div>
            <table>
                <tr>
                    <td align="left">
                        <s:iterator value="shipMethods" status="status">
                        <s:if test="#status.index==clientListColumnBreak">
                            </td>
                            <td align="left">
                        </s:if>
                        <s:checkbox theme="simple" label="%{value}" name="assignedShipMethod" fieldValue="%{key}" value="%{key in assignedShipMethods}"/>
                        <s:label theme="simple" value="%{value}"/>
                        <br>
                        </s:iterator>
                    </td>
                </tr>
            </table>
            <hr>
        </td>
    </tr>


    <s:hidden name="boxcode.Id" value="%{boxcode.Id}"/>
    <s:hidden name="boxcode.location" value="%{boxcode.location}"/>
    <s:submit value="%{getText('button.label.submit')}"/>
    <s:submit value="%{getText('button.label.cancel')}" name="redirect-action:list"/>
</s:form>
</body>
</html>
