<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

 <META HTTP-Equiv="Scanner" content="Disable">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
    <meta name="viewport" content="width=device-width, height=device-height, user-scalable=no target-densitydpi=high-dpi" />
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>

</head> <body>
<div class="body">
<TABLE width=100%><TR><TH ALIGN=LEFT>

<c:out value='${loginName}'/>
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
<table border="0" width="100%" cellspacing="0" cellpadding="0" frame="box">
<tr><td valign=top  >

${editWCountForm.oldQuanity} were counted for ${editWCountForm.inventoryId}<p>
<span class="label">Enter New Quanity.</span><br>

<HR ALIGN="center" SIZE="1" >
<html:form action="editWcountSave" focus="quanity">
<html:text property="quanity" value="" styleId="autoSubmit"/>
<html:hidden property="requestId"/>
<html:hidden property="location"/>
<html:hidden property="inventoryId"/>
<html:hidden property="oldQuanity"/>
<html:hidden property="wCountId"/>
<html:submit value="Submit"/>
</html:form>


<HR ALIGN="center" SIZE="1" >
</td></tr></table>

</div>
</BODY>
</HTML>