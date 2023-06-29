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

    String invoiceID = request.getParameter("iid");
    String amount = request.getParameter("amount");
    String itemNotes = request.getParameter("itemNotes");
    String invoiceNotes = request.getParameter("invNotes");
    String startDate = request.getParameter("invDate");

      if(request.getParameter("auth") != null)
      {

          String[] result = OWDUtilities.decryptData(request.getParameter("auth")).split("/");
     if(result.length == 6)
     {
        cid=result[0];
         invoiceID=result[1];
         amount=result[2];
         itemNotes=result[3];
         invoiceNotes=result[4];
         startDate=result[5];
     }


      }else
      {

         cid = request.getParameter("cid");

    invoiceID = request.getParameter("iid");
    amount = request.getParameter("amount");
    startDate = request.getParameter("invDate");
    itemNotes = request.getParameter("itemNotes");
    invoiceNotes = request.getParameter("invNotes");

      }

        if(cid==null)
        {
            cid = (String) request.getAttribute("cid");
        }
         if(invoiceID==null)
        {
            invoiceID="";
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

        if(invoiceID.length()>0)
        {
            summaryrs =HibernateSession.getResultSet("select activity_name, round(sum(amount),2) " +
            "from owdbill_shipping_trans join owdbill_activity_types att on att.id= activity_type_fkey" +
            " where client_fkey="+cid+" and trans_fkey=" + invoiceID+
            " group by activity_name with rollup" +
            " order by activity_name desc");
         }else
        {
            String setLevelSQL= "(select '"+itemNotes+"' as notes,"+amount+")UNION(select null as notes,"+amount+") order by notes desc";
            System.out.println(setLevelSQL);
             summaryrs =HibernateSession.getResultSet(setLevelSQL);
        }
    OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class,new Integer(cid));
    DecimalFormat moneyFormat = new DecimalFormat("$#,###,##0.00");

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);


%>
<body leftmargin="15" marginheight="0" marginwidth="15" topmargin="0" ><center >
<hr>
<table cellpadding="0" border="0" width="100%">
        <tr>
            <td colspan=3 align=center><font size="+2"><strong>Shipping Account Invoice</strong></font>
       <hr>


        <tr>
            <td align="left" valign=top width="47.5%"><strong>
            Invoice To:</strong><br>
                <%= client.getShipInvoiceConfig().getInvoiceToAddress().length()<1?client.getCompanyName():client.getShipInvoiceConfig().getInvoiceToAddress().replaceAll("\n","").replaceAll("\r","<br clear=\"all\">")%>
            </td>
            <td width="5%" valign=top><strong>From:&nbsp;</strong><br>
            <td width="47.5%" valign=top>
            One World Direct
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
    <p class="text2"> To avoid fulfillment service interruptions, your shipping account must be kept in a positive balance. This invoice reflects a request for payment to maintain your account; please refer to your shipping account statement for usage details or historical activity.</p>
   <p class="text2">
    <b>Your current shipping account balance is <%= moneyFormat.format(currBalance) %>. <%
        if(currBalance<0)
        {
           %><%
        }   else
        {
            %>Based on the last 30 days of activity, this represents approximately <%= daysRemaining %> day(s) of funding for your shipping account at an average usage rate of <%= moneyFormat.format(perDayAverage)%> per day.<%
        }
    %></b></p>
<p>
    <table cellspacing="0"  width="100%" class="tableBorder2" cellpadding=4 cellspacing="5">
        <tr align="center" valign="top">
            <td width="34%" bgcolor="#AADDAA"><strong>Customer</strong>
            <td width="33%" bgcolor="#AADDAA"><strong>Account Number</strong>
            <td width="33%" bgcolor="#AADDAA"><strong>Invoice Date</strong>
            <td width="33%" bgcolor="#AADDAA"><strong>Terms</strong>
        <tr valign="top">
            <td align="center"><%= client.getCompanyName()%>
            <td align="center"><%= client.getClientId()+"-"+client.getShipperSymbol()+"_1"%>
            <td align="center"><%= startDate%>
            <td align="center">&nbsp;On&nbsp;Receipt&nbsp;
            </table>
  <p>
    <table border="0" width="100%" >
        <tr>
           
            <td width="100%">
                <table border="0" cellpadding=4 cellspacing="3" width="100%" class="tableBorder2">
                    <tr bgcolor="#AADDAA" valign="top">
                        <th align="left" >Invoice Item(s)</th>

                        <th align="right" >Amount</th>
                        </tr>
                        <%

                            while(summaryrs.next())
                            {
                                if(summaryrs.getString(1)==null)
                                {
                                 %>    <tr>
                                    <td  colspan=2><hr></td>
                             </tr>
                                <tr>
                                    <td  bgcolor="#ffffff" align=right><strong>Total:&nbsp;&nbsp;</strong></td>
                                    <td  align="right" bgcolor="#ffffff"><strong><%= moneyFormat.format(summaryrs.getFloat(2)) %></strong></td>
                             </tr>
                    <%
                                }   else
                                {
   %><tr>
                    <td ><%= summaryrs.getString(1)%></td>

<td  align="right"><%= moneyFormat.format(summaryrs.getFloat(2))%></td>
        </tr>

                    <%

                                }


                            }
                        %>
                        </table> </td></tr>
            </table>
    <p>
    
    <% summaryrs.close();



    %>
</center>

<p class="text2" align=left>This invoice was automatically generated according to your policies on file at OWD. If you have questions about it, or need to set up payment arrangements, please contact your Account Manager
        or the OWD Controller at 605-845-7172.<p class="text2" align=left>To contact your Account Manager via email, simply reply to this message or send a message to <A href="mailto:<%= client.getAmEmail()%>"><%= client.getAmEmail()%></a>.
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
