<%@ page import="com.owd.core.business.order.OrderUtilities"%>
<%@ page import="com.owd.extranet.servlet.ExtranetManager" %>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %>
<%@ page import="com.owd.core.TagUtilities" %>
<%@ page import="org.ditchnet.xml.Xhtml" %>
<%@ page import="javax.swing.text.html.HTML" %>
<%
      com.owd.core.business.order.OrderStatus order = (com.owd.core.business.order.OrderStatus)request.getAttribute(com.owd.extranet.servlet.OrdersManager.kCurrentOrder);
    java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");
    com.owd.core.business.Contact shipc = order.shipping.shipContact;
    com.owd.core.business.Address shipa = order.shipping.shipAddress;
    com.owd.core.business.order.ShippingInfo shipping = order.shipping;

    boolean isInternalUser = ExtranetServlet.getSessionFlag(request,ExtranetServlet.kExtranetInternalFlag);
%>
<P>  
    
<B>Edit Shipping Information for order <%= order.orderReference %> (<%= order.OWDorderReference %>)</B>
<HR><fontx COLOR=RED><B><%= request.getAttribute("formerror")==null?"":request.getAttribute("formerror") %></B> <FORM METHOD=POST ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.extranet.servlet.OrdersManager.kParamOrderEditShippingAction%>&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>" value="<%= ExtranetServlet.kParamChangeMgrAction %>" />
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>" value="<%= ExtranetManager.kOrdersMgrName %>" />
    <INPUT TYPE=HIDDEN NAME="editshippingsave" VALUE="1">
<TABLE width=100% border=0>
<TR><TD width=30% align=right><fontx SIZE=-1><B>Name :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipc.name VALUE="<%= shipc.name %>"></TD></TR>
<TR><TD width=30% align=right><fontx SIZE=-1><B>Company Name :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipa.company_name VALUE="<%= shipa.company_name %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Address Line 1 :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipa.address_one VALUE="<%= shipa.address_one %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Address Line 2 :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipa.address_two VALUE="<%= shipa.address_two %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>City :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipa.city VALUE="<%= shipa.city %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>State :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipa.state VALUE="<%= shipa.state %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Postal Code :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipa.zip VALUE="<%= shipa.zip %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Country :</TD><TD><%= com.owd.core.business.order.OrderUtilities.getCountryList().getHTMLSelect(shipa.country, "shipa.country") %></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Email :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipc.email VALUE="<%= shipc.email %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Phone :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipc.phone VALUE="<%= shipc.phone %>"></TD></TR>
<%
if(order.getTagMap().containsKey(TagUtilities.kVendorComplianceIDReference)){
    if(order.getTagMap().get(TagUtilities.kVendorComplianceIDReference).equals("16")){
        if(order.getTagMap().containsKey(TagUtilities.kEDIZapposDN)){ %>

            <TR><TD align=right><fontx SIZE=-1><B>Zappos DN :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipc.tagZDN VALUE="<%= order.getTagMap().get(TagUtilities.kEDIZapposDN) %>"></TD></TR>

         <% } else { %>
             <TR><TD align=right><fontx SIZE=-1><B>Zappos DN :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipc.tagZDN VALUE=""></TD></TR>
         <% } %>

    <% } else if(order.getTagMap().get(TagUtilities.kVendorComplianceIDReference).equals("1")){
      if(order.getTagMap().containsKey(TagUtilities.kEDIAmazonARN)){ %>

        <TR><TD align=right><fontx SIZE=-1><B>Amazon ARN :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipc.tagARN VALUE="<%= order.getTagMap().get(TagUtilities.kEDIAmazonARN) %>"></TD></TR>

        <% } else { %>
            <TR><TD align=right><fontx SIZE=-1><B>Amazon ARN :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipc.tagARN VALUE=""></TD></TR>
        <% } %>

     <%} else if(order.getTagMap().get(TagUtilities.kVendorComplianceIDReference).equals("4")){
          if(order.getTagMap().containsKey(TagUtilities.kEDIDicksASIDN)){ %>

            <TR><TD align=right><fontx SIZE=-1><B>Dick&#39;s ASIDN :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipc.tagDASIDN VALUE="<%= order.getTagMap().get(TagUtilities.kEDIDicksASIDN) %>"></TD></TR>

            <% } else { %>
                <TR><TD align=right><fontx SIZE=-1><B>Dick&#39;s ASIDN :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipc.tagDASIDN VALUE=""></TD></TR>
             <% } %>
        <% } %>

<% } %>


    <TR><TD align=right valign=top><fontx SIZE=-1><B>Carrier :</TD><TD><%= OrderUtilities.getServiceList().getHTMLSelect(shipping.carr_service,"shipping.carr_service") %></TD></TR>
