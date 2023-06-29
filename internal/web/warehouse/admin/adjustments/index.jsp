<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus,
                 java.text.DecimalFormat,
                 java.util.*,
                 java.text.DateFormat,
                 com.owd.core.managers.ConnectionManager,
                 java.text.SimpleDateFormat,
                 java.sql.ResultSet,
                 com.owd.web.internal.navigation.*,
                 com.owd.hibernate.HibernateSession,
                 org.hibernate.Session,
                 org.apache.commons.beanutils.RowSetDynaClass,
                 com.owd.core.business.order.OrderManager,
                 com.owd.hibernate.generated.OwdOrder,
                 com.owd.core.business.order.OrderFactory,
                 com.owd.hibernate.generated.OwdUser,
                 com.owd.core.business.user.UserFactory,
                 com.owd.hibernate.generated.OwdOrderShipHold,
                 com.owd.web.internal.warehouse.admin.WarehouseHoldUtilities,
                 com.owd.web.internal.servlet.HomeServlet" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
    String cid = HomeServlet.getSessionString(request, HomeServlet.kExtranetClientID);
    if (cid == null) cid = "0";

    if ("0".equals(cid) || "".equals(cid)) cid = "146";

    cid = "146";
    HomeServlet.setSessionString(request, HomeServlet.kExtranetClientID, cid);

    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Inventory Adjustments", request);
    try {


%>


<HTML>
<HEAD>
    <script language=JavaScript>

        var ModalDialogWindow;
        var ModalDialogInterval;
        var ModalDialog = new Object;

        ModalDialog.value = '';
        ModalDialog.eventhandler = '';


        function ModalDialogMaintainFocus()
        {
            try
            {
                if (ModalDialogWindow.closed)
                {
                    window.clearInterval(ModalDialogInterval);
                    eval(ModalDialog.eventhandler);
                    return;
                }
                ModalDialogWindow.getObject("q").focus();
            }
            catch (everything) {
            }
        }

        function ModalDialogRemoveWatch()
        {
            ModalDialog.value = '';
            ModalDialog.eventhandler = '';
        }

        function ModalDialogShow(Title, pageURL, EventHandler)
        {

            ModalDialogRemoveWatch();
            ModalDialog.eventhandler = EventHandler;

            var args = 'width=400,height=500,left=325,top=300,toolbar=0,';
            args += 'location=0,status=0,menubar=0,scrollbars=1,resizable=1';

            ModalDialogWindow = window.open(pageURL, "", args);

            ModalDialogWindow.focus();
            ModalDialogInterval = window.setInterval("ModalDialogMaintainFocus()", 30);

        }

    </script>

    <script language=JavaScript>


        function lookupSKU(BodyText, EventHandler)
        {
            ModalDialogShow("SKU Finder", BodyText, EventHandler);
        }


        function lookupValueSet()
        {
            document.getElementById('modalreturn1').value = ModalDialog.value;
            ModalDialogRemoveWatch();
        }


    </script>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>
    <style>
        span.pagelinks {
            border: none;
        }
    </style>

    <div id="pagecommands" style="padding-top: 8px;">
        <h3>
            <a class="admin" href="/internal/warehouse/admin">Cancel and go back</a>

        </h3>
    </div>

    <font color=red>
        <B><%= (request.getAttribute("errormessage") != null ? "<div class=\"AlertBad\">" + request.getAttribute("errormessage") + "</div>" : "") %></B>
    </font>
    <%


    %>

    <FORM METHOD=POST ACTION="/internal/warehouse/admin/adjustments/addadjustment">
        <table border=0 cellpadding=0><TR><TD ALIGN=RIGHT NOWRAP>
            <B>First, choose a client:&nbsp;</B></TD><TD ALIGN=LEFT NOWRAP>
            <SELECT NAME=adjcid><%= HomeServlet.getClientSelector(request, "name", cid)%></SELECT></TD></TR>
            <TR><TD></TD><TD ALIGN=LEFT NOWRAP></TD></TR>
            <TR><TD ALIGN=RIGHT><B></B></TD><TD ALIGN=LEFT NOWRAP>
                &nbsp;&nbsp;<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Next Step"></TD></TR>
        </table>
    </FORM>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>

<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>
