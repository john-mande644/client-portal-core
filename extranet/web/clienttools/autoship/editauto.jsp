<%@ page import="com.owd.core.OWDUtilities,
                 com.owd.core.business.Client,
                 com.owd.core.business.order.OrderUtilities,
                 com.owd.core.business.order.Inventory,
                 com.owd.core.managers.ConnectionManager,
                 com.owd.hibernate.generated.OwdOrder,
                 com.owd.hibernate.generated.OwdOrderAuto,
                 com.owd.hibernate.generated.OwdOrderAutoHistory,
                 com.owd.hibernate.generated.OwdOrderAutoItem,
                 com.owd.hibernate.generated.OwdInventory,
                 com.owd.web.autoship.AutoShipHome,
                 java.sql.Connection,
                 java.text.DateFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Hashtable" %>

<%
	 String error = (String) request.getAttribute("errormessage");


     OwdOrderAuto autos = (OwdOrderAuto) request.getAttribute("autoship.currentautoship");

     DateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.US);

     DateFormat dft = new SimpleDateFormat("MM/dd/yyyy hh:mm a",Locale.US);
     DecimalFormat decf = new DecimalFormat("##,##0.00");
     DecimalFormat dollarf = new DecimalFormat("$##,##0.00");
    Connection con = null;
    Client client = null;
    try {

        con = ConnectionManager.getConnection();

         client = Client.getClientForID(con, AutoShipHome.getSessionString(request, AutoShipHome.kExtranetClientID));

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }


    try{
 %>
<HTML>
<HEAD></HEAD><script type="text/javascript">

/***********************************************
* Disable "Enter" key in Form script- By Nurul Fadilah(nurul@REMOVETHISvolmedia.com)
* This notice must stay intact for use
* Visit http://www.dynamicdrive.com/ for full source code
***********************************************/

function handleEnter (field, event) {
		var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
		if (keyCode == 13) {
			var i;
			for (i = 0; i < field.form.elements.length; i++)
				if (field == field.form.elements[i])
					break;
			i = (i + 1) % field.form.elements.length;
			field.form.elements[i].focus();
			return false;
		}
		else
		return true;
	}

</script>
<script language="JavaScript">
     // The following script is used to hide the calendar whenever you click the document.
     document.onmousedown=function(e){
     var n=!e?self.event.srcElement.name:e.target.name;
     if (document.layers) {
     with (gfPop) var l=pageX, t=pageY, r=l+clip.width,b=t+clip.height;
     if (n!="popcal"&&(e.pageX>r||e.pageX<l||e.pageY>b||e.pageY<t)) gfPop.fHideCal();
     return routeEvent(e); // must use return here.
     } else if (n!="popcal") gfPop.fHideCal();
            }
     if (document.layers) document.captureEvents(Event.MOUSEDOWN);
</script>

<jsp:include page="autoheader.jsp" flush="true" />

<script language="JavaScript">
    console.log("script loeaded");
    $(document).ready(function(){
        console.log("jquery ready");
        $('#copyDetails').click(function(){
            console.log("clicked!!!!");
            $("#shipName").val($("#billName").val());
            $("#shipCompany").val($("#billCompany").val());
            $("#shipAddressOne").val($("#billAddressOne").val());
            $("#shipAddressTwo").val($("#billAddressTwo").val());
            $("#shipCity").val($("#billCity").val());
            $("#shipState").val($("#billState").val());
            $("#shipZip").val($("#billZip").val());
            $("#shipPhone").val($("#billPhone").val());
            $("#shipEmail").val($("#billEmail").val());

            $("#shipCountry").val($("#billCountry").val());
            return false;
        });
    });
</script>

<% if(autos.getAutoShipId()!=null)
{
      %> <TABLE width=100%><TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="ffffff">
<FORM ACTION="./edit" METHOD=POST>
    <INPUT TYPE=HIDDEN NAME="act" VALUE="delete-auto">
    <INPUT TYPE=HIDDEN NAME="sub_id" VALUE="<%= autos.getAutoShipId()%> ">
    <INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Delete this subscription"  onsubmit = return "confirm ('Are you sure to delete the subscription?!?')">
</FORM>
</TD></TR></TABLE>
      <%
}
    %>
<P><center><B><%= (autos.getAutoShipId()==null?"Creating New Subscription ":"Editing Subscription ") %>for <%= Client.getClientForID(autos.getClientFkey()+"").company_name%></B></center>
<HR>
<FORM NAME="dateform" ACTION="./edit" METHOD=POST > <CENTER><INPUT TYPE=submit NAME="submit" VALUE="Save This Subscription" ></CENTER>
<%= (autos.getAutoShipId()==null?"<INPUT TYPE=HIDDEN NAME=\""+AutoShipHome.kParamAdminAction+"\" VALUE=\"create-save\">":"<INPUT TYPE=HIDDEN NAME=\""+AutoShipHome.kParamAdminAction+"\" VALUE=\"edit-save\">")%>
<INPUT TYPE=HIDDEN NAME="sub_id" VALUE="<%= autos.getAutoShipId()%>"><TABLE border=0 width=100%><TR><TD valign=top>
 <CENTER><B>Billing</B><HR></CENTER>
<table border=0 cellspacing=0 width="100%">



<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Name:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="billName" onkeypress="return handleEnter(this, event)" name="billName" value="<%= autos.getBillName() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Company:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="billCompany" onkeypress="return handleEnter(this, event)" name="billCompany" value="<%= autos.getBillCompany() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Address 1:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="billAddressOne" onkeypress="return handleEnter(this, event)" name="billAddressOne" value="<%= autos.getBillAddressOne() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Address 2:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="billAddressTwo" onkeypress="return handleEnter(this, event)" name="billAddressTwo" value="<%= autos.getBillAddressTwo() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>City:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="billCity" onkeypress="return handleEnter(this, event)" name="billCity" value="<%= autos.getBillCity() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>State:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="billState" onkeypress="return handleEnter(this, event)" name="billState" value="<%= autos.getBillState() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Zip:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="billZip" onkeypress="return handleEnter(this, event)" name="billZip" value="<%= autos.getBillZip() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Phone:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="billPhone" onkeypress="return handleEnter(this, event)" name="billPhone" value="<%= autos.getBillPhone() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Email:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="billEmail" onkeypress="return handleEnter(this, event)" name="billEmail" value="<%= autos.getBillEmail() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Country:</B></TD><TD ALIGN=LEFT width="100%">
<%= OrderUtilities.getCountryList().getHTMLSelect(autos.getBillCountry(),"billCountry") %></TD></TR>



<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Payment Required?:</B></TD><TD ALIGN=LEFT width="100%"><INPUT TYPE=CHECKBOX NAME="requirePayment" value=1  <%= autos.getRequirePayment().intValue()==1?"CHECKED":"" %>>Require payment to succeed before shipping
<!--<BR><FONT size=-1>Selecting AutoRelease will make your inventory immediately available for orders as it is received and counted. Uncheck the box above if you want items in this shipment to not be added to available inventory until after review by you or your Account Manager following receipt.</font>--></TD></TR>

    <%
        if(null!= client.do_echeck && client.do_echeck.equals("1")){%>
    <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Payment Type:</B></TD><TD ALIGN=LEFT width="100%">
        <SELECT NAME="fop" SIZE=1>
            <OPTION VALUE="0" <%= (autos.getFop()==0?" SELECTED":"") %>>Use Credit Card (CC)
            <OPTION VALUE="3" <%= (autos.getFop()==3?" SELECTED":"") %>>Use ECheck
        </SELECT></TD></TR>
    <TR>
        <TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>E-Check Routing #</B></TD><TD ALIGN=LEFT width="100%">
        <INPUT TYPE=TEXT NAME=ck_bank size=40 VALUE="<%= autos.getCkBank() %>"></TD>
    </TR>
    <TR>
        <TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>E-Check Account #</B></TD><TD ALIGN=LEFT width="100%">
        <INPUT TYPE=TEXT NAME=ck_acct size=40 VALUE="<%= autos.getCkAcct() %>"></TD>
    </TR>
    <TR>
        <TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>E-Check Check #</B></TD><TD ALIGN=LEFT width="100%">
        <INPUT TYPE=TEXT NAME=ck_number size=40 VALUE="<%= autos.getCkNumber() %>"></TD>
    </TR>
    <% } else { %>
        <INPUT TYPE="HIDDEN" NAME="fop" VALUE="0">
    <INPUT TYPE="HIDDEN" NAME="ck_bank" VALUE="">
    <INPUT TYPE="HIDDEN" NAME="ck_acct" VALUE="">
    <INPUT TYPE="HIDDEN" NAME="ck_number" VALUE="">

    <% } %>

    <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>CC Number:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="ccNum" value="<%= autos.getCcNum() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>CC Exp Mon/Year:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="ccExpMon" value="<%= autos.getCcExpMon() %>" size=5 MAXLENGTH=255>
<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="ccExpYear" value="<%= autos.getCcExpYear() %>" size=10 MAXLENGTH=255></TD></TR>





</table><CENTER><B>Shipping</B><HR></CENTER>
<table cellspacing=0>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Same as Billing:</B></TD><TD ALIGN=LEFT width="100%">
<BUTTON id="copyDetails">Copy Billing Details</BUTTON></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Name:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="shipName" onkeypress="return handleEnter(this, event)" name="shipName" value="<%= autos.getShipName() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Company:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="shipCompany" onkeypress="return handleEnter(this, event)" name="shipCompany" value="<%= autos.getShipCompany() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Address 1:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="shipAddressOne" onkeypress="return handleEnter(this, event)" name="shipAddressOne" value="<%= autos.getShipAddressOne() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Address 2:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="shipAddressTwo" onkeypress="return handleEnter(this, event)" name="shipAddressTwo" value="<%= autos.getShipAddressTwo() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>City:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="shipCity" onkeypress="return handleEnter(this, event)" name="shipCity" value="<%= autos.getShipCity() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>State:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="shipState" onkeypress="return handleEnter(this, event)" name="shipState" value="<%= autos.getShipState() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Zip:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="shipZip" onkeypress="return handleEnter(this, event)" name="shipZip" value="<%= autos.getShipZip() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Phone:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="shipPhone" onkeypress="return handleEnter(this, event)" name="shipPhone" value="<%= autos.getShipPhone() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Email:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT id="shipEmail" onkeypress="return handleEnter(this, event)" name="shipEmail" value="<%= autos.getShipEmail() %>" size=40 MAXLENGTH=255></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Country:</B></TD><TD ALIGN=LEFT width="100%"><%= com.owd.core.business.order.OrderUtilities.getCountryList().getHTMLSelect(autos.getShipCountry(), "shipCountry") %></TD></TR>
 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Ship Method:</B></TD><TD ALIGN=LEFT width="100%">
 <%= OWDUtilities.getChoiceList("Service").getHTMLSelect(autos.getShipMethod(),"shipMethod") %></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Ship Cost:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="shipCost" value="<%= decf.format(autos.getShipCost()) %>" size=10 MAXLENGTH=255></TD></TR>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Sales Tax:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="salesTax" value="<%= decf.format(autos.getSalesTax()) %>" size=10 MAXLENGTH=255></TD></TR>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Ship Every:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="shipInterval" value="<%= autos.getShipInterval() %>" size=5 MAXLENGTH=255>&nbsp;
<select name="calendarUnit" >
<OPTION VALUE="day" <%= autos.getCalendarUnit().equals("day")?"SELECTED":"" %>>day(s)
<OPTION VALUE="week" <%= autos.getCalendarUnit().equals("week")?"SELECTED":"" %>>week(s)
<OPTION VALUE="month" <%= autos.getCalendarUnit().equals("month")?"SELECTED":"" %>>month(s)
<OPTION VALUE="day of month" <%= autos.getCalendarUnit().equals("day of month")?"SELECTED":"" %>>on day of month
<OPTION VALUE="never" <%= autos.getCalendarUnit().equals("never")?"SELECTED":""%>>never</select></TD></TR>


<TR><TD ALIGN=RIGHT NOWRAP VALIGN=TOP bgcolor="99ccff"><B>Next Shipment Date:</B></TD><TD ALIGN=LEFT width="100%" >
<input name="next_shipment_date"  value="<%= df.format(autos.getNextShipmentDate())%>" size="11" onfocus="this.blur()" readonly>
<a href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.dateform.next_shipment_date);return false;" HIDEFOCUS name="popcal" ><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>
<!--<BR><FONT size=-1>Click on the icon to choose the date that this shipment is most likely to arrive at OWD.</font>--></TD></TR>






