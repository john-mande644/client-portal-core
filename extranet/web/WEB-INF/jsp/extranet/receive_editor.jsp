<%  
com.owd.core.business.order.Receive receive = null;
   
receive = (com.owd.core.business.order.Receive)(request.getAttribute(com.owd.web.servlet.ReceivingManager.kCurrentReceiveName));
%>

<TABLE border=0><FORM METHOD=POST ACTION="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath %>?<%=com.owd.web.servlet.ReceivingManager.kParamRcvMgrAction%>=<%=com.owd.web.servlet.ReceivingManager.kParamRcvSaveAction%>">
 
<TR><TD COLSPAN=4><INPUT TYPE=HIDDEN NAME="<%=com.owd.core.business.order.Receive.kdbReceivePKName%>" VALUE="<%=receive.receive_id%>">
<% if(receive.isEditable()){
%><HR>
<INPUT TYPE=SUBMIT NAME="<%=com.owd.web.servlet.ReceivingManager.kParamRcvMgrAction%>" VALUE="<%=(receive.isNew()?"Save New Receive":"Save Changes") %>"><%=(receive.isNew()?"":"&nbsp;&nbsp;") %><HR>
<%}%></TD></TR>

<TR><TD COLSPAN=4><fontx size="-1">Receive Information:<HR></TD></TR>




<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Received By:&nbsp;</B></fontx></TD><TD ALIGN=LEFT><fontx size="-2">
<%=receive.receive_user%>
</TD>
<TD ALIGN=RIGHT ><fontx  color="#000000" size="-2"><b>
Received On:&nbsp;</B></fontx></TD><TD ALIGN=LEFT width=100 ><fontx size="-2"><%=receive.receive_date%>
</TD></TR>
<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Time Taken (Hours):&nbsp;</B></fontx></TD><TD ALIGN=LEFT><fontx size="-2">
<% if(!receive.isEditable())
{%>
<INPUT TYPE=HIDDEN NAME=carrier size=32 VALUE="<%=receive.total_time%>" >
<%=receive.total_time%>
<%}else{%>
<INPUT TYPE=TEXT NAME=carrier size=8 VALUE="<%=receive.total_time%>" >
<%}%>
</TD>
<TD ALIGN=RIGHT ><fontx  color="#000000" size="-2"><b>
</B></fontx></TD><TD ALIGN=LEFT width=100 ><fontx size="-2">
</TD></TR>

<TR><TD COLSPAN=4> <BR> </TD></TR>
<TR><TD COLSPAN=4><fontx size="-1">Advance Ship Notice (ASN) Information:<HR></TD></TR>
<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Carrier&nbsp;Name:&nbsp;</B></fontx></TD><TD ALIGN=LEFT><fontx size="-2">
<% if(!receive.isEditable())
{%>
<INPUT TYPE=HIDDEN NAME=carrier size=32 VALUE="<%=receive.carrier%>" >
<%=receive.carrier%>
<%}else{%>
<INPUT TYPE=TEXT NAME=carrier size=32 VALUE="<%=receive.carrier%>" >
<%}%>
</TD> 
<TD ALIGN=RIGHT><fontx size="-2"><b>
Shipment&nbsp;Ref.:&nbsp;</B></fontx></TD><TD ALIGN=LEFT><fontx size="-2">
<% if(!receive.isEditable())
{%>
<INPUT TYPE=HIDDEN NAME=shipper_ref size=32 VALUE="<%=receive.shipper_ref%>" >
<%=receive.shipper_ref%>
<%}else{%>
<INPUT TYPE=TEXT NAME=shipper_ref size=32 VALUE="<%=receive.shipper_ref%>" >
<%}%>
</TD> </TR>
 
 <TR><TD ALIGN=RIGHT><fontx size="-2"><b>
