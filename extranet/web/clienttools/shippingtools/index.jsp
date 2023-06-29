<%@ page import="com.owd.core.business.Client,
                 com.owd.core.business.PackageRate,
                 com.owd.core.csXml.OrderRater,
                 com.owd.extranet.servlet.ExtranetServlet,
                 com.owd.hibernate.HibernateSession,
                 com.owd.hibernate.generated.OwdClient"%>
<%@ page import="java.util.List" %>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>
<%@ page import="com.owd.hibernate.generated.OwdFacility" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.TreeMap" %>
<%

OrderRater rater = new OrderRater();

	String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
	if(user_client_id == null) user_client_id = "-1";
	if(user_client_id.equals("")) user_client_id = "-1";


    OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class,Integer.parseInt(user_client_id));

    String address1 = request.getParameter("address1")==null?"":request.getParameter("address1");
    String city = request.getParameter("city")==null?"":request.getParameter("city");
    String state = request.getParameter("state")==null?"":request.getParameter("state");
    String zip = request.getParameter("zip")==null?"":request.getParameter("zip");
    String country = request.getParameter("country")==null?"USA":request.getParameter("country");
    String weight_lbs = request.getParameter("weight_lbs")==null?"0.0":request.getParameter("weight_lbs");
    String type = request.getParameter("type")==null?"0":request.getParameter("type");
    String sat = request.getParameter("sat")==null?"0":request.getParameter("sat");

    Map<String,List<PackageRate>> facilityRates = new TreeMap<String,List<PackageRate>>();


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

          if (user_client_id.equals("0") || client.getDefaultFacilityCode().equals("ALL"))
{
    for(OwdFacility f: FacilitiesManager.getAllFacilities())
    {
        if(f.getIsActive()==1 && f.getIsPublic()==1)
        {
    facilityRates.put(f.getFacilityCode()+" - "+f.getMetroArea(),rater.getRates(address1,
                   city.length()>0?city:"nocity",
                   state,
                   zip,
                   country,
                   new Float(request.getParameter("weight_lbs")).floatValue(),
                   "1".equals(type),"1".equals(sat),(String)OrderRater.getShipperMap().get(f.getFacilityCode()),client.getClientId(),null));
        }
    }


}  else
          {
          OwdFacility f = FacilitiesManager.getFacilityForCode(client.getDefaultFacilityCode());

              facilityRates.put(f.getFacilityCode()+" - "+f.getMetroArea(),rater.getRates(address1,
                             city.length()>0?city:"nocity",
                             state,
                             zip,
                             country,
                             new Float(request.getParameter("weight_lbs")).floatValue(),
                             "1".equals(type),"1".equals(sat),(String)OrderRater.getShipperMap().get(client.getDefaultFacilityCode()),client.getClientId(),null));

          }
       }catch(Exception ex)
    {
            ex.printStackTrace();
        rateError = "Please provide a valid address for the package";
    }

    }



%><%

    ExtranetServlet.authenticateUser(request);
    request.setAttribute("clientselector","<FORM METHOD=POST ACTION=\""+ request.getContextPath() + ExtranetServlet.kExtranetURLPath +"\">"+ ExtranetServlet.getClientSelector(request) +"</FORM>");
    request.setAttribute("areatitle","Shipping Tools");
%>
<jsp:include page="/extranettop.jsp" />


<H2>One World Direct Shipping Tools</H2>
<P>
<HR>
<B>[<A HREF="/webapps/clienttools/index.jsp">Return to main page</A>]</B>
<P>
<fontx size=+1><B>Tools and Resources</B><BR><P><BR>

<table width="100%" cellspacing=5>
<TR><TD width="30%" ALIGN=LEFT VALIGN=TOP><A HREF="http://www.ups.com/" target = "_upsSite"><B>UPS&nbsp;Web&nbsp;Site</B></A> <BR><font size="-2">Official site for UPS services and information</font> <FORM ACTION="http://wwwapps.ups.com/WebTracking/processInputRequest" METHOD="GET" target="trackresult_"></TD><TD ALIGN=LEFT VALIGN=TOP NOWRAP>

