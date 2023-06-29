<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

 <META HTTP-Equiv="Scanner" content="Disabled">
 <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
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
<h2>You Entered:<br> </h2>
<center>
<font size="18"><span class="inventoryid">${wCountForm.quanity}<br></span>  </font>
</center>
<span class="label"></span><br>
<HR ALIGN="center" SIZE="1" >
Is this correct???????
<html:form action="wverifyQuanity" >
<html:hidden property="quanity" />
<html:hidden property="barcode"/>
<html:hidden property="inventoryId"/>
<html:hidden property="invLocFkey"/>
<html:hidden property="requestId"/>
<html:hidden property="location"/>
<html:hidden property="UPC"/>
<html:hidden property="ISBN"/>
<html:hidden property="verified" value="yes"/>
<html:submit value="Yes"/>
</html:form>
<hr>
<html:form action="wverifyQuanity" >
<html:hidden property="quanity" />
<html:hidden property="barcode"/>
<html:hidden property="inventoryId"/>
<html:hidden property="invLocFkey"/>
<html:hidden property="requestId"/>
<html:hidden property="location"/>
<html:hidden property="UPC"/>
<html:hidden property="ISBN"/>
<html:hidden property="verified" value="no"/>
<html:submit value="No"/>
</html:form>


<HR ALIGN="center" SIZE="1" >
</td></tr></table>


<br>

</div>
</BODY>
</HTML>