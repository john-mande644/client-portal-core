<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<META HTTP-Equiv="Scanner" content="Disabled">
<jsp:include page="/rfapps/buttonmeta.jsp" />
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
</head>
<body bgcolor=#ffffff >
<div class="body">
<H2>OWD Location Utilities</H2>
<HR>

<html:form action="menu"><html:submit><bean:message key="button.mainmenu"/></html:submit></html:form>  <P>
<center>
<H3><c:out value='${loginName}'/></H3>
    <table>
        <tr>
            <td>
                <html:form action="assign" ><html:submit><bean:message key="button.assign"/></html:submit></html:form>
            </td>
            <td>
                <html:form action="remove" ><html:submit><bean:message key="button.remove"/></html:submit></html:form>
            </td>
        </tr>
        <tr>
            <td>
                <a href="/wms/locationManagement/start.action" class="button">Location Mangement</a><P class="button">
            </td>
            <td>
                <html:form action="clear"><html:submit><bean:message key="button.clear"/></html:submit></html:form>
            </td>
        </tr>
        <tr>
            <td>
               <a href="/wms/locationManagement/pickPriorityStart.action" class="button">Pick Priority</a>
            </td>
            <td>
                <a href="/wms/locationAudit/start.action" class="button">Location Audits</a>
            </td>
        </tr>
        <tr>
            <td>

                <c:if test='${inventoryAdmin == "true"}'>
                    <a href="/wms/advancedManagement/start.action" class="button">Advanced management</a>
                </c:if>

            </td>
            <td>

            </td>
        </tr>
    </table>


<br>
 <!--<a href="/wms/checkFront/start.action" class="button">Check First</a><P class="button"></P> -->



</center>
 </div>
    </body>
</html>