<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
  </head>
<body>
<c:if test='${outcome != null}'>
<font color='blue'>
<b><c:out value='${outcome}'/></b>
</font>
<hr>
</c:if>
Change email address for Auto Notification
<br>Changes go into effect immediately after save.
<br>
<html:form action="emailEdit">
<span class="label">DC Hold Emails</span><html:text property="hold" size="30"/> <br>
<span class="label">StockOut Emails</span><html:text property="stockout" size="30"/><br>
 <html:submit property="submit"><bean:message key="button.emailChange"/></html:submit>
</html:form>


</body>
</html>