<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/rfapps/includes/androidStuff.jsp"/>

<body><div class="body">
<TABLE width=100%><TR><TH ALIGN=LEFT>
<html:form action="menu"><html:submit><bean:message key="button.mainmenu"/></html:submit>&nbsp;<c:out value='${loginName}'/></html:form>
</TH><TH ALIGN=RIGHT>
 </TH><tr></tr></TR></TABLE>
<HR SIZE="3" color=black>
<c:if test='${error != null}'>
<B class="error"><c:out value='${error}'/></B>
<script>
var gen = new ActiveXObject("SymbolBrowser.Generic");
gen.PlayWave("\\err.wav",0);
</script>
</c:if>
<h2>This order was placed on Pending Hold<br>
<br>
 Place Item(s) in the nearest hold location <br>
 and scan the locations tag.
Follow procedure for this client.</h2>
<br>
<br>
<html:form action="setHoldLocation">
    <html:text property="hold_location" value="" size= "20"  styleId="autoSubmit"/>
    <html:hidden property="pickStatusId" value="${pstat.id}"/>
    <html:submit><bean:message key="button.ok"/></html:submit>
</html:form>
</div></body>
