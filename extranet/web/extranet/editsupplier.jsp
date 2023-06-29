<%@ page import="com.owd.core.business.order.Supplier" %>
<%@ page import="com.owd.extranet.servlet.ExtranetManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %>

<%  com.owd.core.business.order.Supplier supplier = (com.owd.core.business.order.Supplier)request.getAttribute(com.owd.extranet.servlet.SupplierManager.kCurrentSupplier);
%>
  <P>  
  <HR><fontx COLOR=RED><B><%= request.getAttribute("formerror")==null?"":request.getAttribute("formerror") %></B><BR>
  <TABLE border=0 width=100% cellpadding=5>
<FORM METHOD=POST ACTION=<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath %>?<%= com.owd.extranet.servlet.SupplierManager.kParamSupplierMgrAction %>=<%=com.owd.extranet.servlet.SupplierManager.kParamSupplierSaveAction%>>
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>" value="<%= ExtranetServlet.kParamChangeMgrAction %>" />
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>" value="<%= ExtranetManager.kSuppliersMgrName %>" />
    <TR><TD COLSPAN=2 width="100%">
<HR><INPUT TYPE=HIDDEN NAME="<%= Supplier.kdbSupplierPKName %>" VALUE="<%= supplier.supplier_id %>">
<INPUT TYPE=SUBMIT NAME="SUBMIT" VALUE="<%= (supplier.isNew()?"Save New Supplier":"Save Changes") %>">
<HR>
</TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Display&nbsp;Name:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Supplier.kdbSupplierName %>" VALUE="<%= supplier.supp_name %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Enter the displayed name of the supplier
</TD>

</TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Contact&nbsp;Name:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Supplier.kdbSupplierContact %>" VALUE="<%= supplier.supp_contact %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Enter the name of your contact at the supplier, if any.
</TD>

</TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Company&nbsp;Name:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Supplier.kdbSupplierCompany %>" VALUE="<%= supplier.supp_company %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Enter the company name for the supplier, if any.
</TD>

</TR>


<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Address&nbsp;Line&nbsp;1:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Supplier.kdbSupplierAddr1 %>" VALUE="<%= supplier.supp_addr_one %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Supplier address
</TD>

</TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Address&nbsp;Line&nbsp;2:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Supplier.kdbSupplierAddr2 %>" VALUE="<%= supplier.supp_addr_two %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Supplier address
</TD>

</TR>


<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
City:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Supplier.kdbSupplierCity %>" VALUE="<%= supplier.supp_city %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Supplier city
</TD>

</TR>


<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
State/Province:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Supplier.kdbSupplierState %>" VALUE="<%= supplier.supp_state %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Supplier state/province/region
</TD>

</TR>


<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Postal&nbsp;Code:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Supplier.kdbSupplierZip %>" VALUE="<%= supplier.supp_zip %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Supplier postal code
</TD>

</TR>


<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Email:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Supplier.kdbSupplierEmail %>" VALUE="<%= supplier.supp_email %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Email address for supplier
</TD>

</TR>


<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Phone:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Supplier.kdbSupplierPhone %>" VALUE="<%= supplier.supp_phone %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Phone number for supplier
</TD>

</TR>


<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Fax:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Supplier.kdbSupplierFax %>" VALUE="<%= supplier.supp_fax %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Fax number for supplier
</TD>

</TR>



</FORM></TABLE>

