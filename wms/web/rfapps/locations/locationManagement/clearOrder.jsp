<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <jsp:include page="/rfapps/includes/androidResponsive.jsp"/>
</head>
<body>


<a href="wms/locationManagement/orderMenu.action" class="button">Back</a>
<div class="errors">
    <s:if test="hasActionErrors()">
        <s:iterator value="actionErrors">
<span class="errorMessage"><s:property escape="false" />
</span>
        </s:iterator>
    </s:if>

</div>

<div>

    <h3>Please scan location you need to clear<br>
       </h3>
    <s:form action="clearOrderFromLocation">

        <s:textfield name="locationValue" id="autoSubmit" value=""/>
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