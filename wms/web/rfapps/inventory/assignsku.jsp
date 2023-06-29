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
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>


</head>
<body>
<div class="body"><TABLE width=100%><TR><TH ALIGN=LEFT>

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
<table border="0" width="100%" cellspacing="0" cellpadding="0" frame="box">
<tr><td valign=top  >
<b><bean:message key="text.assingsku"/></b><br>
<c:out value='${chkbarcode}'/>
<HR ALIGN="center" SIZE="1" >
<html:form action="assignsku" focus="invID">
<bean:message key="label.upc.assign"/><br>
<html:text property="invID" value="" styleId="autoSubmit"/><br>
<html:hidden property="barcode" value="${chkbarcode}"/>
<html:submit><bean:message key="button.upcassign"/></html:submit>
</html:form>

<HR ALIGN="center" SIZE="1" >
</td></tr></table>
</div>

</BODY>
</HTML>