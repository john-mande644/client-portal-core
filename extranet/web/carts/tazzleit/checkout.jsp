<%@ page import="com.owd.core.business.order.LineItem"%>
<%@ page import="com.owd.core.business.order.Order"%>
<%@ page import="com.owd.web.carts.tazzle.TazzleCart" %>
<%@ page import="com.owd.web.carts.tazzle.TazzleServlet" %>
<%
	TazzleCart cart = (TazzleCart) request.getSession(true).getAttribute(TazzleServlet.kCartKey);
	
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
<% if (!("".equals(request.getAttribute(TazzleServlet.kCartError))))
{
%>
<TR><td colspan=5><font color="red" face="Geneva, Verdana, Helvetica" size="+1"><%= request.getAttribute(TazzleServlet.kCartError)%><P> </font></TD>
<%
}
%>
 <tr>
  <td colspan=2 align=left><FORM ACTION="/carts/tazzleit/tazzleit.jsp" METHOD="GET">
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
<FORM METHOD="POST" ACTION="/carts/tazzleit/tazzleit.jsp">

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
	<OPTION VALUE="AB"<%= (dom_bill_state.equals("AB")?" SELECTED":"") %>>Alberta
	<OPTION VALUE="AS"<%= (dom_bill_state.equals("AS")?" SELECTED":"") %>>American Samoa
	<OPTION VALUE="AZ"<%= (dom_bill_state.equals("AZ")?" SELECTED":"") %>>Arizona
	<OPTION VALUE="AR"<%= (dom_bill_state.equals("AR")?" SELECTED":"") %>>Arkansas
	<OPTION VALUE="AA"<%= (dom_bill_state.equals("AA")?" SELECTED":"") %>>Armed Forces - USA/Canada
	<OPTION VALUE="AE"<%= (dom_bill_state.equals("AE")?" SELECTED":"") %>>Armed Forces - Europe
	<OPTION VALUE="AP"<%= (dom_bill_state.equals("AP")?" SELECTED":"") %>>Armed Forces - Pacific
	<OPTION VALUE="BC"<%= (dom_bill_state.equals("BC")?" SELECTED":"") %>>British Columbia
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
	<OPTION VALUE="MB"<%= (dom_bill_state.equals("MB")?" SELECTED":"") %>>Manitoba
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
	<OPTION VALUE="NB"<%= (dom_bill_state.equals("NB")?" SELECTED":"") %>>New Brunswick
	<OPTION VALUE="NH"<%= (dom_bill_state.equals("NH")?" SELECTED":"") %>>New Hampshire
	<OPTION VALUE="NJ"<%= (dom_bill_state.equals("NJ")?" SELECTED":"") %>>New Jersey
	<OPTION VALUE="NM"<%= (dom_bill_state.equals("NM")?" SELECTED":"") %>>New Mexico
	<OPTION VALUE="NY"<%= (dom_bill_state.equals("NY")?" SELECTED":"") %>>New York
	<OPTION VALUE="NF"<%= (dom_bill_state.equals("NF")?" SELECTED":"") %>>Newfoundland
	<OPTION VALUE="NC"<%= (dom_bill_state.equals("NC")?" SELECTED":"") %>>North Carolina
	<OPTION VALUE="ND"<%= (dom_bill_state.equals("ND")?" SELECTED":"") %>>North Dakota
	<OPTION VALUE="MP"<%= (dom_bill_state.equals("MP")?" SELECTED":"") %>>Northern Mariana Island
	<OPTION VALUE="NT"<%= (dom_bill_state.equals("NT")?" SELECTED":"") %>>Northwest Territories
	<OPTION VALUE="NS"<%= (dom_bill_state.equals("NS")?" SELECTED":"") %>>Nova Scotia
	<OPTION VALUE="OH"<%= (dom_bill_state.equals("OH")?" SELECTED":"") %>>Ohio
	<OPTION VALUE="OK"<%= (dom_bill_state.equals("OK")?" SELECTED":"") %>>Oklahoma
	<OPTION VALUE="ON"<%= (dom_bill_state.equals("ON")?" SELECTED":"") %>>Ontario
	<OPTION VALUE="OR"<%= (dom_bill_state.equals("OR")?" SELECTED":"") %>>Oregon
	<OPTION VALUE="PW"<%= (dom_bill_state.equals("PW")?" SELECTED":"") %>>Palau Island
	<OPTION VALUE="PA"<%= (dom_bill_state.equals("PA")?" SELECTED":"") %>>Pennsylvania
	<OPTION VALUE="PE"<%= (dom_bill_state.equals("PE")?" SELECTED":"") %>>Prince Edward Island
	<OPTION VALUE="PR"<%= (dom_bill_state.equals("PR")?" SELECTED":"") %>>Puerto Rico
	<OPTION VALUE="QC"<%= (dom_bill_state.equals("QC")?" SELECTED":"") %>>Quebec
	<OPTION VALUE="RI"<%= (dom_bill_state.equals("RI")?" SELECTED":"") %>>Rhode Island
	<OPTION VALUE="SK"<%= (dom_bill_state.equals("SK")?" SELECTED":"") %>>Saskatchewan
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
	<OPTION VALUE="WY"<%= (dom_bill_state.equals("WY")?" SELECTED":"") %>>Wyoming
	<OPTION VALUE="YT"<%= (dom_bill_state.equals("YT")?" SELECTED":"") %>>Yukon Territory</SELECT><BR>
<INPUT TYPE=TEXT SIZE=20 NAME="intl_bill_state" VALUE="<%=intl_bill_state%>" ><BR><font face="Geneva, Verdana, Helvetica" size="-1">If outside the US/Canada, please enter the state or region here, otherwise leave this entry blank.
</TD></TR></TABLE>
</TD></TR>

<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Zip/Postal&nbsp;Code: </TD><TD align="left"><font face="Geneva, Verdana, Helvetica" size="-1"><INPUT TYPE=TEXT SIZE=26 NAME=bill_zip VALUE="<%=order.getBillingAddress().zip%>" ></TD></TR>
<TR>
<TD ALIGN=RIGHT>
<font face="Geneva, Verdana, Helvetica" size="-1">Country:
</TD><TD ><SELECT NAME="bill_country">

