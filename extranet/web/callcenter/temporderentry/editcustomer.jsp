<html>
<%@ page import="com.owd.core.business.Address,
				 com.owd.core.business.Client,
				 com.owd.core.business.Contact,
				 com.owd.core.business.client.CustomField,
				 com.owd.core.business.order.Order,
				 com.owd.core.business.order.OrderUtilities,
				 java.text.DecimalFormat,
				 java.text.NumberFormat,
				 java.util.Iterator,
				 java.util.List,
                 java.util.Locale,
				 java.util.Map"
%>
<%
	String[] payTypes = {"CC","CK", "FREE"};
	String errorMessage = (String) session.getAttribute("error");


	String[] payMons = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	String[] payMonNames = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

	String[] payYears = {"2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011"};

	String shipError = "";


	String client_id = (String) request.getSession(true).getAttribute("client_id");
	if(client_id == null || "".equals(client_id) || "0".equals(client_id))
	{
		try
		{
			request.getRequestDispatcher("orderentry.jsp").forward(request, response);
		}
		catch(Exception e)
		{

		}
	}
	Client client = (Client) request.getSession(true).getAttribute("client");
    List customFields = (List) request.getSession(true).getAttribute("customFields");

	Order currorder = (Order) request.getSession(true).getAttribute("currorder");

	Address billingAddress = currorder.getBillingAddress();
	Address shippingAddress = currorder.getShippingAddress();
	Contact	billContact = currorder.getBillingContact();
	Contact	shipContact = currorder.getShippingContact();

	if("update".equals((String)request.getParameter("editactionitems")))
	{
        currorder.order_refnum = ts((String)request.getParameter("order_refnum"));
        currorder.po_num = ts((String)request.getParameter("po_num"));
        currorder.coupon_code = ts((String)request.getParameter("coupon_code"));

        client.getClientPolicy().updateCustomOrderFields(currorder,request,customFields);

		if(ts((String)request.getParameter("is_gift")).equals("1"))
	{
		currorder.is_gift = "1";
		currorder.gift_message = (String)request.getParameter("gift_message");
	}else
	{
		currorder.is_gift = "0";
		currorder.gift_message = "";
	}


		billContact = new Contact(ts((String)request.getParameter("bill_name")),
						ts((String)request.getParameter("bill_phone")),
						"",
						ts((String)request.getParameter("bill_email")),
						""
						);
		currorder.setBillingContact(billContact);
	if(ts((String)request.getParameter("shipsame")).equals("1"))
	{
		shipContact = billContact;
	}else{
		shipContact = new Contact(ts((String)request.getParameter("ship_name")),
						ts((String)request.getParameter("ship_phone")),
						"",
						ts((String)request.getParameter("ship_email")),
						""
						);
	}
		currorder.getShippingInfo().setShippingContact(shipContact);

		String temp_bill_state = ts((String)request.getParameter("bill_state"));
		String done_bill_state = temp_bill_state;

		if(temp_bill_state.equals(""))
		{
			done_bill_state = ts((String)request.getParameter("bill_state_intl"));
		}

		String temp_ship_state = ts((String)request.getParameter("ship_state"));
		String done_ship_state = temp_ship_state;

		if(temp_ship_state.equals(""))
		{
			done_ship_state = ts((String)request.getParameter("ship_state_intl"));
		}



		billingAddress = new Address(ts((String)request.getParameter("bill_company")),
						ts((String)request.getParameter("bill_address1")),
						ts((String)request.getParameter("bill_address2")),
						ts((String)request.getParameter("bill_city")),
						done_bill_state,
						ts((String)request.getParameter("bill_zip")),
						ts((String)request.getParameter("bill_country"))
						);
		currorder.setBillingAddress(billingAddress);
	if(ts((String)request.getParameter("shipsame")).equals("1"))
	{
		shippingAddress = billingAddress;
	}else{
		shippingAddress = new Address(	ts((String)request.getParameter("ship_company")),
						ts((String)request.getParameter("ship_address1")),
						ts((String)request.getParameter("ship_address2")),
						ts((String)request.getParameter("ship_city")),
						done_ship_state,
						ts((String)request.getParameter("ship_zip")),
						ts((String)request.getParameter("ship_country"))
						);
	}
		if(shippingAddress.address_one.length()<1)
			shippingAddress = billingAddress;

		currorder.getShippingInfo().setShippingAddress(shippingAddress);

		currorder.bill_cc_type = ts((String)request.getParameter("payment_type"));

			System.out.println("editcustomer.jsp - Payment type: "+ currorder.bill_cc_type);

		if (currorder.bill_cc_type.equals("CK"))
		{
			currorder.cc_num = "";
			currorder.cc_exp_mon=1;
			currorder.cc_exp_year=2000;
		}else if(currorder.bill_cc_type.equals("CC") || currorder.bill_cc_type.equals("FLEX")){
			//CC
			currorder.cc_num = ts((String)request.getParameter("cc_num"));
			try{
				currorder.cc_exp_mon=new Integer(ts((String)request.getParameter("cc_exp_mon"))).intValue();
			}catch(Exception ex){currorder.cc_exp_mon=1;}
			try{
				currorder.cc_exp_year=new Integer(ts((String)request.getParameter("cc_exp_year"))).intValue();
			}catch(Exception ex){currorder.cc_exp_year=2000;}
		}
		else
		{
			/*
			if("146".equals(client_id))
			{
				if(currorder.hasItemID("26845")||currorder.hasItemID("26846")||currorder.hasItemID("30821")||currorder.hasItemID("30822"))
				{
						currorder.bill_cc_type="CC";
				}


			}
			*/

		}

String service = (String) ts((String)request.getParameter("ship_type"));
if(service.length()>0)
currorder.getShippingInfo().setShipOptions(ts((String)request.getParameter("ship_type")), "Prepaid", "");


         client.getClientPolicy().handleCustomOrderFields(currorder,customFields);

		try
		{
			session.setAttribute("currorder", currorder);
			if(currorder.bill_cc_type.equalsIgnoreCase("FREE"))
			{
				session.setAttribute("warning", "WARNING: This order has been marked as FREE - no charge will be made to the customer. If this is not correct, return to the customer information page and choose the proper form of payment.");
			}
			request.getRequestDispatcher("orderentry.jsp").forward(request, response);
		}
		catch(Exception e)
		{

		}
	}





	DecimalFormat dform = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);

