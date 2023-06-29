<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: danny
  Date: 10/29/14
  Time: 6:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  These are what will be printed
<s:form action="printItems">
    <s:hidden name="asnId"/>
    <s:hidden name="includeUPC"/>
    <s:hidden name="qtyLeft"/>
    <s:submit label="Print"/>
</s:form>
 <table>
     <tr>
         <thead>
         <th>Inventory ID</th>
         <th>Inventory Number</th>
         <th>Qty</th>
         </thead>
     </tr>

<s:iterator value="items">
     <tr>
         <td><s:property value="inventoryId"/></td>
         <td><s:property value="inventoryNum"/></td>
         <td><s:property value="countToPrint"/></td>
     </tr>

</s:iterator>
 </table>
</body>
</html>