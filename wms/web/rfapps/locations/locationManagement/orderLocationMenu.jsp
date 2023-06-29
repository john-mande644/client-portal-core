<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <jsp:include page="/rfapps/includes/androidResponsive.jsp"/>
</head>

<body>
<div class="row header">
    Order Location Tools


</div>

<div class="row">
    <div class="small-6 columns">
        <!-- left menue -->
        <html:form action="menu"><html:submit><bean:message key="button.mainmenu"/></html:submit></html:form>
        <%-- <a href="wms/locationManagement/orderMenu.action" class="button">Back</a> --%>
        <a href="/wms/locationManagement/clearOrderFromLocation.action" class="button">Clear Location</a>

    </div>

    <div class="small-6 columns">





    </div>
</div>





</body>