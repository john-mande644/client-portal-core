<%@ page import="com.owd.core.business.order.Coupon" %>
<%@ page import="com.owd.extranet.servlet.ExtranetManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %>

<%  
  com.owd.core.business.order.Coupon coupon = (com.owd.core.business.order.Coupon)request.getAttribute(com.owd.extranet.servlet.CouponManager.kCurrentCoupon);
  

%>
  <P>  
  <HR><fontx COLOR=RED><B><%= request.getAttribute("formerror")==null?"":request.getAttribute("formerror") %></B><BR>
  <TABLE border=0 width=100% cellpadding=5>
<FORM METHOD=POST ACTION=<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath %>?<%= com.owd.extranet.servlet.CouponManager.kParamCouponMgrAction %>=<%=com.owd.extranet.servlet.CouponManager.kParamCouponSaveAction%>>
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>" value="<%= ExtranetServlet.kParamChangeMgrAction %>" />
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>" value="<%= ExtranetManager.kCouponsMgrName %>" />

    <TR><TD COLSPAN=2 width="100%">
<HR><INPUT TYPE=HIDDEN NAME="<%= Coupon.kdbCouponPKName %>" VALUE="<%= coupon.coupon_id %>">
<INPUT TYPE=SUBMIT NAME="<%= com.owd.extranet.servlet.CouponManager.kParamCouponMgrSubAction %>" VALUE="<%= (coupon.isNew()?"Save New Coupon":"Save Changes") %>">
<HR>
</TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Campaign/Group:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Coupon.kdbCouponName %>" VALUE="<%= coupon.coupon_name %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Enter a name for the promotional campaign or coupon group you want this coupon to be grouped under. This field is searchable and available for reports.
</TD>

</TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Issued To:&nbsp;</B></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME="<%= Coupon.kdbCouponUser %>" VALUE="<%= coupon.coupon_user %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Enter a name for the receiver of this coupon. For example, if this is a credit to a customer, you might use the customer name or order reference. If this coupon is given to a third party as part of a marketing agreement, you might put the third party name here. This field is searchable and available for reports.
</TD>

</TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Coupon/Discount&nbsp;Code:&nbsp;</B></TD><TD ALIGN=LEFT><SELECT NAME="<%= coupon.kdbCouponMatchType %>" >
<OPTION VALUE="0" <%= (0==(coupon.code_match_type)?"SELECTED":"") %>>is
<OPTION VALUE="2" <%= (2==(coupon.code_match_type)?"SELECTED":"") %>>starts with
<OPTION VALUE="3" <%= (3==(coupon.code_match_type)?"SELECTED":"") %>>ends with
<OPTION VALUE="1" <%= (1==(coupon.code_match_type)?"SELECTED":"") %>>contains
</SELECT>
<INPUT TYPE=TEXT NAME="<%= Coupon.kdbCouponCode %>" VALUE="<%= coupon.coupon_code %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Enter the actual text string to accept as this coupon code and the type of matching to perform when validating the coupon. The actual coupon value will be part of the order record. You can use the start with, ends with or contains options to allow other parties to generate groups of valid coupon codes with distinct prefixes and/or suffixes.<BR>For example, a code of "12345" with the "begins with" option selected would match submitted coupon values of "12345" or "12345-ABC".<BR><B>Coupon matches are case-insensitive ('a' is the same as 'A').</B>
</TD>

</TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Require&nbsp;SKU&nbsp;Match:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=CHECKBOX NAME="<%= coupon.kdbCouponSKUMatch %>" VALUE="1" <%= (1==(coupon.sku_match_flag)?"CHECKED":"") %>>&nbsp;<SELECT NAME="<%= coupon.kdbCouponSKUMatchType %>" >
<OPTION VALUE="0" <%= (0==(coupon.sku_match_type)?"SELECTED":"") %>>is
<OPTION VALUE="2" <%= (2==(coupon.sku_match_type)?"SELECTED":"") %>>starts with
<OPTION VALUE="3" <%= (3==(coupon.sku_match_type)?"SELECTED":"") %>>ends with
<OPTION VALUE="1" <%= (1==(coupon.sku_match_type)?"SELECTED":"") %>>contains
</SELECT>
<INPUT TYPE=TEXT NAME="<%= Coupon.kdbCouponSKU %>" VALUE="<%= coupon.sku_value %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Check the checkboxes above to require that a matching SKU be present in an order for this coupon code to work. Then enter the actual text string to accept as this SKU code and the type of matching to perform when validating the coupon. The actual coupon value will be part of the order record. You can use the start with, ends with or contains options to allow matching groups of SKUs with distinct, shared prefixes and/or suffixes.<BR>For example, a required SKU value of "12345" with the "begins with" option selected would match submitted orders with SKUs "12345" or "12345-ABC".<BR><B>SKU matches are case-insensitive ('a' is the same as 'A').</B>
</TD>

</TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Valid&nbsp;Ship&nbsp;Countries:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=CHECKBOX NAME="<%= coupon.kdbCouponUSGood %>" VALUE="1" <%= (1==(coupon.us_good)?"CHECKED":"") %>>&nbsp;USA<BR>
<INPUT TYPE=CHECKBOX NAME="<%= coupon.kdbCouponCanadaGood %>" VALUE="1" <%= (1==(coupon.canada_good)?"CHECKED":"") %>>Canada<BR>
<INPUT TYPE=CHECKBOX NAME="<%= coupon.kdbCouponIntlGood %>" VALUE="1" <%= (1==(coupon.intl_good)?"CHECKED":"") %>>Other International<BR> 
<fontx SIZE=-2>Uncheck the checkboxes above to restrict this coupon to use with orders shipping to the designated countries. For example, if you wanted a coupon to only be valid for shipping to Canada and the US, but nowhere else, you would check the USA and Canada checkboxes and uncheck the Other International checkbox.<BR><B>Unchecking all three boxes will create a coupon that will not work at all.</B>
</TD>

</TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Minimum&nbsp;Order&nbsp;Subtotal:&nbsp;</B></TD><TD ALIGN=LEFT><INPUT TYPE=TEXT NAME="<%= Coupon.kdbCouponMinSubtotal %>" VALUE="<%= coupon.min_subtotal %>" MAXLENGTH=20 SIZE=20><BR>
<fontx SIZE=-2>Enter a value here to set a minimum order subtotal for this coupon. For example, if you entered 9.99, any order with a product subtotal less than $10.00 would not accept this coupon. Leave the value at 0.00 to allow all orders to use this coupon.<BR>
<B>Only use a decimal number here - no $ characters or commas. To enter $1,500.00, for example, you would type in "1500.00" (without the quotes).</B>
</TD>

</TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Expires:&nbsp;</B></TD><TD ALIGN=LEFT>
<SELECT NAME=<%= coupon.kExpiresYearValue %>>
<OPTION VALUE=2001 <%= (2001==(coupon.getExpiresYear())?"SELECTED":"") %>>2001
<OPTION VALUE=2002 <%= (2002==(coupon.getExpiresYear())?"SELECTED":"") %>>2002
<OPTION VALUE=2003 <%= (2003==(coupon.getExpiresYear())?"SELECTED":"") %>>2003
<OPTION VALUE=2004 <%= (2004==(coupon.getExpiresYear())?"SELECTED":"") %>>2004
<OPTION VALUE=2005 <%= (2005==(coupon.getExpiresYear())?"SELECTED":"") %>>2005
<OPTION VALUE=2006 <%= (2006==(coupon.getExpiresYear())?"SELECTED":"") %>>2006
<OPTION VALUE=2007 <%= (2007==(coupon.getExpiresYear())?"SELECTED":"") %>>2007
<OPTION VALUE=2008 <%= (2008==(coupon.getExpiresYear())?"SELECTED":"") %>>2008
<OPTION VALUE=2009 <%= (2009==(coupon.getExpiresYear())?"SELECTED":"") %>>2009
<OPTION VALUE=2010 <%= (2010==(coupon.getExpiresYear())?"SELECTED":"") %>>2010
<OPTION VALUE=2011 <%= (2011==(coupon.getExpiresYear())?"SELECTED":"") %>>2011
<OPTION VALUE=2012 <%= (2012==(coupon.getExpiresYear())?"SELECTED":"") %>>2012
<OPTION VALUE=2013 <%= (2013==(coupon.getExpiresYear())?"SELECTED":"") %>>2013
<OPTION VALUE=2014 <%= (2014==(coupon.getExpiresYear())?"SELECTED":"") %>>2014
<OPTION VALUE=2015 <%= (2015==(coupon.getExpiresYear())?"SELECTED":"") %>>2015
<OPTION VALUE=2016 <%= (2016==(coupon.getExpiresYear())?"SELECTED":"") %>>2016
</SELECT>
<SELECT NAME=<%= coupon.kExpiresMonthValue %>>
<OPTION VALUE=1 <%= (1==(coupon.getExpiresMonth())?"SELECTED":"") %>>January
<OPTION VALUE=2 <%= (2==(coupon.getExpiresMonth())?"SELECTED":"") %>>February
<OPTION VALUE=3 <%= (3==(coupon.getExpiresMonth())?"SELECTED":"") %>>March
<OPTION VALUE=4 <%= (4==(coupon.getExpiresMonth())?"SELECTED":"") %>>April
<OPTION VALUE=5 <%= (5==(coupon.getExpiresMonth())?"SELECTED":"") %>>May
<OPTION VALUE=6 <%= (6==(coupon.getExpiresMonth())?"SELECTED":"") %>>June
<OPTION VALUE=7 <%= (7==(coupon.getExpiresMonth())?"SELECTED":"") %>>July
<OPTION VALUE=8 <%= (8==(coupon.getExpiresMonth())?"SELECTED":"") %>>August
<OPTION VALUE=9 <%= (9==(coupon.getExpiresMonth())?"SELECTED":"") %>>September
<OPTION VALUE=10 <%= (10==(coupon.getExpiresMonth())?"SELECTED":"") %>>October
<OPTION VALUE=11 <%= (11==(coupon.getExpiresMonth())?"SELECTED":"") %>>November
<OPTION VALUE=12 <%= (12==(coupon.getExpiresMonth())?"SELECTED":"") %>>December
</SELECT>
<SELECT NAME=<%= coupon.kExpiresDayValue %>>
<OPTION VALUE=1 <%= (1==(coupon.getExpiresDay())?"SELECTED":"") %>>1
<OPTION VALUE=2 <%= (2==(coupon.getExpiresDay())?"SELECTED":"") %>>2
<OPTION VALUE=3 <%= (3==(coupon.getExpiresDay())?"SELECTED":"") %>>3
<OPTION VALUE=4 <%= (4==(coupon.getExpiresDay())?"SELECTED":"") %>>4
<OPTION VALUE=5 <%= (5==(coupon.getExpiresDay())?"SELECTED":"") %>>5
<OPTION VALUE=6 <%= (6==(coupon.getExpiresDay())?"SELECTED":"") %>>6
<OPTION VALUE=7 <%= (7==(coupon.getExpiresDay())?"SELECTED":"") %>>7
<OPTION VALUE=8 <%= (8==(coupon.getExpiresDay())?"SELECTED":"") %>>8
<OPTION VALUE=9 <%= (9==(coupon.getExpiresDay())?"SELECTED":"") %>>9
<OPTION VALUE=10 <%= (10==(coupon.getExpiresDay())?"SELECTED":"") %>>10
<OPTION VALUE=11 <%= (11==(coupon.getExpiresDay())?"SELECTED":"") %>>11
<OPTION VALUE=12 <%= (12==(coupon.getExpiresDay())?"SELECTED":"") %>>12
<OPTION VALUE=13 <%= (13==(coupon.getExpiresDay())?"SELECTED":"") %>>13
<OPTION VALUE=14 <%= (14==(coupon.getExpiresDay())?"SELECTED":"") %>>14
<OPTION VALUE=15 <%= (15==(coupon.getExpiresDay())?"SELECTED":"") %>>15
<OPTION VALUE=16 <%= (16==(coupon.getExpiresDay())?"SELECTED":"") %>>16
<OPTION VALUE=17 <%= (17==(coupon.getExpiresDay())?"SELECTED":"") %>>17
<OPTION VALUE=18 <%= (18==(coupon.getExpiresDay())?"SELECTED":"") %>>18
<OPTION VALUE=19 <%= (19==(coupon.getExpiresDay())?"SELECTED":"") %>>19
<OPTION VALUE=20 <%= (20==(coupon.getExpiresDay())?"SELECTED":"") %>>20
<OPTION VALUE=21 <%= (21==(coupon.getExpiresDay())?"SELECTED":"") %>>21
<OPTION VALUE=22 <%= (22==(coupon.getExpiresDay())?"SELECTED":"") %>>22
<OPTION VALUE=23 <%= (23==(coupon.getExpiresDay())?"SELECTED":"") %>>23
<OPTION VALUE=24 <%= (24==(coupon.getExpiresDay())?"SELECTED":"") %>>24
<OPTION VALUE=25 <%= (25==(coupon.getExpiresDay())?"SELECTED":"") %>>25
<OPTION VALUE=26 <%= (26==(coupon.getExpiresDay())?"SELECTED":"") %>>26
<OPTION VALUE=27 <%= (27==(coupon.getExpiresDay())?"SELECTED":"") %>>27
<OPTION VALUE=28 <%= (28==(coupon.getExpiresDay())?"SELECTED":"") %>>28
<OPTION VALUE=29 <%= (29==(coupon.getExpiresDay())?"SELECTED":"") %>>29
<OPTION VALUE=30 <%= (30==(coupon.getExpiresDay())?"SELECTED":"") %>>30
<OPTION VALUE=31 <%= (31==(coupon.getExpiresDay())?"SELECTED":"") %>>31
</SELECT><BR>
<fontx SIZE=-2>The last date that the coupon is valid.
</TD>
 