%>
<head>
<title>Order Entry - Editing Customer Information</title>
 <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
</head>
<body bgcolor=#FFFFFF>
<CENTER><fontx face="Geneva, Verdana, Helvetica" size=-1><B>Order Entry - Editing Customer Information</B></font>
<font size=-2 color=red>
	<%=errorMessage!=null?"<p>"+errorMessage:""%>
</CENTER>
<FORM METHOD=POST ACTION=editcustomer.jsp?editactionitems=update>
<TABLE border=0 cellpadding=0 cellspacing=0 width=100%>
<TR><TD align=center colspan=2><font  size=2><HR><B>Billing Information<B></FONT><HR></TD></TR>
<TR>
<TD ALIGN=RIGHT valign=top width=50%><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Full name as it appears on credit card or check</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top width=50%>
<INPUT TYPE=TEXT NAME=bill_name size=40 VALUE="<%= billContact.name %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Company name (optional)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_company size=40 VALUE='<%=".".equals(billingAddress.company_name)?"":billingAddress.company_name%>'></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Billing Address Line 1</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_address1 size=40 VALUE="<%= billingAddress.address_one %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Billing Address Line 2</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_address2 size=40 VALUE="<%= billingAddress.address_two %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>City</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_city size=40 VALUE="<%= billingAddress.city %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>State/Region/Province</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top><BR>
<SELECT NAME="bill_state" SIZE=1>
	<OPTION VALUE=""<%= (billingAddress.state.equals("")?" SELECTED":"") %>>Click to select or use box below
	<OPTION VALUE="AL"<%= (billingAddress.state.equals("AL")?" SELECTED":"") %>>Alabama
	<OPTION VALUE="AK"<%= (billingAddress.state.equals("AK")?" SELECTED":"") %>>Alaska
	<OPTION VALUE="AB"<%= (billingAddress.state.equals("AB")?" SELECTED":"") %>>Alberta
	<OPTION VALUE="AS"<%= (billingAddress.state.equals("AS")?" SELECTED":"") %>>American Samoa
	<OPTION VALUE="AZ"<%= (billingAddress.state.equals("AZ")?" SELECTED":"") %>>Arizona
	<OPTION VALUE="AR"<%= (billingAddress.state.equals("AR")?" SELECTED":"") %>>Arkansas
	<OPTION VALUE="AA"<%= (billingAddress.state.equals("AA")?" SELECTED":"") %>>Armed Forces - USA/Canada
	<OPTION VALUE="AE"<%= (billingAddress.state.equals("AE")?" SELECTED":"") %>>Armed Forces - Europe
	<OPTION VALUE="AP"<%= (billingAddress.state.equals("AP")?" SELECTED":"") %>>Armed Forces - Pacific
	<OPTION VALUE="BC"<%= (billingAddress.state.equals("BC")?" SELECTED":"") %>>British Columbia
	<OPTION VALUE="CA"<%= (billingAddress.state.equals("CA")?" SELECTED":"") %>>California
	<OPTION VALUE="CO"<%= (billingAddress.state.equals("CO")?" SELECTED":"") %>>Colorado
	<OPTION VALUE="CT"<%= (billingAddress.state.equals("CT")?" SELECTED":"") %>>Connecticut
	<OPTION VALUE="DE"<%= (billingAddress.state.equals("DE")?" SELECTED":"") %>>Delaware
	<OPTION VALUE="DC"<%= (billingAddress.state.equals("DC")?" SELECTED":"") %>>District of Columbia
	<OPTION VALUE="FM"<%= (billingAddress.state.equals("FM")?" SELECTED":"") %>>Federated States of Micronesia
	<OPTION VALUE="FL"<%= (billingAddress.state.equals("FL")?" SELECTED":"") %>>Florida
	<OPTION VALUE="GA"<%= (billingAddress.state.equals("GA")?" SELECTED":"") %>>Georgia
	<OPTION VALUE="GU"<%= (billingAddress.state.equals("GU")?" SELECTED":"") %>>Guam
	<OPTION VALUE="HI"<%= (billingAddress.state.equals("HI")?" SELECTED":"") %>>Hawaii
	<OPTION VALUE="ID"<%= (billingAddress.state.equals("ID")?" SELECTED":"") %>>Idaho
	<OPTION VALUE="IL"<%= (billingAddress.state.equals("IL")?" SELECTED":"") %>>Illinois
	<OPTION VALUE="IN"<%= (billingAddress.state.equals("IN")?" SELECTED":"") %>>Indiana
	<OPTION VALUE="IA"<%= (billingAddress.state.equals("IA")?" SELECTED":"") %>>Iowa
	<OPTION VALUE="KS"<%= (billingAddress.state.equals("KS")?" SELECTED":"") %>>Kansas
	<OPTION VALUE="KY"<%= (billingAddress.state.equals("KY")?" SELECTED":"") %>>Kentucky
	<OPTION VALUE="LA"<%= (billingAddress.state.equals("LA")?" SELECTED":"") %>>Louisiana
	<OPTION VALUE="ME"<%= (billingAddress.state.equals("ME")?" SELECTED":"") %>>Maine
	<OPTION VALUE="MB"<%= (billingAddress.state.equals("MB")?" SELECTED":"") %>>Manitoba
	<OPTION VALUE="MH"<%= (billingAddress.state.equals("MH")?" SELECTED":"") %>>Marshall Islands
	<OPTION VALUE="MD"<%= (billingAddress.state.equals("MD")?" SELECTED":"") %>>Maryland
	<OPTION VALUE="MA"<%= (billingAddress.state.equals("MA")?" SELECTED":"") %>>Massachusetts
	<OPTION VALUE="MI"<%= (billingAddress.state.equals("MI")?" SELECTED":"") %>>Michigan
	<OPTION VALUE="MN"<%= (billingAddress.state.equals("MN")?" SELECTED":"") %>>Minnesota
	<OPTION VALUE="MS"<%= (billingAddress.state.equals("MS")?" SELECTED":"") %>>Mississippi
	<OPTION VALUE="MO"<%= (billingAddress.state.equals("MO")?" SELECTED":"") %>>Missouri
	<OPTION VALUE="MT"<%= (billingAddress.state.equals("MT")?" SELECTED":"") %>>Montana
	<OPTION VALUE="NE"<%= (billingAddress.state.equals("NE")?" SELECTED":"") %>>Nebraska
	<OPTION VALUE="NV"<%= (billingAddress.state.equals("NV")?" SELECTED":"") %>>Nevada
	<OPTION VALUE="NB"<%= (billingAddress.state.equals("NB")?" SELECTED":"") %>>New Brunswick
	<OPTION VALUE="NH"<%= (billingAddress.state.equals("NH")?" SELECTED":"") %>>New Hampshire
	<OPTION VALUE="NJ"<%= (billingAddress.state.equals("NJ")?" SELECTED":"") %>>New Jersey
	<OPTION VALUE="NM"<%= (billingAddress.state.equals("NM")?" SELECTED":"") %>>New Mexico
	<OPTION VALUE="NY"<%= (billingAddress.state.equals("NY")?" SELECTED":"") %>>New York
	<OPTION VALUE="NF"<%= (billingAddress.state.equals("NF")?" SELECTED":"") %>>Newfoundland
	<OPTION VALUE="NC"<%= (billingAddress.state.equals("NC")?" SELECTED":"") %>>North Carolina
	<OPTION VALUE="ND"<%= (billingAddress.state.equals("ND")?" SELECTED":"") %>>North Dakota
	<OPTION VALUE="MP"<%= (billingAddress.state.equals("MP")?" SELECTED":"") %>>Northern Mariana Island
	<OPTION VALUE="NT"<%= (billingAddress.state.equals("NT")?" SELECTED":"") %>>Northwest Territories
	<OPTION VALUE="NS"<%= (billingAddress.state.equals("NS")?" SELECTED":"") %>>Nova Scotia
	<OPTION VALUE="OH"<%= (billingAddress.state.equals("OH")?" SELECTED":"") %>>Ohio
	<OPTION VALUE="OK"<%= (billingAddress.state.equals("OK")?" SELECTED":"") %>>Oklahoma
	<OPTION VALUE="ON"<%= (billingAddress.state.equals("ON")?" SELECTED":"") %>>Ontario
	<OPTION VALUE="OR"<%= (billingAddress.state.equals("OR")?" SELECTED":"") %>>Oregon
	<OPTION VALUE="PW"<%= (billingAddress.state.equals("PW")?" SELECTED":"") %>>Palau Island
	<OPTION VALUE="PA"<%= (billingAddress.state.equals("PA")?" SELECTED":"") %>>Pennsylvania
	<OPTION VALUE="PE"<%= (billingAddress.state.equals("PE")?" SELECTED":"") %>>Prince Edward Island
	<OPTION VALUE="PR"<%= (billingAddress.state.equals("PR")?" SELECTED":"") %>>Puerto Rico
	<OPTION VALUE="QC"<%= (billingAddress.state.equals("QC")?" SELECTED":"") %>>Quebec
	<OPTION VALUE="RI"<%= (billingAddress.state.equals("RI")?" SELECTED":"") %>>Rhode Island
	<OPTION VALUE="SK"<%= (billingAddress.state.equals("SK")?" SELECTED":"") %>>Saskatchewan
	<OPTION VALUE="SC"<%= (billingAddress.state.equals("SC")?" SELECTED":"") %>>South Carolina
	<OPTION VALUE="SD"<%= (billingAddress.state.equals("SD")?" SELECTED":"") %>>South Dakota
	<OPTION VALUE="TN"<%= (billingAddress.state.equals("TN")?" SELECTED":"") %>>Tennessee
	<OPTION VALUE="TX"<%= (billingAddress.state.equals("TX")?" SELECTED":"") %>>Texas
	<OPTION VALUE="UT"<%= (billingAddress.state.equals("UT")?" SELECTED":"") %>>Utah
	<OPTION VALUE="VT"<%= (billingAddress.state.equals("VT")?" SELECTED":"") %>>Vermont
	<OPTION VALUE="VI"<%= (billingAddress.state.equals("VI")?" SELECTED":"") %>>Virgin Islands
	<OPTION VALUE="VA"<%= (billingAddress.state.equals("VA")?" SELECTED":"") %>>Virginia
	<OPTION VALUE="WA"<%= (billingAddress.state.equals("WA")?" SELECTED":"") %>>Washington
	<OPTION VALUE="WV"<%= (billingAddress.state.equals("WV")?" SELECTED":"") %>>West Virginia
	<OPTION VALUE="WI"<%= (billingAddress.state.equals("WI")?" SELECTED":"") %>>Wisconsin
	<OPTION VALUE="WY"<%= (billingAddress.state.equals("WY")?" SELECTED":"") %>>Wyoming
	<OPTION VALUE="YT"<%= (billingAddress.state.equals("YT")?" SELECTED":"") %>>Yukon Territory</SELECT>
	<fontx face="Geneva, Verdana, Helvetica" size=-1><BR>US/Canada/Military addresses use menu above - use box below for others<BR>
	<INPUT TYPE=TEXT NAME=bill_state_intl size=40 VALUE="<%= billingAddress.state %>"><BR>&nbsp;</TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Zip/Postal Code</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_zip size=40 VALUE="<%= billingAddress.zip %>"></TD>
