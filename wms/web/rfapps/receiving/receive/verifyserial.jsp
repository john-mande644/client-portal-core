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
<span class="label">Verify Serial Number<br></span>
<center>${receiveForm.serialNumber}</center>
<table>
<html:form action="receiveVerifySerial" focus="vSerialNumber">
<tr><td class="right">Serial Number:</td><td>
<html:text  property="vSerialNumber" value="" styleId="autoSubmit"/>
<html:hidden property="serialNumber"/>
<html:hidden property="count"/>
<html:hidden property="vCount"/>
<html:hidden property="invId"/>
<html:hidden property="receiveItemId"/>
<html:hidden property="rcvId"/>
<html:hidden property="weight"/>
<html:hidden property="damaged"/>
</td></tr>
<tr><td><html:submit value="Verify Serial"/></td></tr>

</html:form>

<html:form action="receiveVerifySerial">
<tr><td class="right"></td><td>
<html:hidden  property="vSerialNumber" value="change"/>
<html:hidden property="serialNumber"/>
<html:hidden property="count"/>
<html:hidden property="vCount"/>
<html:hidden property="invId"/>
<html:hidden property="receiveItemId"/>
<html:hidden property="rcvId"/>
</td></tr>
<tr><td><html:submit value="Change"/></td></tr>

</html:form>

</body>
