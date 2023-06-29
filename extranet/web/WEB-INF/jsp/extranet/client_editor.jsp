<%@ page import="com.owd.core.OWDUtilities"%>
<%
  	com.owd.core.business.Client client = (com.owd.core.business.Client)request.getAttribute(com.owd.web.servlet.ClientManager.kCurrentClientName);
 
                              
               
%>

<TABLE border=0><FORM METHOD=POST ACTION="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath %>?<%=com.owd.web.servlet.ClientManager.kParamClientMgrAction%>=<%=com.owd.web.servlet.ClientManager.kParamClientSaveAction%>">

<TR><TD COLSPAN=4><HR><INPUT TYPE=HIDDEN NAME="<%=com.owd.core.business.Client.kdbClientPKName%>" VALUE="<%=client.client_id%>">

<INPUT TYPE=SUBMIT NAME="<%=com.owd.web.servlet.ClientManager.kParamClientMgrSubAction%>" VALUE="<%=(client.isNew()?"Save New Client":"Save Changes") %>"><HR></TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Name:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=company_name size=32 VALUE="<%=client.company_name%>" >
</TD>
<TD ALIGN=RIGHT ><fontx  color="#000000" size="-2"><b>
&nbsp;&nbsp;Is Active:&nbsp;</B></fontx></TD><TD ALIGN=LEFT width=100 >
<INPUT TYPE=CHECKBOX NAME=is_active VALUE="1" 
<%= ("1".equals(client.is_active)?" CHECKED":"")%> >
</TD></TR>
<TR><TD COLSPAN=4> <P><BR><P> </TD></TR>
<TR><TD COLSPAN=4>Contact Information:<HR></TD></TR>
<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Address 1:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=address_one size=32 VALUE="<%=client.address_one%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Address 2:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=address_two size=32 VALUE="<%=client.address_two%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
City:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=city size=32 VALUE="<%=client.city%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
State:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=state size=32 VALUE="<%=client.state%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Zip:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=zip size=32 VALUE="<%=client.zip%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Country:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=country size=32 VALUE="<%=client.country%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Contact Name:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=contact_name size=32 VALUE="<%=client.contact_name%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Phone:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=primary_phone_num size=32 VALUE="<%=client.primary_phone_num%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Email:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=primary_email_address size=32 VALUE="<%=client.primary_email_address%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Fax:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=primary_fax_num size=32 VALUE="<%=client.primary_fax_num%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Web:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=url_string size=32 VALUE="<%=client.url_string%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Label Return Line 1<BR>(street address)<BR>Up to 30 characters&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=ret_addr_1 size=32 VALUE="<%=client.ret_addr_1%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Label Return Line 2<BR>Format as: (City, State Zip)<BR>Up to 30 characters:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=ret_addr_2 size=32 VALUE="<%=client.ret_addr_2%>" >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>


<TR><TD COLSPAN=4> <P><BR><P> </TD></TR> 




<TR><TD COLSPAN=4>Order Status Updates Configuration:<HR></TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Send&nbsp;Updates&nbsp;to&nbsp;Yahoo:&nbsp;</B></fontx></TD><TD COLSPAN=3 ALIGN=LEFT VALIGN=TOP>
<INPUT TYPE=CHECKBOX NAME=tell_yahoo VALUE="1" 
<%= ("1".equals(client.tell_yahoo)?" CHECKED":"")%> ><fontx size="-2"><b>&nbsp;&nbsp;Yahoo&nbsp;Password:&nbsp;</B></fontx><INPUT TYPE=TEXT NAME=yahoo_pass size=8 VALUE="<%=client.yahoo_pass%>" >
</TD></TR>
<!--
<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Send&nbsp;Updates&nbsp;to&nbsp;OrderTrust:&nbsp;</B></fontx></TD><TD COLSPAN=3 ALIGN=LEFT VALIGN=TOP>
<INPUT TYPE=CHECKBOX NAME=tell_ordertrust VALUE="1" 
<%= ("1".equals(client.tell_ordertrust)?" CHECKED":"")%> ><fontx size="-2"><b>&nbsp;&nbsp;OrderTrust&nbsp;ID:&nbsp;</B></fontx><INPUT TYPE=TEXT NAME=otrust_source size=6 VALUE="<%=client.otrust_source%>" >
</TD></TR> 
-->
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR> 
 <TR><TD COLSPAN=4> <P><BR><P> </TD></TR>
