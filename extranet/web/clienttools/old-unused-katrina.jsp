<%@ page import="com.owd.core.business.Client,
                 com.owd.core.business.client.ClientFactory,
                 com.owd.core.business.client.ClientManager,
                 com.owd.core.business.user.UserFactory,
                 com.owd.core.managers.ConnectionManager,
                 com.owd.hibernate.generated.OwdClient,
                 com.owd.hibernate.generated.OwdUser,
                 com.owd.web.warehouse.asn.ASNHome"%>
<%
	String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
    boolean isInternal = false;
    String errormessage="";
	if(user_client_id == null) user_client_id = "-1";
	if(user_client_id.equals("")) user_client_id = "-1";
    int clientIDInt = new Integer(user_client_id).intValue();
    
    if(clientIDInt==0)
    {
        isInternal = true;
        //is internal
        if(request.getParameter("internalid") != null)
        {
            clientIDInt=new Integer(request.getParameter("internalid")).intValue();

        }else
        {
          if(request.getParameter("idc") != null)
        {
            clientIDInt=new Integer(request.getParameter("idc")).intValue();

        }
        }
    }

    if(clientIDInt==0) clientIDInt=55;

    if(request.getParameter("savepolicy") != null)
        {
        if((clientIDInt+"").equals((String)request.getParameter("idc")))
        {
                        ClientManager.setKatrinaHoldStatus(new Integer(request.getParameter("idc")).intValue(),new Integer(request.getParameter("khold")).intValue());

            try
            {
            ClientManager.setKatrinaHoldEmail(new Integer(request.getParameter("idc")).intValue(),(String)request.getParameter("kemail"));
            }catch(Exception ex)
            {
                ex.printStackTrace();
                errormessage="The email address entered was not valid - please edit it and submit again."+(String)request.getParameter("kemail");
            }
        }
        }


    OwdClient client = ClientFactory.getClientFromClientID(new Integer(clientIDInt),clientIDInt,"0".equals(user_client_id));




    OwdUser ou = UserFactory.getOwdUserFromLogin(request.getUserPrincipal().getName());
    %>
<HTML>
<head>
<title>One World Direct Online Applications</title>

 <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
</head>

<body bgcolor=#ffffff>
<BASEFONT >


<H2>One World Direct Katrina Updates Page</H2>
<P>
<HR>
<B>[<A HREF="index.jsp">Return to main client tools page</A>]</B>
<P><BR>&nbsp;
<P>
Hurricane Katrina has left large populations of Mississippi, Alabama and Louisiana without basic services, including mail package delivery. The USPS is maintaining a list of
zip codes that have suspended delivery at this time. FedEx and UPS have not released any detailed information about areas with service interruptions.
</P><BR><P>
To help manage client shipments to the affected areas, OWD is providing the following services:
</P><BR><P><OL>
<LI>OWD is maintaining an internal list of the affected zip codes as reported by the USPS. This list will be updated weekly.
<LI>Clients can designate that any orders released for shipping to these areas will be held and not shipped without a positive confirmation.
<LI>Clients can request a daily report of all orders currently held by OWD under the zip code criteria to be sent via email.
<LI>Clients can release orders individually for shipping, place them on hold or void them via this page as they deem appropriate.
</OL></P><BR><P>
Please use the forms below to let us know how we should manage your Katrina-affected shipments.
<HR></P><BR><P>
<B>Shipping Account Statement for:
<% if (isInternal)
{
    %><FORM METHOD=POST ACTION="accounts.jsp"><SELECT NAME="internalid" onChange="this.form.submit()"><%= ASNHome.getClientSelector(request,"internalid",""+clientIDInt) %></SELECT></FORM><%
} else
{
  %><B><%= client.getCompanyName() %></B><%
}   %>
</B><FORM METHOD=POST ACTION="katrina.jsp"><INPUT TYPE=HIDDEN NAME=savepolicy VALUE=1>
<INPUT TYPE=hidden name="idc" value="<%= clientIDInt %>">
  <P>&nbsp;<BR>
