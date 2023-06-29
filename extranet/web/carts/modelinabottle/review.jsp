<%@ page import="com.owd.core.OWDUtilities,
                 com.owd.core.business.order.Coupon,
                 com.owd.core.csXml.OrderRater" %>
<%

com.owd.web.carts.modelbottle.ModelBottleCart cart = null;
com.owd.core.business.order.Order order = null;
try{
	  cart = (com.owd.web.carts.modelbottle.ModelBottleCart) request.getSession(true).getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartKey);
	order = cart.getOrder();
	order.recalculateBalance();

	}catch(Throwable th)
	{
		th.printStackTrace();
	}


  		//		cart.resetShipping();
  		//		com.owd.core.shipper.xml.OrderRater rater = new com.owd.core.shipper.xml.OrderRater(order);
  		//		rater.setSleepcoCartMethodsAndRates(cart);

  		//		order.total_shipping_cost = com.owd.core.OWDUtilities.roundFloat(new Float((String)cart.shipCosts.elementAt(0)).floatValue());
      	cart.resetShipping();
  				OrderRater rater = new OrderRater(order);
  				rater.setCalculateKitWeights(true);
    rater.setModelBottleCartMethodsAndRates(cart);

  				order.total_shipping_cost = OWDUtilities.roundFloat(new Float((String)cart.shipCosts.elementAt(0)).floatValue());

%>

<jsp:include page="top.jsp"/>
<!--<DISPLAY CART>-->
													<table border="0" cellpadding="0" cellspacing="0" width="100%">
														<tr>
															<td width="15">
																<br>
															</td>
															<td>
																<table border="0" cellpadding="0" cellspacing="0" width="100%">
<% if (!("".equals(request.getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartError))))
{
%>
																	<tr>
																		<td colspan="5" align=center >
																			<font color="black" size="+2"><B>
<%= request.getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartError)%></B>
																			<p>
																		</font>
																	</td>
																</tr>
<%
}
%>
																<tr>
																	<td colspan="2" align="left">
																		<form action="/carts/modelinabottle/checkout.jsp" method="POST">
																			<input type="SUBMIT" name="catalog" value="&lt;-Back to Ordering Information">
																		</form>
																	</td>
																	<td>
																		&nbsp;&nbsp;
																	</td>
																	<td colspan="2" align="right">
																		<font size="-2" face="Geneva, Verdana, Helvetica">Please check your order for accuracy and continue at bottom of page
																	</td>
																</tr>
																<tr valign="BOTTOM">
																	<th align="LEFT" nowrap>
																	</th>
																	<th align="LEFT" nowrap colspan="2" class="xxx">
																		<font size="-1" face="Geneva, Verdana, Helvetica">Ordered By:
																	</th>
																	<th align="LEFT" nowrap colspan="2" class="xxx">
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
																			Billed to CC: XXXX-XXXX-XXXX-
<%= order.cc_num.substring(12) %>
																			<br>
<%
}
%>
																</td>
																<td align="LEFT" nowrap valign="TOP" colspan="2">
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
																		<font size="-2" face="Geneva, Verdana, Helvetica">The "Ship To" address above will print directly
																			<br>
																			onto the address label of your package,
																			<br>
																			exactly as you typed it. Please double check
																			<br>
																			that it is thorough and accurate.
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
															<th align="LEFT" nowrap class="xxx">
																<font size="-1" face="Geneva, Verdana, Helvetica"><b>Product</b>
															</th>
															<th align="CENTER" nowrap class="xxx">
																<font size="-1" face="Geneva, Verdana, Helvetica">&nbsp;Quantity&nbsp;
															</th>
															<th align="CENTER" nowrap class="xxx">
																<font size="-1" face="Geneva, Verdana, Helvetica">Price&nbsp;Each&nbsp;
															</th>
															<th align="RIGHT" nowrap class="xxx">
																<font size="-1" face="Geneva, Verdana, Helvetica">Total
															</th>
														</tr>
														<tr>
															<td>
																<br>
															</td>
														</tr>
