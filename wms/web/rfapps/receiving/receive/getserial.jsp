<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/includes/androidStuff.jsp"/>

 <SCRIPT>
             function insertAndSubmit(scanData){
               document.getElementById("autoSubmit").value = scanData;
               document.getElementById("autoSubmit").form.submit();
             }
 </SCRIPT>
<body>
<c:if test='${error != null}'><B class="error">${error}</B>
 <script>
         try{
             javautil.playErrorSound();
         }catch(err){

         }
     </script>
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
<hr size="3">
</c:if>
<span class="label">Serial Number Required<br> </span>

<table>
<html:form action="receiveGetSerial" focus="serialNumber">
<tr><td class="right">Serial Number:</td><td>
<html:text  property="serialNumber" value="" styleId="autoSubmit"/>
<html:hidden property="count"/>
<html:hidden property="vCount"/>
<html:hidden property="invId"/>
<html:hidden property="receiveItemId"/>
<html:hidden property="rcvId"/>
<html:hidden property="weight"/>
<html:hidden property="damaged"/>
<html:hidden property="doLabelPrint"/>
</td></tr>
<tr><td><html:submit value="Submit"/></td></tr>

</html:form>

<br>