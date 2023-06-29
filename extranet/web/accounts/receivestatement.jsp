<%@ page import="com.owd.core.OWDUtilities" %>
<%@ page import="com.owd.hibernate.*" %>
<%@ page import="com.owd.web.warehouse.asn.ASNHome" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.owd.hibernate.generated.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta name="generator" content="HTML Tidy for Mac OS X (vers 12 April 2005), see www.w3.org">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>OWD Receive Report</title>
<style>


INPUT {
    font: 7.5pt Verdana;
    color: #000000;
    width: 210px;
}

P {
    font: 8pt Verdana;
    color: #000000;
}

td {
    font: 8pt Verdana;
    color: #222222;
}

th {
    font: bold 8pt Verdana;
    color: #222222;

}

A {
    font: 8pt Verdana;
    color: #364FA7;
}

A:hover {
    text-decoration: none;
    color: #4F6D8F;
}

.td0 {
    BORDER: 0pt;
}

.text {
    font: 8pt Verdana;
    color: #888888;
}

.text2 {
    font: 11px Arial, Verdana;
    color: #333333;
    padding-left: 1px;
}

.text3 {
    font: 13px Arial, Verdana;
    color: #333333;
    padding-left: 1px;
}

.text2bold {
    font: bold 11px Arial, Verdana;
}

.textSmall {
    font: 7.5pt Verdana;
}

.padding {
    padding-left: 20pt;
    padding-right: 15pt;
}

.linkN {
    font: 8pt Verdana;
    text-decoration: none;
}

.linkS {
    font: 8pt Verdana;
    color: #9CCB39;
    text-decoration: underline;
}

.linkRight {
    font: 11px Arial, Verdana;
    text-decoration: underline;
}

.arrow {
    margin-left: 5px;
    vertical-align: middle;
}

.title {
    font: bold 8pt Verdana;
    color: #FFFFFF;
    margin-left: 0px;
    vertical-align: middle;
}

.title1 {
    font: bold 8pt Verdana;
    color: #333333;
    margin-top: 3px;
    vertical-align: middle;
}

.title2 {
    font: bold 7.5pt Verdana;
    color: #666666;
    margin-top: 3px;
    vertical-align: middle;
}

.titleLink {
    font: 7.5pt Verdana;
    icolor: #9CCB39;
    text-decoration: underline;
    padding-top: 0px;
    padding-left: 5px;
    padding-right: 10px;
    width: 180px;
    vertical-align: middle;
}

.subHeader {
    font: bold 8pt Verdana;
    color: #98CB00;
}

.subHeaderRight {
    font: bold 7.5pt Verdana;
    color: #98CB00;
}

.headerLine {
    border-top: 2px Solid #B2B2B2;
    border-bottom: 2px Solid #B2B2B2;
}

.trademarkText {
    font-family: Verdana, Helvetica, sans serif;
    font-weight: normal;
    font-size: 7pt;
    color: #000000;
    line-height: 11px
}

.tableBorder1 {
    Border-top: 0px Solid #AADDAA;
    border-right: 2px Solid #AADDAA;
    border-bottom: 2px Solid #AADDAA;
    border-left: 2px Solid #AADDAA;
}

.tableBorder2 {
    Border-top: 2px Solid #AADDAA;
    border-right: 2px Solid #AADDAA;
    border-bottom: 2px Solid #AADDAA;
    border-left: 2px Solid #AADDAA;
}


</style>
<%
    try {
        String cid = request.getParameter("cid");

        String receiveID = request.getParameter("rid");

        if (request.getParameter("auth") != null) {

            String[] result = OWDUtilities.decryptData(request.getParameter("auth")).split("/");
            if (result.length == 2) {

                cid = result[0];
                receiveID = result[1];
            }


        } else {

            cid = request.getParameter("cid");

            receiveID = request.getParameter("rid");
        }

        if (cid == null) {
            cid = (String) request.getAttribute("cid");

        }
        //    ResultSet summaryrs = null;

        // summaryrs =HibernateSession.getResultSet("exec dbo.getshipdaysremaining "+cid);
        //  if(summaryrs.next())
        //  {

        //   }
        //   HibernateSession.closeStatement();

        Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, new Integer(receiveID));
        OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, new Integer(cid));
        DecimalFormat moneyFormat = new DecimalFormat("$#,###,##0.00");

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        DateFormat dft = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);

