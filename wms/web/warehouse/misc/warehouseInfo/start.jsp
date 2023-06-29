<%@ taglib prefix="s" uri="/struts-tags" %>
<html>


<head>

</head>
<body>
<h1><s:property value="companyName"/> Info for <s:property value="facility"/></h1>
<s:form action="start">
    <s:select list="locations" label="Facility" name="facility"/>

    <s:submit/>
</s:form>
<div class="links">

    <a href="/wms/boxCounts/getCount.action?facility=<s:property value="facility"/>&sla=<s:property value="today"/>&report=Candles" target="_blank">Candle Box Counts</a>
    <br>
    <a href= "/wms/warehouseInfo/skuLoadInfo.action?facility=<s:property value="facility"/>&clientId=489&groupName=diamondcandles&caseQty=20&palletQty=560" target="_blank">Candle Inventory Needed</a>
    <br>
    <a href= "/wms/warehouseInfo/skuLoadInfo.action?facility=<s:property value="facility"/>&clientId=489&groupName=fijiwater&caseQty=1&palletQty=999" target="_blank">Fiji Water Inventory Needed</a>
</div>
<div class="slaInfo">
    <h3> Un Shipped orders in warehosue</h3>
    <table>
        <thead>
        <th>Count</th>
        <th>Client</th>
        <th>SLA</th>
        </thead>
    <s:iterator value="slas">
     <tr>
        <td>
            <s:property value="count"/>
        </td>
         <td>
             <s:property value="Client"/>
         </td>
         <td>
             <s:property value="sla"/>
         </td>
      </tr>
    </s:iterator>
    </table>
</div>
<div class="slaInfo2">
    <h3>Un Picked orders in warehosue</h3>
    <table>
        <thead>
        <th>Count</th>
        <th>Client</th>
        <th>SLA</th>
        </thead>
        <s:iterator value="unpickSlas">
        <tr>
            <td>
                <s:property value="count"/>
            </td>
            <td>
                <s:property value="Client"/>
            </td>
            <td>
                <s:property value="sla"/>
            </td>
        </tr>
        </s:iterator>
    </table>
</div>
</body>
</html>