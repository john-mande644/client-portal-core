<%@ page import="com.owd.core.business.Client,
                 com.owd.core.business.PackageRate,
                 com.owd.core.csXml.OrderRater,
                 java.util.List"%>
<%

	String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
	if(user_client_id == null) user_client_id = "-1";
	if(user_client_id.equals("")) user_client_id = "-1";
    String address1 = request.getParameter("address1")==null?"":request.getParameter("address1");
    String city = request.getParameter("city")==null?"":request.getParameter("city");
    String state = request.getParameter("state")==null?"":request.getParameter("state");
    String zip = request.getParameter("zip")==null?"":request.getParameter("zip");
    String country = request.getParameter("country")==null?"USA":request.getParameter("country");
    String weight_lbs = request.getParameter("weight_lbs")==null?"0.0":request.getParameter("weight_lbs");
    String type = request.getParameter("type")==null?"0":request.getParameter("type");

    List<PackageRate> resp = null;
    String rateError = null;

    try
    {
       float tFloat = new Float(request.getParameter("weight_lbs")).floatValue();
    }catch(Exception ex)
    {
        rateError = "Please provide a valid value for the weight of the package";
    }
    String rategetter = request.getParameter("Get Rates");
    if(rategetter==null)rategetter = "";
    if("Get Rates".equals(rategetter) && rateError == null)
    {

      try
      {
       resp = OrderRater.getRates(address1,
                city.length()>0?city:"nocity",
                state,
                zip,
                country,
                new Float(request.getParameter("weight_lbs")).floatValue(),
                "1".equals(type));


       }catch(Exception ex)
    {
            ex.printStackTrace();
        rateError = "Please provide a valid address for the package";
    }

    }



%>
<HTML>
<html>
<head>
<title>One World Direct Online Applications</title>

 <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
</head>

<body bgcolor=#ffffff>
<BASEFONT >


<H2>One World Direct Shipping Rate Comparison</H2>
<P>
<HR>
<B>[<A HREF="/webapps/clienttools/index.jsp">Return to main page</A>]</B>
<P>
<fontx size=+1><B>Tools and Resources</B><BR><P><BR>

<table width="100%" cellspacing=5>

<TR><TD COLSPAN=2><HR></TD></TR>
<TR><TD width=30% ALIGN=LEFT VALIGN=TOP><B>Compare Shipping Costs</B><BR><font size=-2>Review postage costs for historical orders<P>
</TD><TD ALIGN=LEFT VALIGN=TOP><% if (rateError != null && "Get Rates".equals(rategetter))
{
      %><font color=red><%= rateError%></font><%
}
    %>
<FORM>
<TABLE>
<TR><TD ALIGN=RIGHT NOWRAP>Destinations:</TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=TEXT NAME="address1" VALUE="<%= address1 %>"></TD></TR>

<TR><TD ALIGN=RIGHT NOWRAP>Weight Range:</TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=TEXT NAME="city" VALUE="<%= city %>"></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP>Shipping Methods:</TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=TEXT NAME="state" VALUE="<%= state %>"></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP>Zip/Postal Code:</TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=TEXT NAME="zip" VALUE="<%= zip %>"></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP>Address Type:</TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=CHECKBOX NAME="type" VALUE=1 <%= "1".equals(type)?"CHECKED":""%>>Commercial/Business Address<BR><font size=-2>Does not affect USPS rates</font></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP></TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=SUBMIT NAME="Get Rates" VALUE="Get Rates"></FORM></TD></TR>
</TABLE>
 </font>  <P>


<%
     if(resp != null)
     {


    	String bgcolor = "#E4ECF4";
	        java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");


%><B>Shipment Cost Check Results</B><HR><TABLE cellpadding=3 border=0 cellspacing=0><TR bgcolor=<%= bgcolor %>><TH ALIGN=LEFT>Predicted&nbsp;Rate</TH><TH NOWRAP>Shipping Method</TH><TH>Notes</TH></TR><%

    	for (PackageRate currShipment:resp)
        {

    if(
            !(currShipment.getMethodName().indexOf("Library")>=0
            || currShipment.getMethodName().indexOf("-Custom")>=0
            || currShipment.getMethodName().indexOf("-Post Office")>=0
            || currShipment.getMethodName().indexOf("Presorted")>=0
            || currShipment.getMethodName().indexOf("M-bags")>=0
            || currShipment.getMethodName().indexOf("BMC")>=0

            || (currShipment.getMethodName().indexOf("Overnight")>=0 && (currShipment.getMethodName().indexOf("FedEx")>=0))

            || currShipment.getMethodName().indexOf("5 Digit")>=0))
    {

    		if(bgcolor.equals("#E4ECF4")) bgcolor = "#FFFFFF";
    		else bgcolor = "#E4ECF4";

    %>
    <!-- err:<%= currShipment.getErrorCode() %> desc:<%= currShipment.getErrorMessage() %> -->
    <%

    	if(currShipment.getErrorCode()==0 || ( currShipment.getErrorCode()==1001 && currShipment.getMethodCode().indexOf("AIRBORNE")>=0))
    		{

    		float shiprate = new Float(currShipment.getFinalRate()).floatValue();

    		String rateStr;

    			rateStr = formatter.format(com.owd.core.OWDUtilities.roundFloat(shiprate,2));


       %>

    <TR bgcolor=<%= bgcolor %>><TD ALIGN=LEFT><%= rateStr %></TD><TD NOWRAP><%= (currShipment.getMethodName().indexOf("(")>=0?currShipment.getMethodName().substring(0,currShipment.getMethodName().indexOf("(")):currShipment.getMethodName())%></TD><TD></TD></TR>
    <%
    		}else
    		{
    %>

    <TR bgcolor=<%= bgcolor %>><TD ALIGN=LEFT>N/A</TD><TD NOWRAP><%= (currShipment.getMethodName().indexOf("(")>=0?currShipment.getMethodName().substring(0,currShipment.getMethodName().indexOf("(")):currShipment.getMethodName())%></TD><TD ><%= currShipment.getErrorMessage()+(currShipment.getErrorMessage().indexOf("ountry")>0?" or insufficent address information provided":"") %></TD></TR>
    <%
    		}
    	}
     }
         %></TABLE><%
     }
    %>


</TD></TR>
</TABLE>
    <!-- Begin Footer -->
    <HR ALIGN="center" SIZE="5" NOSHADE/>
    <fontx SIZE="1em">
        &nbsp;&nbsp;Copyright 2002-2018,
        <A HREF="http://www.owd.com/">
            One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;
        <script src="//rum-static.pingdom.net/pa-5b369fc56a549f00160000bc.js" async></script>
</BODY>
</HTML>
