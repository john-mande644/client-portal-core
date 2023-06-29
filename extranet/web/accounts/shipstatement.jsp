<%@ page import="com.owd.core.OWDUtilities"%>
<%@ page import="com.owd.hibernate.HibernateSession"%>
<%@ page import="com.owd.hibernate.generated.OwdClient"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta name="generator" content="HTML Tidy for Mac OS X (vers 12 April 2005), see www.w3.org">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>Shipping Account Statement</title>
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
    try
    {
            String cid = request.getParameter("cid");

    String startDate = request.getParameter("sd");
    String endDate = request.getParameter("ed");

      if(request.getParameter("auth") != null)
      {

          String[] result = OWDUtilities.decryptData(request.getParameter("auth")).split("/");
     if(result.length == 3)
     {

        cid=result[0];
         startDate=result[1];
         endDate=result[2];
     }


      }else
      {

        cid = request.getParameter("cid");

    startDate = request.getParameter("sd");
    endDate = request.getParameter("ed");
      }

        if(cid==null)
        {
            cid = (String) request.getAttribute("cid");

        }
       ResultSet summaryrs = null;

         double currBalance=0.00;
        double perDayAverage = 0.00;
        int daysRemaining= 0;

        summaryrs =HibernateSession.getResultSet("exec dbo.getshipdaysremaining "+cid);
        if(summaryrs.next())
        {
           currBalance=summaryrs.getDouble(1);
            perDayAverage = summaryrs.getDouble(2);
            daysRemaining=summaryrs.getInt(3);

        }
        HibernateSession.closeStatement();

    summaryrs = HibernateSession.getResultSet("exec getshippingsummary '"+startDate+"','"+endDate+"',"+cid);

    OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class,new Integer(cid));
    DecimalFormat moneyFormat = new DecimalFormat("$#,###,##0.00");

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

