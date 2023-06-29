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
Adding to ${addWcountForm.location}<br>
Scan Sku to Add to Request.        <br>
Scan location Again to Finish Adding Sku's<br>
<html:form action="wAddsku">
<table>

<tr><td class="right">Scan Inventory:</td><td>
<html:text  property="inventoryId" styleId="autoSubmit"/>
<html:hidden property="location"/>
<html:hidden property="wRequestId"/>
</td></tr>

<html:submit value="Add SKU"/>
</html:form>
<br>
<br>



