package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.jobs.OWDStatefulJob;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 27, 2005
 * Time: 10:57:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class LOOPOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    private static String[] ordersBulkLoadColumns = {"TxnId", "Customer", "Customer Account Number", "TxnDate", "RefNumber", "Class",
            "ARAccount", "BalanceRemaining", "Bill To Line1", "Bill To Line2", "Bill To Line3",
            "Bill To Line4", "Bill To City", "Bill To State", "Bill To Postal Code", "Bill To Country"
            , "Ship To Line1", "Ship To Line2", "Ship To Line3", "Ship To Line4", "Ship To City"
            , "Ship To State", "Ship To Postal Code", "Ship To Country", "PO Number", "Terms",
            "Sales Rep", "Ship Date", "Due Date", "Ship Method", "FOB", "Class", "Memo",
            "SalesTaxCode", "SalesTaxItem", "SalesTaxPercentage", "SalesTaxTotal", "Other"
            , "TxnLine Service Date", "TxnLine Quantity", "TxnLine Item", "TxnLine Description"
            , "TxnLine Other1", "TxnLine Other2", "TxnLine Cost", "TxnLine Amount", "TxnLine SalesTaxCode"
            , "TxnLine Class", "TxnLine TaxCode", "TxnLine CDNTaxCode", "SubTotal"};      //46

    static List<String> holdNames = new ArrayList<String>();

    {


    }

    public static void main(String[] args) throws Exception {

        //  internalExecute("2005-4-7");
        run();
    }

    public void internalExecute() {


        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", OWDConstants.SMTPServer);

            URLName url = new URLName("pop3", "pop.emailsrvr.com", -1, "INBOX", "loop@orders.owd.com", "one7172");


            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(false);
            Store popStore = mailSession.getStore(url);

            popStore.connect();
            Folder inbox = popStore.getDefaultFolder();
            if (inbox == null)
                throw new MessagingException("No default folder");

            inbox = inbox.getFolder("INBOX");
            if (inbox == null)
                throw new MessagingException("Invalid folder");

            inbox.open(Folder.READ_WRITE);

            Message[] messages = {};
            int count = inbox.getMessageCount();
            log.debug("avpi got " + count + " messages...");
            if (count > 0)
                messages = inbox.getMessages();

            for (int i = 0; i < messages.length; i++) {
                int newOrders = 0;

                try {
                    String subject = messages[i].getSubject();
                    log.debug("got message: " + subject);


                    Multipart mp = null;
                    int parts = 1;
                    try {
                        MimeMessage message = (MimeMessage) messages[i];
                        //log.debug("got javax.mail.Message content=" + message.getContentType());
                        mp = (Multipart) (((MimeMessage) message).getContent());
                        //log.debug("got message content");
                        parts = mp.getCount();
                        //log.debug("got message partcount=" + parts);
                        log.debug("got MimeMessage, partcount=" + parts + ", content-type=" + mp.getContentType() + ", subject=" + subject);
                    } catch (ClassCastException ecc1) {
                        //////log.debug("avpi imp got null mp");


                    }

                    InputStream in = null;
                    String partName = "";
                    for (int j = 0; j < parts; j++) {
                        log.debug("checking part " + j);
                        if (mp != null) {
                            MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(j);
                            //////log.debug("got message bodypart");


                            partName = part.getFileName();
                            if (partName == null) partName = "";

                            if (part.getContentType().indexOf("binhex") >= 0) {
                                in = new BinHex4InputStream(part.getInputStream());
                                //////log.debug(((com.owd.BinHex4InputStream)in).getHeader());
                            } else if (part.getContentType().indexOf("mixed") >= 0) {
                                //////log.debug(((com.owd.BinHex4InputStream)in).getHeader());
                                Multipart internalmp = (Multipart) part.getContent();
                                int internalparts = internalmp.getCount();
                                for (int k = 0; k < internalparts; k++) {
                                    MimeBodyPart internalpart = (MimeBodyPart) internalmp.getBodyPart(k);

                                    log.debug("got internal part " + k + " , encoding=" + internalpart.getEncoding() + ", content-type=" + internalpart.getContentType() + " , description=" + internalpart.getDescription() + " , disposition=" + internalpart.getDisposition() + ", size=" + internalpart.getSize() + ", name=" + internalpart.getFileName());
                                    if (internalpart.getFileName() != null)
                                    {
                                        if(internalpart.getFileName().toUpperCase().endsWith("TAB"))
                                        {
                                            log.debug(">>>Found file: "+internalpart.getFileName());
                                            partName = internalpart.getFileName();
                                            in = internalpart.getInputStream();
                                            k = internalparts;
                                            j = parts;
                                        }
                                    }

                                }
                                //log.debug("got message content");
                                parts = mp.getCount();
                            } else {
                                in = part.getInputStream();
                            }
                            //////log.debug("got inputstream");
                            log.debug("got part " + j + " , encoding=" + part.getEncoding() + ", content-type=" + part.getContentType() + " , description=" + part.getDescription() + " , disposition=" + part.getDisposition() + ", size=" + part.getSize() + ", name=" + part.getFileName());
                        } else {
                            in = messages[i].getInputStream();

                        }

                        try {
                            if (partName.toUpperCase().endsWith(".TAB")) {
                                StringBuffer sbx = new StringBuffer();

                                List results = processDataFile(new TabReader(new BufferedReader(new InputStreamReader(in)), true));

                                if (results.size() > 0) {
                                    newOrders = 1;
                                }
                                Iterator it = results.iterator();
                                while (it.hasNext()) {
                                    sbx.append("\r\n" + it.next());
                                }
                                Vector emailsx = new Vector();
                                log.debug(sbx);
                                // emailsx.add("frank@ph-ion.com");
                                emailsx.add("customer_service@looporganic.com");
                                emailsx.add("owditadmin@owd.com");
                                Mailer.postMailMessage("LOOP Organic Order Import (" + partName + ")", sbx.toString(), emailsx, "loopimports@owd.com");
                            } else {
                                //log.debug("Got non-CSV part");
                            }
                        } catch (Exception mimepartex) {
                            mimepartex.printStackTrace();
                        }
                    } //for each MIME part

                } catch (ClassCastException ecc) {
                    //messages[i].setFlag(Flags.Flag.DELETED,true);
                    ecc.printStackTrace();
                    StringBuffer sb = new StringBuffer();
                    String stamper = OWDUtilities.getSQLDateTimeForToday();
                    sb.append("\nException: " + ecc.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
                    //////////log.debug("CUTImporter stamper="+stamper);
                    ecc.printStackTrace();
                    //      Mailer.postMailMessage("AVPIImporter import error", sb.toString(), "owditadmin@owd.com", "owditadmin@owd.com");

                } catch (Exception ex) {

                    ex.printStackTrace();
                    StringBuffer sb = new StringBuffer();
                    String stamper = OWDUtilities.getSQLDateTimeForToday();
                    sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
                    //////////log.debug("CUTImporter stamper="+stamper);
                    ex.printStackTrace();
                    Vector emails = new Vector();
                    emails.add("owditadmin@owd.com");
                    //     Mailer.postMailMessage("AVPIImporter import error", sb.toString(), emails, "owditadmin@owd.com");
                }
                if (newOrders == 1) {
                    messages[i].setFlag(Flags.Flag.DELETED, true);
                }
            }    //for each message


            inbox.close(true);


        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            //  Mailer.postMailMessage("AVPIImporter import error", sb.toString(), "owditadmin@owd.com", "owditadmin@owd.com");
        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            //   Mailer.postMailMessage("AVPIImporter import error", sb.toString(), "owditadmin@owd.com", "owditadmin@owd.com");
        }

    }

    private static List processDataFile(DelimitedReader data) throws Exception {

        int importColumnCount = ordersBulkLoadColumns.length;
        List resultsList = new ArrayList();

        if (data.columnCount < importColumnCount) {
            throw new Exception("<B>There must be " + importColumnCount + " columns in the file; you provided " + data.columnCount
                    + " columns.<BR>Please check your file and try again.</B>");

        } else {
               String newOrderID = "badvalueXXX";

            for (int row = 0; row < data.getRowCount(); row++) {
                if(!(newOrderID.equals(data.getStrValue(0,row,""))))
                {
                newOrderID = data.getStrValue(0, row, "").trim();
                log.debug("processing order " + newOrderID);

                  resultsList.add(processOrder(data, row));
                }

            }
        }

        return resultsList;
    }

    private static List processOrder(DelimitedReader rdr, int startRow) {
        //returns list of two elements - client Order ID and response
        List response = new ArrayList();
        response.add(rdr.getStrValue(0, startRow, "NA"));
        //add new
        //log.debug("checking start row " + startRow);


        try {
            String shipmethod = "";
            Order order = new Order("409");
            int paymentType = 0;
            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.backorder_rule = OrderXMLDoc.kBackOrderAll;

                String newOrderID = rdr.getStrValue(0, startRow, "").trim();
             int row = startRow-1;

            while(newOrderID.equals(rdr.getStrValue(0, row+1, "").trim()) && row<(rdr.getRowCount()-1))
                {
                  row++;
                  newOrderID = rdr.getStrValue(0, row, "").trim();
                    log.debug("checking row #" + row + " for start row " + startRow);
                    if (row == startRow) {
                        order.order_refnum = rdr.getStrValue(4, row, "");

            order.ship_operator = rdr.getStrValue(31,row,"Email Import");
            order.po_num = rdr.getStrValue(24,row,"");
            order.order_type = rdr.getStrValue(5,row,"Email Import");



//response.add(order.order_refnum);
                        //log.debug("starting run");
                        String line1=rdr.getStrValue(8, row, "").trim();
                        String line2=rdr.getStrValue(9, row, "").trim();
                        String line3=rdr.getStrValue(10, row, "").trim();
                        String line4=rdr.getStrValue(11, row, "").trim();


                        if(isCompany(line2))
                        {
                            order.getBillingContact().setName(line2);

                                               order.getBillingAddress().company_name = line1;
                                               order.getBillingAddress().address_one = line3;
                                               order.getBillingAddress().address_two = line4;
                        } else
                        {
                            order.getBillingContact().setName(line1);

                                               order.getBillingAddress().company_name = "";
                                               order.getBillingAddress().address_one = line2;
                                               order.getBillingAddress().address_two = line3;
                        }


                        order.getBillingContact().email = rdr.getStrValue(2, row, "");
                        order.getBillingContact().phone = "";


                        order.getBillingAddress().city = rdr.getStrValue(12, row, "");
                        order.getBillingAddress().state = rdr.getStrValue(13, row, "");
                        order.getBillingAddress().zip = String.format("%5s", rdr.getStrValue(14, row, "").trim()).replace(' ', '0');
                        order.getBillingAddress().country = rdr.getStrValue(15, row, "USA");

                           line1=rdr.getStrValue(16, row, "").trim();
                         line2=rdr.getStrValue(17, row, "").trim();
                         line3=rdr.getStrValue(18, row, "").trim();
                         line4=rdr.getStrValue(19, row, "").trim();


                        if(isCompany(line2))
                        {
                            order.getShippingContact().setName(line2);

                                               order.getShippingAddress().company_name = line1;
                                               order.getShippingAddress().address_one = line3;
                                               order.getShippingAddress().address_two = line4;
                        } else
                        {
                            order.getShippingContact().setName(line1);

                                               order.getShippingAddress().company_name = "";
                                               order.getShippingAddress().address_one = line2;
                                               order.getShippingAddress().address_two = line3;
                        }

                            order.getShippingContact().email = order.getBillingContact().email;
                            order.getShippingContact().phone = "";

                            order.getShippingAddress().city = rdr.getStrValue(20, row, "");
                            order.getShippingAddress().state = rdr.getStrValue(21, row, "");
                            order.getShippingAddress().zip = String.format("%5s", rdr.getStrValue(22, row, "").trim()).replace(' ', '0');
                            order.getShippingAddress().country = rdr.getStrValue(23, row, "USA");

                        log.debug("shipaddress="+order.getShippingAddress().toString());

                        order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                        order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                        if("Dropship".equalsIgnoreCase(order.order_type))
                        {
                            order.setBillingAddress(order.getShippingAddress());
                            order.getBillingContact().setName(order.getShippingContact().getName());

                        }
                      //  order.total_shipping_cost = rdr.getFloatValue(30, row, 0.00f) + rdr.getFloatValue(37, row, 0.00f);



                        order.total_tax_cost = rdr.getFloatValue(36, row, 0.00f);

                    //    order.discount = -1 * rdr.getFloatValue(32, row, 0.00f);
                        order.getShippingInfo().comments = rdr.getStrValue(0, row, "");

                        shipmethod = rdr.getStrValue(29, row, "UPS Ground");


                    }
                      log.debug("checking row "+row+" for li");
                        //  //log.debug("colcount:"+rdr.getRowSize(row));
                        log.debug("Rowdata:"+rdr.getRawRow(row));

                            String sku = LineItem.getCleanSKU(rdr.getStrValue(40, row, ""));

                            // //log.debug("adding li" + rdr.getStrValue(7, row, "") + "," + rdr.getStrValue(5, row, ""));
                            if (sku.toUpperCase().contains("SHIPPING")) {

                                //ignore free- items, but add absolute value of price as order-level discount value
                                order.total_shipping_cost = rdr.getFloatValue(45,row,0.00f);

                            } else if (sku.toUpperCase().contains("DISCOUNT") ) {


                                order.discount = (-1.00f * Math.abs(rdr.getFloatValue(45, row, 0.00f))) + (-1.00f * Math.abs(order.discount));

                            //    PhionUtilities.getInstance().addItem(order, "DISCOUNT", rdr.getStrValue(5, row, ""), "0.00", "0.00", rdr.getStrValue(2, row, "") + " (" + rdr.getStrValue(7, row, "") + ")", "", "");


                            } else if (sku.toUpperCase().contains(" FEE")) {

                                  order.addLineItem("FEE",
                    ""+rdr.getIntValue(39,row,1),
                     ""+(rdr.getIntValue(45,row,0)/rdr.getIntValue(39,row,1)),
                    "0.00",
                    rdr.getStrValue(41,row,""),
                    "", "");



                            } else if (sku.toUpperCase().contains("SUBTOTAL")) {


                                //ignore

                            }
                            else {

                                // //log.debug("adding li" + rdr.getStrValue(8, row, "") + "," + rdr.getStrValue(6, row, ""));

                                if(sku.contains(":") && sku.startsWith("B"))
                                {
                                    sku = sku.substring(sku.indexOf(":")+1);
                            }
                                order.addLineItem(sku,
                                                 ""+rdr.getIntValue(39,row,1),
                                                  ""+(rdr.getIntValue(45,row,0)/rdr.getIntValue(39,row,1)),
                                                 "0.00",
                                                 rdr.getStrValue(41,row,""),
                                                 "", "");
                            }





            }




            order.recalculateBalance();


            order.paid_amount = order.total_order_cost;
            order.is_paid = 1;

         //   order.is_future_ship = 1;


                order.getShippingInfo().setShipOptions(shipmethod, "Prepaid", "");


            for (String name : holdNames) {                                                                   /**/
                putAddresseeOnHold(order, name);
            }
            order.recalculateBalance();
            log.debug("total:" + order.total_order_cost + "::paid:" + order.paid_amount);
            String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
            if (reference == null) {
                String notice = "The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".";
                //log.debug(notice);

                throw new Exception(notice);
            } else {
                log.debug("Created order "+reference);
            }

            response.add("Confirmed as Reference: " + reference);
            if (order.is_backorder == 1) {
                response.set(1, response.get(1) + "; Backorder created");
            }


        } catch (Exception
                ex) {
            ex.printStackTrace();
            response.add(" " + ex.getMessage());
            //response.add("<font color=red>" + ex.getMessage() + "</font>");
        } finally {


        }
        return response;
    }

    private static boolean isCompany(String line2) throws Exception {
        boolean hasCompany = true;
        if(line2.length()>0)
        {
            String fchar = line2.substring(0,1);
           // log.debug("fchar="+fchar);
            try
            {
                int testNum = Integer.parseInt(fchar);
                hasCompany = false;
                }
                      catch(Exception ex)
                      {
                          log.debug("NAN!!!");
            }
        }   else
        {
            throw new Exception("Cannot read valid value for address line 2");
        }
        return hasCompany;
    }

    private static void putAddresseeOnHold(Order order, String testname)
            throws Exception {
        String cappedtestName = testname.toUpperCase();

        if (order.getBillingContact().getName().toUpperCase().indexOf(cappedtestName) >= 0
                || order.getBillingAddress().company_name.toUpperCase().indexOf(cappedtestName) >= 0
                || order.getShippingContact().getName().toUpperCase().indexOf(cappedtestName) >= 0
                || order.getShippingAddress().company_name.toUpperCase().indexOf(cappedtestName) >= 0
                ) {
            order.is_future_ship = 1;
            Mailer.sendMail("LOOP " + testname + " order received on hold", "LOOP order " + order.order_refnum + " was received and placed on hold per the \"" + testname + "\" rule", "sstotz@owd.com", "do-not-reply@owd.com");
        }
    }



}
