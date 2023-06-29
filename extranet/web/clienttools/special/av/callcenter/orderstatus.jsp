<html>
<%@ page import="
com.owd.core.business.order.LineItem,
com.owd.core.business.order.OrderStatus,
com.owd.core.business.order.Package,
com.owd.core.business.order.ShippingInfo,
java.util.Vector"
%>


<head>
<title>Call Center Order Status</title>
</head>
<A NAME="top">
<body bgcolor=#FFFFFF>
<CENTER><H2><font face="Geneva, Verdana, Helvetica" >  Call Center Order Status Form</font></H2></CENTER>

<HR>

<P>
<font face="Geneva, Verdana, Helvetica" size="-1">You can check the status of an existing order by entering a search value below and clicking on the "Find" button.  <P>
Multiple matching orders may be returned - they contain the delivery shipping address and line items involved. <P>PLEASE VERIFY THE STREET ADDRESS WITH THE CUSTOMER BEFORE PROVIDING STATUS INFORMATION
<P>
<TABLE border=0>
<TR><TD></TD><TD>
<%
	String actionType = (String) request.getParameter("action");
	String type = (String) request.getParameter("find_type");
	String key = (String) request.getParameter("find_key");
	String lastError = "";
	String sqlQuery = "";
	String errorHeader = "<font face=\"Geneva, Verdana, Helvetica\" size=\"-1\" color=red><B>";
	String errorFooter = "<P>Please correct your entries and try again.<P></B></FONT>";
	boolean validated = false;
	String bg = "#FFFFFF";
	Vector statuses = new Vector();
System.out.println("in status:"+type+":"+key+":"+actionType);
	
	if("find".equals(actionType))
	{
			if (type.length() > 0)
			{
System.out.println("getting status");
				statuses = OrderStatus.getOrderStatusByKey(type,key,"117");
System.out.println("got statuses ");
System.out.println("got statuses "+statuses.size());
			}
			
			if(statuses.size() > 0)
			{
		
				validated = true;
			}else{
				%>
				<%= errorHeader+"No matching orders found"+errorFooter %>
				<%
			}
	}
%>
	
	
</TD></TR>
<TR><TD>
<TABLE>
<TR><TD ALIGN=RIGHT valign=TOP><font face="Geneva, Verdana, Helvetica" size="-1"><FORM METHOD=POST ACTION=orderstatus.jsp?action=find><B>Billing Last Name:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=TEXT NAME="find_key"><INPUT TYPE=HIDDEN NAME="find_type" VALUE="<%= OrderStatus.kByBillLastName %>"><INPUT TYPE=SUBMIT NAME=actionType VALUE="Find"></FORM>
</TD></TR>
<TR><TD ALIGN=RIGHT valign=TOP><font face="Geneva, Verdana, Helvetica" size="-1"><FORM METHOD=POST ACTION=orderstatus.jsp?action=find><B>Delivery Last Name:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=TEXT NAME="find_key"><INPUT TYPE=HIDDEN NAME="find_type" VALUE="<%= OrderStatus.kByShipLastName %>"><INPUT TYPE=SUBMIT NAME=actionType VALUE="Find"></FORM></TD></TR>

<TR><TD ALIGN=RIGHT valign=TOP><font face="Geneva, Verdana, Helvetica" size="-1"><FORM METHOD=POST ACTION=orderstatus.jsp?action=find><B>Customer Zip:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=TEXT NAME="find_key"><INPUT TYPE=HIDDEN NAME="find_type" VALUE="<%= OrderStatus.kByBillZip %>"><INPUT TYPE=SUBMIT NAME=actionType VALUE="Find"></FORM></TD></TR>

<TR><TD ALIGN=RIGHT valign=TOP><font face="Geneva, Verdana, Helvetica" size="-1"><FORM METHOD=POST ACTION=orderstatus.jsp?action=find><B>Customer Email:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=TEXT NAME="find_key"><INPUT TYPE=HIDDEN NAME="find_type" VALUE="<%= OrderStatus.kByEmail %>"><INPUT TYPE=SUBMIT NAME=actionType VALUE="Find"></FORM></TD></TR>

