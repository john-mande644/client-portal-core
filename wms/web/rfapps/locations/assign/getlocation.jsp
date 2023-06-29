<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="/rfapps/includes/androidResponsive.jsp"/>
</head>
<body>
<div class="row">
    <div class="large-12 columns">
        <html:form action="loc-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit>&nbsp;<c:out
                value='${loginName}'/></html:form>
    </div>
</div>


<div class="row">
    <div class="large-12 columns">
        <c:if test='${error != null}'>
            <script>
                try {
                    javautil.playErrorSound();
                } catch (err) {

                }
            </script>
            <B class="error">${error}</B>
            <HR>


        </c:if>
    </div>
</div>

<div class="row">
    <div class="large-12 columns">
        <b>Move Item <span class="inventoryid"><c:out value='${skuInfo.inventoryId}'/></span></b>
        <BR><span class="inventorynum"><c:out value='${skuInfo.inventoryNum}'/></span>
        <BR><c:out value='${skuInfo.description}'/>
        <c:if test="${lotValue!=null}">
            <br/> Lot: ${lotValue}
        </c:if>
        <HR ALIGN="center" SIZE="1">
        <html:form action="assignsinglelocation" focus="location"
                   styleClass="skinned-form-controls skinned-form-controls-mac">
            <bean:message key="label.singlelocations.scan"/><br>
            <html:text property="location" value="" styleId="autoSubmit"/><br>
            <br>
            Notes:<br>
            <html:textarea property="notes" cols="30" rows="4"/>

            <html:hidden property="sku" value="${skuInfo.inventoryId}"/>
            <html:hidden property="lotValue" value="${lotValue}"/>
            <br>
            <html:submit value="Set Location"/>
        </html:form>
    </div>
</div>

</body>
</HTML>

