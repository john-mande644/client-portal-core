<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<META HTTP-Equiv="Scanner" content="ENABLED">
<META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/buttonmeta.jsp" />
<META HTTP-Equiv="OnKey0x0d" content="Javascript:onKey1()">
  <script type="text/javascript" >
    function onKey1(){
       document.forms[1].elements[1].click();
        return false;
    }
</script>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>


</head>
<body>
<div class="body">



<html:form action="inv-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit></html:form>  <P>
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
<H3>${loginName}</H3>
<c:if test='${outcome != null}'>
<span class="outcome">${outcome}</span>
<hr size="3">
</c:if>
<HR>
    <html:form action="viewUpc" focus="sku">
     <span class="label">Enter Sku??</span>   <html:text property="sku" value="" size="10" styleId="autoSubmit"/> <br>
        <html:submit property="submit"><bean:message key="button.viewUPC"/></html:submit>
    </html:form>

</div>
    </body>
</html>