%>
<body leftmargin="15" marginheight="0" marginwidth="15" topmargin="0">
<center>
<hr>
<center>
<table cellpadding="0" border="0" width="100%">
    <tr>
        <td colspan=3 align=center><font size="+2"><strong>OWD Receive Report<br></font><font size=+1></strong></font>
    <tr>
        <td width="60%">
            <td width="7%">
                <td align="left" width="33%">
    <tr>
        <td align="left" width="60%" valign=top><strong>
            Statement For:</strong><br>
            <%= client.getShipInvoiceConfig().getInvoiceToAddress().length() < 1 ? client.getCompanyName() : client.getShipInvoiceConfig().getInvoiceToAddress().replaceAll("\n", "").replaceAll("\r", "<br clear=\"all\">")%>
        </td>
        <td width="7%">
        <td width="23%"><strong>
            One World Direct</strong>
            <br>
            PO Box 6
            <br>
            Mobridge, SD 57601
            <br>
            605-845-7172
            <br>
            www.owd.com
            <br>
        </td>
    </tr>
</table>
<hr>

<table cellspacing="0" width="100%" class="tableBorder2" cellpadding=4 cellspacing="5">
    <tr align="center" valign="top">
        <td width="50%" bgcolor="#AADDAA"><strong>Customer</strong>
            <td width="50%" bgcolor="#AADDAA"><strong>Account Number</strong>
    <tr valign="top">
        <td align="center"><%= client.getCompanyName()%>
        <td align="center"><%= client.getClientId()+"-"+client.getShipperSymbol() %>
</table>
<p><br clear="all">
    <%

        Asn asn = rcv.getAsn();


    %>
<table border="0" width="100%">
    <tr>

        <td width="100%">
            <table border="0" cellpadding=4 cellspacing="3" width="100%" class="tableBorder2">
                <tr bgcolor="#AADDAA" valign="top">
                    <th align="left" colspan=2>ASN Information</th>

                </tr>
                <tr>
                    <td bgcolor="#EFEFEF"><strong>Online Links</strong></td>
                    <td align="left"><strong><A target="_newsummary"
                                                HREF="http://service.owd.com/webapps/warehouse/asn/edit?<%=ASNHome.kParamAdminAction%>=edit-old&asn_id=<%=asn.getId()%>">ASN
                        Summary (All Receives)</A>
                        &nbsp;|&nbsp;<A
                            HREF="http://service.owd.com/webapps/warehouse/asn/download/<%= (asn.getClientRef().length()>0?asn.getClientRef():asn.getId()+"").replaceAll("#","")+".csv" %>?<%=ASNHome.kParamAdminAction%>=download-asn">Excel
                        Download</A></strong></td>
                </tr>
                <tr>
                <td bgcolor="#EFEFEF"><strong>OWD ASN ID</strong></td>
                <td align="left"><strong><%= asn.getId() %>
                </strong></td>
            </tr>
                <tr>
                    <td bgcolor="#EFEFEF"><strong>Client ASN Reference</strong></td>
                    <td align="left"><strong><%= asn.getClientRef() %>
                    </strong></td>
                </tr>
                <tr>
                    <td bgcolor="#EFEFEF"><strong>Client PO Number</strong></td>
                    <td align="left"><strong><%= asn.getClientPo() %>
                    </strong></td>
                </tr>


                <tr>
                    <td bgcolor="#EFEFEF"><strong>Blind ASN?</strong></td>
                    <td align="left">
                        <strong><%= asn.getHasBlind() == 0 ? "No" : "Yes (additional charges will apply)" %>
                        </strong></td>
                </tr>

                <tr>
                    <td bgcolor="#EFEFEF"><strong>Shipper Name</strong></td>
                    <td align="left"><strong><%= asn.getShipperName() %>
                    </strong></td>
                </tr>
                <tr>
                    <td bgcolor="#EFEFEF"><strong>Expected On</strong></td>
                    <td align="left"><strong><%= df.format(asn.getExpectDate())%>
                    </strong></td>
                </tr>
                <tr>
                    <td bgcolor="#EFEFEF"><strong>Carrier</strong></td>
                    <td align="left"><strong><%= asn.getCarrier() %>
                    </strong></td>
                </tr>


                <tr>
                    <td bgcolor="#EFEFEF"><strong>ASN Notes/Instructions</strong></td>
                    <td align="left"><strong><%= asn.getNotes() %>
                    </strong></td>
                </tr>

            </table>
        </td>
    </tr>
