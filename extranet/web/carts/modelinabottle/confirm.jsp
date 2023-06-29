<%@ page import="com.owd.core.business.order.Coupon" %>
<%@ page import="com.owd.core.business.order.LineItem" %>
<%

     System.out.println("confirm top");
    com.owd.web.carts.modelbottle.ModelBottleCart cart =
            (com.owd.web.carts.modelbottle.ModelBottleCart) request.getSession(true).getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartKey+"familycircle");

    System.out.println("confirm top");
    if(cart==null) { cart  =                (com.owd.web.carts.modelbottle.ModelBottleCart) request.getSession(true).getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartKey);
 }
    System.out.println("confirm top");
    com.owd.core.business.order.Order order = cart.old_order;
    System.out.println("Order"+cart.getOrder());
    if(order==null)
    {   order = cart.getOrder();
    }else{
        cart.order=null;
    }
    System.out.println("Order2"+order);
    order.recalculateBalance();
      System.out.println("confirm top");
%>

<jsp:include page="top.jsp"/>
<!--<DISPLAY CART>-->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<TR><TD width="15"> <BR> </TD><TD>
<table border="0" cellpadding="3" cellspacing="0" width="100%">

<% if (!("".equals(request.getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartError))))
{
%>
<TR><td colspan=5><font color="red"><%= request.getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartError)%><P> </font></TD></TR>
<%
}
%>

<tr>
  <td colspan=5 align=left>  <HR color="#FFFFFF"><B><%= (order.bill_cc_type.equals("CK")?"Printable Order Form - Reference #"+order.order_refnum:"Order Completed!<P>Your Order Reference Is: "+order.order_refnum) %><P></B>

</td>
</tr>
<tr>
  <td colspan=5 align=left>  <font size="-1" face="Geneva, Verdana, Helvetica"><% if(order.bill_cc_type.equals("CK") && !(order.coupon_code.equals("familycirclefreebottle")))
  {
  %>
<B>Your order has not been placed yet!</B>&nbsp;If you would like to pay by mail, print a copy of this page and send it with your credit card information to:<p><B>Model In A Bottle Orders<br>
Attn: Order Processing<br>
10 First Ave E.<br>
Mobridge, SD&nbsp; 57601</B></p>
<P>
If you would like to send your order via fax, print a copy of this page, fill in your credit card number below and fax the completed form to <B>1-605-845-7627</B>.
<P>
If you provided an email address, you will receive an email from us when your order ships. For questions or problems with your order, please contact us via email at <B><%=cart.storeEmail%></B>.
<P>
  <%
  }else{
  %>
<B>Please save or print this page for your records!</B><P>If you provided your email address, you will receive an email confirmation when your order ships.
  <%
  }%>
  <P>Thank you for your order!<HR color="#000000"><P>
 <A HREF="/carts/modelinabottle/cart.jsp?PRODUCT_ID=BOTTLE&NEW=YES">Click here to start a new order</A>
  </td>
</tr>
<tr VALIGN="BOTTOM">
  <TH ALIGN="LEFT" NOWRAP></TH>
  <TH ALIGN="LEFT" NOWRAP class="xxx"><font size="-1" face="Geneva, Verdana, Helvetica"><B>Product</B>&nbsp;</TH>
  <TH ALIGN="CENTER" NOWRAP class="xxx"><font size="-1" face="Geneva, Verdana, Helvetica">Quantity&nbsp;</TH>
  <TH ALIGN="CENTER" NOWRAP class="xxx"><font size="-1" face="Geneva, Verdana, Helvetica">Price&nbsp;Each&nbsp;</TH>
  <TH ALIGN="RIGHT" NOWRAP class="xxx"><font size="-1" face="Geneva, Verdana, Helvetica">Total</TH>
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
  <td ALIGN="LEFT"><font size="-1" face="Geneva, Verdana, Helvetica"><%=item.description%></td>
  <td ALIGN="CENTER" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><%=item.quantity_request+item.quantity_backordered%></td>
  <td ALIGN="CENTER" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">$ <%=decform.format(item.sku_price)%></td>
  <td ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">$ <%=decform.format(item.total_price)%></td>
</tr>

<%
	 }
%>
<tr>
  <td colspan=5 align=right>
<HR color="#FFFFFF">
</td>
</tr>

<tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Product&nbsp;Subtotal</B></TD><TD ALIGN=RIGHT><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_product_cost)%></B></td>
</tr><tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Shipping/Handling</B></TD><TD ALIGN=RIGHT><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_shipping_cost)%></B></td>
</tr>
<% if(order.coupon_code.length() > 0 && order.discount < 0)
{
%>
<tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Discount&nbsp;Applied&nbsp;(<%
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

  %>)</B></TD><TD ALIGN=RIGHT NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$<%=decform.format(order.discount)%></font></B></td>
</tr>

<% } %>
<tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Sales&nbsp;Tax</B></TD><TD ALIGN=RIGHT NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_tax_cost)%></B></td>
</tr>
<tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Grand&nbsp;Total</B></TD><TD ALIGN=RIGHT NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_order_cost)%></B></td>
</tr>
  <TR> <td colspan=5 align=right>
