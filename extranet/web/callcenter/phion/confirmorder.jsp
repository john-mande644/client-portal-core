
<%@ page import="com.owd.core.business.Address,
				 com.owd.core.business.Contact,
				 com.owd.core.business.order.Order"
%>
<html> <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">


<head>

<title>Phion Order Confirmation</title>
</head>
<A NAME="top">
<body bgcolor=#FFFFFF>
<TABLE border=0 WIDTH=100%>
<TR><TD ALIGN=LEFT VALIGN=TOP><CENTER><H2><fontx face="Geneva, Verdana, Helvetica" >Phion Order Confirmation</H2></CENTER>

</TD><TD ALIGN=RIGHT VALIGN=TOP></TD></TR></TABLE>


<%

	Order order = (Order) request.getSession(true).getAttribute("currentorder");


	Address b_addr = order.getBillingAddress();

	Contact b_contact = order.getBillingContact();


	Address s_addr = order.getShippingAddress();

	Contact s_contact = order.getShippingContact();




%>
Give Customer Order Reference: <%= order.order_refnum %><P>
Order Summary:<P>
<table>
<tr VALIGN="BOTTOM">
<TH ALIGN="LEFT" NOWRAP></TH>
  <TD ALIGN="LEFT" NOWRAP VALIGN=TOP COLSPAN=2><fontx size="-1" face="Geneva, Verdana, Helvetica"><%= order.getBillingContact().name %><BR>
<%= order.getBillingAddress().company_name %><BR><%= order.getBillingAddress().address_one %><BR>
<%= (order.getBillingAddress().address_two.length() > 0?order.getBillingAddress().address_two+"<BR>":"") %>
<%= order.getBillingAddress().city %>, <%= order.getBillingAddress().state %> <%= order.getBillingAddress().zip %><BR>
<%= order.getBillingAddress().country %><BR>
<%= order.getBillingContact().email %><BR>
<%= order.getBillingContact().phone %><P>
<% if(order.bill_cc_type.equals("CK"))
	{
		if("999999".equals(order.check_num))
		{
%>
This order was prepaid.<BR>
<%
		}   else
		{
%>
This order will be billed COD.<BR>
<%       }
	}else if(order.bill_cc_type.equals("CC"))  {
%>
Billed to CC: <%= order.cc_num %><BR>
<%
	}else
	{
%>
Billed to PO number: <%= order.po_num %><BR>
<%
	}
%>
 </TD>
  <TD ALIGN="LEFT" NOWRAP VALIGN=TOP COLSPAN=2><fontx size="-1" face="Geneva, Verdana, Helvetica"><%= order.getShippingContact().name %><BR>
<%= order.getShippingAddress().company_name %><BR><%= order.getShippingAddress().address_one %><BR>
<%= (order.getShippingAddress().address_two.length() > 0?order.getShippingAddress().address_two+"<BR>":"") %>
<%= order.getShippingAddress().city %>, <%= order.getShippingAddress().state %> <%= order.getShippingAddress().zip %><BR>
<%= order.getShippingAddress().country %><BR>
<%= order.getShippingContact().email %><BR>
<%= order.getShippingContact().phone %><P><fontx size="-2" face="Geneva, Verdana, Helvetica"></TD>
</tr>
<tr>
  <td colspan=5 align=right>
<HR color="#000000">
</td>
</tr>
<tr VALIGN="BOTTOM">
  <TH ALIGN="LEFT" NOWRAP></TH>
  <TH ALIGN="LEFT" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><B>Product</B></TH>
  <TH ALIGN="CENTER" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica">&nbsp;Quantity&nbsp;</TH>
  <TH ALIGN="CENTER" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica">Price Each</TH>
  <TH ALIGN="RIGHT" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica">Total</TH>
</tr>
<tr>
    <td><BR></td>
</tr>
<%
		java.text.DecimalFormat decform = new java.text.DecimalFormat("#,###,##0.00");
		if(order.skuList.size() > 0)
		{
		for(int i=0;i<order.skuList.size();i++)
		{
			com.owd.core.business.order.LineItem item = (com.owd.core.business.order.LineItem)order.skuList.elementAt(i);
%>

<tr VALIGN="TOP">
  <td ALIGN="LEFT"><BR></td>
  <td ALIGN="LEFT"><fontx size="-1" face="Geneva, Verdana, Helvetica"><%=item.description%></td>
  <td ALIGN="CENTER" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><%=item.quantity_request%></td>
  <td ALIGN="CENTER" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica">$ <%=decform.format(item.sku_price)%></td>
  <td ALIGN="RIGHT" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica">$ <%=decform.format(item.total_price)%></td>
</tr>

<%
		} }
%>

<tr>
  <td colspan=5 align=right>
<HR color="#000000">
</td>
</tr>

<tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><B>Product Subtotal</B></TD><TD ALIGN=RIGHT NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$<%=decform.format(order.total_product_cost)%></B></td>
</tr><tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><B>Shipping/Handling</B></TD><TD ALIGN=RIGHT NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$<%=decform.format(order.total_shipping_cost)%></B></td>
</tr>
<tr>

  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><B>Discount</B></TD><TD ALIGN=RIGHT NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$<%=decform.format(order.discount)%></B></td>
</tr>
<tr>

  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><B>Sales Tax</B></TD><TD ALIGN=RIGHT NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$<%=decform.format(order.total_tax_cost)%></B></td>
</tr>
<tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><B>Grand Total</B></TD><TD ALIGN=RIGHT NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$<%=decform.format(order.total_order_cost)%><%= (order.getShippingAddress().country.equals("USA")?"":"&nbsp;U.S.&nbsp;Dollars") %></B></td>
</tr>
</table>
</body>
</HTML>
<%


	request.getSession(true).setAttribute("currentorder",null);

%>










