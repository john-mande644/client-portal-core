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

    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Setting Order OWD:" + owdref + " On Hold", request);
    try {

        OwdUser user = UserFactory.getOwdUserFromLogin(request.getRemoteUser());

        OwdOrder order = OrderFactory.getOwdOrderFromOwdReference(owdref);

        OwdOrderShipHold holder = order.getHoldinfo();


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
            <a class="admin" href="/internal/warehouse/admin">Cancel and go back</a>&nbsp;&nbsp;|&nbsp;&nbsp;
            <A class="admin" HREF="resolvehold.jsp?owdref=<%= order.getOrderNum()%>">Resolve this problem report</A>

        </h3>
    </div>

    <font color=red>
        <B><%= (request.getAttribute("errormessage") != null ? "<div class=\"AlertBad\">" + request.getAttribute("errormessage") + "</div>" : "") %></B>
    </font>
    <%


    %>

    <H1>Order on DC Hold</H1><HR>

    <H3><B>OWD Reference:</B> <%= owdref %></H3>

    <H3><B>Client Reference:</B> <%= order.getClient().getCompanyName() %> - <%= order.getOrderRefnum() %></H3>

    <H3><B>Ship Method:</B> <%= order.getShipinfo().getCarrService() %></H3>
    <INPUT TYPE=HIDDEN NAME=sethold VALUE=1>
    <INPUT TYPE=HIDDEN NAME=owdref VALUE=<%= owdref %>>

    <table cellspacing="0">
        <tr>

            <th colspan="2">Order already on hold</th>
        </tr>
        <tr>
            <td colspan="2">
                <p style="margin: 0px; font-size: 10px; color: #666;">This order has already been placed on hold - you
                    will need to resolve this report before any changes can be made</p>
            </td>
        </tr>
        <tr>

            <td align=right><B>Reported Problem:</B></td><td><%=holder.getWhHoldReason() %></td>

        </tr>
        <tr><td align=right><B>Notes:</B></td><td align=left><%= holder.getWhHoldNotes()%>

        </td></tr>     <tr><td align=right><B>Reported:</B></td><td align=left><%= holder.getCreatedDate()%>
        by <%= holder.getCreatedBy() %>

    </td></tr>
    </table>


    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY></HTML>

<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>