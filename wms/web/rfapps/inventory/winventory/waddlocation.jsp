<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/includes/androidStuff.jsp"/>
<body>
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
<font color='blue' size="+2">
<b>${outcome}</b>
</font>
<hr>
</c:if>
<h2><center>Scan Location to Add to<br>
request number ${createrequestid}. </center></h2>
<hr size=2>
<html:form action="wAddLocation" focus="location">
<table>

<tr><td class="right">Scan Location:</td><td>
<html:text property="location" value="" size="10" styleId="autoSubmit"/>
<html:hidden property="wRequestId" value="${createrequestid}"/>
</td></tr>
<tr><td><html:submit value="Add"/></td></tr>


</table>
</html:form>
<br>
<br>
<html:form action="dowinventory">
<html:submit value="Done"/>
</html:form>


