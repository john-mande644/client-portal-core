<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus,
                 java.text.DecimalFormat,
                 java.util.*,
                 java.text.DateFormat,
                 com.owd.core.managers.ConnectionManager,
                 java.text.SimpleDateFormat,
                 java.sql.ResultSet,
                 com.owd.web.internal.navigation.*,
                 org.hibernate.Session,
                 org.apache.commons.beanutils.RowSetDynaClass,
                 com.owd.core.business.order.OrderManager,
                 com.owd.core.business.order.OrderFactory,
                 com.owd.core.business.user.UserFactory,
                 com.owd.hibernate.HibUtils,
                 com.owd.web.internal.warehouse.admin.WarehouseHoldUtilities,
                 java.sql.PreparedStatement" %>
<%@ page import="com.owd.hibernate.*" %>
<%@ page import="com.owd.hibernate.generated.OwdUser" %>
<%@ page import="com.owd.hibernate.generated.OwdOrder" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
    String owdref = request.getParameter("owdref");

    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Reprint Order OWD:" + owdref, request);
    try {

        OwdUser user = UserFactory.getOwdUserFromLogin(request.getRemoteUser());

        OwdOrder order = OrderFactory.getOwdOrderFromOwdReference(owdref);

        if (null != request.getParameter("doprint")) {
            try {


                PreparedStatement ps = HibernateSession.getPreparedStatement("exec reprint_order_nopost " + order.getOrderId());
                ps.execute();
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

                response.sendRedirect("/internal/warehouse/admin/index.jsp");
            } catch (Exception ex) {
                request.setAttribute("errormessage", ex.getMessage());
            }
        }

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

<FORM action="reprint.jsp" method=POST>
    <H1>Reprint Order Request</H1><HR>

    <H3><B>OWD Reference:</B> <%= owdref %></H3>

    <H3><B>Client Reference:</B> <%= order.getClient().getCompanyName() %> - <%= order.getOrderRefnum() %></H3>

    <H3><B>Ship Method:</B> <%= order.getShipinfo().getCarrService() %></H3>
    <INPUT TYPE=HIDDEN NAME=doprint VALUE=1>
    <INPUT TYPE=HIDDEN NAME=owdref VALUE=<%= owdref %>>

    <div class="FormSubBlock">
        <table cellspacing="0">
            <tr>

                <th colspan="2">Confirm Reprint Request</th>
            </tr>
            <tr>
                <td colspan="2">
                    <P>&nbsp;</P>

                    <p style="margin: 0px; font-size: 12px; ">Reprinting this order will: <P>&nbsp;</P>
                        <UL class="xxx"><LI style="margin: 0px; font-size: 12px; ">&#187; Invalidate any previously
                            printed packing slips
                            <UL class="xxx"><LI style="margin: 0px; font-size: 12px; ">&#187; Reset the pick status to
                                unpicked
                                <LI style="margin: 0px; font-size: 12px; ">&#187; Cause this order to be placed in the
                                    printing queue for printing at its normal location
                                <UL><P>&nbsp;</P>Reprinting this order will <B>NOT</B>:<P>&nbsp;</P>
                                    <UL><LI style="margin: 0px; font-size: 12px; ">&#187;Change or adjust inventory
                                        levels
                                        <LI style="margin: 0px; font-size: 12px; ">&#187;Change the original post date
                                            for the order
                                        <LI style="margin: 0px; font-size: 12px; ">&#187;Change the SLA date for this
                                            order
                                    </UL></p>
                </td>
            </tr>


        </table>
    </div>

    <div class="FormSubBlock">
        <table cellspacing="0">
            <tr>

                <th colspan="2">Click the Save Changes button below to reprint this order.<P>Click the Cancel link at
                    the top of this page to go back.</th>
            </tr>


        </table>
    </div>


    <div style="background: #ffffff; border-top: 1px solid #ccc; padding: 10px 15px;
                     margin-top: 25px;">
        <input type="image" src="/internal/images/b-save_changes.gif" alt="Save changes"
               onClick="return validateForm();"/>
    </div>
    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY>
</HTML>

<% } catch (Exception ex) {
    ex.printStackTrace();
    request.setAttribute("errormessage", ex.getMessage());
    request.getRequestDispatcher("/warehouse/admin/index.jsp").forward(request, response);
} finally {
    HibernateSession.closeSession();
}%>