<TR><TD COLSPAN=4>Customer Emails Configuration:<HR></TD></TR>
<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Email Customers on Shipment:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=CHECKBOX NAME=ship_email VALUE="1" 
<%= ("1".equals(client.ship_email)?" CHECKED":"")%> >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-2"><b>
Return Email Address:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=ship_email_from size=32 VALUE="<%=client.ship_email_from%>" ><BR>
<fontx SIZE="-2">Only one address allowed</fontx>
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-2"><b>
CC Emails To:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=ship_email_cc size=60 VALUE="<%=client.ship_email_cc%>" ><BR>
<fontx SIZE="-2">Separate multiple addresses with a comma</fontx>
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>
 
<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-2"><b>
Bcc Emails To:&nbsp;</B></fontx></TD><TD ALIGN=LEFT >
<INPUT TYPE=TEXT NAME=ship_email_bcc size=60 VALUE="<%=client.ship_email_bcc%>" ><BR>
<fontx SIZE="-2">Separate multiple addresses with a comma</fontx>
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-2"><b>
Custom Email Footer:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<TEXTAREA NAME=ship_email_ftr ROWS=6 COLS=50><%=client.ship_email_ftr%></TEXTAREA>
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR> 
 <TR><TD COLSPAN=4> <P><BR><P> </TD></TR>
<TR><TD COLSPAN=4>Automatic Backorder Shipping Configuration:<HR></TD></TR>
<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
Ship Fillable Backorders Automatically:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=CHECKBOX NAME=is_backship VALUE="1" 
<%= ("1".equals(client.is_backship)?" CHECKED":"")%> >
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-2"><b>
When the backorder is the full, original order:&nbsp;</B></fontx></TD><TD ALIGN=LEFT><fontx size="-2">
<INPUT TYPE=RADIO NAME=original_ship_type VALUE=0 <%= ("0".equals(client.original_ship_type)?" CHECKED":"")%>>Use the original orders shipping method<BR>
<INPUT TYPE=RADIO NAME=original_ship_type VALUE=1 <%= ("1".equals(client.original_ship_type)?" CHECKED":"")%>>Use this shipping method:<%= OWDUtilities.getChoiceList("Service").getHTMLSelect(client.original_ship_carrier,"original_ship_carrier") %>
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-2"><b>
When the backorder is the final shipment of a partially fulfilled order:&nbsp;</B></fontx></TD><TD ALIGN=LEFT><fontx size="-2">
<INPUT TYPE=RADIO NAME=partial_ship_type VALUE=0 <%= ("0".equals(client.partial_ship_type)?" CHECKED":"")%>>Use the backorder's current shipping method<BR>
<INPUT TYPE=RADIO NAME=partial_ship_type VALUE=1 <%= ("1".equals(client.partial_ship_type)?" CHECKED":"")%>>Use this shipping method:<%= OWDUtilities.getChoiceList("Service").getHTMLSelect(client.partial_ship_carrier,"partial_ship_carrier") %>
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR> 
 <TR><TD COLSPAN=4> <P><BR><P> </TD></TR>
<TR><TD COLSPAN=4>Custom Shipping Accounts Configuration:<HR></TD></TR>
<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
FedEx Account Number:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=fedex_acct VALUE="<%= client.fedex_acct.trim() %>">
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

<TR><TD ALIGN=RIGHT><fontx size="-2"><b>
UPS Account Number:&nbsp;</B></fontx></TD><TD ALIGN=LEFT>
<INPUT TYPE=TEXT NAME=ups_acct VALUE="<%= client.ups_acct.trim() %>">
</TD>
<TD ALIGN=RIGHT ></TD><TD ALIGN=LEFT width=100 >
</TD></TR>

</FORM></TABLE> 

  