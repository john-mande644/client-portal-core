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
<html:form action="inv-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit><c:out value='${loginName}'/></html:form>
<c:if test="${null!=error}">
<span class="error">${error}</span>
    <hr>
</c:if>
<c:if test="${null!=outcome}">
   <span class="outcome">${outcome}</span>
    <hr>
</c:if>


Please scan the Supplies you are going to use.
<html:form action="useSupply" focus="sku">
    <html:text property="sku" size="15"/>
    <html:submit property="submit"><bean:message key="button.use1"/></html:submit>
</html:form>







</body>
</html>