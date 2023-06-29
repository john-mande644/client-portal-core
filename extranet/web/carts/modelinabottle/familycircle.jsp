<%@ page import="com.owd.core.business.order.Coupon" %>
<%
	com.owd.web.carts.modelbottle.ModelBottleCart cart = (com.owd.web.carts.modelbottle.ModelBottleCart) request.getSession(true).getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartKey+"familycircle");

    if(cart==null) { cart = new com.owd.web.carts.modelbottle.ModelBottleCart("316"); request.getSession(true).setAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartKey+"familycircle",cart); }

    com.owd.core.business.order.Order order = cart.getOrder();
    if(order.skuList.size()<1)
    {
        System.out.println("Adding bottle!");
        order.addLineItem("SSF","1","","","","","");
    }

    order.recalculateBalance();
        //    System.out.println("test"+cart.getMethods());


    String dom_ship_state = (String) request.getSession(true).getAttribute("dom_ship_state");
	if(dom_ship_state == null) dom_ship_state = "";


%>
<jsp:include page="top.jsp"/><table border="0" cellpadding="0" cellspacing="0" width="100%">
<TR><TD width="5"> <BR> </TD><TD>
<table border="0" cellpadding="3" cellspacing="0" width="100%">

<%
      Coupon coupon = Coupon.dbloadByCoupon("familycirclefreebottle","316");
    if(coupon.used_count>=coupon.use_limit)
    {
     %>

    <tr>
  <td colspan=5 align=center><font size="+2" face="Geneva, Verdana, Helvetica">Promotional Offer for Family Circle Readers<br>Limited to first 250 requests!
  <br><font size=-1>Model in a Bottle and this site are not affiliated in any way with the Family Circle magazine or other Family Circle properties</font>
  </td>
</tr>
    <tr>
  <td colspan=5 align=right>
<HR color="#000000">
</td>
</tr>
    <tr>
     <td colspan=5 align=center><font size="+1" face="Geneva, Verdana, Helvetica">We're very sorry, but all 250 of the free bottles have been sent!<p>
 <HR color="#000000">Please visit our online store at <A HREF="http://www.modelinabottle.com/">http://www.modelinabottle.com/</A> and type in the coupon code CIRCLEREADER when entering an order to receive 20% off the price of your items!
         <p><br>

         <p><font size="+1" face="Geneva, Verdana, Helvetica">Thanks for visiting Model in a Bottle!


     </td>
   </tr>



    <%
    }   else
    {

     order.coupon_code="familycirclefreebottle";



    if (!("".equals(request.getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartError))))
{
%>
<TR><td colspan=5><font color="red" size="-1" face="Geneva, Verdana, Helvetica"><%= (null==request.getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartError))?"":request.getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartError) %><P> </font></TD></TR>
<%
}
%><tr>
  <td colspan=5 align=center><font size="+2" face="Geneva, Verdana, Helvetica">Promotional Offer for Family Circle Readers<br>Limited to first 250 requests!
  <br><font size=-1>Model in a Bottle and this site are not affiliated in any way with the Family Circle magazine or other Family Circle properties</font>
  </td>
</tr>
<tr>
  <td colspan=5 align=right>
<HR color="#000000">
</td>
</tr>
<tr VALIGN="BOTTOM">
  <TH ALIGN="LEFT" NOWRAP></TH>
  <TH ALIGN="LEFT" NOWRAP class="xxx"><font size="-1" face="Geneva, Verdana, Helvetica"><B>Product</B>&nbsp;</TH>
  <TH ALIGN="CENTER" NOWRAP class="xxx"><font size="-1" face="Geneva, Verdana, Helvetica">Quantity&nbsp;</TH>
  <TH ALIGN="RIGHT" NOWRAP class="xxx"><font size="-1" face="Geneva, Verdana, Helvetica">Total</TH>
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
		com.owd.core.business.order.LineItem item = (com.owd.core.business.order.LineItem)order.skuList.elementAt(i);
