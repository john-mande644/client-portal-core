<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

<head>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
    <style>
        .onhold {
            background-color: #FF0000;

            height: 200px;
            padding: 10px;
            width: 200px;
        }
        .good {
            background-color: #008000;

            height: 200px;
            padding: 10px;
            width: 200px;
        }
    </style>
</head>
<body>

<a href="/wms/miscMenu/start.action" class="button">MENU</a> <P class="button>">
    <s:if test="hasActionErrors()">
<div id="errorDiv" style="padding-left: 10px; margin-bottom: 5px">
     <span class="error">
     <s:iterator value="actionErrors">
         <span class="errorMessage"><s:property escape="false" /></span>
     </s:iterator>
     </span>
</div>
</s:if>
<s:actionmessage/>





    <s:if test="goodLocation">

        <s:form action="set.action">
            <s:property value="lib.name"/><br>
            Scan Locatoin this should be at. <br>
            <s:textfield name="newParentLocation" id="autoSubmit" value=""/>
            <s:hidden name="location"/>
            <s:submit></s:submit>
        </s:form>



    </s:if>
    <s:else>
        <s:form action="location.action">
            Scan bin to check!! <br>
            <s:textfield name="location" id="autoSubmit" value=""/>
            <s:submit></s:submit>
        </s:form>
    </s:else>



</body>
</html>
