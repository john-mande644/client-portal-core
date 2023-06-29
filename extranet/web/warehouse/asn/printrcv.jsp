<%@ page import="com.owd.core.business.Client,
                 com.owd.hibernate.generated.Asn,
                 com.owd.hibernate.generated.AsnItem,
                 com.owd.hibernate.HibernateSession,
                 com.owd.hibernate.generated.OwdInventory,
                 com.owd.web.warehouse.asn.ASNEditServlet,
                 com.owd.web.warehouse.asn.AsnItemSortComparator,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat"%>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.*" %>

<%
try
{String error = (String) request.getAttribute("errormessage");

     Asn asn = (Asn) request.getSession(true).getAttribute("asn.currentasn");

     DateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.US);

    ResultSet rs = HibernateSession.getResultSet("select distinct inventory_id, replace(isnull(pickString,''),'<BR>','::') from asn_items ai join owd_inventory i left outer join w_location wl on convert(varchar,wl.ixnode)=replace(front_location,'/','')\n" +
            "on ai.inventory_fkey=inventory_id\n" +
            "where\n" +
            "ai.asn_fkey="+asn.getId());
    Map<Integer,String> locMap = new HashMap<Integer,String>();

    while(rs.next())
    {
        locMap.put(rs.getInt(1),rs.getString(2));
    }

 %>
<HTML>
<HEAD></HEAD>
<link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
<body style="font-size:.75em">


<P><center><B>One World ASN Receive Sheet</B><BR><B><%= Client.getClientForID(asn.getClientFkey()+"").company_name%></B><BR>Receive onto this sheet before completing electronic receive form.<BR>Staple any packing slip/waybill to this sheet and file when done.</center>
<center><img src="http://internal.owd.com:8080/internal/bbq?data=<%= asn.getId()%>&width=2&type=Code128&height=15"/></center>
<HR><P>
<table border=0 ><TR><TD >
<table border=0 >
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP  bgcolor="eeeeee" ><B>OWD ASN ID:</B></TD><TD ALIGN=LEFT NOWRAP ><%= asn.getId() %></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP  bgcolor="eeeeee"><B>Client ASN Reference:</B></TD><TD ALIGN=LEFT NOWRAP ><%= asn.getClientRef() %></TD></TR>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP  bgcolor="eeeeee"><B>Shipper Name:</B></TD><TD ALIGN=LEFT NOWRAP ><%= asn.getShipperName() %></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP VALIGN=TOP  bgcolor="eeeeee"><B>Expected Date:</B></TD><TD ALIGN=LEFT NOWRAP  ><%= df.format(asn.getExpectDate())%></TD></TR>
</TABLE>

</TD><TD  ALIGN=LEFT width=100%></TD><TD  ALIGN=LEFT>
<table  width=100%>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="eeeeee" ><B>Carton/Package Count:</B></TD><TD ALIGN=LEFT NOWRAP  ><%= asn.getCartonCount() %></TD></TR>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP  bgcolor="eeeeee"><B>Pallet Count:</B></TD><TD ALIGN=LEFT NOWRAP  ><%= asn.getPalletCount() %></TD></TR>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP  bgcolor="eeeeee"><B>Client Supplier PO Number:</B></TD><TD ALIGN=LEFT NOWRAP ><%= asn.getClientPo() %></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP VALIGN=TOP  bgcolor="eeeeee"><B>Carrier:</B></TD><TD ALIGN=LEFT NOWRAP  ><%=asn.getCarrier()%></TD></TR>

</table>
</TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP COLSPAN=3 ><HR></td></tr>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP ><B>Client's Instructions/Notes:</B></TD><TD ALIGN=LEFT width="100%" colspan=2><%= asn.getNotes().replaceAll("\r\n","\n").replaceAll("\n","<BR>") %></td></tr>

</table>
<HR>Receive Notes:<P>&nbsp;<P>&nbsp;<P>&nbsp;<P>&nbsp;<P>Rcv By:________&nbsp;&nbsp;Start Date/Time:______________&nbsp;&nbsp;End&nbsp;Date/Time:______________&nbsp Cartons:________&nbsp;&nbsp;Pallets:________</B><P>
<table border=0 cellspacing=5 width="100%"><TR><TD><HR></TD><TD NOWRAP ALIGN=CENTER width=1% ><B>ASN Items</B></TD><TD><HR></TD></TR>
</table>

<table border=1 cellspacing=0 width="100%">

<TR><TH ALIGN=LEFT>SKU</TH><TH ALIGN=LEFT>Title</TH><!--<TH ALIGN=LEFT>Description</TH>--><TH ALIGN=LEFT>Units (Rem/Exp)</TH><TH ALIGN=LEFT>Counts</TH><TH>Weight</TH></TR>
        <%
              TreeSet<AsnItem> ss = new TreeSet<AsnItem>(new AsnItemSortComparator());
             ss.addAll(asn.getAsnItems());
            Iterator it = ss.iterator();
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
               String weigh = ASNEditServlet.returnNeedToWeigh(item.getInventoryFkey());
        %>
 <tr >

 <TD ALIGN=LEFT NOWRAP VALIGN=center bgcolor="eeeeee"><B><%= item.getInventoryNum() %>&nbsp &nbsp <%= weigh%><BR>
<%= item.getInventoryFkey() %>&nbsp;/&nbsp;<%= ((OwdInventory)HibernateSession.currentSession().load(OwdInventory.class,new Integer(item.getInventoryFkey()))).getFrontLocation() %></B><br>
<span class="asnbarcode"><img src="http://internal.owd.com:8080/internal/bbq?data=<%= item.getInventoryFkey()%>&width=0&type=Code39&height=15"/></span>
 </TD>
  <TD ALIGN=LEFT  valign=center ><%= item.getDescription().length()==0?item.getTitle():item.getDescription() %>
<!-- <TD ALIGN=LEFT  valign=center><%= item.getDescription()==null?"":item.getDescription()%>

</TD>-->
     <%
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,item.getInventoryFkey());
         if (inv.getHarmCode().length()>0){
     %>
     <br>
    Supplier SKU:  <%= inv.getHarmCode()%>
     <%
         }
     %>
  </TD>
  <TD ALIGN=CENTER NOWRAP valign=bottom ><B>&nbsp;<P><%= item.getExpected()-item.getReceived() %>/<%= item.getExpected() %></B></TD><TD ALIGN=RIGHT valign=bottom ><B>Count:______&nbsp;&nbsp;Damage:______</B><BR><%= locMap.get(item.getInventoryFkey())%></TD>
<TD ALIGN=CENTER valign=bottom><%= weigh.equals("Weigh")?"___ lbs":""%></TR>
 </TR>
 <% } %>


</table>



<HR>
<table class="info">
  <tr>
<td align="right">Rcv By:</td><td>______________</td><td align="right">  Start Date/Time:</td><td>______________</td><td align="right"> End Date/Time:</td><td>______________</td></tr><tr>
<td align="right">Stocked By:</td><td>______________</td><td align="right">  Start Date/Time:</td><td>______________</td><td align="right">  End Date/Time:</td><td>______________</td></tr><tr>
<td align="right">Scan By: </td><td>______________</td><td align="right"> Start Date/Time:</td><td>______________</td><td align="right">  End Date/Time:</td><td>______________</td></tr><tr>
<td align="right">Verified By:</td><td>______________</td><td align="right">  Start Date/Time:</td><td>______________</td><td align="right">  End Date/Time:</td><td>______________</td></tr>
</table>
</body>
</HTML>
<% }catch(Exception hex){ throw hex;}finally{HibernateSession.closeSession();}
    %>
