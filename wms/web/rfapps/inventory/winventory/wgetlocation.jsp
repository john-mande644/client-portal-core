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

<c:out value='${loginName}'/>
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
<c:if test='${inventoryAdmin == "yes"}'>
  <a href="/wms/wInventory/addtoclosed.action?currentCount=${doingwinventoryid}" class="button">Add to Closed</a><P class="button">
 </c:if>
<center>Doing Count for <br>${doingwinventoryclientname}</center> <br>
<span class="label">Scan Location to Start Count</span><br>
<HR ALIGN="center" SIZE="1" lenght="50">
<html:form action="wgetLocation" focus ="location">
<html:text property="location" value="" styleId="autoSubmit"/>
<html:hidden property="requestId" value='${doingwinventoryid}'/>
<html:submit value="Start Doing Inventory"/>
</html:form>


<HR ALIGN="center" SIZE="1" >
</td></tr></table>
    Top 25 Open Locations
<c:forEach var="item" items="${wlocationsforrequest}">
 <font size="+1"><span class="location">  ${item.location} </span></font>
  <br>
</c:forEach>
<html:form action="donewinventory"><html:submit value="DONE"/></html:form>
<br>

</div>
</BODY>
</HTML>