<BR> </TD></TR>

<%
	}else{
%>
<tr VALIGN="TOP">
  <td ALIGN="LEFT"><BR></td>
  <td ALIGN="CENTER" NOWRAP colspan="4"><font size="-1" face="Geneva, Verdana, Helvetica"><B>Cart is empty</B></td>
</tr>
<% 	} %>





</TABLE>

</TD></TR></TABLE>


<table border="0" cellpadding="0" cellspacing="0" width="100%">
<TR><TD width="15"> <BR> </TD><TD width="100%">

<TABLE border=0 width="100%" cellpadding=0 cellspacing=0  >

<tr>
  <td colspan=2 align=left>
<font size="-1" face="Geneva, Verdana, Helvetica"><B>Ordered By</B>
<HR color="#FFFFFF">
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
<% if(order.bill_cc_type.equals("CK") && !(order.coupon_code.equals("familycirclefreebottle")))
{
%>
Payment via:<P>___ Check (enclose with order)<BR>___ Credit Card :<BR>&nbsp;&nbsp;Account#:____________________<BR>&nbsp;&nbsp;Expiration month/year: _______<BR>
<%
}else{
%>
Billed to CC: XXXX-XXXX-XXXX-<%= order.cc_num.length()>=13?order.cc_num.substring(12):"" %><BR>
<%
}
%>
</td>
<tr>
  <td colspan=2 align=left>
<font size="-1" face="Geneva, Verdana, Helvetica">
 <BR>
</td>
</tr>
<tr>
  <td colspan=2 align=left>
<font size="-1" face="Geneva, Verdana, Helvetica"><B>Ship To (via <%=order.getShippingInfo().carr_service %> )</B>
<HR color="#FFFFFF">
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
    <P>

</td>
<tr>
</TABLE>

</TD></TR></TABLE>

    <!-- END CART -->

<!-- Google Code for Purchase Conversion Page -->
<script type="text/javascript">
/*<![CDATA[ */
var google_conversion_id = 1011620595;
var google_conversion_language = "en";
var google_conversion_format = "3";
var google_conversion_color = "ffffff";
var google_conversion_label = "YAgfCO3IygIQ87Ww4gM";
var google_conversion_value = 0;
/* ]]>  */
</script>
<script type="text/javascript"
src="https://www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt=""
src="https://www.googleadservices.com/pagead/conversion/1011620595/?label=YAgfCO3IygIQ87Ww4gM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>

<SCRIPT LANGUAGE="JavaScript">
<!-- Overture Services Inc. 07/15/2003
var cc_tagVersion = "1.0";
var cc_accountID = "2000853967";
var cc_marketID =  "0";
var cc_protocol="http";
var cc_subdomain = "convctr";
if(location.protocol == "https:")
{
    cc_protocol="https";
     cc_subdomain="convctrs";
}
var cc_queryStr = "?" + "ver=" + cc_tagVersion + "&aID=" + cc_accountID + "&mkt=" + cc_marketID +"&ref=" + escape(document.referrer);
var cc_imageUrl = cc_protocol + "://" + cc_subdomain + ".overture.com/images/cc/cc.gif" + cc_queryStr;
var cc_imageObject = new Image();
cc_imageObject.src = cc_imageUrl;
// -->
</SCRIPT>

<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
  var pageTracker = _gat._getTracker("UA-29586944-1");
pageTracker._setDomainName("none");
  pageTracker._setAllowHash(false);
pageTracker._setAllowLinker(true);
  pageTracker._trackPageview("/cart/order");

  pageTracker._addTrans(
    "<%= order.order_refnum  %>",                                     // Order ID
    "<%= order.po_num  %>",                            // Affiliation
    "<%= order.total_product_cost  %>",                                    // Total
    "<%= order.total_tax_cost  %>",                                     // Tax
    "<%= order.total_shipping_cost  %>",                                        // Shipping
    "<%= order.getBillingAddress().city  %>",                                 // City
    "<%= order.getBillingAddress().state  %>",                               // State
    "<%= order.getBillingAddress().country  %>"                                       // Country
  );
     <%
     for(int i=0;i<order.skuList.size();i++)
     {
       LineItem item = (LineItem) order.skuList.elementAt(i);
     %>
  pageTracker._addItem(
    "<%= order.order_refnum  %>",                                     // Order ID
    "<%= item.client_part_no  %>",                                     // SKU
    "<%= item.description  %>",                                  // Product Name
    "",                             // Category
    "<%= item.sku_price  %>",                                    // Price
    "<%= item.quantity_request  %>"                                         // Quantity
  );
  <%
  }
  %>

  pageTracker._trackTrans();
</script>
<%
    cart.order=null;
%>
<jsp:include page="bottom.jsp"/>