</table>
</TD><TD valign=top width=100%>
 <table width=100% border=0 cellspacing=3 cellpadding=3>
  <TR>
<TD ALIGN=LEFT  NOWRAP VALIGN=CENTER><B>Status:&nbsp;<SELECT NAME="status">
<OPTION VALUE="0" <%= autos.getStatus().intValue()==0?"SELECTED":""%>>Active
<OPTION VALUE="1" <%= autos.getStatus().intValue()==1?"SELECTED":""%>>Suspended
<OPTION VALUE="2" <%= autos.getStatus().intValue()==2?"SELECTED":""%>>Cancelled
<OPTION VALUE="3" <%= autos.getStatus().intValue()==3?"SELECTED":""%>>Complete</SELECT>
</B></TD></TR> </table>
&nbsp;<HR>


<table border=0 cellspacing=0 width="100%"><TR  bgcolor="99ccff"><TD><HR></TD><TD NOWRAP ALIGN=CENTER width=1% ><B>Current Items</B></TD><TD><HR></TD></TR>
</table>

<table border=0 cellspacing=1 width="100%">
<TR><TH ALIGN=LEFT>SKU</TH><TH>Description</TH><TH ALIGN=RIGHT>Units</TH><TH ALIGN=RIGHT>Unit&nbsp;Price</TH></TR>
        <%
            Iterator it = autos.getOwdOrderAutoItems().iterator();
            Hashtable inv = null;
            if(it.hasNext()){
               try {
                    inv = Inventory.getItemSKUandDescForClientID(AutoShipHome.getSessionString(request, AutoShipHome.kExtranetClientID));

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        con.close();
                    } catch (Exception ex) {
                    }
                }
            }
            while(it.hasNext())
            {
               OwdOrderAutoItem item = (OwdOrderAutoItem) it.next();
               String desc = (String) inv.get(item.getSku());

        %>
 <tr>
 <TD ALIGN=LEFT NOWRAP ><B><%= item.getSku() %></B></TD>
 <TD ALIGN=CENTER NOWRAP><B><%= desc %></B></TD>
 <TD ALIGN=RIGHT NOWRAP ><INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" SIZE=6 NAME="<%= item.getSku()+"_qty"%>" VALUE="<%= item.getQuantity() %>"></TD>
 <TD ALIGN=RIGHT NOWRAP ><INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" SIZE=6 NAME="<%= item.getSku()+"_price"%>" VALUE="<%= decf.format(item.getUnitPrice()) %>"></TD></TR>
 </TR>
 <% } %>

