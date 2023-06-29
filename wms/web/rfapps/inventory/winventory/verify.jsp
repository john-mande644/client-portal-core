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
<!-- <html:link action="loc-menu" styleClass="button">Locations</html:link> -->
<span class="error"><s:actionerror/> </span>
<s:actionmessage/>


<hr>
<span class="label">
    Here is what we are going todo
     <br>
    Adding ${qty}
    <br> of ${invName} <br>
    to ${location}


</span>
<br>
<s:form action="insertInfo">
 <s:hidden name="location" value="%{location}" />
     <s:hidden name="inventoryId" value="%{inventoryId}" />
     <s:hidden name="qty" value="%{qty}" />
     <s:hidden name="invName" value="%{invName}" />
     <s:hidden name="currentCount" value="%{currentCount}"/>

    <html:submit/>

</s:form>
  <br>

<form name="skuForm" method="post" action="/wms/do/wstartInventory">

    <s:hidden name="sku" value="%{currentCount}"/>
    <s:submit value="Cancel"/>
</form>


</body>
</html>