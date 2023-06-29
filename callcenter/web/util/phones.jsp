
<%@ taglib uri="struts/bean-el" prefix="bean" %>
<%@ taglib uri="struts/html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
<body> <div id="body">
<table>
<c:forEach var="item" items="${phoneForm.phones}">
     <tr>
         <td>${item.ext}</td>
         <td>${item.method}</td>
         <td><html:form action="/phoneLogedIn">
             <html:hidden property="exten" value="${item.ext}"/>
             <html:submit property="submit" onclick="javascript:return confirm('Do you Really want to logoff this exention')">
                 <bean:message key="button.logoff"/>
             </html:submit></td>
         </tr>
       </html:form>
</c:forEach>
     <tr><td colspan=3>Last Updated: ${phoneForm.lastModified}</td></tr>

    <tr></tr>
    <td><td>
    <html:form action="/phoneLogedIn">
    <html:submit property="submit">
                 <bean:message key="button.refresh"/>
             </html:submit>
    </html:form></td></td>
</table>




</div>
</body>
</html>