%>
<!--SoftCart.cart.product.Loopbegin-->

<tr VALIGN="TOP">
  <td ALIGN="LEFT"><BR></td>
  <td ALIGN="LEFT"><font size="-1" face="Geneva, Verdana, Helvetica"><%=item.description%></td>
  <td ALIGN="CENTER" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica"><%=item.quantity_request%></td>
  <td ALIGN="RIGHT" NOWRAP><font size="-1" face="Geneva, Verdana, Helvetica">No Charge!</td>
</tr>

<%
	}
%>
<tr>
  <td colspan=5 align=right>
<HR color="#000000">
</td>
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
<FORM METHOD="POST" ACTION="/carts/modelinabottle/store">
</TD></TR></TABLE>

<table border="0" cellpadding="0" cellspacing="0" >
<TR><TD width="50"> <BR> </TD><TD >
<TABLE border=0 width="100%" cellpadding=3 cellspacing=0  >

<tr>
  <td colspan=2 align=left>
<font size="-1" face="Geneva, Verdana, Helvetica"><B>Where should we send your free bottle of Skin Sensitive Formula?</B>
<HR color="#000000">
</td>
</tr>

<TR>
<TD ALIGN=RIGHT >
<BR><font face="Geneva, Verdana, Helvetica" size="-1">Addressee's&nbsp;Name:
</TD>
<TD>
<BR><INPUT TYPE=TEXT SIZE=31 NAME="shipName" VALUE="<%=order.getShippingContact().name%>">
</TD>
</TR>

