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
<c:if test='${outcome != null}'>
<font color='blue'>
<b><c:out value='${outcome}'/></b>
</font>
<hr>
</c:if>
<table border="0" width="100%" cellspacing="0" cellpadding="0" frame="box">
<tr><td valign=top  >
<b><bean:message key="text.printskustart"/></b><br>

<HR ALIGN="center" SIZE="1" >
<html:form action="printSkuStart" focus="sku">
<bean:message key="label.printsku.start"/><br>
<html:text property="sku" value="" styleId="autoSubmit"/><br>
<html:submit value="Submit"/>
</html:form>

<HR ALIGN="center" SIZE="1" >
</td></tr></table>
</div>
</body>
</HTML>