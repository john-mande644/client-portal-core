<html>
<%@ page import="
com.owd.core.business.Address,
com.owd.core.business.Contact,
com.owd.core.business.order.Order,
com.owd.core.business.order.OrderUtilities,
java.text.DecimalFormat,
java.text.NumberFormat"
%>
<%@ page import="java.util.Locale" %>
<%
	String[] payTypes = {"CC","CK"};
	
	String[] shipTypes = {"First Class","Priority Mail","UPS Next Day Air Saver","UPS 2nd Day Air","Int\'l Letter-Post/Sm Packet Air"};
	String[] shipTypeNames = {"Standard (First Class/Priority/UPS Ground)","(US) Priority Mail (+1.50 for single units)","(US) Overnight - Order by Noon","(US) 2-Day - Order by Noon","(International)"};
	
	String[] payMons = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	String[] payMonNames = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	String[] payYears = {"2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012",
	"2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023"};
	
	String shipError = "";
	String addressError = "";
	Order currorder = null;
	try{
		currorder = (Order) request.getSession(true).getAttribute("currorder");
	}catch (Exception ex)
	{
		currorder = new Order("117");
		request.getSession(true).setAttribute("currorder",currorder);
	}
	if(null==currorder)
	{
		currorder = new Order("117");
		request.getSession(true).setAttribute("currorder",currorder);
	}else{
	
	}
	Address billingAddress = currorder.getBillingAddress();
	Address shippingAddress = currorder.getShippingAddress();
	Contact	billContact = currorder.getBillingContact();
	Contact	shipContact = currorder.getShippingContact();
	
	if("update".equals((String)request.getParameter("editaction")))
	{
	
		if(ts((String)request.getParameter("is_gift")).equals("1"))
	{
		currorder.is_gift = "1";
		//currorder.prt_pack_reqd = 0;
		//currorder.prt_invoice_reqd = 1;
		currorder.gift_message = (String)request.getParameter("gift_message");
        if(currorder.gift_message == null) currorder.gift_message="A gift for you";
            if(currorder.gift_message.trim().length()<1) currorder.gift_message="Agift for you";
    }else
	{
		currorder.is_gift = "0";
		//currorder.prt_pack_reqd = 1;
		//currorder.prt_invoice_reqd = 0;
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
		currorder.getShippingInfo().setShippingAddress(shippingAddress);
	
		currorder.bill_cc_type = ts((String)request.getParameter("payment_type"));
		currorder.cc_cvv_code = ts((String)request.getParameter("cvv_code"));
		if (currorder.bill_cc_type.equals("CK"))
		{
			currorder.cc_num = "";
			currorder.cc_exp_mon=1;
			currorder.cc_exp_year=2000;
		}else{
			//CC
			currorder.bill_cc_type = "CC";
			currorder.cc_num = ts((String)request.getParameter("cc_num"));
			try{
				currorder.cc_exp_mon=new Integer(ts((String)request.getParameter("cc_exp_mon"))).intValue();
			}catch(Exception ex){currorder.cc_exp_mon=1;}
			try{
				currorder.cc_exp_year=new Integer(ts((String)request.getParameter("cc_exp_year"))).intValue();
			}catch(Exception ex){currorder.cc_exp_year=2000;}
		}
			currorder.getShippingInfo().carr_service = ts((String)request.getParameter("ship_type"));
			
			if(shippingAddress.country.equals("USA") && currorder.getCarrier().equals("Int\'l Letter-Post/Sm Packet Air"))
			{
				currorder.getShippingInfo().carr_service = "First Class";
				shipError = "Invalid shipping type for USA destination; carrier has been reset.";
			}
			if((!(shippingAddress.country.equals("USA"))) && (!(currorder.getCarrier().equals("Int\'l Letter-Post/Sm Packet Air"))))
			{
				currorder.getShippingInfo().carr_service = "Int\'l Letter-Post/Sm Packet Air";
				shipError = "Invalid shipping type for non-US destination; carrier has been reset.";
			}
		
		if (!shippingAddress.validateStateValue() || ((shippingAddress.country.toUpperCase().equals("CANADA") || shippingAddress.country.toUpperCase().equals("USA")) && shippingAddress.state.equals("")))
			addressError = "The shipping address state/province and country values do not match. Please choose the correct shipping country and state.";
		if (!billingAddress.validateStateValue() || ((billingAddress.country.toUpperCase().equals("CANADA") || billingAddress.country.toUpperCase().equals("USA")) && billingAddress.state.equals("")))
			addressError = "The billing address state/province and country values do not match. Please choose the correct billing country and state.";
	
	}
	
	
	
	

	
	DecimalFormat dform = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
	
%>
<head>
<title>Call Center Order Creation - Editing Customer Information</title>
</head>
<body bgcolor=#FFFFFF>
<CENTER><font face="Geneva, Verdana, Helvetica" size=-1><B>Call Center Order Creation - Editing Customer Information</B></font></CENTER>
<HR>
<font color=red><B><%= addressError %></B></FONT><BR>
<FORM METHOD=POST ACTION=editcustomer.jsp?editaction=update>
<TABLE border=0 cellpadding=0 cellspacing=0 width=100%>
<TR><TD align=center colspan=2><font face="Geneva, Verdana, Helvetica" size=-1><B>Billing Information<B></FONT></TD></TR>
<TR>
<TD ALIGN=RIGHT valign=top width=50%><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Full name as it appears on credit card or check</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top width=50%>
<INPUT TYPE=TEXT NAME=bill_name size=40 VALUE="<%= billContact.name %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Company name (optional)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_company size=40 VALUE="<%= billingAddress.company_name %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Billing Address Line 1</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_address1 size=40 VALUE="<%= billingAddress.address_one %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Billing Address Line 2</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_address2 size=40 VALUE="<%= billingAddress.address_two %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>City</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_city size=40 VALUE="<%= billingAddress.city %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
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
	<font face="Geneva, Verdana, Helvetica" size=-1><BR>US/Canada/Military addresses use menu above - use box below for others<BR>
	<INPUT TYPE=TEXT NAME=bill_state_intl size=40 VALUE="<%= billingAddress.state %>"><BR>&nbsp;</TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Zip/Postal Code</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_zip size=40 VALUE="<%= billingAddress.zip %>"></TD>
</TR>

<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Country</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<%= com.owd.core.business.order.OrderUtilities.getCountryList().getHTMLSelect(billingAddress.country, "bill_country") %></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Phone Number</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_phone size=40 VALUE="<%= billContact.phone %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Email Address (optional)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=bill_email size=40 VALUE="<%= billContact.email %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Payment Type</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<%= getHTMLSelect(payTypes,payTypes,currorder.bill_cc_type,"payment_type") %></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Credit Card Account # (if applicable)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=cc_num size=40 VALUE="<%= currorder.cc_num %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Credit Card Expiration Month (if applicable)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<%= getHTMLSelect(payMons,payMonNames,""+currorder.cc_exp_mon,"cc_exp_mon") %></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Credit Card Expiration Year (if applicable)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<%= getHTMLSelect(payYears,payYears,""+currorder.cc_exp_year,"cc_exp_year") %></TD>
</TR>
<TR> 
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size="-1"><B>CVV&nbsp;Code
</B>&nbsp;:&nbsp;</TD>
<TD  ALIGN=LEFT  valign=top><INPUT TYPE=TEXT SIZE=4 NAME="cvv_code" VALUE="" >
</TD>
</TR>
<TR><TD >&nbsp;</TD><TD><font face="Geneva, Verdana, Helvetica" size="-2">
The CVV (Customer Verification Value or Card ID #) is an added security feature to help protect you from online credit card fraud. You'll find the three digit CVV number printed on the back of your VISA, Mastercard or Discover card; American Express has a four digit number on the front. If the card does not have a CVV, please leave this field  blank.</TD></TR>
<TR ><TD >&nbsp;</TD><TD><TABLE width=100% border=0 cellpadding=0 cellspacing=0><TR><TD ALIGN=CENTER>Visa, MasterCard & Discover</TD><TD ALIGN=CENTER>American Express</TD></TR>
<TR><TD ALIGN=CENTER><IMG SRC="<%= request.getContextPath()%>/images/cv_card.gif" width=173 height=121></TD><TD ALIGN=CENTER><IMG SRC="<%= request.getContextPath()%>/images/cv_amex_card.gif"  width=173 height=121></TD></TR></TABLE>
</TD></TR>
<TR><TD align=center colspan=2><font face="Geneva, Verdana, Helvetica" size=-1>&nbsp;<P><B>Shipping Information<B>&nbsp;<INPUT TYPE=CHECKBOX NAME=shipsame VALUE=1> Same as billing<HR></FONT></TD></TR>
<TR>
<TD ALIGN=RIGHT valign=top width=50%><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Full name of shipment addressee</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top width=50%>
<INPUT TYPE=TEXT NAME=ship_name size=40 VALUE="<%= shipContact.name %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Company name (optional)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_company size=40 VALUE="<%= shippingAddress.company_name %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Delivery Address Line 1</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_address1 size=40 VALUE="<%= shippingAddress.address_one %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Delivery Address Line 2</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_address2 size=40 VALUE="<%= shippingAddress.address_two %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>City</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_city size=40 VALUE="<%= shippingAddress.city %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
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
	<OPTION VALUE="YT"<%= (shippingAddress.state.equals("YT")?" SELECTED":"") %>>Yukon Territory</SELECT><font face="Geneva, Verdana, Helvetica" size=-1><BR>US/Canada/Military addresses use menu above - use box below for others<BR>
	<INPUT TYPE=TEXT NAME=ship_state_intl size=40 VALUE="<%= shippingAddress.state %>"><BR>&nbsp;</TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Zip/Postal Code</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_zip size=40 VALUE="<%= shippingAddress.zip %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Country</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<%= OrderUtilities.getCountryList().getHTMLSelect(shippingAddress.country,"ship_country") %></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Phone Number</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_phone size=40 VALUE="<%= shipContact.phone %>"></TD>
</TR>
<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Email Address (optional)</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>
<INPUT TYPE=TEXT NAME=ship_email size=40 VALUE="<%= shipContact.email %>"></TD>
</TR>

<TR>
<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>Gift Shipment</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<INPUT TYPE=CHECKBOX NAME=is_gift VALUE="1" <%= currorder.is_gift.equals("1")?"CHECKED":"" %>> Use Gift Invoice<BR>
Optional Gift Message (up to size of box)<BR><TEXTAREA NAME="gift_message" rows=6 cols=40 ><%= currorder.gift_message %></TEXTAREA></TD>
</TR>
<TR>

<TD ALIGN=RIGHT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>
<B>&nbsp;<BR>Ship Via</B>&nbsp;:&nbsp;</TD><TD ALIGN=LEFT valign=top>&nbsp;<BR>
<font color=red><B><%= shipError %></B></FONT><BR>
<%= getHTMLSelect(shipTypes,shipTypeNames,currorder.getCarrier(),"ship_type") %><P>
</TD>
</TR>

<TR><TD COLSPAN=2 ALIGN=RIGHT>&nbsp;<BR><INPUT TYPE=SUBMIT NAME=editAction VALUE="Save Customer Info"></TD></TR>
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
		
		for (int i=0;i<values.length;i++)
		{
			String value = values[i];
			if (value.equals(defaultValue))
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


