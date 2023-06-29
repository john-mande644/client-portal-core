<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

    <META HTTP-Equiv="Scanner" content="Enabled">
    <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
    <META HTTP-Equiv="Scanner" content="AutoEnter">
    <jsp:include page="/rfapps/buttonmeta.jsp" />
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>



</head>
<body><div class="body"><TABLE width=100%><TR><TH ALIGN=LEFT>

<html:form action="inv-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit><c:out value='${loginName}'/></html:form>
</TH><TH ALIGN=RIGHT>
</TH></TR></TABLE>
<HR>
<c:if test='${error != null}'>
     <script>
         try{
             javautil.playErrorSound();
         }catch(err){

         }
     </script>
<B class="error">${error}</B>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>
<br>
</c:if>


<b><font size=+1><bean:message key="text.upcfinish"/></font></b><br>
<table border="0" width="100%" cellspacing="0" cellpadding="0" frame="box">
<tr ALIGN="center">
<TD><B><font size=+4><span class="inventorynum"><c:out value='${skuinfo.inventoryNum}'/></span>
<BR><span class="inventoryid"><c:out value='${skuinfo.inventoryId}'/></span>
<BR><span class="description"><c:out value='${skuinfo.description}'/></span></font></b></td></tr>
<tr><td valign=top>


<HR ALIGN="center" SIZE="1" >
<html:form action="upcAssignFinish" focus="barcode">
<bean:message key="label.upc.finish"/><br>
<html:text property="barcode" value="" styleId="autoSubmit"/><br>
<html:hidden property="invID" value="${skuinfo.inventoryId}"/>
<html:submit><bean:message key="button.upcassign"/></html:submit>
</html:form>

<%-- Sean 2020/01/09 case 736499 10 key keybaord--%>
<%-- <form name="upcAssignFinishForm" method="post" action="upcAssignFinish"> --%>
    <%-- <span>UPC or ISBN:</span><br> --%>
    <%-- <input type="number" name="barcode" autofocus value="" id="autoSubmit"> <br> --%>
    <%-- <input type="submit" value="Submit" class="submit"> --%>
<%-- </form> --%>

<HR ALIGN="center" SIZE="1" >
<html:form action="upcisbnstart"><html:submit><bean:message key="button.cancel"/></html:submit></html:form> 
</td></tr></table>
</div></body>

</HTML>