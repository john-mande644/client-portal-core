<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html public "-//w3c/dtd HTML 4.0//en">

<html>
      <head>

 <META HTTP-Equiv="Scanner" content="Enabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
          <meta name="HandheldFriendly" content="true" />
<meta name="viewport" content="width=device-width, height=device-height, user-scalable=no" />
<jsp:include page="/rfapps/buttonmeta.jsp" />


<title>Warehouse Location Manager</title>

          <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
       <SCRIPT>
             function insertAndSubmit(scanData){
               document.getElementById("autoSubmit").value = scanData;
               document.getElementById("autoSubmit").form.submit();
             }
    </SCRIPT>
          <body>
          <div class="body">


<TABLE><TR><TH ALIGN=LEFT>
<html:form action="menu"><html:submit><bean:message key="button.mainmenu"/></html:submit>${loginName}</html:form> </TH><TH ALIGN=RIGHT>
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
<c:if test='${outcome != null}'>
<font color='blue' size="+2">
<b>${outcome}</b>
</font>
<hr>
</c:if>

<html:form action="findSku" focus="sku">
<html:text property="sku" value="" styleId="autoSubmit"/>

<html:submit value="FIND"/>
</html:form>   
              <span class="inventorynum">${searchSku.inventoryNum}</span> / 
<span class="inventoryid">${searchSku.inventoryId}</span><BR>

         ${searchSku.imageUrl}
      



 </div>

    </body>

</html>