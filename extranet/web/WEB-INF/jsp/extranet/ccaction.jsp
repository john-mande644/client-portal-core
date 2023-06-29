<%@ page import="com.owd.core.payment.FinancialTransaction,java.util.Iterator" %>
<%   
  	com.owd.core.business.order.OrderStatus order = (com.owd.core.business.order.OrderStatus)request.getAttribute(com.owd.web.servlet.OrdersManager.kCurrentOrder);
	java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");
	com.owd.core.business.Contact billc = order.billContact;
	com.owd.core.business.Address billa = order.billAddress;
%>       
<P>  
   
<B>Credit Card Transactions for order <%= order.orderReference %> (<%= order.OWDorderReference %>)</B>
<HR><fontx COLOR=RED><B><%= (String)request.getAttribute("formerror") %></B></fontx> <BR>
<B><fontx SIZE=-1><A HREF="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath%>?<%=com.owd.web.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.web.servlet.OrdersManager.kParamOrderEditAction%>&<%=com.owd.web.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">Return to Order Details</A></fontx></B>
<FORM METHOD=POST ACTION="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath%>?<%=com.owd.web.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.web.servlet.OrdersManager.kParamOrderDoCCAction%>&<%=com.owd.web.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
<INPUT TYPE=HIDDEN NAME="doccaction" VALUE="1">
<TABLE width=100% border=0>
<TR><TD width=20% align=right valign=top><fontx SIZE=-1><B>Account :</TD><TD colspan=2><fontx SIZE=-1><% if ( order.transactions.size() > 0){%><INPUT TYPE="RADIO" NAME="useoriginalcc" VALUE="1" CHECKED>Use Original CC Info<BR>
<%}%><INPUT TYPE="RADIO" NAME="useoriginalcc" VALUE="0" <% if ( order.transactions.size() == 0){%>CHECKED<%}%>>Use New CC Info<BR><fontx SIZE=-2>If using new CC information, fill out the fields below with the information to use for this transaction. Some default values have been provided for your convenience.<HR></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Billing Name :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billc.name VALUE="<%= billc.name %>"></TD></TR>

<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Company Name :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billa.company_name VALUE="<%= billa.company_name %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Address Line 1 :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billa.address_one VALUE="<%= billa.address_one %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Postal Code :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billa.zip VALUE="<%= billa.zip %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>CC Number :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=cc_num VALUE=""></TD></TR>
<%
	String expMonStr = "00";
	String expYrStr = "00";
	String allexpStr = "0000";
	if ( order.transactions.size() >0){
	
		allexpStr = ((FinancialTransaction)order.transactions.get(0)).cc_exp;
		expMonStr = allexpStr.substring(0,2);
		expYrStr = allexpStr.substring(2);
		
	
	}

%>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>CC Expiration :</TD><TD><SELECT NAME=cc_exp_mon >
<OPTION VALUE=01 <%= ("01".equals(expMonStr)?"SELECTED":"")%>>January
<OPTION VALUE=02 <%= ("02".equals(expMonStr)?"SELECTED":"")%>>February
<OPTION VALUE=03 <%= ("03".equals(expMonStr)?"SELECTED":"")%>>March
<OPTION VALUE=04 <%= ("04".equals(expMonStr)?"SELECTED":"")%>>April
<OPTION VALUE=05 <%= ("05".equals(expMonStr)?"SELECTED":"")%>>May
<OPTION VALUE=06 <%= ("06".equals(expMonStr)?"SELECTED":"")%>>June
<OPTION VALUE=07 <%= ("07".equals(expMonStr)?"SELECTED":"")%>>July
<OPTION VALUE=08 <%= ("08".equals(expMonStr)?"SELECTED":"")%>>August
<OPTION VALUE=09 <%= ("09".equals(expMonStr)?"SELECTED":"")%>>September
<OPTION VALUE=10 <%= ("10".equals(expMonStr)?"SELECTED":"")%>>October
<OPTION VALUE=11 <%= ("11".equals(expMonStr)?"SELECTED":"")%>>November
<OPTION VALUE=12 <%= ("12".equals(expMonStr)?"SELECTED":"")%>>December
</SELECT><SELECT NAME=cc_exp_year >
<OPTION VALUE=2001  <%= ("01".equals(expYrStr)?"SELECTED":"")%>>2001
<OPTION VALUE=2002 <%= ("02".equals(expYrStr)?"SELECTED":"")%>>2002
<OPTION VALUE=2003 <%= ("03".equals(expYrStr)?"SELECTED":"")%>>2003
<OPTION VALUE=2004 <%= ("04".equals(expYrStr)?"SELECTED":"")%>>2004
<OPTION VALUE=2005 <%= ("05".equals(expYrStr)?"SELECTED":"")%>>2005
<OPTION VALUE=2006 <%= ("06".equals(expYrStr)?"SELECTED":"")%>>2006
<OPTION VALUE=2007 <%= ("07".equals(expYrStr)?"SELECTED":"")%>>2007
<OPTION VALUE=2008 <%= ("08".equals(expYrStr)?"SELECTED":"")%>>2008
<OPTION VALUE=2009 <%= ("09".equals(expYrStr)?"SELECTED":"")%>>2009
<OPTION VALUE=2010 <%= ("10".equals(expYrStr)?"SELECTED":"")%>>2010</SELECT></TD></TR>

