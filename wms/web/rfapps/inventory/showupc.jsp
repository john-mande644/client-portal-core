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

<HR>

<html:form action="inv-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit></html:form>  <P>

<H3>${loginName}</H3>

    <html:form action="viewUpc" >
      <html:hidden property="sku" value="${skuForm.sku}" />
       <c:if test="${upc!=''}">
       ${upc} <html:submit property="submit"><bean:message key="button.removeUpc"/></html:submit><p>
       </c:if>
       <c:if test="${isbn!=''}">
        ${isbn} <html:submit property="submit"><bean:message key="button.removeIsbn"/></html:submit><p>
       </c:if>
       <c:if test="${upc!='' && isbn!= ''}">
          <html:submit property="submit"><bean:message key="button.all"/></html:submit>
       </c:if>
       <c:if test="${upc=='' && isbn==''}">
           None Assigned!!!!!!!
       </c:if>
    </html:form>
<HR>
 <P class="button">
    <html:link href="/wms/rfapps/inventory/viewupcstart.jsp" styleClass="invbutton">View Another Upc</html:link>
</div>
    </body>
</html>
