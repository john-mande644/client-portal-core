<%@ page import="
com.owd.core.OWDUtilities,
com.owd.core.business.Address,
com.owd.core.business.Contact,
com.owd.core.business.order.Order,
com.owd.web.callcenter.servlet.PhionServlet,
java.util.Hashtable,
java.util.Iterator,
java.util.List,
java.util.Vector"
%>

<%
	Order order = (Order) request.getSession(true).getAttribute("currentorder");
	List paymentTypeList = (List) request.getSession(true).getAttribute("paymentTypeList");
	if(order==null)
		order = new Order("179");
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


	Vector errors = (Vector) request.getAttribute("errors");
	if (errors == null)
		errors = new Vector();

    List itemList = PhionServlet.getCurrentItemList(request,true);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML> <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css"><HEAD><TITLE>PhIon Wholesale Order Form</TITLE>
<BODY bgColor=#ffffff><P><font color=red><B><%= (request.getAttribute("errormessage")!=null?request.getAttribute("errormessage"):"") %></B></font>
<P>
<TABLE border=0 width="100%">
  <TBODY>
  <TR>
    <TD align=left vAlign=top>
      <CENTER>
      <H2><FONT face="Geneva, Verdana, Helvetica">PhIon</FONT><FONT
      face="Geneva, Verdana, Helvetica"> Order Form</H2></CENTER>
      <P></P></FONT></TD>
    <TD align=right vAlign=top></TD></TR></TBODY></TABLE>
 <A HREF="index.jsp" ><B>Return to Customer List</B></A>
<%
	if(errors.size()>0){
		for(int i=0; i<errors.size();i++)
		{
%>
			<p><FONT face="Geneva, Verdana, Helvetica" color="red"><b>
			<%=(String) errors.get(i)%>
			</b></FONT></p>
		<%
		}
	}
            //10010 43.20






		%>
<FORM action="/webapps/callcenter/phion/phion.jsp" method=post>
<INPUT TYPE=HIDDEN name=action value="submitorder">

<TABLE bgColor=#ffccff border=0 cellPadding=3 cellSpacing=0 width="100%">
  <TBODY>
  <TR>
    <TD align=left vAlign=center><FONT
      face="Geneva, Verdana, Helvetica"><B>&nbsp;1. What products do you need today?</B></FONT></TD></TR>
  <TR>
    <TD>
      <TABLE bgColor=#ffffff border=0 cellPadding=3 cellSpacing=0
        width="100%"><TBODY>
        <TR>
          <TD align=middle><FONT face="Geneva, Verdana, Helvetica"
            size=-1><B>Quantity</B></FONT></TD>
          <TD align=left><FONT face="Geneva, Verdana, Helvetica"
            size=-1><B>&nbsp;&nbsp;Price&nbsp;(per&nbsp;case)</B></FONT></TD>
          <TD align=left NOWRAP><FONT face="Geneva, Verdana, Helvetica"
            size=-1><B>&nbsp;&nbsp;Description</B></FONT></TD>
          </TR>

         <%
        			 Iterator itl = itemList.iterator();
        			 java.text.DecimalFormat decform = new java.text.DecimalFormat("$#,###,##0.00");

        			 while(itl.hasNext())
        			 {

        				 Hashtable item = (Hashtable)itl.next();
                         if("wholesale".equalsIgnoreCase(((String)item.get("keyword")).trim()))
                         {
        				 %>
        				 <TR><TD ALIGN=left width="1%"><INPUT TYPE=TEXT size=6 value="<%= order.getQuantityForSKU((String)item.get("inventory_num")) %>" NAME="quant_<%=item.get("inventory_num")%>"></TD>
                         <TD width="1%" align=left><INPUT type=text size=8 name="price_<%=item.get("inventory_num")%>" value="<%= OWDUtilities.roundFloat(new Float((String)item.get("price")).floatValue())%>"></TD>
                         <TD width="97%" align=left><B><%= "("+item.get("inventory_num")+") "+item.get("description")%></B></TD>

        				 </TR>
                       <% }} %>




        </TBODY></TABLE></TABLE>
