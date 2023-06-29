<%@ page import="com.owd.core.OWDUtilities"%>
<%@ page import="com.owd.core.business.order.Coupon"%>

<%@ page import="com.owd.core.business.order.LineItem"%>
<%@ page import="com.owd.core.business.order.Order"%>
<%@ page import="com.owd.web.carts.tazzle.TazzleCart"%>
<%@ page import="com.owd.web.carts.tazzle.TazzleServlet" %>
<%
 
TazzleCart cart = null;
Order order = null;
try{
	  cart = (TazzleCart) request.getSession(true).getAttribute(TazzleServlet.kCartKey);
	order = cart.getOrder();
	order.recalculateBalance(); 
	
	}catch(Throwable th)
	{
		th.printStackTrace();
	}
	
%><jsp:include page="bitehead.jsp"/>
<div id="cart">
<FORM ACTION="/carts/tazzleit/tazzleit.jsp" METHOD="POST">
 <table border="0" cellpadding="0" cellspacing="0" width="100%">
<TR><td colspan=5> <BR> </TD>      </TR>
    <TR><td colspan=5 align="LEFT"><B>Shopping Cart</B></TD></TR>
<TR><td colspan=5> <BR> </TD></TR>
<% if (!("".equals(request.getAttribute(TazzleServlet.kCartError))))
{
%>
<TR><td colspan=5><font color="red" face="Geneva, Verdana, Helvetica" size="+1"><%= request.getAttribute(TazzleServlet.kCartError)%><P> </font></TD></TR>
<%
}
%>
 <tr>
  <td colspan=2 align=left>
  <INPUT TYPE=HIDDEN NAME=do VALUE=4><INPUT TYPE=SUBMIT NAME="catalog" VALUE="<-Back to Ordering Information">
  </FORM>
</td>
  <td colspan=3 align=right><font size="-1" face="Geneva, Verdana, Helvetica">Continue at bottom of page

</td>
</tr>
     <tr>
         <td colspan="5" align="right">
             <hr color="#000000">
         </td>
     </tr>
     
     	<tr valign="BOTTOM">
			<th align="LEFT" nowrap>
			</th>
			<th align="LEFT" nowrap colspan="2">
				<font size="-1" face="Geneva, Verdana, Helvetica">Ordered By:
			</th>
			<th align="LEFT" nowrap colspan="2">
				<font size="-1" face="Geneva, Verdana, Helvetica">Ship To:
			</th>
		</tr>
		<tr valign="BOTTOM">
			<th align="LEFT" nowrap>
			</th>
			<td align="LEFT" nowrap valign="TOP" colspan="2">
				<font size="-1" face="Geneva, Verdana, Helvetica">
<%= order.getBillingContact().name %>
					<br>
<%= order.getBillingAddress().address_one %>
					<br>
<%= (order.getBillingAddress().address_two.length() > 0?order.getBillingAddress().address_two+"<BR>":"") %>
<%= order.getBillingAddress().city %>
					,
<%= order.getBillingAddress().state %>
<%= order.getBillingAddress().zip %>
					<br>
<%= order.getBillingAddress().country %>
					<br>
<%= order.getBillingContact().email %>
					<br>
<%= order.getBillingContact().phone %>
				<p>
<% if(order.bill_cc_type.equals("CK"))
{
%>
<%
}else{
%>
					Billed to CC: XXXX-XXXX-XXXX-<%= order.cc_num.length()>=12?order.cc_num.substring(12):"" %>
					<br>
<%
}
%>
		</td>
		<td align="LEFT" nowrap valign="TOP" colspan="2" width="50%">
			<font size="-1" face="Geneva, Verdana, Helvetica">
<%= order.getShippingContact().name %>
				<br>
<%= order.getShippingAddress().address_one %>
				<br>
<%= (order.getShippingAddress().address_two.length() > 0?order.getShippingAddress().address_two+"<BR>":"") %>
<%= order.getShippingAddress().city %>
				,
<%= order.getShippingAddress().state %>
<%= order.getShippingAddress().zip %>
				<br>
