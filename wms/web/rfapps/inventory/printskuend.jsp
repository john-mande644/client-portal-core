<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.owd.hibernate.generated.OwdInventory"%>

<html>
<head>

 <META HTTP-Equiv="Scanner" content="Disabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/buttonmeta.jsp" />
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>



</head>
<body><div class="body">
<TABLE width=100%><TR><TH ALIGN=LEFT>

<html:form action="inv-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit><c:out value='${loginName}'/></html:form>
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


<b><font size=+1><bean:message key="text.printlabelfinish"/></font></b><br>
<table border="0" width="100%" cellspacing="0" cellpadding="0" frame="box">
<tr ALIGN="center">
<TD><B><font size=+4><span class="inventorynum"><c:out value='${skuinfo.inventoryNum}'/></span>
<BR><span class="inventoryid"><c:out value='${skuinfo.inventoryId}'/></span>
<BR><span class="description"><c:out value='${skuinfo.description}'/></span></font></b></td></tr>
<tr><td valign=top>


<HR ALIGN="center" SIZE="1" >
<html:form action="printskuend" >
<bean:message key="label.printsku.finish" /><br>
<html:text property="numlabels" value="" styleId="autoSubmit"/><br>
<html:hidden property="sku" value="${skuinfo.inventoryId}"/>
<html:submit><bean:message key="button.printskuend"/></html:submit>
</html:form>

<HR ALIGN="center" SIZE="1" >
<html:form action="printskulabel"><html:submit><bean:message key="button.cancel"/></html:submit></html:form>
</td></tr></table>
</div>
<script>try{
      document.forms[0].elements["numlabels"].focus();
     document.getElementById("autoSubmit").type = 'number'
    javautil.delayedFocus();
    }catch(err){
        document.forms[0].elements[0].focus()
    }
    function focusButton(){
         document.getElementById("autoSubmit").focus();
        }

</script>

</body>

</HTML>