ASN&nbsp;Reference:&nbsp;</B></fontx></TD><TD ALIGN=LEFT><fontx size="-2">
<% if(!receive.isEditable())
{%>
<INPUT TYPE=HIDDEN NAME=ref_num size=32 VALUE="<%=receive.ref_num%>" >
<%=receive.ref_num%>
<%}else{%>
<INPUT TYPE=TEXT NAME=ref_num size=32 VALUE="<%=receive.ref_num%>" >
<%}%>
</TD> 
<TD ALIGN=RIGHT><fontx size="-2"><b>
Expected&nbsp;Date:&nbsp;</B></fontx></TD><TD ALIGN=LEFT><fontx size="-2">
<% if(!receive.isEditable())
{%>
<INPUT TYPE=HIDDEN NAME=expected_date size=32 VALUE="<%=receive.expected_date%>" >
<%=receive.expected_date%>
<%}else{%>
<INPUT TYPE=TEXT NAME=expected_date size=32 VALUE="<%=receive.expected_date%>" >
<%}%>
</TD> </TR>

<TR><TD COLSPAN=4> <BR> </TD></TR>
<TR><TD COLSPAN=4><fontx size="-1">Notes:<HR></TD></TR>
<TR><TD COLSPAN=4 ALIGN=LEFT><fontx size="-2">
<% if(!receive.isEditable())
{%>
<INPUT TYPE=HIDDEN NAME=notes VALUE="<%=receive.notes%>">
<%=receive.notes%>
<%}else{%>
<TEXTAREA NAME=notes ROWS=6 COLS=60><%=receive.notes%></TEXTAREA>
<%}%>
</TD>
</TR>

<TR><TD COLSPAN=4> <BR> </TD></TR>
<% if(receive.isEditable())
{%>
<TR><TD COLSPAN=4><fontx size="-1">Items:&nbsp;&nbsp;<B><fontx size="-2">SKU:</B><INPUT TYPE=TEXT NAME=new_sku size=6 VALUE="" ><B><fontx size="-2">Quantity Shipped:</B><INPUT TYPE=TEXT NAME=new_sku_quantity size=6 VALUE="0" ><INPUT TYPE=SUBMIT NAME="<%=com.owd.web.servlet.ReceivingManager.kParamRcvMgrAction%>" VALUE="Add Item">
<%}else{%>
<TR><TD COLSPAN=4><fontx size="-1">Items:</TD>
<%}%>
</TR>
<TR><TD COLSPAN=4><HR></TD></TR>
</TABLE>
<TABLE border=0 >
<TR><TD><fontx  color="#000000" size="-2"><B>&nbsp;Part&nbsp;Reference/SKU&nbsp;</TD><TD><fontx  color="#000000" size="-2"><B>&nbsp;Description&nbsp;</TD><TD align=right><fontx  color="#000000" size="-2"><B>Expected</TD><TD align=right><fontx  color="#000000" size="-2"><B>Received</TD><TD align=right><fontx  color="#000000" size="-2"><B>Damaged</TD></TR>
<%
	for(int i=0;i<receive.itemsList.size();i++)
	{
		com.owd.core.business.order.ReceiveItem item = (com.owd.core.business.order.ReceiveItem)receive.itemsList.elementAt(i);

%>	
<TR><TD><fontx  color="#000000" size="-2"><%= item.inventory_num%></TD><TD><fontx  color="#000000" size="-2"><%= item.description%></TD>
<TD align=right><fontx  color="#000000" size="-2"><%= item.quantity%></TD><TD align=right><fontx  color="#000000" size="-2">
<% if(!receive.isEditable())
{%>
<INPUT TYPE=HIDDEN NAME=carrier size=6 VALUE="<%=item.quantity%>" >
<%=item.quantity%>
<%}else{%>
<INPUT TYPE=TEXT NAME=carrier size=6 VALUE="<%=item.quantity%>" >
<%}%>
</TD><TD align=right><fontx  color="#000000" size="-2">0</TD></TR>
<%	
	}
%>


</FORM></TABLE>



  