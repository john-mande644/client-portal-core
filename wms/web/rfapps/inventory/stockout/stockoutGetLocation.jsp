<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

 <META HTTP-Equiv="Scanner" content="Enabled">
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
<c:if test='${outcome != null}'>
<font color='blue' size="+2">
<b>${outcome}</b>
</font>
<hr>
</c:if>
<table border="0" width="100%" cellspacing="0" cellpadding="0" frame="box">
<tr><td valign=top  >

 Go to<p>
 <font size="+2">${stockoutForm.sLocation}</font><br>
<span class="label">Scan it</span><br>

<HR ALIGN="center" SIZE="1" >
<html:form action="stockoutGetLocation" focus="location">
<html:text property="location" value="" styleId="autoSubmit"/>
<html:hidden property="sLocation"/>
<html:hidden property="sInventoryId"/>
<html:hidden property="clientFkey"/>
<html:hidden property="id"/>
<html:submit value="Submit"/>
</html:form>


<HR ALIGN="center" SIZE="1" >
</td></tr></table>

</div>
</BODY>
</html>