<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD colspan=2 >&nbsp;</TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Amount :</TD><TD colspan=2><fontx SIZE=-1><B>$</B><INPUT SIZE=15 TYPE=TEXT NAME=amount VALUE="0.00"><B>&nbsp;USD</B></TD></TR>
<TR><TD width=20% align=right valign=top><fontx SIZE=-1><B>Type :</TD><TD colspan=2><fontx SIZE=-1><INPUT TYPE="RADIO" NAME="transtype" VALUE="1" CHECKED>Charge - Take from customer CC account<BR>
<INPUT TYPE="RADIO" NAME="transtype" VALUE="0">Credit - Give money to customer<BR>&nbsp;&nbsp;&nbsp;Transaction&nbsp;ID&nbsp;to&nbsp;credit:&nbsp;<INPUT TYPE="TEXT" NAME="creditTransID" VALUE=""></TD></TR>
<TR><TD></TD><TD colspan=2>&nbsp;<BR><INPUT TYPE=SUBMIT name=submit value="Attempt Transaction"></TD></TR>
<TR><TD colspan=3><HR></TD></TR>

</TABLE></FORM>


<fontx size=-1><B>Order Summary</B></fontx><HR>
<TABLE >
<TR>
<TD align=right><fontx  size=-2 ><B>Subtotal&nbsp;:&nbsp;</TD>
<TD align=left><fontx  size=-2 ><%= formatter.format(order.order_sub_total) %></TD>
</TR>
<TR>
<TD align=right><fontx  size=-2 ><B>Discount&nbsp;:&nbsp;</TD>
<TD align=left><fontx  size=-2 ><%= formatter.format(order.discount*-1) %></TD>
</TR>
<TR>
<TD align=right><fontx  size=-2 ><B>Tax&nbsp;:&nbsp;</TD>
<TD align=left><fontx  size=-2 ><%= formatter.format(order.tax_amount) %></TD>
</TR>
<TR>
<TD align=right><fontx  size=-2 ><B>S/H&nbsp;:&nbsp;</TD>
<TD align=left><fontx  size=-2 ><%= formatter.format(order.ship_handling_fee) %></TD>
</TR>
<TR>
<TD align=right><fontx  size=-2 ><B>Total&nbsp;:&nbsp;</TD>
<TD align=left><fontx  size=-2 ><%= formatter.format(order.order_total) %></TD>
</TR>
</TABLE><HR>
<fontx size=-1><B>Past Transactions</B></fontx>
<TABLE width=100%>
<TR bgcolor="#000000">
<TH><fontx  size=-1 color="#ffffff">Type</fontx></TH>
<TH><fontx  size=-1 color="#ffffff">When</fontx></TH>
<TH><fontx  size=-1 color="#ffffff">Status</fontx></TH>
<TH><fontx  size=-1 color="#ffffff">Auth Code</fontx></TH>
<TH><fontx  size=-1 color="#ffffff">AVS</fontx></TH>
<TH><fontx  size=-1 color="#ffffff">Trans ID</fontx></TH>
<TH><fontx  size=-1 color="#ffffff">Old Trans ID</fontx></TH>
<TH><fontx  size=-1 color="#ffffff">Message</fontx></TH>
<TH><fontx  size=-1 color="#ffffff">Amount</fontx></TH></TR>
<%
	Iterator iter = order.transactions.iterator();
	while(iter.hasNext())
	{	  
		
		com.owd.core.payment.FinancialTransaction item = (FinancialTransaction) iter.next();
		if(0 == item.is_void)
		{
%>
<TR><TD align=center><fontx size=-2><B><%
	String strType="Unknown";
	if("0".equals(item.fop))
	{
		//CC FOP
		int cctype = new Integer(item.type).intValue();
		switch(cctype)
		{
			case (FinancialTransaction.transTypeAuthOnlyRequest):
			strType = "Authorization";
				break;
			case (FinancialTransaction.transTypeCaptureOutsideAuthRequest):
			strType = "Capture";
				break;
			case (FinancialTransaction.transTypeAuthCaptureRequest):
			strType = "Capture";
				break;
			case (FinancialTransaction.transTypeVoidTransactionRequest):
			strType = "Void Transaction";
				break;
			case (FinancialTransaction.transTypeCreditRequest):
				strType = "Credit";
				break;
		
		}
		
	}else
	{
		//CK or PO FOP
		strType = "Check/PO";
	}
	String when = "";
	when = item.created_on.substring(0,item.created_on.indexOf(" "));
		when = when+"&nbsp;"+item.created_on.substring(item.created_on.indexOf(" ")+1,item.created_on.indexOf("."));
		if(item.created_by.equalsIgnoreCase("NULL") 
			|| item.created_by.equalsIgnoreCase("dbo") 
			|| item.created_by.equalsIgnoreCase("guest") 
			|| item.created_by.equalsIgnoreCase("system") 
			|| item.created_by.equalsIgnoreCase("sa") )
		{
			when = when+"<BR><B>(by&nbsp;System)";
		}else
		{
			when = when+"<BR><B>(by&nbsp;"+item.created_by+")";
		}


%><%= strType %></B></TD>
<TD align=center><fontx size=-2><%= when %></TD><TD align=center><fontx size=-2><B><%= item.status %></B></TD><TD align=center><fontx size=-2><B><%= item.proc_auth_code %></B></TD><TD align=center><fontx size=-2><B><%= item.proc_avs_code %></B></TD><TD align=center><fontx size=-2><%= item.proc_trans_id %></TD><TD align=center><fontx size=-2><%= item.old_trans_id %></TD><TD align=center><fontx size=-2><%= item.error_reponse %></TD><TD align=right><fontx size=-2><% if(item.status.equals("Approved")){%><%= formatter.format(item.amount) %><%}else{%>(Tried <%= formatter.format(item.amount) %>)<%}%></TD></TR>
<%		}
	}
%>
</TABLE>