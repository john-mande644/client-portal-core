<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>

<jsp:include page="/rfapps/includes/androidStuff.jsp"/>

    </head>
<body>

${donemessage}
<table><tr>
 <td>
<html:link action="pick" styleClass="button"><bean:message key="button.pickorder"/></html:link>  <P class="button"></td>
</tr></table>
</body>