</TR>
 
<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Maximum&nbsp;Number&nbsp;of&nbsp;Uses:&nbsp;</B></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME="<%= Coupon.kdbCouponUseLimit %>" VALUE="<%= coupon.use_limit %>" MAXLENGTH=10 SIZE=10>&nbsp;<B>Uses to date: <fontx color=red><%= coupon.used_count %></B><BR>
<fontx SIZE=-2>The maximum number of times this coupon can be redeemed. Enter zero for unlimited usage.
</TD>

</TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Coupon&nbsp;Type&nbsp;and&nbsp;Value:&nbsp;</B></TD><TD ALIGN=LEFT><fontx SIZE=-1>Choose <B>one</B> of the available coupon types:<P><INPUT TYPE=RADIO NAME="<%= Coupon.kdbCouponType %>" VALUE="<%= Coupon.kCouponTypeAmount %>" <%= (Coupon.kCouponTypeAmount==(coupon.coupon_type)?"CHECKED":"") %>><fontx size="-1"><B>Credit Toward Product Cost</B>&nbsp;&nbsp;<INPUT TYPE=TEXT NAME="<%= Coupon.kdbCouponDiscAmt %>" VALUE="<%= coupon.disc_amt %>" MAXLENGTH=10 SIZE=10><BR>
<fontx SIZE=-2>Must be zero or greater.
<P>
<INPUT TYPE=RADIO NAME="<%= Coupon.kdbCouponType %>" VALUE="<%= Coupon.kCouponTypePercent %>" <%= (Coupon.kCouponTypePercent==(coupon.coupon_type)?"CHECKED":"") %>><fontx size="-1"><B>Discount Applied To Product Cost</B>&nbsp;&nbsp;<INPUT TYPE=TEXT NAME="<%= Coupon.kdbCouponDiscPct %>" VALUE="<%= coupon.disc_pct %>" MAXLENGTH=10 SIZE=10><BR>
<fontx SIZE=-2>Must be greater than or equal to zero and less than or equal to 1. For example, a value of 0.50 would be a 50% discount.
<P>
<INPUT TYPE=RADIO NAME="<%= Coupon.kdbCouponType %>" VALUE="<%= Coupon.kCouponTypeShipAmt %>" <%= (Coupon.kCouponTypeShipAmt==(coupon.coupon_type)?"CHECKED":"") %>><fontx size="-1"><B>Credit Toward Shipping Cost</B>&nbsp;&nbsp;<INPUT TYPE=TEXT NAME="<%= Coupon.kdbCouponShipAmt %>" VALUE="<%= coupon.ship_amt %>" MAXLENGTH=10 SIZE=10><BR>
<fontx SIZE=-2>Must be zero or greater.
<P>
<INPUT TYPE=RADIO NAME="<%= Coupon.kdbCouponType %>" VALUE="<%= Coupon.kCouponTypeShipPct %>" <%= (Coupon.kCouponTypeShipPct==(coupon.coupon_type)?"CHECKED":"") %>><fontx size="-1"><B>Discount Applied To Shipping Cost</B>&nbsp;&nbsp;<INPUT TYPE=TEXT NAME="<%= Coupon.kdbCouponShipPct %>" VALUE="<%= coupon.ship_pct %>" MAXLENGTH=10 SIZE=10><BR>
<fontx SIZE=-2>Must be greater than or equal to zero and less than or equal to 1. For example, a value of 0.50 would be a 50% discount.

</TD>

</TR>

</FORM></TABLE>