<%= order.getShippingAddress().country %>
				<br>
<%= order.getShippingContact().email %>
				<br>
<%= order.getShippingContact().phone %>
			<p>
				<font size="-1" face="Geneva, Verdana, Helvetica">
	</td>
</tr>
<tr>
  <td colspan=5 align=center> <HR color="#000000"><font size="-1" face="Geneva, Verdana, Helvetica">The "Ship To" address above will print directly
					onto the address label of your package,
					exactly as you typed it. Please double check
					that it is thorough and accurate.
<HR color="#000000">
</td>
</tr>
<tr VALIGN="BOTTOM">
  <TH ALIGN="LEFT" NOWRAP></TH>
  <TH ALIGN="LEFT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Product</B></TH>
  <TH ALIGN="CENTER" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">Quantity</TH>
  <TH ALIGN="CENTER" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">Price Each</TH>
  <TH ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">Total</TH>
</tr>
<tr>
    <td><BR></td>
</tr>
<%
	java.text.DecimalFormat decform = new java.text.DecimalFormat("#,###,##0.00");
	if(cart.getLineCount() > 0)
	{

	for(int i=0;i<order.skuList.size();i++)
	{
		LineItem item = (LineItem)order.skuList.elementAt(i);
%>
<!--SoftCart.cart.product.Loopbegin-->

<tr VALIGN="TOP">
  <td ALIGN="LEFT"><BR></td>
  <td ALIGN="LEFT"><font size="-1" face="Geneva, Verdana, Helvetica"><%=item.description%></td>
  <td ALIGN="CENTER" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><%=item.quantity_request%></td>
  <td ALIGN="CENTER" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">$ <%=decform.format(item.sku_price)%></td>
  <td ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">$ <%=decform.format(item.total_price)%></td>
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
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Product Subtotal</B></TD><TD ALIGN=RIGHT><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_product_cost)%></B></td>
</tr><tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Shipping/Handling</B></TD><TD ALIGN=RIGHT><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_shipping_cost)%></B></td>
</tr>
<tr>
<% if(order.coupon_code.length() > 0 && order.discount < 0)
{
%>
<tr>
	<td colspan="4" align="RIGHT" nowrap>
		<font size="-1" face="Geneva, Verdana, Helvetica"><b>Discount&nbsp;Applied&nbsp;(
<%
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
<%= (coupon.disc_pct*100)%>
				% discount&nbsp;on&nbsp;product&nbsp;subtotal
<%
  		break;
  	case Coupon.kCouponTypeShipAmt:
  		%>
				credit&nbsp;toward&nbsp;shipping
<%
  		break;
  	case Coupon.kCouponTypeShipPct:
  		%>
<%= (coupon.ship_pct*100)%>
				%&nbsp;discount&nbsp;on&nbsp;standard&nbsp;shipping
<%
  		break;


  }

  %>
				)</b>
	</td>
	<td align="RIGHT" nowrap>
		<font size="-1" face="Geneva, Verdana, Helvetica"><b>&nbsp;&nbsp;&nbsp;&nbsp;$&nbsp;
<%=decform.format(order.discount)%>
		</font></b>
	</td>
</tr>
<% } %>
<td colspan="4" align="RIGHT" nowrap>
<font size="-1" face="Geneva, Verdana, Helvetica"><b>Sales Tax</b>
</td>
<td align="RIGHT" nowrap>
<font size="-1" face="Geneva, Verdana, Helvetica"><b>&nbsp;&nbsp;&nbsp;&nbsp;$
<%=decform.format(order.total_tax_cost)%>
	</b>
</td>
</tr>
<tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Grand Total</B></TD><TD ALIGN=RIGHT><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_order_cost)%></B></td>
</tr>
<tr>
	<td colspan="5" align="right">
<%= (order.getShippingAddress().country.equals("USA")?"":"<font size=\"-1\" face=\"Geneva, Verdana, Helvetica\">For non-US orders, any applicable Duties, Customs and Taxes<BR>will be the sole responsibility of the recipient</font>") %>
	</td>
