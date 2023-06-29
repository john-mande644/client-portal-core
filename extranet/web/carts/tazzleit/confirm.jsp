
<%@ page import="com.owd.core.business.order.Coupon"%>
<%@ page import="com.owd.core.business.order.LineItem"%>
<%@ page import="com.owd.core.business.order.Order"%>
<%@ page import="com.owd.web.carts.tazzle.TazzleCart" %>
<%@ page import="com.owd.web.carts.tazzle.TazzleServlet" %>
<%
    TazzleCart cart = (com.owd.web.carts.tazzle.TazzleCart) request.getSession(true).getAttribute(TazzleServlet.kCartKey);
    Order order = cart.old_order;
    order.recalculateBalance();

%> <jsp:include page="bitehead.jsp"/>
     <div id="cart">
<!--<DISPLAY CART>-->
<table border="0" cellpadding="0" cellspacing="0" width="100%">

<% if (!("".equals(request.getAttribute(TazzleServlet.kCartError))))
{
%>
<TR><td colspan=5><font color="red" face="Geneva, Verdana, Helvetica"><%= request.getAttribute(TazzleServlet.kCartError)%><P> </font></TD></TR>
<%
}
%>

<tr>
  <td colspan=5 align=left>  <HR color="#000000"><B><%= (order.bill_cc_type.equals("CK")?"Printable Order Form - Reference #"+order.order_refnum:"Order Completed!<P>Your Order Reference Is: "+order.order_refnum) %><P></B>

</td>
</tr>
<tr>
  <td colspan=5 align=left>  <font size="-1" face="Geneva, Verdana, Helvetica">

<B>Please save or print this page for your records!</B><P>If you provided your email address, you will receive an email confirmation when your order ships.
  <P>Thank you for your order!<HR color="#000000"></font></td>
</tr>
<tr VALIGN="BOTTOM">
  <TH ALIGN="LEFT" NOWRAP></TH>
  <TH ALIGN="LEFT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Product</B></font></TH>
  <TH ALIGN="CENTER" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">Quantity</font></TH>
  <TH ALIGN="CENTER" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">Price Each</font></TH>
  <TH ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">Total</font></TH>
</tr>
<tr>
    <td COLSPAN="5"><BR></td>
</tr>
<%
	java.text.DecimalFormat decform = new java.text.DecimalFormat("#,###,##0.00");
	if(order.skuList.size() > 0)
	{

	for(int i=0;i<order.skuList.size();i++)
	{
		 LineItem item = (LineItem)order.skuList.elementAt(i);
%>
<!--SoftCart.cart.product.Loopbegin-->

<tr VALIGN="TOP">
  <td ALIGN="LEFT"><BR></td>
  <td ALIGN="LEFT"><font size="-1" face="Geneva, Verdana, Helvetica"><%=item.description%></font></td>
  <td ALIGN="CENTER" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><%=item.quantity_request+item.quantity_backordered%></font></td>
  <td ALIGN="CENTER" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">$ <%=decform.format(item.sku_price)%></font></td>
  <td ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">$ <%=decform.format(item.total_price)%></font></td>
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
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Product&nbsp;Subtotal</B></font></TD><TD ALIGN=RIGHT><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_product_cost)%></B></font></td>
</tr><tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Shipping/Handling</B></font></TD><TD ALIGN=RIGHT><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_shipping_cost)%></B></font></td>
</tr>
<% if(order.coupon_code.length() > 0 && order.discount < 0)
{
%>
<tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Discount&nbsp;Applied&nbsp;(<%
  Coupon coupon = Coupon.dbloadByCoupon(order.coupon_code,order.clientID);
  switch(coupon.coupon_type)
  {
  	case Coupon.kCouponTypeAmount:
  		%>
  		credit&nbsp;for&nbsp;order
  		<%
  		break;
  	case Coupon.kCouponTypePercent:
  		%>
  		<%= (coupon.disc_pct*100)%>%&nbsp;discount&nbsp;on&nbsp;product&nbsp;subtotal
  		<%
  		break;
  	case Coupon.kCouponTypeShipAmt:
  		%>
  		credit&nbsp;toward&nbsp;shipping
  		<%
  		break;
  	case Coupon.kCouponTypeShipPct:
  		%>
  		<%= (coupon.ship_pct*100)%>%&nbsp;discount&nbsp;on&nbsp;standard&nbsp;shipping
  		<%
  		break;


  }

  %>)</B></font></TD><TD ALIGN=RIGHT NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$<%=decform.format(order.discount)%></B></font></td>
</tr>

<% } %>
<tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Sales&nbsp;Tax</B></font></TD><TD ALIGN=RIGHT NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_tax_cost)%></B></font></td>
</tr>
<tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Grand&nbsp;Total</B></font></TD><TD ALIGN=RIGHT NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_order_cost)%></B></font></td>
</tr>
  <TR> <td colspan=5 align=right>
