<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<META HTTP-Equiv="Scanner" content="Disabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">

<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/includes/androidStuff.jsp"/>

<body>
<div class="receive">
<c:if test='${error != null}'><B class="error">${error}</B>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>
<br>
</c:if>
<c:if test='${outcome != null}'>
<font color='blue' size="+2">
<b>${outcome}</b>
</font>
<hr size="3">
</c:if>
<span class="label">Verify Information<br></span>
<html:form action="receiveVerify">
<table>
<tr><td class="right">Quanity:</td><td class="right">${receiveForm.count}</td><td><html:submit property="submit"><bean:message key="button.editQuanity"/></html:submit></td></tr>
<tr><td class="right">Damaged:</td><td class="right">${receiveForm.damaged}</td><td><html:submit property="submit"><bean:message key="button.editDamaged"/></html:submit></td></tr>
<tr><td class="right">Weight:</td><td class="right">${receiveForm.weight}</td><td><html:submit property="submit"><bean:message key="button.editWeight"/></html:submit></td></tr>


<html:hidden property="weight"/>
<html:hidden property="count"/>
<html:hidden property="invId"/>
<html:hidden property="receiveItemId"/>
<html:hidden property="rcvId"/>
<html:hidden property="damaged"/>
<html:hidden property="doLabelPrint"/>

<tr><td><html:submit property="submit"><bean:message key="button.verify"/></html:submit></td></tr>

</html:form>
</table>
<br>
<span class="notes">VERIFY WHAT YOU ENTERED, EDIT IF NEEDED</span>
</div>
</body>
