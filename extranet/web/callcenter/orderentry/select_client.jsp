<%@page import="com.owd.core.business.Client,
				java.util.ArrayList,
				java.util.Iterator"
%>
<%

	String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
	if(user_client_id == null) user_client_id = "-1";
	if(user_client_id.equals("")) user_client_id = "-1";

    String orderid = (String) request.getParameter("cloneorderid");


    request.setAttribute("clientselector", "");
    request.setAttribute("areatitle","Order Entry");
    String error = (String) request.getAttribute("errormessage");

%>

<jsp:include page="/extranettop.jsp" />


<center> <p>	<H2>OWD Order Internal Entry </H2>
	<p><font color="orange"><small><b>logged in as <%=request.getUserPrincipal().getName()%></b></small></font></center>

	<H3> Please Choose a Client: </H3>
    (Entries marked with a * have not had any custom rules applied)<P>
	<table border=0>  <tr>
<%
    System.out.println("In select client");
		ArrayList clients = Client.getActiveClientList(request.getUserPrincipal().getName());
    System.out.println("got active client list");
		Iterator i = clients.iterator();
        int client_count = clients.size();
		int column_count=1;
		while(i.hasNext())
		{
			ArrayList client = (ArrayList)i.next();
			String id = (String) client.get(0);//System.out.println("id: "+id+"\n");
			String name = (String)client.get(2);//System.out.println("name: "+name+"\n");
			if(id==null||name==null||("".equals(id))||("".equals(name)))
				continue;

			if(user_client_id.equals("0") || id.equals(user_client_id))
			{

                    if(column_count==1)
			{
		%>
				<td align=left valign=top nowrap>
		<%
			}
            System.out.println(name);
		%>

	<BR><a href="orderentry.jsp?selected_client=<%=id%>" target="_top"><b><%=name%></b></a>
		<%

			if(column_count==30)
			{
		%>
				</td>
		<%
				column_count=0;
			}
		%>
		<%
			column_count++;

		}
        }
		%>
        </tr>
	</table>
    <!-- Begin Footer -->
    <HR ALIGN="center" SIZE="5" NOSHADE/>

        &nbsp;&nbsp;Copyright 2002-2018,
        <A HREF="http://www.owd.com/">
            One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

</BODY>
</HTML>