Your current policy for orders shipping to affected areas is:   <P>&nbsp;<BR>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=RADIO NAME="khold" VALUE="0" <%= ClientManager.getKatrinaHoldStatus(clientIDInt)==0?"CHECKED":"" %>>Ship them anyway<BR>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=RADIO NAME="khold" VALUE="1" <%= ClientManager.getKatrinaHoldStatus(clientIDInt)==1?"CHECKED":"" %>>Hold affected orders<P>
                                                                       <P>&nbsp;<font color=red><%= errormessage %></font><BR>
The email address to send a daily report of held orders is: <INPUT TYPE=TEXT NAME=kemail VALUE="<%= ClientManager.getKatrinaHoldEmail(clientIDInt)%>" >
<P>&nbsp;<BR>
<INPUT TYPE=SUBMIT NAME="Update Katrina Policy" VALUE="Update Katrina Policy">
</FORM>                                                                     <P>&nbsp;<BR>
<H2>Orders currently being held at warehouse due to Katrina Policy</H2>
<table width=100% cellspacing=0 cellpadding=2 border=0>
<TR><TD colspan=6><HR></TD></TR>
<TR><% if (isInternal){%><TH align=left>Client</TH><%}%><TH align=left>Actions</TH><TH align=left>Order Ref / Client Order Ref</TH><TH align=left>Billing Info</TH><TH align=left>Shipping Info</TH></TR>
<TR><TD colspan=6><HR></TD></TR>
<%



	java.sql.Connection cxn = null;

	java.sql.PreparedStatement stmt = null;

	java.sql.ResultSet rs = null;
	String reportAction = "";
	String actionRequest = request.getParameter("ACTION");
	if(actionRequest == null) actionRequest = "";

	try{

	String sql = "select order_num+' / '+order_refnum+'<BR>Released: '+convert(varchar,post_date,101)," +
            "bill_first_name+' '+bill_last_name+'<BR>'+bill_company_name+'<BR>'+bill_address_one+'<BR>'+bill_address_two+'<BR>'+" +
            "bill_city+', '+bill_state+' '+bill_zip+'<BR>Phone: '+bill_phone_num+'<BR>Email: '+bill_email_address," +
            "ship_first_name+' '+ship_last_name+'<BR>'+ship_company_name+'<BR>'+ship_address_one+'<BR>'+ship_address_two+'<BR>'+"+
            "ship_city+', '+ship_state+' '+ship_zip+'<BR>Phone: '+ship_phone_num+'<BR>Email: '+ship_email_address,company_name from owd_print_queue_sl p join owd_order o " +
            " join owd_client on client_id=client_fkey" +
            " join owd_order_ship_info on order_fkey=o.order_id" +
            " on p.order_id=o.order_id where p.client_id="+clientIDInt+" order by company_name, order_num";

	cxn = ConnectionManager.getConnection();

			stmt = cxn.prepareStatement(sql);



			stmt.executeQuery();



			rs = stmt.getResultSet();


   String bgcolor="dddddd";

	while(rs.next())

	{
		if("dddddd".equals(bgcolor))
		{
			bgcolor="ffffff";

		}   else
		{
			bgcolor= "dddddd";
		}

%>


<TR bgcolor=<%= bgcolor %> ><% if (isInternal){%><TD><%= client.getCompanyName()%></TD><%}%><TD>None</TD><TD><%= rs.getString(1) %></TD><TD><%= rs.getString(2) %></TD><TD><%= rs.getString(3) %></TD></TR>

<%

	}





}catch(Throwable th)

{

	th.printStackTrace();

	%>

	<tr><td colspan=6>Error: <%= th.getMessage() %></td></tr>

	<%

}finally

{

	try{rs.close();}catch(Exception ex){}

	try{stmt.close();}catch(Exception ex){}

	try{cxn.close();}catch(Exception ex){}



}

%>

<TR><TD colspan=6><HR></TD></TR>
</table>
<P>
<fontx size=-1>For assistance, please send an email to One World technical support at <A HREF=mailto:casetracker@owd.com>casetracker@owd.com</A> or call the One World office at 605-845-7172 for help.
<P>
<HR>
<fontx size=-2>Copyright 2000-2005, One World Direct All Rights Reserved.
</body>
</html>
