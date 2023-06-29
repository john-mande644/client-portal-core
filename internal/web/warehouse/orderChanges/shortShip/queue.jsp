<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Short Ship page</title>

    <style>
        .error{
            color:red;
        }
        .message{
            color: green;
        }
    </style>
</head>
<body>
<div><h1>Viewing Queue for <s:property value="facility"/> </h1>
<br> <s:form action="viewQueue.action" theme="simple">
        <s:submit label="Refresh" value="Refresh"/>
    </s:form></div>
<div>
    <div class="error">
        <s:actionerror/>
    </div>
    <div class="message">
        <s:actionmessage/>
    </div>
    <table>

<tr><TH>OrderNum</TH><th>Customer Name</th><th>Client</th><th>download</th></tr>
<s:iterator value="queue" status="status" >
    <tr>
        <td><s:property value="order.orderNum"/> </td>
        <td><s:property value="customerName"/></td>
        <td><s:property value="client"/></td>
        <td><s:form action="download.action" theme="simple">
            <s:hidden name="queueId"/>
            <s:submit label="Download" value="Download"/>
        </s:form> </td>
    </tr>


    </s:iterator>
    </table>
</div>

<div>
    <hr>
    <s:form action="start.action" theme="simple">
        <s:submit label="Back" value="Back"/>
    </s:form>
</div>
</body>
</html>
