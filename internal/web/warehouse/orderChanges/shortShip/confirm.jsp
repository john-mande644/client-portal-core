<%@ taglib prefix="s" uri="/struts-tags" %>

<%--
  Created by IntelliJ IDEA.
  User: danny
  Date: 10/2/2019
  Time: 3:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Short Ship</title>
  <style>
    .label{
      font-weight:bold;
    }
    .cancel{
      float:right;
    }

    .error{
    color:red;
    }
    .lines{
      width:100%;
      border-collapse: collapse;
    }
    .lines td{
      text-align:center;
      padding:5px;
    }
    .row-sku{
      width:20%;
    }
    .row-description{
      width:30%;
    }
    .row-qty{
      width:10%;
    }
    .row-short{
      width:10%;
    }
    .row-reason{
      width:10%;
    }
    .oddrow {
      background-color: lightgrey;
    }
    .evenrow{

    }
    .infoConfirm{
      text-align: center;
      background-color: lightblue;

    }
  </style>
</head>
<body>
<div class="cancel"><s:form action="start.action"><s:submit label="cancel" value="Cancel"/></s:form></div>
<div class="error"><s:actionerror/></div>
<div><h3>Order Info</h3></div>
<div class="orderInfo">
  <span class="label">Order Number:</span> <s:property value="order.orderNum"/><br/>
  <span class="label">Client RefNum:</span> <s:property value="order.orderRefnum"/><br/>
  <span class="label">Client Name:</span> <s:property value="order.client.companyName"/><br/>
  <span class="label">Group:</span> <s:property value="order.groupName"/>
</div>
<div>
  <div class="infoConfirm"><h2>Please confirm info below and submit.</h2></div>
  <s:form action="submitShortShip.action" theme="simple">
    <s:hidden name="orderId" value="%{order.orderId}"/>
    <s:hidden name="clientId" value="%{order.clientFkey}"/>
    <s:hidden name="user" value="%{user}"/>
    <table class="lines"><thead><tr>
      <th class="row-1 row-sku">Sku</th><th class="row-2 row-description">Description</th><th class="row-3 row-qty">Quantity</th><th class="row-reason">Reason</th><th class="row-4 row-short">Removed Quantity</th>
    </tr>
    </thead>
      <s:iterator value="confirmLinesList" status="status" >
        <s:if test="#status.odd == true">
          <tr class="oddrow">
        </s:if>
        <s:else>
          <tr class="evenrow">
        </s:else>

        <td><s:property value="inventoryNum"/></td>
        <td><s:property value="description"/></td>
        <td><s:property value="quantityActual"/></td>
        <td><s:select list="reasons" name="editLines[%{#status.index}].reason" value="%{reason}"/></td>
        <td><s:hidden name="editLines[%{#status.index}].lineId" value="%{lineId}" theme="simple"/><s:textfield name="editLines[%{#status.index}].qty" theme="simple" size="5" value="%{removeQty}"/> </td>
        </tr>


      </s:iterator>
    </table>



    <s:submit onclick="return confirm('Are you sure you want to continue')"/>
  </s:form>
</div>
</body>
</html>
