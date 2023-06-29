<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
</head>
<body>


<a href="/wms/do/loc-menu" class="button">Locations</a>

<div>
    <h1>Please select the Pick Priority you want to set</h1>
   <s:form action="pickPrioritySet">
       <s:hidden name="priority" value="1"/>
       <s:submit label="Primary" value="Primary"/>
   </s:form>
    <s:form action="pickPrioritySet">
        <s:hidden name="priority" value="2"/>
        <s:submit label="Secondary" value="Secondary"/>
    </s:form>
    <s:form action="pickPrioritySet">
        <s:hidden name="priority" value="3"/>
        <s:submit label="Default" value="Default"/>
    </s:form>
</div>
</body>
</html>