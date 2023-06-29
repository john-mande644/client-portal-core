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
<div class="messages">

    <s:actionmessage/>
</div>
<div class="locationInfo">
    <a href="/wms/do/loc-menu" class="button">Menu</a>
    <br>
    We are working in <s:property value="currentlocationName"/>
    <s:if test="showDelete == true">
<br>
    If you are here. We trust you. Please use these buttons wisely.
        <br>
There are <s:property value="assignedLocations"/> assigned items in this location and children.
        <br>

        <s:form action="clearInventoryOne" theme="simple">
            <s:hidden name="ixNode"/>
            <s:submit value="Clear All Assigned Inventory"/>
        </s:form>


        <s:if test="assignedLocations == 0">
            <br><br><br>
            <s:form action="deleteLocationOne" theme="simple">
                <s:hidden name="ixNode"/>
                <s:submit value="Delete this and all Child Locations"/>
            </s:form>
        </s:if>
    </s:if>
</div>
<div class="locationList">
<hr>
 Select location to load<br>
    <s:iterator value="childMap">

      <a href="/wms/advancedManagement/loadLocation.action?ixNode=<s:property value="value"/>" class="button">
          <s:property value="key"/>
      </a>
        <br>
    </s:iterator>

</div>

</body>
</html>