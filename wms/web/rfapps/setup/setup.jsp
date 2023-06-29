<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<META HTTP-Equiv="Scanner" content="Disabled">


    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
</head>
<body>
<div class="body">
<html:form action="menu"><html:submit value="Cancel"/></html:form>  <P>

<c:if test='${outcome != null}'>
<b class="outcome"><c:out value='${outcome}'/></b>
</c:if>
Please select the values you wish to use!!
<html:form action="savebuttonoptions">

<table>

 <c:if test="${buttonForm.teleport}">

    <tr><td class="setup">Facility</td></tr><tr>
    <td class="right">
        <html:select name="buttonForm" property="facility">
            <html:optionsCollection name="buttonForm" property="facilities" value="action" label="display"/>
        </html:select>
        <hr>
    </td></tr>
    </c:if>
 <tr><td class="setup">Printer</td></tr><tr>
<td class="right">
    <html:select name="buttonForm" property="printer">
        <html:optionsCollection name="buttonForm" property="printerlist" value="action" label="display"/>
    </html:select>
    <hr>
 </td></tr>
     <tr><td class="setup">Small Printer</td></tr><tr>
<td class="right">
    <html:select name="buttonForm" property="smallPrinter">
        <html:optionsCollection name="buttonForm" property="smallprinterlist" value="action" label="display"/>
    </html:select>
    <hr>
 </td></tr>

    <tr><td class="setup">Pallet Tag</td></tr><tr>
    <td class="right">
        <html:select name="buttonForm" property="palletTag">
            <html:optionsCollection name="buttonForm" property="palletTags" value="action" label="display"/>
        </html:select>
        <hr>
    </td></tr>
 <tr><td align="center"><html:submit value="Save"/></td></tr>
</table>
</html:form>

</div>
</body>
</html>