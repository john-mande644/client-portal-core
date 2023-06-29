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
<div>
    Going to delete <s:property value="wloc.locationName"/>

    This is your final warning
                                Remember, is this not un-doable.<br>

    If you do this on accident and call IT we are going to laugh at you.<br>
    <br>
    And will mock you<br>

    <a href="/wms/advancedManagement/loadLocation.action?ixNode=<s:property value="ixNode"/>" class="button">CANCEL</a>
    <br><br><br>
    <s:form action="deleteLocationThree" theme="simple">
        <s:hidden name="ixNode"/>
        <s:hidden name="reloadIx"/>
        <s:submit value="Delete this and all Child Locations" onclick="return tryClear()"/>
    </s:form>



</div>


<script type="text/javascript">
    function tryClear(){
        s = prompt('Say goodbye. You may be mocked. To do so Enter 9137','Cancel Code');
        if (s == 9137){
            return true;
        }
        return false;
    }
</script>