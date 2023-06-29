<%@ page import="com.owd.core.business.order.LineItem"%>
<%@ page import="com.owd.core.business.order.Order"%>
<%@ page import="com.owd.web.carts.autumnleaves.AutumnLeavesCart" %>
<%@ page import="com.owd.web.carts.autumnleaves.AutumnLeavesServlet" %>
<%
	AutumnLeavesCart cart = (AutumnLeavesCart) request.getSession(true).getAttribute(AutumnLeavesServlet.kCartKey);
	
	Order order = cart.getOrder();
	order.recalculateBalance(); 

       String dom_bill_state = (String) request.getSession(true).getAttribute("dom_bill_state");
    if (dom_bill_state == null) dom_bill_state = "";
    String dom_ship_state = (String) request.getSession(true).getAttribute("dom_ship_state");
    if (dom_ship_state == null) dom_ship_state = "";
    String intl_bill_state = (String) request.getSession(true).getAttribute("intl_bill_state");
    if (intl_bill_state == null) intl_bill_state = "";
    String intl_ship_state = (String) request.getSession(true).getAttribute("intl_ship_state");
    if (intl_ship_state == null) intl_ship_state = "";
    String bill_country = order.getBillingAddress().country;
    String ship_country = order.getShippingAddress().country;


	String bill_state = order.getBillingAddress().state;
    String ship_state = order.getShippingAddress().state;
	
                         
%>  <jsp:include page="bitehead.jsp"/>
<!--<DISPLAY CART>-->
<div id="cart">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<TR><td colspan=5> <BR> </TD></TR>
<% if (!("".equals(request.getAttribute(AutumnLeavesServlet.kCartError))))
{
%>
<TR><td colspan=5><font color="red" face="Geneva, Verdana, Helvetica" size="+1"><%= request.getAttribute(AutumnLeavesServlet.kCartError)%><P> </font></TD>
<%
}
%>
 <tr>
  <td colspan=2 align=left><FORM ACTION="/carts/autumnleaves/autumnleaves.jsp" METHOD="GET">
  <INPUT TYPE=SUBMIT NAME="catalog" VALUE="<-Back to Shopping Cart">
  </FORM>
</td>
  <td colspan=3 align=right><font size="-1" face="Geneva, Verdana, Helvetica">Continue at bottom of page

</td>
</tr>
<tr>
  <td colspan=5 align=right>
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
  <td></td>
  <td COLSPAN=5 ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><B>Product Subtotal&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_product_cost)%></B></td>
</tr>  <td colspan=5 align=right>
<BR>

<%
	}else{
%>
<tr VALIGN="TOP">
  <td ALIGN="LEFT"><BR></td>
  <td ALIGN="CENTER" NOWRAP colspan="4"><font size="-1" face="Geneva, Verdana, Helvetica"><B>Cart is empty</B></td>
</tr>
<% 	} %>





</TABLE>
<FORM METHOD="POST" ACTION="/carts/autumnleaves/autumnleaves.jsp">

<table border="0" cellpadding="0" cellspacing="0" width="100%">
<TR><TD >

<TABLE border=0 width=100% cellpadding=3 cellspacing=0  >

<tr>
  <td colspan=2 align=left>
