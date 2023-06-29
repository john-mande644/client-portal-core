<%@ page import="com.owd.extranet.servlet.ExtranetManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %>
<%
      com.owd.core.business.order.OrderStatus order = (com.owd.core.business.order.OrderStatus)request.getAttribute(com.owd.extranet.servlet.OrdersManager.kCurrentOrder);
    java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");

%>       
<P>
<B>Issue Call Tag (UPS Return with up to 3 Pickup Attempts) for order <%= order.orderReference %> (<%= order.OWDorderReference %>)</B><BR>
<HR><A HREF="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=edit&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>"><-Go back to order editing page</A></B>
<HR><fontx COLOR=RED><B><%= request.getAttribute("formerror")==null?"":request.getAttribute("formerror") %></B>
<FORM METHOD=POST ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=calltag-request&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>" value="<%= ExtranetServlet.kParamChangeMgrAction %>" />
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>" value="<%= ExtranetManager.kOrdersMgrName %>" />
    <TABLE width=100% border=0>
<TR><TD colspan=2 align=left><B>Pickup Location and Contact (ALL fields are required)</B></TD></TR>
<TR><TD width=30% align=right><fontx SIZE=-1><B>Name :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=ct_name VALUE="<%= request.getAttribute("ct_name") %>"></TD></TR>
<TR><TD width=30% align=right><fontx SIZE=-1><B>Company Name :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=ct_company_name VALUE="<%= request.getAttribute("ct_company_name") %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Address Line 1 :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=ct_address_one VALUE="<%= request.getAttribute("ct_address_one") %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Address Line 2 :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=ct_address_two VALUE="<%= request.getAttribute("ct_address_two") %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>City :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=ct_city VALUE="<%= request.getAttribute("ct_city") %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>State :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=ct_state VALUE="<%= request.getAttribute("ct_state") %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Zip Code :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=ct_zip VALUE="<%= request.getAttribute("ct_zip") %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Email :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=ct_email VALUE="<%= request.getAttribute("ct_email") %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Phone :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=ct_phone VALUE="<%= request.getAttribute("ct_phone") %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Insured Value (optional, up to 100.00 free):</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=ct_declared_value VALUE="<%= request.getAttribute("ct_declared_value") %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Estimated Weight (lbs) :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=ct_package_weight VALUE="<%= request.getAttribute("ct_package_weight")%>"></TD></TR>

<TR><TD colspan=2><HR></TD></TR>
<TR><TD><INPUT TYPE=SUBMIT NAME=submit VALUE="Issue Call Tag">
<BR><fontx size=-1>UPS Return shipments are shipped directly to OWD via Ground service. UPS will contact the customer the next day to schedule pickup.<BR>Normal UPS freight and surcharges apply. Mainland USA only. No APO/PO Box addresses.

</TD><TD></TD></TR>
</TABLE>
    </FORM>
