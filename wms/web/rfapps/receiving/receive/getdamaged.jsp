<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<META HTTP-Equiv="Scanner" content="Disabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
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
<span class="label">Enter Damaged Quanity<br></span>

<table>
<html:form action="receiveGetDamaged" >
<tr><td class="right">Damaged:</td><td>
<html:text  property="damaged" value="" size="5" styleId="autoSubmit"/>
<html:hidden property="weight"/>
<html:hidden property="count"/>
<html:hidden property="invId"/>
<html:hidden property="receiveItemId"/>
<html:hidden property="rcvId"/>
<html:hidden property="doLabelPrint"/>
<html:hidden property="action"/>
</td></tr>
<tr><td><html:submit value="Submit Qty"/></td></tr>

</html:form>
</table>
<br>
<span class="notes">ENTER ZERO IF THERE IS NONE</span>
</div>
<script>
try{
      document.getElementById("autoSubmit").type = 'number'

       javautil.delayedFocus();
    }catch(err){
       document.forms[0].elements[0].focus();
    }
    function focusButton(){
         document.getElementById("autoSubmit").focus();
        }
</script>
</body>