</table> &nbsp;<HR>
 <table width=100% border=0 cellspacing=3 cellpadding=3>
  <TR>
<TD ALIGN=LEFT  NOWRAP VALIGN=CENTER><INPUT TYPE=HIDDEN NAME="subaction" VALUE="add-item">Add SKU:<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" NAME="addsku" VALUE="" size=10>&nbsp;Units:<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" NAME="qtysku" size=4>&nbsp;Unit&nbsp;Price:<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" NAME="pricesku" size=4>&nbsp;<INPUT TYPE=SUBMIT NAME="subactionSubmit" VALUE="Add SKU" >
</TD></TR> </table>
&nbsp;<HR>
<table width=100% border=0 cellspacing=3 cellpadding=3>
  <TR>
<TD ALIGN=LEFT  NOWRAP VALIGN=CENTER><INPUT TYPE=HIDDEN NAME="subaction2" VALUE="add-comment">Add Memo:<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" NAME="message" VALUE="" size=30>&nbsp;<INPUT TYPE=SUBMIT NAME="subaction2Submit" VALUE="Add Memo" >
</TD></TR> </table>
&nbsp;<P>&nbsp;
<table border=0 cellspacing=0 width="100%"><TR  bgcolor="99ccff"><TD><HR>

</TD><TD NOWRAP ALIGN=CENTER width=1% ><B>Subscription Events</B></TD><TD><HR></TD></TR>
</table>