%>
<body leftmargin="15" marginheight="0" marginwidth="15" topmargin="0" ><center >
<hr>
<table cellpadding="0" border="0" width="100%">
        <tr>
            <td colspan=3 align=center><font size="+2"><strong>Shipping Account Statement<br></font><font size=+1><%= startDate%> to <%= endDate%></strong></font>
        <tr>
            <td width="60%">
            <td width="7%">
            <td align="left" width="33%">
        <tr>
            <td align="left" width="60%" valign=top ><strong>
            Statement For:</strong><br>
                <%= client.getShipInvoiceConfig().getInvoiceToAddress().length()<1?client.getCompanyName():client.getShipInvoiceConfig().getInvoiceToAddress().replaceAll("\n","").replaceAll("\r","<br clear=\"all\">")%>
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
            </td></tr>
        </table>
    <hr>

    <table cellspacing="0"  width="100%" class="tableBorder2" cellpadding=4 cellspacing="5">
        <tr align="center" valign="top">
            <td width="34%" bgcolor="#AADDAA"><strong>Customer</strong>
            <td width="33%" bgcolor="#AADDAA"><strong>Account Number</strong>
            <td width="33%" bgcolor="#AADDAA"><strong>Statement Date</strong>
        <tr valign="top">
            <td align="center"><%= client.getCompanyName()%>
            <td align="center"><%= client.getClientId()+"-"+client.getShipperSymbol()+"_1"%>
            <td align="center"><%= startDate%> to <%= endDate%>
            </table>
    <p><br clear="all">
    <table border="0" width="100%">
        <tr>
           
            <td width="100%">
                <table border="0" cellpadding=4 cellspacing="3" width="100%" class="tableBorder2">
                    <tr bgcolor="#AADDAA" valign="top">
                        <th align="left" colspan=3>Account Summary    </th>

                        <th align="right" >Credit</th>

                        <th align="right" >Debit</th>

                        <th align="right" >Balance</th>
                        </tr>
                        <%
                            float runningTotal = 0.00f;
                            float creditTotal = 0.00f;
                            float debitTotal = 0.00f;

                            while(summaryrs.next())
                            {

                                if(summaryrs.getFloat(3) != 0.00f || "Previous Balance".equals(summaryrs.getString(2)))
                                {
                             runningTotal += summaryrs.getFloat(3);
                                    if("Previous Balance".equals(summaryrs.getString(2)))
                                    {
                                  %>
                                <tr>
                                    <td colspan="3" bgcolor="#EFEFEF"><strong>Previous Account Balance (before <%= startDate%>)</strong></td>
                                    <td  align="right" bgcolor="#EFEFEF"><strong></strong></td>
                                    <td  align="right" bgcolor="#EFEFEF"><strong></strong></td>
                                    <td  align="right" bgcolor="#EFEFEF"><strong><%= moneyFormat.format(runningTotal) %></strong></td>
                             </tr><%
                                    }   else
                            if(summaryrs.getFloat(3)>0)
                            {
                                creditTotal += summaryrs.getFloat(3);
                                //credit
                                %><tr>
                                    <td colspan="3"><%= summaryrs.getString(2)%></td>
                                    <td  align="right"><%= moneyFormat.format(summaryrs.getFloat(3))%></td>
            <td align=right></td>
            <td  align="right"><%= moneyFormat.format(runningTotal)%></td>
                        </tr><%
                            }   else
                            {
                                //debit
                    debitTotal += (-1.00f*summaryrs.getFloat(3));
                                %><tr>
                                    <td colspan="3"><%= summaryrs.getString(2)%></td>
            <td align=right></td>
                                    <td  align="right"><%= moneyFormat.format(-1.00f*summaryrs.getFloat(3))%></td>

            <td  align="right"><%= moneyFormat.format(runningTotal)%></td>
                        </tr><%
                            }
                }
                            }
                        %>
                                <tr>
                                    <td colspan="3" bgcolor="#EFEFEF"><strong>Total Account Balance (through <%= endDate%>)</strong></td>
                                    <td  align="right" bgcolor="#EFEFEF"><strong><%= moneyFormat.format(creditTotal) %></strong></td>
                                    <td  align="right" bgcolor="#EFEFEF"><strong><%= moneyFormat.format(debitTotal) %></strong></td>
                                    <td  align="right" bgcolor="#EFEFEF"><strong><%= moneyFormat.format(runningTotal) %></strong></td>
                             </tr>
                        </table> </td></tr>
            </table>
    <p><br clear="all">
    
    <% summaryrs.close();


    summaryrs = HibernateSession.getResultSet("exec getshippingsummaryintacct '"+startDate+"','"+endDate+"',"+cid);




    %>

        <font size="+1"><strong>Payments/Adjustments/Transfers</strong></font>
    <table cellpadding="0" border="0" width="100%">
        <tr align="left">
            <td></td></tr>
        <tr>
            <td align="left">
                <table border="0" cellpadding=4 cellspacing="3" width="100%" class="tableBorder2">
                    <tr align="left" valign="top">
                        <th bgcolor="#AADDAA"><strong>Type</strong></th>
                        <th bgcolor="#AADDAA"><strong>Date</strong></th>
                        <th  bgcolor="#AADDAA"><strong>Description</strong></th>
                        <th align="right" bgcolor="#AADDAA"><strong>Amount</strong></th>
                        </tr>
                 <%
                            float intacctTotal = 0.00f;

                            while(summaryrs.next())
                            {

                    intacctTotal += (summaryrs.getFloat(4));
                                %><tr>
                                    <td ><%= summaryrs.getString(1)%></td>
                    <td ><%= df.format(summaryrs.getDate(2))%></td>
            <td align=left><%= summaryrs.getString(3)%></td>
                                    <td  align="right"><%= moneyFormat.format(summaryrs.getFloat(4))%></td>

                        </tr><%

                }

                        %>
                                <tr>
                                    <td colspan="3" bgcolor="#EFEFEF"><strong>Total:&nbsp;(<%= startDate%> to <%= endDate%>)</strong></td>
                                    <td  align="right" bgcolor="#EFEFEF"><strong><%= moneyFormat.format(intacctTotal) %></strong></td>
                             </tr>
                    </table>
    </td></tr>
            </table>
    <p><br clear="all">
    <br clear="all">
     <% summaryrs.close();


    summaryrs = HibernateSession.getResultSet("exec getshippingsummaryfreightsummary '"+startDate+"','"+endDate+"',"+cid);




    %>
    <font size="+1"><strong>Freight/Postage Summary</strong></font>
    <table cellpadding="0" border="0" width="100%">
        <tr align="left">
            <td></td></tr>
        <tr>
            <td align="left">
                <table border="0" cellpadding=4 cellspacing="3" width="100%" class="tableBorder2">
                    <tr align="left" valign="top">
                        <th bgcolor="#AADDAA"><strong>Ship Method</strong></th>
                        <th align="right" bgcolor="#AADDAA"><strong>Packages</strong></th>
                        <th align="right" bgcolor="#AADDAA"><strong>Amount</strong></th>
                        </tr>
                 <%
                            int packageTotal = 0;
                            float freightSummaryTotal = 0.00f;

                            while(summaryrs.next())
                            {

                    packageTotal += (summaryrs.getInt(2));
                    freightSummaryTotal += (-1.00f*summaryrs.getFloat(3));
                                %><tr>
                                    <td ><%= summaryrs.getString(1)%></td>
            <td align=right><%= summaryrs.getInt(2)%></td>
                                    <td  align="right"><%= moneyFormat.format(-1.00f*summaryrs.getFloat(3))%></td>

                        </tr><%

                }

                        %>
                                <tr>
                                    <td  bgcolor="#EFEFEF"><strong>Totals:&nbsp;(<%= startDate%> to <%= endDate%>)</strong></td>
                                    <td  align="right" bgcolor="#EFEFEF"><strong><%= packageTotal %></strong></td>
                                    <td  align="right" bgcolor="#EFEFEF"><strong><%= moneyFormat.format(freightSummaryTotal) %></strong></td>
                             </tr>
                    </table>
    </td></tr>
            </table>
    <p><br clear="all">
    <br clear="all">

    <% summaryrs.close();


    summaryrs = HibernateSession.getResultSet("exec getshippingsummarynonfreight '"+startDate+"','"+endDate+"',"+cid);




    %>
    <font size="+1"><strong>Adjustments/Discounts/Guarantees</strong></font>
    <table cellpadding="0" border="0" width="100%">
        <tr align="left">
            <td></td></tr>
        <tr>
            <td align="left">
                <table border="0" cellpadding=4 cellspacing="3" width="100%" class="tableBorder2">
                    <tr align="left" valign="top">
                        <th bgcolor="#AADDAA"><strong>Type</strong></th>
                        <th bgcolor="#AADDAA"><strong>Date</strong></th>
                        <th  bgcolor="#AADDAA"><strong>OWD/Client Ref</strong></th>
                        <th  bgcolor="#AADDAA"><strong>Description</strong></th>
                        <th align="right" bgcolor="#AADDAA"><strong>Amount</strong></th>
                        </tr>
                 <%
                            float adjustSummaryTotal = 0.00f;

                            while(summaryrs.next())
                            {

                    adjustSummaryTotal += (-1.00f*summaryrs.getFloat(6));
                                %><tr>
                                    <td NOWRAP><%= summaryrs.getString(1)%></td>
            <td ><%= df.format(summaryrs.getDate(2))%></td>
                    <td ><%= summaryrs.getString(3)+"/"+summaryrs.getString(4)%></td>
                    <td ><%= summaryrs.getString(5)%></td>
                                    <td  align="right"><%= moneyFormat.format(-1.00f*summaryrs.getFloat(6))%></td>

                        </tr><%

                }

                        %>
                                <tr>
                                    <td colspan="4" bgcolor="#EFEFEF"><strong>Total:&nbsp;(<%= startDate%> to <%= endDate%>)</strong></td>
                                    <td  align="right" bgcolor="#EFEFEF"><strong><%= moneyFormat.format(adjustSummaryTotal) %></strong></td>
                             </tr>
                    </table>
    </td></tr>
            </table>
</center>

<p class="text2" align=left>This statement was automatically generated - if you have questions about it, please contact your Account Manager
        at 605-845-7172.<p class="text2" align=left>To contact your Account Manager via email, simply reply to this message or send a message to <A href="mailto:<%= client.getAmEmail()%>"><%= client.getAmEmail()%></a>.
    <p class="text2" align=center>Thank you for working with OWD!
  </p>
<hr>
<p class=trademarkText>One World Direct - 605-845-7172 - 10 1st Ave East - Mobridge - South Dakota - 57601</p>
</body>
<%
    }catch(Exception ex)
    {
        throw ex;
    }            finally
    {
        HibernateSession.closeSession();
    }
%>
    </html>
