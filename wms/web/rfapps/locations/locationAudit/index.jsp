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
<a href="/wms/do/loc-menu" class="button">Menu</a>
<div>
    <h1>Scan the location to Audit </h1>
    <s:form action="scanItem">
        <s:hidden name="employeeId"/>
        <s:textfield name="inventoryId"  id="autoSubmit" value=""/>

        <s:submit/>
    </s:form>

  <s:actionmessage/>

</div>
<br>



</body>
</html>

