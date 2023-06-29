<%@ page import="com.owd.core.business.Address,
				 com.owd.core.business.Client,
				 com.owd.core.business.Contact,
				 com.owd.core.business.order.LineItem,
				 com.owd.core.business.order.Order,
				 java.text.DecimalFormat,
				 java.text.NumberFormat"%>
<%@ page import="java.util.Locale" %>

<%
        Order order = (Order) request.getSession(true).getAttribute("currorder");
        String client_id = (String) request.getSession(true).getAttribute("client_id");
        String client_name = (String) request.getSession(true).getAttribute("client_name");

	String trackEvent = null;     //for AV - 117
    trackEvent = "SalePhone";
                              if(order.bill_cc_type.equals("CK"))
                              {
                                  trackEvent = "SalePhoneCheck";
                              }

        if(order==null&&client_id!=null)
                order = new Order(client_id);
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

	DecimalFormat dform = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);



request.setAttribute("clientselector", Client.getClientForID(client_id).company_name);
request.setAttribute("areatitle","Order Entry");
String error = (String) request.getAttribute("errormessage");

%>

<jsp:include page="/extranettop.jsp" />
<h1> The order has been submitted by the user "<%=order.created_by%>".</h1>
<title><%=client_name%> Order Submission Receipt</title>
<body>
<center>
<H2> <%=client_name%> Receipt </H2>
<p> Please print this receipt for your records </p>
<p> <a href="orderentry.jsp?saved=Y" decoration = "none" target="_top"> Return to the order entry page </a></p>
</center>
<br /><br />
<% if (trackEvent != null && "117".equals(order.clientID))
{
%><!--BEGIN ROI TRACKING Code-->
<script language="javascript" src="https://track.roiservice.com/track/track.aspx?ROIID=937433107743318"></script>
<script language="javascript">
<!--
	if (typeof(ROIID) + '' != 'undefined') {
		TrackEvent('<%= trackEvent %>', '<%= order.total_order_cost%>','<%= order.order_refnum%>');
	}
//-->
</script>
<!--END ROI TRACKING Code-->
<%
}
%>
<table border=0 width="85%">
<tr>
<td>
        <font color="red"><b>OWD Order Number:</b> </font>
</td>
<td>
        <%=order.orderNum%>
</td>
<td>
        <font color="red"><b>Client Order Number:</b> </font>
</td>
<td>
        <%=order.order_refnum%>
</td>

</tr>
</table>

<table border=0 width="85%">
<tr>
<td> Product </td> <td> Quantity </td> <td> Unit Price </td> <td> Sub-Total </td>
</tr>
<tr>
<%
        for (int i=0; i<order.skuList.size(); i++)
        {
                LineItem li = (LineItem) order.skuList.get(i);
%>
<td><%=li.client_part_no%></td> <td><%=li.quantity_request%></td><td><%=dform.format(li.sku_price)%></td><td><%=dform.format(li.total_price)%></td>
</tr>
<%
        }
%>
<tr>&nbsp;</tr>
<tr>&nbsp;</tr>
<tr>
<td></td><td></td><td> Shipping and Handling: </td><td><%=dform.format(order.total_shipping_cost)%></td>
</tr>
<tr>
<td></td><td></td><td> Discount Applied: </td><td><%=dform.format(-1.00f*(Math.abs(order.discount)))%></td>
</tr>
<tr>
<td></td><td></td><td> Sales Tax: </td><td><%=dform.format(order.total_tax_cost)%></td>
</tr>
<tr>
<td></td><td></td><td> Grand Total: </td><td><%=dform.format(order.total_order_cost)%></td>
</tr>
<tr>
<td></td><td></td><td> Declared Value: </td><td><%=order.getShippingInfo().declared_value%></td>
</tr>


</table>
<br /> <br />

<H2> Billing Address </H2>
<table border=0 width=100%>
<tr>
<td> Name: </td>
<td> <%=b_contact.name%></td>
</tr>
<tr>
<td> Company: </td>
<td> <%=b_addr.company_name%> </td>
</tr>
<tr>
<td> Phone: </td>
<td> <%=b_contact.phone%> </td>
</tr>
<tr>
<td> Address: </td>
<td> <%=b_addr.address_one%> </td>
</tr>
<tr>
<td>  </td>
<td> <%=b_addr.address_two%> </td>
</tr>
<tr>
<td>  </td>
<td> <%=b_addr.city%>,&nbsp; </td> <td><%=b_addr.state%> &nbsp;</td><td><%=b_addr.zip%></td>
</tr>
<tr>
<td>  </td>
<td> <%=b_addr.country%> </td>
</tr>
</table>
<br />
<br />
<H2> Shipping Details: </H2>
<p> <%=order.getShippingInfo().carr_freight_terms%>&nbsp;<%=order.getShippingInfo().carr_service%></p>
<p> Scheduled shipping date: &nbsp;<%=order.getShippingInfo().scheduled_ship_date%></p>
<br />
<br />
<H2> Shipping Address </H2>
<table border=0 width=100%>
<tr>
<td> Name: </td>
<td> <%=s_contact.name%></td>
</tr>
<tr>
<td> Company: </td>
<td> <%=s_addr.company_name%> </td>
</tr>
<td> Phone: </td>
<td> <%=s_contact.phone%> </td>
</tr>
<tr>
<td> Address: </td>
<td> <%=s_addr.address_one%> </td>
</tr>
<tr>
<td>  </td>
<td> <%=s_addr.address_two%> </td>
</tr>
<tr>
<td>  </td>
<td> <%=s_addr.city%>,&nbsp; </td> <td><%=s_addr.state%> &nbsp;</td><td><%=s_addr.zip%></td>
</tr>
<tr>
<td>  </td>
<td> <%=s_addr.country%> </td>
</tr>
</table>
<br /> <br />
<center>
<%
        //session.invalidate();
        //session = request.getSession(true);
%>
</center>
<% if ("117".equals(order.clientID))
{
%>
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
  var pageTracker = _gat._getTracker("UA-4481717-1");
  pageTracker._initData();
pageTracker._setDomainName("none");
pageTracker._setAllowLinker(true);
  pageTracker._trackPageview("/av/phoneorder");

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
<% }
%>
<!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

    </BODY>
</HTML>

