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
<font color='blue' size="+4">
<c:out value='${outcome}'/>
</font>
<hr>
</c:if>
<table border="0" width="100%" cellspacing="0" cellpadding="0" frame="box">
<tr><td valign=top  >
<b><bean:message key="text.upccheck"/></b><br>

<HR ALIGN="center" SIZE="1" >
<%-- <html:form action="checkupc" focus="barcode"> --%>
<%-- <bean:message key="label.upc.check"/><br> --%>
<%-- <html:text property="barcode" value="" styleId="autoSubmit"/><br> --%>
<%-- <html:submit value="Submit"/> --%>
<%-- </html:form> --%>

<%-- Sean 2020/01/09 case 736499 10 key keybaord--%>
<form name="checkupcForm" method="post" action="checkupc">
    <span>UPC or ISBN:</span><br>
    <input type="number" name="barcode" autofocus value="" id="autoSubmit"> <br>
    <input type="submit" value="Submit" class="submit">
</form>

<HR ALIGN="center" SIZE="1" >
</td></tr></table>
</div>
</BODY>
</HTML>