</tr>
  <TR> <td colspan=5 align=right>
<BR> </TD></TR>
<tr>
  <td colspan=5 align=right><font size="-1" face="Geneva, Verdana, Helvetica"></td>
</tr>
<%
	}else{
%>
<tr VALIGN="TOP">
  <td ALIGN="LEFT"><BR></td>
  <td ALIGN="CENTER" NOWRAP colspan="4"><font size="-1" face="Geneva, Verdana, Helvetica"><B>Cart is empty</B></td>
</tr>
<% 	} %>





</TABLE>
<FORM METHOD="POST" ACTION="/carts/tazzleit/tazzleit.jsp">


<table border="0" cellpadding="0" cellspacing="0" width="100%">
<TR><TD>

<TABLE border=0 width=100% cellpadding=0 cellspacing=0  >
	<tr>
		<td colspan="3">
			<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="2" align="left">
						<font face="Geneva, Verdana, Helvetica"><b>Shipping Options</b>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="left"><font size="-1" face="Geneva, Verdana, Helvetica">
					Please choose a shipping option :

				</td>
			</tr>
                </table>
          <HR color="#000000">
            </td></tr>
    <tr><th nowrap align="left"><font size="-1" face="Geneva, Verdana, Helvetica">Ship Method</th><th nowrap align="right"><font size="-1" face="Geneva, Verdana, Helvetica">Shipping Cost</th><th nowrap align="right"><font size="-1" face="Geneva, Verdana, Helvetica">Order Total</th></tr>
<%
cart.getOrder().total_shipping_cost = OWDUtilities.roundFloat(new Float((String)cart.shipCosts.elementAt(0)).floatValue());

				cart.getOrder().recalculateBalance();

	for (int i=0;i<cart.shipTypes.size();i++)
	{
        String shiptype=(String)cart.shipTypes.elementAt(i);
       String deliverydays = "";
       
%>         <tr><td colspan="3"><HR color="#000000"></td></tr>
			<tr>
				<td align="left" width="100%" valign="TOP">
					<font size="-1" face="Geneva, Verdana, Helvetica"> <INPUT TYPE=RADIO NAME=shipping VALUE="<%= (String)cart.shipRefs.elementAt(i)%>"
<%= (i==0?"CHECKED":"") %>
						>
<%= (String)cart.shipTypes.elementAt(i)%>
<%= ((((String)cart.shipTypes.elementAt(i)).indexOf("UPS") >= 0 || ((String)cart.shipTypes.elementAt(i)).indexOf("FedEx") >= 0)?"<BR><FONT size=-1 >No P.O. Boxes or APO/FPO addresses":"") %>
			</td>
				<td align="RIGHT" ><font size="-1" face="Geneva, Verdana, Helvetica">
                        <b>$ <%= decform.format(new Float((String)cart.shipCosts.elementAt(i)).floatValue())%></b>
				</td>
               <td align="RIGHT" ><font size="-1" face="Geneva, Verdana, Helvetica">
                        <b>$ <%= decform.format(cart.calculateTotalForShippingAmount(new Float((String)cart.shipCosts.elementAt(i)).floatValue()))%></b>
				</td>
			</tr>
<%  } %>
   <tr><td colspan="3"><HR color="#000000"></td></tr>
<tr>
     <td colspan=2 align=left valign="top"><font size="-1" face="Geneva, Verdana, Helvetica">Review your order summary above to ensure accurate delivery.
</td>
  <td align=right valign="center">
<font size="-1" face="Geneva, Verdana, Helvetica">
<INPUT TYPE=HIDDEN NAME=cd VALUE=429><INPUT TYPE=HIDDEN NAME=do VALUE=6>
<INPUT TYPE=SUBMIT NAME="finish" VALUE="Place Order ->">      </font>
</td>
</tr>
</TABLE>
    </TD></TR></table>


</FORM>
<!-- end cart display -->		
 </div>
<!-- end cart display -->
<jsp:include page="bitefoot.jsp"/>