</TABLE></TD><TD>
<TABLE>
<TR><TD ALIGN=RIGHT valign=TOP><font face="Geneva, Verdana, Helvetica" size="-1"><FORM METHOD=POST ACTION=orderstatus.jsp?action=find><B>Delivery Zip:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=TEXT NAME="find_key"><INPUT TYPE=HIDDEN NAME="find_type" VALUE="<%= OrderStatus.kByShipZip %>"><INPUT TYPE=SUBMIT NAME=actionType VALUE="Find"></FORM></TD></TR>

<TR><TD ALIGN=RIGHT valign=TOP><font face="Geneva, Verdana, Helvetica" size="-1"><FORM METHOD=POST ACTION=orderstatus.jsp?action=find><B>OWD Order Reference (like 0023423):&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=TEXT NAME="find_key"><INPUT TYPE=HIDDEN NAME="find_type" VALUE="<%= OrderStatus.kByOrderRef %>"><INPUT TYPE=SUBMIT NAME=actionType VALUE="Find"></FORM></TD></TR>

<TR><TD ALIGN=RIGHT valign=TOP><font face="Geneva, Verdana, Helvetica" size="-1"><FORM METHOD=POST ACTION=orderstatus.jsp?action=find><B>Client's Order Reference:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=TEXT NAME="find_key"><INPUT TYPE=HIDDEN NAME="find_type" VALUE="<%= OrderStatus.kByOrderClientReference %>"><INPUT TYPE=SUBMIT NAME=actionType VALUE="Find"></FORM></TD></TR>


</TABLE></TD></TR>

</TABLE>

