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
<div><h1>Please enter in the order you need to Short Ship: </h1></div>
<div>
  <div class="error">
  <s:actionerror/>
  </div>
  <div class="message">
    <s:actionmessage/>
  </div>
  <s:form action="loadOrder.action" theme="simple">
    <s:textfield name="orderNumber"/>
    <s:submit label="Load Order" value="LoadOrder"/>
  </s:form>
</div>
<s:if test="%{packingUrl.length()>0}">
  <h3>New Packing Slip can be downloaded <a href="<s:property value="packingUrl"/>">HERE!!</a></h3>
</s:if>

<div>
  <hr>
  <s:form action="viewQueue.action" theme="simple">
    <s:submit label="View Queue" value="View Queue"/>
  </s:form>
</div>
</body>
</html>
