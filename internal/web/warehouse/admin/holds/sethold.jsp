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
        if (holder != null) {
            //request.getSession(true).setAttribute("ordershiphold.currshiphold",holder);
            if (holder.getIsOnWhHold().intValue() == 1) {
                request.getRequestDispatcher("/warehouse/admin/holds/alreadyheld.jsp").forward(request, response);
            } else {
                holder = OrderFactory.getNewOwdOrderShipHold();
                request.getSession(true).setAttribute("ordershiphold.currshiphold", holder);
            }
        }

        if (request.getSession(true).getAttribute("ordershiphold.currshiphold") == null) {
            holder = OrderFactory.getNewOwdOrderShipHold();
            request.getSession(true).setAttribute("ordershiphold.currshiphold", holder);

        } else {
            holder = (OwdOrderShipHold) request.getSession(true).getAttribute("ordershiphold.currshiphold");
        }

        if (holder.getIsOnWhHold().intValue() == 1) {
            request.getRequestDispatcher("/warehouse/admin/holds/alreadyheld.jsp").forward(request, response);
        } else {

            if (null != request.getParameter("sethold")) {
                try {
                    WarehouseHoldUtilities.setPostedOrderOnHold(request, user, order, holder);
                    request.getSession(true).setAttribute("ordershiphold.currshiphold", null);
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

<FORM action="sethold.jsp" method=POST>
    <H1>Place Order on Warehouse Hold</H1><HR>

    <H3><B>OWD Reference:</B> <%= owdref %></H3>

    <H3><B>Client Reference:</B> <%= order.getClient().getCompanyName() %> - <%= order.getOrderRefnum() %></H3>

    <H3><B>Ship Method:</B> <%= order.getShipinfo().getCarrService() %></H3>
    <INPUT TYPE=HIDDEN NAME=sethold VALUE=1>
    <INPUT TYPE=HIDDEN NAME=owdref VALUE=<%= owdref %>>

    <div class="FormSubBlock">
        <table cellspacing="0">
            <tr>

                <th colspan="2">Identify Issue</th>
            </tr>
            <tr>
                <td colspan="2">
                    <p style="margin: 0px; font-size: 10px; color: #666;">Indicate the problem causing the shipment to
                        be held - you must choose one option</p>
                </td>
            </tr>
            <tr>

                <td colspan=2>

                    <UL>
                        <LI><INPUT TYPE=RADIO NAME=wh_hold_reason
                                   VALUE="Invalid Address" <%= "Invalid Address".equals(holder.getWhHoldReason())?"CHECKED":"" %>>
                            Invalid Address
                        <LI><INPUT TYPE=RADIO NAME=wh_hold_reason
                                   VALUE="Invalid Ship Method (for address)" <%= "Invalid Ship Method (for address)".equals(holder.getWhHoldReason())?"CHECKED":"" %>>
                            Address does not match ship method
                        <LI><INPUT TYPE=RADIO NAME=wh_hold_reason
                                   VALUE="Invalid Ship Method (for package)" <%= "Invalid Ship Method (for package)".equals(holder.getWhHoldReason())?"CHECKED":"" %>>
                            Shipment (size, weight, type) does not match ship method
                        <LI><INPUT TYPE=RADIO NAME=wh_hold_reason
                                   VALUE="Inventory Stockout" <%= "Inventory Stockout".equals(holder.getWhHoldReason())?"CHECKED":"" %>>
                            Unable to fulfill due to inventory missing/not found
                        <LI><INPUT TYPE=RADIO NAME=wh_hold_reason
                                   VALUE="Manifesting Error" <%= "Manifesting Error".equals(holder.getWhHoldReason())?"CHECKED":"" %>>
                            Shipment will not manifest properly (no label, strange error, etc.)
                        <LI><INPUT TYPE=RADIO NAME=wh_hold_reason
                                   VALUE="Work Order" <%= "Work ORder".equals(holder.getWhHoldReason())?"CHECKED":"" %>>
                            Order is being worked on for a Work Order.(place work order info in notes)
                        <LI><INPUT TYPE=RADIO NAME=wh_hold_reason
                                   VALUE="Other" <%= "Other".equals(holder.getWhHoldReason())?"CHECKED":"" %>> Other
                            (describe fully in notes section)
                        </UL>

                </td>

            </tr>
            <tr><td>Notes:</td><td align=left><TEXTAREA NAME="notes" rows=5
                                                        cols=40><%= holder.getWhHoldNotes()%></TEXTAREA>
                <BR>

                <p style="margin: 0px; font-size: 10px; color: #666;">Notes/comments/instructions</p>

            </td></tr>
        </table>
    </div>

    <div class="FormSubBlock">
        <table cellspacing="0">
            <tr>

                <th colspan="2">Notification</th>
            </tr>
            <tr>
                <td colspan="2">
                    <p style="margin: 0px; font-size: 10px; color: #666;">Send an email reporting this problem to the
                        following people (select as many as needed)</p>
                </td>
            </tr>
            <tr>

                <td>

                    <UL>
                        <LI><INPUT TYPE=checkbox NAME=notifyAM
                                   VALUE="1" <%= holder.getNotifyAM().length()>0?"CHECKED":""%>> Account Manager
                            (<%= order.getClient().getAmEmail()%>)
                        <LI><INPUT TYPE=checkbox NAME=notifyIT
                                   VALUE="1" <%= holder.getNotifyIT().length()>0?"CHECKED":""%>> IT
                            (casetracker@owd.com)
                        <LI><INPUT TYPE=checkbox NAME=notifyEmail
                                   VALUE="1" <%= holder.getNotifyEmail().length()>0?"CHECKED":""%>> Other Email: <INPUT
                                TYPE=TEXT NAME=notifyEmailAddress VALUE="<%= holder.getNotifyEmail()%>">
                        <LI><INPUT TYPE=checkbox NAME=notifyUser
                                   VALUE="1" <%= holder.getNotifyUser().length()>0?"CHECKED":""%>> Yourself
                            (<%= user.getEmail() %>)
                        </UL>
                </td>

            </tr>
        </table>
    </div>

    <div class="FormSubBlock">
        <table cellspacing="0">
            <tr>

                <th colspan="2">Resolution Notification</th>
            </tr>
            <tr>
                <td colspan="2">
                    <p style="margin: 0px; font-size: 10px; color: #666;">Notifications of this problem will include
                        instructions to contact the following people, as indicated, when the issue is resolved.<BR>Email
                        notifications of problem resolution will be sent automatically if requested.</p>
                </td>
            </tr>
            <tr>

                <td>

                    <UL>
                        <LI><INPUT TYPE=checkbox NAME=resNotifyAM
                                   VALUE=1 <%= holder.getResNotifyAM().length()>0?"CHECKED":""%>> Account Manager
                            (<%= order.getClient().getAmEmail()%>)
                        <LI><INPUT TYPE=checkbox NAME=resNotifyPhone
                                   VALUE=1 <%= holder.getResNotifyPhone().length()>0?"CHECKED":""%>> By Phone (type name
                            and number): <INPUT TYPE=TEXT NAME=resNotifyPhoneText
                                                VALUE="<%= holder.getResNotifyPhone()%>">
                        <LI><INPUT TYPE=checkbox NAME=resNotifyEmail
                                   VALUE=1 <%= holder.getResNotifyEmail().length()>0?"CHECKED":""%>> Other Email: <INPUT
                                TYPE=TEXT NAME=resNotifyEmailAddress VALUE="<%= holder.getResNotifyEmail()%>">
                        <LI><INPUT TYPE=checkbox NAME=resNotifySelf
                                   VALUE=1 <%= holder.getResNotifyUser().length()>0?"CHECKED":""%>> Yourself
                            (<%= user.getEmail() %>)
                        </UL>
                </td>

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

<% }
} catch (Exception ex) {
    ex.printStackTrace();
    request.setAttribute("errormessage", ex.getMessage());
    request.getRequestDispatcher("/warehouse/admin/index.jsp").forward(request, response);
} finally {
    HibernateSession.closeSession();
}%>