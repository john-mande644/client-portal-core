<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>


<html>
<head>
<META HTTP-Equiv="Scanner" content="Disabled">
<jsp:include page="/rfapps/buttonmeta.jsp" />
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>

</head>
<body bgcolor=#ffffff >
<div class="body">
<H2>OWD Receiving Utilities</H2>
<HR>

<html:form action="menu"><html:submit><bean:message key="button.mainmenu"/></html:submit></html:form>  <P>
<center>
<H3><c:out value='${loginName}'/></H3>
<html:form action="startreceive"><html:submit><bean:message key="button.receive"/></html:submit></html:form>  <P>
<!--<html:link action="return" styleClass="button">RETURN</html:link>-->
    <html:link href="/wms/receive/start.action" styleClass="button">New Receive Beta</html:link>


</center>
 </div>
    </body>
