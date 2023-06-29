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
<div><h1>Please fill out the info to lookup and clear: </h1></div>
<div>
    <div class="error">
        <s:actionerror/>
    </div>
    <div class="message">
        <s:actionmessage/>
    </div>
    <s:form action="processRequest.action" theme="simple">
       <s:label for="email" value="Email address"/> <s:textfield name="email" value=""/><br><br>
        <s:label for="requestId" value="Request ID"/> <s:textfield name="requestId" value=""/><br>
        <s:submit label="Clear" value="Clear"/>
    </s:form>
</div>

<div>
    <hr>

</div>
</body>
</html>
