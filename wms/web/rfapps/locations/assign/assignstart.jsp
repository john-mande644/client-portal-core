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
        <html:form action="loc-menu"><html:submit><bean:message key="button.mainmenu"/></html:submit><c:out
                value='${loginName}'/></html:form>
    </div>
</div>


<div class="row">
    <div class="large-12 columns">
        <HR>
        <c:if test='${error != null}'>
            <script>
                try {
                    javautil.playErrorSound();
                } catch (err) {

                }
            </script>
            <B class="error">${error}</B>
        </c:if>
    </div>


</div>

<div class="row">
    <div class="large-12 columns">


        <c:if test='${outcome != null}'>
            <font color='blue'>
                <b>${outcome}</b>
            </font>
            <hr>
        </c:if>
        <span class="label">Scan Sku for single assign</span><br>
        <span class="label">Scan Location for batch assign</span>
        <br>
        <HR ALIGN="center" SIZE="1">

    </div>
</div>

 <div class="row">

     <div class="large-12 columns">
         <html:form action="assignlocationstart" focus="location">
             <bean:message key="label.locations.start"/><br>
             <html:text property="location" value="" styleId="autoSubmit"/><br>

             <html:submit value="Submit"/>
         </html:form>
         <c:if test='${remember != null}'>
             Remembering <c:out value='${rememberclientname}'/>
             <html:form action="clearRememberingClient">
                 <html:hidden property="sku" value="${loginName}"/>
                 <html:submit value="Clear Client"/>
             </html:form>
         </c:if>

     </div>
 </div>




</body>

</HTML>