<OPTION VALUE="AFGHANISTAN"<%= bill_country.equals("AFGHANISTAN")?" Selected":""%>>AFGHANISTAN
<OPTION VALUE="ALBANIA" <%= bill_country.equals("ALBANIA")?" Selected":""%>>ALBANIA
<OPTION VALUE="ALGERIA"<%= bill_country.equals("ALGERIA")?" Selected":""%>>ALGERIA
<OPTION VALUE="AMERICAN SAMOA"<%= bill_country.equals("AMERICAN SAMOA")?" Selected":""%>>AMERICAN SAMOA
<OPTION VALUE="ANDORRA"<%= bill_country.equals("ANDORRA")?" Selected":""%>>ANDORRA
<OPTION VALUE="ANGOLA"<%= bill_country.equals("ANGOLA")?" Selected":""%>>ANGOLA
<OPTION VALUE="ANGUILLA ISLANDS"<%= bill_country.equals("ANGUILLA ISLANDS")?" Selected":""%>>ANGUILLA ISLANDS
<OPTION VALUE="ANTIGUA"<%= bill_country.equals("ANTIGUA")?" Selected":""%>>ANTIGUA
<OPTION VALUE="ARGENTINA"<%= bill_country.equals("ARGENTINA")?" Selected":""%>>ARGENTINA
<OPTION VALUE="ARMENIA"<%= bill_country.equals("ARMENIA")?" Selected":""%>>ARMENIA
<OPTION VALUE="ARUBA"<%= bill_country.equals("ARUBA")?" Selected":""%>>ARUBA
<OPTION VALUE="AUSTRALIA"<%= bill_country.equals("AUSTRALIA")?" Selected":""%>>AUSTRALIA
<OPTION VALUE="AUSTRIA"<%= bill_country.equals("AUSTRIA")?" Selected":""%>>AUSTRIA
<OPTION VALUE="AZERBAIJAN"<%= bill_country.equals("AZERBAIJAN")?" Selected":""%>>AZERBAIJAN
<OPTION VALUE="BAHAMAS"<%= bill_country.equals("BAHAMAS")?" Selected":""%>>BAHAMAS
<OPTION VALUE="BAHRAIN"<%= bill_country.equals("BAHRAIN")?" Selected":""%>>BAHRAIN
<OPTION VALUE="BANGLADESH"<%= bill_country.equals("BANGLADESH")?" Selected":""%>>BANGLADESH
<OPTION VALUE="BARBADOS"<%= bill_country.equals("BARBADOS")?" Selected":""%>>BARBADOS
<OPTION VALUE="BELARUS"<%= bill_country.equals("BELARUS")?" Selected":""%>>BELARUS
<OPTION VALUE="BELGIUM"<%= bill_country.equals("BELGIUM")?" Selected":""%>>BELGIUM
<OPTION VALUE="BELIZE"<%= bill_country.equals("BELIZE")?" Selected":""%>>BELIZE
<OPTION VALUE="BENIN"<%= bill_country.equals("BENIN")?" Selected":""%>>BENIN
<OPTION VALUE="BERMUDA"<%= bill_country.equals("BERMUDA")?" Selected":""%>>BERMUDA
<OPTION VALUE="BHUTAN"<%= bill_country.equals("BHUTAN")?" Selected":""%>>BHUTAN
<OPTION VALUE="BOLIVIA"<%= bill_country.equals("BOLIVIA")?" Selected":""%>>BOLIVIA
<OPTION VALUE="BOSNIA"<%= bill_country.equals("BOSNIA")?" Selected":""%>>BOSNIA
<OPTION VALUE="BOTSWANA"<%= bill_country.equals("BOTSWANA")?" Selected":""%>>BOTSWANA
<OPTION VALUE="BRAZIL"<%= bill_country.equals("BRAZIL")?" Selected":""%>>BRAZIL
<OPTION VALUE="BRITISH VIRGIN ISLANDS"<%= bill_country.equals("BRITISH VIRGIN ISLANDS")?" Selected":""%>>BRITISH VIRGIN ISLANDS
<OPTION VALUE="BRUNEI"<%= bill_country.equals("BRUNEI")?" Selected":""%>>BRUNEI
<OPTION VALUE="BULGARIA"<%= bill_country.equals("BULGARIA")?" Selected":""%>>BULGARIA
<OPTION VALUE="BURKINA FASO"<%= bill_country.equals("BURKINA FASO")?" Selected":""%>>BURKINA FASO
<OPTION VALUE="BURMA"<%= bill_country.equals("BURMA")?" Selected":""%>>BURMA
<OPTION VALUE="BURUNDI"<%= bill_country.equals("BURUNDI")?" Selected":""%>>BURUNDI
<OPTION VALUE="CAMBODIA"<%= bill_country.equals("CAMBODIA")?" Selected":""%>>CAMBODIA
<OPTION VALUE="CAMEROON"<%= bill_country.equals("CAMEROON")?" Selected":""%>>CAMEROON
<OPTION VALUE="CANADA"<%= bill_country.equals("CANADA")?" Selected":""%>>CANADA
<OPTION VALUE="CAPE VERDE"<%= bill_country.equals("CAPE VERDE")?" Selected":""%>>CAPE VERDE
<OPTION VALUE="CAYMAN ISLANDS"<%= bill_country.equals("CAYMAN ISLANDS")?" Selected":""%>>CAYMAN ISLANDS
<OPTION VALUE="CENTRAL AFRICAN REPUBLIC"<%= bill_country.equals("CENTRAL AFRICAN REPUBLIC")?" Selected":""%>>CENTRAL AFRICAN REPUBLIC
<OPTION VALUE="CHAD"<%= bill_country.equals("CHAD")?" Selected":""%>>CHAD
<OPTION VALUE="CHILE"<%= bill_country.equals("CHILE")?" Selected":""%>>CHILE
<OPTION VALUE="CHINA PEOPLES REPUBLIC OF"<%= bill_country.equals("CHINA PEOPLES REPUBLIC OF")?" Selected":""%>>CHINA PEOPLES REPUBLIC OF
<OPTION VALUE="COLOMBIA"<%= bill_country.equals("COLOMBIA")?" Selected":""%>>COLOMBIA
<OPTION VALUE="COMOROS"<%= bill_country.equals("COMOROS")?" Selected":""%>>COMOROS
<OPTION VALUE="CONGO PEOPLES REPUBLIC"<%= bill_country.equals("CONGO PEOPLES REPUBLIC")?" Selected":""%>>CONGO PEOPLES REPUBLIC
<OPTION VALUE="COOK ISLANDS"<%= bill_country.equals("COOK ISLANDS")?" Selected":""%>>COOK ISLANDS
<OPTION VALUE="COSTA RICA"<%= bill_country.equals("COSTA RICA")?" Selected":""%>>COSTA RICA
<OPTION VALUE="CROATIA"<%= bill_country.equals("CROATIA")?" Selected":""%>>CROATIA
<OPTION VALUE="CUBA"<%= bill_country.equals("CUBA")?" Selected":""%>>CUBA
<OPTION VALUE="CYPRUS"<%= bill_country.equals("CYPRUS")?" Selected":""%>>CYPRUS
<OPTION VALUE="CZECH REPUBLIC"<%= bill_country.equals("CZECH REPUBLIC")?" Selected":""%>>CZECH REPUBLIC
<OPTION VALUE="DENMARK"<%= bill_country.equals("DENMARK")?" Selected":""%>>DENMARK
<OPTION VALUE="DJIBOUTI"<%= bill_country.equals("DJIBOUTI")?" Selected":""%>>DJIBOUTI
<OPTION VALUE="DOMINICA"<%= bill_country.equals("DOMINICA")?" Selected":""%>>DOMINICA
<OPTION VALUE="DOMINICAN REPUBLIC"<%= bill_country.equals("DOMINICAN REPUBLI")?" Selected":""%>>DOMINICAN REPUBLIC
<OPTION VALUE="EAST TIMOR"<%= bill_country.equals("EAST TIMOR")?" Selected":""%>>EAST TIMOR
<OPTION VALUE="ECUADOR"<%= bill_country.equals("ECUADOR")?" Selected":""%>>ECUADOR
<OPTION VALUE="EGYPT"<%= bill_country.equals("EGYPT")?" Selected":""%>>EGYPT
<OPTION VALUE="EL SALVADOR"<%= bill_country.equals("EL SALVADOR")?" Selected":""%>>EL SALVADOR
<OPTION VALUE="ENGLAND"<%= bill_country.equals("ENGLAND")?" Selected":""%>>ENGLAND
<OPTION VALUE="EQUATORIAL GUINEA"<%= bill_country.equals("EQUATORIAL GUINEA")?" Selected":""%>>EQUATORIAL GUINEA
<OPTION VALUE="ERITREA"<%= bill_country.equals("ERITREA")?" Selected":""%>>ERITREA
<OPTION VALUE="ESTONIA"<%= bill_country.equals("ESTONIA")?" Selected":""%>>ESTONIA
<OPTION VALUE="ETHIOPIA"<%= bill_country.equals("AFGHANISTAN")?" Selected":""%>>ETHIOPIA
<OPTION VALUE="FALKLAND ISLANDS"<%= bill_country.equals("FALKLAND ISLANDS")?" Selected":""%>>FALKLAND ISLANDS
<OPTION VALUE="FAROE ISLANDS"<%= bill_country.equals("FAROE ISLANDS")?" Selected":""%>>FAROE ISLANDS
<OPTION VALUE="FIJI"<%= bill_country.equals("FIJI")?" Selected":""%>>FIJI
<OPTION VALUE="FINLAND"<%= bill_country.equals("FINLAND")?" Selected":""%>>FINLAND
<OPTION VALUE="FRANCE"<%= bill_country.equals("FRANCE")?" Selected":""%>>FRANCE
<OPTION VALUE="FRENCH GUIANA"<%= bill_country.equals("FRENCH GUIANA")?" Selected":""%>>FRENCH GUIANA
<OPTION VALUE="FRENCH POLYNESIA"<%= bill_country.equals("FRENCH POLYNESIA")?" Selected":""%>>FRENCH POLYNESIA
<OPTION VALUE="GABON"<%= bill_country.equals("GABON")?" Selected":""%>>GABON
<OPTION VALUE="GAMBIA"<%= bill_country.equals("GAMBIA")?" Selected":""%>>GAMBIA
<OPTION VALUE="GEORGIA"<%= bill_country.equals("GEORGIA")?" Selected":""%>>GEORGIA
<OPTION VALUE="GERMANY"<%= bill_country.equals("GERMANY")?" Selected":""%>>GERMANY
<OPTION VALUE="GIBRALTAR"<%= bill_country.equals("GIBRALTAR")?" Selected":""%>>GIBRALTAR
<OPTION VALUE="GREECE"<%= bill_country.equals("GREECE")?" Selected":""%>>GREECE
<OPTION VALUE="GREENLAND"<%= bill_country.equals("GREENLAND")?" Selected":""%>>GREENLAND
<OPTION VALUE="GRENADA"<%= bill_country.equals("GRENADA")?" Selected":""%>>GRENADA
<OPTION VALUE="GUADELOUPE"<%= bill_country.equals("GUADELOUPE")?" Selected":""%>>GUADELOUPE
<OPTION VALUE="GUAM"<%= bill_country.equals("GUAM")?" Selected":""%>>GUAM
<OPTION VALUE="GUATEMALA"<%= bill_country.equals("GUATEMALA")?" Selected":""%>>GUATEMALA
<OPTION VALUE="GUERNSEY"<%= bill_country.equals("GUERNSEY")?" Selected":""%>>GUERNSEY
<OPTION VALUE="GUINEA"<%= bill_country.equals("GUINEA")?" Selected":""%>>GUINEA
<OPTION VALUE="GUINEA BISSAU"<%= bill_country.equals("GUINEA BISSAU")?" Selected":""%>>GUINEA BISSAU
<OPTION VALUE="GUYANA"<%= bill_country.equals("GUYANA")?" Selected":""%>>GUYANA
<OPTION VALUE="HAITI"<%= bill_country.equals("HAITI")?" Selected":""%>>HAITI
<OPTION VALUE="HONDURAS"<%= bill_country.equals("HONDURAS")?" Selected":""%>>HONDURAS
<OPTION VALUE="HONG KONG"<%= bill_country.equals("HONG KONG")?" Selected":""%>>HONG KONG
<OPTION VALUE="HUNGARY"<%= bill_country.equals("HUNGARY")?" Selected":""%>>HUNGARY
<OPTION VALUE="ICELAND"<%= bill_country.equals("ICELAND")?" Selected":""%>>ICELAND
<OPTION VALUE="INDIA"<%= bill_country.equals("INDIA")?" Selected":""%>>INDIA
<OPTION VALUE="INDONESIA"<%= bill_country.equals("INDONESIA")?" Selected":""%>>INDONESIA
<OPTION VALUE="IRAN"<%= bill_country.equals("IRAN")?" Selected":""%>>IRAN
<OPTION VALUE="IRAQ"<%= bill_country.equals("IRAQ")?" Selected":""%>>IRAQ
<OPTION VALUE="IRELAND"<%= bill_country.equals("IRELAND")?" Selected":""%>>IRELAND
<OPTION VALUE="ISRAEL"<%= bill_country.equals("ISRAEL")?" Selected":""%>>ISRAEL
<OPTION VALUE="ITALY"<%= bill_country.equals("ITALY")?" Selected":""%>>ITALY
<OPTION VALUE="JAMAICA"<%= bill_country.equals("JAMAICA")?" Selected":""%>>JAMAICA
<OPTION VALUE="JAPAN"<%= bill_country.equals("JAPAN")?" Selected":""%>>JAPAN
<OPTION VALUE="JERSEY"<%= bill_country.equals("JERSEY")?" Selected":""%>>JERSEY
<OPTION VALUE="JORDAN"<%= bill_country.equals("JORDAN")?" Selected":""%>>JORDAN
<OPTION VALUE="KAZAKHSTAN"<%= bill_country.equals("KAZAKHSTAN")?" Selected":""%>>KAZAKHSTAN
<OPTION VALUE="KENYA"<%= bill_country.equals("KENYA")?" Selected":""%>>KENYA
<OPTION VALUE="KIRIBATI"<%= bill_country.equals("KIRIBATI")?" Selected":""%>>KIRIBATI
<OPTION VALUE="KOREA NORTH"<%= bill_country.equals("KOREA NORTH")?" Selected":""%>>KOREA NORTH
<OPTION VALUE="KOREA SOUTH"<%= bill_country.equals("KOREA SOUTH")?" Selected":""%>>KOREA SOUTH
<OPTION VALUE="KUWAIT"<%= bill_country.equals("KUWAIT")?" Selected":""%>>KUWAIT
<OPTION VALUE="KYRGHYZSTAN"<%= bill_country.equals("KYRGHYZSTAN")?" Selected":""%>>KYRGHYZSTAN
<OPTION VALUE="LAOS"<%= bill_country.equals("LAOS")?" Selected":""%>>LAOS
<OPTION VALUE="LATVIA"<%= bill_country.equals("LATVIA")?" Selected":""%>>LATVIA
<OPTION VALUE="LEBANON"<%= bill_country.equals("LEBANON")?" Selected":""%>>LEBANON
<OPTION VALUE="LESOTHO"<%= bill_country.equals("LESOTHO")?" Selected":""%>>LESOTHO
<OPTION VALUE="LIBERIA"<%= bill_country.equals("LIBERIA")?" Selected":""%>>LIBERIA
<OPTION VALUE="LIBYA"<%= bill_country.equals("LIBYA")?" Selected":""%>>LIBYA
<OPTION VALUE="LIECHTENSTEIN"<%= bill_country.equals("LIECHTENSTEIN")?" Selected":""%>>LIECHTENSTEIN
<OPTION VALUE="LITHUANIA"<%= bill_country.equals("LITHUANIA")?" Selected":""%>>LITHUANIA
<OPTION VALUE="LUXEMBOURG"<%= bill_country.equals("LUXEMBOURG")?" Selected":""%>>LUXEMBOURG
<OPTION VALUE="MACAU"<%= bill_country.equals("MACAU")?" Selected":""%>>MACAU
<OPTION VALUE="MACEDONIA"<%= bill_country.equals("MACEDONIA")?" Selected":""%>>MACEDONIA
<OPTION VALUE="MADAGASCAR"<%= bill_country.equals("MADAGASCAR")?" Selected":""%>>MADAGASCAR
<OPTION VALUE="MALAWI"<%= bill_country.equals("MALAWI")?" Selected":""%>>MALAWI
<OPTION VALUE="MALAYSIA"<%= bill_country.equals("MALAYSIA")?" Selected":""%>>MALAYSIA
<OPTION VALUE="MALDIVES"<%= bill_country.equals("MALDIVES")?" Selected":""%>>MALDIVES
<OPTION VALUE="MALI"<%= bill_country.equals("MALI")?" Selected":""%>>MALI
<OPTION VALUE="MALTA"<%= bill_country.equals("MALTA")?" Selected":""%>>MALTA
<OPTION VALUE="MARSHALL ISLANDS"<%= bill_country.equals("MARSHALL ISLANDS")?" Selected":""%>>MARSHALL ISLANDS
<OPTION VALUE="MARTINIQUE"<%= bill_country.equals("MARTINIQUE")?" Selected":""%>>MARTINIQUE
<OPTION VALUE="MAURITANIA"<%= bill_country.equals("MAURITANIA")?" Selected":""%>>MAURITANIA
<OPTION VALUE="MAURITIUS"<%= bill_country.equals("MAURITIUS")?" Selected":""%>>MAURITIUS
<OPTION VALUE="MEXICO"<%= bill_country.equals("MEXICO")?" Selected":""%>>MEXICO
<OPTION VALUE="MICRONESIA FEDERATED STATES"<%= bill_country.equals("MICRONESIA FEDERATED STATES")?" Selected":""%>>MICRONESIA FEDERATED STATES
<OPTION VALUE="MOLDOVA"<%= bill_country.equals("MOLDOVA")?" Selected":""%>>MOLDOVA
<OPTION VALUE="MONACO"<%= bill_country.equals("MONACO")?" Selected":""%>>MONACO
<OPTION VALUE="MONGOLIAN PEOPLES REP"<%= bill_country.equals("MONGOLIAN PEOPLES REP")?" Selected":""%>>MONGOLIAN PEOPLES REP
<OPTION VALUE="MONTSERRAT"<%= bill_country.equals("MONTSERRAT")?" Selected":""%>>MONTSERRAT
<OPTION VALUE="MOROCCO"<%= bill_country.equals("MOROCCO")?" Selected":""%>>MOROCCO
<OPTION VALUE="MOZAMBIQUE"<%= bill_country.equals("MOZAMBIQUE")?" Selected":""%>>MOZAMBIQUE
<OPTION VALUE="NAMIBIA"<%= bill_country.equals("NAMIBIA")?" Selected":""%>>NAMIBIA
<OPTION VALUE="NAURU"<%= bill_country.equals("NAURU")?" Selected":""%>>NAURU
<OPTION VALUE="NEPAL"<%= bill_country.equals("NEPAL")?" Selected":""%>>NEPAL
<OPTION VALUE="NETHERLANDS"<%= bill_country.equals("NETHERLANDS")?" Selected":""%>>NETHERLANDS
<OPTION VALUE="NETHERLANDS ANTILLES"<%= bill_country.equals("NETHERLANDS ANTILLES")?" Selected":""%>>NETHERLANDS ANTILLES
<OPTION VALUE="NEW CALEDONIA"<%= bill_country.equals("NEW CALEDONIA")?" Selected":""%>>NEW CALEDONIA
<OPTION VALUE="NEW ZEALAND"<%= bill_country.equals("NEW ZEALAND")?" Selected":""%>>NEW ZEALAND
<OPTION VALUE="NICARAGUA"<%= bill_country.equals("NICARAGUA")?" Selected":""%>>NICARAGUA
<OPTION VALUE="NIGER"<%= bill_country.equals("NIGER")?" Selected":""%>>NIGER

