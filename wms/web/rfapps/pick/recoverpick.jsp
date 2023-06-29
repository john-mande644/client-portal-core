<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.owd.hibernate.generated.OrderPickStatus,
                 com.owd.hibernate.generated.OrderPickItem" %>
<html>
<head>

    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
    <META HTTP-Equiv="Scanner" content="Enabled">
    <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
    <META HTTP-Equiv="Scanner" content="AutoEnter">
    <jsp:include page="/rfapps/buttonmeta.jsp"/>
</head>
<body>
<div class="body">
    <TABLE>
        <TR>
            <TH ALIGN=LEFT>
                <html:form action="menu"><html:submit><bean:message key="button.mainmenu"/></html:submit><c:out
                        value='${loginName}'/></html:form>
            </TH>
        </TR>
    </TABLE>
    <HR>


    <c:if test='${error != null}'>
        <script>
            try {
                javautil.playErrorSound();
            } catch (err) {

            }
        </script>
        <B class="error">${error}</B>
        <script>
            var gen = new ActiveXObject("SymbolBrowser.Generic");
            gen.PlayWave("\\err.wav", 0);
        </script>
        <br>
    </c:if>
    <table border="0" width="230" cellspacing="0" cellpadding="0" frame="box">
        <tr>
            <td valign=top>
                <span class="label">Continue Order Pick</span>
                <HR ALIGN="center" SIZE="1">
                You are already picking order <c:out value='${pickInfo.orderNum}'/><BR>
                You are picking line <c:out value='${pickInfo.currentItem}'/> of <c:out
                    value='${pickInfo.numberOfItems}'/> lines in this order.<BR>
                You have picked <c:out value='${pickInfo.currentPicked}'/>
                of <c:out value='${pickInfo.currentTotal}'/> units for SKU
                <c:out value='${pickInfo.sku}'/><P>

                <table width=100%>
                    <TR>
                        <TD align=left>
                            <html:form action="pickcontinue"><html:submit><bean:message
                                    key="button.pickcontinue"/></html:submit></html:form>

                        </td>
                        <td align=right>
                            <html:form action="pickstatuscancel"><html:submit
                                    onclick="return confirm('Are you sure you want to Cancel Pick?')"><bean:message
                                    key="button.pickstatuscancel"/></html:submit></html:form>

                        </td>
                    </tr>
                </table>
                <HR ALIGN="center" SIZE="1">
            </td>
        </tr>
    </table>
</div>
</body>
</HTML>

