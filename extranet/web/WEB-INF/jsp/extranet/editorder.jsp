


<%@ page import="com.owd.core.business.order.Event,com.owd.core.business.order.OrderStatus,com.owd.core.payment.FinancialTransaction, com.owd.web.servlet.ExtranetServlet,com.owd.web.servlet.OrdersManager,java.util.Iterator,
                 java.util.List" %>
<%@ page import="com.owd.core.business.order.OrderUtilities" %>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>
<%   
    try
    {
      System.out.println("in editorder.jsp 1");
  	OrderStatus order = (OrderStatus) request.getAttribute(OrdersManager.kCurrentOrder);
     System.out.println("in editorder.jsp 2");
    
    if(order==null)    System.out.println("order null in editorder.jsp");
	java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");
                  System.out.println("in editorder.jsp 3");
	com.owd.core.business.Contact billc = order.billContact;

	com.owd.core.business.Address billa = order.billAddress;
                                                System.out.println("in editorder.jsp 4");
	com.owd.core.business.Contact shipc = order.shipping.shipContact;
                                                  System.out.println("in editorder.jsp x");
	 com.owd.core.business.Address shipa = order.shipping.shipAddress;

	String carrier = order.shipping.carr_service; 

	boolean cannotShipAll = false;

	boolean canPartialShip = false;

	String backorder = order.backorderNum.trim();

    Integer listindex = (Integer) request.getSession(true).getAttribute("orderlistindex");

    if(listindex == null) listindex = new Integer(0);




	if(!("".equals(backorder)))

	{

		backorder="<A HREF=\""+com.owd.web.servlet.ExtranetServlet.kExtranetURLPath+"?"+com.owd.web.servlet.OrdersManager.kParamOrderMgrAction+"="+com.owd.web.servlet.OrdersManager.kParamOrderEditAction+"&"+com.owd.web.servlet.OrdersManager.kparamOrderID+"="+order.backorder.order_id+"\">"+order.backorderNum.trim()+"</A>";

	} 

%>
<table width=100% cellpadding=0 cellspacing=0 border=0 bgcolor=ffffff>
<tr ><td valign=top bgcolor=f0f0f0 nowrap>
    <table cellpadding=3 cellspacing=0 border=0 width=100% >
          <tr><td align=center valign=top>
        <table cellpadding=1 cellspacing=0 border=0 bgcolor=#000000 >
																<tr>
																	<td >
																		<table cellpadding=2 cellspacing=0 border=0 bgcolor=f0f0f0 width=100%>
																			<tr>
																				<td bgcolor=dddddd align=center nowrap>
                                                                                <div align=center>
																					<b><a href="<%=ExtranetServlet.kExtranetURLPath%>?<%=OrdersManager.kParamOrderMgrAction%>=<%=OrdersManager.kParamOrderExploreAction%>" /><font color=blue>Return to search</font></a></b>
                                                                                    </div>
																				</td>
																			</tr>
																			<tr>
																				<td nowrap> <div align=center valign=center>
                                                                                <% int index=listindex.intValue();
                                                                                    int listSize = ((List)request.getSession(true).getAttribute("orderfinderresults")).size();
                                                                                    %>
																					<font size=1> Order <b><%= index+1 %></b> of <b><%= request.getSession(true).getAttribute("orderfinderresults")==null?"1":listSize+"" %></b> order(s)
																						<br>

																						<a href="<%=ExtranetServlet.kExtranetURLPath%>?<%=OrdersManager.kParamOrderMgrAction%>=<%=OrdersManager.kParamOrderEditAction%>&listindex=<%= index-1%>"> &lt;&lt; Previous</a> | <%= order.OWDorderReference %> | <a href="<%=ExtranetServlet.kExtranetURLPath%>?<%=OrdersManager.kParamOrderMgrAction%>=<%=OrdersManager.kParamOrderEditAction%>&listindex=<%= index+1 %>">Next &gt;&gt;</a>
																					</div>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>

              </td></tr>
    <tr><td bgcolor=dddddd ><b>Available Actions</b>
    </td></tr>
    </table>
<img src="/webapps/images/bluepixel.gif" width=100% height=1 border=0 valign=top >
<table cellpadding=3 cellspacing=0 border=0 bgcolor=f0f0f0>







 <%if(!order.is_void && !order.is_posted)
 {
 %>
   <tr><td>
        <img src="/webapps/images/icons/bullet_creme.gif" height=8 width=8 border=0 align=absmiddle>
        <b><a href="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath%>?<%=com.owd.web.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>&<%=com.owd.web.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.web.servlet.OrdersManager.kParamOrderVoidAction%>" >Cancel</a></b> this order
    </td></tr>

 <% } %>
<%

  if(!order.is_void && !order.is_posted)

{

	if(order.is_on_hold)

	{

%>


 <tr><td>
        <img src="/webapps/images/icons/bullet_creme.gif" height=8 width=8 border=0 align=absmiddle>
        Create a <b><a href="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath%>?<%=com.owd.web.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>&<%=com.owd.web.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.web.servlet.OrdersManager.kParamOrderRemoveHoldAction%>" >Pending Backorder</a></b> for this order
    </td></tr>

<%

} else {

%>
 <tr><td>
        <img src="/webapps/images/icons/bullet_creme.gif" height=8 width=8 border=0 align=absmiddle>
        <b><a href="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath%>?<%=com.owd.web.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>&<%=com.owd.web.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.web.servlet.OrdersManager.kParamOrderSetHoldAction%>" >Hold</a></b> this order
    </td></tr>

<%
	}
}
%>


    <tr><td>
        <img src="/webapps/images/icons/bullet_creme.gif" height=8 width=8 border=0 align=absmiddle>
        <b><a href="/secure/AddComment!default.jspa?id=14209">Comment</a></b> on this order
    </td></tr>

     <tr><td>
            <img src="/webapps/images/icons/bullet_creme.gif" height=8 width=8 border=0 align=absmiddle>
            <b><a href="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath%>?ordermgr=billedit&oid=<%= order.order_id%>">Edit</a></b> customer information
        </td></tr>

      <tr><td>
                <img src="/webapps/images/icons/bullet_creme.gif" height=8 width=8 border=0 align=absmiddle>
                <b><a href="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath%>?ordermgr=shipedit&oid=<%= order.order_id%>">Edit</a></b> shipping information
            </td></tr>



</table>

</td><td valign=top width=100% >


<B><%= (String)request.getAttribute("formerror")==null?"":(String)request.getAttribute("formerror")+"<BR>" %></B>
<table cellpadding=5 bgcolor=ffffff width=100%>
<tr><td valign="top">
  <table cellpadding=1 cellspacing=0 border=0 bgcolor=bbbbbb width=100% >
							<tr>
								<td>
									<table cellpadding=4 cellspacing=0 border=0 width=100% bgcolor=ffffff>
										<tr>
											<td bgcolor=f0f0f0 width=99%  ><table width=100% cellpadding=0 cellspacing=0>
													<tr><td bgcolor=f0f0f0  valign=center><h3 class="formtitle"><%= order.orderReference+" : "+(order.billAddress.company_name.length()>2?order.billAddress.company_name:order.billContact.name) %>
</h3> <font size=1> Created: <font color=336699><%= order.orderDate %> </font><B>via <%=order.order_type%></B> </font>
                                                        </td>
                                                        <td NOWRAP bgcolor=f0f0f0 valign=center width=1%>
                                                <h2 ><center>&nbsp;&nbsp;<%= order.getStatusString() %>&nbsp;&nbsp;</center></h2>                                            </td>
												</tr>
											</table>
									</tr>


								</table>
							</td>
						</tr>
					</table>




&nbsp;<P>
<table cellpadding=2 cellspacing=0 border=0 width=100% >
<tr>
	<td bgcolor=00aa00 width=1% nowrap align=left>
			&nbsp;<font color=ffffff><b>Bill To:</b></font>&nbsp;
	</td>
	<td align=right width=99%>
</td>
</tr>
</table>
<table cellpadding=1 cellspacing=0 border=0  width=100% bgcolor=bbbbbb align=center><tr><td>
<table cellpadding=2 cellspacing=0 border=0  width=100%>
<tr height=100%>
	<td bgcolor=ffffff valign=top width=50%>
    <%=billc.name+"<BR>"+(billa.company_name.length()>1?billa.company_name+"<BR>":"")+(billa.address_one.length()>1?billa.address_one+"<BR>":"")+(billa.address_two.length()>1?billa.address_two+"<BR>":"") %>
    <%= billa.city+"&nbsp;"+billa.state+"&nbsp;"+billa.zip+"<BR>"+billa.country %>
    </td>	<td bgcolor=ffffff valign=top width=50%>
Phone: <%= billc.phone %><BR>
Fax: <%= billc.fax %><BR>
Email: <%= (billc.email.length()>1?"<A HREF=\"mailto:"+billc.email+"\">"+billc.email+"</A>":"") %>
    </td>
</tr>
</table>
</td>
</tr>
</table>
&nbsp;<P>
<table cellpadding=2 cellspacing=0 border=0 width=100% align=center>
<tr>
	<td bgcolor=00aa00 width=1% nowrap align=center>
			&nbsp;<font color=ffffff><b>Ship To:</b></font>&nbsp;
	</td>
	<td>
</td>
</tr>
</table>
<table cellpadding=1 cellspacing=0 border=0 width=100% bgcolor=bbbbbb align=center><tr><td>
<table cellpadding=2 cellspacing=0 border=0 width=100%>
<tr>
	<td bgcolor=ffffff valign=top>


<%= shipc.name+"<BR>"+(shipa.company_name.length()>1?shipa.company_name+"<BR>":"")+(shipa.address_one.length()>1?shipa.address_one+"<BR>":"")+(shipa.address_two.length()>1?shipa.address_two+"<BR>":"") %>

<%= shipa.city+"&nbsp;"+shipa.state+"&nbsp;"+shipa.zip+"<BR>"+shipa.country %>
    </td>	<td bgcolor=ffffff valign=top width=50%>

Phone: <%= shipc.phone %><BR>

Fax: <%= shipc.fax %><BR>

Email: <%= (shipc.email.length()>1?"<A HREF=\"mailto:"+shipc.email+"\">"+shipc.email+"</A>":"") %><BR>

Ship Via: <B><%= carrier %></B>
    </td>
</tr>
</table>
</td>
</tr>
</table>
&nbsp;<P>
<table cellpadding=2 cellspacing=0 border=0 width=100% align=center>
<tr>
	<td bgcolor=00aa00 width=1% nowrap align=center>
			&nbsp;<font color=ffffff><b>Fields:</b></font>&nbsp;
	</td>
	<td>
</td>
</tr>
</table>
<table cellpadding=1 cellspacing=0 border=0 width=100% bgcolor=bbbbbb align=center><tr><td>
<table cellpadding=2 cellspacing=0 border=0 width=100%>
<tr>
	<td bgcolor=ffffff valign=top>
    <table cellpadding=1>
        <TR><TD ALIGN=RIGHT width=20% bgcolor=bbee88><B>&nbsp;<%= (order.client_id.equals("117")?"Affiliate":"PO Number") %>:&nbsp;</B></TD><TD><%= order.po_num %></TD></TR>
             <TR><TD ALIGN=RIGHT width=20% bgcolor=bbee88><B>&nbsp;Coupon:&nbsp;</B></TD><TD><%= order.coupon %></TD></TR>
                 <TR><TD ALIGN=RIGHT width=20% bgcolor=bbee88><B>&nbsp;Comments:&nbsp;</B></TD><TD><%= order.shipping.comments %></TD></TR>
                    <TR><TD ALIGN=RIGHT width=20% bgcolor=bbee88><B>&nbsp;Ship Notes:&nbsp;</B></TD><TD><%= order.shipping.whse_notes %></TD></TR>
<TR><TD ALIGN=RIGHT width=20% bgcolor=bbee88><B>&nbsp;Backorders:&nbsp;</B></TD><TD><%= ("".equals(order.backorderNum.trim())?"No linked orders":backorder) %></TD></TR>


    </table>
    </td>
</tr>
</table>
</td>
</tr>
</table>


<FORM METHOD=POST ACTION="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath%>?<%=com.owd.web.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
&nbsp;<P>
<table cellpadding=2 cellspacing=0 border=0 width=100% align=center>
<tr>
	<td bgcolor=00aa00 width=1% nowrap align=center>
			&nbsp;<font color=ffffff><b>Line Items</b></font>&nbsp;
	</td>
	<td>
</td>
</tr>
</table>
<table cellpadding=1 cellspacing=0 border=0 width=100% bgcolor=bbbbbb align=center><tr><td>
<table cellpadding=2 cellspacing=0 border=0 width=100%>
<tr>
	<td bgcolor=ffffff valign=top>

<% if(order.items.size() < 1) {

%>

<B>No items found for this order</B>

<%

}else{ 

%><FORM METHOD=POST ACTION="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath%>?<%=com.owd.web.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.web.servlet.OrdersManager.kParamOrderShipAction%>&<%=com.owd.web.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">

<TABLE width=100% border=0>

<TR bgcolor="#dddddd"><TH></TH><TH><fontx  size=-2 color="#99cc66">&nbsp;SKU&nbsp;</fontx></TH>

<TH><fontx  size=-2 color="#99cc66">&nbsp;Description&nbsp;</fontx></TH>

<TH><fontx  size=-2 color="#99cc66">&nbsp;Color&nbsp;</fontx></TH>

<TH><fontx  size=-2 color="#99cc66">&nbsp;Size&nbsp;</fontx></TH>

<TH><fontx  size=-2 color="#99cc66">&nbsp;Count&nbsp;</fontx></TH>

<TH><fontx  size=-2 color="#99cc66">&nbsp;BO&nbsp;</fontx></TH>

<TH><fontx  size=-2 color="#99cc66">&nbsp;Cost&nbsp;</fontx></TH>

<TH><fontx  size=-2 color="#99cc66">&nbsp;Line&nbsp;Total&nbsp;</fontx></TH></TR>

<% 

	for(int i=0; i<order.items.size(); i++)

	{

		com.owd.core.business.order.LineItem item = (com.owd.core.business.order.LineItem) order.items.elementAt(i);

%><TR><TD><%

String stockLevel = "";



if(!order.is_void && !order.is_posted)

{

	int available = OrderUtilities.getAvailableInventory(item.inventory_fkey, FacilitiesManager.getFacilityForCode(order.getLocation()).getId());

	if(available>=item.quantity_request)

	{

		stockLevel = "<fontx color=green><B>On Hand ("+available+")&nbsp;&nbsp;</B></fontx>";

		if (!order.is_shipped){%><INPUT TYPE=CHECKBOX NAME="ship_item_<%= item.line_item_id %>" VALUE=1>&nbsp;<%

		}

		canPartialShip=true;

	}else

	{

		stockLevel = "<fontx color=red><B>On Hand ("+available+")&nbsp;&nbsp;</B></fontx>";

		cannotShipAll=true;

	}

	

}



%>

</TD><TD><fontx  size=-2><%= item.client_part_no %></TD>

<TD><fontx  size=-2><%= item.description %></TD>

<TD><fontx  size=-2><%= ("null".equalsIgnoreCase(item.color)?"":item.color) %></TD>

<TD><fontx  size=-2><%= ("null".equalsIgnoreCase(item.size)?"":item.size) %></TD>

<TD align=right><fontx  size=-2 ><%= stockLevel %><%= (item.quantity_backordered+item.quantity_request) %></TD>

<TD align=right><fontx  size=-2 ><%= item.quantity_backordered %></TD>

<TD align=right><fontx  size=-2 ><%= formatter.format(item.sku_price) %></TD>

<TD align=right><fontx  size=-2><%= formatter.format(item.total_price) %></TD>

</TR>

<%	

	}

%>

<TR><TD colspan=9><HR></TD></TR>

<TR><TD colspan=7><fontx  size=-2>&nbsp;

<% if(!(order.is_posted) && !order.is_void && canPartialShip)

	{

		

	%>

<INPUT TYPE=SUBMIT NAME=shipOrder VALUE="Ship Checked Items Only">

<%

		

		if(!cannotShipAll)

		{

%>

&nbsp;<INPUT TYPE=SUBMIT NAME=shipOrder VALUE="Ship Entire Order">

<%

		}

		

	}

%>



</TD>

<TD align=right><fontx  size=-2 ><B>Subtotal&nbsp;:&nbsp;</TD>

<TD align=right><fontx  size=-2 ><%= formatter.format(order.order_sub_total) %></TD>

</TR>

<TR><TD colspan=7><fontx  size=-2>&nbsp;</TD>

<TD align=right><fontx  size=-2 ><B>Discount&nbsp;:&nbsp;</TD>

<TD align=right><fontx  size=-2 ><%= formatter.format(order.discount*-1) %></TD>

</TR>

<TR><TD colspan=7><fontx  size=-2>&nbsp;</TD>

<TD align=right><fontx  size=-2 ><B>Tax&nbsp;:&nbsp;</TD>

<TD align=right><fontx  size=-2 ><%= formatter.format(order.tax_amount) %></TD>

</TR>

<TR><TD colspan=7><fontx  size=-2>&nbsp;</TD>

<TD align=right><fontx  size=-2 ><B>S/H&nbsp;:&nbsp;</TD>

<TD align=right><fontx  size=-2 ><%= formatter.format(order.ship_handling_fee) %></TD>

</TR>

<TR><TD colspan=7><fontx  size=-2>&nbsp;</TD>

<TD align=right><fontx  size=-2 ><B>Total&nbsp;:&nbsp;</TD>

<TD align=right><fontx  size=-2 ><%= formatter.format(order.order_total) %></TD>

</TR>

</TABLE>

</FORM>

<%

}

%>

</td>
</tr>
</table>
</td>
</tr>
</table>
&nbsp;<P>
<table cellpadding=2 cellspacing=0 border=0 width=100% align=center>
<tr>
	<td bgcolor=00aa00 width=1% nowrap align=center>
			&nbsp;<font color=ffffff><b>Comments</b></font>&nbsp;
	</td>
	<td>
</td>
</tr>
</table>
<table cellpadding=1 cellspacing=0 border=0 width=100% bgcolor=bbbbbb align=center><tr><td>
<table cellpadding=2 cellspacing=0 border=0 width=100%>
<tr>
	<td bgcolor=ffffff valign=top>


		<%

	if(order.comments.size() < 1) {

%>
<B>No comments found for this order</B>

<%

}else{

%>

<TABLE width=100% cellspacing=0 cellpadding=2>



<%

	

	Iterator iter = order.comments.iterator();

	while(iter.hasNext())

	{

		Event item = (Event) iter.next();

		String when;

		String what = "Unknown Type ("+item.event_type+")";

		String msg = "";

		if(item.message != null) msg = item.message;

		switch(item.event_type)

		{

			case (Event.kEventTypeGeneral):

				what = "Miscellaneous";

			

				break;

			case (Event.kEventTypeComment):

				what = "Note";

			

				break;

			case (Event.kEventTypeEmailSent):

				what = "Email Sent";

			

				break;

			case (Event.kEventTypeHandling):

				what = "Order Processing";

			

				break;

			case (Event.kEventTypeEdit):

				what = "Order Edit";

			

				break;

		}

		

		

		when = item.event_stamp.substring(0,item.event_stamp.indexOf(" "));

		when = when+"&nbsp;"+item.event_stamp.substring(item.event_stamp.indexOf(" ")+1,item.event_stamp.indexOf("."));

		if(item.creator.equalsIgnoreCase("NULL") 

			|| item.creator.equalsIgnoreCase("dbo") 

			|| item.creator.equalsIgnoreCase("guest") 

			|| item.creator.equalsIgnoreCase("system") 

			|| item.creator.equalsIgnoreCase("sa") )

		{

			when = when+"<BR><B>("+what+"&nbsp;by&nbsp;System)";

		}else

		{

			when = when+"<BR><B>("+what+"&nbsp;by&nbsp;"+item.creator+")";

		}

			

			



	

%><TR><TD colspan=3><HR></TD></TR>

<TR>

<TD VALIGN="TOP"><fontx  size=-2><%= when %></B></TD>

<TD>&nbsp;&nbsp;</TD>

<TD VALIGN="TOP"><fontx  size=-2><%= msg %></TD>

</TR>



<%		

	}

%>

</TABLE>

<%

}





%>
</td></tr></table></td></tr></table>

&nbsp;<P>
<table cellpadding=2 cellspacing=0 border=0 width=100% align=center>
<tr>
	<td bgcolor=00aa00 width=1% nowrap align=center>
			&nbsp;<font color=ffffff><b>Credit Card Transactions</b></font>&nbsp;
	</td>
	<td>
</td>
</tr>
</table>
<table cellpadding=1 cellspacing=0 border=0 width=100% bgcolor=bbbbbb align=center><tr><td>
<table cellpadding=2 cellspacing=0 border=0 width=100%>
<tr>
	<td bgcolor=ffffff valign=top>
		<% if(order.transactions.size() < 1) {

%>

<fontx  color="#000000" size=-2><B>No transactions found for this order</B></fontx>

<%

}else{

%>

<TABLE width=100%>

<TR bgcolor="#dddddd">

<TH>&nbsp;Type</TH>

<TH>&nbsp;Status</TH>

<TH>&nbsp;Message</TH>

<TH>&nbsp;Amount</TH></TR>

<%

	Iterator iter = order.transactions.iterator();

	while(iter.hasNext())

	{



		com.owd.core.payment.FinancialTransaction item = (FinancialTransaction) iter.next();

		if(0 == item.is_void)

		{

%>

<TR><TD align=LEFT><fontx size=-2><B><%

	String strType="Unknown";

	if("0".equals(item.fop))

	{

		//CC FOP

		int cctype = new Integer(item.type).intValue();

		switch(cctype)

		{

			case (FinancialTransaction.transTypeAuthOnlyRequest):

			strType = "CC Authorization";

				break;

			case (FinancialTransaction.transTypeCaptureOutsideAuthRequest):

			strType = "CC Capture";

				break;

			case (FinancialTransaction.transTypeAuthCaptureRequest):

			strType = "CC Capture";

				break;

			case (FinancialTransaction.transTypeVoidTransactionRequest):

			strType = "CC Void Transaction";

				break;

			case (FinancialTransaction.transTypeCreditRequest):

				strType = "Credit CC";

				break;



		}



	}else

	{

		//CK or PO FOP

		strType = "Check/PO";

	}



	if(item.is_future_charge==1 && item.charge_status==1)

	{

		strType=strType+" (FlexPay Payment)";

	}else if(item.is_future_charge==1 && item.charge_status==0)

	{

		strType=strType+" (FlexPay for "+item.charge_date.substring(0,item.charge_date.indexOf(" "))+")";

	}else if(item.is_future_charge==1 && item.charge_status==2)

	{

		strType=strType+" (Failed FlexPay on "+item.charge_date.substring(0,item.charge_date.indexOf(" "))+")";

	}



%><%= strType %></B></TD>

<TD align=center><fontx size=-2><B><%= item.status %></B></TD><TD align=center><fontx size=-2><%= item.error_reponse %></TD><TD align=right><fontx size=-2><%= formatter.format(item.amount) %></TD></TR>

<%		}

	}

%>

</TABLE>

<%

}%>
	</td>
</tr>
</table>
</td></tr></table>

<%


com.owd.core.business.Client client = com.owd.core.business.Client.getClientForID(order.client_id);

System.out.println("cking packages");

if(client == null)

{

	System.out.println("client null");

}

if(client.pp_proc == null)

{

	System.out.println("pp_proc null");

}



	

if(client.pp_proc != null)

{

if(client.pp_proc.length()>7)

{

System.out.println("cking packages 2");

 }

}
	 %>
&nbsp;<P>
<table cellpadding=2 cellspacing=0 border=0 width=100% align=center>
<tr>
	<td bgcolor=00aa00 width=1% nowrap align=center>
			&nbsp;<font color=ffffff><b>Shipments</b></font>&nbsp;
	</td>
	<td>
</td>
</tr>
</table>
<table cellpadding=1 cellspacing=0 border=0 width=100% bgcolor=bbbbbb align=center><tr><td>
<table cellpadding=2 cellspacing=0 border=0 width=100%>
<tr>
	<td bgcolor=ffffff valign=top>

<% if(order.packages.size() < 1) {

%>

<B>No packages/shipments found for this order</B>

<%

}else{

%>

<TABLE width=100% >

<TR bgcolor="#dddddd"><TH></TH><TH>Shipped</TH>

<TH>Carrier</TH>

<TH>Tracking</TH>

<TH>Weight</TH>

<TH>Rate</TH></TR>

<%

	//String carrier = order.shipping.carr_service;

	for(int i=0; i<order.packages.size(); i++)

	{

		

		

		com.owd.core.business.order.Package item = (com.owd.core.business.order.Package) order.packages.elementAt(i);

		

		if("0".equals(item.is_void))

		{

%>

<TR VALIGN=CENTER><TD VALIGN=CENTER><%

if (com.owd.web.servlet.ExtranetServlet.getSessionFlag(request,com.owd.web.servlet.ExtranetServlet.kExtranetInternalFlag))

{

%><fontx SIZE=-2><A HREF="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath%>?<%=com.owd.web.servlet.OrdersManager.kparamShipmentID%>=<%= item.order_track_id%>&<%=com.owd.web.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.web.servlet.OrdersManager.kParamOrderVoidPackageAction%>&<%=com.owd.web.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">Void Shipment</A>

<%

}

%></TD><TD VALIGN=CENTER><fontx size=-2><%= item.createdOn.substring(0,item.createdOn.indexOf(" ")) %></TD><TD VALIGN=CENTER><fontx size=-2><%= carrier %></TD><TD VALIGN=CENTER><fontx size=-2><%= (carrier.indexOf("UPS")==0?"<A HREF=\"http://wwwapps.ups.com/etracking/tracking.cgi?HTMLVersion=4.0&TypeOfInquiryNumber=T&tracknums_displayed=1&InquiryNumber1="+item.tracking_no+"\">"+item.tracking_no+"</A>":((carrier.indexOf("Priority Mail")>=0) || (carrier.indexOf("Express")>=0)?"<A HREF=\"https://tools.usps.com/go/TrackConfirmAction.action?tLabels="+item.tracking_no+"\">"+item.tracking_no+"</A>":item.tracking_no)) %></TD><TD VALIGN=CENTER><fontx size=-2><%= item.weight %> lbs</TD><TD VALIGN=CENTER><fontx size=-2><%= formatter.format(item.total_billed) %></TD></TR>

<%		}else if (com.owd.web.servlet.ExtranetServlet.getSessionFlag(request,com.owd.web.servlet.ExtranetServlet.kExtranetInternalFlag))

{

%>

<TR VALIGN=CENTER><TD><fontx size=-2><I>Voided</I></TD><TD><fontx size=-2><I><%= item.createdOn.substring(0,item.createdOn.indexOf(" ")) %></I></TD><TD><fontx size=-2><I><%= carrier %></I></TD><TD><fontx size=-2><I><%= (carrier.indexOf("UPS")==0?"<A HREF=\"http://wwwapps.ups.com/etracking/tracking.cgi?HTMLVersion=4.0&TypeOfInquiryNumber=T&tracknums_displayed=1&InquiryNumber1="+item.tracking_no+"\">"+item.tracking_no+"</A>":((carrier.indexOf("Priority Mail")>=0) || (carrier.indexOf("Express")>=0)?"<A HREF=\"https://tools.usps.com/go/TrackConfirmAction.action?tLabels="+item.tracking_no+"\">"+item.tracking_no+"</A>":item.tracking_no)) %></I></TD><TD><fontx size=-2><I><%= item.weight %> lbs</I></TD><TD><fontx size=-2><I><%= formatter.format(item.total_billed) %></I></TD></TR>

<%

}

	}

%>

</TABLE>

<%

}

%>
</td></tr></table>
</td></tr></table>


&nbsp;<P>
<table cellpadding=2 cellspacing=0 border=0 width=100% align=center>
<tr>
	<td bgcolor=00aa00 width=1% nowrap align=center>
			&nbsp;<font color=ffffff><b>Order Processing Events</b></font>&nbsp;
	</td>
	<td>
</td>
</tr>
</table>
<table cellpadding=1 cellspacing=0 border=0 width=100% bgcolor=bbbbbb align=center><tr><td>
<table cellpadding=2 cellspacing=0 border=0 width=100%>
<tr>
	<td bgcolor=ffffff valign=top>

<%

	

	if(order.events.size() < 1) {

%>

<HR><fontx  color="#000000" size=-2><B>No events found for this order</B></fontx>

<%

}else{

%>

<TABLE WIDTH=100% cellspacing=0 cellpadding=2>



<%

	

	Iterator iter = order.events.iterator();

	while(iter.hasNext())

	{

		Event item = (Event) iter.next();

		String when;

		String what = "Unknown Type ("+item.event_type+")";

		String msg = "";

		StringBuffer msgPrint = new StringBuffer();

		if(item.message != null) msg = item.message;

		

		switch(item.event_type)

		{

			case (Event.kEventTypeGeneral):

				what = "Miscellaneous";

			

				break;

			case (Event.kEventTypeComment):

				what = "Note";

			

				break;

			case (Event.kEventTypeEmailSent):

				what = "Email&nbsp;Sent";

				//msg = "<PRE>"+msg+"</PRE>";

			

				break;

			case (Event.kEventTypeHandling):

				what = "Order&nbsp;Processing";

			

				break;

			case (Event.kEventTypeEdit):

				what = "Order&nbsp;Edit";

			

				break;

		}

		

		

		when = item.event_stamp.substring(0,item.event_stamp.indexOf(" "));

		when = when+"&nbsp;"+item.event_stamp.substring(item.event_stamp.indexOf(" ")+1,item.event_stamp.indexOf("."));

		if(item.creator.equalsIgnoreCase("NULL") 

			|| item.creator.equalsIgnoreCase("dbo") 

			|| item.creator.equalsIgnoreCase("guest") 

			|| item.creator.equalsIgnoreCase("system") 

			|| item.creator.equalsIgnoreCase("sa") )

		{

			when = when+"<BR><B>("+what+"&nbsp;by&nbsp;System)";

		}else

		{

			when = when+"<BR><B>("+what+"&nbsp;by&nbsp;"+item.creator+")";

		}

			

			

		int lineChars = 0;

		boolean lastCharWasReturn = false;
    /*
	for(int i=0;i<msg.length();i++)

		{



			int theChar = msg.charAt(i);

			

			if((theChar==32 || theChar==9) && (lastCharWasReturn || lineChars == 0))

			{

				//skip it

			}else

			{

			if(theChar == 10 || theChar == 13)

			{

				if(lastCharWasReturn)

					msgPrint.append("&nbsp;");

				msgPrint.append("<BR>");

				lineChars = 0;

				lastCharWasReturn = true;

				

			}else if (lineChars > 80)

			{

				msgPrint.append("<BR>");

				if((theChar==32 || theChar==9))

				{

					lineChars = 0;

				}else

				{

					msgPrint.append(msg.substring(i,i+1));

					lineChars = 1;

				}

				lastCharWasReturn = true;

			

			}else

			{

				msgPrint.append(msg.substring(i,i+1));

				lineChars++;

				lastCharWasReturn = false;

			}

			}*/

			

			%>
            <TR><TD colspan=2><HR></TD></TR>

            <TR>
            <TD VALIGN="TOP" width=20%><%= when %></TD>
            <TD VALIGN="TOP" width=80%><%= msg.replaceAll("\r\n","\r").replaceAll("\n","\r").replaceAll("\r","<BR>").replaceAll("===","=") %></TD>
            </TR>

			

			<%

		

		}

%>


<%		

	}

%>

</TABLE>

<%




    }catch(Exception ex)
    {
        ex.printStackTrace();
    }


%>

</td></tr></table>
</td></tr></table>
</td></tr></table>
</td></tr></table>