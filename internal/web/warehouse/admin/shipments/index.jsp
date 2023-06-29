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
                 com.owd.web.internal.warehouse.admin.WarehouseHoldUtilities" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
    String owdref = request.getParameter("owdref");
String currentLocation = WarehouseStatus.getCurrentLocation(request);

    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, currentLocation+" Start Entering Manual Shipment Confirmation", request);
    try {


%>


<HTML>
<HEAD>
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

    <FORM METHOD=POST ACTION="/internal/warehouse/admin/shipments/manualship">
        <table border=0 cellpadding=0><TR><TD ALIGN=RIGHT NOWRAP>
            <B>Order Num:&nbsp;</B></TD><TD ALIGN=LEFT NOWRAP>
            <INPUT TYPE=TEXT size=30 NAME=ordernum></TD></TR>
            <TR><TD></TD><TD ALIGN=LEFT NOWRAP>OWD order reference from packing slip</TD></TR>

            <TR><TD ALIGN=RIGHT><B>Sent&nbsp;via:</B></TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=SUBMIT NAME=SUBMIT
                                                                                       VALUE="Express Carrier">
                &nbsp;&nbsp;<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="LTL/Other Shipment">
                &nbsp;&nbsp;<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Picked Up"></TD></TR>
        </table>
    </FORM>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>

<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>