</TR>

<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Country</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<%= OrderUtilities.getCountryList().getHTMLSelect(billingAddress.country,"bill_country") %></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Phone Number</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_phone size=40 VALUE="<%= billContact.phone %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Email Address (optional)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_email size=40 VALUE="<%= billContact.email %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Payment Type</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top><SELECT NAME="payment_type">
<% Map paymentOptions = client.getClientPolicy().getPaymentOptions();
	Iterator codeIterator = paymentOptions.keySet().iterator();
	while(codeIterator.hasNext())
	{
		String key = (String) codeIterator.next();
        if((client.pp_proc != null && client.pp_proc.indexOf("payment") > 0) || ((!(key.equals("CC")))))
        {

		%><OPTION VALUE="<%=key%>" <%= currorder.bill_cc_type.equals(key)?"SELECTED":""%>><%=paymentOptions.get(key)%>
<%}}%></SELECT></TD>
</TR>
<%
   if(client.pp_proc != null && client.pp_proc.indexOf("payment") > 0)
   { %>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Credit Card Account # (if applicable)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=cc_num size=40 VALUE="<%= currorder.cc_num %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Credit Card Expiration Month (if applicable)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<%= getHTMLSelect(payMons,payMonNames,""+currorder.cc_exp_mon,"cc_exp_mon") %></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Credit Card Expiration Year (if applicable)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<%= getHTMLSelect(payYears,payYears,""+currorder.cc_exp_year,"cc_exp_year") %></TD>
</TR>
<% } %>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>PO Number</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=po_num size=40 VALUE="<%= currorder.po_num %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Client Order Reference</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=order_refnum size=40 VALUE="<%= currorder.order_refnum %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Coupon/Discount Code</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=coupon_code size=40 VALUE="<%= currorder.coupon_code %>"></TD>
</TR>
<% for (int i=0;i<customFields.size();i++)
{
	CustomField cf = (CustomField)customFields.get(i);
	%>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B><%= cf.getDisplayName()%></B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<%= cf.getFormEntryHTML(currorder) %><BR><font size=-1><%= cf.getDescription() %></font></TD>
</TR>
	<%
}
	%>
