<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
     <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoTab">
<link href="<html:rewrite page="/rfapps/computer.css" />" rel="stylesheet" type="text/css" media="screen">
<link href="<html:rewrite page="/rfapps/handhelds.css" />" rel="stylesheet" type="text/css" media="handheld">
</head>
<body>
<!--<html:link action="inv-menu" styleClass="button">Inventory Menu</html:link> -->
<span class="error"><s:actionerror/> </span>
<s:actionmessage/>



<hr>



<span class="label">
We are Counting this sku:
    <br><s:property value="invInfo.inventoryNum"/><br>
   Inv Id:<s:property value="invInfo.inventoryId"/><br>
    <s:property value="invInfo.description"/><br>


</span>
<br>
<s:form action="countThisLocation">
 Scan Location To Count:  <html:text property="locScan" value="" />
    <s:hidden name="cycleCountId"/>
     <s:hidden name="invInfo.inventoryNum"/>
    <s:hidden name="invInfo.inventoryId"/>
    <s:hidden name="invInfo.description"/>
    <s:hidden name="canVerify"/>
       <html:submit/>

</s:form>
  <br>


<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms["countThisLocation"].elements["locScan"];

  if (focusControl.type != "hidden" && !focusControl.disabled) {
     focusControl.focus();
  }
  // -->
</script>
  <hr>
<span class="label">Location you need to count</span>  <br>

  <s:iterator value="wccl">
      <s:property value="locationDisplay"/>  <br>


  </s:iterator>

</body>

</html>