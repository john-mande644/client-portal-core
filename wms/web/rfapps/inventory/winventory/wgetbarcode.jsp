<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

 <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
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
<h2>Counting Sku: <br>
<center>
<span class="inventoryid">${wCountForm.inventoryId}<br></span>
<span class="inventorynum">${ib.inventoryNum}</span>
<br>${ib.description}
</center>            </h2>
<span class="label">Verify Item Info, Then Scan UPC</span>
<HR ALIGN="center" SIZE="1" >
<html:form action="wgetBarcode" focus="barcode">
<html:text property="barcode" value="" size="13" styleId="autoSubmit"/>
<html:hidden property="inventoryId"/>
<html:hidden property="invLocFkey"/>
<html:hidden property="requestId"/>
<html:hidden property="location"/>
<html:submit value="Submit"/>
</html:form>


<HR ALIGN="center" SIZE="1" >
</td></tr></table>
<font size="+1">Enter 0 if the Item Does not have a UPC!! </font>
<br>

</div>
</BODY>
</HTML>