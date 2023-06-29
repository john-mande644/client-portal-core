<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<META HTTP-Equiv="Scanner" content="Disabled">
<jsp:include page="/rfapps/buttonmeta.jsp" />

    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>


</head>
<body>
<div class="body">
<H2>OWD Inventory Utilities</H2>
<HR>

<html:form action="menu"><html:submit><bean:message key="button.mainmenu"/></html:submit></html:form>  <P>
<center>
<H3><c:out value='${loginName}'/></H3>
<table class="invMenu">
    <tr><td>
<html:link action="upcisbnstart" styleClass="invbutton"><bean:message key="button.upcorisbn"/></html:link>  <P class="button">
    </td><td>
<html:link action="checkupcstart" styleClass="invbutton"><bean:message key="button.checkupc"/></html:link>  <P class="button">
    </td>
        </tr>
    <tr><td>
<html:link action="verifyupc" styleClass="invbutton"><bean:message key="button.verifyupc"/></html:link>  <P class="button">
    </td><td>
<html:link action="printskulabel" styleClass="invbutton"><bean:message key="button.printskulabel"/></html:link>  <P class="button">
    </td></tr>
    <tr><td>
    <html:link href="/wms/rfapps/inventory/viewupcstart.jsp" styleClass="invbutton">View UPC</html:link>   <P class="button">
        </td><td>

<html:link action="dowinventory" styleClass="invbutton"><bean:message key="button.dowinventory"/></html:link> <p class="button">
    </td></tr>
    <tr><td>
<html:link action="doVerification" styleClass="invbutton" >Verify</html:link>  <p class="button">
    </td><td>
   <html:link href="/wms/cycleCount/start.action" styleClass="invbutton">Cycle Counts</html:link>
    </td></tr>
    <tr>
    <td>
       <html:link href="/wms/supplies/handheldRecord.action?name=${loginName}" styleClass="invbutton">Supplies</html:link>
        </td></tr>
    </table>
</center>
</div>
    </body>
</html>