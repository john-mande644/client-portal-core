<%@ page import="com.owd.extranet.servlet.ExtranetManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %>
<%
      com.owd.core.business.order.OrderStatus order = (com.owd.core.business.order.OrderStatus)request.getAttribute(com.owd.extranet.servlet.OrdersManager.kCurrentOrder);
    java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");

    boolean isEditable = "1".equals(request.getAttribute("owdGuaranteeEditable"));
%>       
<P>  
<%= isEditable%>  : <%= request.getAttribute("owdGuaranteeEditable")%>
<B>Edit OWD Guarantee Credit for order <%= order.orderReference %> (<%= order.OWDorderReference %>)</B>
<HR><A HREF="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=edit&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>"><-Go back to order editing page</A></B>
<HR><fontx COLOR=RED><B><%= request.getAttribute("formerror")==null?"":request.getAttribute("formerror") %></B>
<FORM METHOD=POST ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.extranet.servlet.OrdersManager.kParamOrderEditSvcBillingAction%>&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>" value="<%= ExtranetServlet.kParamChangeMgrAction %>" />
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>" value="<%= ExtranetManager.kOrdersMgrName %>" />

    <TABLE width=100% border=0>

<TR><TD NOWRAP align=right style="font-family:Geneva, Verdana, Helvetica;font-size:11;font-weigt:bold;"><INPUT TYPE="CHECKBOX" name="owdbillServiceGuarantee" value="1" <%= ("1".equals(request.getAttribute("owdbillServiceGuarantee"))?"CHECKED":"")%>></TD>
    <TD width="100%" style="font-family:Geneva, Verdana, Helvetica;font-size:11;font-weight:bold;"> OWD Guarantee (Service Fees)</TD></TR>
<TR><TD align=right style="font-family:Geneva, Verdana, Helvetica;font-size:11;font-weight:bold;">
     <INPUT TYPE="CHECKBOX" name="owdbillShippingGuarantee" value="1" <%= ("1".equals(request.getAttribute("owdbillShippingGuarantee"))?"CHECKED":"")%>></TD>
    <TD  style="font-family:Geneva, Verdana, Helvetica;font-size:11;font-weight:bold;">OWD Guarantee (Shipping)</TD></TR>

 <TR><TD align=right style="font-family:Geneva, Verdana, Helvetica;font-size:11;font-weight:bold;">
    Reason:</TD>
    <TD align="left"  style="font-family:Geneva, Verdana, Helvetica;font-size:11;font-weight:bold;"><SELECT name="owdGuaranteeReason">
    <OPTION VALUE="MISC" <%= ("MISC".equals(request.getAttribute("owdGuaranteeReason"))?"SELECTED":"")%>>MISC</OPTION>
<OPTION VALUE="MISKEY" <%= ("MISKEY".equals(request.getAttribute("owdGuaranteeReason"))?"SELECTED":"")%>>MISKEY</OPTION>
<OPTION VALUE="MISPICK" <%= ("MISPICK".equals(request.getAttribute("owdGuaranteeReason"))?"SELECTED":"")%>>MISPICK</OPTION>
<OPTION VALUE="MISSHIP" <%= ("MISSHIP".equals(request.getAttribute("owdGuaranteeReason"))?"SELECTED":"")%>>MISSHIP</OPTION>
<OPTION VALUE="OMIT" <%= ("OMIT".equals(request.getAttribute("owdGuaranteeReason"))?"SELECTED":"")%>>OMIT</OPTION>
<OPTION VALUE="TECH-ERROR" <%= ("TECH-ERROR".equals(request.getAttribute("owdGuaranteeReason"))?"SELECTED":"")%>>TECH-ERROR</OPTION></SELECT></TD></TR>

         <TR><TD align=right style="font-family:Geneva, Verdana, Helvetica;font-size:11;font-weight:bold;">
    Notes:</TD>
    <TD align="left"  style="font-family:Geneva, Verdana, Helvetica;font-size:11;font-weight:bold;"><INPUT TYPE="TEXT" NAME="owdGuaranteeNotes" VALUE="<%=request.getAttribute("owdGuaranteeNotes")%>" SIZE="50"></TD></TR>

<TR><TD colspan=2><HR></TD></TR>
<TR><TD colspan="2"><% if(isEditable){%><INPUT TYPE="HIDDEN" NAME="saveGuarantee" VALUE="1"><INPUT TYPE=SUBMIT NAME=submit VALUE="Save Changes">
    <%}else{%>This credit has already been processed and cannot be edited<%}%>
</TD><TD></TD></TR>
</TABLE>
    </FORM>
