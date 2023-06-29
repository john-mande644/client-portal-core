<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html><head>
<META HTTP-Equiv="Scanner" content="Enabled">
<META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/buttonmeta.jsp" />
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
</head>
<body><div class="body">
<TABLE width=100%>
<TR><TH ALIGN=LEFT>
<html:form action="loc-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit><%= " " + request.getAttribute("loginName")%></html:form>
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
<font color='blue'>
<b><c:out value='${outcome}'/></b>
</font>
<hr>
</c:if>
<table border="0" width="100%" cellspacing="0" cellpadding="0" frame="box">
<tr><td valign=top  >
<span class="label">Scan Sku to <font color=red>Remove</font> Single</span><br>
<span class="label">Scan Location to Batch <font color=red>Remove</font></span>
<HR ALIGN="center" SIZE="1" >

<html:form action="removelocationstart" focus="location">
<bean:message key="label.locations.start"/><br>
<html:text property="location" value="" styleId="autoSubmit"/><br>
<html:submit value="Submit"/>
</html:form>

<HR ALIGN="center" SIZE="1" >
</td></tr></table>
<c:if test='${remember != null}'>
Remembering <c:out value='${rememberclientname}'/>
<html:form action="clearRememberingClient">
<html:hidden property="sku" value="${loginName}"/>
<html:submit value="Clear Client"/>
</html:form>
</c:if>
</div>

</body>

</HTML>