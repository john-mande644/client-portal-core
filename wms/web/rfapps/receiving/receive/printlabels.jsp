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
<span class="label">Print SKU labes for Items without Barcode<br></span>
<center>
<span class="inventoryid">${ib.inventoryId}<br></span>
<span class="inventorynum">${ib.inventoryNum}</span>
<br>${ib.description}

</center>
<table>
<html:form action="receivePrintLabels" focus="labelCount">

<html:hidden property="weight"/>

<html:hidden property="invId"/>
<html:hidden property="receiveItemId"/>
<html:hidden property="rcvId"/>
<html:hidden property="damaged"/>
<html:hidden property="action"/>
<html:hidden property="doLabelPrint"/>

<tr><td><html:text property="labelCount" value="1"/></td></tr>
<tr><td><html:submit property="submit"><bean:message key="button.printLabel"/></html:submit></td></tr>

<tr> <td><br><html:submit property="submit"><bean:message key="button.printLargeLabel"/></html:submit></td></tr>
    <tr> <td><br><html:submit property="submit"><bean:message key="button.palletTag"/></html:submit></td></tr>
    </tr>

<tr><td><br><html:submit property="submit"><bean:message key="button.noPrint"/></html:submit></td></tr>
</html:form>
</table>
<br>

</div>
</body>
<script>
    try{
        document.forms['receiveForm'].elements['labelCount'].type = "number";
    }catch(err){

    }
</script>
</html>