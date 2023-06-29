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

<span class="inventoryid">${ib.inventoryId}<br></span>
<span class="inventorynum">${ib.inventoryNum}</span>
<br>${ib.description} <br>
<span class="label">If you Find this Sku, scan it</span><br>

<HR ALIGN="center" SIZE="1" >
<html:form action="stockoutGetSku" focus="inventoryId">
<html:text property="inventoryId" value="" styleId="autoSubmit"/>
<html:hidden property="location"/>
<html:hidden property="sLocation"/>
<html:hidden property="sInventoryId"/>
<html:hidden property="clientFkey"/>
<html:hidden property="id"/>
<html:submit value="Submit"/>
</html:form>


<HR ALIGN="center" SIZE="1" >

</td></tr></table>
Enter 0 if the item is not found.
</div>
</BODY>
</html>