<OPTION VALUE="NORFOLK ISLANDS"<%= bill_country.equals("NORFOLK ISLANDS")?" Selected":""%>>NORFOLK ISLANDS
<OPTION VALUE="NORTHERN MARIANA ISLANDS"<%= bill_country.equals("NORTHERN MARIANA ISLANDS")?" Selected":""%>>NORTHERN MARIANA ISLANDS
<OPTION VALUE="NORWAY"<%= bill_country.equals("NORWAY")?" Selected":""%>>NORWAY
<OPTION VALUE="OMAN"<%= bill_country.equals("OMAN")?" Selected":""%>>OMAN
<OPTION VALUE="PAKISTAN"<%= bill_country.equals("PAKISTAN")?" Selected":""%>>PAKISTAN
<OPTION VALUE="PALAU"<%= bill_country.equals("PALAU")?" Selected":""%>>PALAU
<OPTION VALUE="PANAMA"<%= bill_country.equals("PANAMA")?" Selected":""%>>PANAMA
<OPTION VALUE="PAPUA NEW GUINEA"<%= bill_country.equals("PAPUA NEW GUINEA")?" Selected":""%>>PAPUA NEW GUINEA
<OPTION VALUE="PARAGUAY"<%= bill_country.equals("PARAGUAY")?" Selected":""%>>PARAGUAY
<OPTION VALUE="PERU"<%= bill_country.equals("PERU")?" Selected":""%>>PERU
<OPTION VALUE="PHILIPPINES"<%= bill_country.equals("PHILIPPINES")?" Selected":""%>>PHILIPPINES
<OPTION VALUE="PITCAIRN ISLANDS"<%= bill_country.equals("PITCAIRN ISLANDS")?" Selected":""%>>PITCAIRN ISLANDS
<OPTION VALUE="POLAND"<%= bill_country.equals("POLAND")?" Selected":""%>>POLAND
<OPTION VALUE="PORTUGAL"<%= bill_country.equals("PORTUGAL")?" Selected":""%>>PORTUGAL
<OPTION VALUE="PUERTO RICO"<%= bill_country.equals("PUERTO RICO")?" Selected":""%>>PUERTO RICO
<OPTION VALUE="QATAR"<%= bill_country.equals("QATAR")?" Selected":""%>>QATAR
<OPTION VALUE="REUNION"<%= bill_country.equals("REUNION")?" Selected":""%>>REUNION
<OPTION VALUE="ROMANIA"<%= bill_country.equals("ROMANIA")?" Selected":""%>>ROMANIA
<OPTION VALUE="RUSSIA"<%= bill_country.equals("RUSSIA")?" Selected":""%>>RUSSIA
<OPTION VALUE="RWANDA"<%= bill_country.equals("RWANDA")?" Selected":""%>>RWANDA
<OPTION VALUE="SAO TOME PRINCIPE"<%= bill_country.equals("SAO TOME PRINCIPE")?" Selected":""%>>SAO TOME PRINCIPE
<OPTION VALUE="SAUDI ARABIA"<%= bill_country.equals("SAUDI ARABIA")?" Selected":""%>>SAUDI ARABIA
<OPTION VALUE="SENEGAL"<%= bill_country.equals("SENEGAL")?" Selected":""%>>SENEGAL
<OPTION VALUE="SERBIA"<%= bill_country.equals("SERBIA")?" Selected":""%>>SERBIA
<OPTION VALUE="SEYCHELLES"<%= bill_country.equals("SEYCHELLES")?" Selected":""%>>SEYCHELLES
<OPTION VALUE="SIERRA LEONE"<%= bill_country.equals("SIERRA LEONE")?" Selected":""%>>SIERRA LEONE
<OPTION VALUE="SINGAPORE"<%= bill_country.equals("SINGAPORE")?" Selected":""%>>SINGAPORE
<OPTION VALUE="SLOVAK REPUBLIC"<%= bill_country.equals("SLOVAK REPUBLIC")?" Selected":""%>>SLOVAK REPUBLIC
<OPTION VALUE="SLOVENIA"<%= bill_country.equals("SLOVENIA")?" Selected":""%>>SLOVENIA
<OPTION VALUE="SOLOMON ISLANDS"<%= bill_country.equals("SOLOMON ISLANDS")?" Selected":""%>>SOLOMON ISLANDS
<OPTION VALUE="SOMALIA"<%= bill_country.equals("SOMALIA")?" Selected":""%>>SOMALIA
<OPTION VALUE="SOUTH AFRICA"<%= bill_country.equals("SOUTH AFRICA")?" Selected":""%>>SOUTH AFRICA
<OPTION VALUE="SPAIN"<%= bill_country.equals("SPAIN")?" Selected":""%>>SPAIN
<OPTION VALUE="SRI LANKA"<%= bill_country.equals("SRI LANKA")?" Selected":""%>>SRI LANKA
<OPTION VALUE="ST HELENA"<%= bill_country.equals("ST HELENA")?" Selected":""%>>ST HELENA
<OPTION VALUE="ST KITTS"<%= bill_country.equals("ST KITTS")?" Selected":""%>>ST KITTS
<OPTION VALUE="ST LUCIA"<%= bill_country.equals("ST LUCIA")?" Selected":""%>>ST LUCIA
<OPTION VALUE="ST PIERRE AND MIQUELON"<%= bill_country.equals("ST PIERRE AND MIQUELON")?" Selected":""%>>ST PIERRE AND MIQUELON
<OPTION VALUE="ST VINCENT GRENADINES"<%= bill_country.equals("ST VINCENT GRENADINES")?" Selected":""%>>ST VINCENT GRENADINES
<OPTION VALUE="SUDAN"<%= bill_country.equals("SUDAN")?" Selected":""%>>SUDAN
<OPTION VALUE="SURINAME"<%= bill_country.equals("SURINAME")?" Selected":""%>>SURINAME
<OPTION VALUE="SWAZILAND"<%= bill_country.equals("SWAZILAND")?" Selected":""%>>SWAZILAND
<OPTION VALUE="SWEDEN"<%= bill_country.equals("SWEDEN")?" Selected":""%>>SWEDEN
<OPTION VALUE="SWITZERLAND"<%= bill_country.equals("SWITZERLAND")?" Selected":""%>>SWITZERLAND
<OPTION VALUE="SYRIA"<%= bill_country.equals("SYRIA")?" Selected":""%>>SYRIA
<OPTION VALUE="TAIWAN"<%= bill_country.equals("TAIWAN")?" Selected":""%>>TAIWAN
<OPTION VALUE="TAJIKISTAN"<%= bill_country.equals("TAJIKISTAN")?" Selected":""%>>TAJIKISTAN
<OPTION VALUE="TANZANIA"<%= bill_country.equals("TANZANIA")?" Selected":""%>>TANZANIA
<OPTION VALUE="THAILAND"<%= bill_country.equals("THAILAND")?" Selected":""%>>THAILAND
<OPTION VALUE="TOGO"<%= bill_country.equals("TOGO")?" Selected":""%>>TOGO
<OPTION VALUE="TONGA"<%= bill_country.equals("TONGA")?" Selected":""%>>TONGA
<OPTION VALUE="TRINIDAD TOBAGO"<%= bill_country.equals("TRINIDAD TOBAGO")?" Selected":""%>>TRINIDAD TOBAGO
<OPTION VALUE="TUNISIA"<%= bill_country.equals("TUNISIA")?" Selected":""%>>TUNISIA
<OPTION VALUE="TURKEY"<%= bill_country.equals("TURKEY")?" Selected":""%>>TURKEY
<OPTION VALUE="TURKMENISTAN"<%= bill_country.equals("TURKMENISTAN")?" Selected":""%>>TURKMENISTAN
<OPTION VALUE="TURKS CAICOS ISLANDS"<%= bill_country.equals("TURKS CAICOS ISLANDS")?" Selected":""%>>TURKS CAICOS ISLANDS
<OPTION VALUE="TUVALU"<%= bill_country.equals("TUVALU")?" Selected":""%>>TUVALU
<OPTION VALUE="UGANDA"<%= bill_country.equals("UGANDA")?" Selected":""%>>UGANDA
<OPTION VALUE="UKRAINE"<%= bill_country.equals("UKRAINE")?" Selected":""%>>UKRAINE
<OPTION VALUE="UNITED ARAB EMIRATES"<%= bill_country.equals("UNITED ARAB EMIRATES")?" Selected":""%>>UNITED ARAB EMIRATES
<OPTION VALUE="UNITED KINGDOM"<%= bill_country.equals("UNITED KINGDOM")?" Selected":""%>>UNITED KINGDOM
<OPTION VALUE="URUGUAY"<%= bill_country.equals("URUGUAY")?" Selected":""%>>URUGUAY
<OPTION VALUE="US VIRGIN ISLANDS"<%= bill_country.equals("US VIRGIN ISLANDS")?" Selected":""%>>US VIRGIN ISLANDS
<% if(bill_country.equals(""))  {%>
<OPTION VALUE="USA" SELECTED>USA
<%}else{%>
 <OPTION VALUE="USA"<%= bill_country.equals("USA")?" Selected":""%> >USA
<%}%>
<OPTION VALUE="UZBEKISTAN"<%= bill_country.equals("UZBEKISTAN")?" Selected":""%>>UZBEKISTAN
<OPTION VALUE="VANUATU"<%= bill_country.equals("VANUATU")?" Selected":""%>>VANUATU
<OPTION VALUE="VATICAN CITY STATE"<%= bill_country.equals("VATICAN CITY STATE")?" Selected":""%>>VATICAN CITY STATE
<OPTION VALUE="VENEZUELA"<%= bill_country.equals("VENEZUELA")?" Selected":""%>>VENEZUELA
<OPTION VALUE="VIETNAM"<%= bill_country.equals("VIETNAM")?" Selected":""%>>VIETNAM
<OPTION VALUE="VIETNAM NORTH SOUTH"<%= bill_country.equals("VIETNAM NORTH SOUTH")?" Selected":""%>>VIETNAM NORTH SOUTH
<OPTION VALUE="WAKE ISLAND"<%= bill_country.equals("WAKE ISLAND")?" Selected":""%>>WAKE ISLAND
<OPTION VALUE="WALLIS FUTUNA ISLANDS"<%= bill_country.equals("WALLIS FUTUNA ISLANDS")?" Selected":""%>>WALLIS FUTUNA ISLANDS
<OPTION VALUE="WESTERN SAMOA"<%= bill_country.equals("WESTERN SAMOA")?" Selected":""%>>WESTERN SAMOA
<OPTION VALUE="ZAMBIA"<%= bill_country.equals("ZAMBIA")?" Selected":""%>>ZAMBIA
<OPTION VALUE="ZIMBABWE RHODESIA"<%= bill_country.equals("ZIMBABWE RHODESIA")?" Selected":""%>>ZIMBABWE RHODESIA
</SELECT>
</TD></TR>
<TR><TD></TD><TD></TD></TR>
<TR><TD></TD><TD><font face="Geneva, Verdana, Helvetica" size="-1">Please enter your BlackBerry's email address below. This will allow you to quickly download the Tazzle companion software.</TD></TR>
<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">BlackBerry Email:</TD><TD><INPUT TYPE=TEXT SIZE=31 NAME=bill_email  VALUE="<%=order.getBillingContact().email%>" ></TD></TR>

