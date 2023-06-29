<%@ page import="com.owd.core.business.Client,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 java.util.Iterator"%>
<%@ page import="java.util.Locale" %>
<%@ page import="com.owd.hibernate.*" %>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>
<%@ page import="com.owd.hibernate.generated.Asn" %>
<%@ page import="com.owd.hibernate.generated.AsnItem" %>
<%@ page import="com.owd.hibernate.generated.OwdClient" %>
<%@ page import="com.owd.hibernate.generated.OwdFacility" %>

<%
    if(request.getSession(true).getAttribute("owd_current_location")==null)
    {
        request.getSession(true).setAttribute("owd_current_location",request.getSession(true).getAttribute("owd_default_location"));
    }

	 String error = (String) request.getAttribute("errormessage");

     Asn asn = (Asn) request.getSession(true).getAttribute("asn.currentasn");

     DateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.US);

    OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class,asn.getClientFkey());

    String facilityCode = client.getDefaultFacilityCode();
    OwdFacility facility = null;
    if(facilityCode.equals("ALL"))
    {
        facility = FacilitiesManager.getFacilityForCode(asn.getFacilityCode());
    }   else
    {
        facility = FacilitiesManager.getFacilityForCode(facilityCode);
    }



 %>
<HTML>
<HEAD></HEAD>
<link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
<P><center><B>One World ASN Summary</B><BR><font size=-1>Include this sheet in your shipment to guarantee proper identification</font></center>
<P>
<table border=0 cellspacing=0 width="100%">
<TR><TD ALIGN=RIGHT  valign=top NOWRAP ><B>Shipping Address:</B></TD><TD ALIGN=LEFT valign=top width="100%"><HR>
    <B>Rcv:<%= Client.getClientForID(asn.getClientFkey()+"").company_name%><BR>ASN #<%= asn.getClientRef().length()<1?"OWD-"+asn.getId():asn.getClientRef() %><BR>
    <%= facility.getAddress()+"<BR>"+facility.getCity()+", "+ facility.getState()+" "+ facility.getZip() %>
    </B><P><HR></TD></TR>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP ><B>OWD ASN ID:</B></TD><TD ALIGN=LEFT width="100%"><%= asn.getId() %>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP ><B>Client ASN Reference:</B></TD><TD ALIGN=LEFT width="100%"><%= asn.getClientRef() %>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP ><B>Shipper Name:</B></TD><TD ALIGN=LEFT width="100%"><%= asn.getShipperName() %></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP VALIGN=TOP ><B>Expected Date:</B></TD><TD ALIGN=LEFT width="100%" ><%= df.format(asn.getExpectDate())%></TD></TR>
</TD></TR>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP ><B>Carton/Package Count:</B></TD><TD ALIGN=LEFT width="100%" ><%= asn.getCartonCount() %></TD></TR>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP ><B>Pallet Count:</B></TD><TD ALIGN=LEFT width="100%" ><%= asn.getPalletCount() %></TD></TR>

</TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP ><B>Client Supplier PO Number:</B></TD><TD ALIGN=LEFT width="100%"><%= asn.getClientPo() %>
</TD></TR>
</TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP ><B>Carrier:</B></TD><TD ALIGN=LEFT width="100%" ><%=asn.getCarrier()%>
</TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP ><B>Release Stock on Receipt:</B></TD><TD ALIGN=LEFT width="100%"><%= asn.getIsAutorelease().intValue()==1?"Auto Release":"Manual Release" %>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP ><B>Instructions/Notes:</B></TD><TD ALIGN=LEFT width="100%" ><%= asn.getNotes() %>

</table>

<table border=0 cellspacing=0 width="100%"><TR><TD><HR></TD><TD NOWRAP ALIGN=CENTER width=1% ><B>ASN Items</B></TD><TD><HR></TD></TR>
</table>

<table border=0 cellspacing=0 width="100%">
<TR><TH ALIGN=LEFT>SKU</TH><TH ALIGN=LEFT>Title</TH><TH ALIGN=LEFT>Description</TH><TH ALIGN=LEFT>Units</TH><TH ALIGN=LEFT></TH></TR>
        <%
            Iterator it = asn.getAsnItems().iterator();
            String currbg = "ffffff";
            int lines=0;
            int expected = 0;

            while(it.hasNext())
            {
                if(currbg.equals("ffffff"))
                {
                    currbg = "cccccc";
                }                     else
                {
                    currbg = "ffffff";
                }
               AsnItem item = (AsnItem) it.next();
                lines++;
                expected += item.getExpected();

        %>
 <tr bgcolor="<%= currbg %>">
 <TD ALIGN=LEFT NOWRAP ><B><%= item.getInventoryNum() %></B></TD>
  <TD ALIGN=LEFT NOWRAP ><%= item.getTitle()==null?"":item.getTitle() %></TD>
 <TD ALIGN=LEFT NOWRAP><%= item.getDescription()==null?"":item.getDescription()%></TD>
  <TD ALIGN=LEFT NOWRAP COLSPAN=2><%= item.getExpected() %></TD></TR>
 </TR>
 <% } %>
<tr bgcolor="ffffff">
 <TD ALIGN=LEFT NOWRAP colspan=3><B>SKUs: <%= lines%></B></TD>
  <TD ALIGN=RIGHT NOWRAP COLSPAN=2><B>Units:<%= expected %></B></TD></TR>
 </TR>

</table>


<!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
<fontx SIZE="1em">
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;
    <script src="//rum-static.pingdom.net/pa-5b369fc56a549f00160000bc.js" async></script>
    </BODY>
</HTML>
