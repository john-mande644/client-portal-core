<%@ page import="com.owd.extranet.servlet.ExtranetManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %>
<%
      com.owd.core.business.order.OrderStatus order = (com.owd.core.business.order.OrderStatus)request.getAttribute(com.owd.extranet.servlet.OrdersManager.kCurrentOrder);
    java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");
    com.owd.core.business.Contact billc = order.billContact;
    com.owd.core.business.Address billa = order.billAddress;
%>       
<P>  
   
<B>Edit Billing Information for order <%= order.orderReference %> (<%= order.OWDorderReference %>)</B>
<HR><fontx COLOR=RED><B><%= request.getAttribute("formerror")==null?"":request.getAttribute("formerror") %></B> <FORM METHOD=POST ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.extranet.servlet.OrdersManager.kParamOrderEditBillingAction%>&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>" value="<%= ExtranetServlet.kParamChangeMgrAction %>" />
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>" value="<%= ExtranetManager.kOrdersMgrName %>" />
    <INPUT TYPE=HIDDEN NAME="editbillingsave" VALUE="1">
<TABLE width=100% border=0>
<TR><TD width=30% align=right><fontx SIZE=-1><B>Name :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=billc.name VALUE="<%= billc.name %>"></TD></TR>
<TR><TD width=30% align=right><fontx SIZE=-1><B>Company Name :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=billa.company_name VALUE="<%= billa.company_name %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Address Line 1 :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=billa.address_one VALUE="<%= billa.address_one %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Address Line 2 :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=billa.address_two VALUE="<%= billa.address_two %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>City :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=billa.city VALUE="<%= billa.city %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>State :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=billa.state VALUE="<%= billa.state %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Postal Code :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=billa.zip VALUE="<%= billa.zip %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Country :</TD><TD><%= com.owd.core.business.order.OrderUtilities.getCountryList().getHTMLSelect(billa.country, "billa.country") %></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Email :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=billc.email VALUE="<%= billc.email %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Phone :</TD><TD><INPUT SIZE=50 TYPE=TEXT NAME=billc.phone VALUE="<%= billc.phone %>"></TD></TR>
<TR><TD colspan=2><HR></TD></TR>
<TR><TD><INPUT TYPE=SUBMIT NAME=submit VALUE="Save Changes"></TD><TD></TD></TR>
</TABLE>
