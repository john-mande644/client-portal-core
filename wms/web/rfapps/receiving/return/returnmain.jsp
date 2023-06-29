<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
 <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/buttonmeta.jsp" />
<link href="<html:rewrite page="/rfapps/receiving/return/return.css" />" rel="stylesheet" type="text/css">

</head><TABLE width=100%><TR><TH ALIGN=LEFT>

<html:form action="recv-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit><c:out value='${loginName}'/></html:form>

</TH><TH ALIGN=RIGHT>
</TH></TR></TABLE>
<HR>
<c:if test='${error != null}'>
<font color=red><B>
<c:out value='${error}'/></B></font>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>
</c:if>
<c:if test='${outcome != null}'>
<font color='blue'>
<b><c:out value='${outcome}'/></b>
</font>
<hr>
</c:if>

<table border="0" width="100%" cellspacing="0" cellpadding="0" frame="box">
<tr><td valign=top  >
<b>Enter Order Number for Return</b>
<HR ALIGN="center" SIZE="1" >
<html:form action="returnLoadOrder" focus="sku">
Scan or enter Order Number<br>
<html:text property="sku" value=""/><br>
<html:submit value="Submit"/>
</html:form>

<HR ALIGN="center" SIZE="1" >
</td></tr></table>

</BODY>
</HTML>