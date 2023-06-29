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


<META HTTP-Equiv="OnKey0x0d" content="">
 
<body >
<div class="receive">
<html:link action="recv-menu" styleClass="button">Done</html:link><br><hr>
 <c:if test='${error != null}'>
     <script>
         try{
             javautil.playErrorSound();
         }catch(err){

         }
     </script>
     <script>
      try{
     //   javautil.focusButton();
    }  catch(e){}
             </script>
<B class="error">${error}</B>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>

<br>
</c:if>
<c:if test='${outcome != null}'>
<font color='blue'>
<b>${outcome}</b>
</font>
<hr>
</c:if>
<span class="label">Enter Asn id to start<br></span>
<table>
<html:form action="receiveLoadAsn"  >
<tr><td class="right">Asn Id:</td><td>
<html:text  property="asnId" value="" size="10" styleId="autoSubmit"/>

</td></tr>
<tr><td><html:submit value="Start Receive"/></td></tr>

</html:form>
</table>
<br>
<br>
 </div>
<script>try{
      document.forms[0].elements["asnId"].focus();
     document.getElementById("autoSubmit").type = 'number'

    }catch(err){
        document.forms[0].elements[0].focus()
    }
    function focusButton(){
         document.getElementById("autoSubmit").focus();
        }

</script>

</body>
