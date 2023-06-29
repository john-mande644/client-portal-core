<html>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<META HTTP-Equiv="Scanner" content="Disabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">


<META HTTP-Equiv="OnKey0x0d" content="Javascript:onKey()">
<script type="text/javascript" >
    function onKey(){

         document.forms[0].elements[8].click();

    }
</script>
<jsp:include page="/rfapps/includes/androidStuff.jsp"/>

<body>
<div class="receive">
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
<span class="label">Verify Info and Enter Received Quanity<br></span>
<center>
<span class="inventoryid">${ib.inventoryId}<br></span>
<span class="inventorynum">${ib.inventoryNum}</span>
    <br>Qty on Hand ${ib.qtyOnHand}
<br>${ib.description}

</center>
<table class="receiveGetSkuTable">
<html:form action="receiveGetQty" >
<tr><td class="right">Quanity:</td><td>
<html:text  property="count" value="" size="5" styleId="autoSubmit" />
<html:hidden property="weight"/>

<html:hidden property="invId"/>
<html:hidden property="receiveItemId"/>
<html:hidden property="rcvId"/>
<html:hidden property="damaged"/>
<html:hidden property="action"/>
<html:hidden property="doLabelPrint"/>

</td></tr>
<tr><td class="right"><html:submit property="submit"><bean:message key="button.submitQty"/></html:submit></td></tr>
    </table>

<c:if test="${counted!=0}">
You have already entered ${counted} of this sku.   <br>
</c:if>
<span class="notes">DO NOT INCLUDE DAMAGED QUANITY</span>
<c:if test="${receiveForm.action!='edit'}">
<table>
<tr><td><html:text property="labelCount" value="1" styleId="num"/></td></tr>
<tr><td><html:submit property="submit"><bean:message key="button.printLabel"/></html:submit></td>
    <td><html:submit property="submit"><bean:message key="button.printLargeLabel"/></html:submit></td>
    <td><html:submit property="submit"><bean:message key="button.palletTag"/></html:submit></td>
</tr>
</c:if>
</html:form>
</table>
<br>
<html:form action="receiveLoadAsn">
<html:hidden property="rcvId"/>
<html:submit value="cancel"/></html:form>
</div>
</body>
<script>
 try{
    document.getElementById("autoSubmit").type = 'number'
     document.getElementById("num").type = 'number'
       javautil.delayedFocus();
    }catch(err){
       document.forms[0].elements[0].focus();
    }
    function focusButton(){
         document.getElementById("autoSubmit").focus();
        }
</script>
</html>