<B>
Orders of $95 or more get FREE shipping<BR>
$250 or more get 5% Discount and FREE Shipping<BR>
$500 or more get 10% Discount and FREE Shipping<BR>
Orders over 1000.00 please refer to Phion main office for volume sales.<BR>
Discounts applied on next page before final confirmation of order</B><P><BR>

<TABLE bgColor=#ccffcc border=0 cellPadding=3 cellSpacing=0 width="100%">
  <TBODY>
  <TR>
    <TD align=left vAlign=center><FONT
      face="Geneva, Verdana, Helvetica"><B>&nbsp;2. Verify Payment
      Information, determine payment type</B></FONT></TD></TR>
  <TR>
    <TD>
      <TABLE bgColor=#ffffff border=0 cellPadding=3 cellSpacing=0
        width="100%"><TBODY>
        <TR>
          <TD align=right><BR><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Purchaser's&nbsp;Name: </FONT></TD>
          <TD><BR><FONT face="Geneva, Verdana, Helvetica" size=-1><INPUT
            name=bill_billingName size=68 value="<%=b_contact.name.trim()%>"> </FONT></TD></TR>
        <TR>
          <TD>&nbsp; </TD>
          <TD><FONT face="Geneva, Verdana, Helvetica" size=-2>Please type the
            full name as it appears on the card for CC purchases, or the name of the responsible party for other forms of payment </FONT></TD></TR>
        <TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Company&nbsp;Name: </FONT></TD>
          <TD><INPUT name=bill_companyName size=68 value="<%=b_addr.company_name%>"> </TD></TR>
        <TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Address&nbsp;Line&nbsp;1: </FONT></TD>
          <TD><INPUT name=bill_address1 size=68 value="<%=b_addr.address_one%>"> </TD></TR>
        <TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Address&nbsp;Line&nbsp;2: </FONT></TD>
          <TD align=left><INPUT name=bill_address2 size=68 value="<%=b_addr.address_two%>"> </TD></TR>
        <TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>City: </FONT></TD>
          <TD align=left><FONT face="Geneva, Verdana, Helvetica"
            size=-1><INPUT name=bill_city size=26 value="<%=b_addr.city%>"> &nbsp;State: <SELECT
            name=bill_state>
            	<OPTION VALUE=""<%= (b_addr.state.equals("")?" SELECTED":"") %>>Click to Select
	<OPTION VALUE="AL"<%= (b_addr.state.equals("AL")?" SELECTED":"") %>>Alabama
	<OPTION VALUE="AK"<%= (b_addr.state.equals("AK")?" SELECTED":"") %>>Alaska
	<OPTION VALUE="AZ"<%= (b_addr.state.equals("AZ")?" SELECTED":"") %>>Arizona
	<OPTION VALUE="AR"<%= (b_addr.state.equals("AR")?" SELECTED":"") %>>Arkansas
	<OPTION VALUE="CA"<%= (b_addr.state.equals("CA")?" SELECTED":"") %>>California
	<OPTION VALUE="CO"<%= (b_addr.state.equals("CO")?" SELECTED":"") %>>Colorado
	<OPTION VALUE="CT"<%= (b_addr.state.equals("CT")?" SELECTED":"") %>>Connecticut
	<OPTION VALUE="DE"<%= (b_addr.state.equals("DE")?" SELECTED":"") %>>Delaware
	<OPTION VALUE="DC"<%= (b_addr.state.equals("DC")?" SELECTED":"") %>>District of Columbia
	<OPTION VALUE="FL"<%= (b_addr.state.equals("FL")?" SELECTED":"") %>>Florida
	<OPTION VALUE="GA"<%= (b_addr.state.equals("GA")?" SELECTED":"") %>>Georgia
	<OPTION VALUE="HI"<%= (b_addr.state.equals("HI")?" SELECTED":"") %>>Hawaii
	<OPTION VALUE="ID"<%= (b_addr.state.equals("ID")?" SELECTED":"") %>>Idaho
	<OPTION VALUE="IL"<%= (b_addr.state.equals("IL")?" SELECTED":"") %>>Illinois
	<OPTION VALUE="IN"<%= (b_addr.state.equals("IN")?" SELECTED":"") %>>Indiana
	<OPTION VALUE="IA"<%= (b_addr.state.equals("IA")?" SELECTED":"") %>>Iowa
	<OPTION VALUE="KS"<%= (b_addr.state.equals("KS")?" SELECTED":"") %>>Kansas
	<OPTION VALUE="KY"<%= (b_addr.state.equals("KY")?" SELECTED":"") %>>Kentucky
	<OPTION VALUE="LA"<%= (b_addr.state.equals("LA")?" SELECTED":"") %>>Louisiana
	<OPTION VALUE="ME"<%= (b_addr.state.equals("ME")?" SELECTED":"") %>>Maine
	<OPTION VALUE="MD"<%= (b_addr.state.equals("MD")?" SELECTED":"") %>>Maryland
	<OPTION VALUE="MA"<%= (b_addr.state.equals("MA")?" SELECTED":"") %>>Massachusetts
	<OPTION VALUE="MI"<%= (b_addr.state.equals("MI")?" SELECTED":"") %>>Michigan
	<OPTION VALUE="MN"<%= (b_addr.state.equals("MN")?" SELECTED":"") %>>Minnesota
	<OPTION VALUE="MS"<%= (b_addr.state.equals("MS")?" SELECTED":"") %>>Mississippi
	<OPTION VALUE="MO"<%= (b_addr.state.equals("MO")?" SELECTED":"") %>>Missouri
	<OPTION VALUE="MT"<%= (b_addr.state.equals("MT")?" SELECTED":"") %>>Montana
	<OPTION VALUE="NE"<%= (b_addr.state.equals("NE")?" SELECTED":"") %>>Nebraska
	<OPTION VALUE="NV"<%= (b_addr.state.equals("NV")?" SELECTED":"") %>>Nevada
	<OPTION VALUE="NH"<%= (b_addr.state.equals("NH")?" SELECTED":"") %>>New Hampshire
	<OPTION VALUE="NJ"<%= (b_addr.state.equals("NJ")?" SELECTED":"") %>>New Jersey
	<OPTION VALUE="NM"<%= (b_addr.state.equals("NM")?" SELECTED":"") %>>New Mexico
	<OPTION VALUE="NY"<%= (b_addr.state.equals("NY")?" SELECTED":"") %>>New York
	<OPTION VALUE="NC"<%= (b_addr.state.equals("NC")?" SELECTED":"") %>>North Carolina
	<OPTION VALUE="ND"<%= (b_addr.state.equals("ND")?" SELECTED":"") %>>North Dakota
	<OPTION VALUE="OH"<%= (b_addr.state.equals("OH")?" SELECTED":"") %>>Ohio
	<OPTION VALUE="OK"<%= (b_addr.state.equals("OK")?" SELECTED":"") %>>Oklahoma
	<OPTION VALUE="OR"<%= (b_addr.state.equals("OR")?" SELECTED":"") %>>Oregon
	<OPTION VALUE="PA"<%= (b_addr.state.equals("PA")?" SELECTED":"") %>>Pennsylvania
	<OPTION VALUE="RI"<%= (b_addr.state.equals("RI")?" SELECTED":"") %>>Rhode Island
	<OPTION VALUE="SC"<%= (b_addr.state.equals("SC")?" SELECTED":"") %>>South Carolina
	<OPTION VALUE="SD"<%= (b_addr.state.equals("SD")?" SELECTED":"") %>>South Dakota
	<OPTION VALUE="TN"<%= (b_addr.state.equals("TN")?" SELECTED":"") %>>Tennessee
	<OPTION VALUE="TX"<%= (b_addr.state.equals("TX")?" SELECTED":"") %>>Texas
	<OPTION VALUE="UT"<%= (b_addr.state.equals("UT")?" SELECTED":"") %>>Utah
	<OPTION VALUE="VT"<%= (b_addr.state.equals("VT")?" SELECTED":"") %>>Vermont
	<OPTION VALUE="VA"<%= (b_addr.state.equals("VA")?" SELECTED":"") %>>Virginia
	<OPTION VALUE="WA"<%= (b_addr.state.equals("WA")?" SELECTED":"") %>>Washington
	<OPTION VALUE="WV"<%= (b_addr.state.equals("WV")?" SELECTED":"") %>>West Virginia
	<OPTION VALUE="WI"<%= (b_addr.state.equals("WI")?" SELECTED":"") %>>Wisconsin
	<OPTION VALUE="WY"<%= (b_addr.state.equals("WY")?" SELECTED":"") %>>Wyoming</SELECT> &nbsp;Zip: <INPUT
            name=bill_zip value="<%=b_addr.zip%>" type="text" size=11></FONT></TD></TR>
        <TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Email Address:</FONT></TD>
          <TD><INPUT name=bill_email size=31 value="<%=b_contact.email%>"><FONT
            face="Geneva, Verdana, Helvetica" size=-1>&nbsp;&nbsp;Phone: <INPUT
            name=bill_phone_num size=21 value="<%=b_contact.phone%>"></FONT></TD></TR>


<TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Payment Type:</FONT></TD>
          <TD><SELECT
            name=bill_cc_type>
    <% if (paymentTypeList.contains("Purchase Order"))
	{
    %>
	<OPTION VALUE="PO" <%= (order.bill_cc_type.equals("PO")?" SELECTED":"") %>>Purchase Order
	<%
	} %>
	<OPTION VALUE="CC"<%= (order.bill_cc_type.equals("CC")?" SELECTED":"") %>>Credit Card
	<OPTION VALUE="PP"<%= (order.bill_cc_type.equals("PP")?" SELECTED":"") %>>Prepaid
	<OPTION VALUE="30"<%= (order.bill_cc_type.equals("30")?" SELECTED":"") %>>30 Day Terms
	</SELECT></TD></TR>


<TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>PO Number (optional):</FONT></TD>
          <TD><INPUT TYPE=TEXT NAME=po_num value="<%= order.po_num %>"></TD></TR>

        <TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Credit Card Number:</FONT></TD>
          <TD><INPUT name=cc_num WIDTH="40" value="<%=order.cc_num%>"><FONT
            face="Geneva, Verdana, Helvetica" size=-1>&nbsp;&nbsp;Expires:
            <SELECT name=cc_exp_mon> <OPTION selected value=01>January<OPTION
              value=02>February<OPTION value=03>March<OPTION
              value=04>April<OPTION value=05>May<OPTION value=06>June<OPTION
              value=07>July<OPTION value=08>August<OPTION
              value=09>September<OPTION value=10>October<OPTION
              value=11>November<OPTION value=12>December</OPTION></SELECT><SELECT
            name=cc_exp_year><OPTION selected
