<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

 <META HTTP-Equiv="Scanner" content="disabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
<jsp:include page="/rfapps/buttonmeta.jsp" />

<div class="body">
<body><TABLE width=100%><TR><TH ALIGN=LEFT>
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

<table border="0" width="230" cellspacing="0" cellpadding="0" frame="box">
<tr><td valign=top>
<b>Doing Inventory for <br><font color="blue"><c:out value='${doinginventoryclientname}'/></font></b>  <br>
<b>Get Quanity for sku: </b>
<HR ALIGN="center" SIZE="1" color="green">
<center><b><font size="+1">
<font color="#01D709"><c:out value='${sku}'/></font><br>
<font color="#85147C"><c:out value='${invnum}'/></font><br>
<c:out value='${desc}'/><br>    </font></b>
</center>
<HR ALIGN="center" SIZE="1" color="green">
<html:form action="inventorygetquanity" focus="numlabels">
<bean:message key="label.inventorygetquanity.scan"/><br>
<html:text property="numlabels" styleId="autoSubmit" /><br>
<html:hidden property="sku" value='${sku}'/>
<html:hidden property="inventoryNum" value='${invnum}'/>
<html:submit value="SUBMIT"/>

</html:form>

<HR ALIGN="center" SIZE="1" >
</td></tr></table>
 <html:form action="doinventory"><html:submit value="CANCEL"/></html:form>
</BODY>
 </div>

</HTML>