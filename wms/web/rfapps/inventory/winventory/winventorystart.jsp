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

</head> <body>
<div class="body">
<TABLE width=100%><TR><TH ALIGN=LEFT>

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
<span class="label">Enter Inventory Count Working On</span><br>
<HR ALIGN="center" SIZE="1" >
<html:form action="wstartInventory" focus="sku">
<html:text property="sku" styleId="autoSubmit"/>
<html:submit value="Start Doing Inventory"/>
</html:form>


<HR ALIGN="center" SIZE="1" >
</td></tr></table>
<c:forEach var="item" items="${wopenrequests}">
  ${item.id} ${item.client} ${item.description}
  <br>
</c:forEach>
<html:form action="winventoryRequestStart">
<html:submit value="Create New Request"/>
</html:form>
<br>

</div>
</BODY>
</HTML>