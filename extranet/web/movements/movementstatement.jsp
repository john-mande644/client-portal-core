<%@ page import="com.owd.core.OWDUtilities" %>
<%@ page import="com.owd.hibernate.HibernateSession" %>
<%@ page import="com.owd.hibernate.generated.OwdClient" %>
<%@ page import="org.apache.commons.beanutils.RowSetDynaClass" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta name="generator" content="HTML Tidy for Mac OS X (vers 12 April 2005), see www.w3.org">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>Inventory Movement Statement</title>
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

        String startDate = request.getParameter("sd");
        String endDate = request.getParameter("ed");

        if (request.getParameter("auth") != null) {

            String[] result = OWDUtilities.decryptData(request.getParameter("auth")).split("/");
            if (result.length == 3) {

                cid = result[0];
                startDate = result[1];
                endDate = result[2];
            }


        } else {

            cid = request.getParameter("cid");

            startDate = request.getParameter("sd");
            endDate = request.getParameter("ed");
        }

        if (cid == null) {
            cid = (String) request.getAttribute("cid");

        }
        ResultSet summaryrs = null;


        summaryrs = HibernateSession.getResultSet("exec sp_getinventorymovementsummary '" + startDate + "','" + endDate + "'," + cid);

        RowSetDynaClass resultSet = new RowSetDynaClass(summaryrs, false);
        int summaryrows = resultSet.getRows().size();
        request.setAttribute("results1", resultSet);

        OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, new Integer(cid));
        DecimalFormat moneyFormat = new DecimalFormat("$#,###,##0.00");

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

%>
<body leftmargin="15" marginheight="0" marginwidth="15" topmargin="0">
<center>
    <hr>
    <table cellpadding="0" border="0" width="100%">
        <tr>
            <td colspan=3 align=center><font size="+2"><strong>Inventory Movement Statement<br></font><font
                    size=+1><%= startDate%> to <%= endDate%></strong></font>
        <tr>
            <td width="60%">
            <td width="7%">
            <td align="left" width="33%">

    </table>
    <hr>


    <p><br clear="all">


        <font size="+1"><strong>Inventory Movement Summary</strong></font>

        <display:table name="requestScope.results1.rows" export="true" pagesize="10" id="mvsummary">
            <display:setProperty name="export.csv.filename" value="inventorysummary.csv"/>
            <display:setProperty name="export.amount" value="list"/>
            <display:column property="inventory_num" title="SKU"/>
            <display:column property="startqty" title="Starting Qty"/>
            <display:column property="received" title="Received"/>
            <display:column property="shipped" title="Shipped"/>
            <display:column property="returned" title="Returned"/>
            <display:column property="adjusted" title="Adjusted"/>
            <display:column property="damaged" title="Damaged"/>
            <display:column property="endqty" title="Ending Qty"/>
        </display:table>


    <p><br clear="all">
        <br clear="all">
            <% summaryrs.close();


    summaryrs = HibernateSession.getResultSet("exec sp_getinventorymovementdetail '"+startDate+"','"+endDate+"',"+cid);

 RowSetDynaClass resultSet2 = new RowSetDynaClass(summaryrs, false);
         summaryrows = resultSet2.getRows().size();
        request.setAttribute("results2", resultSet2);



    %>
        <font size="+1"><strong>Inventory Movement Detail</strong></font>

        <display:table name="requestScope.results2.rows" export="true" pagesize="20" id="mvdetails">
            <display:setProperty name="export.csv.filename" value="inventorydetail.csv"/>
            <display:setProperty name="export.amount" value="list"/>
            <display:column property="inventory_num" title="SKU" group="1"/>
            <display:column property="TransactionDate" title="Date" group="2"/>
            <display:column property="ClientReference" title="Reference"/>
            <display:column property="TransactionType" title="Type"/>
            <display:column property="QuantityChange" title="Quantity"/>
            <display:column property="Notes" title="Notes"/>
        </display:table>

    <p><br clear="all">
        <br clear="all">

            <% summaryrs.close();

    %>

</center>

<p class="text2" align=left>This statement was automatically generated - if you have questions about it, please contact
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
