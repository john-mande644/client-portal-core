<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
     <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
</head>
<body>

<span class="error"><s:actionerror/> </span>
<s:actionmessage/>
 <a href="/wms/locationManagement/start.action" class="button">Back</a>
<hr>
Location Name:  <s:property value="lib.name"/>  <br>



Please Scan New Parent Location:
<s:form action="moveLocationComplete">
   <s:hidden name="location"/>
     Scan: <s:textfield name="newParentLocation" id="autoSubmit" />
    <br>
    <s:submit/>
</s:form>
<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms["moveLocationComplete"].elements["newParentLocation"];

  if (focusControl.type != "hidden" && !focusControl.disabled) {
     focusControl.focus();
  }
  // -->
</script>
  <hr>
These are valid Location Types to Move To:<br>
    <s:iterator value="lib.validParentLocationTypes">
                 <span class="label"> <s:property/></span>    <br>
   </s:iterator>
        </body>
</html>