<TR><TD align=center colspan=2><font  size=2><HR><B>Shipping Information<B></FONT><HR></TD></TR>
<TR><TD align=right valign=top nowrap colspan=2><fontx face="Geneva, Verdana, Helvetica" size=-1>
<INPUT TYPE=CHECKBOX NAME=shipsame VALUE=1>&nbsp;Copy Shipping from Billing<BR><INPUT TYPE=SUBMIT NAME=editAction VALUE="Save Customer Info and Continue"></FONT><HR></TD></TR>
<TR>
<TD ALIGN=RIGHT valign=top width=50%><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Full name of shipment addressee</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top width=50%>
<INPUT TYPE=TEXT NAME=ship_name size=40 VALUE="<%= shipContact.name %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Company name (optional)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_company size=40 VALUE='<%=".".equals(shippingAddress.company_name)?"":shippingAddress.company_name%>'></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Delivery Address Line 1</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_address1 size=40 VALUE="<%= shippingAddress.address_one %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Delivery Address Line 2</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_address2 size=40 VALUE="<%= shippingAddress.address_two %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>City</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_city size=40 VALUE="<%= shippingAddress.city %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>State/Region/Province</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top><BR>
<SELECT NAME="ship_state" SIZE=1>
	<OPTION VALUE=""<%= (shippingAddress.state.equals("")?" SELECTED":"") %>>Click to select or use box below
	<OPTION VALUE="AL"<%= (shippingAddress.state.equals("AL")?" SELECTED":"") %>>Alabama
	<OPTION VALUE="AK"<%= (shippingAddress.state.equals("AK")?" SELECTED":"") %>>Alaska
	<OPTION VALUE="AB"<%= (shippingAddress.state.equals("AB")?" SELECTED":"") %>>Alberta
	<OPTION VALUE="AS"<%= (shippingAddress.state.equals("AS")?" SELECTED":"") %>>American Samoa
	<OPTION VALUE="AZ"<%= (shippingAddress.state.equals("AZ")?" SELECTED":"") %>>Arizona
	<OPTION VALUE="AR"<%= (shippingAddress.state.equals("AR")?" SELECTED":"") %>>Arkansas
	<OPTION VALUE="AA"<%= (shippingAddress.state.equals("AA")?" SELECTED":"") %>>Armed Forces - USA/Canada
	<OPTION VALUE="AE"<%= (shippingAddress.state.equals("AE")?" SELECTED":"") %>>Armed Forces - Europe
	<OPTION VALUE="AP"<%= (shippingAddress.state.equals("AP")?" SELECTED":"") %>>Armed Forces - Pacific
	<OPTION VALUE="BC"<%= (shippingAddress.state.equals("BC")?" SELECTED":"") %>>British Columbia
	<OPTION VALUE="CA"<%= (shippingAddress.state.equals("CA")?" SELECTED":"") %>>California
	<OPTION VALUE="CO"<%= (shippingAddress.state.equals("CO")?" SELECTED":"") %>>Colorado
	<OPTION VALUE="CT"<%= (shippingAddress.state.equals("CT")?" SELECTED":"") %>>Connecticut
	<OPTION VALUE="DE"<%= (shippingAddress.state.equals("DE")?" SELECTED":"") %>>Delaware
	<OPTION VALUE="DC"<%= (shippingAddress.state.equals("DC")?" SELECTED":"") %>>District of Columbia
	<OPTION VALUE="FM"<%= (shippingAddress.state.equals("FM")?" SELECTED":"") %>>Federated States of Micronesia
	<OPTION VALUE="FL"<%= (shippingAddress.state.equals("FL")?" SELECTED":"") %>>Florida
	<OPTION VALUE="GA"<%= (shippingAddress.state.equals("GA")?" SELECTED":"") %>>Georgia
	<OPTION VALUE="GU"<%= (shippingAddress.state.equals("GU")?" SELECTED":"") %>>Guam
	<OPTION VALUE="HI"<%= (shippingAddress.state.equals("HI")?" SELECTED":"") %>>Hawaii
	<OPTION VALUE="ID"<%= (shippingAddress.state.equals("ID")?" SELECTED":"") %>>Idaho
	<OPTION VALUE="IL"<%= (shippingAddress.state.equals("IL")?" SELECTED":"") %>>Illinois
	<OPTION VALUE="IN"<%= (shippingAddress.state.equals("IN")?" SELECTED":"") %>>Indiana
	<OPTION VALUE="IA"<%= (shippingAddress.state.equals("IA")?" SELECTED":"") %>>Iowa
	<OPTION VALUE="KS"<%= (shippingAddress.state.equals("KS")?" SELECTED":"") %>>Kansas
	<OPTION VALUE="KY"<%= (shippingAddress.state.equals("KY")?" SELECTED":"") %>>Kentucky
	<OPTION VALUE="LA"<%= (shippingAddress.state.equals("LA")?" SELECTED":"") %>>Louisiana
	<OPTION VALUE="ME"<%= (shippingAddress.state.equals("ME")?" SELECTED":"") %>>Maine
	<OPTION VALUE="MB"<%= (shippingAddress.state.equals("MB")?" SELECTED":"") %>>Manitoba
	<OPTION VALUE="MH"<%= (shippingAddress.state.equals("MH")?" SELECTED":"") %>>Marshall Islands
	<OPTION VALUE="MD"<%= (shippingAddress.state.equals("MD")?" SELECTED":"") %>>Maryland
	<OPTION VALUE="MA"<%= (shippingAddress.state.equals("MA")?" SELECTED":"") %>>Massachusetts
	<OPTION VALUE="MI"<%= (shippingAddress.state.equals("MI")?" SELECTED":"") %>>Michigan
	<OPTION VALUE="MN"<%= (shippingAddress.state.equals("MN")?" SELECTED":"") %>>Minnesota
	<OPTION VALUE="MS"<%= (shippingAddress.state.equals("MS")?" SELECTED":"") %>>Mississippi
	<OPTION VALUE="MO"<%= (shippingAddress.state.equals("MO")?" SELECTED":"") %>>Missouri
	<OPTION VALUE="MT"<%= (shippingAddress.state.equals("MT")?" SELECTED":"") %>>Montana
	<OPTION VALUE="NE"<%= (shippingAddress.state.equals("NE")?" SELECTED":"") %>>Nebraska
	<OPTION VALUE="NV"<%= (shippingAddress.state.equals("NV")?" SELECTED":"") %>>Nevada
	<OPTION VALUE="NB"<%= (shippingAddress.state.equals("NB")?" SELECTED":"") %>>New Brunswick
	<OPTION VALUE="NH"<%= (shippingAddress.state.equals("NH")?" SELECTED":"") %>>New Hampshire
	<OPTION VALUE="NJ"<%= (shippingAddress.state.equals("NJ")?" SELECTED":"") %>>New Jersey
	<OPTION VALUE="NM"<%= (shippingAddress.state.equals("NM")?" SELECTED":"") %>>New Mexico
	<OPTION VALUE="NY"<%= (shippingAddress.state.equals("NY")?" SELECTED":"") %>>New York
	<OPTION VALUE="NF"<%= (shippingAddress.state.equals("NF")?" SELECTED":"") %>>Newfoundland
	<OPTION VALUE="NC"<%= (shippingAddress.state.equals("NC")?" SELECTED":"") %>>North Carolina
	<OPTION VALUE="ND"<%= (shippingAddress.state.equals("ND")?" SELECTED":"") %>>North Dakota
	<OPTION VALUE="MP"<%= (shippingAddress.state.equals("MP")?" SELECTED":"") %>>Northern Mariana Island
	<OPTION VALUE="NT"<%= (shippingAddress.state.equals("NT")?" SELECTED":"") %>>Northwest Territories
	<OPTION VALUE="NS"<%= (shippingAddress.state.equals("NS")?" SELECTED":"") %>>Nova Scotia
	<OPTION VALUE="OH"<%= (shippingAddress.state.equals("OH")?" SELECTED":"") %>>Ohio
	<OPTION VALUE="OK"<%= (shippingAddress.state.equals("OK")?" SELECTED":"") %>>Oklahoma
	<OPTION VALUE="ON"<%= (shippingAddress.state.equals("ON")?" SELECTED":"") %>>Ontario
	<OPTION VALUE="OR"<%= (shippingAddress.state.equals("OR")?" SELECTED":"") %>>Oregon
	<OPTION VALUE="PW"<%= (shippingAddress.state.equals("PW")?" SELECTED":"") %>>Palau Island
	<OPTION VALUE="PA"<%= (shippingAddress.state.equals("PA")?" SELECTED":"") %>>Pennsylvania
	<OPTION VALUE="PE"<%= (shippingAddress.state.equals("PE")?" SELECTED":"") %>>Prince Edward Island
	<OPTION VALUE="PR"<%= (shippingAddress.state.equals("PR")?" SELECTED":"") %>>Puerto Rico
	<OPTION VALUE="QC"<%= (shippingAddress.state.equals("QC")?" SELECTED":"") %>>Quebec
	<OPTION VALUE="RI"<%= (shippingAddress.state.equals("RI")?" SELECTED":"") %>>Rhode Island
	<OPTION VALUE="SK"<%= (shippingAddress.state.equals("SK")?" SELECTED":"") %>>Saskatchewan
	<OPTION VALUE="SC"<%= (shippingAddress.state.equals("SC")?" SELECTED":"") %>>South Carolina
	<OPTION VALUE="SD"<%= (shippingAddress.state.equals("SD")?" SELECTED":"") %>>South Dakota
	<OPTION VALUE="TN"<%= (shippingAddress.state.equals("TN")?" SELECTED":"") %>>Tennessee
	<OPTION VALUE="TX"<%= (shippingAddress.state.equals("TX")?" SELECTED":"") %>>Texas
	<OPTION VALUE="UT"<%= (shippingAddress.state.equals("UT")?" SELECTED":"") %>>Utah
	<OPTION VALUE="VT"<%= (shippingAddress.state.equals("VT")?" SELECTED":"") %>>Vermont
	<OPTION VALUE="VI"<%= (shippingAddress.state.equals("VI")?" SELECTED":"") %>>Virgin Islands
	<OPTION VALUE="VA"<%= (shippingAddress.state.equals("VA")?" SELECTED":"") %>>Virginia
	<OPTION VALUE="WA"<%= (shippingAddress.state.equals("WA")?" SELECTED":"") %>>Washington
	<OPTION VALUE="WV"<%= (shippingAddress.state.equals("WV")?" SELECTED":"") %>>West Virginia
	<OPTION VALUE="WI"<%= (shippingAddress.state.equals("WI")?" SELECTED":"") %>>Wisconsin
	<OPTION VALUE="WY"<%= (shippingAddress.state.equals("WY")?" SELECTED":"") %>>Wyoming
	<OPTION VALUE="YT"<%= (shippingAddress.state.equals("YT")?" SELECTED":"") %>>Yukon Territory</SELECT><fontx face="Geneva, Verdana, Helvetica" size=-1><BR>US/Canada/Military addresses use menu above - use box below for others<BR>
	<INPUT TYPE=TEXT NAME=ship_state_intl size=40 VALUE="<%= shippingAddress.state %>"><BR>&nbsp;</TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Zip/Postal Code</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_zip size=40 VALUE="<%= shippingAddress.zip %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Country</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<%= com.owd.core.business.order.OrderUtilities.getCountryList().getHTMLSelect(shippingAddress.country, "ship_country") %></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Phone Number</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_phone size=40 VALUE="<%= shipContact.phone %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Email Address (optional)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_email size=40 VALUE="<%= shipContact.email %>"></TD>
