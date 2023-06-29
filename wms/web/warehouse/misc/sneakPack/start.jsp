<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

<head>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>

</head>
<body onload="document.getElementById('autoSubmit').focus();">

<a href="/wms/miscMenu/start.action" class="button">MENU</a> <P class="button>">      <s:property value="pickerId"/> is logged in.
    <s:if test="hasActionErrors()">
<div id="errorDiv" style="padding-left: 10px; margin-bottom: 5px">
    <script>
        try{
            javautil.playErrorSound();
        }catch(err){

        }
    </script>
     <span class="error">
     <s:iterator value="actionErrors">
         <span class="errorMessage"><s:property escape="false" /></span>
     </s:iterator>
     </span>
</div>
</s:if>
<s:actionmessage/>






        <s:form action="loadOrder.action">
            Scan order to begin!! <br>
            <s:textfield name="barcodeScan" id="autoSubmit" value="" />
            <s:hidden name="pickerId"/>
            <s:hidden name="printerName"/>
            <s:hidden name="printerUrl"/>
            <s:submit></s:submit>
        </s:form>
You will be printing to <s:property value="printerName"/><br>

        <a href="<s:url value="setupPrinter.action"/>?pickerId=<s:property value="pickerId"/> "/>Printer Setup</a>

       <br><br>
<a href="<s:url value="clearBoxes.action"/>?pickerId=<s:property value="pickerId"/> "/>Clear Remembered Boxes</a>

</body>
</html>
