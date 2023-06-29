<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

 <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/buttonmeta.jsp" />
    <meta name="viewport" content="width=device-width, height=device-height, user-scalable=no target-densitydpi=high-dpi" />
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>



</head>
<body><div class="body"><TABLE width=100%><TR><TH ALIGN=LEFT>
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
<c:if test='${outcome != null}'>
<font color='blue'>
<b><c:out value='${outcome}'/></b>
</font>
<hr>
</c:if>
<table border="0"  cellspacing="0" cellpadding="0" frame="box">
<tr><td valign=top  >
<b>Move Item to Location: <c:out value='${loc}'/></b>
<HR ALIGN="center" SIZE="1" >
<html:form action="assignbatchlocation" focus="sku">
<bean:message key="label.batchlocations.scan"/><br>
<html:text property="sku" value="" styleId="autoSubmit"/><br>
<html:hidden property="location" value="${loc}"/>
<html:submit value="Confirm Item:"/>
</html:form>
<html:form action="assign"><html:submit><bean:message key="button.setnewlocation"/></html:submit></html:form>

<HR ALIGN="center" SIZE="1" >
</td></tr></table>
<c:if test='${remember != null}'>
Remembering <c:out value='${rememberclientname}'/>
<html:form action="clearRememberingClient">
<html:hidden property="sku" value="${loginName}"/>
<html:submit value="Clear Client"/>
</html:form>
</c:if>
</div></body>

</HTML>

