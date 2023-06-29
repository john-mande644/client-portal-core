<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>

    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>

    <META HTTP-Equiv="Scanner" content="Enabled">
    <META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
    <META HTTP-Equiv="Scanner" content="AutoEnter">

    <meta name="HandheldFriendly" content="true"/>

    <meta name="viewport" content="width=device-width, height=device-height, user-scalable=no"/>
    <jsp:include page="/rfapps/buttonmeta.jsp"/>
</head>
<div class="body">
    <body>
    <TABLE width=100%>
        <TR>
            <TH ALIGN=LEFT>
                <html:form action="menu"><html:submit><bean:message key="button.mainmenu"/></html:submit><c:out
                        value='${loginName}'/></html:form></TH>
            <TH ALIGN=RIGHT>
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
        ${donemessage}
        <br>

        <c:if test="${fn:indexOf(error,'already')>=0}">

            <c:if test='${reqordernum!=null&&shipped=="no"}'>
                <html:form action="pickcancel"><html:hidden property="sku"
                                                            value="${reqordernum}"/><html:submit><bean:message
                        key="button.forcerepick"/></html:submit></html:form>

                <html:form action="verify"><html:hidden property="ordernumber" value="${reqordernum}"/>
                    <html:hidden property="verify" value="1"/><html:submit><bean:message
                            key="button.verify"/></html:submit></html:form>
            </c:if>
        </c:if>
    </c:if>
    <table border="0" width="230" cellspacing="0" cellpadding="0" frame="box" class="fullWidthTable">
        <tr>
            <td valign=top>
                <b>Pick Order</b> <c:if test='${license!=null}'>: into ${license}</c:if>
                <HR ALIGN="center" SIZE="1">
                <html:form action="pickstart" focus="ordernumber">
                    <html:hidden property="license" value="${license}"/>
                    <bean:message key="label.pickstart.scan"/><br>
                    <html:text property="ordernumber" value="" styleId="autoSubmit"/><br>
                    <br>
                    <html:checkbox property="priority"/> Pick Secondary Locations </br>

                    <br>


                    <html:submit><bean:message key="button.pick"/> </html:submit>
                    <html:hidden property="verify" value="0"/>
                </html:form>

                <HR ALIGN="center" SIZE="1">
            </td>
        </tr>
    </table>
    <c:if test="${priority == 2}">
        <script type="text/javascript">
            document.forms["ordernumForm"].elements["priority"].checked = true;
        </script>
    </c:if>
    </BODY>
</div>
</HTML>