<INPUT TYPE=HIDDEN NAME="sort_by" VALUE="status">
<INPUT TYPE=HIDDEN NAME="tracknums_displayed" VALUE="1">
<INPUT TYPE=HIDDEN NAME="TypeOfInquiryNumber" VALUE="T">
<INPUT TYPE=HIDDEN NAME="loc" VALUE="en_US">
<INPUT TYPE=HIDDEN NAME="track.x" VALUE="0">
<INPUT TYPE=HIDDEN NAME="track.y" VALUE="0">
<INPUT TYPE=TEXT NAME="InquiryNumber1" SIZE=30 VALUE=""><font size=-2>UPS Tracking Number (begins with "1Z")</font><BR><INPUT TYPE=SUBMIT NAME="Display Tracking Information for UPS Shipment" VALUE="Display Tracking Information for UPS Shipment"></FORM>
</TD></TR>
<TR><TD COLSPAN=2><HR></TD></TR>
<TR><TD width=30% ALIGN=LEFT VALIGN=TOP><A HREF="http://www.usps.gov/" target="_uspsSite"><B>USPS Web Site</B></A><BR><font size=-2>Official site for USPS services and information<BR></font>
</TD><TD ALIGN=LEFT VALIGN=TOP>
<FORM ACTION="http://trkcnfrm1.smi.usps.com/netdata-cgi/db2www/cbd_243.d2w/output" METHOD=GET target="trackresult_"><INPUT TYPE=HIDDEN NAME="CAMEFROM" VALUE="OK">
<INPUT TYPE=TEXT NAME="strOrigTrackNum" SIZE=30 VALUE=""><font size=-2>USPS Tracking/Delivery Confirmation Number</font><BR>
<INPUT TYPE=SUBMIT NAME="Display Tracking/Delivery Information for USPS Shipment" VALUE="Display Tracking/Delivery Information for USPS Shipment"></FORM>
</TD></TR>

<TR><TD COLSPAN=2><HR></TD></TR>
<TR><TD width=30% ALIGN=LEFT VALIGN=TOP><B>Compare Shipment Costs</B><BR><font size=-2>Predict and compare postage costs for a shipment<P>
</TD><TD ALIGN=LEFT VALIGN=TOP><% if (rateError != null && "Get Rates".equals(rategetter))
{
      %><font color=red><%= rateError%></font><%
}
    %>
<FORM>
<TABLE>
<TR><TD ALIGN=RIGHT NOWRAP>Street Address:</TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=TEXT NAME="address1" VALUE="<%= address1 %>"></TD></TR>

<TR><TD ALIGN=RIGHT NOWRAP>City:</TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=TEXT NAME="city" VALUE="<%= city %>"></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP>State/Region:</TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=TEXT NAME="state" VALUE="<%= state %>"></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP>Zip/Postal Code:</TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=TEXT NAME="zip" VALUE="<%= zip %>"></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP>Country:</TD><TD ALIGN=LEFT NOWRAP><%= com.owd.core.business.order.OrderUtilities.getCountryList().getHTMLSelect(country, "country") %></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP>Package Weight:</TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=TEXT NAME="weight_lbs" VALUE="<%= weight_lbs %>"><BR><font size=-2>in decimal lbs - for one-half pound enter 0.5</font></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP>Address Type:</TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=CHECKBOX NAME="type" VALUE=1 <%= "1".equals(type)?"CHECKED":""%>>Commercial/Business Address<BR><font size=-2>Does not affect USPS rates</font></TD></TR>
    <TR><TD ALIGN=RIGHT NOWRAP>Saturday Delivery:</TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=CHECKBOX NAME="sat" VALUE=1 <%= "1".equals(type)?"CHECKED":""%>>Check to request Saturday Delivery rates<BR><font size=-2>Does not affect USPS rates</font></TD></TR>

    <TR><TD ALIGN=RIGHT NOWRAP></TD><TD ALIGN=LEFT NOWRAP><INPUT TYPE=SUBMIT NAME="Get Rates" VALUE="Get Rates"></FORM></TD></TR>
</TABLE>
 </font>  <P>


<%
    for(String origin:facilityRates.keySet())
    {
%><h2>From <%= origin %></h2><%

    	String bgcolor = "#E4ECF4";
	        java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");


%><B>Shipment Cost Check Results</B><HR><TABLE cellpadding=3 border=0 cellspacing=0><TR bgcolor=<%= bgcolor %>><TH ALIGN=LEFT>Predicted&nbsp;Rate</TH><TH NOWRAP>Shipping Method</TH><TH>Notes</TH></TR><%

    	for (PackageRate currShipment:facilityRates.get(origin))
        {
    if(
            !(currShipment.getMethodName().indexOf("Library")>=0
            || currShipment.getMethodName().indexOf("-Custom")>=0
            || currShipment.getMethodName().indexOf("-Post Office")>=0
            || currShipment.getMethodName().indexOf("Presorted")>=0
            || currShipment.getMethodName().indexOf("M-bags")>=0
            || currShipment.getMethodName().indexOf("BMC")>=0

            //|| (currShipment.getMethodName().indexOf("Overnight")>=0 && (currShipment.getMethodName().indexOf("FedEx")>=0))

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