<TR><TD align=right valign=top><fontx SIZE=-1><B>Carrier Billing :</TD><TD><%= OrderUtilities.getTermsList().getHTMLSelect(shipping.carr_freight_terms,"shipping.carr_freight_terms") %><BR><fontx size=-2>Normally is "Prepaid". Select "Third Party Billing" and provide your account information below to bill a third party. If you have made arrangements with One World to have your packages billed to your own account by default, you do not need to fill out this section. Only select third party billing when the package should be billed to someone other than you or OWD.<BR>Note for International Orders:� You must use a USA account number if you wish to bill Third Party and ship to another country.� You can bill Freight Collect if the account number is that of the receiving party.
</TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Shipper Account ID :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipping.third_party_refnum VALUE="<%= shipping.third_party_refnum %>"><BR><fontx size=-2>Required if "Third Party Billing" is chosen above - otherwise, leave blank.</TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Declared Value :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=shipping.declared_value VALUE="<%= shipping.declared_value %>">&nbsp;
<fontx SIZE=-1><B>AES ITN:<INPUT SIZE=25 TYPE=TEXT NAME=order.aesItn VALUE="<%= order.aesItn %>"></TD></TR>
    <TR><TD align=right valign=top><fontx SIZE=-1><B>International Tax and Duty:</TD><TD><%= OrderUtilities.getIntlTaxAndDutyList().getHTMLSelect(order.dduDDP,"order.dduDDP") %></TD></TR>
<TR><TD align=right valign=top><fontx SIZE=-1><B></B></TD><TD valign=top><fontx SIZE=-1><INPUT TYPE=CHECKBOX NAME=shipping.ss_saturday VALUE=1 <%= (shipping.ss_saturday.equals("1")?"CHECKED":"") %>><B>Request Saturday Delivery</B><BR><fontx size=-2>Check this box to force Saturday shipping. This option only applies to services that deliver on specific days (overnight, 2-day, 3-day, etc.) where the normal delivery date is a Saturday. Carriers will assign an additional cost for this option. If the normal delivery date is a Saturday and this option is not checked, delivery will occur the following Monday.</TD></TR>
<TR><TD align=right valign=top><fontx SIZE=-1><B></B></TD><TD valign=top><fontx SIZE=-1><INPUT TYPE=CHECKBOX NAME=shipping.ss_proof_delivery VALUE=1 <%= (shipping.ss_proof_delivery.equals("1")?"CHECKED":"") %>><B>Request Signature Confirmation</B><BR><fontx size=-2>Check this box to force Signature Confirmation for delivery. This option only applies to services that offer signature services - this request will be ignored for other services. Carriers will assign an additional cost for this option.</TD></TR>

<TR><TD colspan=2><HR></TD></TR>
    <% if(isInternalUser && order.is_posted && !order.is_void) { %>
<TR><TD colspan=2><INPUT TYPE=CHECKBOX NAME=reprint  VALUE=1>Check here to reprint packing slip with new ship address - leave unchecked to update record but keep current slip. If the order is unpicked, it will reprint regardless of your choice.</TD></TR>
    <% } %>


    <TR><TD colspan=2><INPUT TYPE=CHECKBOX NAME=shipping.avsOveride  VALUE=1 <%= (shipping.avs_overide.equals("1")?"CHECKED":"")%>>Check here to bypass AVS. We will ship using address above with no changes.</TD></TR>


<TR><TD><INPUT TYPE=SUBMIT NAME=submit VALUE="Save Changes"></TD><TD></TD></TR>
</TABLE>