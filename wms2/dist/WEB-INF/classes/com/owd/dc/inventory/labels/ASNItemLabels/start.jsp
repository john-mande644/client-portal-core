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
   <s:form action="viewItems" >
      <s:textfield name="asnId" label="ASN ID"/>
           <s:checkbox name="includeUPC" label="Include Items with UPC codes"/>
       <s:checkbox name="qtyLeft" value="true" label="Print Qty Left to Receive"/>
       <s:submit/>
   </s:form>
</body>
</html>