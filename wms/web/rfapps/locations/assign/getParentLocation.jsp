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
<body><div class="body">
<TABLE width=100%><TR><TH ALIGN=LEFT>
<html:form action="loc-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit><c:out value='${loginName}'/></html:form>

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
<tr><td valign=top  >
 <b>We need to get a valid parent location for ${lib.name}</b>
<HR ALIGN="center" SIZE="1" >
<html:form action="assignsinglelocation" focus="newParentId">
Scan Parent Location for ${lib.name}<br>
<html:text property="newParentId" value="" styleId="autoSubmit"/><br>
<html:hidden property="location" value="${location}" />
<html:hidden property="sku" value="${skuInfo.inventoryId}"/>
    <html:hidden property="primary" value="${primary}"/>
<html:submit value="Set Location:"/>
</html:form>
 <b>The following will be added to ${lib.name}
     <br><span class="inventoryid"><c:out value='${skuInfo.inventoryId}'/></span></b>
<BR><span class="inventorynum"><c:out value='${skuInfo.inventoryNum}'/></span>
<BR><c:out value='${skuInfo.description}'/>
<HR ALIGN="center" SIZE="1" >
</td></tr></table>
</div></body>

</HTML>

