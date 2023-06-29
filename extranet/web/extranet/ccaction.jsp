<%@ page import="com.owd.core.payment.FinancialTransaction" %>
<%@ page import="com.owd.extranet.servlet.ExtranetManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet"%>
<%@ page import="java.util.Iterator" %>
<%
 
      com.owd.core.business.order.OrderStatus order = (com.owd.core.business.order.OrderStatus)request.getAttribute(com.owd.extranet.servlet.OrdersManager.kCurrentOrder);
	java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");
	com.owd.core.business.Contact billc = order.billContact;
	com.owd.core.business.Address billa = order.billAddress;
    com.owd.core.business.Client client = com.owd.core.business.Client.getClientForID(order.client_id);
%>
<P>

<B>Payment Transactions for order <%= order.orderReference %> (<%= order.OWDorderReference %>)</B>
<HR><fontx COLOR=RED><B><%= request.getAttribute("formerror")==null?"":request.getAttribute("formerror") %></B> <BR>
<B><fontx SIZE=-1><A HREF="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.extranet.servlet.OrdersManager.kParamOrderEditAction%>&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">Return to Order Details</A></B>
<% if(client.pp_proc!=null&&client.pp_proc.length()>4){ %>
<FORM METHOD=POST ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.extranet.servlet.OrdersManager.kParamOrderDoCCAction%>&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
<INPUT TYPE=HIDDEN NAME="doccaction" VALUE="1">
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>" value="<%= ExtranetServlet.kParamChangeMgrAction %>" />
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>" value="<%= ExtranetManager.kOrdersMgrName %>" />
    <TABLE width=100% border=0>
<TR><TD width=20% align=right valign=top><fontx SIZE=-1><B>Credit Card Account :</TD><TD colspan=2><fontx SIZE=-1><% if ( order.transactions.size() > 0){%><INPUT TYPE="RADIO" NAME="useoriginalcc" VALUE="1" CHECKED>Use Original CC Info&nbsp&nbsp;<% if(ExtranetServlet.getSessionFlag(request,ExtranetServlet.kExtranetInternalFlag)){ %><INPUT TYPE=CHECKBOX NAME="bill.expoverride" VALUE="1">Use Expiration Date from below<% } %><BR>
<% } %><INPUT TYPE="RADIO" NAME="useoriginalcc" VALUE="0" <% if ( order.transactions.size() == 0){%>CHECKED<%}%>>Use New CC Info<BR><fontx SIZE=-2>If using new CC information, fill out the fields below with the information to use for this transaction. Some default values have been provided for your convenience.<HR></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Billing Name :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billc.name VALUE="<%= billc.name %>"></TD></TR>

<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Company Name :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billa.company_name VALUE="<%= billa.company_name %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Address Line 1 :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billa.address_one VALUE="<%= billa.address_one %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Postal Code :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billa.zip VALUE="<%= billa.zip %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>CC Number :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=cc_num VALUE=""></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>CVV Code Number :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=cc_cvv_code VALUE=""></TD></TR>

<%

    String expMonStr = "00";
	String expYrStr = "00";
	String allexpStr = "0000";
	if ( order.transactions.size() >0){
       try{
		allexpStr = ((FinancialTransaction)order.transactions.get(0)).cc_exp;
		expMonStr = allexpStr.substring(0,2);
		expYrStr = allexpStr.substring(2);
       }catch (Exception e){};
	
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
<OPTION VALUE=2006 <%= ("06".equals(expYrStr)?"SELECTED":"")%>>2006
<OPTION VALUE=2007 <%= ("07".equals(expYrStr)?"SELECTED":"")%>>2007
<OPTION VALUE=2008 <%= ("08".equals(expYrStr)?"SELECTED":"")%>>2008
<OPTION VALUE=2009 <%= ("09".equals(expYrStr)?"SELECTED":"")%>>2009
<OPTION VALUE=2010 <%= ("10".equals(expYrStr)?"SELECTED":"")%>>2010
<OPTION VALUE=2011 <%= ("11".equals(expYrStr)?"SELECTED":"")%>>2011
<OPTION VALUE=2012 <%= ("12".equals(expYrStr)?"SELECTED":"")%>>2012
<OPTION VALUE=2013 <%= ("13".equals(expYrStr)?"SELECTED":"")%>>2013
<OPTION VALUE=2014 <%= ("14".equals(expYrStr)?"SELECTED":"")%>>2014
<OPTION VALUE=2015 <%= ("15".equals(expYrStr)?"SELECTED":"")%>>2015
<OPTION VALUE=2016 <%= ("16".equals(expYrStr)?"SELECTED":"")%>>2016
<OPTION VALUE=2017 <%= ("17".equals(expYrStr)?"SELECTED":"")%>>2017
<OPTION VALUE=2018 <%= ("18".equals(expYrStr)?"SELECTED":"")%>>2018
<OPTION VALUE=2019 <%= ("19".equals(expYrStr)?"SELECTED":"")%>>2019
    <OPTION VALUE=2020 <%= ("20".equals(expYrStr)?"SELECTED":"")%>>2020
    <OPTION VALUE=2021 <%= ("21".equals(expYrStr)?"SELECTED":"")%>>2021
    <OPTION VALUE=2022 <%= ("22".equals(expYrStr)?"SELECTED":"")%>>2022
    <OPTION VALUE=2023 <%= ("23".equals(expYrStr)?"SELECTED":"")%>>2023


</SELECT></TD></TR>

<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD colspan=2 >&nbsp;</TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Amount :</TD><TD colspan=2><fontx SIZE=-1><B>$</B><INPUT SIZE=15 TYPE=TEXT NAME=amount VALUE="0.00"><B>&nbsp;USD</B></TD></TR>
<TR><TD width=20% align=right valign=top><fontx SIZE=-1><B>Type :</TD><TD colspan=2><fontx SIZE=-1><INPUT TYPE="RADIO" NAME="transtype" VALUE="1" CHECKED>Charge - Take from customer CC account<BR>
<INPUT TYPE="RADIO" NAME="transtype" VALUE="0">Credit - Give money to customer<BR>&nbsp;&nbsp;&nbsp;Transaction&nbsp;ID&nbsp;to&nbsp;credit:&nbsp;<INPUT TYPE="TEXT" NAME="creditTransID" VALUE=""></TD></TR>
<TR><TD></TD><TD colspan=2>&nbsp;<BR><INPUT TYPE=SUBMIT name=submit value="Attempt CC Transaction"></TD></TR>
<TR><TD colspan=3><HR></TD></TR>

</TABLE></FORM>

<hr>
<% }
System.out.println("chekc_Procccccc "+client.check_proc);
if("1".equals(client.do_echeck)){ %>
<FORM METHOD=POST ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.extranet.servlet.OrdersManager.kParamOrderDoCCAction%>&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
<INPUT TYPE=HIDDEN NAME="doccaction" VALUE="2">
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>" value="<%= ExtranetServlet.kParamChangeMgrAction %>" />
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>" value="<%= ExtranetManager.kOrdersMgrName %>" />

    <TABLE width=100% border=0>
<TR><TD width=20% align=right valign=top><fontx SIZE=-1><B>E-check Account :</TD><TD colspan=2><fontx SIZE=-1><% if ( order.transactions.size() > 0){%><INPUT TYPE="RADIO" NAME="useoriginalcc" VALUE="1" CHECKED>Use Original Check Info&nbsp&nbsp;<BR>  <% } %>
<INPUT TYPE="RADIO" NAME="useoriginalcc" VALUE="0" <%  if ( order.transactions.size() == 0){%>CHECKED<%}%>>Use New Check Info<BR><fontx SIZE=-2>If using new Check information, fill out the fields below with the information to use for this transaction. Some default values have been provided for your convenience.<HR></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Billing Name :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billc.name VALUE="<%= billc.name %>"></TD></TR>

<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Company Name :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billa.company_name VALUE="<%= billa.company_name %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Address Line 1 :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billa.address_one VALUE="<%= billa.address_one %>"></TD></TR>
    <TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>City :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billa.city VALUE="<%= billa.city %>"></TD></TR>
    <TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>State :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billa.state VALUE="<%= billa.state %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Postal Code :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=billa.zip VALUE="<%= billa.zip %>"></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Routing Number :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=ck_bank VALUE=""></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Account Number :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=ck_acct VALUE=""></TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD align=right><fontx SIZE=-2><B>Check Number :</TD><TD><INPUT SIZE=30 TYPE=TEXT NAME=ck_number VALUE=""></TD></TR>



<TR><TD align=right><fontx SIZE=-1><B>&nbsp;</TD><TD colspan=2 >&nbsp;</TD></TR>
<TR><TD align=right><fontx SIZE=-1><B>Amount :</TD><TD colspan=2><fontx SIZE=-1><B>$</B><INPUT SIZE=15 TYPE=TEXT NAME=amount VALUE="0.00"><B>&nbsp;USD</B></TD></TR>
<TR><TD width=20% align=right valign=top><fontx SIZE=-1><B>Type :</TD><TD colspan=2><fontx SIZE=-1><INPUT TYPE="RADIO" NAME="transtype" VALUE="5" CHECKED>Charge - Take from customer Checking account<BR>
    <!--
<INPUT TYPE="RADIO" NAME="transtype" VALUE="0">Credit - Give money to customer<BR>&nbsp;&nbsp;&nbsp;Transaction&nbsp;ID&nbsp;to&nbsp;credit:&nbsp;<INPUT TYPE="TEXT" NAME="creditTransID" VALUE="">--></TD></TR>
<TR><TD></TD><TD colspan=2>&nbsp;<BR><INPUT TYPE=SUBMIT name=submit value="Attempt E-Check Transaction"></TD></TR>
<TR><TD colspan=3><HR></TD></TR>

</TABLE></FORM>
<%
} %>

<fontx size=-1><B>Order Summary</B><HR>
<TABLE width="1%">
<TR>
<TD align=right NOWRAP><fontx  size=-2 ><B>Subtotal&nbsp;:&nbsp;</TD>
<TD align=left NOWRAP><fontx  size=-2 ><%= formatter.format(order.order_sub_total) %></TD>
</TR>
<TR>
<TD align=right NOWRAP><fontx  size=-2 ><B>Discount&nbsp;:&nbsp;</TD>
<TD align=left NOWRAP><fontx  size=-2 ><%= formatter.format(order.discount*-1) %></TD>
</TR>
<TR>
<TD align=right NOWRAP><fontx  size=-2 ><B>Tax&nbsp;:&nbsp;</TD>
<TD align=left NOWRAP><fontx  size=-2 ><%= formatter.format(order.tax_amount) %></TD>
</TR>
<TR>
<TD align=right NOWRAP><fontx  size=-2 ><B>S/H&nbsp;:&nbsp;</TD>
<TD align=left NOWRAP><fontx  size=-2 ><%= formatter.format(order.ship_handling_fee) %></TD>
</TR>
<TR>
<TD align=right NOWRAP><fontx  size=-2 ><B>Total&nbsp;:&nbsp;</TD>
<TD align=left NOWRAP><fontx  size=-2 ><%= formatter.format(order.order_total) %></TD>
</TR>
</TABLE><HR>
<fontx size=-1><B>Past Transactions</B>
<TABLE width=100%>
<TR bgcolor="#000000">
<TH style="color=#ffffff;"><fontx  size=-1 color="#ffffff">Type</TH>
<TH style="color=#ffffff;"><fontx  size=-1 color="#ffffff">When</TH>
<TH style="color=#ffffff;"><fontx  size=-1 color="#ffffff">Status</TH>
<TH style="color=#ffffff;"><fontx  size=-1 color="#ffffff">Auth Code</TH>
<TH style="color=#ffffff;"><fontx  size=-1 color="#ffffff">AVS</TH>
<TH style="color=#ffffff;"><fontx  size=-1 color="#ffffff">CVV</TH>
<TH style="color=#ffffff;"><fontx  size=-1 color="#ffffff">Trans ID</TH>
<TH style="color=#ffffff;"><fontx  size=-1 color="#ffffff">Old Trans ID</TH>
<TH style="color=#ffffff;"><fontx  size=-1 color="#ffffff">Message</TH>
<TH style="color=#ffffff;"><fontx  size=-1 color="#ffffff">Amount</TH></TR>
<%
	Iterator iter = order.transactions.iterator();
	while(iter.hasNext())
	{	  
		
		FinancialTransaction item = (FinancialTransaction) iter.next();
		if(0 == item.is_void)
		{
%>
<TR><TD align=left><fontx size=-2><B><%
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
			strType = "Capture Auth";
				break;
			case (FinancialTransaction.transTypeAuthCaptureRequest):
			strType = "Charge";
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
		 if(item.type.equals(FinancialTransaction.transTypeCheckTransactionRequest+"")) {
          strType="E-Check";
      }else{
        strType = "External";
      }
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
if(item.is_future_charge==1 && item.isProcessed()==false && ExtranetServlet.getSessionFlag(request,ExtranetServlet.kExtranetInternalFlag))
	{
	
		strType= "<A HREF=\""+request.getContextPath()+ExtranetServlet.kExtranetURLPath+"?"+com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction+"="+com.owd.extranet.servlet.OrdersManager.kParamOrderVoidCCAction+"&"+com.owd.extranet.servlet.OrdersManager.kparamTransactionID+"="+ item.trans_id+"&"+com.owd.extranet.servlet.OrdersManager.kparamOrderID+"="+ item.order_fkey+"\">&nbsp;Void&nbsp;</A>&nbsp;"+strType;
	}
	
	
	if(item.is_future_charge==1 && item.charge_status==1)

	{

		strType=strType+" (FlexPay Payment on "+item.trans_time.substring(0,item.trans_time.indexOf(" "))+")";

	}else if(item.is_future_charge==1 && item.charge_status==0)

	{

		strType=strType+" (FlexPay for "+item.charge_date.substring(0,item.charge_date.indexOf(" "))+")";

	}else if(item.is_future_charge==1 && item.charge_status==2)

	{

		strType=strType+" (Failed FlexPay on "+item.charge_date.substring(0,item.charge_date.indexOf(" "))+")";

	}


%><%= strType %></B></TD>
<TD align=center><fontx size=-2><%= when %></TD><TD align=center><fontx size=-2><B><%= item.status %></B></TD><TD align=center><fontx size=-2><B><%= item.proc_auth_code %></B></TD><TD align=center><fontx size=-2><B><%= item.proc_avs_code %></B></TD><TD align=center><fontx size=-2><B><%= item.proc_cvv_code %></B></TD><TD align=center><fontx size=-2><%= item.proc_trans_id %></TD><TD align=center><fontx size=-2><%= item.old_trans_id %></TD><TD align=center><fontx size=-2><%= item.error_reponse %></TD><TD align=right><fontx size=-2><% if(item.status.equals("Approved")){%><%= formatter.format(item.amount) %><%}else{%>(Tried <%= formatter.format(item.amount) %>)<%}%></TD></TR>
<%		}
	}
%>
</TABLE>