<font size="-1" face="Geneva, Verdana, Helvetica"><B>Billing Information</B>
<HR color="#000000">
</td>
</tr>
<TR>
<TD ALIGN=RIGHT >
<BR><font face="Geneva, Verdana, Helvetica" size="-1">Purchaser's&nbsp;Name:
</TD>
<TD>
<BR><font face="Geneva, Verdana, Helvetica" size="-1"><INPUT TYPE=TEXT SIZE=50 NAME="bill_billingName" VALUE="<%=order.getBillingContact().name%>" >
</TD>
</TR>
<TR>
<TD>
&nbsp;
</TD>
<TD>
<font face="Geneva, Verdana, Helvetica" size="-1">Please type your full name as it appears on your credit card
</TD>
</TR>
<TR>
<TD ALIGN=RIGHT>
<font face="Geneva, Verdana, Helvetica" size="-1">Address&nbsp;Line&nbsp;1:
</TD><TD ><INPUT TYPE=TEXT size=50 NAME=bill_address1 VALUE="<%=order.getBillingAddress().address_one%>" >
</TD></TR>
<TR>
<TD ALIGN=RIGHT>
<font face="Geneva, Verdana, Helvetica" size="-1">Address&nbsp;Line&nbsp;2:
</TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT SIZE=50 NAME=bill_address2 VALUE="<%=order.getBillingAddress().address_two%>" >
</TD>
</TR>
<TR><TD ALIGN=RIGHT ><font face="Geneva, Verdana, Helvetica" size="-1">City: </TD><TD ALIGN=left><font face="Geneva, Verdana, Helvetica" size="-1"><INPUT TYPE=TEXT SIZE=26 NAME=bill_city VALUE="<%=order.getBillingAddress().city%>" ></TD></TR>
<TR>
<TD ALIGN=RIGHT VALIGN=TOP>
<font face="Geneva, Verdana, Helvetica" size="-1">State/Province:
</TD><TD ><TABLE BORDER="0"><TR><TD VALIGN=TOP><SELECT NAME="dom_bill_state" SIZE=1>
	<OPTION VALUE=""<%= (dom_bill_state.equals("")?" SELECTED":"") %>>Click to Select
	<OPTION VALUE="AL"<%= (dom_bill_state.equals("AL")?" SELECTED":"") %>>Alabama
	<OPTION VALUE="AK"<%= (dom_bill_state.equals("AK")?" SELECTED":"") %>>Alaska
	<OPTION VALUE="AS"<%= (dom_bill_state.equals("AS")?" SELECTED":"") %>>American Samoa
	<OPTION VALUE="AZ"<%= (dom_bill_state.equals("AZ")?" SELECTED":"") %>>Arizona
	<OPTION VALUE="AR"<%= (dom_bill_state.equals("AR")?" SELECTED":"") %>>Arkansas
	<OPTION VALUE="AA"<%= (dom_bill_state.equals("AA")?" SELECTED":"") %>>Armed Forces - USA/Canada
	<OPTION VALUE="AE"<%= (dom_bill_state.equals("AE")?" SELECTED":"") %>>Armed Forces - Europe
	<OPTION VALUE="AP"<%= (dom_bill_state.equals("AP")?" SELECTED":"") %>>Armed Forces - Pacific
	<OPTION VALUE="CA"<%= (dom_bill_state.equals("CA")?" SELECTED":"") %>>California
	<OPTION VALUE="CO"<%= (dom_bill_state.equals("CO")?" SELECTED":"") %>>Colorado
	<OPTION VALUE="CT"<%= (dom_bill_state.equals("CT")?" SELECTED":"") %>>Connecticut
	<OPTION VALUE="DE"<%= (dom_bill_state.equals("DE")?" SELECTED":"") %>>Delaware
	<OPTION VALUE="DC"<%= (dom_bill_state.equals("DC")?" SELECTED":"") %>>District of Columbia
	<OPTION VALUE="FM"<%= (dom_bill_state.equals("FM")?" SELECTED":"") %>>Federated States of Micronesia
	<OPTION VALUE="FL"<%= (dom_bill_state.equals("FL")?" SELECTED":"") %>>Florida
	<OPTION VALUE="GA"<%= (dom_bill_state.equals("GA")?" SELECTED":"") %>>Georgia
	<OPTION VALUE="GU"<%= (dom_bill_state.equals("GU")?" SELECTED":"") %>>Guam
	<OPTION VALUE="HI"<%= (dom_bill_state.equals("HI")?" SELECTED":"") %>>Hawaii
	<OPTION VALUE="ID"<%= (dom_bill_state.equals("ID")?" SELECTED":"") %>>Idaho
	<OPTION VALUE="IL"<%= (dom_bill_state.equals("IL")?" SELECTED":"") %>>Illinois
	<OPTION VALUE="IN"<%= (dom_bill_state.equals("IN")?" SELECTED":"") %>>Indiana
	<OPTION VALUE="IA"<%= (dom_bill_state.equals("IA")?" SELECTED":"") %>>Iowa
	<OPTION VALUE="KS"<%= (dom_bill_state.equals("KS")?" SELECTED":"") %>>Kansas
	<OPTION VALUE="KY"<%= (dom_bill_state.equals("KY")?" SELECTED":"") %>>Kentucky
	<OPTION VALUE="LA"<%= (dom_bill_state.equals("LA")?" SELECTED":"") %>>Louisiana
	<OPTION VALUE="ME"<%= (dom_bill_state.equals("ME")?" SELECTED":"") %>>Maine
	<OPTION VALUE="MH"<%= (dom_bill_state.equals("MH")?" SELECTED":"") %>>Marshall Islands
	<OPTION VALUE="MD"<%= (dom_bill_state.equals("MD")?" SELECTED":"") %>>Maryland
	<OPTION VALUE="MA"<%= (dom_bill_state.equals("MA")?" SELECTED":"") %>>Massachusetts
	<OPTION VALUE="MI"<%= (dom_bill_state.equals("MI")?" SELECTED":"") %>>Michigan
	<OPTION VALUE="MN"<%= (dom_bill_state.equals("MN")?" SELECTED":"") %>>Minnesota
	<OPTION VALUE="MS"<%= (dom_bill_state.equals("MS")?" SELECTED":"") %>>Mississippi
	<OPTION VALUE="MO"<%= (dom_bill_state.equals("MO")?" SELECTED":"") %>>Missouri
	<OPTION VALUE="MT"<%= (dom_bill_state.equals("MT")?" SELECTED":"") %>>Montana
	<OPTION VALUE="NE"<%= (dom_bill_state.equals("NE")?" SELECTED":"") %>>Nebraska
	<OPTION VALUE="NV"<%= (dom_bill_state.equals("NV")?" SELECTED":"") %>>Nevada
	<OPTION VALUE="NH"<%= (dom_bill_state.equals("NH")?" SELECTED":"") %>>New Hampshire
	<OPTION VALUE="NJ"<%= (dom_bill_state.equals("NJ")?" SELECTED":"") %>>New Jersey
	<OPTION VALUE="NM"<%= (dom_bill_state.equals("NM")?" SELECTED":"") %>>New Mexico
	<OPTION VALUE="NY"<%= (dom_bill_state.equals("NY")?" SELECTED":"") %>>New York
	<OPTION VALUE="NC"<%= (dom_bill_state.equals("NC")?" SELECTED":"") %>>North Carolina
	<OPTION VALUE="ND"<%= (dom_bill_state.equals("ND")?" SELECTED":"") %>>North Dakota
	<OPTION VALUE="MP"<%= (dom_bill_state.equals("MP")?" SELECTED":"") %>>Northern Mariana Island
	<OPTION VALUE="OH"<%= (dom_bill_state.equals("OH")?" SELECTED":"") %>>Ohio
	<OPTION VALUE="OK"<%= (dom_bill_state.equals("OK")?" SELECTED":"") %>>Oklahoma
	<OPTION VALUE="OR"<%= (dom_bill_state.equals("OR")?" SELECTED":"") %>>Oregon
	<OPTION VALUE="PW"<%= (dom_bill_state.equals("PW")?" SELECTED":"") %>>Palau Island
	<OPTION VALUE="PA"<%= (dom_bill_state.equals("PA")?" SELECTED":"") %>>Pennsylvania
	<OPTION VALUE="PR"<%= (dom_bill_state.equals("PR")?" SELECTED":"") %>>Puerto Rico
	<OPTION VALUE="RI"<%= (dom_bill_state.equals("RI")?" SELECTED":"") %>>Rhode Island
	<OPTION VALUE="SC"<%= (dom_bill_state.equals("SC")?" SELECTED":"") %>>South Carolina
	<OPTION VALUE="SD"<%= (dom_bill_state.equals("SD")?" SELECTED":"") %>>South Dakota
	<OPTION VALUE="TN"<%= (dom_bill_state.equals("TN")?" SELECTED":"") %>>Tennessee
	<OPTION VALUE="TX"<%= (dom_bill_state.equals("TX")?" SELECTED":"") %>>Texas
	<OPTION VALUE="UT"<%= (dom_bill_state.equals("UT")?" SELECTED":"") %>>Utah
	<OPTION VALUE="VT"<%= (dom_bill_state.equals("VT")?" SELECTED":"") %>>Vermont
	<OPTION VALUE="VI"<%= (dom_bill_state.equals("VI")?" SELECTED":"") %>>Virgin Islands
	<OPTION VALUE="VA"<%= (dom_bill_state.equals("VA")?" SELECTED":"") %>>Virginia
	<OPTION VALUE="WA"<%= (dom_bill_state.equals("WA")?" SELECTED":"") %>>Washington
	<OPTION VALUE="WV"<%= (dom_bill_state.equals("WV")?" SELECTED":"") %>>West Virginia
	<OPTION VALUE="WI"<%= (dom_bill_state.equals("WI")?" SELECTED":"") %>>Wisconsin
	<OPTION VALUE="WY"<%= (dom_bill_state.equals("WY")?" SELECTED":"") %>>Wyoming</SELECT>
