<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<META HTTP-Equiv="Scanner" content="Disabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<META HTTP-Equiv="OnKey0x0d" content="Javascript:onKey1()">
  <script type="text/javascript" >
    function onKey1(){
       document.forms[0].elements[2].click();

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
<span class="label">How Many Minutes in the Receive??<br></span>

<table>
<html:form action="doneWithReceive" >
<html:text property="time" size="10" value="" styleId="autoSubmit"/>
<html:hidden property="rcvId"/>
<tr><td><html:submit property="submit"><bean:message key="button.enterTime"/></html:submit></td>
</html:form>
</table>
<br>
</div>
<script>
 try{
      document.forms[0].elements[0].type = "number";

       javautil.delayedFocus();
    }catch(err){
       document.forms[0].elements[0].focus();
    }
    function focusButton(){
         document.getElementById("autoSubmit").focus();
        }
</script>
</body>
