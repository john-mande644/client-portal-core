<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
</head>
<body>


<a href="/wms/locationManagement/pickPriorityStart.action" class="button">Back</a>
 <div class="errors">
     <s:if test="hasActionErrors()">
         <s:iterator value="actionErrors">
<span class="errorMessage"><s:property escape="false" />
</span>
         </s:iterator>
     </s:if>

 </div>

<div>

    <h1>Please scan the locations you<br>
        want to set to <s:property value="%{priorityText[priority]}"/> </h1>
    <s:form action="pickPriorityLocationScan">
        <s:hidden name="priority"/>
        <s:textfield name="locationScan" id="autoSubmit" value=""/>
        <s:submit label="Scan"/>
    </s:form>

</div>

<div class="message">
    <s:actionmessage/>
</div>
</body>
<script type="text/javascript">
document.getElementById('autoSubmit').focus();

</script>
</html>