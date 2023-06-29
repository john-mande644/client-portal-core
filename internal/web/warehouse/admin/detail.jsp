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
                 org.apache.commons.beanutils.RowSetDynaClass" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%String currentLocation = WarehouseStatus.getCurrentLocation(request);

    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, currentLocation+" Order Detail (By " + request.getParameter("type") + ")", request);
    request.getSession(true).setAttribute("ordershiphold.currshiphold", null);
%>


<HTML>
<HEAD>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>

<div id="pagecommands" style="padding-top: 8px;">
    <h3>
        <a class="/internal/warehouse/admin/" href="index.jsp">Summary</a>
        &nbsp;|&nbsp;<a class="admin" href="/internal/warehouse/admin/detail.jsp?type=Client">Order Detail (By
        Client)</a>
        &nbsp;|&nbsp;<a class="admin" href="/internal/warehouse/admin/detail.jsp?type=Method">Order Detail (By
        Method)</a>
        &nbsp;|&nbsp;<a class="admin" href="/internal/warehouse/admin/detail.jsp?type=SLA">Order Detail (By
        Deadline)</a>

    </h3>
</div>
<style>
    span.pagelinks {
        border: none;
    }
</style>
<font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? "<div class=\"AlertBad\">" + request.getAttribute("errormessage") + "</div>" : "") %></B>
</font>
<%

    try {
        WarehouseStatus status = null;
        Session sess = HibernateSession.currentSession();


        synchronized (ConnectionManager.getConnectionManager()) {
            if (WarehouseStatus.getCreationTime(currentLocation) < (Calendar.getInstance().getTime().getTime() - (.5 * 1000 * 60))) //5 minute interval
            {

                if (WarehouseStatus.getOldStatus(currentLocation) != null) {
                    WarehouseStatus.getOldStatus(currentLocation).setOldChart(null);
                    WarehouseStatus.getOldStatus(currentLocation).setOldChart2(null);
                }
                WarehouseStatus.setOldStatus(currentLocation,null);
                status = new WarehouseStatus(currentLocation);

            } else {
                status = WarehouseStatus.getOldStatus(currentLocation);

            }
        }
        request.setAttribute("whStatus", status);
        List clientStatus = status.getClientStatusList();
        System.out.println("got clientstatuslist size=" + clientStatus.size());
        request.setAttribute("clientStatus", clientStatus);
        List methodStatus = status.getMethodStatusList();
        List slaStatus = status.getSlaStatusList();
        Map ordersWaitingToPrint = status.getOrdersWaitingToPrint();
        int ordersPosted = status.getPostedOrders();
        int ordersShipped = status.getShippedOrders();
        Date currentDate = status.getReferenceDate();
        Iterator clientIT = clientStatus.iterator();
        request.setAttribute("methodStatus", methodStatus);
        request.setAttribute("slaStatus", slaStatus);

        DecimalFormat twoPlaceNumFormatter = new DecimalFormat("00");


        String updateTime = DateFormat.getDateTimeInstance().format(new Date(WarehouseStatus.getCreationTime(currentLocation)));

        int shippedPct = (int) (100.0 * (((float) ordersShipped) / ((float) ordersPosted)));
        int pickedPct = (int) (100.0 * (((float) status.getPickedUnpackedOrders()) / ((float) ordersPosted)));
        int packedPct = (int) (100.0 * (((float) status.getPackedUnshippedOrders()) / ((float) ordersPosted)));
        int unshippedPct = (int) (100.0 * (((float) ordersPosted - ordersShipped) / ((float) ordersPosted)));
%>
<CENTER><B>Last Updated: <%= updateTime %></B></CENTER>
<TABLE border=0><TR><TD width="200" valign="top">
    <HR>

    <FORM method="POST" action="./holds/sethold.jsp"><CENTER><H2>Report Problem Order</H2><BR><B>OWD&nbsp;Reference:&nbsp;</B>
        <INPUT TYPE=TEXT NAME=owdref VALUE="" size=15>

        <P><CENTER><INPUT TYPE=SUBMIT NAME="GO" VALUE="GO"></CENTER>

        <P></FORM>
    <HR>

    <div class="latemilestones" style="display:block;">
        <h1>Shipping Holds</h1>
        <ul>

            <%
                System.out.println("HELLOOOOOO");
                ResultSet rssh = HibernateSession.getResultSet("select company_name as 'Client' from owd_client (NOLOCK) where is_on_shipping_hold=1 and is_active=1 and default_facility_code='"+currentLocation+"'");
                System.out.println("hi") ;

                while (rssh.next()) {

            %> <li><%= rssh.getString(1) %></li>
            <%
                    System.out.println("Heeee");
                }
                HibernateSession.closeSession();
            %>
        </ul>
    </div>
    <TABLE WIDTH=100%><TR><TD ALIGN=LEFT VALIGN=TOP NOWRAP>
        <B>Orders in Print Queue:</B></TD><TD ALIGN=RIGHT VALIGN=TOP><% Iterator it = ordersWaitingToPrint.keySet().iterator();
        while(it.hasNext())
        {
            String printerStr= (String)it.next();

            %><b><%= ordersWaitingToPrint.get(printerStr) %>&nbsp;-&nbsp;<%= printerStr.replaceAll(" ","&nbsp;") %></b><br><%
        }
    %></TD></TR>
        <TR>
            <TD ALIGN=LEFT VALIGN=TOP NOWRAP></TD><TD ALIGN=RIGHT><B>(Orders/Lines/Units)</B></TD></TR>
        <TR>
            <TD ALIGN=LEFT VALIGN=TOP NOWRAP><B>Total Posted to Ship:</B></TD><TD ALIGN=RIGHT><%= ordersPosted %>
            /<%= status.getPostedOrdersLines() %>/<%= status.getPostedOrdersUnits() %></TD></TR><TR>
        <TD ALIGN=LEFT VALIGN=TOP NOWRAP><B>Total Unshipped:</B></TD><TD ALIGN=RIGHT><%= ordersPosted - ordersShipped %>
        /<%= status.getPostedOrdersLines() - status.getShippedOrdersLines() %>
        /<%= status.getPostedOrdersUnits() - status.getShippedOrdersUnits() %></TD></TR><TR>
        <TD ALIGN=LEFT VALIGN=TOP NOWRAP><B>Total Picked(Unpacked):</B></TD><TD
            ALIGN=RIGHT><%= status.getPickedUnpackedOrders() %>
        /<%= status.getPickedOrdersLines() - status.getPackedOrdersLines()%>
        /<%= status.getPickedOrdersUnits() - status.getPackedOrdersUnits() %></TD></TR><TR>
        <TD ALIGN=LEFT VALIGN=TOP NOWRAP><B>Total Packed(Unshipped):</B></TD><TD
            ALIGN=RIGHT><%= status.getPackedUnshippedOrders() %>
        /<%= status.getPackedOrdersLines() - status.getShippedOrdersLines()%>
        /<%= status.getPackedOrdersUnits() - status.getShippedOrdersUnits() %></TD></TR><TR>
        <TD ALIGN=LEFT VALIGN=TOP NOWRAP><B>Total Shipped:</B></TD><TD ALIGN=RIGHT><%= ordersShipped %>
        /<%= status.getShippedOrdersLines() %>/<%= status.getShippedOrdersUnits() %>

    </TD></TR></TABLE>
    <A HREF="/internal/warehouse/admin/charts.jsp" target="_chartwindow"><IMG
            SRC="/internal/warehouse/admin/orderHistoryChart.jsp" width="200" height="125" border=0><BR>
        <IMG SRC="/internal/warehouse/admin/orderPickHistoryChart.jsp" width="200" height="125"border=0><BR>
        <IMG SRC="/internal/warehouse/admin/orderShipHistoryChart.jsp" width="200" height="125"border=0>
    </A>

    <P>
</TD>
    <TD width=99% valign=top align=center>
        <%
            if ("Client".equals(request.getParameter("type"))) {
        %>
        <jsp:include page="ordersbyclient.jsp"/>
        <% } else if ("Method".equals(request.getParameter("type"))) { %>
        <jsp:include page="ordersbymethod.jsp"/>
        <% } else {%>
        <jsp:include page="ordersbysla.jsp"/>

        <% } %>
    </TD></TR></TABLE>


<P>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>

<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>
