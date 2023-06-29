<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

 <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/buttonmeta.jsp" />
 <link href="<html:rewrite page="/rfapps/computer.css" />" rel="stylesheet" type="text/css" media="screen">
<link href="<html:rewrite page="/rfapps/handhelds.css" />" rel="stylesheet" type="text/css" media="handheld">

</head>
<body>

<center>
  ${skuInfo.inventoryNum}<br>
    ${skuInfo.description}
</center>
Enter quantity
<html:form action="useSupply" focus="sku">
    <html:text property="numlabels" size="10"/>
    <html:hidden property="sku"/>
    <html:submit property="submit"><bean:message key="button.use"/></html:submit>
</html:form>


</body>
</html>
