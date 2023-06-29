<html><%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
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
<div class="receive">
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
<HR>
</c:if>
Asn: ${receiveForm.asnId} | Rcv: ${receiveForm.rcvId}<p>
    <HR class="head">
<center><span class="label">${info.client}</span><br>
${info.desc}<br></center>      
    <HR class="head">
<span class="label">Scan Sku to Start Count:<br> </span>
<table class="fullWidthTable">
<html:form action="receiveGetSku" focus="invId">
<tr><td class="right">SKU:</td><td>
<html:text  property="invId" value="" size="10" styleId="autoSubmit"/>
<html:hidden property="rcvId"/>
</td></tr>
<tr><td><html:submit value="Receive Sku"/></td></tr>

</html:form>
</table>
<br>
<html:form  action="doneWithReceive"><html:submit value="Done"/>
<html:hidden property="rcvId"/></html:form>
<table>
    <tr>
        <th style="text-align: left;">Sku Id</th>
        <th>Qty</th>
        <th>Dmg</th>
    </tr>
<c:forEach var="item" items="${countList}">
 <tr><td class="receiveItemtd">${item.sku}</td><td class="receiveCounttd">${item.count}</td><td class="receivedmg red">${item.damage}</td></tr>
</c:forEach>
</table>
</div>
<script>
 try{

       document.getElementById("autoSubmit").type = 'number'
    }catch(err){

    }
</script>
</body>
</html>