</TD></TR></TABLE>
</TD></TR>

<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Zip/Postal&nbsp;Code: </TD><TD align="left"><font face="Geneva, Verdana, Helvetica" size="-1"><INPUT TYPE=TEXT SIZE=26 NAME=bill_zip VALUE="<%=order.getBillingAddress().zip%>" ></TD></TR>
<TR>
<TD ALIGN=RIGHT>
<font face="Geneva, Verdana, Helvetica" size="-1">Country:
</TD><TD >USA<INPUT type="hidden" name="bill_country" value="USA">
</TD></TR>
    <TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Email&nbsp;Address:</TD><TD><INPUT TYPE=TEXT SIZE=31 NAME=bill_email  VALUE="<%=order.getBillingContact().email%>" ></TD></TR>


    <TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Phone:</TD><TD><INPUT TYPE=TEXT SIZE=31 NAME=bill_phone_num  VALUE="<%=order.getBillingContact().phone%>" ></TD></TR>

<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Credit&nbsp;Card&nbsp;Number:</TD>
<TD><INPUT TYPE=TEXT SIZE=16 MAXLENGTH=16 NAME=cc_num ><font face="Geneva, Verdana, Helvetica" size="-1">&nbsp;&nbsp;Expires: <SELECT NAME=cc_exp_mon >
<OPTION VALUE=01>January
<OPTION VALUE=02>February
<OPTION VALUE=03>March
<OPTION VALUE=04>April
<OPTION VALUE=05>May
<OPTION VALUE=06>June
<OPTION VALUE=07>July
<OPTION VALUE=08>August
<OPTION VALUE=09>September
<OPTION VALUE=10>October
<OPTION VALUE=11>November
<OPTION VALUE=12>December
</SELECT><SELECT NAME=cc_exp_year >
<OPTION VALUE=2010>2010
<OPTION VALUE=2011>2011
<OPTION VALUE=2012>2012
<OPTION VALUE=2013>2013
<OPTION VALUE=2014>2014
<OPTION VALUE=2015>2015
<OPTION VALUE=2016>2016
<OPTION VALUE=2017>2017
<OPTION VALUE=2018>2018
<OPTION VALUE=2019>2019</SELECT></TD></TR>
<TR><TD >&nbsp;</TD><TD><font face="Geneva, Verdana, Helvetica" size="-1">We accept VISA, MasterCard, Discover and American Express credit cards.</TD></TR>
<TR>
<TD ALIGN=RIGHT ><font face="Geneva, Verdana, Helvetica" size="-1">CVV&nbsp;Code:
</TD>
<TD><INPUT TYPE=TEXT SIZE=4 NAME="cc_cvv" VALUE="" >
</TD>
</TR>
<TR><TD >&nbsp;</TD><TD><font face="Geneva, Verdana, Helvetica" size="-1">
The CVV (Customer Verification Value or Card ID #) is an added security feature to help protect you from online credit card fraud. You'll find the three digit CVV number printed on the back of your VISA, Mastercard or Discover card; American Express has a four digit number on the front.</TD></TR>
<TR>
<TD ALIGN=RIGHT >
<BR><font face="Geneva, Verdana, Helvetica" size="-1">Promo&nbsp;Code:
</TD>
<TD>
<BR><INPUT TYPE=TEXT SIZE=25 NAME="coupon" VALUE="<%=order.coupon_code%>" >
</TD>
</TR>
<TR>
<TD>
&nbsp;
</TD>
<TD>
<font face="Geneva, Verdana, Helvetica" size="-1">If you have a promotional code, enter it here. Otherwise, leave this entry blank.
</TD>
</TR>
    <TR>
<TD ALIGN=RIGHT VALIGN=TOP>
<font face="Geneva, Verdana, Helvetica" size="-1">How did you hear about us?
</TD><TD ><TABLE BORDER="0"><TR><TD VALIGN=TOP><SELECT NAME="po_num" SIZE=1>
	<OPTION VALUE=""<%= (order.po_num.equals("")?" SELECTED":"") %>>Click to Select
	<OPTION VALUE="Google search"<%= (order.po_num.equals("Google search")?" SELECTED":"") %>>Google search
	<OPTION VALUE="Google AdWords"<%= (order.po_num.equals("Google AdWords")?" SELECTED":"") %>>Google AdWords
	<OPTION VALUE="Yahoo search"<%= (order.po_num.equals("Yahoo search")?" SELECTED":"") %>>Yahoo search
	<OPTION VALUE="MSN search"<%= (order.po_num.equals("MSN search")?" SELECTED":"") %>>MSN search
	<OPTION VALUE="Facebook"<%= (order.po_num.equals("Facebook")?" SELECTED":"") %>>Facebook
	<OPTION VALUE="Twitter"<%= (order.po_num.equals("Twitter")?" SELECTED":"") %>>Twitter
	<OPTION VALUE="Email"<%= (order.po_num.equals("Email")?" SELECTED":"") %>>Email
	<OPTION VALUE="Partnering website advertisements"<%= (order.po_num.equals("Partnering website advertisements")?" SELECTED":"") %>>Partnering website advertisements
	<OPTION VALUE="Other"<%= (order.po_num.equals("Other")?" SELECTED":"") %>></SELECT>
</TD></TR>
</TABLE>
</TD></TR></TABLE>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<TR>
    <TD >
<TABLE border=0 width="100%" cellpadding=3 cellspacing=0  >

<tr>
  <td colspan=2 align=left>
<font size="-1" face="Geneva, Verdana, Helvetica"><B>Delivery Information</B>
<HR color="#000000">
</td>
</tr>
<tr>
  <td colspan=2 align=left>
<font size="-1" face="Geneva, Verdana, Helvetica"><INPUT TYPE=CHECKBOX NAME=addr_same VALUE=1>&nbsp;Check here to deliver to the billing address and skip this section.
</td>
</tr>
<TR>
<TD ALIGN=RIGHT >
<BR><font face="Geneva, Verdana, Helvetica" size="-1">Addressee's&nbsp;Name:
</TD>
<TD>
<BR><font face="Geneva, Verdana, Helvetica" size="-1"><INPUT TYPE=TEXT SIZE=50 NAME="shipName" VALUE="<%=order.getShippingContact().name%>">
</TD>
</TR>

<TR>
<TD ALIGN=RIGHT>
<font face="Geneva, Verdana, Helvetica" size="-1">Address&nbsp;Line&nbsp;1:
</TD><TD ><INPUT TYPE=TEXT size=50 NAME=ship_address1 VALUE="<%=order.getShippingAddress().address_one%>">
</TD></TR>
<TR>
<TD ALIGN=RIGHT>
<font face="Geneva, Verdana, Helvetica" size="-1">Address&nbsp;Line&nbsp;2:
</TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT SIZE=50 NAME=ship_address2  VALUE="<%=order.getShippingAddress().address_two%>">
</TD>
</TR>
<TR><TD ALIGN=RIGHT ><font face="Geneva, Verdana, Helvetica" size="-1">City: </TD><TD ALIGN=left><font face="Geneva, Verdana, Helvetica" size="-1"><INPUT TYPE=TEXT SIZE=26 NAME=ship_city  VALUE="<%=order.getShippingAddress().city%>"></TD></TR>
<TR>
<TD ALIGN=RIGHT VALIGN=TOP>
<font face="Geneva, Verdana, Helvetica" size="-1">State/Province:
</TD><TD ><TABLE BORDER="0"><TR><TD VALIGN=TOP><SELECT NAME="dom_ship_state" SIZE=1>
	<OPTION VALUE=""<%= (dom_ship_state.equals("")?" SELECTED":"") %>>Click to Select
	<OPTION VALUE="AL"<%= (dom_ship_state.equals("AL")?" SELECTED":"") %>>Alabama
	<OPTION VALUE="AK"<%= (dom_ship_state.equals("AK")?" SELECTED":"") %>>Alaska
	<OPTION VALUE="AS"<%= (dom_ship_state.equals("AS")?" SELECTED":"") %>>American Samoa
	<OPTION VALUE="AZ"<%= (dom_ship_state.equals("AZ")?" SELECTED":"") %>>Arizona
	<OPTION VALUE="AR"<%= (dom_ship_state.equals("AR")?" SELECTED":"") %>>Arkansas
	<OPTION VALUE="AA"<%= (dom_ship_state.equals("AA")?" SELECTED":"") %>>Armed Forces - USA/Canada
	<OPTION VALUE="AE"<%= (dom_ship_state.equals("AE")?" SELECTED":"") %>>Armed Forces - Europe
	<OPTION VALUE="AP"<%= (dom_ship_state.equals("AP")?" SELECTED":"") %>>Armed Forces - Pacific
	<OPTION VALUE="CA"<%= (dom_ship_state.equals("CA")?" SELECTED":"") %>>California
	<OPTION VALUE="CO"<%= (dom_ship_state.equals("CO")?" SELECTED":"") %>>Colorado
	<OPTION VALUE="CT"<%= (dom_ship_state.equals("CT")?" SELECTED":"") %>>Connecticut
	<OPTION VALUE="DE"<%= (dom_ship_state.equals("DE")?" SELECTED":"") %>>Delaware
	<OPTION VALUE="DC"<%= (dom_ship_state.equals("DC")?" SELECTED":"") %>>District of Columbia
	<OPTION VALUE="FM"<%= (dom_ship_state.equals("FM")?" SELECTED":"") %>>Federated States of Micronesia
	<OPTION VALUE="FL"<%= (dom_ship_state.equals("FL")?" SELECTED":"") %>>Florida
	<OPTION VALUE="GA"<%= (dom_ship_state.equals("GA")?" SELECTED":"") %>>Georgia
	<OPTION VALUE="GU"<%= (dom_ship_state.equals("GU")?" SELECTED":"") %>>Guam
	<OPTION VALUE="HI"<%= (dom_ship_state.equals("HI")?" SELECTED":"") %>>Hawaii
	<OPTION VALUE="ID"<%= (dom_ship_state.equals("ID")?" SELECTED":"") %>>Idaho
	<OPTION VALUE="IL"<%= (dom_ship_state.equals("IL")?" SELECTED":"") %>>Illinois
	<OPTION VALUE="IN"<%= (dom_ship_state.equals("IN")?" SELECTED":"") %>>Indiana
	<OPTION VALUE="IA"<%= (dom_ship_state.equals("IA")?" SELECTED":"") %>>Iowa
	<OPTION VALUE="KS"<%= (dom_ship_state.equals("KS")?" SELECTED":"") %>>Kansas
	<OPTION VALUE="KY"<%= (dom_ship_state.equals("KY")?" SELECTED":"") %>>Kentucky
	<OPTION VALUE="LA"<%= (dom_ship_state.equals("LA")?" SELECTED":"") %>>Louisiana
	<OPTION VALUE="ME"<%= (dom_ship_state.equals("ME")?" SELECTED":"") %>>Maine
	<OPTION VALUE="MH"<%= (dom_ship_state.equals("MH")?" SELECTED":"") %>>Marshall Islands
	<OPTION VALUE="MD"<%= (dom_ship_state.equals("MD")?" SELECTED":"") %>>Maryland
	<OPTION VALUE="MA"<%= (dom_ship_state.equals("MA")?" SELECTED":"") %>>Massachusetts
	<OPTION VALUE="MI"<%= (dom_ship_state.equals("MI")?" SELECTED":"") %>>Michigan
	<OPTION VALUE="MN"<%= (dom_ship_state.equals("MN")?" SELECTED":"") %>>Minnesota
	<OPTION VALUE="MS"<%= (dom_ship_state.equals("MS")?" SELECTED":"") %>>Mississippi
	<OPTION VALUE="MO"<%= (dom_ship_state.equals("MO")?" SELECTED":"") %>>Missouri
	<OPTION VALUE="MT"<%= (dom_ship_state.equals("MT")?" SELECTED":"") %>>Montana
	<OPTION VALUE="NE"<%= (dom_ship_state.equals("NE")?" SELECTED":"") %>>Nebraska
	<OPTION VALUE="NV"<%= (dom_ship_state.equals("NV")?" SELECTED":"") %>>Nevada
	<OPTION VALUE="NH"<%= (dom_ship_state.equals("NH")?" SELECTED":"") %>>New Hampshire
	<OPTION VALUE="NJ"<%= (dom_ship_state.equals("NJ")?" SELECTED":"") %>>New Jersey
	<OPTION VALUE="NM"<%= (dom_ship_state.equals("NM")?" SELECTED":"") %>>New Mexico
	<OPTION VALUE="NY"<%= (dom_ship_state.equals("NY")?" SELECTED":"") %>>New York
	<OPTION VALUE="NC"<%= (dom_ship_state.equals("NC")?" SELECTED":"") %>>North Carolina
	<OPTION VALUE="ND"<%= (dom_ship_state.equals("ND")?" SELECTED":"") %>>North Dakota
	<OPTION VALUE="MP"<%= (dom_ship_state.equals("MP")?" SELECTED":"") %>>Northern Mariana Island
	<OPTION VALUE="OH"<%= (dom_ship_state.equals("OH")?" SELECTED":"") %>>Ohio
	<OPTION VALUE="OK"<%= (dom_ship_state.equals("OK")?" SELECTED":"") %>>Oklahoma
	<OPTION VALUE="OR"<%= (dom_ship_state.equals("OR")?" SELECTED":"") %>>Oregon
	<OPTION VALUE="PW"<%= (dom_ship_state.equals("PW")?" SELECTED":"") %>>Palau Island
	<OPTION VALUE="PA"<%= (dom_ship_state.equals("PA")?" SELECTED":"") %>>Pennsylvania
	<OPTION VALUE="PR"<%= (dom_ship_state.equals("PR")?" SELECTED":"") %>>Puerto Rico
	<OPTION VALUE="RI"<%= (dom_ship_state.equals("RI")?" SELECTED":"") %>>Rhode Island
	<OPTION VALUE="SC"<%= (dom_ship_state.equals("SC")?" SELECTED":"") %>>South Carolina
	<OPTION VALUE="SD"<%= (dom_ship_state.equals("SD")?" SELECTED":"") %>>South Dakota
	<OPTION VALUE="TN"<%= (dom_ship_state.equals("TN")?" SELECTED":"") %>>Tennessee
	<OPTION VALUE="TX"<%= (dom_ship_state.equals("TX")?" SELECTED":"") %>>Texas
	<OPTION VALUE="UT"<%= (dom_ship_state.equals("UT")?" SELECTED":"") %>>Utah
	<OPTION VALUE="VT"<%= (dom_ship_state.equals("VT")?" SELECTED":"") %>>Vermont
	<OPTION VALUE="VI"<%= (dom_ship_state.equals("VI")?" SELECTED":"") %>>Virgin Islands
	<OPTION VALUE="VA"<%= (dom_ship_state.equals("VA")?" SELECTED":"") %>>Virginia
	<OPTION VALUE="WA"<%= (dom_ship_state.equals("WA")?" SELECTED":"") %>>Washington
	<OPTION VALUE="WV"<%= (dom_ship_state.equals("WV")?" SELECTED":"") %>>West Virginia
	<OPTION VALUE="WI"<%= (dom_ship_state.equals("WI")?" SELECTED":"") %>>Wisconsin
	<OPTION VALUE="WY"<%= (dom_ship_state.equals("WY")?" SELECTED":"") %>>Wyoming</SELECT></TD></TR> </TABLE>
    </TD></TR>

<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Zip/Postal&nbsp;Code: </TD><TD align="left"><INPUT TYPE=TEXT SIZE=13 NAME=ship_zip  VALUE="<%=order.getShippingAddress().zip%>"></TD></TR>
<TR>
<TD ALIGN=RIGHT>
<font face="Geneva, Verdana, Helvetica" size="-1">Country:
</TD><TD >USA<INPUT type="hidden" NAME="ship_country" value="USA">
</TD></TR>

<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Email&nbsp;Address:</TD><TD><INPUT TYPE=TEXT SIZE=31 NAME=ship_email  VALUE="<%=order.getShippingContact().email%>" ></TD></TR>


<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Phone:</TD><TD><INPUT TYPE=TEXT SIZE=31 NAME=ship_phone_num  VALUE="<%=order.getShippingContact().phone%>" ></TD></TR>

<TR>
<TD ALIGN=RIGHT COLSPAN=2><BR>
</TD></TR>
<tr>
  <td colspan=2 align=left>
<HR color="#000000">
</td>
</tr>
<tr>
  <td colspan=2  align=right><font face="Geneva, Verdana, Helvetica" size="-1">Please check your information above, then click the button to continue :
  <INPUT TYPE=HIDDEN NAME=cd VALUE=447><INPUT TYPE=HIDDEN NAME=do VALUE=5><P><INPUT TYPE=SUBMIT NAME="Complete" VALUE="Complete Order ->">

</td>
</tr>
<tr>
  <td colspan=2 align=left>
<BR>
</td>
</tr>
</FORM>
</TABLE>
</TD></TR></TABLE>
</div>
<!-- end cart display -->
<jsp:include page="bitefoot.jsp"/>