<TR>
<TD ALIGN=RIGHT COLSPAN=2><BR>
</TD></TR>
<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Credit Card Number:</TD>
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
<OPTION VALUE=2009>2009
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
<BR><font face="Geneva, Verdana, Helvetica" size="-1">PROMO&nbsp;CODE:
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
<font face="Geneva, Verdana, Helvetica" size="-1">If you have a PROMO CODE, enter it here. Otherwise, leave this entry blank.
</TD>
</TR>
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
	<OPTION VALUE="AB"<%= (dom_ship_state.equals("AB")?" SELECTED":"") %>>Alberta
	<OPTION VALUE="AS"<%= (dom_ship_state.equals("AS")?" SELECTED":"") %>>American Samoa
	<OPTION VALUE="AZ"<%= (dom_ship_state.equals("AZ")?" SELECTED":"") %>>Arizona
	<OPTION VALUE="AR"<%= (dom_ship_state.equals("AR")?" SELECTED":"") %>>Arkansas
	<OPTION VALUE="AA"<%= (dom_ship_state.equals("AA")?" SELECTED":"") %>>Armed Forces - USA/Canada
	<OPTION VALUE="AE"<%= (dom_ship_state.equals("AE")?" SELECTED":"") %>>Armed Forces - Europe
	<OPTION VALUE="AP"<%= (dom_ship_state.equals("AP")?" SELECTED":"") %>>Armed Forces - Pacific
	<OPTION VALUE="BC"<%= (dom_ship_state.equals("BC")?" SELECTED":"") %>>British Columbia
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
	<OPTION VALUE="MB"<%= (dom_ship_state.equals("MB")?" SELECTED":"") %>>Manitoba
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
	<OPTION VALUE="NB"<%= (dom_ship_state.equals("NB")?" SELECTED":"") %>>New Brunswick
	<OPTION VALUE="NH"<%= (dom_ship_state.equals("NH")?" SELECTED":"") %>>New Hampshire
	<OPTION VALUE="NJ"<%= (dom_ship_state.equals("NJ")?" SELECTED":"") %>>New Jersey
	<OPTION VALUE="NM"<%= (dom_ship_state.equals("NM")?" SELECTED":"") %>>New Mexico
	<OPTION VALUE="NY"<%= (dom_ship_state.equals("NY")?" SELECTED":"") %>>New York
	<OPTION VALUE="NF"<%= (dom_ship_state.equals("NF")?" SELECTED":"") %>>Newfoundland
	<OPTION VALUE="NC"<%= (dom_ship_state.equals("NC")?" SELECTED":"") %>>North Carolina
	<OPTION VALUE="ND"<%= (dom_ship_state.equals("ND")?" SELECTED":"") %>>North Dakota
	<OPTION VALUE="MP"<%= (dom_ship_state.equals("MP")?" SELECTED":"") %>>Northern Mariana Island
	<OPTION VALUE="NT"<%= (dom_ship_state.equals("NT")?" SELECTED":"") %>>Northwest Territories
	<OPTION VALUE="NS"<%= (dom_ship_state.equals("NS")?" SELECTED":"") %>>Nova Scotia
	<OPTION VALUE="OH"<%= (dom_ship_state.equals("OH")?" SELECTED":"") %>>Ohio
	<OPTION VALUE="OK"<%= (dom_ship_state.equals("OK")?" SELECTED":"") %>>Oklahoma
	<OPTION VALUE="ON"<%= (dom_ship_state.equals("ON")?" SELECTED":"") %>>Ontario
	<OPTION VALUE="OR"<%= (dom_ship_state.equals("OR")?" SELECTED":"") %>>Oregon
	<OPTION VALUE="PW"<%= (dom_ship_state.equals("PW")?" SELECTED":"") %>>Palau Island
	<OPTION VALUE="PA"<%= (dom_ship_state.equals("PA")?" SELECTED":"") %>>Pennsylvania
	<OPTION VALUE="PE"<%= (dom_ship_state.equals("PE")?" SELECTED":"") %>>Prince Edward Island
	<OPTION VALUE="PR"<%= (dom_ship_state.equals("PR")?" SELECTED":"") %>>Puerto Rico
	<OPTION VALUE="QC"<%= (dom_ship_state.equals("QC")?" SELECTED":"") %>>Quebec
	<OPTION VALUE="RI"<%= (dom_ship_state.equals("RI")?" SELECTED":"") %>>Rhode Island
	<OPTION VALUE="SK"<%= (dom_ship_state.equals("SK")?" SELECTED":"") %>>Saskatchewan
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
	<OPTION VALUE="WY"<%= (dom_ship_state.equals("WY")?" SELECTED":"") %>>Wyoming
	<OPTION VALUE="YT"<%= (dom_ship_state.equals("YT")?" SELECTED":"") %>>Yukon Territory</SELECT><BR><INPUT TYPE=TEXT SIZE=26 NAME="intl_ship_state" VALUE="<%=intl_ship_state%>" ><BR>
