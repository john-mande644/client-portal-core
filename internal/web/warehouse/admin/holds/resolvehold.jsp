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
<%@ taglib uri="http://ditchnet.org/jsp-tabs-taglib" prefix="tab" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String owdref = request.getParameter("owdref");
    System.out.println("rtop start");
    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Warehouse Hold on Order OWD:" + owdref + "", request);
    try {

            System.out.println("rtop user");
        OwdUser user = UserFactory.getOwdUserFromLogin(request.getRemoteUser());

            System.out.println("rtop order");
        OwdOrder order = OrderFactory.getOwdOrderFromOwdReference(owdref);

            System.out.println("rtop hold");
        OwdOrderShipHold holder = order.getHoldinfo();


            System.out.println("rtop check");
        if (holder != null) {
            request.getSession(true).setAttribute("ordershiphold.currshiphold", holder);
        }

            System.out.println("rtop check1");
        if (request.getSession(true).getAttribute("ordershiphold.currshiphold") == null) {
            holder = OrderFactory.getNewOwdOrderShipHold();
            request.getSession(true).setAttribute("ordershiphold.currshiphold", holder);

        } else {
            holder = (OwdOrderShipHold) request.getSession(true).getAttribute("ordershiphold.currshiphold");
        }

            System.out.println("rtop check2");
        if (holder.getIsOnWhHold().intValue() == 0) {
            request.getRequestDispatcher("/warehouse/admin/sethold.jsp").forward(request, response);
        } else {

            System.out.println("rtop check2b");
            if(holder.getNeedsReview()==null){holder.setNeedsReview(new Integer(1));}

            if (null != request.getParameter("sethold")) {

                Integer oldReview = new Integer(holder.getNeedsReview().intValue());

                if("1".equals(""+new Integer((request.getParameter("sethold")))))
                {

            System.out.println("rtop check2c");
                 //resolve existing hold
                try {
                    holder.setNeedsReview(new Integer(0));
                    WarehouseHoldUtilities.resolveHeldOrder(request, user, order, holder);
                    request.getSession(true).setAttribute("ordershiphold.currshiphold", null);
                    response.sendRedirect("/internal/warehouse/admin/index.jsp");
                } catch (Exception ex) {
                    request.setAttribute("errormessage", ex.getMessage());
                }
                }else
                {

            System.out.println("rtop check2d");
                    //confirm existing hold and send notifications
                  try {
                      holder.setNeedsReview(new Integer(0));
                    WarehouseHoldUtilities.setPostedOrderOnHold(request, user, order, holder);
                    request.getSession(true).setAttribute("ordershiphold.currshiphold", null);
                    response.sendRedirect("/internal/warehouse/admin/index.jsp");
                } catch (Exception ex) {
                        holder.setNeedsReview(oldReview);
                    request.setAttribute("errormessage", ex.getMessage());
                }
                }
            }

            System.out.println("rtop check3");
            request.setAttribute("holder", holder);
    request.setAttribute("order", order);
    request.setAttribute("user", user);
            System.out.println("rtop done");
%>


<HTML>
<HEAD>
<tab:tabConfig />
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
   <H1>Warehouse Hold</H1><HR>
  <H3><B>OWD Reference:</B> ${order.orderNum}</H3>

  <H3><B>Client Reference:</B> ${order.client.companyName} - ${order.orderRefnum}</H3>

  <H3><B>Ship Method:</B> ${order.shipinfo.carrService}</H3>

  <H3><B>Current Status:</B> ${order.orderStatus}</H3>

  <H3><B>Hold Type:</B> ${holder.whHoldReason}</H3>

  <H3><B>Hold Notes:</B> ${holder.whHoldNotes}</H3>
 <% System.out.println("start tabs");%>
<tab:tabContainer id="resolvehold-container">

      <tab:tabPane id="DCHolds" tabTitle="Resolve DC Hold" >

   <FORM action="resolvehold.jsp" method=POST>

    <INPUT TYPE=HIDDEN NAME=sethold VALUE=1>
    <INPUT TYPE=HIDDEN NAME=owdref VALUE="${order.orderNum}">

    <div class="FormSubBlock">
        <table cellspacing="0">
            <tr>

                <th colspan="2">Report Resolution</th>
            </tr>
            <tr>
                <td colspan="2">
                    <p style="margin: 0px; font-size: 10px; color: #666;">Indicate how the problem was resolved</p>
                </td>
            </tr>
            <tr>

                <td colspan="2">

                    <UL>
                        <LI><INPUT TYPE=RADIO NAME=resolution_type
                                   VALUE="Order Edited" <c:if test="${holder.resolutionType == 'Order Edited'}">CHECKED</c:if>>
                            Order edited without reprint
                        <LI><INPUT TYPE=RADIO NAME=resolution_type
                                   VALUE="Order Reprinted/Reposted"
                            <c:if test="${holder.resolutionType == 'Order Reprinted/Reposted'}">CHECKED</c:if>>
                            Order edited and reprinted/reposted
                        <LI><INPUT TYPE=RADIO NAME=resolution_type
                                   VALUE="Inventory Located"
                            <c:if test="${holder.resolutionType == 'Inventory Located'}">CHECKED</c:if>>
                            Inventory was found after further investigation
                        <LI><INPUT TYPE=RADIO NAME=resolution_type
                                   VALUE="Order Cancelled"
                            <c:if test="${holder.resolutionType == 'Order Cancelled'}">CHECKED</c:if>>
                            Order cancelled
                        <LI><INPUT TYPE=RADIO NAME=resolution_type
                            <c:if test="${holder.resolutionType == 'IT Resolved'}">CHECKED</c:if>>
                            IT action resolved problem
                        <LI><INPUT TYPE=RADIO NAME=resolution_type
                                   VALUE="Other"  <c:if test="${holder.resolutionType == 'Other'}">CHECKED</c:if>> Other

                        </LI>
                        </UL>

                </td>

            </tr>
            <tr><td>Notes/Comments:</td><td align=left><TEXTAREA NAME="res_note" rows=5
                                                        cols=40>${holder.resNote}</TEXTAREA>
                <BR>

                <p style="margin: 0px; font-size: 10px; color: #666;">Please add a note for all resolutions</p>

            </td></tr>
        </table>
    </div>


    <div style="background: #ffffff; border-top: 1px solid #ccc; padding: 10px 15px;
                     margin-top: 25px;">
        <input type="image" src="/internal/images/b-save_changes.gif" alt="Save changes"
               onClick="return validateForm();"/>
    </div>
       </FORM>
 </tab:tabPane>
    <c:if test="${holder.needsReview == 1}">
    <tab:tabPane id="Confirm" tabTitle="Confirm DC Hold">
            <FORM action="resolvehold.jsp" method=POST>
                <INPUT TYPE=HIDDEN NAME=sethold VALUE=2>
                <INPUT TYPE=HIDDEN NAME=owdref VALUE="${order.orderNum}">

                <INPUT TYPE=HIDDEN NAME=wh_hold_reason VALUE="${holder.whHoldReason}">



                <div class="FormSubBlock">
                     <table cellspacing="0"> <tr>

                <th colspan="2">Confirm Hold and Send Notifications</th>
            </tr>
            <tr>
                <td colspan="2">
                    <p style="margin: 0px; font-size: 10px; color: #666;">Verify that the hold is valid and send email notifications to others</p>
                </td>
            </tr>
                    </table>
                    <table cellspacing="0">
                        <tr>

                            <th colspan="2">Notification</th>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <p style="margin: 0px; font-size: 10px; color: #666;">Send an email reporting this
                                    problem to the
                                    following people (select as many as needed)</p>
                            </td>
                        </tr>
                        <tr>

                            <td colspan="2">

                                <UL>
                                    <LI><INPUT TYPE=checkbox NAME=notifyAM
                                               VALUE="1"
                                    <c:if test="${fn:length(holder.notifyAM)>0}"> CHECKED</c:if>> Account Manager
                                        (${order.client.amEmail})
                                    <LI><INPUT TYPE=checkbox NAME=notifyIT
                                               VALUE="1"
                                    <c:if test="${fn:length(holder.notifyIT)>0}"> CHECKED</c:if>> IT
                                        (casetracker@owd.com)
                                    <LI><INPUT TYPE=checkbox NAME=notifyEmail VALUE="1" <c:if test="${fn:length(holder.notifyEmail)>0}"> CHECKED</c:if>>
                                    Other Email: <INPUT TYPE=TEXT NAME=notifyEmailAddress VALUE="${holder.notifyEmail}">

                                    </LI>
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
                                <p style="margin: 0px; font-size: 10px; color: #666;">Notifications of this problem will
                                    include
                                    instructions to contact the following people, as indicated, when the issue is
                                    resolved.<BR>Email
                                    notifications of problem resolution will be sent automatically if requested.</p>
                            </td>
                        </tr>
                        <tr>

                            <td colspan="2">

                                <UL>
                                    <LI><INPUT TYPE=checkbox NAME=resNotifyAM
                                               VALUE=1
                                    <c:if test="${fn:length(holder.resNotifyAM)>0}"> CHECKED</c:if>> Account Manager
                                        (${order.client.amEmail})
                                    <LI><INPUT TYPE=checkbox NAME=resNotifyPhone
                                               VALUE=1
                                    <c:if test="${fn:length(holder.resNotifyPhone)>0}"> CHECKED</c:if>> By Phone (type
                                        name
                                        and number): <INPUT TYPE=TEXT size=30 NAME=resNotifyPhoneText
                                                            VALUE="${holder.resNotifyPhone}">
                                    <LI><INPUT TYPE=checkbox NAME=resNotifyEmail
                                               VALUE=1
                                    <c:if test="${fn:length(holder.resNotifyEmail)>0}"> CHECKED</c:if>> Other Email:
                                        <INPUT
                                                TYPE=TEXT NAME=resNotifyEmailAddress
                                                VALUE="${holder.resNotifyEmail}">
                                    <LI><INPUT TYPE=checkbox NAME=resNotifySelf
                                               VALUE=1
                                    <c:if test="${fn:length(holder.resNotifyUser)>0}"> CHECKED</c:if>> Your Email
                                        (${user.email})
                                    </LI>
                                    </UL>
                            </td>

                        </tr>
                        <tr><td width="1%">Notes/Comments:</td><td align=left><TEXTAREA NAME="notes" rows=5
                                                                             cols=40>${holder.whHoldNotes}</TEXTAREA>
                            <BR>

                            <p style="margin: 0px; font-size: 10px; color: #666;">Edit DC Hold Notes</p>

                        </td></tr>
                    </table>
                </div>

                <div style="background: #ffffff; border-top: 1px solid #ccc; padding: 10px 15px;
                     margin-top: 25px;">
                    <input type="image" src="/internal/images/b-save_changes.gif" alt="Save changes"
                           onClick="return validateForm();"/>
                </div>

            </form>


    </tab:tabPane>
    </c:if>

  </tab:tabContainer>
     <% System.out.println("end tabs");%>
<jsp:include page="/includes/owdbottom.jsp"/>
</BODY>
</HTML>

<% }
} catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>