</table>
<p><br clear="all">
<table border="0" width="100%">
    <tr>

        <td width="100%">
            <table border="0" cellpadding=4 cellspacing="3" width="100%" class="tableBorder2">
                <tr bgcolor="#AADDAA" valign="top">
                    <th align="left" colspan=2>Receive Information</th>

                </tr>
                <tr>
                    <td NOWRAP bgcolor="#EFEFEF"><strong>Inventory Availability</strong></td>
                    <td align="left">
                        <strong><%= rcv.getIsPosted() == 1 ? "Released for shipping" : "Must be released manually for shipping - click ASN detail lik above to release items" %>
                        </strong></td>
                </tr>

                <tr>
                    <td NOWRAP bgcolor="#EFEFEF"><strong>Receive Time</strong></td>
                    <td align="left"><strong><%=dft.format(rcv.getStartTimestamp()) %>
                        - <%= dft.format(rcv.getEndTimestamp()) %> by <%= rcv.getReceiveBy()%>
                        (<%= rcv.getBilledMinutes() %> billed minutes)</strong></td>
                </tr>

                <tr>
                    <td NOWRAP bgcolor="#EFEFEF"><strong>Received As</strong></td>
                    <td align="left"><strong><%= rcv.getCartonCount() %> carton(s), <%= rcv.getPalletCount() %>
                        pallet(s)</strong></td>
                </tr>
                <tr>
                    <td NOWRAP bgcolor="#EFEFEF"><strong>Count Method</strong></td>
                    <td align="left">
                        <strong><%= rcv.getCountMethod() == null ? "(Not specified)" : rcv.getCountMethod() %> / A
                            packing slip was <%= rcv.getIsPackSlipFound() == 1 ? "" : "not "%>
                            found. <% if (rcv.getIsPackSlipFound() == 1) {%>
                            It did <%= rcv.getIsPackSlipMatch() == 1 ? "" : "not "%> match the item counts.<% }
                            if (rcv.getScanDocs().size() > 0) {
                        %><br>Scanned Documents: <%
                            StringBuffer sb = new StringBuffer();
                            Collection scans = rcv.getScanDocs();
                            if (scans != null) {
                                Iterator it2 = scans.iterator();
                                while (it2.hasNext()) {
                                    ScanReceive sr = (ScanReceive) it2.next();

                                    String typeicon = "text.gif";
                                    if (sr.getName().toUpperCase().endsWith(".PDF")) {
                                        typeicon = "acrobat.gif";
                                    }
                                    sb.append("<A HREF=\"" + ("http://service.owd.com/webapps/warehouse/asn/edit?act=get-scan&auth=" + URLEncoder.encode(OWDUtilities.encryptData(rcv.getId()
                                            + "/" + sr.getName() + "/" + rcv.getClientFkey()), "UTF-8") + "\"><IMG SRC=\"" +
                                            "http://service.owd.com/webapps/images/" + typeicon + " \" border=\"0\">&nbsp;" + sr.getName() + "</A>" +
                                            (it2.hasNext() ? "<BR>" : "")));


                                }
                        %><%=sb.toString()%><%
                                }
                            }
                        %></strong></td>
                </tr>
                <tr>
                    <td NOWRAP bgcolor="#EFEFEF"><strong>Receive Status</strong></td>
                    <td align="left">
                        <strong><%= rcv.getIsPosted() == 1 ? "Released for shipping" : "Must be released manually for shipping" %>
                        </strong></td>
                </tr>
                <tr>
                    <td NOWRAP bgcolor="#EFEFEF"><strong>Notes</strong></td>
                    <td align="left"><strong><%= rcv.getNotes() %>
                    </strong></td>
                </tr>

            </table>
        </td>
    </tr>