<font face="Geneva, Verdana, Helvetica" size="-1">If outside the US/Canada, please enter the state or region here, otherwise leave this entry blank.</TD></TR></TABLE>
</TD></TR>

<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Zip/Postal&nbsp;Code: </TD><TD align="left"><INPUT TYPE=TEXT SIZE=13 NAME=ship_zip  VALUE="<%=order.getShippingAddress().zip%>"></TD></TR>
<TR>
<TD ALIGN=RIGHT>
<font face="Geneva, Verdana, Helvetica" size="-1">Country:
</TD><TD ><SELECT NAME="ship_country">

<OPTION VALUE="AFGHANISTAN"<%= ship_country.equals("AFGHANISTAN")?" Selected":""%>>AFGHANISTAN
<OPTION VALUE="ALBANIA" <%= ship_country.equals("ALBANIA")?" Selected":""%>>ALBANIA
<OPTION VALUE="ALGERIA"<%= ship_country.equals("ALGERIA")?" Selected":""%>>ALGERIA
<OPTION VALUE="AMERICAN SAMOA"<%= ship_country.equals("AMERICAN SAMOA")?" Selected":""%>>AMERICAN SAMOA
<OPTION VALUE="ANDORRA"<%= ship_country.equals("ANDORRA")?" Selected":""%>>ANDORRA
<OPTION VALUE="ANGOLA"<%= ship_country.equals("ANGOLA")?" Selected":""%>>ANGOLA
<OPTION VALUE="ANGUILLA ISLANDS"<%= ship_country.equals("ANGUILLA ISLANDS")?" Selected":""%>>ANGUILLA ISLANDS
<OPTION VALUE="ANTIGUA"<%= ship_country.equals("ANTIGUA")?" Selected":""%>>ANTIGUA
<OPTION VALUE="ARGENTINA"<%= ship_country.equals("ARGENTINA")?" Selected":""%>>ARGENTINA
<OPTION VALUE="ARMENIA"<%= ship_country.equals("ARMENIA")?" Selected":""%>>ARMENIA
<OPTION VALUE="ARUBA"<%= ship_country.equals("ARUBA")?" Selected":""%>>ARUBA
<OPTION VALUE="AUSTRALIA"<%= ship_country.equals("AUSTRALIA")?" Selected":""%>>AUSTRALIA
<OPTION VALUE="AUSTRIA"<%= ship_country.equals("AUSTRIA")?" Selected":""%>>AUSTRIA
<OPTION VALUE="AZERBAIJAN"<%= ship_country.equals("AZERBAIJAN")?" Selected":""%>>AZERBAIJAN
<OPTION VALUE="BAHAMAS"<%= ship_country.equals("BAHAMAS")?" Selected":""%>>BAHAMAS
<OPTION VALUE="BAHRAIN"<%= ship_country.equals("BAHRAIN")?" Selected":""%>>BAHRAIN
<OPTION VALUE="BANGLADESH"<%= ship_country.equals("BANGLADESH")?" Selected":""%>>BANGLADESH
<OPTION VALUE="BARBADOS"<%= ship_country.equals("BARBADOS")?" Selected":""%>>BARBADOS
<OPTION VALUE="BELARUS"<%= ship_country.equals("BELARUS")?" Selected":""%>>BELARUS
<OPTION VALUE="BELGIUM"<%= ship_country.equals("BELGIUM")?" Selected":""%>>BELGIUM
<OPTION VALUE="BELIZE"<%= ship_country.equals("BELIZE")?" Selected":""%>>BELIZE
<OPTION VALUE="BENIN"<%= ship_country.equals("BENIN")?" Selected":""%>>BENIN
<OPTION VALUE="BERMUDA"<%= ship_country.equals("BERMUDA")?" Selected":""%>>BERMUDA
<OPTION VALUE="BHUTAN"<%= ship_country.equals("BHUTAN")?" Selected":""%>>BHUTAN
<OPTION VALUE="BOLIVIA"<%= ship_country.equals("BOLIVIA")?" Selected":""%>>BOLIVIA
<OPTION VALUE="BOSNIA"<%= ship_country.equals("BOSNIA")?" Selected":""%>>BOSNIA
<OPTION VALUE="BOTSWANA"<%= ship_country.equals("BOTSWANA")?" Selected":""%>>BOTSWANA
<OPTION VALUE="BRAZIL"<%= ship_country.equals("BRAZIL")?" Selected":""%>>BRAZIL
<OPTION VALUE="BRITISH VIRGIN ISLANDS"<%= ship_country.equals("BRITISH VIRGIN ISLANDS")?" Selected":""%>>BRITISH VIRGIN ISLANDS
<OPTION VALUE="BRUNEI"<%= ship_country.equals("BRUNEI")?" Selected":""%>>BRUNEI
<OPTION VALUE="BULGARIA"<%= ship_country.equals("BULGARIA")?" Selected":""%>>BULGARIA
<OPTION VALUE="BURKINA FASO"<%= ship_country.equals("BURKINA FASO")?" Selected":""%>>BURKINA FASO
<OPTION VALUE="BURMA"<%= ship_country.equals("BURMA")?" Selected":""%>>BURMA
<OPTION VALUE="BURUNDI"<%= ship_country.equals("BURUNDI")?" Selected":""%>>BURUNDI
<OPTION VALUE="CAMBODIA"<%= ship_country.equals("CAMBODIA")?" Selected":""%>>CAMBODIA
<OPTION VALUE="CAMEROON"<%= ship_country.equals("CAMEROON")?" Selected":""%>>CAMEROON
<OPTION VALUE="CANADA"<%= ship_country.equals("CANADA")?" Selected":""%>>CANADA
<OPTION VALUE="CAPE VERDE"<%= ship_country.equals("CAPE VERDE")?" Selected":""%>>CAPE VERDE
<OPTION VALUE="CAYMAN ISLANDS"<%= ship_country.equals("CAYMAN ISLANDS")?" Selected":""%>>CAYMAN ISLANDS
<OPTION VALUE="CENTRAL AFRICAN REPUBLIC"<%= ship_country.equals("CENTRAL AFRICAN REPUBLIC")?" Selected":""%>>CENTRAL AFRICAN REPUBLIC
<OPTION VALUE="CHAD"<%= ship_country.equals("CHAD")?" Selected":""%>>CHAD
<OPTION VALUE="CHILE"<%= ship_country.equals("CHILE")?" Selected":""%>>CHILE
<OPTION VALUE="CHINA PEOPLES REPUBLIC OF"<%= ship_country.equals("CHINA PEOPLES REPUBLIC OF")?" Selected":""%>>CHINA PEOPLES REPUBLIC OF
<OPTION VALUE="COLOMBIA"<%= ship_country.equals("COLOMBIA")?" Selected":""%>>COLOMBIA
<OPTION VALUE="COMOROS"<%= ship_country.equals("COMOROS")?" Selected":""%>>COMOROS
<OPTION VALUE="CONGO PEOPLES REPUBLIC"<%= ship_country.equals("CONGO PEOPLES REPUBLIC")?" Selected":""%>>CONGO PEOPLES REPUBLIC
<OPTION VALUE="COOK ISLANDS"<%= ship_country.equals("COOK ISLANDS")?" Selected":""%>>COOK ISLANDS
<OPTION VALUE="COSTA RICA"<%= ship_country.equals("COSTA RICA")?" Selected":""%>>COSTA RICA
<OPTION VALUE="CROATIA"<%= ship_country.equals("CROATIA")?" Selected":""%>>CROATIA
<OPTION VALUE="CUBA"<%= ship_country.equals("CUBA")?" Selected":""%>>CUBA
<OPTION VALUE="CYPRUS"<%= ship_country.equals("CYPRUS")?" Selected":""%>>CYPRUS
<OPTION VALUE="CZECH REPUBLIC"<%= ship_country.equals("CZECH REPUBLIC")?" Selected":""%>>CZECH REPUBLIC
<OPTION VALUE="DENMARK"<%= ship_country.equals("DENMARK")?" Selected":""%>>DENMARK
<OPTION VALUE="DJIBOUTI"<%= ship_country.equals("DJIBOUTI")?" Selected":""%>>DJIBOUTI
<OPTION VALUE="DOMINICA"<%= ship_country.equals("DOMINICA")?" Selected":""%>>DOMINICA
<OPTION VALUE="DOMINICAN REPUBLIC"<%= ship_country.equals("DOMINICAN REPUBLI")?" Selected":""%>>DOMINICAN REPUBLIC
<OPTION VALUE="EAST TIMOR"<%= ship_country.equals("EAST TIMOR")?" Selected":""%>>EAST TIMOR
<OPTION VALUE="ECUADOR"<%= ship_country.equals("ECUADOR")?" Selected":""%>>ECUADOR
<OPTION VALUE="EGYPT"<%= ship_country.equals("EGYPT")?" Selected":""%>>EGYPT
<OPTION VALUE="EL SALVADOR"<%= ship_country.equals("EL SALVADOR")?" Selected":""%>>EL SALVADOR
<OPTION VALUE="ENGLAND"<%= ship_country.equals("ENGLAND")?" Selected":""%>>ENGLAND
<OPTION VALUE="EQUATORIAL GUINEA"<%= ship_country.equals("EQUATORIAL GUINEA")?" Selected":""%>>EQUATORIAL GUINEA
<OPTION VALUE="ERITREA"<%= ship_country.equals("ERITREA")?" Selected":""%>>ERITREA
<OPTION VALUE="ESTONIA"<%= ship_country.equals("ESTONIA")?" Selected":""%>>ESTONIA
<OPTION VALUE="ETHIOPIA"<%= ship_country.equals("AFGHANISTAN")?" Selected":""%>>ETHIOPIA
<OPTION VALUE="FALKLAND ISLANDS"<%= ship_country.equals("FALKLAND ISLANDS")?" Selected":""%>>FALKLAND ISLANDS
<OPTION VALUE="FAROE ISLANDS"<%= ship_country.equals("FAROE ISLANDS")?" Selected":""%>>FAROE ISLANDS
<OPTION VALUE="FIJI"<%= ship_country.equals("FIJI")?" Selected":""%>>FIJI
<OPTION VALUE="FINLAND"<%= ship_country.equals("FINLAND")?" Selected":""%>>FINLAND
<OPTION VALUE="FRANCE"<%= ship_country.equals("FRANCE")?" Selected":""%>>FRANCE
<OPTION VALUE="FRENCH GUIANA"<%= ship_country.equals("FRENCH GUIANA")?" Selected":""%>>FRENCH GUIANA
<OPTION VALUE="FRENCH POLYNESIA"<%= ship_country.equals("FRENCH POLYNESIA")?" Selected":""%>>FRENCH POLYNESIA
<OPTION VALUE="GABON"<%= ship_country.equals("GABON")?" Selected":""%>>GABON
<OPTION VALUE="GAMBIA"<%= ship_country.equals("GAMBIA")?" Selected":""%>>GAMBIA
<OPTION VALUE="GEORGIA"<%= ship_country.equals("GEORGIA")?" Selected":""%>>GEORGIA
<OPTION VALUE="GERMANY"<%= ship_country.equals("GERMANY")?" Selected":""%>>GERMANY
<OPTION VALUE="GIBRALTAR"<%= ship_country.equals("GIBRALTAR")?" Selected":""%>>GIBRALTAR
<OPTION VALUE="GREECE"<%= ship_country.equals("GREECE")?" Selected":""%>>GREECE
<OPTION VALUE="GREENLAND"<%= ship_country.equals("GREENLAND")?" Selected":""%>>GREENLAND
<OPTION VALUE="GRENADA"<%= ship_country.equals("GRENADA")?" Selected":""%>>GRENADA
<OPTION VALUE="GUADELOUPE"<%= ship_country.equals("GUADELOUPE")?" Selected":""%>>GUADELOUPE
<OPTION VALUE="GUAM"<%= ship_country.equals("GUAM")?" Selected":""%>>GUAM
<OPTION VALUE="GUATEMALA"<%= ship_country.equals("GUATEMALA")?" Selected":""%>>GUATEMALA
<OPTION VALUE="GUINEA"<%= ship_country.equals("GUINEA")?" Selected":""%>>GUINEA
<OPTION VALUE="GUINEA BISSAU"<%= ship_country.equals("GUINEA BISSAU")?" Selected":""%>>GUINEA BISSAU
<OPTION VALUE="GUYANA"<%= ship_country.equals("GUYANA")?" Selected":""%>>GUYANA
<OPTION VALUE="HAITI"<%= ship_country.equals("HAITI")?" Selected":""%>>HAITI
<OPTION VALUE="HONDURAS"<%= ship_country.equals("HONDURAS")?" Selected":""%>>HONDURAS
<OPTION VALUE="HONG KONG"<%= ship_country.equals("HONG KONG")?" Selected":""%>>HONG KONG
<OPTION VALUE="HUNGARY"<%= ship_country.equals("HUNGARY")?" Selected":""%>>HUNGARY
<OPTION VALUE="ICELAND"<%= ship_country.equals("ICELAND")?" Selected":""%>>ICELAND
<OPTION VALUE="INDIA"<%= ship_country.equals("INDIA")?" Selected":""%>>INDIA
<OPTION VALUE="INDONESIA"<%= ship_country.equals("INDONESIA")?" Selected":""%>>INDONESIA
<OPTION VALUE="IRAN"<%= ship_country.equals("IRAN")?" Selected":""%>>IRAN
<OPTION VALUE="IRAQ"<%= ship_country.equals("IRAQ")?" Selected":""%>>IRAQ
<OPTION VALUE="IRELAND"<%= ship_country.equals("IRELAND")?" Selected":""%>>IRELAND
<OPTION VALUE="ISRAEL"<%= ship_country.equals("ISRAEL")?" Selected":""%>>ISRAEL
<OPTION VALUE="ITALY"<%= ship_country.equals("ITALY")?" Selected":""%>>ITALY
<OPTION VALUE="JAMAICA"<%= ship_country.equals("JAMAICA")?" Selected":""%>>JAMAICA
<OPTION VALUE="JAPAN"<%= ship_country.equals("JAPAN")?" Selected":""%>>JAPAN
<OPTION VALUE="JORDAN"<%= ship_country.equals("JORDAN")?" Selected":""%>>JORDAN
<OPTION VALUE="KAZAKHSTAN"<%= ship_country.equals("KAZAKHSTAN")?" Selected":""%>>KAZAKHSTAN
<OPTION VALUE="KENYA"<%= ship_country.equals("KENYA")?" Selected":""%>>KENYA
<OPTION VALUE="KIRIBATI"<%= ship_country.equals("KIRIBATI")?" Selected":""%>>KIRIBATI
<OPTION VALUE="KOREA NORTH"<%= ship_country.equals("KOREA NORTH")?" Selected":""%>>KOREA NORTH
<OPTION VALUE="KOREA SOUTH"<%= ship_country.equals("KOREA SOUTH")?" Selected":""%>>KOREA SOUTH
<OPTION VALUE="KUWAIT"<%= ship_country.equals("KUWAIT")?" Selected":""%>>KUWAIT
<OPTION VALUE="KYRGHYZSTAN"<%= ship_country.equals("KYRGHYZSTAN")?" Selected":""%>>KYRGHYZSTAN
<OPTION VALUE="LAOS"<%= ship_country.equals("LAOS")?" Selected":""%>>LAOS
<OPTION VALUE="LATVIA"<%= ship_country.equals("LATVIA")?" Selected":""%>>LATVIA
<OPTION VALUE="LEBANON"<%= ship_country.equals("LEBANON")?" Selected":""%>>LEBANON
<OPTION VALUE="LESOTHO"<%= ship_country.equals("LESOTHO")?" Selected":""%>>LESOTHO
<OPTION VALUE="LIBERIA"<%= ship_country.equals("LIBERIA")?" Selected":""%>>LIBERIA
<OPTION VALUE="LIBYA"<%= ship_country.equals("LIBYA")?" Selected":""%>>LIBYA
<OPTION VALUE="LIECHTENSTEIN"<%= ship_country.equals("LIECHTENSTEIN")?" Selected":""%>>LIECHTENSTEIN
<OPTION VALUE="LITHUANIA"<%= ship_country.equals("LITHUANIA")?" Selected":""%>>LITHUANIA
<OPTION VALUE="LUXEMBOURG"<%= ship_country.equals("LUXEMBOURG")?" Selected":""%>>LUXEMBOURG
<OPTION VALUE="MACAU"<%= ship_country.equals("MACAU")?" Selected":""%>>MACAU
<OPTION VALUE="MACEDONIA"<%= ship_country.equals("MACEDONIA")?" Selected":""%>>MACEDONIA
<OPTION VALUE="MADAGASCAR"<%= ship_country.equals("MADAGASCAR")?" Selected":""%>>MADAGASCAR
<OPTION VALUE="MALAWI"<%= ship_country.equals("MALAWI")?" Selected":""%>>MALAWI
<OPTION VALUE="MALAYSIA"<%= ship_country.equals("MALAYSIA")?" Selected":""%>>MALAYSIA
<OPTION VALUE="MALDIVES"<%= ship_country.equals("MALDIVES")?" Selected":""%>>MALDIVES
<OPTION VALUE="MALI"<%= ship_country.equals("MALI")?" Selected":""%>>MALI
<OPTION VALUE="MALTA"<%= ship_country.equals("MALTA")?" Selected":""%>>MALTA
<OPTION VALUE="MARSHALL ISLANDS"<%= ship_country.equals("MARSHALL ISLANDS")?" Selected":""%>>MARSHALL ISLANDS
<OPTION VALUE="MARTINIQUE"<%= ship_country.equals("MARTINIQUE")?" Selected":""%>>MARTINIQUE
<OPTION VALUE="MAURITANIA"<%= ship_country.equals("MAURITANIA")?" Selected":""%>>MAURITANIA
<OPTION VALUE="MAURITIUS"<%= ship_country.equals("MAURITIUS")?" Selected":""%>>MAURITIUS
<OPTION VALUE="MEXICO"<%= ship_country.equals("MEXICO")?" Selected":""%>>MEXICO
<OPTION VALUE="MICRONESIA FEDERATED STATES"<%= ship_country.equals("MICRONESIA FEDERATED STATES")?" Selected":""%>>MICRONESIA FEDERATED STATES
<OPTION VALUE="MOLDOVA"<%= ship_country.equals("MOLDOVA")?" Selected":""%>>MOLDOVA
<OPTION VALUE="MONACO"<%= ship_country.equals("MONACO")?" Selected":""%>>MONACO
<OPTION VALUE="MONGOLIAN PEOPLES REP"<%= ship_country.equals("MONGOLIAN PEOPLES REP")?" Selected":""%>>MONGOLIAN PEOPLES REP
<OPTION VALUE="MONTSERRAT"<%= ship_country.equals("MONTSERRAT")?" Selected":""%>>MONTSERRAT
<OPTION VALUE="MOROCCO"<%= ship_country.equals("MOROCCO")?" Selected":""%>>MOROCCO
<OPTION VALUE="MOZAMBIQUE"<%= ship_country.equals("MOZAMBIQUE")?" Selected":""%>>MOZAMBIQUE
<OPTION VALUE="NAMIBIA"<%= ship_country.equals("NAMIBIA")?" Selected":""%>>NAMIBIA
<OPTION VALUE="NAURU"<%= ship_country.equals("NAURU")?" Selected":""%>>NAURU
<OPTION VALUE="NEPAL"<%= ship_country.equals("NEPAL")?" Selected":""%>>NEPAL
<OPTION VALUE="NETHERLANDS"<%= ship_country.equals("NETHERLANDS")?" Selected":""%>>NETHERLANDS
<OPTION VALUE="NETHERLANDS ANTILLES"<%= ship_country.equals("NETHERLANDS ANTILLES")?" Selected":""%>>NETHERLANDS ANTILLES
<OPTION VALUE="NEW CALEDONIA"<%= ship_country.equals("NEW CALEDONIA")?" Selected":""%>>NEW CALEDONIA
<OPTION VALUE="NEW ZEALAND"<%= ship_country.equals("NEW ZEALAND")?" Selected":""%>>NEW ZEALAND
<OPTION VALUE="NICARAGUA"<%= ship_country.equals("NICARAGUA")?" Selected":""%>>NICARAGUA
<OPTION VALUE="NIGER"<%= ship_country.equals("NIGER")?" Selected":""%>>NIGER

