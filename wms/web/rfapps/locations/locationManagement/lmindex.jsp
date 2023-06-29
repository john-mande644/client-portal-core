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
<html:link action="loc-menu" styleClass="button">Locations</html:link>
<span class="error"><s:actionerror/> </span>
<s:actionmessage/>


<hr>
<span class="label">
Scan Location to Manage</span>
<br>
<s:form action="loadLocation">
 Scan: <html:text property="location" value="" styleId="autoSubmit"/>
    <br>
    <html:submit/>
</s:form>
<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms["loadLocation"].elements["location"];

  if (focusControl.type != "hidden" && !focusControl.disabled) {
     focusControl.focus();
  }
  // -->
</script>


</body>
</html>