<%
	java.text.DecimalFormat decform = new java.text.DecimalFormat("#,###,##0.00");
	if(cart.getLineCount() > 0)
	{

	for(int i=0;i<order.skuList.size();i++)
	{
		com.owd.core.business.order.LineItem item = (com.owd.core.business.order.LineItem)order.skuList.elementAt(i);
%>
<!--SoftCart.cart.product.Loopbegin-->
														<tr valign="TOP">
															<td align="LEFT">
																<br>
															</td>
															<td align="LEFT">
																<font size="-1" face="Geneva, Verdana, Helvetica">
<%=item.description%>
															</td>
															<td align="CENTER" nowrap>
																<font size="-1" face="Geneva, Verdana, Helvetica">
<%=item.quantity_request%>
															</td>
															<td align="CENTER" nowrap>
																<font size="-1" face="Geneva, Verdana, Helvetica">$
<%=decform.format(item.sku_price)%>
															</td>
															<td align="RIGHT" nowrap>
																<font size="-1" face="Geneva, Verdana, Helvetica">$
<%=decform.format(item.total_price)%>
															</td>
														</tr>
<%
	}
%>
														<tr>
															<td colspan="5" align="right">
																<hr color="#000000">
															</td>
														</tr>
														<tr>
															<td colspan="4" align="RIGHT" nowrap>
																<font size="-1" face="Geneva, Verdana, Helvetica"><b>Product Subtotal</b>
															</td>
															<td align="RIGHT" nowrap>
																<font size="-1" face="Geneva, Verdana, Helvetica"><b>&nbsp;&nbsp;&nbsp;&nbsp;$
<%=decform.format(order.total_product_cost)%>
																	</b>
															</td>
														</tr>
														<tr>
															<td colspan="4" align="RIGHT" nowrap>
																<font size="-1" face="Geneva, Verdana, Helvetica"><b>Shipping/Handling</b>
															</td>
															<td align="RIGHT" nowrap>
																<font size="-1" face="Geneva, Verdana, Helvetica"><b>&nbsp;&nbsp;&nbsp;&nbsp;$
<%=decform.format(order.total_shipping_cost)%>
																	</b>
															</td>
														</tr>
<% if(order.coupon_code.length() > 0 && order.discount < 0)
{
%>
<tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><%= order.discount<0.00f?"<FONT COLOR=RED>":"" %><B>Discount&nbsp;Applied&nbsp;(<%
  com.owd.core.business.order.Coupon coupon = Coupon.dbloadByCoupon(order.coupon_code,order.clientID);
  switch(coupon.coupon_type)
  {
  	case Coupon.kCouponTypeAmount:
  		%>
  		credit&nbsp;for&nbsp;order
  		<%
  		break;
  	case Coupon.kCouponTypePercent:
  		%>
  		<%= (coupon.disc_pct*100)%>% discount&nbsp;on&nbsp;product&nbsp;subtotal
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

  %>)</B><%= order.discount<0.00f?"</FONT>":"" %></TD><TD ALIGN=RIGHT NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;<%= order.discount<0.00f?"<FONT COLOR=RED>":"" %>$&nbsp;<%=decform.format(order.discount)%><%= order.discount<0.00f?"</FONT>":"" %></font></B></td>
</tr>

<% } %>
														<tr>
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
															<td colspan="4" align="RIGHT" nowrap>
																<font size="-1" face="Geneva, Verdana, Helvetica"><b>Grand Total</b>
															</td>
															<td align="RIGHT" nowrap>
																<font size="-1" face="Geneva, Verdana, Helvetica"><b>&nbsp;&nbsp;&nbsp;&nbsp;$
<%=decform.format(order.total_order_cost)%>
<%= (order.getShippingAddress().country.equals("USA")?"":"&nbsp;U.S.&nbsp;Dollars") %>
																	</b>
															</td>
														</tr>
														<tr>
															<td colspan="5" align="right">
<%= (order.getShippingAddress().country.equals("USA")?"":"<font size=\"-2\" face=\"Geneva, Verdana, Helvetica\">For non-US orders, any applicable Duties, Customs and Taxes<BR>will be the sole responsibility of the recipient</font>") %>
															</td>
														</tr>
														<tr>
															<td colspan="5" align="right">
																<font size="-2" face="Geneva, Verdana, Helvetica">
															</td>
														</tr>
<%
	}else{
%>
														<tr valign="TOP">
															<td align="LEFT">
																<br>
															</td>
															<td align="CENTER" nowrap colspan="4">
																<font size="-1" face="Geneva, Verdana, Helvetica"><b>Cart is empty</b>
															</td>
														</tr>
<% 	} %>
													</table>
													<form method="POST" action="/carts/modelinabottle/store">
												</td>
											</tr>
										</table>
										<table border="0" cellpadding="0" cellspacing="0" WIDTH="100%">
											<tr>
												<td width="15">
													<br>
												</td>
												<td>
													<table border="0" width="100%" cellpadding="0" cellspacing="0">
														<tr>
															<td colspan="2" align="left">
																<font size="-1" face="Geneva, Verdana, Helvetica">
																	<hr color="#000000">
															</td>
														</tr>
                                                        <tr>
					<td colspan="2" align="left">
						<font size="-1" face="Geneva, Verdana, Helvetica"> USPS shipping methods do not provide a tracking number. If you wish to track your packages while they are in transit, you must choose a UPS or FedEx shipping method.
						<P>

						 Please choose a shipping option (the indicated cost and any applicable additional sales tax will be added to your total shown above) :
						<P>
							&nbsp;
				</td>
			</tr>
<%
cart.getOrder().total_shipping_cost = OWDUtilities.roundFloat(new Float((String)cart.shipCosts.elementAt(0)).floatValue());

				cart.getOrder().recalculateBalance();

	for (int i=0;i<cart.shipTypes.size();i++)
	{
%>
			<tr>
				<td align="left" width="100%" valign="TOP">
					<font size="-1" face="Geneva, Verdana, Helvetica"> <INPUT TYPE=RADIO NAME=shipping VALUE="<%= (String)cart.shipTypes.elementAt(i)%>"
<%= (i==0?"CHECKED":"") %>
						>
<%= (String)cart.shipTypes.elementAt(i)%>
<%= ((((String)cart.shipTypes.elementAt(i)).indexOf("UPS") >= 0 || ((String)cart.shipTypes.elementAt(i)).indexOf("FedEx") >= 0)?"<BR><FONT size=-2 >No P.O. Boxes or APO/FPO addresses":"") %>
						<br>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td align="RIGHT" valign="TOP">
					<font size="-1" face="Geneva, Verdana, Helvetica"><b>
<%= (i==0?"Standard":"Adds&nbsp;$&nbsp;"+decform.format(new Float((String)cart.shipCosts.elementAt(i)).floatValue()))%>
						</b>
				</td>
			</tr>
<%  } %>
			<tr>
				<td colspan="2" align="left">
					<font size="-1" face="Geneva, Verdana, Helvetica">
						<hr color="#000000">
				</td>
			</tr>
														<tr>
															<td colspan="2" align="right">
																<font size="-1" face="Geneva, Verdana, Helvetica"> <input type="HIDDEN" name="do" value="6"> <input type="SUBMIT" name="finish" value="<%= (order.bill_cc_type.equals(" CK")?"Display Printable Order Form ->":"Place Order ->") %>" >
																	<br>
																	Please click the "Place Order" button only once.
																	<br>
																	Confirmation may take up to 2 minutes.
																	<br>
																	Clicking on the button more than once may
																	<br>
																	result in multiple orders.
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
										</form>
<!-- END CART -->
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
var pageTracker = _gat._getTracker("UA-29586944-1");
pageTracker._setDomainName("none");
pageTracker._setAllowHash(false);
pageTracker._setAllowLinker(true);
pageTracker._trackPageview("/cart/review");
</script>
<jsp:include page="bottom.jsp"/>