value=2013>2013</OPTION><OPTION
value=2014>2014</OPTION><OPTION
value=2015>2015</OPTION><OPTION
value=2016>2016</OPTION><OPTION
value=2017>2017</OPTION><OPTION
value=2018>2018</OPTION><OPTION
value=2019>2019</OPTION><OPTION
value=2020>2020</OPTION><OPTION
value=2021>2021</OPTION></SELECT></FONT>(for Credit Card orders only)</TD></TR>



        <TR>
          <TD>&nbsp;</TD>
          <TD><FONT face="Geneva, Verdana, Helvetica" size=-2>We accept VISA,
            MasterCard, and American Express credit
        cards</FONT></TD></TR>
<TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Sales Agent Code:</FONT></TD>
          <TD><INPUT TYPE=TEXT NAME=ship_operator value="<%= order.ship_operator %>">(Only use if offered)</TD></TR>
</TBODY></TABLE></TD></TR>


		</TBODY></TABLE>
<P>
<TABLE bgColor=#ccffcc border=0 cellPadding=3 cellSpacing=0 width="100%">
  <TBODY>
  <TR>
    <TD align=left vAlign=center><FONT
      face="Geneva, Verdana, Helvetica"><B>&nbsp;3. Add Shipping
      Information</B></FONT></TD></TR>
  <TR>
    <TD>
      <TABLE bgColor=#ffffff border=0 cellPadding=3 cellSpacing=0
        width="100%"><TBODY>


        <TR>
          <TD>&nbsp; </TD>
          <TD><FONT face="Geneva, Verdana, Helvetica" size=-2>Confirm the delivery name and address below
            before continuing.<BR>Shipping Method and cost will be displayed on the next page.</FONT></TD></TR>
        <TR>
          <TD align=right><BR><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Override&nbsp;Shipping: </FONT></TD>
          <TD><BR><FONT face="Geneva, Verdana, Helvetica" size=-1><INPUT
            name=bill_to_shipping type=checkbox value="1">Use Billing Information for Shipping</FONT></TD></TR>

		<TR>
          <TD align=right><BR><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Delivery&nbsp;Name: </FONT></TD>
          <TD><BR><FONT face="Geneva, Verdana, Helvetica" size=-1><INPUT
            name=ship_shippingName size=68 value="<%=s_contact.name.trim()%>"> </FONT></TD></TR>
        <TR>
          <TD>&nbsp; </TD>
          <TD><FONT face="Geneva, Verdana, Helvetica" size=-2>Please type the
            person's name that the order should be shipped to </FONT></TD></TR>
        <TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Company&nbsp;Name: </FONT></TD>
          <TD><INPUT name=ship_companyName size=68 value="<%=s_addr.company_name%>"> </TD></TR>
        <TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Address&nbsp;Line&nbsp;1: </FONT></TD>
          <TD><INPUT name=ship_address1 size=68 value="<%=s_addr.address_one%>"> </TD></TR>
        <TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>Address&nbsp;Line&nbsp;2: </FONT></TD>
          <TD align=left><INPUT name=ship_address2 size=68 value="<%=s_addr.address_two%>"> </TD></TR>
        <TR>
          <TD align=right><FONT face="Geneva, Verdana, Helvetica"
            size=-1>City: </FONT></TD>
          <TD align=left><FONT face="Geneva, Verdana, Helvetica"
            size=-1><INPUT name=ship_city size=26 value="<%=s_addr.city%>"> &nbsp;State: <SELECT
            name=ship_state>
		<OPTION selected value="<%=s_addr.state%>"><%=s_addr.state%>&nbsp;