</TR>

<TR>
<TD ALIGN=RIGHT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<B>Gift Shipment</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>
<INPUT TYPE=CHECKBOX NAME=is_gift VALUE="1" <%= currorder.is_gift.equals("1")?"CHECKED":"" %>> Use Gift Invoice<BR>
Optional Gift Message (up to size of box)<BR><TEXTAREA NAME="gift_message" rows=6 cols=40 ><%= currorder.gift_message %></TEXTAREA></TD>
</TR>

<TR><TD COLSPAN=2 ALIGN=RIGHT>&nbsp;<BR><INPUT TYPE=SUBMIT NAME=editAction VALUE="Save Customer Info and Continue"></TD></TR>
</TABLE>
<HR>


</body>
</html>
<%!

public  String ts(String str)
 {

 	if (str == null) return "";

 	return str.trim();
 }

	public String getHTMLSelect(String[] values, String[] names, String defaultValue, String selectName)
	{

		StringBuffer sb = new StringBuffer();

		sb.append("<SELECT NAME=\""+selectName+"\">");

		sb.append("<OPTION VALUE=\"\"> Click to Select </OPTION>");
		for (int i=0;i<values.length;i++)
		{
			String value = values[i].trim();
			////System.out.println("value: "+value+"\t"+"default value: "+defaultValue);
			if (value.equalsIgnoreCase(defaultValue.trim()))
			{
				sb.append("<OPTION VALUE=\""+value+"\" SELECTED>"+names[i]);
			}else
			{
				sb.append("<OPTION VALUE=\""+value+"\">"+names[i]);
			}
		}

		sb.append("</SELECT>");
		return sb.toString();

	}



%>


