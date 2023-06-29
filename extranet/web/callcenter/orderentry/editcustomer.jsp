<html>
<%@ page import="com.owd.core.business.Address,
				 com.owd.core.business.Client,
				 com.owd.core.business.Contact,
				 com.owd.core.business.client.CustomField,
				 com.owd.core.business.order.Order,
				 com.owd.core.business.order.OrderUtilities,
				 com.owd.core.managers.FacilitiesManager,
				 com.owd.hibernate.HibernateSession"
        %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%


    String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
    if (user_client_id == null) user_client_id = "-1";
    if (user_client_id.equals("")) user_client_id = "-1";


    DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    String[] payTypes = {"CC", "CK", "FREE"};
    String errorMessage = (String) session.getAttribute("error");


    String[] payMons = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String[] payMonNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    String[] payYears = {"2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031"};

    String shipError = "";


    String client_id = (String) request.getSession(true).getAttribute("client_id");
    if (client_id == null || "".equals(client_id) || "0".equals(client_id)) {
        try {
            request.getRequestDispatcher("orderentry.jsp").forward(request, response);
        } catch (Exception e) {

        }
    }
    String sql = "select template from owd_client_template_list where client_fkey = :clientFkey";
    List<String> templates = new ArrayList<String>();
    System.out.println(client_id);
    System.out.println(user_client_id);
    try {
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("clientFkey", client_id);
        List l = q.list();
        if (l.size() > 0) {
            for (Object data : l) {
                templates.add(data.toString());
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    Client client = (Client) request.getSession(true).getAttribute("client");
    List customFields = (List) request.getSession(true).getAttribute("customFields");

    Order currorder = (Order) request.getSession(true).getAttribute("currorder");

    Address billingAddress = currorder.getBillingAddress();
    Address shippingAddress = currorder.getShippingAddress();
    Contact billContact = currorder.getBillingContact();
    Contact shipContact = currorder.getShippingContact();
    String orderType = "";
    if(billContact.getName().length()>1 || billingAddress.getCompany_name().length()>1){
        System.out.println("WE have name set doing business order lookup");
        if(currorder.business_order){
            orderType = "Business";
        }else{
            orderType = "Consumer";
        }
    }else{
        System.out.println("ORder type select");
        orderType = "select";
    }

    if ("update".equals((String) request.getParameter("editactionitems"))) {
        currorder.order_type = ts((String) request.getParameter("order_type"));
        currorder.holdUntilDate = null;
        if ((String) request.getParameter("template") != null) {
            currorder.template = (String) request.getParameter("template");
        }
        currorder.companyOverride = (String) request.getParameter("companyOverride");
        if ((String) request.getParameter("holdUntilDate") != null) {
            try {
                currorder.holdUntilDate = df.parse(request.getParameter("holdUntilDate"));
            } catch (Exception exd) {

            }

        }
        currorder.order_refnum = ts((String) request.getParameter("order_refnum"));
        currorder.po_num = ts((String) request.getParameter("po_num"));
        currorder.coupon_code = ts((String) request.getParameter("coupon_code"));

        client.getClientPolicy().updateCustomOrderFields(currorder, request, customFields);

        if (ts((String) request.getParameter("is_gift")).equals("1")) {
            currorder.is_gift = "1";
            currorder.gift_message = (String) request.getParameter("gift_message");
        } else {
            currorder.is_gift = "0";
            currorder.gift_message = "";
        }


        billContact = new Contact(ts((String) request.getParameter("bill_name")),
                ts((String) request.getParameter("bill_phone")),
                "",
                ts((String) request.getParameter("bill_email")),
                ""
        );
        currorder.setBillingContact(billContact);
        if (ts((String) request.getParameter("shipsame")).equals("1")) {
            shipContact = billContact;
        } else {
            shipContact = new Contact(ts((String) request.getParameter("ship_name")),
                    ts((String) request.getParameter("ship_phone")),
                    "",
                    ts((String) request.getParameter("ship_email")),
                    ""
            );
        }
        currorder.getShippingInfo().setShippingContact(shipContact);

        String temp_bill_state = ts((String) request.getParameter("bill_state"));
        String done_bill_state = temp_bill_state;

        if (temp_bill_state.equals("")) {
            done_bill_state = ts((String) request.getParameter("bill_state_intl"));
        }

        String temp_ship_state = ts((String) request.getParameter("ship_state"));
        String done_ship_state = temp_ship_state;

        if (temp_ship_state.equals("")) {
            done_ship_state = ts((String) request.getParameter("ship_state_intl"));
        }


        billingAddress = new Address(ts((String) request.getParameter("bill_company")),
                ts((String) request.getParameter("bill_address1")),
                ts((String) request.getParameter("bill_address2")),
                ts((String) request.getParameter("bill_city")),
                done_bill_state,
                ts((String) request.getParameter("bill_zip")),
                ts((String) request.getParameter("bill_country"))
        );
        currorder.setBillingAddress(billingAddress);
        if (ts((String) request.getParameter("shipsame")).equals("1")) {
            shippingAddress = billingAddress;
        } else {
            shippingAddress = new Address(ts((String) request.getParameter("ship_company")),
                    ts((String) request.getParameter("ship_address1")),
                    ts((String) request.getParameter("ship_address2")),
                    ts((String) request.getParameter("ship_city")),
                    done_ship_state,
                    ts((String) request.getParameter("ship_zip")),
                    ts((String) request.getParameter("ship_country"))
            );
        }
        if (shippingAddress.address_one.length() < 1)
            shippingAddress = billingAddress;

        currorder.getShippingInfo().setShippingAddress(shippingAddress);

        currorder.bill_cc_type = ts((String) request.getParameter("payment_type"));
        currorder.group_name = ts((String) request.getParameter("group"));
        currorder.setFacilityPolicy(ts((String) request.getParameter("facility")));
        currorder.setFacilityCode(currorder.getFacilityPolicy());

        System.out.println("editcustomer.jsp - Payment type: " + currorder.bill_cc_type);
        if (currorder.bill_cc_type.equals("Custom")) {
            currorder.cc_num = ts((String) request.getParameter("cc_num"));
            currorder.cc_exp_mon = 1;
            currorder.cc_exp_year = 2000;
        }
        if (currorder.bill_cc_type.equals("CK")) {
            currorder.cc_num = "";
            currorder.cc_exp_mon = 1;
            currorder.cc_exp_year = 2000;
        } else if (currorder.bill_cc_type.equals("CC") || currorder.bill_cc_type.equals("FLEX")) {
            //CC
            currorder.cc_num = ts((String) request.getParameter("cc_num"));
            try {
                currorder.cc_exp_mon = new Integer(ts((String) request.getParameter("cc_exp_mon"))).intValue();
            } catch (Exception ex) {
                currorder.cc_exp_mon = 1;
            }
            try {
                currorder.cc_exp_year = new Integer(ts((String) request.getParameter("cc_exp_year"))).intValue();
            } catch (Exception ex) {
                currorder.cc_exp_year = 2000;
            }
        } else if (currorder.bill_cc_type.equals("ECK")) {
            currorder.cc_num = "";
            currorder.cc_exp_mon = 1;
            currorder.cc_exp_year = 2000;
            currorder.ck_acct = ts((String) request.getParameter("ck_acct"));
            currorder.ck_bank = ts((String) request.getParameter("ck_bank"));
            currorder.ck_number = ts((String) request.getParameter("ck_number"));


        } else {
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

        String service = (String) ts((String) request.getParameter("ship_type"));
        if (service.length() > 0)
            currorder.getShippingInfo().setShipOptions(ts((String) request.getParameter("ship_type")), "Prepaid", "");




        client.getClientPolicy().handleCustomOrderFields(currorder, customFields);
        String shipmentType = (String) request.getParameter("business_order");
        if(shipmentType.equals("Business")||shipmentType.equals("Consumer")) {
            if(shipmentType.equals("Business")){
                currorder.business_order = true;
            }
            try {
                session.setAttribute("currorder", currorder);
                if (currorder.bill_cc_type.equalsIgnoreCase("FREE")) {
                    session.setAttribute("warning", "WARNING: This order has been marked as FREE - no charge will be made to the customer. If this is not correct, return to the customer information page and choose the proper form of payment.");
                }
                request.getRequestDispatcher("orderentry.jsp").forward(request, response);
            } catch (Exception e) {

            }
        }else{
            errorMessage = "You must select shipment Type";
        }

        // Sean 04-06-2020 case 764637 
        String tax_and_duty = (String) request.getParameter("tax_and_duty");
        if(tax_and_duty.equals("ddp") && request.getParameter("ship_country")!="USA" ){
            currorder.setDDPFlag(1);
        }


    }
    DecimalFormat dform = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
    request.setAttribute("clientselector", Client.getClientForID(client_id).company_name);
    request.setAttribute("areatitle", "Order Entry");
    String error = (String) request.getAttribute("errormessage");

%>

<jsp:include page="/extranettop.jsp"/>
<script language="JavaScript">
    // The following script is used to hide the calendar whenever you click the document.
    document.onmousedown = function (e) {
        var n = !e ? self.event.srcElement.name : e.target.name;
        if (document.layers) {
            with (gfPop) var l = pageX, t = pageY, r = l + clip.width, b = t + clip.height;
            if (n != "popcal" && (e.pageX > r || e.pageX < l || e.pageY > b || e.pageY < t)) gfPop.fHideCal();
            return routeEvent(e); // must use return here.
        } else if (n != "popcal") gfPop.fHideCal();
    }
    if (document.layers) document.captureEvents(Event.MOUSEDOWN);
</script>
<body bgcolor=#FFFFFF>
<CENTER>
    <fontx face="Geneva, Verdana, Helvetica" size=-1><B>Editing Customer Information</B></font>

            <%=errorMessage!=null?"<p><span style=\"color:red\">"+errorMessage:"</span>"%>
</CENTER>
<FORM METHOD=POST name="custform" ACTION=editcustomer.jsp?editactionitems=update>
    <TABLE border=0 cellpadding=0 cellspacing=0 width=100%>
        <TR>
            <TD align=center colspan=2><font size=2>
                <HR>
                <B>Billing Information<B></FONT>
                <HR>
            </TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top width=50%>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Shipment Type:</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top width=50%>
                <SELECT NAME="business_order" SIZE=1>
                    <OPTION VALUE=""<%= (orderType.equals("select")?" SELECTED":"") %>>Click to select
                    <OPTION VALUE="Business"<%= (orderType.equals("Business")?" SELECTED":"") %>>Business
                    <OPTION VALUE="Consumer"<%= (orderType.equals("Consumer")?" SELECTED":"") %>>Consumer
                </Select>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top width=50%>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Full name as it appears on credit card or check</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top width=50%>
                <INPUT TYPE=TEXT NAME=bill_name size=40 VALUE="<%= billContact.name %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Company name (optional)</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=bill_company size=40
                       VALUE='<%=".".equals(billingAddress.company_name)?"":billingAddress.company_name%>'></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Billing Address Line 1</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=bill_address1 size=40 VALUE="<%= billingAddress.address_one %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Billing Address Line 2</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=bill_address2 size=40 VALUE="<%= billingAddress.address_two %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>City</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=bill_city size=40 VALUE="<%= billingAddress.city %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>State/Region/Province</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top><BR>
                <SELECT NAME="bill_state" SIZE=1>
                    <OPTION VALUE=""<%= (billingAddress.state.equals("")?" SELECTED":"") %>>Click to select or use box
                        below
                    <OPTION VALUE="AL"<%= (billingAddress.state.equals("AL")?" SELECTED":"") %>>Alabama
                    <OPTION VALUE="AK"<%= (billingAddress.state.equals("AK")?" SELECTED":"") %>>Alaska
                    <OPTION VALUE="AB"<%= (billingAddress.state.equals("AB")?" SELECTED":"") %>>Alberta
                    <OPTION VALUE="AS"<%= (billingAddress.state.equals("AS")?" SELECTED":"") %>>American Samoa
                    <OPTION VALUE="AZ"<%= (billingAddress.state.equals("AZ")?" SELECTED":"") %>>Arizona
                    <OPTION VALUE="AR"<%= (billingAddress.state.equals("AR")?" SELECTED":"") %>>Arkansas
                    <OPTION VALUE="AA"<%= (billingAddress.state.equals("AA")?" SELECTED":"") %>>Armed Forces -
                        USA/Canada
                    <OPTION VALUE="AE"<%= (billingAddress.state.equals("AE")?" SELECTED":"") %>>Armed Forces - Europe
                    <OPTION VALUE="AP"<%= (billingAddress.state.equals("AP")?" SELECTED":"") %>>Armed Forces - Pacific
                    <OPTION VALUE="BC"<%= (billingAddress.state.equals("BC")?" SELECTED":"") %>>British Columbia
                    <OPTION VALUE="CA"<%= (billingAddress.state.equals("CA")?" SELECTED":"") %>>California
                    <OPTION VALUE="CO"<%= (billingAddress.state.equals("CO")?" SELECTED":"") %>>Colorado
                    <OPTION VALUE="CT"<%= (billingAddress.state.equals("CT")?" SELECTED":"") %>>Connecticut
                    <OPTION VALUE="DE"<%= (billingAddress.state.equals("DE")?" SELECTED":"") %>>Delaware
                    <OPTION VALUE="DC"<%= (billingAddress.state.equals("DC")?" SELECTED":"") %>>District of Columbia
                    <OPTION VALUE="FM"<%= (billingAddress.state.equals("FM")?" SELECTED":"") %>>Federated States of
                        Micronesia
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
                    <OPTION VALUE="YT"<%= (billingAddress.state.equals("YT")?" SELECTED":"") %>>Yukon Territory
                </SELECT>
                <fontx face="Geneva, Verdana, Helvetica" size=-1><BR>US/Canada/Military addresses use menu above - use
                    box below for others<BR>
                    <INPUT TYPE=TEXT NAME=bill_state_intl size=40 VALUE="<%= billingAddress.state %>"><BR>&nbsp;
            </TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Zip/Postal Code</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=bill_zip size=40 VALUE="<%= billingAddress.zip %>"></TD>
        </TR>

        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Country</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <%= OrderUtilities.getCountryList().getHTMLSelect(billingAddress.country, "bill_country") %>
            </TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Phone Number</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=bill_phone size=40 VALUE="<%= billContact.phone %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Email Address (optional)</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=bill_email size=40 VALUE="<%= billContact.email %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Payment Type</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top><SELECT NAME="payment_type">
                <% Map paymentOptions = client.getClientPolicy().getPaymentOptions();
                    Iterator codeIterator = paymentOptions.keySet().iterator();
                    while (codeIterator.hasNext()) {
                        String key = (String) codeIterator.next();
                        if ((client.pp_proc != null && client.pp_proc.indexOf("payment") > 0) || ((!(key.equals("CC"))))) {

                %>
                <OPTION VALUE="<%=key%>" <%= currorder.bill_cc_type.equals(key)?"SELECTED":""%>><%=paymentOptions.get(key)%>
                <%
                        }
                    }
                %></SELECT></TD>
        </TR>
        <%
            if (client.pp_proc != null && client.pp_proc.indexOf("payment") > 0) { %>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Credit Card Account # (if applicable)</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=cc_num size=40 VALUE="<%= currorder.cc_num %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Credit Card Expiration Month (if applicable)</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <%= getHTMLSelect(payMons, payMonNames, "" + currorder.cc_exp_mon, "cc_exp_mon") %>
            </TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Credit Card Expiration Year (if applicable)</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <%= getHTMLSelect(payYears, payYears, "" + currorder.cc_exp_year, "cc_exp_year") %>
            </TD>
        </TR>
        <% } %>
        <% System.out.println("right here      :" + client.do_echeck);
            if (null != client.do_echeck && client.do_echeck.equals("1")) {%>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>E-Check Routing # (if applicable)</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=ck_bank size=40 VALUE="<%= currorder.ck_bank %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>E-Check Account # (if applicable)</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=ck_acct size=40 VALUE="<%= currorder.ck_acct %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>E-Check Check # (if applicable)</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=ck_number size=40 VALUE="<%= currorder.ck_number %>"></TD>
        </TR>
        <%}%>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>PO Number</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=po_num size=40 VALUE="<%= currorder.po_num %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Client Order Reference</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=order_refnum size=40 VALUE="<%= currorder.order_refnum %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Coupon/Discount Code</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=coupon_code size=40 VALUE="<%= currorder.coupon_code %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Group Code</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=group size=40 VALUE="<%= currorder.group_name %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Shipping Facility</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                    <%
      int cid =  Integer.parseInt(currorder.getClientID());

      if(FacilitiesManager.isClientMultiFacility(cid))
    {

      %><SELECT NAME="facility"><%= FacilitiesManager.getHTMLSelectOptions(currorder.getFacilityCode() == null ? FacilitiesManager.getLocationCodeForClient(cid) : currorder.getFacilityCode())%>
            </SELECT>
                    <%
    }else
    {
    String defaultFacility = FacilitiesManager.getLocationCodeForClient(cid);
        %>
                    <%= FacilitiesManager.getFacilityDisplayLabel(FacilitiesManager.getFacilityForCode(defaultFacility)) %><INPUT
                    TYPE="HIDDEN" NAME="facility" VALUE="<%= defaultFacility %>"><%
    }%>
        </TR>
        <% if ("0".equals(user_client_id)) { %>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Order Type:</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <SELECT NAME="order_type" SIZE=1>
                    <OPTION VALUE="OWD Order Entry" <%= (currorder.order_type.equals("OWD Order Entry")?" SELECTED":"") %>>
                        OWD Order Entry (Not Billed)
                    <OPTION VALUE="Mail Order" <%= (currorder.order_type.equals("Mail Order")?" SELECTED":"") %>>Mail
                        Order (Billed as Manual Order)
                </SELECT></TD>
        </TR>

        <TD ALIGN=RIGHT valign=top>
            <fontx face="Geneva, Verdana, Helvetica" size=-1>
                <B>Hold Until (if in stock):</B>&nbsp;:&nbsp;
        </TD>
        <TD ALIGN=LEFT valign=top>
            <input name="holdUntilDate"
                   value="<%= currorder.holdUntilDate==null?"":df.format(currorder.holdUntilDate) %>" size="11"
                   onfocus="this.blur()" readonly><a href="javascript:void(0)"
                                                     onclick="gfPop.fPopCalendar(document.custform.holdUntilDate);return false;"
                                                     HIDEFOCUS name="popcal"><img name="popcali" align="absbottom"
                                                                                  src="/webapps/images/calbtn.gif"
                                                                                  width="34" height="22" border="0"
                                                                                  alt=""></A>&nbsp;<a name="cleardate"
                                                                                                      href="javascript:void(0)"
                                                                                                      onclick="document.custform.holdUntilDate.value='';return false;">CLEAR&nbsp;DATE</a>
            <br><font size="-1">If you enter a date here and choose "Check/Mail" as the payment type, the order will be
            released immediately but will not print until the indicated date. For
            other payment types, printing will also be delayed if the order can be released immediately. The indicated
            date will be ignored if the order is saved as an active backorder or placed on hold.</font></TD>
        </TR>
        <% } %>
        <%
            if (templates.size() > 0) {
        %>
        <tr>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Order Template</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <select name="template">
                    <option value=""></option>
                    <%
                        for (String t : templates) {
                    %>
                    <option value="<%=t%>" <%= (currorder.template.equals(t) ? " SELECTED" : "")%>><%=t%>
                    </option>
                    <%
                        }

                    %>


                </select</TD>
        </tr>
        <%
            }
        %>
        <tr>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Shipping Company Name OverRide(optional)</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=companyOverride size=40 VALUE="<%= currorder.companyOverride %>"></TD>
        </tr>
        <%-- Sean 4/2/2020 --%>
        <tr>
            <td align="RIGHT" valign="top">
                <div>
                    <fontx face="Geneva, Verdana, Helvetica" size="-1"></fontx>
                    <b>International Tax and Duty :</b><br />
                </div>
            </td>
            <td align="LEFT" valign="top">
                <input type="radio" name="tax_and_duty" value="none" checked/> DDU
                <input type="radio" name="tax_and_duty" value="ddp" /> DDP
                <br><br>
            </td>
        </tr>
        <TR>

                <% for (int i=0;i<customFields.size();i++)
{
	CustomField cf = (CustomField)customFields.get(i);
	%>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B><%= cf.getDisplayName()%>
                    </B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <%= cf.getFormEntryHTML(currorder) %><BR><font size=-1><%= cf.getDescription() %>
            </font></TD>
        </TR>
        <%
            }
        %>
        <TR>
            <TD align=center colspan=2><font size=2>
                <HR>
                <B>Shipping Information<B></FONT>
                <HR>
            </TD>
        </TR>
        <TR>
            <TD align=right valign=top nowrap colspan=2>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <INPUT TYPE=CHECKBOX NAME=shipsame VALUE=1>&nbsp;Copy Shipping from Billing<BR><INPUT TYPE=SUBMIT
                                                                                                          NAME=editAction
                                                                                                          VALUE="Save Customer Info and Continue"></FONT>
                    <HR>
            </TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top width=50%>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Full name of shipment addressee</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top width=50%>
                <INPUT TYPE=TEXT NAME=ship_name size=40 VALUE="<%= shipContact.name %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Company name (optional)</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=ship_company size=40
                       VALUE='<%=".".equals(shippingAddress.company_name)?"":shippingAddress.company_name%>'></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Delivery Address Line 1</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=ship_address1 size=40 VALUE="<%= shippingAddress.address_one %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Delivery Address Line 2</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=ship_address2 size=40 VALUE="<%= shippingAddress.address_two %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>City</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=ship_city size=40 VALUE="<%= shippingAddress.city %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>State/Region/Province</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top><BR>
                <SELECT NAME="ship_state" SIZE=1>
                    <OPTION VALUE=""<%= (shippingAddress.state.equals("")?" SELECTED":"") %>>Click to select or use box
                        below
                    <OPTION VALUE="AL"<%= (shippingAddress.state.equals("AL")?" SELECTED":"") %>>Alabama
                    <OPTION VALUE="AK"<%= (shippingAddress.state.equals("AK")?" SELECTED":"") %>>Alaska
                    <OPTION VALUE="AB"<%= (shippingAddress.state.equals("AB")?" SELECTED":"") %>>Alberta
                    <OPTION VALUE="AS"<%= (shippingAddress.state.equals("AS")?" SELECTED":"") %>>American Samoa
                    <OPTION VALUE="AZ"<%= (shippingAddress.state.equals("AZ")?" SELECTED":"") %>>Arizona
                    <OPTION VALUE="AR"<%= (shippingAddress.state.equals("AR")?" SELECTED":"") %>>Arkansas
                    <OPTION VALUE="AA"<%= (shippingAddress.state.equals("AA")?" SELECTED":"") %>>Armed Forces -
                        USA/Canada
                    <OPTION VALUE="AE"<%= (shippingAddress.state.equals("AE")?" SELECTED":"") %>>Armed Forces - Europe
                    <OPTION VALUE="AP"<%= (shippingAddress.state.equals("AP")?" SELECTED":"") %>>Armed Forces - Pacific
                    <OPTION VALUE="BC"<%= (shippingAddress.state.equals("BC")?" SELECTED":"") %>>British Columbia
                    <OPTION VALUE="CA"<%= (shippingAddress.state.equals("CA")?" SELECTED":"") %>>California
                    <OPTION VALUE="CO"<%= (shippingAddress.state.equals("CO")?" SELECTED":"") %>>Colorado
                    <OPTION VALUE="CT"<%= (shippingAddress.state.equals("CT")?" SELECTED":"") %>>Connecticut
                    <OPTION VALUE="DE"<%= (shippingAddress.state.equals("DE")?" SELECTED":"") %>>Delaware
                    <OPTION VALUE="DC"<%= (shippingAddress.state.equals("DC")?" SELECTED":"") %>>District of Columbia
                    <OPTION VALUE="FM"<%= (shippingAddress.state.equals("FM")?" SELECTED":"") %>>Federated States of
                        Micronesia
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
                    <OPTION VALUE="YT"<%= (shippingAddress.state.equals("YT")?" SELECTED":"") %>>Yukon Territory
                </SELECT>
                <fontx face="Geneva, Verdana, Helvetica" size=-1><BR>US/Canada/Military addresses use menu above - use
                    box below for others<BR>
                    <INPUT TYPE=TEXT NAME=ship_state_intl size=40 VALUE="<%= shippingAddress.state %>"><BR>&nbsp;
            </TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Zip/Postal Code</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=ship_zip size=40 VALUE="<%= shippingAddress.zip %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Country</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <%= com.owd.core.business.order.OrderUtilities.getCountryList().getHTMLSelect(shippingAddress.country, "ship_country") %>
            </TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Phone Number</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=ship_phone size=40 VALUE="<%= shipContact.phone %>"></TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Email Address (optional)</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <INPUT TYPE=TEXT NAME=ship_email size=40 VALUE="<%= shipContact.email %>"></TD>
        </TR>

        <TR>
            <TD ALIGN=RIGHT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <B>Gift Shipment</B>&nbsp;:&nbsp;
            </TD>
            <TD ALIGN=LEFT valign=top>
                <fontx face="Geneva, Verdana, Helvetica" size=-1>
                    <INPUT TYPE=CHECKBOX NAME=is_gift VALUE="1" <%= currorder.is_gift.equals("1")?"CHECKED":"" %>> Use
                    Gift Invoice<BR>
                    Optional Gift Message (up to size of box)<BR><TEXTAREA NAME="gift_message" rows=6
                                                                           cols=40><%= currorder.gift_message %></TEXTAREA>
            </TD>
        </TR>

        <TR>
            <TD COLSPAN=2 ALIGN=RIGHT>&nbsp;<BR><INPUT TYPE=SUBMIT NAME=editAction
                                                       VALUE="Save Customer Info and Continue"></TD>
        </TR>
    </TABLE>
    <!-- Begin Footer -->
    <HR ALIGN="center" SIZE="5" NOSHADE/>
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

</BODY>

<iframe width=152 height=163 name="gToday:outlook:agenda.js" id="gToday:outlook:agenda.js"
        src="/webapps/popcal/outlook/ipopeng.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;">
    <LAYER name="gToday:outlook:agenda.js" src="/webapps/popcal/npopeng.htm"></LAYER>
</iframe>
</html>
<%!

    public String ts(String str) {

        if (str == null) return "";

        return str.trim();
    }

    public String getHTMLSelect(String[] values, String[] names, String defaultValue, String selectName) {

        StringBuffer sb = new StringBuffer();

        sb.append("<SELECT NAME=\"" + selectName + "\">");

        sb.append("<OPTION VALUE=\"\"> Click to Select </OPTION>");
        for (int i = 0; i < values.length; i++) {
            String value = values[i].trim();
            ////System.out.println("value: "+value+"\t"+"default value: "+defaultValue);
            if (value.equalsIgnoreCase(defaultValue.trim())) {
                sb.append("<OPTION VALUE=\"" + value + "\" SELECTED>" + names[i]);
            } else {
                sb.append("<OPTION VALUE=\"" + value + "\">" + names[i]);
            }
        }

        sb.append("</SELECT>");
        return sb.toString();

    }


%>


