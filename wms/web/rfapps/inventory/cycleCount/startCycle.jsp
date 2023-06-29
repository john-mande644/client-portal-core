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
<html:link action="inv-menu" styleClass="button">Inventory Menu</html:link>
<span class="error"><s:actionerror/> </span>
<s:actionmessage/>



<hr>



<span class="label">
Please Scan Item you want to count

</span>
<br>
<s:form action="countThisSku">
 Scan Inventory Item <html:text property="skuScan" value="" />
    <s:hidden name="canVerify"/>
       <html:submit/>

</s:form>
  <br>

<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms["countThisSku"].elements["skuScan"];

  if (focusControl.type != "hidden" && !focusControl.disabled) {
     focusControl.focus();
  }
  // -->
</script>


</body>

</html>