<table border=0 cellspacing=1 width="100%">
<TR><TH ALIGN=LEFT>When</TH><TH ALIGN=LEFT>Message</TH></TR>
        <%
            Iterator ith = autos.getOwdOrderAutoHistories().iterator();
            while(ith.hasNext())
            {
               OwdOrderAutoHistory event = (OwdOrderAutoHistory) ith.next();

        %>
 <tr>
 <TD ALIGN=LEFT NOWRAP ><B><%= dft.format(event.getCreated()) %></B></TD>
  <TD ALIGN=LEFT NOWRAP ><B><%= event.getMessage() %></B></TD>
 </TR>
 <% } %>


</table>

    &nbsp;<P>&nbsp;
<table border=0 cellspacing=0 width="100%"><TR  bgcolor="99ccff">
    <TD><HR></TD><TD NOWRAP ALIGN=CENTER width=1% ><B>Subscription Orders Created</B></TD><TD><HR></TD></TR>
</table>

<table border=0 cellspacing=1 width="100%">
<TR><TH ALIGN=LEFT>When</TH><TH ALIGN=LEFT>Order Info</TH></TR>
        <%
            Set cOrders = autos.getCreatedOrders();
            if(cOrders!=null)
            {
            Iterator itc = cOrders.iterator();
            while(itc.hasNext())
            {
               OwdOrder created = (OwdOrder) itc.next();


        %>
 <tr>
 <TD ALIGN=LEFT NOWRAP ><B><%= dft.format(created.getCreatedDate()) %></B></TD>
  <TD ALIGN=LEFT NOWRAP ><B><A target="_neworder" HREF="http://service.owd.com/webapps/extranet/extranet.jsp?act=cngMgr&extranet.currmgr=Orders&ordermgr=edit&oid=<%= created.getOrderId()%>"><%= created.getOrderNum()+"/"+created.getOrderRefnum()+"</A> - "+created.getOrderStatus()+" - "+dollarf.format(created.getOrderTotal()) %></B></TD>
 </TR>
 <% } }%>


</table>

</TD></TR>


</table>

</form>



     <!--  PopCalendar(tag name and id must match) Tags should sit at the page bottom -->
     <iframe width=152 height=163 name="gToday:outlook:agenda.js" id="gToday:outlook:agenda.js" src="/webapps/popcal/outlook/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;">
     <LAYER name="gToday:outlook:agenda.js" src="/webapps/popcal/npopeng.htm">     </LAYER>
     </iframe>

<!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
<fontx SIZE="1em">
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

    </BODY>
</HTML>
<%
    }catch (Exception ex)
    {
        ex.printStackTrace();
    }
%>