<HR>
<%


	
if(validated)
{
		
		
		
%>

<TABLE border=0 width=100% cellpadding=2 cellspacing=1 bgcolor=#000000>
<TR>
<TD ALIGN=LEFT bgcolor=#CCCCFF width=10%><font face="Geneva, Verdana, Helvetica" size="-1"><B>
Status&nbsp;</B></TD>
<TD ALIGN=LEFT bgcolor=#CCCCFF width=20%><font face="Geneva, Verdana, Helvetica" size="-1"><B>
Ship&nbsp;To&nbsp;</B></TD>
<TD ALIGN=LEFT bgcolor=#CCCCFF width=40%><font face="Geneva, Verdana, Helvetica" size="-1"><B>
Line&nbsp;Items&nbsp;</B></TD>
<TD ALIGN=LEFT bgcolor=#CCCCFF width=10%><font face="Geneva, Verdana, Helvetica" size="-1"><B>
Shipped&nbsp;</B></TD>
<TD ALIGN=LEFT bgcolor=#CCCCFF width=10%><font face="Geneva, Verdana, Helvetica" size="-1"><B>
Carrier&nbsp;</B></TD>
<TD ALIGN=LEFT bgcolor=#CCCCFF width=10%><font face="Geneva, Verdana, Helvetica" size="-1"><B>
Tracking&nbsp;</B></TD>
</TR>


<%
	for(int i=0;i<statuses.size();i++)
	{

		OrderStatus status = (OrderStatus) statuses.elementAt(i);

		if(status.shipping == null){
			status.shipping=new ShippingInfo();
		}
	
%>

<%
	
	
	StringBuffer itemParts = new StringBuffer();
	StringBuffer packDates = new StringBuffer();
	StringBuffer packCarriers = new StringBuffer();
	StringBuffer packTracks = new StringBuffer();
		if(bg.equals("#CCEECC"))
					    		bg = "#FFFFFF";
					    	else
					    		bg = "#CCEECC";	
if(!(status.is_void))
	{

itemParts.append("<TABLE cellpadding=0 cellspacing=0 width=100% ><TR><TD ALIGN=LEFT bgcolor=#00000><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\" color="+bg+" ><B>SKU&nbsp;</B></TD><TD ALIGN=LEFT bgcolor=#00000><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\"  color="+bg+"><B>Count&nbsp;</B></TD><TD ALIGN=LEFT bgcolor=#00000><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\" color="+bg+"><B>BO&nbsp;</B></TD><TD ALIGN=LEFT bgcolor=#00000 width=100% ><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\" color="+bg+"><B>Description&nbsp;</B></TD></TR>");
System.out.println("status header");
for(int l=0;l<status.items.size();l++)
{

System.out.println("status item "+l);
itemParts.append("<TR><TD align=left VALIGN=TOP bgcolor="+bg+"  width=50% ><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\">"+((LineItem)status.items.elementAt(l)).client_part_no.replace(' ','_')+"&nbsp;</TD>");
itemParts.append("<TD align=left VALIGN=TOP bgcolor="+bg+"><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\">"+((LineItem)status.items.elementAt(l)).quantity_request+"&nbsp;</TD>");
itemParts.append("<TD align=left VALIGN=TOP bgcolor="+bg+"><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\">"+((LineItem)status.items.elementAt(l)).quantity_backordered+"&nbsp;</TD>");
itemParts.append("<TD align=left VALIGN=TOP bgcolor="+bg+"  width=100% ><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\">"+((LineItem)status.items.elementAt(l)).description+"&nbsp;</TD></TR>");		

}
itemParts.append("</TABLE>");
if(status.packages.size()==0)
{

packDates.append("&nbsp;");
packCarriers.append(status.shipping.carr_service);
packTracks.append("&nbsp;");

}

for(int p=0;p<status.packages.size();p++)
{
System.out.println("in package loop ");

packDates.append(((Package)status.packages.elementAt(p)).ship_date.substring(0,((Package)status.packages.elementAt(p)).ship_date.indexOf(" "))+"<BR>");
packCarriers.append(status.shipping.carr_service+"<BR>");
packTracks.append(((Package)status.packages.elementAt(p)).tracking_no+"<BR>");

}
%>

<TR>
<TD align=CENTER VALIGN=TOP bgcolor=<%= bg %>><font face="Geneva, Verdana, Helvetica" size="-1">
<%= "<B>"+status.orderReference+"<BR>"+(null==status.getStatusString()?"":status.getStatusString())+"</B><P>In:&nbsp;"+status.orderDate.substring(0,status.orderDate.indexOf(" ")) %></TD>
<TD align=left VALIGN=TOP bgcolor=<%= bg %>><font face="Geneva, Verdana, Helvetica" size="-1">
<%= status.shipping.shipContact.name %><BR><%= status.shipping.shipAddress.company_name %><BR><%= status.shipping.shipAddress.address_one +"<BR>"+status.shipping.shipAddress.address_two %><BR><%= status.shipping.shipAddress.city+", "+status.shipping.shipAddress.state+"  "+status.shipping.shipAddress.zip %></TD>
<TD align=left VALIGN=TOP bgcolor=<%= bg %>><font face="Geneva, Verdana, Helvetica" size="-1">
<%= itemParts.toString() %></TD>

<TD align=left VALIGN=TOP bgcolor=<%= bg %>><font face="Geneva, Verdana, Helvetica" size="-1">
<%= packDates.toString() %></TD>
<TD align=left VALIGN=TOP bgcolor=<%= bg %>><font face="Geneva, Verdana, Helvetica" size="-1">
<%= packCarriers.toString() %></TD>
<TD align=left VALIGN=TOP bgcolor=<%= bg %>><font face="Geneva, Verdana, Helvetica" size="-1">
<%= packTracks.toString() %></TD>
</TR>



<%		
	}
	if(status.hasBackorder)
	{	
		if(!(status.is_void))
		{
	String backordered_order_num = status.backorderNum;
	status = status.backorder;

	itemParts = new StringBuffer();
	packDates = new StringBuffer();
	packCarriers = new StringBuffer();
	packTracks = new StringBuffer();
	
itemParts.append("<TABLE cellpadding=0 cellspacing=0 width=100% ><TR><TD ALIGN=LEFT bgcolor=#00000><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\" color="+bg+" ><B>SKU&nbsp;</B></TD><TD ALIGN=LEFT bgcolor=#00000><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\"  color="+bg+"><B>Count&nbsp;</B></TD><TD ALIGN=LEFT bgcolor=#00000><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\" color="+bg+"><B>BO&nbsp;</B></TD><TD ALIGN=LEFT bgcolor=#00000 width=100% ><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\" color="+bg+"><B>Description&nbsp;</B></TD></TR>");
for(int l=0;l<status.items.size();l++)
{


itemParts.append("<TR><TD align=left VALIGN=TOP bgcolor="+bg+"  width=50% ><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\">"+((LineItem)status.items.elementAt(l)).client_part_no.replace(' ','_')+"&nbsp;</TD>");
itemParts.append("<TD align=left VALIGN=TOP bgcolor="+bg+"><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\">"+((LineItem)status.items.elementAt(l)).quantity_request+"&nbsp;</TD>");
itemParts.append("<TD align=left VALIGN=TOP bgcolor="+bg+"><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\">"+((LineItem)status.items.elementAt(l)).quantity_backordered+"&nbsp;</TD>");
itemParts.append("<TD align=left VALIGN=TOP bgcolor="+bg+"  width=100% ><font face=\"Geneva, Verdana, Helvetica\" size=\"-1\">"+((LineItem)status.items.elementAt(l)).description+"&nbsp;</TD></TR>");		

}
itemParts.append("</TABLE>");
if(status.packages.size()==0)
{

packDates.append("&nbsp;");
packCarriers.append(status.shipping.carr_service);
packTracks.append("&nbsp;");

}
for(int p=0;p<status.packages.size();p++)
{
System.out.println("in package loop ");

packDates.append(((Package)status.packages.elementAt(p)).ship_date.substring(0,((Package)status.packages.elementAt(p)).ship_date.indexOf(" "))+"<BR>");
packCarriers.append(status.shipping.carr_service+"<BR>");
packTracks.append(((Package)status.packages.elementAt(p)).tracking_no+"<BR>");

}
%>

<TR>
<TD align=center VALIGN=TOP bgcolor=<%= bg %>><font face="Geneva, Verdana, Helvetica" size="-1" color="red"><B>BackOrder</B><font face="Geneva, Verdana, Helvetica" size="-1" color="black"><P>
<%= "<B>"+status.orderReference+"<BR>"+(null==status.getStatusString()?"":status.getStatusString())+"</B><P>In:&nbsp;"+status.orderDate.substring(0,status.orderDate.indexOf(" ")) %></TD>
<TD align=left VALIGN=TOP bgcolor=<%= bg %>><font face="Geneva, Verdana, Helvetica" size="-1">
<%= status.shipping.shipContact.name %><BR><%= status.shipping.shipAddress.company_name %><BR><%= status.shipping.shipAddress.address_one +"<BR>"+status.shipping.shipAddress.address_two %><BR><%= status.shipping.shipAddress.city+", "+status.shipping.shipAddress.state+"  "+status.shipping.shipAddress.zip %></TD>
<TD align=left VALIGN=TOP bgcolor=<%= bg %>><font face="Geneva, Verdana, Helvetica" size="-1">
<%= itemParts.toString() %></TD>
<TD align=left VALIGN=TOP bgcolor=<%= bg %>><font face="Geneva, Verdana, Helvetica" size="-1">
<%= packDates.toString() %></TD>
<TD align=left VALIGN=TOP bgcolor=<%= bg %>><font face="Geneva, Verdana, Helvetica" size="-1">
<%= packCarriers.toString() %></TD>
<TD align=left VALIGN=TOP bgcolor=<%= bg %>><font face="Geneva, Verdana, Helvetica" size="-1">
<%= packTracks.toString() %></TD>
</TR>
	
<%
		}
	}
}

%>
</TABLE>



<%
	
	
}
	

%>
<HR>
</body>
</html>



