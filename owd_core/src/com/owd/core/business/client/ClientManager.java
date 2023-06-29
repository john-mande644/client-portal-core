package com.owd.core.business.client;

import com.owd.LogableException;
import com.owd.core.MailAddressValidator;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import com.owd.core.business.intacct.ShippingAccountUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdClientAcctTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:41:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientManager {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);
        Date billDate = cal.getTime();
        
        Mailer.testingOnly = false;
     //   sendAMClientShippingStatusNotifications(billDate);
         Mailer.testingOnly = false;
        log.debug(getClientSupportBoxEmail(HibernateSession.currentSession(),402));
       /*
        try
        {
         //
        updateClientIntacctTransactions();
       // Mailer.testingOnly = false;
      //  sendAMClientShippingStatusNotifications(billDate);

          //  updateClientAccountHistory("VITA");
    }  catch(Exception ex)
        {
            ex.printStackTrace();
        }finally
        {
            HibernateSession.closeSession();
        }*/
     //  sendAMClientShippingStatusNotifications();
       // List<String> emails = new ArrayList<String>();
      //  emails.add("hello@owd.com");
      //  String emailStr = "amit@photojojo.com, store@photojojo.com, jen@photojojo.com";
      //  log.debug(""+parseEmailStringToList(emails,emailStr).size());
    }


     public static String getClientSupportBoxEmail(Session sess, int clientID) throws Exception {
        log.debug("getting email");
        String sql = "select address from vw_client_mailboxes where cid=convert(varchar,"+clientID+")";
        ResultSet rs = HibernateSession.getResultSet(sess, sql);
       String address ="customerservice@owd.com";
          if(rs.next())
        {
            address = rs.getString(1);
        }

        rs.close();
        return address;
    }

    public static void sendClientHTMLShippingStatusNotifications() {
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date billDate = cal.getTime();

            DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            List clients = ClientFactory.getAllClientsWithIntacctID(HibernateSession.currentSession());
            Iterator it = clients.iterator();
            while (it.hasNext()) {
                OwdClient client = (OwdClient) it.next();

            log.debug("getting account data for " + client.getCompanyName() + " (" + client.getShipperSymbol() + ")");

                StringBuffer message = new StringBuffer();
                String subject = "Shipping Status " + df.format(Calendar.getInstance().getTime()) + " : " + client.getCompanyName() + " (" + client.getClientId() + ")";
                message.append("<HTML>DAILY SHIPPING REPORT FOR " + client.getCompanyName().toUpperCase() + "<HR>");
                message.append("Reporting status and activities through the end of " + df.format(billDate) + "<P>");
                message.append("<B>Total Shipping Balance:</B> " + mf.format(client.getShippingBalance() - client.getUnbilledShipping()) + "\r\n");
                message.append("<P><B>Payments Received " + df.format(billDate) + ":</B><HR>");
                boolean gotInfo = false;
                if (client.getShippingBalance() < 0.00 || client.getUnbilledShipping() != 0.00)
                {
                    //gotInfo=true;
                }
                ResultSet rs = HibernateSession.getResultSet("select intacct_ref, total_amount, notes from owd_client_acct_transactions" +
                        " where transaction_date='" + df.format(billDate) + "' and transaction_type='Payment' and account_type='Shipping' and client_fkey=" + client.getClientId());
                while (rs.next()) {
                    gotInfo = true;
                    message.append(mf.format(rs.getDouble(2)) + " - " + rs.getString(1) + "/" + rs.getString(3) + "\r\n");
                }
                HibernateSession.closeStatement();
                message.append("<P><B>Transfers/Adjustments " + df.format(billDate) + ":</B><HR>");

                rs = HibernateSession.getResultSet("select intacct_ref, total_amount, notes from owd_client_acct_transactions" +
                        " where transaction_date='" + df.format(billDate) + "' and transaction_type='Adjustment' and account_type='Shipping' and client_fkey=" + client.getClientId());
                while (rs.next()) {
                    gotInfo = true;
                    message.append(mf.format(rs.getDouble(2)) + " - " + rs.getString(1) + "/" + rs.getString(3) + "\r\n");
                }
                HibernateSession.closeStatement();

                message.append("<P>Activity Summary " + df.format(billDate) + ":</B><HR>");

                rs = HibernateSession.getResultSet("select activity_name, sum(amount), is_inbound" +
                        " from owdbill_shipping_trans join owdbill_activity_types t on t.id=activity_type_fkey" +
                        " where recorded_date='" + df.format(billDate) + "' and client_fkey=" + client.getClientId() + " group by activity_name, is_inbound");
                while (rs.next()) {
                    gotInfo = true;
                    message.append(mf.format(rs.getDouble(2)) + " - " + rs.getString(1) + " " + (rs.getInt(3) == 1 ? "(Inbound)" : "") + "\r\n");
                }
                HibernateSession.closeStatement();

                if (gotInfo) {
                    ////log.debug("Subject:" + subject);
                    ////log.debug("Message:" + message);


                    Mailer.sendMail(subject, message.append("</HTML>").toString(), "owditadmin@owd.com", "htmlmail@owd.com");

                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            LogableException le = new LogableException(ex, ex.getMessage(),
                    "TS:"+Calendar.getInstance().getTimeInMillis(),
                    OWDUtilities.getStackTraceAsString(ex),
                    ClientManager.class.getName(),
                    LogableException.errorTypes.UNDEFINED_ERROR);
        } finally {
         //   // HibernateSession.closeSession();
        }
    }

    public static void sendClientShippingStatusNotifications() {
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date billDate = cal.getTime();

            DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            List clients = ClientFactory.getAllClientsWithIntacctID(HibernateSession.currentSession());
            Iterator it = clients.iterator();
            while (it.hasNext()) {
                OwdClient client = (OwdClient) it.next();

                //log.debug("getting account data for " + client.getCompanyName() + " (" + client.getShipperSymbol() + ")");

                StringBuffer message = new StringBuffer();
                String subject = "Shipping Status " + df.format(Calendar.getInstance().getTime()) + " : " + client.getCompanyName() + " (" + client.getClientId() + ")";
                message.append("DAILY SHIPPING REPORT FOR " + client.getCompanyName().toUpperCase() + "\r\n");
                message.append("Reporting status and activities through the end of " + df.format(billDate) + "\r\n\r\n");
                message.append("Total Shipping Balance: " + mf.format(client.getShippingBalance() - client.getUnbilledShipping()) + "\r\n");
                message.append("\r\nPayments Received " + df.format(billDate) + ":\r\n\r\n");
                boolean gotInfo = false;
                if (client.getShippingBalance() < 0.00 || client.getUnbilledShipping() != 0.00)
                {
                    //gotInfo=true;
                }
                ResultSet rs = HibernateSession.getResultSet("select intacct_ref, total_amount, notes from owd_client_acct_transactions" +
                        " where transaction_date='" + df.format(billDate) + "' and transaction_type='Payment' and account_type='Shipping' and client_fkey=" + client.getClientId());
                while (rs.next()) {
                    gotInfo = true;
                    message.append(mf.format(rs.getDouble(2)) + " - " + rs.getString(1) + "/" + rs.getString(3) + "\r\n");
                }
                HibernateSession.closeStatement();
                message.append("\r\nTransfers/Adjustments " + df.format(billDate) + ":\r\n\r\n");

                rs = HibernateSession.getResultSet("select intacct_ref, total_amount, notes from owd_client_acct_transactions" +
                        " where transaction_date='" + df.format(billDate) + "' and transaction_type='Adjustment' and account_type='Shipping' and client_fkey=" + client.getClientId());
                while (rs.next()) {
                    gotInfo = true;
                    message.append(mf.format(rs.getDouble(2)) + " - " + rs.getString(1) + "/" + rs.getString(3) + "\r\n");
                }
                HibernateSession.closeStatement();

                message.append("\r\nActivity Summary " + df.format(billDate) + ":\r\n\r\n");

                rs = HibernateSession.getResultSet("select activity_name, sum(amount), is_inbound" +
                        " from owdbill_shipping_trans join owdbill_activity_types t on t.id=activity_type_fkey" +
                        " where recorded_date='" + df.format(billDate) + "' and client_fkey=" + client.getClientId() + " group by activity_name, is_inbound");
                while (rs.next()) {
                    gotInfo = true;
                    message.append(mf.format(rs.getDouble(2)) + " - " + rs.getString(1) + " " + (rs.getInt(3) == 1 ? "(Inbound)" : "") + "\r\n");
                }
                HibernateSession.closeStatement();

                if (gotInfo) {
                    ////log.debug("Subject:" + subject);
                    ////log.debug("Message:" + message);
                    if (client.getShipInvoiceConfig().getNotifyFlag().intValue() == 1) {
                        try {
                            String emailList = client.getShipInvoiceConfig().getInvoiceEmails();
                            Vector ccs = new Vector();
                            if (emailList.indexOf("@") > 0) {

                                StringTokenizer tokens = new StringTokenizer(emailList, ",");

                                while (tokens.hasMoreTokens()) {

                                    String addr = tokens.nextToken();

                                    try {
                                        InternetAddress iAddr = new InternetAddress(addr);
                                    } catch (AddressException ea) {
                                        addr = null;
                                    }

                                    if (addr != null)

                                        ccs.addElement(addr);

                                }

                            }

                            Mailer.postMailMessage(subject, message.toString(), ccs, "do-not-reply@owd.com");
                        } catch (Exception exxx) {
                            exxx.printStackTrace();
                        }
                    }

                    Mailer.sendMail(subject, message.toString(), client.getAmEmail(), "testing@owd.com");
                    Mailer.sendMail(subject, message.toString(), "owditadmin@owd.com", "testing@owd.com");

                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            LogableException le = new LogableException(ex, ex.getMessage(),
                    "TS:"+Calendar.getInstance().getTimeInMillis(),
                    OWDUtilities.getStackTraceAsString(ex),
                    ClientManager.class.getName(),
                    LogableException.errorTypes.UNDEFINED_ERROR);
        } finally {
          //  // HibernateSession.closeSession();
        }
    }

    public static void sendAMClientShippingInvoices() {
         try {
             Calendar cal = Calendar.getInstance();
             cal.add(Calendar.DATE, -1);
             Date billDate = cal.getTime();

             DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
             DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

             List clients = ClientFactory.getAllClientsWithIntacctID(HibernateSession.currentSession());
             Iterator it = clients.iterator();
             while (it.hasNext()) {
                 OwdClient client = (OwdClient) it.next();

                 boolean gotInfo = false;


                 if(1==client.getShipInvoiceConfig().getInvoiceFlag().intValue())
                 {

                                                       double perDayAverage = 0.00;

                ResultSet summaryrs =HibernateSession.getResultSet("exec dbo.getshipdaysremaining "+client.getClientId());
                      if(summaryrs.next())
                      {
                          perDayAverage = summaryrs.getDouble(2);

                      }
                      HibernateSession.closeStatement();


                     double currBalance = client.getShippingBalance()-client.getUnbilledShipping();
                         double triggerLevel = client.getShipInvoiceConfig().getInvoiceTriggerLevel().doubleValue();
                         double invoiceAmount=0.00;
                     String invoiceID="";
                     String invNotes="";
                     String itemNotes="";

                     //log.debug("Testing "+client.getCompanyName()+" trigger "+triggerLevel+" balance "+currBalance);
                    if("WEEKLY".equals(client.getShipInvoiceConfig().getInvoiceTriggerType()))
                    {
                     //if billDate = invoiceDate in shippingtransactions, gather invoice info
                          String sql="select max(trans_fkey) from owdbill_shipping_trans where ";
                          if(currBalance <= triggerLevel && (!("".equals(client.getShipInvoiceConfig().getInvoiceAmountType()))))
                        {
                            //we are in the trigger zone
                            if("BALANCE".equals(client.getShipInvoiceConfig().getInvoiceAmountType()))
                            {
                                //log.debug("balance");
                              if(currBalance<0)
                              {


                                  invoiceAmount = (-1.00*currBalance);
                                  itemNotes="Current Balance Due";

                              }
                            } else if("FIXED_AMOUNT".equals(client.getShipInvoiceConfig().getInvoiceAmountType()))
                            {
                                //log.debug("fixed");
                                   invoiceAmount=client.getShipInvoiceConfig().getInvoiceAmount().doubleValue();
                                itemNotes = "Standard Funding Request";

                                if(invoiceAmount==0.00)
                                {
                                   invoiceAmount = invoiceAmount+(7.00*perDayAverage);
                                    itemNotes = "Average Weekly Shipping Cost (7 days x "+mf.format(perDayAverage)+"/day = "+mf.format(7.00*perDayAverage)+")";

                                }

                            } else if("DIFFERENCE".equals(client.getShipInvoiceConfig().getInvoiceAmountType()))
                            {
                                //log.debug("difference");
                                if(currBalance<triggerLevel)
                                {
                                 invoiceAmount=client.getShipInvoiceConfig().getInvoiceAmount().doubleValue()-currBalance;
                                    itemNotes="Amount to fund account to standard "+mf.format(triggerLevel)+" level";
                                }
                            } else
                            {
                                //log.debug("none");
                                //skip it

                            }


                        }

                    }else if("AT_TRIGGER_LEVEL".equals(client.getShipInvoiceConfig().getInvoiceTriggerType()))
                    {

                        if(currBalance <= triggerLevel && (!("".equals(client.getShipInvoiceConfig().getInvoiceAmountType()))))
                        {
                            //we are in the trigger zone
                            if("BALANCE".equals(client.getShipInvoiceConfig().getInvoiceAmountType()))
                            {
                                //log.debug("balance");
                              if(currBalance<0)
                              {


                                  invoiceAmount = (-1.00*currBalance);
                                  itemNotes="Current Balance Due";

                              }
                            } else if("FIXED_AMOUNT".equals(client.getShipInvoiceConfig().getInvoiceAmountType()))
                            {
                                //log.debug("fixed");
                                   invoiceAmount=client.getShipInvoiceConfig().getInvoiceAmount().doubleValue();
                                itemNotes = "Standard Funding Request";

                                if(invoiceAmount==0.00)
                                {
                                   invoiceAmount = invoiceAmount+(7.00*perDayAverage);
                                    itemNotes = "Average Weekly Shipping Cost (7 days x "+mf.format(perDayAverage)+"/day = "+mf.format(7.00*perDayAverage)+")";

                                }

                            } else if("DIFFERENCE".equals(client.getShipInvoiceConfig().getInvoiceAmountType()))
                            {
                                //log.debug("difference");
                                if(currBalance>0 && currBalance<triggerLevel)
                                {
                                 invoiceAmount=client.getShipInvoiceConfig().getInvoiceAmount().doubleValue()-currBalance;
                                    itemNotes="Amount to fund account to standard "+mf.format(triggerLevel)+" level";
                                }
                            } else
                            {
                                //log.debug("none");
                                //skip it

                            }


                        }

                    }




                 if (invoiceAmount>0.00) {
                     try {

                         String authString = OWDUtilities.encryptData(client.getClientId() + "/"
                                 +invoiceID + "/"
                                 +invoiceAmount + "/"
                                 +itemNotes + "/"
                                 +invNotes + "/"
                                 + df.format(billDate));

                         String text = "\r\nThis is an HTML or web-formatted email containing your shipping account invoice. If you are seeing this message, you may be unable" +
                                 " to view HTML mail. You can access your invoice at the URL below:" +
                                 "\r\n\r\nhttp://service.owd.com/webapps/accounts/shipinvoice.jsp?auth=" + URLEncoder.encode(authString) + "\r\n\r\n" +
                                 " If you continue to have problems, please contact your account manager at OWD for assistance.";


                         WebResource server = new WebResource("http://stewart.owd.com:8080/webapps/accounts/shipinvoice.jsp", WebResource.kGETMethod);
                         server.addParameter("auth", authString);

                         StringBuffer buffer = new StringBuffer();

                         BufferedReader in = server.getResource();
                         String inputLine;


                         while ((inputLine = in.readLine()) != null) {
                             buffer.append(inputLine + "\r");
                         }


                         String[] toAddress = new String[1];
                         toAddress[0] = "owditadmin@owd.com";

                         Mailer.sendHTMLMail("Shipping Invoice for " + client.getCompanyName() + " " + df.format(billDate), text, buffer.toString(), toAddress, null, null, "htmltest@owd.com");

                         if (client.getShipInvoiceConfig().getNotifyFlag().intValue() == 1) {

                             String toEmail = client.getShipInvoiceConfig().getNotifyEmails();

                             if (toEmail.indexOf("@") > 0) {

                                 StringTokenizer tokens = new StringTokenizer(toEmail, ",");
                                 toAddress = new String[tokens.countTokens()];
                                 int index = 0;
                                 while (tokens.hasMoreTokens()) {

                                     String addr = tokens.nextToken();

                                     try {
                                         InternetAddress iAddr = new InternetAddress(addr);
                                     } catch (AddressException ea) {
                                         addr = null;
                                     }

                                     if (addr != null) {
                                         toAddress[index] = addr;
                                     }
                                     index++;
                                 }

                             }

                             //log.debug("Sending HTML account email for "+toAddress[0]);
                           //  Mailer.sendHTMLMail("OWD Shipping Account Invoice for " + client.getCompanyName() + " " + df.format(billDate), text, buffer.toString(), toAddress, null, null, "do-not-reply@owd.com");

                         }

                     } catch (Exception ex) {
                         ex.printStackTrace();
                     }
                 }
             }

             }

         } catch (Exception ex) {
             ex.printStackTrace();
             LogableException le = new LogableException(ex, ex.getMessage(),
                     "TS:"+Calendar.getInstance().getTimeInMillis(),
                     OWDUtilities.getStackTraceAsString(ex),
                     ClientManager.class.getName(),
                     LogableException.errorTypes.UNDEFINED_ERROR);
         } finally {
          //   // HibernateSession.closeSession();
         }
     }
    public static void sendAMClientShippingStatusNotifications() {

           Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date billDate = cal.getTime();

        sendAMClientShippingStatusNotifications(billDate);

    }
    public static void sendAMClientShippingStatusNotifications(Date billDate) {
        try {

            log.debug("sending ship email notifications");
            DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            List clients = ClientFactory.getAllClientsWithIntacctID(HibernateSession.currentSession());
            Iterator it = clients.iterator();
            while (it.hasNext()) {
                log.debug("sending email");
                OwdClient client = (OwdClient) it.next();
                boolean gotInfo = false;
                log.debug("1");
                ResultSet rs = HibernateSession.getResultSet("select intacct_ref, total_amount, notes from owd_client_acct_transactions" +
                        " where transaction_date='" + df.format(billDate) + "' and transaction_type='Payment' and account_type='Shipping' and client_fkey=" + client.getClientId());
                while (rs.next()) {
                    gotInfo = true;
                }
                HibernateSession.closeStatement();
                log.debug("2");
                rs = HibernateSession.getResultSet("select intacct_ref, total_amount, notes from owd_client_acct_transactions" +
                        " where transaction_date='" + df.format(billDate) + "' and transaction_type='Adjustment' and account_type='Shipping' and client_fkey=" + client.getClientId());
                while (rs.next()) {
                    gotInfo = true;
                }
                HibernateSession.closeStatement();
                log.debug("3");
                rs = HibernateSession.getResultSet("select intacct_ref, total_amount, notes from owd_client_acct_transactions" +
                        " where transaction_date='" + df.format(billDate) + "' and transaction_type='Invoice' and account_type='Shipping' and client_fkey=" + client.getClientId());
                while (rs.next()) {
                    gotInfo = true;
                }
                HibernateSession.closeStatement();
                log.debug("4");

                rs = HibernateSession.getResultSet("select activity_name, sum(amount), is_inbound" +
                        " from owdbill_shipping_trans join owdbill_activity_types t on t.id=activity_type_fkey" +
                        " where recorded_date='" + df.format(billDate) + "' and client_fkey=" + client.getClientId() + " group by activity_name, is_inbound");
                while (rs.next()) {
                    gotInfo = true;
                }
                HibernateSession.closeStatement();
                log.debug("5");
                if (gotInfo) {
                    log.debug("gotinfo");
                    try {

                        String authString = OWDUtilities.encryptData(client.getClientId() + "/" + df.format(billDate) + "/" + df.format(billDate));

                        String text = "\r\nThis is an HTML or web-formatted email containing your shipping account statement. If you are seeing this message, you may be unable" +
                                " to view HTML mail. You can access your statement at the URL below:" +
                                "\r\n\r\nhttp://service.owd.com/webapps/accounts/shipstatement.jsp?auth=" + URLEncoder.encode(authString) + "\r\n\r\n" +
                                " If you continue to have problems, please contact your account manager at OWD for assistance.";


                        WebResource server = new WebResource("http://service.owd.com/webapps/accounts/shipstatement.jsp", WebResource.kGETMethod);
                        server.addParameter("auth", authString);

                        StringBuffer buffer = new StringBuffer();

                        BufferedReader in = server.getResource();
                        String inputLine;


                        while ((inputLine = in.readLine()) != null) {
                            buffer.append(inputLine + "\r");
                        }

                        log.debug(""+buffer);
                        List toAddress = new ArrayList<String>();
                        toAddress.add(client.getAmEmail());
                      //  toAddress[1] = "owditadmin@owd.com";

                        Mailer.sendHTMLMail("Account Statement for " + client.getCompanyName() + " " + df.format(billDate), text, buffer.toString(), toAddress.toArray(), null, null, "do-not-reply@owd.com");

                        if (client.getShipInvoiceConfig().getNotifyFlag().intValue() == 1) {

                            String toEmail = client.getShipInvoiceConfig().getNotifyEmails();

                            toAddress = parseEmailStringToList(toAddress, toEmail);

                            //  String[] bccs = new String[1];
                          //  bccs[0] = "owditadmin@owd.com";
                            log.debug("Sending HTML account email for "+client.getClientId()+" to "+toAddress);
                            Mailer.sendHTMLMail("OWD Shipping Account Statement for " + client.getCompanyName() + " " + df.format(billDate), text, buffer.toString(),
                                    toAddress.toArray(), null, null, "do-not-reply@owd.com");

                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        LogableException le = new LogableException(ex, ex.getMessage(),
                                "TS:"+Calendar.getInstance().getTimeInMillis(),
                                OWDUtilities.getStackTraceAsString(ex),
                                ClientManager.class.getName(),
                                LogableException.errorTypes.UNDEFINED_ERROR);

                    }
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            LogableException le = new LogableException(ex, ex.getMessage(),
                    "TS:"+Calendar.getInstance().getTimeInMillis(),
                    OWDUtilities.getStackTraceAsString(ex),
                    ClientManager.class.getName(),
                    LogableException.errorTypes.UNDEFINED_ERROR);
        } finally {
          //  // HibernateSession.closeSession();
        }
    }

    private static List parseEmailStringToList(List toAddress, String toEmail) {
        if (toEmail.indexOf("@") > 0) {

            StringTokenizer tokens = new StringTokenizer(toEmail, ",");

            int index = 0;
            while (tokens.hasMoreTokens()) {

                String addr = tokens.nextToken();

                try {
                    InternetAddress iAddr = new InternetAddress(addr);
                } catch (AddressException ea) {
                    addr = null;
                }

                if (addr != null) {
                    toAddress.add(addr.trim());
                }
                index++;
            }

        }
        return toAddress;
    }

    public static void updateClientIntacctTransactions() {
        try {

            List clients = ClientFactory.getAllClientsWithIntacctID(HibernateSession.currentSession());
            Iterator it = clients.iterator();
            while (it.hasNext()) {
                OwdClient client = (OwdClient) it.next();
                if(client.getClientId()>=0 && client.isIsActive())
                {
                log.debug("getting account data for " + client.getCompanyName() + " (" + client.getShipperSymbol() + ")");

                Map acctMap = ShippingAccountUtilities.getARCustomerData(client.getShipperSymbol());


                updateClientAccountHistory(client.getShipperSymbol());

           //log.debug("set "+intacctID+" ship_bal="+map.get(intacctID));
                if (acctMap.containsKey(client.getShipperSymbol() + "_1")) {
                    client.setShippingBalance(acctMap.containsKey(client.getShipperSymbol() + "_1") ? new Double(new Double((String) acctMap.get(client.getShipperSymbol() + "_1")).doubleValue() * (-1.00d)) : new Double(0.00d));
                }

                if (acctMap.containsKey(client.getShipperSymbol() + "_1limit")) {
                    //log.debug("checking credit limit "+client.getShipInvoiceConfig().getCreditLimit());
                    client.getShipInvoiceConfig().setCreditLimit(new BigDecimal((String) acctMap.get(client.getShipperSymbol() + "_1limit")));

                    //log.debug("saving credit limit "+(String) acctMap.get(client.getShipperSymbol() + "_1limit"));
                    //log.debug("checked credit limit "+client.getShipInvoiceConfig().getCreditLimit());
                    HibernateSession.currentSession().saveOrUpdate(client.getShipInvoiceConfig());
                }



                //     //log.debug("set "+intacctID+" bill_bal="+map.get(intacctID));
                //
                if (acctMap.containsKey(client.getShipperSymbol())) {
                    client.setBillingBalance(acctMap.containsKey(client.getShipperSymbol()) ? new Double(new Double((String) acctMap.get(client.getShipperSymbol())).doubleValue() * (-1.00d)) : new Double(0.00d));
                }

                if (acctMap.containsKey(client.getShipperSymbol() + "_1") || acctMap.containsKey(client.getShipperSymbol()))
                {
                    client.setLastIntacctUpdate(Calendar.getInstance().getTime());

                }
log.debug("set update time for "+client.getCompanyName()+" :"+client.getLastIntacctUpdate());
                HibernateSession.currentSession().update(client);
                HibernateSession.currentSession().flush();
                HibUtils.commit(HibernateSession.currentSession());
            }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            LogableException le = new LogableException(ex, ex.getMessage(),
                    "TS:"+Calendar.getInstance().getTimeInMillis(),
                    OWDUtilities.getStackTraceAsString(ex),
                    ClientManager.class.getName(),
                    LogableException.errorTypes.UNDEFINED_ERROR);
        } finally {
            // HibernateSession.closeSession();
        }
    }


    public static void updateClientAccountHistory(String intacctClientID) throws Exception {


        List transList = ShippingAccountUtilities.getNewARLedgerData(intacctClientID,false,200);

        log.debug("list=" + transList.size());

        try {

            String skippingID="SH_20060915";
            String skippingID2="SH_20060916";
            Iterator transactions = transList.iterator();
            while (transactions.hasNext()) {
                Map transMap = (Map) transactions.next();

                log.debug("checking ref "+((String) transMap.get("intacct_ref")));
                if(skippingID.equals((String) transMap.get("intacct_ref")) || skippingID2.equals((String) transMap.get("intacct_ref")) )
                {
                    //log.debug("skipped");
                }   else
                {
                  
                try {

                    OwdClient client = ClientFactory.getClientFromIntacctID(intacctClientID, HibernateSession.currentSession());
                    OwdClientAcctTransaction trans;


                    List transLister =  HibernateSession.currentSession().createQuery("from OwdClientAcctTransaction where intacctId=? and clientFkey=?")
                            .setInteger(0,new Integer((String)transMap.get("intacct_id")))
                            .setInteger(1,client.getClientId())
                            .list();


                    if(transLister.size() < 1)
                    {
                       trans = new OwdClientAcctTransaction();
                    } else
                    {
                        log.debug("got existing transaction for id "+transMap.get("intacct_id"));
                        trans = (OwdClientAcctTransaction) transLister.get(0);
                    }

                    trans.setIntacctId(Integer.parseInt((String) transMap.get("intacct_id")));
                    trans.setIntacctRef((String) transMap.get("intacct_ref"));
                    trans.setClientFkey(client.getClientId().intValue());
                    trans.setDescription((String) transMap.get("description"));
                    trans.setAccountType((String) transMap.get("account_type"));
                    trans.setNotes((String) transMap.get("notes"));
                    trans.setAppliedAmount(new BigDecimal((String) transMap.get("applied_amount")));
                    trans.setTotalAmount(new BigDecimal((String) transMap.get("total_amount")));
                    trans.setTransactionType((String) transMap.get("transaction_type"));
                    trans.setTransactionDate(OWDUtilities.getDateForSQLDateString((String) transMap.get("transaction_date")));
                    trans.setCreated(Calendar.getInstance().getTime());
                    //        //log.debug("set update time for "+client.getCompanyName()+" :"+client.getLastIntacctUpdate());
                    HibernateSession.currentSession().saveOrUpdate(trans);
                 //   HibernateSession.currentSession().flush();
                    HibUtils.commit(HibernateSession.currentSession());

                    //      client = ClientFactory.getClientFromIntacctID(searchID,HibernateSession.currentSession());
                    //      //log.debug("got update time for "+client.getCompanyName()+" :"+client.getLastIntacctUpdate());


                } catch (Exception ex) {
                    ex.printStackTrace();
                    HibUtils.rollback(HibernateSession.currentSession());
                    //client not found for intacct id!
                }
                }
            }

        //    HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
        }
        catch (Exception exx) {
            //  //log.debug("in exx thrower");
            throw exx;
        }

        finally {
            // HibernateSession.closeSession();
        }


    }


    public static int getKatrinaHoldStatus(int clientID) throws Exception {

        int result = 0;
        try {
            String sql = "select katrina_hold from  owd_client where client_id=" + clientID;
            ResultSet rs = HibernateSession.getResultSet(sql);
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }

        return result;

    }

    public static String getKatrinaHoldEmail(int clientID) throws Exception {
        String result = "";

        try {
            String sql = "select katrina_notify from  owd_client where client_id=" + clientID;
            ResultSet rs = HibernateSession.getResultSet(sql);
            while (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }

        return result;

    }

    public static void setKatrinaHoldStatus(int clientID, int newStatus) throws Exception {
        String sql = "update owd_client set katrina_hold=" + newStatus + " where client_id=" + clientID;

        if (newStatus != 1 && newStatus != 0) {
            throw new Exception("Invalid entry for katrina hold policy!");
        }
        try {
            PreparedStatement stmt = HibernateSession.getPreparedStatement(sql);
            stmt.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            // HibernateSession.closeSession();
        }

    }

    public static void setKatrinaHoldEmail(int clientID, String emailAddress) throws Exception {

        String sql = "update owd_client set katrina_notify=? where client_id=" + clientID;

        if (emailAddress != null) {
            if (emailAddress.trim().length() > 0) {
                MailAddressValidator.validate(emailAddress); //throws exception if bad

            }
        }

        try {
            PreparedStatement stmt = HibernateSession.getPreparedStatement(sql);
            stmt.setString(1, emailAddress);

            stmt.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            // HibernateSession.closeSession();
        }
    }


   /* public static Map getClientMethodMap(OwdClient client) {
        Map map = new TreeMap();
        Iterator it = client.getOwdClientMethods().iterator();
        while (it.hasNext()) {
            OwdClientMethod method = (OwdClientMethod) it.next();
            map.put(method.getMethodCode(), method.getDiscountPct());
        }

        return map;
    }
*/

    public static void saveOrUpdateClient(OwdClient client
            , String username) throws Exception {


        Session sess = HibernateSession.currentSession();
        client.setModifiedBy(username);
        client.setModifiedDate(Calendar.getInstance().getTime());

        if (client.getClientId() == null) {
            client.setCreatedBy(username);
            client.setCreatedDate(Calendar.getInstance().getTime());
        }
        sess.saveOrUpdate(client);
        //log.debug("updating items");
       /* Collection alist = client.getOwdClientMethods();
        //log.debug("currently have " + alist.size() + " ship methods");

        //log.debug("flush 1");
        sess.flush();

        Iterator it = sess.createQuery("FROM OwdClientMethod where client_fkey=" + client.getClientId()).iterate();
        while(it.hasNext())
        {
            sess.delete(it.next());
        }
        //log.debug("flush 2");
        sess.flush();*/

       /* Collection alist3 = client.getOwdClientMethods();
        //log.debug("currently have " + alist3.size() + " ship methods");
        Iterator itcm = client.getOwdClientMethods().iterator();
        while (itcm.hasNext()) {
            OwdClientMethod item = (OwdClientMethod) itcm.next();
            item.setOwdClient(client);

            sess.saveOrUpdate(item);

        }*/
        //log.debug("past method save");
        sess.saveOrUpdate(client.getShipInvoiceConfig());
        sess.saveOrUpdate(client.getPaypalData());
        sess.saveOrUpdate(client.getPackingInstructions());

        //log.debug("flush3");
        sess.flush();

       // Collection alist2 = client.getOwdClientMethods();
        //log.debug("currently have " + alist2.size() + " ship methods");
        //log.debug("commit");
        HibUtils.commit(sess);
    }


}







