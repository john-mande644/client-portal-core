<%@ page import="com.owd.core.business.Address,
				 com.owd.core.business.Contact,
				 com.owd.core.business.order.Order,
				 java.util.List,
				 java.util.Vector" %>
<%

	Order order = (Order) request.getSession(true).getAttribute("currentorder");
	List paymentTypeList = (List) request.getSession(true).getAttribute("paymentTypeList");
	if(order==null)
		order = new Order("179");

	order.recalculateBalance();
	Address b_addr = order.getBillingAddress();
	if (b_addr == null)
		b_addr = new Address();
	Contact b_contact = order.getBillingContact();
	if(b_contact==null)
		b_contact=new Contact();

	Address s_addr = order.getShippingAddress();
	if(s_addr == null)
		s_addr = new Address();
	Contact s_contact = order.getShippingContact();
	if(s_contact == null)
		s_contact = new Contact();

	Vector shipRefs = (Vector) request.getSession(true).getAttribute("shipRefs");
	Vector shipTypes = (Vector) request.getSession(true).getAttribute("shipTypes");
	Vector shipCosts = (Vector) request.getSession(true).getAttribute("shipCosts");

Vector shipBadRefs = (Vector) request.getSession(true).getAttribute("shipBadRefs");
	Vector shipBadTypes = (Vector) request.getSession(true).getAttribute("shipBadTypes");

	if(shipTypes == null)
	shipTypes = new Vector();


	if(shipBadTypes == null)
	shipBadTypes = new Vector();


	Vector errors = (Vector) request.getAttribute("errors");
	if (errors == null)
		errors = new Vector();



%>
<html> <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
<head>


<title>PhIon Wholesale Order Form</title>



</head>

<body  bgcolor="#ffffff" text="#000000"><P><font color=red><B><%= (request.getAttribute("errormessage")!=null?request.getAttribute("errormessage"):"") %></B></font>
<P>
<!--<DISPLAY CART>-->
<table border="0" cellpadding="0" cellspacing="0" width="700">
<TR><TD width="50"> <BR> </TD><TD>
<table border="0" cellpadding="0" cellspacing="0" width="500">
<TR><td colspan=5 align=LEFT>
</TD>
</TR>

 <tr>
  <td colspan=2 align=left><FORM ACTION="startorder.jsp" METHOD="POST">
 <INPUT TYPE=SUBMIT NAME="catalog" VALUE="<-Back to Ordering Information">
  </FORM>
</td>
<TD>&nbsp;&nbsp;</TD>
  <td colspan=2 align=right><fontx size="-2" face="Geneva, Verdana, Helvetica">Repeat all information to the customer for verification and continue at bottom of page

</td>
</tr>
<tr VALIGN="BOTTOM">
  <TH ALIGN="LEFT" NOWRAP></TH>
  <TH ALIGN="LEFT" NOWRAP COLSPAN=2><fontx size="-1" face="Geneva, Verdana, Helvetica">Ordered By:</TH>
  <TH ALIGN="LEFT" NOWRAP COLSPAN=2><fontx size="-1" face="Geneva, Verdana, Helvetica">Ship To:</TH>
</tr>
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
%>
This order will be billed COD. $8.00 will be added to final handling charge<BR>
<%
	}else if(order.bill_cc_type.equals("CC"))  {
%>
Billed to CC: <%= order.cc_num %><BR>
<%
	}else if(order.bill_cc_type.equals("PP"))  {
%>
Billing: Prepaid Order<BR>
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
<!--SoftCart.cart.product.Loopbegin-->

<tr VALIGN="TOP">
  <td ALIGN="LEFT"><BR></td>
  <td ALIGN="LEFT"><fontx size="-1" face="Geneva, Verdana, Helvetica"><%=item.description%></td>
  <td ALIGN="CENTER" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica"><%=item.quantity_request%></td>
  <td ALIGN="CENTER" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica">$ <%=decform.format(item.sku_price)%></td>
  <td ALIGN="RIGHT" NOWRAP><fontx size="-1" face="Geneva, Verdana, Helvetica">$ <%=decform.format(item.total_price)%></td>
</tr>

<%
		}
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
  <TR> <td colspan=5 align=right><%= (order.getShippingAddress().country.equals("USA")?"":"<fontx size=\"-2\" face=\"Geneva, Verdana, Helvetica\">For non-US orders, any applicable Duties, Customs and Taxes<BR>will be the sole responsibility of the recipient</fontx>") %></TD></TR>
