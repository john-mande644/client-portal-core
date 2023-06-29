<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
     <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoTab">
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
 </head>
<body>
<!-- <html:link action="loc-menu" styleClass="button">Locations</html:link> -->
<span class="error"><s:actionerror/> </span>
<s:actionmessage/>


<hr>
<span class="label">
Scan Closed Location to Add item to

</span>
<br>
<s:form action="loadlocation">
 Location: <html:text property="location" value="" />
    <br>
    Item: <html:text property="inventoryId" value=""/>
    <br>
    Quantity: <html:text property="qty" value=""/>
      <s:hidden name="currentCount" value="%{currentCount}"/>
    <br>
    Please make sure these values are correct before you Submit.


    <br>

    <html:submit/>

</s:form>
  <br>

<form name="skuForm" method="post" action="/wms/do/wstartInventory">

    <s:hidden name="sku" value="%{currentCount}"/>
    <s:submit value="Cancel"/>
</form>
<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms["loadlocation"].elements["location"];

  if (focusControl.type != "hidden" && !focusControl.disabled) {
     focusControl.focus();
  }
  var entertext = true;

  // -->
</script>


</body>
</html>