</table>
<p><br clear="all">
<table border="0" width="100%">
    <tr>

        <td width="100%">
            <table border="0" cellpadding=4 cellspacing="3" width="100%" class="tableBorder2">
                <tr bgcolor="#FFFFFF" valign="top">
                    <th align="left" COLSPAN=5>Current Receive Details</th>

                    <th align="left" colspan=3>ASN Totals</th>
                </tr>
                <tr bgcolor="#AADDAA" valign="top">
                    <th align="left">SKU</th>

                    <th align="left">Description</th>
                    <th align="left">Total</th>
                    <th align="left">Damaged</th>
                    <th align="left">Stocked</th>
                    <th align="left">Expected</th>
                    <th align="left">Stocked</th>
                    <th align="left">Variance</th>
                </tr>
                <%
                    Collection items = rcv.getReceiveItems();
                    Iterator itri = items.iterator();
                    while (itri.hasNext()) {
                        ReceiveItem ri = (ReceiveItem) itri.next();
                        if (ri.getCountMethod() != null) {
                %>
                <tr>
                    <td bgcolor="#EFEFEF"><strong><%= ri.getAsnItem().getInventoryNum()%>
                    </strong></td>
                    <td><b><%= ri.getAsnItem().getTitle()%>
                    </b><br>Count
                        Method: <%= ri.getCountMethod() == null ? "(Not specified)" : rcv.getCountMethod() + " " + ri.getNotes()%>
                    </td>
                    <td align=right><%= ri.getQtyReceive() + ri.getQtyDamage()%>
                    </td>
                    <td align=right><%= ri.getQtyDamage()%>
                    </td>
                    <td align=right><%= ri.getQtyReceive()%>
                    </td>
                    <td align=right><b><%=ri.getAsnItem().getExpected()%>
                    </b></td>
                    <td align=right><b><%= ri.getAsnItem().getReceived()%>
                    </b></td>
                    <td align=right>
                        <b><%=(ri.getAsnItem().getReceived() - ri.getAsnItem().getExpected()) > 0 ? "<font color=blue>" : (ri.getAsnItem().getReceived() - ri.getAsnItem().getExpected()) < 0 ? "<font color=red>" : "<font color=black>"%><%=ri.getAsnItem().getReceived() - ri.getAsnItem().getExpected()%>
                        </b></font></td>

                </tr>
                <%
                        }

                    }
                %>
            </table>
        </td>
    </tr>
</table>
<p><br clear="all">
</center>

<p class="text2" align=left>This report was automatically generated - if you have questions about it, please contact
    your Account Manager
    at 605-845-7172.

<p class="text2" align=left>To contact your Account Manager via email, simply reply to this message or send a message to
    <A href="mailto:<%= client.getAmEmail()%>"><%= client.getAmEmail()%>
    </a>.

<p class="text2" align=center>Thank you for working with OWD!
</p>
<hr>
<p class=trademarkText>One World Direct - 605-845-7172 - 10 1st Ave East - Mobridge - South Dakota - 57601</p>
</body>
<%
    } catch (Exception ex) {
        throw ex;
    } finally {
        HibernateSession.closeSession();
    }
%>
</html>
