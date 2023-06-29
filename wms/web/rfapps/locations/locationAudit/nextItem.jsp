<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
</head>
<body>
<div class="errors">
    <s:if test="hasActionErrors()">
        <script>
            try{
                javautil.playErrorSound();
            }catch(err){

            }
        </script>
        <s:iterator value="actionErrors">
<span class="errorMessage"><s:property escape="false" />
</span>
        </s:iterator>
    </s:if>


</div>

<div>
    <h1>Scan Inventory in <s:property value="locationString" escape="false"/> </h1>
    <s:form action="scanItem">

        <s:hidden name="employeeId"/>
        <s:hidden name="locationString"/>
        <s:hidden name="empty"/>
        <s:hidden name="ixNode"/>
        <s:textfield name="inventoryId"  id="autoSubmit" value=""/>
        <s:submit/>
    </s:form>


</div>
<s:form action="scanItem" theme="simple">
    <s:hidden name="employeeId"/>
    <s:hidden name="inventoryId" value="//done"/>
    <s:hidden name="ixNode"/>
    <s:hidden name="empty"/>
    <s:submit label="Done" value="Done"/>
</s:form>
<s:actionmessage/>

</body>
</html>

