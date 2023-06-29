<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.owd.dc.inventory.multiSkuForm,
                 java.util.HashMap,
                 java.util.Iterator"%>

<html>

<html:form action="recv-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit><c:out value='${loginName}'/></html:form>

<%=request.getAttribute("sku")%>


</html>