<OPTION VALUE="NORFOLK ISLANDS"<%= ship_country.equals("NORFOLK ISLANDS")?" Selected":""%>>NORFOLK ISLANDS
<OPTION VALUE="NORTHERN MARIANA ISLANDS"<%= ship_country.equals("NORTHERN MARIANA ISLANDS")?" Selected":""%>>NORTHERN MARIANA ISLANDS
<OPTION VALUE="NORWAY"<%= ship_country.equals("NORWAY")?" Selected":""%>>NORWAY
<OPTION VALUE="OMAN"<%= ship_country.equals("OMAN")?" Selected":""%>>OMAN
<OPTION VALUE="PAKISTAN"<%= ship_country.equals("PAKISTAN")?" Selected":""%>>PAKISTAN
<OPTION VALUE="PALAU"<%= ship_country.equals("PALAU")?" Selected":""%>>PALAU
<OPTION VALUE="PANAMA"<%= ship_country.equals("PANAMA")?" Selected":""%>>PANAMA
<OPTION VALUE="PAPUA NEW GUINEA"<%= ship_country.equals("PAPUA NEW GUINEA")?" Selected":""%>>PAPUA NEW GUINEA
<OPTION VALUE="PARAGUAY"<%= ship_country.equals("PARAGUAY")?" Selected":""%>>PARAGUAY
<OPTION VALUE="PERU"<%= ship_country.equals("PERU")?" Selected":""%>>PERU
<OPTION VALUE="PHILIPPINES"<%= ship_country.equals("PHILIPPINES")?" Selected":""%>>PHILIPPINES
<OPTION VALUE="PITCAIRN ISLANDS"<%= ship_country.equals("PITCAIRN ISLANDS")?" Selected":""%>>PITCAIRN ISLANDS
<OPTION VALUE="POLAND"<%= ship_country.equals("POLAND")?" Selected":""%>>POLAND
<OPTION VALUE="PORTUGAL"<%= ship_country.equals("PORTUGAL")?" Selected":""%>>PORTUGAL
<OPTION VALUE="PUERTO RICO"<%= ship_country.equals("PUERTO RICO")?" Selected":""%>>PUERTO RICO
<OPTION VALUE="QATAR"<%= ship_country.equals("QATAR")?" Selected":""%>>QATAR
<OPTION VALUE="REUNION"<%= ship_country.equals("REUNION")?" Selected":""%>>REUNION
<OPTION VALUE="ROMANIA"<%= ship_country.equals("ROMANIA")?" Selected":""%>>ROMANIA
<OPTION VALUE="RUSSIA"<%= ship_country.equals("RUSSIA")?" Selected":""%>>RUSSIA
<OPTION VALUE="RWANDA"<%= ship_country.equals("RWANDA")?" Selected":""%>>RWANDA
<OPTION VALUE="SAO TOME PRINCIPE"<%= ship_country.equals("SAO TOME PRINCIPE")?" Selected":""%>>SAO TOME PRINCIPE
<OPTION VALUE="SAUDI ARABIA"<%= ship_country.equals("SAUDI ARABIA")?" Selected":""%>>SAUDI ARABIA
<OPTION VALUE="SENEGAL"<%= ship_country.equals("SENEGAL")?" Selected":""%>>SENEGAL
<OPTION VALUE="SERBIA"<%= ship_country.equals("SERBIA")?" Selected":""%>>SERBIA
<OPTION VALUE="SEYCHELLES"<%= ship_country.equals("SEYCHELLES")?" Selected":""%>>SEYCHELLES
<OPTION VALUE="SIERRA LEONE"<%= ship_country.equals("SIERRA LEONE")?" Selected":""%>>SIERRA LEONE
<OPTION VALUE="SINGAPORE"<%= ship_country.equals("SINGAPORE")?" Selected":""%>>SINGAPORE
<OPTION VALUE="SLOVAK REPUBLIC"<%= ship_country.equals("SLOVAK REPUBLIC")?" Selected":""%>>SLOVAK REPUBLIC
<OPTION VALUE="SLOVENIA"<%= ship_country.equals("SLOVENIA")?" Selected":""%>>SLOVENIA
<OPTION VALUE="SOLOMON ISLANDS"<%= ship_country.equals("SOLOMON ISLANDS")?" Selected":""%>>SOLOMON ISLANDS
<OPTION VALUE="SOMALIA"<%= ship_country.equals("SOMALIA")?" Selected":""%>>SOMALIA
<OPTION VALUE="SOUTH AFRICA"<%= ship_country.equals("SOUTH AFRICA")?" Selected":""%>>SOUTH AFRICA
<OPTION VALUE="SPAIN"<%= ship_country.equals("SPAIN")?" Selected":""%>>SPAIN
<OPTION VALUE="SRI LANKA"<%= ship_country.equals("SRI LANKA")?" Selected":""%>>SRI LANKA
<OPTION VALUE="ST HELENA"<%= ship_country.equals("ST HELENA")?" Selected":""%>>ST HELENA
<OPTION VALUE="ST KITTS"<%= ship_country.equals("ST KITTS")?" Selected":""%>>ST KITTS
<OPTION VALUE="ST LUCIA"<%= ship_country.equals("ST LUCIA")?" Selected":""%>>ST LUCIA
<OPTION VALUE="ST PIERRE AND MIQUELON"<%= ship_country.equals("ST PIERRE AND MIQUELON")?" Selected":""%>>ST PIERRE AND MIQUELON
<OPTION VALUE="ST VINCENT GRENADINES"<%= ship_country.equals("ST VINCENT GRENADINES")?" Selected":""%>>ST VINCENT GRENADINES
<OPTION VALUE="SUDAN"<%= ship_country.equals("SUDAN")?" Selected":""%>>SUDAN
<OPTION VALUE="SURINAME"<%= ship_country.equals("SURINAME")?" Selected":""%>>SURINAME
<OPTION VALUE="SWAZILAND"<%= ship_country.equals("SWAZILAND")?" Selected":""%>>SWAZILAND
<OPTION VALUE="SWEDEN"<%= ship_country.equals("SWEDEN")?" Selected":""%>>SWEDEN
<OPTION VALUE="SWITZERLAND"<%= ship_country.equals("SWITZERLAND")?" Selected":""%>>SWITZERLAND
<OPTION VALUE="SYRIA"<%= ship_country.equals("SYRIA")?" Selected":""%>>SYRIA
<OPTION VALUE="TAIWAN"<%= ship_country.equals("TAIWAN")?" Selected":""%>>TAIWAN
<OPTION VALUE="TAJIKISTAN"<%= ship_country.equals("TAJIKISTAN")?" Selected":""%>>TAJIKISTAN
<OPTION VALUE="TANZANIA"<%= ship_country.equals("TANZANIA")?" Selected":""%>>TANZANIA
<OPTION VALUE="THAILAND"<%= ship_country.equals("THAILAND")?" Selected":""%>>THAILAND
<OPTION VALUE="TOGO"<%= ship_country.equals("TOGO")?" Selected":""%>>TOGO
<OPTION VALUE="TONGA"<%= ship_country.equals("TONGA")?" Selected":""%>>TONGA
<OPTION VALUE="TRINIDAD TOBAGO"<%= ship_country.equals("TRINIDAD TOBAGO")?" Selected":""%>>TRINIDAD TOBAGO
<OPTION VALUE="TUNISIA"<%= ship_country.equals("TUNISIA")?" Selected":""%>>TUNISIA
<OPTION VALUE="TURKEY"<%= ship_country.equals("TURKEY")?" Selected":""%>>TURKEY
<OPTION VALUE="TURKMENISTAN"<%= ship_country.equals("TURKMENISTAN")?" Selected":""%>>TURKMENISTAN
<OPTION VALUE="TURKS CAICOS ISLANDS"<%= ship_country.equals("TURKS CAICOS ISLANDS")?" Selected":""%>>TURKS CAICOS ISLANDS
<OPTION VALUE="TUVALU"<%= ship_country.equals("TUVALU")?" Selected":""%>>TUVALU
<OPTION VALUE="UGANDA"<%= ship_country.equals("UGANDA")?" Selected":""%>>UGANDA
<OPTION VALUE="UKRAINE"<%= ship_country.equals("UKRAINE")?" Selected":""%>>UKRAINE
<OPTION VALUE="UNITED ARAB EMIRATES"<%= ship_country.equals("UNITED ARAB EMIRATES")?" Selected":""%>>UNITED ARAB EMIRATES
<OPTION VALUE="UNITED KINGDOM"<%= ship_country.equals("UNITED KINGDOM")?" Selected":""%>>UNITED KINGDOM
<OPTION VALUE="URUGUAY"<%= ship_country.equals("URUGUAY")?" Selected":""%>>URUGUAY
<OPTION VALUE="US VIRGIN ISLANDS"<%= ship_country.equals("US VIRGIN ISLANDS")?" Selected":""%>>US VIRGIN ISLANDS
<% if(ship_country.equals(""))  {%>
<OPTION VALUE="USA" SELECTED>USA
<%}else{%>
 <OPTION VALUE="USA"<%= ship_country.equals("USA")?" Selected":""%> >USA
<%}%>
<OPTION VALUE="UZBEKISTAN"<%= ship_country.equals("UZBEKISTAN")?" Selected":""%>>UZBEKISTAN
<OPTION VALUE="VANUATU"<%= ship_country.equals("VANUATU")?" Selected":""%>>VANUATU
<OPTION VALUE="VATICAN CITY STATE"<%= ship_country.equals("VATICAN CITY STATE")?" Selected":""%>>VATICAN CITY STATE
<OPTION VALUE="VENEZUELA"<%= ship_country.equals("VENEZUELA")?" Selected":""%>>VENEZUELA
<OPTION VALUE="VIETNAM"<%= ship_country.equals("VIETNAM")?" Selected":""%>>VIETNAM
<OPTION VALUE="VIETNAM NORTH SOUTH"<%= ship_country.equals("VIETNAM NORTH SOUTH")?" Selected":""%>>VIETNAM NORTH SOUTH
<OPTION VALUE="WAKE ISLAND"<%= ship_country.equals("WAKE ISLAND")?" Selected":""%>>WAKE ISLAND
<OPTION VALUE="WALLIS FUTUNA ISLANDS"<%= ship_country.equals("WALLIS FUTUNA ISLANDS")?" Selected":""%>>WALLIS FUTUNA ISLANDS
<OPTION VALUE="WESTERN SAMOA"<%= ship_country.equals("WESTERN SAMOA")?" Selected":""%>>WESTERN SAMOA
<OPTION VALUE="ZAMBIA"<%= ship_country.equals("ZAMBIA")?" Selected":""%>>ZAMBIA
<OPTION VALUE="ZIMBABWE RHODESIA"<%= ship_country.equals("ZIMBABWE RHODESIA")?" Selected":""%>>ZIMBABWE RHODESIA
</SELECT>
</TD></TR>
 <TR><TD></TD><TD>
  </TD></TR>
<%--
<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Email Address:</TD><TD><INPUT TYPE=TEXT SIZE=31 NAME=ship_email  VALUE="<%=order.getShippingContact().email%>" ></TD></TR>


<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Phone:</TD><TD><INPUT TYPE=TEXT SIZE=31 NAME=ship_phone_num  VALUE="<%=order.getShippingContact().phone%>" ></TD></TR>
--%>
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
  <INPUT TYPE=HIDDEN NAME=cd VALUE=429><INPUT TYPE=HIDDEN NAME=do VALUE=5><P><INPUT TYPE=SUBMIT NAME="Complete" VALUE="Complete Order ->">

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