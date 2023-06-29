<%@ page import="com.owd.core.MailAddressValidator,
                 com.owd.core.business.Client,
                 com.owd.core.business.user.UserFactory,
                 com.owd.extranet.servlet.ExtranetManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %>
<%@ page import="com.owd.hibernate.generated.OwdUser" %>
<%

	String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
	if(user_client_id == null) user_client_id = "-1";
	if(user_client_id.equals("")) user_client_id = "-1";

    OwdUser ou = UserFactory.getOwdUserFromLogin(request.getUserPrincipal().getName());
    boolean mailOK = false;


       try {
                    MailAddressValidator.validate(ou.getEmail());
           mailOK = true;
                } catch (Exception ea) {

           request.setAttribute("error",ea.getMessage());
           
                  request.getRequestDispatcher("getemail.jsp").forward(request, response);
                }

       if(mailOK)
       {


%>
<%

    ExtranetServlet.authenticateUser(request);
    request.setAttribute("clientselector","<FORM METHOD=POST ACTION=\""+ request.getContextPath() + ExtranetServlet.kExtranetURLPath +"\">"+ ExtranetServlet.getClientSelector(request) +"</FORM>");
    request.setAttribute("areatitle","Client Tools");
%>
<jsp:include page="/extranettop.jsp" />
<p>

</p>
<div id="main" style="padding-left:17;">
    <%
        if(!("1".equals(request.getSession(true).getAttribute("updateWarning"))))
        {
       request.getSession(true).setAttribute("updateWarning","1");

    %>
    <div class="blocks">
        <P>&nbsp;</P>
    <P>Welcome to the updated OWD Extranet! Most changes are just in appearance, but this update is a first step in an improving system.</P>
    <P>&nbsp;</P>
    <P>
    There are only two functional changes to be aware of: first, the "Home" icon at the upper right will always bring you back to this page. Second, the Orders, Inventory, Coupons and Suppliers sections are now all independent, so you can work on orders in one window/tab and inventory in another without issue.
    </P>
        <P>&nbsp;</P>
        <P>Please send any feedback or suggestions to <a href="mailto:casetracker@owd.com">casetracker@owd.com</a>.</P>

    </div>
    <%
        }
    %>
<div class="blocks">
    <div class="block1">
        <h2 class="segment">Reporting</h2>
        <ul class="tools">
            <li><a href="<%= request.getContextPath()+"/accounts/index.jsp"%>"><b>Shipping Account Statements</b></a><p>Activity and balance statements</li>
            <li><a href="<%= request.getContextPath()+"/adhoc"%>"><b>Standard OWD Reports</b></a><p>Standard OWD Reporting Tools</p></li>
            <li><a href="http://reports.owd.com/logiadhoc/" target="_blank" name="New Reports (Adhoc)"><b>New Reports (Adhoc)</b></a><p>Next Version of our Reports. Start using!!</p></li>
            <li><a href="<%= request.getContextPath()+"/clienttools/reports/index.jsp"%>"><b>Custom Reports</b></a><p>Reports that are highly customized or unavailable through standard system</p></li>
        </ul>
    </div>

    <div class="block2">
        <h2 class="segment">Fulfillment</h2>
        <ul class="tools">
            <li><a href="<%= request.getContextPath()+"/extranet/extranet.jsp?"+ExtranetServlet.kParamAdminAction+"="+ExtranetServlet.kParamChangeMgrAction +"&"+ ExtranetServlet.kParamChangeMgrTo+"="+ ExtranetManager.kOrdersMgrName %>"><b>Orders</b></a><p>Order entry, editing and research</p></li>
            <li><a href="<%= request.getContextPath()+"/extranet/extranet.jsp?"+ExtranetServlet.kParamAdminAction+"="+ ExtranetServlet.kParamChangeMgrAction +"&"+  ExtranetServlet.kParamChangeMgrTo+"="+  ExtranetManager.kInvMgrName %>"><b>Inventory</b></a><p>Inventory/SKU entry, editing and research</p></li>
            <li><a href="<%= request.getContextPath()+"/warehouse/asn/edit"%>"><b>ASN Management</b></a><p>Manage and review ASNs and receive activity</p></li>
            <li><a href="<%= request.getContextPath()+"/clienttools/autoship/edit"%>"><b>Subscriptions/Autoships</b></a><p>Manage recurring orders</p></li>
            <li><a href="<%= request.getContextPath()+"/extranet/extranet.jsp?"+ExtranetServlet.kParamAdminAction+"="+ ExtranetServlet.kParamChangeMgrAction +"&"+  ExtranetServlet.kParamChangeMgrTo+"="+  ExtranetManager.kCouponsMgrName %>"><b>Coupons (for OWD hosted carts only)</b></a><p>The main OWD Extranet for managing orders and inventory</p></li>
            <li><a href="<%= request.getContextPath()+"/extranet/extranet.jsp?"+ExtranetServlet.kParamAdminAction+"="+ ExtranetServlet.kParamChangeMgrAction+"&"+  ExtranetServlet.kParamChangeMgrTo+"="+ ExtranetManager.kSuppliersMgrName %>"><b>Suppliers</b></a><p>The main OWD Extranet for managing orders and inventory</p></li>

    </div>

    <div class="block3">
        <h2 class="segment">Tools and Reference</h2>
        <ul class="tools">
            <li><a href="<%= request.getContextPath()+"/clienttools/shipmethods.jsp"%>"><b>Shipping Methods</b></a>
                <p>Official shipping method names and codes</p></li>
            </p></li><li><a href="<%= request.getContextPath()+"/clienttools/countries.jsp"%>"><b>International Country Names</b></a>
            <p>Official country names for international destinations</p></li>
            </p></li><li><a href="<%= request.getContextPath()+"/clienttools/shippingtools/index.jsp"%>"><b>Shipping Tools and Resources</b></a>
            <p>Track shipments, calculate rates</p></li>
                <% if (user_client_id.equals("0"))
{ String user = request.getUserPrincipal().getName();
        OwdUser userObj = UserFactory.getOwdUserFromLogin(user);

    %>
            <LI><A HREF="clients.jsp"><B>Clients</B></A><p>Official client names and internal ID numbers</p> </li>

                    <%       }    %>
            <LI><a href="https://s3-us-west-2.amazonaws.com/owdmailimage/OWD+Price+Sheet.pdf"><b>Supply Price Sheet </b></a><p>Price sheet for standard OWD supplies</p></LI>
            <LI><a href="https://docs.google.com/forms/d/e/1FAIpQLScDvH-3oUMkGkp7r7a113e0ht7XSpVC4iclPP29syIcW_gUxQ/viewform?usp=sf_link"><b>Accuracy Tracking</b></a><p>Error Reporting</p></LI>
       </ul>
    </div>
</div>


<hr>
<b>Help: </b>Email <a href="mailto:casetracker@owd.com">casetracker@owd.com</a> or call the One World office at 605-845-7172
<BR>Copyright 2000-2012, One World Direct, All Rights Reserved.
</div>

</body></html>

<% } %>