<tr>
  <td colspan=5 align=right><fontx size="-2" face="Geneva, Verdana, Helvetica">
</td>
</tr>
<%
		}else{
%>
<tr VALIGN="TOP">
  <td ALIGN="LEFT"><BR></td>
  <td ALIGN="CENTER" NOWRAP colspan="4"><fontx size="-1" face="Geneva, Verdana, Helvetica"><B>Cart is empty</B></td>
</tr>
<% 	} %>





</TABLE>
<FORM METHOD="POST" ACTION="phion.jsp" >
</TD></TR></TABLE>
<table border="0" cellpadding="0" cellspacing="0" width="650">
<TR><TD width="50"> <BR> </TD><TD width="600">

<TABLE border=0 width=550 cellpadding=0 cellspacing=0  >

<tr>
  <td colspan=2 align=left>
<fontx size="-1" face="Geneva, Verdana, Helvetica"><B>Shipping Options</B>
<HR color="#000000">
<INPUT TYPE=CHECKBOX NAME=free_shipping_cb VALUE=1>Do not charge for shipping costs
<HR>
</td>
</tr>
<tr>
  <td colspan=2 align=left>
<fontx size="-1" face="Geneva, Verdana, Helvetica">
Please choose a shipping option (the indicated cost will be added to your total) :<P>&nbsp;</TD></TR>



<%
	for (int i=0;i<shipTypes.size();i++)
	{
%>
<TR><TD align=left width=100% VALIGN=TOP><fontx size="-1" face="Geneva, Verdana, Helvetica">
<INPUT TYPE=RADIO NAME=shipping VALUE="<%= (String)shipRefs.elementAt(i)%>" <%= (i==0?"CHECKED":"") %>>
  <%= (String)shipTypes.elementAt(i)%>
 <%= (((String)shipTypes.elementAt(i)).indexOf("UPS") >= 0?"<BR><font size=-2 >No P.O. Boxes or APO/FPO addresses":"") %><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD><TD ALIGN=RIGHT VALIGN=TOP>
<fontx size="-1" face="Geneva, Verdana, Helvetica"><B>
 <%= "Adds&nbsp;$&nbsp;"+(String)shipCosts.elementAt(i) %></B></td></TR>
<%  } %>
<TR><TD COLSPAN=2><B>The following shipping options were not available</B><HR></TD></TR>
<%
	for (int i=0;i<shipBadTypes.size();i++)
	{
%>
<TR><TD align=left width="100%" VALIGN=TOP>
<%= (String)shipBadTypes.elementAt(i)%></TD><TD ALIGN=RIGHT VALIGN=TOP><B>
 <%= (String)shipBadRefs.elementAt(i)%></B></td></TR>
<%  } %>


<tr>
  <td colspan=2 align=left>
<fontx size="-1" face="Geneva, Verdana, Helvetica">
<HR color="#000000">
</td>
</tr>
<tr>
  <td colspan=2 align=right>
<fontx size="-1" face="Geneva, Verdana, Helvetica">
<INPUT TYPE=HIDDEN name="action" value="finishorder">
<INPUT TYPE="SUBMIT" NAME="finish" VALUE="Place Order ->"  ><BR>
Please click the "Place Order" button only once.<BR>
Confirmation may take up to 2 minutes.<BR>
Clicking on the button more than once may<BR>
result in multiple orders.</td>
</tr>
</TABLE>
</TD></TR></TABLE>
</FORM>

</body>

</html>


