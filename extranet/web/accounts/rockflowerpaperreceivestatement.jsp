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
<%@ page import="com.owd.hibernate.generated.Asn" %>
<%@ page import="com.owd.hibernate.generated.OwdClient" %>
<%@ page import="com.owd.hibernate.generated.ReceiveItem" %>
<%@ page import="com.owd.hibernate.generated.Receive" %>
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

<table cellpadding="0" border="0" width="100%">
    <tr>
        <td colspan=3 align=center><font size="+2"><strong>Symphony Receive Report<br></font><font size=+1>Rock Flower Paper</font></strong></font>
    </tr>

</table>
<hr>
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
                <td bgcolor="#EFEFEF"><strong>ASN ID</strong></td>
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


</body>
<%
    } catch (Exception ex) {
        throw ex;
    } finally {
        HibernateSession.closeSession();
    }
%>
</html>
