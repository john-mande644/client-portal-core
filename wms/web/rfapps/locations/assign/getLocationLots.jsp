<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="/rfapps/includes/androidResponsive.jsp"/>
    <script type="text/javascript">
        $( document).ready(function() {

            $(".lotButton").click(function(){
                console.log($(this).val());
                 $('#lotValue').val($(this).val()) ;

            }) ;

        }) ;
    </script>
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
        <b>This Item is Lot Controlled<span class="inventoryid"><c:out value='${skuInfo.inventoryId}'/></span></b>
        <BR><span class="inventorynum"><c:out value='${skuInfo.inventoryNum}'/></span>
         <br/>
        Please Select the lot you are working with

        <html:form action="assigngetlot" styleId="theForm">

            <c:forEach var="lot" items="${lots}">
               <button value="${lot}" class="lotButton">${lot}</button>
            </c:forEach>

            <html:hidden property="sku" value="${sku}"/>

            <html:hidden property="lotValue" styleId="lotValue"/>
        </html:form>
    </div>
</div>

</body>
</HTML>