<OPTION VALUE=""<%= (s_addr.state.equals("")?" SELECTED":"") %>>Click to Select
	<OPTION VALUE="AL"<%= (s_addr.state.equals("AL")?" SELECTED":"") %>>Alabama
	<OPTION VALUE="AK"<%= (s_addr.state.equals("AK")?" SELECTED":"") %>>Alaska
	<OPTION VALUE="AZ"<%= (s_addr.state.equals("AZ")?" SELECTED":"") %>>Arizona
	<OPTION VALUE="AR"<%= (s_addr.state.equals("AR")?" SELECTED":"") %>>Arkansas
	<OPTION VALUE="CA"<%= (s_addr.state.equals("CA")?" SELECTED":"") %>>California
	<OPTION VALUE="CO"<%= (s_addr.state.equals("CO")?" SELECTED":"") %>>Colorado
	<OPTION VALUE="CT"<%= (s_addr.state.equals("CT")?" SELECTED":"") %>>Connecticut
	<OPTION VALUE="DE"<%= (s_addr.state.equals("DE")?" SELECTED":"") %>>Delaware
	<OPTION VALUE="DC"<%= (s_addr.state.equals("DC")?" SELECTED":"") %>>District of Columbia
	<OPTION VALUE="FL"<%= (s_addr.state.equals("FL")?" SELECTED":"") %>>Florida
	<OPTION VALUE="GA"<%= (s_addr.state.equals("GA")?" SELECTED":"") %>>Georgia
	<OPTION VALUE="HI"<%= (s_addr.state.equals("HI")?" SELECTED":"") %>>Hawaii
	<OPTION VALUE="ID"<%= (s_addr.state.equals("ID")?" SELECTED":"") %>>Idaho
	<OPTION VALUE="IL"<%= (s_addr.state.equals("IL")?" SELECTED":"") %>>Illinois
	<OPTION VALUE="IN"<%= (s_addr.state.equals("IN")?" SELECTED":"") %>>Indiana
	<OPTION VALUE="IA"<%= (s_addr.state.equals("IA")?" SELECTED":"") %>>Iowa
	<OPTION VALUE="KS"<%= (s_addr.state.equals("KS")?" SELECTED":"") %>>Kansas
	<OPTION VALUE="KY"<%= (s_addr.state.equals("KY")?" SELECTED":"") %>>Kentucky
	<OPTION VALUE="LA"<%= (s_addr.state.equals("LA")?" SELECTED":"") %>>Louisiana
	<OPTION VALUE="ME"<%= (s_addr.state.equals("ME")?" SELECTED":"") %>>Maine
	<OPTION VALUE="MD"<%= (s_addr.state.equals("MD")?" SELECTED":"") %>>Maryland
	<OPTION VALUE="MA"<%= (s_addr.state.equals("MA")?" SELECTED":"") %>>Massachusetts
	<OPTION VALUE="MI"<%= (s_addr.state.equals("MI")?" SELECTED":"") %>>Michigan
	<OPTION VALUE="MN"<%= (s_addr.state.equals("MN")?" SELECTED":"") %>>Minnesota
	<OPTION VALUE="MS"<%= (s_addr.state.equals("MS")?" SELECTED":"") %>>Mississippi
	<OPTION VALUE="MO"<%= (s_addr.state.equals("MO")?" SELECTED":"") %>>Missouri
	<OPTION VALUE="MT"<%= (s_addr.state.equals("MT")?" SELECTED":"") %>>Montana
	<OPTION VALUE="NE"<%= (s_addr.state.equals("NE")?" SELECTED":"") %>>Nebraska
	<OPTION VALUE="NV"<%= (s_addr.state.equals("NV")?" SELECTED":"") %>>Nevada
	<OPTION VALUE="NH"<%= (s_addr.state.equals("NH")?" SELECTED":"") %>>New Hampshire
	<OPTION VALUE="NJ"<%= (s_addr.state.equals("NJ")?" SELECTED":"") %>>New Jersey
	<OPTION VALUE="NM"<%= (s_addr.state.equals("NM")?" SELECTED":"") %>>New Mexico
	<OPTION VALUE="NY"<%= (s_addr.state.equals("NY")?" SELECTED":"") %>>New York
	<OPTION VALUE="NC"<%= (s_addr.state.equals("NC")?" SELECTED":"") %>>North Carolina
	<OPTION VALUE="ND"<%= (s_addr.state.equals("ND")?" SELECTED":"") %>>North Dakota
	<OPTION VALUE="OH"<%= (s_addr.state.equals("OH")?" SELECTED":"") %>>Ohio
	<OPTION VALUE="OK"<%= (s_addr.state.equals("OK")?" SELECTED":"") %>>Oklahoma
	<OPTION VALUE="OR"<%= (s_addr.state.equals("OR")?" SELECTED":"") %>>Oregon
	<OPTION VALUE="PA"<%= (s_addr.state.equals("PA")?" SELECTED":"") %>>Pennsylvania
	<OPTION VALUE="RI"<%= (s_addr.state.equals("RI")?" SELECTED":"") %>>Rhode Island
	<OPTION VALUE="SC"<%= (s_addr.state.equals("SC")?" SELECTED":"") %>>South Carolina
	<OPTION VALUE="SD"<%= (s_addr.state.equals("SD")?" SELECTED":"") %>>South Dakota
	<OPTION VALUE="TN"<%= (s_addr.state.equals("TN")?" SELECTED":"") %>>Tennessee
	<OPTION VALUE="TX"<%= (s_addr.state.equals("TX")?" SELECTED":"") %>>Texas
	<OPTION VALUE="UT"<%= (s_addr.state.equals("UT")?" SELECTED":"") %>>Utah
	<OPTION VALUE="VT"<%= (s_addr.state.equals("VT")?" SELECTED":"") %>>Vermont
	<OPTION VALUE="VA"<%= (s_addr.state.equals("VA")?" SELECTED":"") %>>Virginia
	<OPTION VALUE="WA"<%= (s_addr.state.equals("WA")?" SELECTED":"") %>>Washington
	<OPTION VALUE="WV"<%= (s_addr.state.equals("WV")?" SELECTED":"") %>>West Virginia
	<OPTION VALUE="WI"<%= (s_addr.state.equals("WI")?" SELECTED":"") %>>Wisconsin
	<OPTION VALUE="WY"<%= (s_addr.state.equals("WY")?" SELECTED":"") %>>Wyoming</SELECT> &nbsp;Zip: <INPUT
            name=ship_zip value="<%=s_addr.zip%>" type="text"
size=11></FONT></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<P>&nbsp;&nbsp;Click on the Review Order button to calculate the total shipping and order cost.
<P>&nbsp;&nbsp;&nbsp;&nbsp;<INPUT name=actionType type=submit value="Review Order">
</FORM>

<HR></BODY></HTML>