<TR>
<TD ALIGN=RIGHT>
<font face="Geneva, Verdana, Helvetica" size="-1">Address&nbsp;Line&nbsp;1:
</TD><TD ><INPUT TYPE=TEXT SIZE=31 NAME=ship_address1 VALUE="<%=order.getShippingAddress().address_one%>">
</TD></TR>
<TR>
<TD ALIGN=RIGHT>
<font face="Geneva, Verdana, Helvetica" size="-1">Address&nbsp;Line&nbsp;2:
</TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT SIZE=31 NAME=ship_address2  VALUE="<%=order.getShippingAddress().address_two%>">
</TD>
</TR>
<TR><TD ALIGN=RIGHT ><font face="Geneva, Verdana, Helvetica" size="-1">City: </TD><TD ALIGN=left><INPUT TYPE=TEXT SIZE=13 NAME=ship_city  VALUE="<%=order.getShippingAddress().city%>"></TD></TR>
<TR>
<TD ALIGN=RIGHT VALIGN=TOP>
<font face="Geneva, Verdana, Helvetica" size="-1">US&nbsp;State:
</TD><TD ><TABLE BORDER="0"><TR><TD VALIGN=TOP><SELECT NAME="dom_ship_state" SIZE=1>
	<OPTION VALUE=""<%= (order.getShippingAddress().state.equals("")?" SELECTED":"") %>>Click to Select
	<OPTION VALUE="AL"<%= (order.getShippingAddress().state.equals("AL")?" SELECTED":"") %>>Alabama
	<OPTION VALUE="AK"<%= (order.getShippingAddress().state.equals("AK")?" SELECTED":"") %>>Alaska
	<OPTION VALUE="AB"<%= (order.getShippingAddress().state.equals("AB")?" SELECTED":"") %>>Alberta
	<OPTION VALUE="AZ"<%= (order.getShippingAddress().state.equals("AZ")?" SELECTED":"") %>>Arizona
	<OPTION VALUE="AR"<%= (order.getShippingAddress().state.equals("AR")?" SELECTED":"") %>>Arkansas
	<OPTION VALUE="AA"<%= (order.getShippingAddress().state.equals("AA")?" SELECTED":"") %>>Armed Forces - USA/Canada
	<OPTION VALUE="AE"<%= (order.getShippingAddress().state.equals("AE")?" SELECTED":"") %>>Armed Forces - Europe
	<OPTION VALUE="AP"<%= (order.getShippingAddress().state.equals("AP")?" SELECTED":"") %>>Armed Forces - Pacific
	<OPTION VALUE="CA"<%= (order.getShippingAddress().state.equals("CA")?" SELECTED":"") %>>California
	<OPTION VALUE="CO"<%= (order.getShippingAddress().state.equals("CO")?" SELECTED":"") %>>Colorado
	<OPTION VALUE="CT"<%= (order.getShippingAddress().state.equals("CT")?" SELECTED":"") %>>Connecticut
	<OPTION VALUE="DE"<%= (order.getShippingAddress().state.equals("DE")?" SELECTED":"") %>>Delaware
	<OPTION VALUE="DC"<%= (order.getShippingAddress().state.equals("DC")?" SELECTED":"") %>>District of Columbia
	<OPTION VALUE="FL"<%= (order.getShippingAddress().state.equals("FL")?" SELECTED":"") %>>Florida
	<OPTION VALUE="GA"<%= (order.getShippingAddress().state.equals("GA")?" SELECTED":"") %>>Georgia
	<OPTION VALUE="HI"<%= (order.getShippingAddress().state.equals("HI")?" SELECTED":"") %>>Hawaii
	<OPTION VALUE="ID"<%= (order.getShippingAddress().state.equals("ID")?" SELECTED":"") %>>Idaho
	<OPTION VALUE="IL"<%= (order.getShippingAddress().state.equals("IL")?" SELECTED":"") %>>Illinois
	<OPTION VALUE="IN"<%= (order.getShippingAddress().state.equals("IN")?" SELECTED":"") %>>Indiana
	<OPTION VALUE="IA"<%= (order.getShippingAddress().state.equals("IA")?" SELECTED":"") %>>Iowa
	<OPTION VALUE="KS"<%= (order.getShippingAddress().state.equals("KS")?" SELECTED":"") %>>Kansas
	<OPTION VALUE="KY"<%= (order.getShippingAddress().state.equals("KY")?" SELECTED":"") %>>Kentucky
	<OPTION VALUE="LA"<%= (order.getShippingAddress().state.equals("LA")?" SELECTED":"") %>>Louisiana
	<OPTION VALUE="ME"<%= (order.getShippingAddress().state.equals("ME")?" SELECTED":"") %>>Maine
	<OPTION VALUE="MD"<%= (order.getShippingAddress().state.equals("MD")?" SELECTED":"") %>>Maryland
	<OPTION VALUE="MA"<%= (order.getShippingAddress().state.equals("MA")?" SELECTED":"") %>>Massachusetts
	<OPTION VALUE="MI"<%= (order.getShippingAddress().state.equals("MI")?" SELECTED":"") %>>Michigan
	<OPTION VALUE="MN"<%= (order.getShippingAddress().state.equals("MN")?" SELECTED":"") %>>Minnesota
	<OPTION VALUE="MS"<%= (order.getShippingAddress().state.equals("MS")?" SELECTED":"") %>>Mississippi
	<OPTION VALUE="MO"<%= (order.getShippingAddress().state.equals("MO")?" SELECTED":"") %>>Missouri
	<OPTION VALUE="MT"<%= (order.getShippingAddress().state.equals("MT")?" SELECTED":"") %>>Montana
	<OPTION VALUE="NE"<%= (order.getShippingAddress().state.equals("NE")?" SELECTED":"") %>>Nebraska
	<OPTION VALUE="NV"<%= (order.getShippingAddress().state.equals("NV")?" SELECTED":"") %>>Nevada
	<OPTION VALUE="NH"<%= (order.getShippingAddress().state.equals("NH")?" SELECTED":"") %>>New Hampshire
	<OPTION VALUE="NJ"<%= (order.getShippingAddress().state.equals("NJ")?" SELECTED":"") %>>New Jersey
	<OPTION VALUE="NM"<%= (order.getShippingAddress().state.equals("NM")?" SELECTED":"") %>>New Mexico
	<OPTION VALUE="NY"<%= (order.getShippingAddress().state.equals("NY")?" SELECTED":"") %>>New York
	<OPTION VALUE="NC"<%= (order.getShippingAddress().state.equals("NC")?" SELECTED":"") %>>North Carolina
	<OPTION VALUE="ND"<%= (order.getShippingAddress().state.equals("ND")?" SELECTED":"") %>>North Dakota
	<OPTION VALUE="OH"<%= (order.getShippingAddress().state.equals("OH")?" SELECTED":"") %>>Ohio
	<OPTION VALUE="OK"<%= (order.getShippingAddress().state.equals("OK")?" SELECTED":"") %>>Oklahoma
	<OPTION VALUE="ON"<%= (order.getShippingAddress().state.equals("ON")?" SELECTED":"") %>>Ontario
	<OPTION VALUE="OR"<%= (order.getShippingAddress().state.equals("OR")?" SELECTED":"") %>>Oregon
	<OPTION VALUE="PA"<%= (order.getShippingAddress().state.equals("PA")?" SELECTED":"") %>>Pennsylvania
	<OPTION VALUE="RI"<%= (order.getShippingAddress().state.equals("RI")?" SELECTED":"") %>>Rhode Island
	<OPTION VALUE="SC"<%= (order.getShippingAddress().state.equals("SC")?" SELECTED":"") %>>South Carolina
	<OPTION VALUE="SD"<%= (order.getShippingAddress().state.equals("SD")?" SELECTED":"") %>>South Dakota
	<OPTION VALUE="TN"<%= (order.getShippingAddress().state.equals("TN")?" SELECTED":"") %>>Tennessee
	<OPTION VALUE="TX"<%= (order.getShippingAddress().state.equals("TX")?" SELECTED":"") %>>Texas
	<OPTION VALUE="UT"<%= (order.getShippingAddress().state.equals("UT")?" SELECTED":"") %>>Utah
	<OPTION VALUE="VT"<%= (order.getShippingAddress().state.equals("VT")?" SELECTED":"") %>>Vermont
	<OPTION VALUE="VA"<%= (order.getShippingAddress().state.equals("VA")?" SELECTED":"") %>>Virginia
	<OPTION VALUE="WA"<%= (order.getShippingAddress().state.equals("WA")?" SELECTED":"") %>>Washington
	<OPTION VALUE="WV"<%= (order.getShippingAddress().state.equals("WV")?" SELECTED":"") %>>West Virginia
	<OPTION VALUE="WI"<%= (order.getShippingAddress().state.equals("WI")?" SELECTED":"") %>>Wisconsin
	<OPTION VALUE="WY"<%= (order.getShippingAddress().state.equals("WY")?" SELECTED":"") %>>Wyoming</SELECT></TD></TR></TABLE>
</TD><TD></TD></TR>

<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Zip/Postal&nbsp;Code: </TD><TD align="left"><INPUT TYPE=TEXT SIZE=13 NAME=ship_zip  VALUE="<%=order.getShippingAddress().zip%>"></TD></TR>
<TR>
<TD ALIGN=RIGHT>
<font face="Geneva, Verdana, Helvetica" size="-1"><INPUT TYPE=HIDDEN NAME="ship_country" VALUE="USA">

</TD><TD ></TD></TR>

<TR><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size="-1">Email Address:</TD><TD><INPUT TYPE=TEXT SIZE=31 NAME=ship_email  VALUE="<%=order.getShippingContact().email%>" ></TD></TR>


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
  <INPUT TYPE=HIDDEN NAME=family VALUE=circle><INPUT TYPE=HIDDEN NAME=do VALUE=6><P><INPUT TYPE=SUBMIT NAME="Complete" VALUE="Continue Order ->">

</td>
</tr>
<tr>
  <td colspan=2 align=left>
<BR>
</td>
</tr>
</FORM>
<% } %>
</TABLE>
</TD></TR></TABLE>
<!-- END CART -->


<jsp:include page="bottom.jsp"/>