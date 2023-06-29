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
<c:if test ='${wcb != null}'>
Counted ${wcb.quanity} for ${wcb.inventoryId}<br>
<html:form action="editWcount">
<html:hidden property="wCountId" value='${wcb.wCountId}'/>
<html:hidden property="requestId" value='${wCountForm.requestId}'/>
<html:hidden property="location" value='${wCountForm.location}'/>
<html:submit value="Edit Quanity"/>
</html:form>
<hr size = "3">
</c:if>
Counting for location ${locationName}<p>
<span class="label">Enter ID Number to start</span><br>

<HR ALIGN="center" SIZE="1" >
<html:form action="wgetSku" focus="inventoryId">
<html:text property="inventoryId" value="" styleId="autoSubmit"/>
<html:hidden property="requestId"/>
<html:hidden property="location"/>
<html:hidden property="prevId"/>
<html:submit value="Submit"/>
</html:form>


<HR ALIGN="center" SIZE="1" >
</td></tr></table>
<span class="label">Scan Location Again When All Counted</span>
<table>
<c:forEach var="item" items="${wskusinlocation}">
 <tr><td width="50%">${item.uncounted}</td><td><font color="red">${item.counted}</font></td></tr>


</c:forEach>
</table>
<br>

</div>
</BODY>
</HTML>