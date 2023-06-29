package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.AWS_S3Api;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.batchimporters.RenuInfusionUploadData;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Feb 18, 2008
 * Time: 3:23:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class RenuHerbsInfusionSoftOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    Folder inbox = null;
    static int removeEmail = 0;

    public static void main(String[] args) {

     //   run();
      //  sendTeaLeavesEmail("vstotz@owd.com");

        sendHCGEmail("Vicki Stotz", "owditadmin@owd.com");

      //  sendHCGEmail("Vicki Stotz", "vstotz@owd.com");

    }

    public void internalExecute() {

        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", OWDConstants.SMTPServer);

            URLName url = new URLName("pop3", "secure.emailsrvr.com", -1, "INBOX", "renuorders@owd.com", "one7172");

            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(false);

            Store popStore = mailSession.getStore(url);


            popStore.connect();

            inbox = popStore.getDefaultFolder();

            if (inbox == null)
                throw new MessagingException("No default folder");
            //log.debug("hello");
            inbox = inbox.getFolder("INBOX");
            if (inbox == null)
                throw new MessagingException("Invalid folder");

            inbox.open(Folder.READ_WRITE);

            Message[] messages = {};
            int count = inbox.getMessageCount();
            log.debug("Receive got " + count + " messages for ...");
            if (count > 0)
                messages = inbox.getMessages();

            for (Message message1 : messages) {
                try {
                    removeEmail = 0;


                    String subject = message1.getSubject();
                    log.debug("got message: " + subject);

                    MimeMessage message = (MimeMessage) message1;
                    //log.debug("got javax.mail.Message content=" + message.getContentType());
                    log.debug(message.getContent().toString());
                    //Check subject for no good orders
                    if (!(subject.contains("Batch"))) {
                        log.debug("bad order email, remove it from the box ");
                        if(subject.contains("failed") || subject.contains("Offline"))
                        {
                           removeEmail = 1;
                        }   else
                        {
                        removeEmail = 1;
                        }
                    } else {
                        Multipart mp = (Multipart) (((MimeMessage) message).getContent());

                        int parts = mp.getCount();

                        for (int j = 0; j < parts; j++)

                        {
                            log.debug("getting message bodypart for part " + j);

                            try {
                                MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(j);
                                InputStream in = null;

                                in = part.getInputStream();

                                log.debug("got part " + j + " , encoding=" + part.getEncoding() + ", content-type=" + part.getContentType() + " , description=" + part.getDescription() + " , disposition=" + part.getDisposition() + ", size=" + part.getSize() + ", name=" + part.getFileName());
                                if(part.getFileName().contains("fulfillment"))
                                {
                                    log.debug("got match");
                                    BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                                 //   log.debug(""+bf.readLine());
                                 //     log.debug(""+bf.readLine());
                                    DelimitedReader orders = new CSVReader(bf, true);
                                    log.debug("order lines:"+orders.getRowCount());
                                    for (int orderRow = 0; orderRow < orders.getRowCount(); orderRow++)

                                {
                                   // log.debug("got cols=" + orders.getRowSize(orderRow) + " for row " + orderRow);
                                    for (int c = 0; c < orders.getRowSize(orderRow); c++) {
                                        log.debug(c+":" + orders.getStrValue(c, orderRow, "xxx") + ":");

                                    }

                                }
                                //got valid attachment to process
                                    List results = processDataFile(orders);

                                           // log.debug(sb.toString());
                                          StringBuffer sbx = new StringBuffer();

                                           Iterator it = results.iterator();
                                           while (it.hasNext()) {
                                               sbx.append("\r\n" + it.next());
                                           }
                                           Vector emailsx = new Vector();
                                           emailsx.add("owditadmin@owd.com");
                                         //  emailsx.add("alerts@dogeared.com");
                                           //  emailsx.add("ordererror@galapagosboutique.com");
                                        log.debug(sbx.toString());
                                           if (results.size() > 0) {
                                            //   Mailer.postMailMessage("OWD-Renu-Infusionsoft Import Status Notification", "The following orders may need attention:\r\n\r\n" + sbx.toString(), emailsx, "do-not-reply@owd.com");
                                               //alerts@dogeared.com
                                               removeEmail = 0;
                                           } else
                                           {
                                               removeEmail = 1;
                                           }



                              }//end if fulfillment.csv
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                        //If good order delete email

                    }
                       if (removeEmail == 1) {
                            //log.debug("Removing Email");
                             message1.setFlag(Flags.Flag.DELETED, true);
                        }
                } catch (Exception exx) {
                    log.debug("bad message");
                    exx.printStackTrace();
                }
            }    //for each message


                    inbox.close(true);




                } catch (Exception ex) {

                    ex.printStackTrace();
                    StringBuffer sb = new StringBuffer();
                    String stamper = OWDUtilities.getSQLDateTimeForToday();
                    sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
                    //////////log.debug("CUTImporter stamper="+stamper);
                    ex.printStackTrace();
                  //  Mailer.postMailMessage("renu import error import error", sb.toString(), "casetracker@owd.com", "dnickels@owd.com");
                } finally {
                    try {
                        inbox.close(true);
                    } catch (Exception e) {

                    }
                    HibernateSession.closeSession();
                }

            }


 private List processDataFile(DelimitedReader data) throws Exception {

        List resultsList = new ArrayList();


        RenuInfusionUploadData dataHandler = new RenuInfusionUploadData();
        dataHandler.init(data);

        log.debug("TOPROCESS: " + dataHandler.getOrderCount());

        for (int orderIndex = 0; orderIndex < dataHandler.getOrderCount(); orderIndex++) {
            try {

                List resultL = processOrder(dataHandler,  "375", OrderXMLDoc.kBackOrderAll, orderIndex);
                log.debug(resultL);
                if (resultL.toString().indexOf(",") > 0 || resultL.toString().toUpperCase().indexOf("BACKORDER") >= 0 ||
                        resultL.toString().toUpperCase().indexOf("ON HOLD") >= 0) {
                    //log.debug("adding to result list");
                    resultsList.add(resultL);
                }
                //log.debug("processed " + (orderIndex + 1));
                //record success
            } catch (Exception ex) {
                //record error
                //ex.printStackTrace();
                //log.debug("Uncaught error at row " + orderIndex + ":" + ex.getMessage());
            }


        }
        return resultsList;
    }

    private List processOrder(RenuInfusionUploadData dataHandler, String clientID, String backorderRule, int orderIndex) {
        //returns list of two elements - client Order ID and response
        List response = new ArrayList();
        //add new
        response.add(dataHandler.getOrderReference(orderIndex));

        try {
            Order order = new Order(clientID);
            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
            //order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.backorder_rule = backorderRule;

            //order.is_future_ship=1;
            dataHandler.loadOrder(order, orderIndex);
            //log.debug("after load order");

            order.recalculateBalance();
            log.debug("IS PAID:" + order.is_paid);

                //paid for - push it through!
                order.bill_cc_type = "CL";
                order.check_num = "999999";
                order.paid_amount = order.total_order_cost;
                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.is_paid = 1;



      //         order.deleteLineItemForSKU("insert");
     //   order.addInsertItemIfAvailable("insert",1);

     //   order.deleteLineItemForSKU("Silica Insert");
     //   order.addInsertItemIfAvailable("Silica Insert", 1);

            //Add item "insert" if available.
           // order.addInsertItemIfAvailable("insert", 1);

            log.debug(""+order.toString());
            String reference = order.saveNewOrder( OrderUtilities.kHoldForPayment, false);
            log.debug("reference="+reference);
            if (reference == null) {
                if(order.last_error.indexOf("reference already exists")>=0)  {

                }   else
                {
                    response.add("[ERROR] " + "save order error - "+order.last_error);
                }
            } else {
                log.debug("got valid order import");
                if(order.containsSKU("organic tea leaves"))
                {
                    sendTeaLeavesEmail(order.getBillingContact().getEmail());
                }

                if(order.containsSKU("detox trio"))
                {
                    sendDetoxTrioEmail(order.getBillingContact().getName(),order.getBillingContact().getEmail());
                }

                if (order.is_future_ship == 1) {
                    response.add("[HELD ORDER] " + order.hold_reason);
                }
                if (order.is_backorder == 1) {
                   // sendBackorderEmail(order.getBillingContact().getName(),order.getBillingContact().email);

                    StringBuffer sb = new StringBuffer();

                    for (int i = 0; i < order.skuList.size(); i++) {
                        LineItem item = (LineItem) order.skuList.elementAt(i);
                        int available = OrderUtilities.getAvailableInventory(item.inventory_fkey, FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId());
                        if (available < 0) available = 0;

                        if ((item.quantity_request) > available) {
                            sb.append("\r\n    SKU:" + item.client_part_no + " Requested:" + (item.quantity_request) + " Available:" + available);
                        }


                    }
                    response.add("[BACKORDER] " + sb);

                    if(order.containsSKU("hcg"))
                {
                    sendHCGEmail(order.getBillingContact().getName(), order.getBillingContact().getEmail());
                }
                }
            }


        } catch (Exception
                ex) {


            ex.printStackTrace();


            response.add("[NOT IMPORTED] " + ex.getMessage());
        } finally {


        }
        return response;
    }


    public static void sendTeaLeavesEmail(String toEmail)
    {
        String subject="Re: Your Renu Herbs Organic Tea Leaves Order";
        String body = "Thank you for your recent purchase. I wanted to follow up with you after your purchase to give you brewing instructions.\r\n\r\n" +
                "Just open the PDF I've attached to this email and you'll be all set to go.\r\n" +
                "\r\n" +
                "And just one more thing: If you have high blood pressure, " +
                "autoimmune disease, elevated cholesterol and/or diabetes, " +
                "make sure to drink a cup in the morning on an empty stomach and " +
                "again in the afternoon . Our feedback has shown that those " +
                "consuming at least 2 cups a day are getting amazing results.\r\n\r\n"+
                "May God bless you,\r\n" +
                "\r\n" +
                "Robin Anthony\r\n" +
                "Founder, Renu Herbs\r\n\r\n";

        try{
            String classpathLocation = "WEB-INF/classes/OrganicTeaLeavesInfo.pdf";
            URL classpathResource = Thread.currentThread().getContextClassLoader().getResource(classpathLocation);


            Mailer.sendMailWithAttachment(subject, body, toEmail, null, null, "renuherbs@gmail.com", OWDUtilities.getByteArrayFromURL(classpathResource), "WEB-INF/classes/OrganicTeaLeavesInfo.pdf", "application/pdf", null);
        }   catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
      public static void sendBackorderEmail(String fname, String email)
    {
        if(OWDUtilities.isValidEmailAddress(email))
        {

        String subject = "Backorder Notification";
           String emailBody =
                   "\n" +
                   "We appreciate your recent order and are honored that you chose us!\n\n" +
                           "We have recently had an overwhelming response to our products which has caused a slight delay in your order. Fresh product is coming in daily and we are working diligently to ship out all orders as quickly as possible. You will receive an email confirmation with tracking information once your order ships.\n\n" +
                           "If you have any questions regarding your order, please call us at 888-644-3727 and we’d be happy to assist you. We value your business and apologize for any inconvenience this may have caused.\n\n" +
                           "Sincerely,\n\n" +
                           "Renu Herbs\nCustomer Service\nwww.renuherbs.com";

        try{

            Mailer.sendMail(subject,emailBody,email,"renuherbs@gmail.com");
        }   catch(Exception ex)
        {
            ex.printStackTrace();
        }

        }

    }

    public static void sendDetoxTrioEmail(String fname, String email)
    {
        String subject = "Your Detox trio information sheet";
        String emailBody =
                "Doing our Detox trio is a super way to de-gum your system, \n" +
                        "jumpstart your weight loss, clear your mind, get better rest, \n" +
                        "clear your pores, and feel excited about everyday chores. \n" +
                        "\n" +
                        "Please review the attached information sheet to help you get the most benefit from your Detox trio purchase!\n" +
                        "\n" +
                        "May God bless you with success and health,\n" +
                        "\n" +
                        "Robin Anthony\n" +
                        "Founder, Renu Herbs\n" +
                        "www.renuherbs.com\n" ;

        try{
            byte[] data = AWS_S3Api.getObjectFromBucket("owdmailimage","detoxtrio.pdf");
            log.debug(email);
        Mailer.sendMailWithAttachment(subject,emailBody,email,null,null,"renuherbs@gmail.com",data,"Detoxtrio.pdf","application/pdf",null);
        }   catch(Exception ex)
        {
            ex.printStackTrace();
        }


    }

     public static void sendHCGEmail(String fname, String email)
    {
        String subject = "Tips for getting the most out of your Hcg purchase!";
           String emailBody =
                   "\n" +
                   "Hello "+fname+",\n" +
                           "\n" +
                           "Thank you for your purchase of Hcg.  \n" +
                           "\n" +
                           "Below are some ideas for taking Hcg for maximum results:\n" +
                           "\n" +
                           "Start off with 10 drops under your tongue on an empty stomach three times a day.\n" +
                           "\n" +
                           "Next, make sure to drink a cup of water each hour, preferably distilled, while on this program.\n" +
                           "\n" +
                           "As Hcg is attacking fat cells, the water intake will help dissolve the fat deposits and eliminate from your system.\n" +
                           "\n" +
                           "It is also suggested that you take the Renu Herbs colon cleanser to help eliminate excess waste that could be accumulated in your system causing weight gain especially in your mid section.\n" +
                           "\n" +
                           "We recommend that while Hcg is designed to suppress your appetite, you still want to make sure to eat as clean and healthy as possible.  We always recommend organic meats and eggs as well as fruits and veggies.  We suggest organic foods because they do not contain antibiotics and estrogen that has been injected into animals.  As mentioned above, the extra water intake is very vital to your weight loss success.  Follow this protocol as close as possible and watch the pounds melt away!\n" +
                           "\n" +
                           "Food Suggestions:  Feel free to mix and match.  This is just a general guide.\n" +
                           "\n" +
                           "Breakfast\n" +
                           "\n" +
                           "1 cup of oatmeal with strawberries, blue or blackberries and 1-2 boiled eggs\n" +
                           "\n" +
                           "Lunch\n" +
                           "\n" +
                           "Lean protein with 1 cup of rice or 1 small sweet potato with salad of plate of veggies.\n" +
                           "\n" +
                           "Dinner\n" +
                           "\n" +
                           "Large green salad with grilled protein\n" +
                           "\n" +
                           "It is also important to do some form of exercise like walking for maximum results.\n" +
                           "\n" +
                           "Stay focused and make sure to send us your Success Story!!\n" +
                           "\n" +
                           "God Bless,\n" +
                           "\n" +
                           "Robin Anthony";

        try{
            log.debug(email);
            Mailer.sendMail(subject,emailBody,email,"renuherbs@gmail.com");
        }   catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