<BR> </TD></TR>

<%
	}else{
%>
<tr VALIGN="TOP">
  <td ALIGN="LEFT"><BR></td>
  <td ALIGN="CENTER" NOWRAP colspan="4"><font size="-1" face="Geneva, Verdana, Helvetica"><B>Cart is empty</B></font></td>
</tr>
<% 	} %>

</table>

<TABLE border=0 width="100%" cellpadding=0 cellspacing=0  >

<tr>
  <td colspan=2 align=left>
<font size="-1" face="Geneva, Verdana, Helvetica"><B>Ordered By</B>
<HR color="#000000"></font>
</td>
</tr>
<tr>
  <td colspan=2 align=left>
<font size="-1" face="Geneva, Verdana, Helvetica"><%= order.getBillingContact().name %><BR>
<%= order.getBillingAddress().address_one %><BR>
<%= (order.getBillingAddress().address_two.length() > 0?order.getBillingAddress().address_two+"<BR>":"") %>
<%= order.getBillingAddress().city %>, <%= order.getBillingAddress().state %> <%= order.getBillingAddress().zip %><BR>
<%= order.getBillingAddress().country %><BR>
<%= order.getBillingContact().email %><BR>
<%= order.getBillingContact().phone %><P>
<% if(order.bill_cc_type.equals("CK"))
{
%>
Payment via:<P>___ Check (enclose with order)<BR>___ Credit Card :<BR>&nbsp;&nbsp;Account#:____________________<BR>&nbsp;&nbsp;Expiration month/year: _______<BR>
<%
}else{
%>
Billed to CC: XXXX-XXXX-XXXX-<%= order.cc_num.substring(order.cc_num.length()-4) %><BR>
<%
}
%>
</td>
<tr>
  <td colspan=2 align=left>
<font size="-1" face="Geneva, Verdana, Helvetica">
 <BR></font>
</td>
</tr>
<tr>
  <td colspan=2 align=left>
<font size="-1" face="Geneva, Verdana, Helvetica"><B>Ship To (via <%=order.getShippingInfo().carr_service %> )</B></font>
<HR color="#000000">
</td>
</tr> 
<tr>
  <td colspan=2 align=left>
<font size="-1" face="Geneva, Verdana, Helvetica"><%= order.getShippingContact().name %><BR>
<%= order.getShippingAddress().address_one %><BR>
<%= (order.getShippingAddress().address_two.length() > 0?order.getShippingAddress().address_two+"<BR>":"") %>
<%= order.getShippingAddress().city %>, <%= order.getShippingAddress().state %> <%= order.getShippingAddress().zip %><BR>
<%= order.getShippingAddress().country %><BR>
<%= order.getShippingContact().email %><BR>
<%= order.getShippingContact().phone %><BR>
<img src="https://shareasale.com/sale.cfm?amount=<%= order.total_order_cost%>&tracking=<%= order.order_refnum %>&transtype=sal
e&merchantID=23641" width="1" height="1">
</td>
<tr>
</TABLE>
 </div>

<jsp:include page="bitefoot.jsp"/>