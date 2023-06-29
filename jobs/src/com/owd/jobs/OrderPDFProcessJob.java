package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.BinHex4InputStream;
import com.owd.core.Mailer;
import com.owd.core.OWDConstants;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ScanManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.jobobjects.utilities.OrderScanInfoBean;
import org.hibernate.ObjectNotFoundException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;


/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jul 13, 2006
 * Time: 11:36:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderPDFProcessJob   extends OWDStatefulJob{
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
         run();

    }



    private String forwardAddress = "casetracker@owd.com";

     public void internalExecute() {

        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", OWDConstants.SMTPServer);

            URLName url = new URLName("pop3", "secure.emailsrvr.com", -1, "INBOX", "orderslips@owd.com", "money1");

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
            log.debug("Order PDFs got " + count + " messages for Scans...");
            if (count > 0)
                messages = inbox.getMessages();

            while (count>0) {
                int newOrders = 0;

                try {
                    String subject = messages[0].getSubject();
                    log.debug("got message: " + subject);


                    Multipart mp = null;
                    int parts = 1;
                    try {
                        MimeMessage message = (MimeMessage) messages[0];
                       log.debug("got javax.mail.Message content=" + message.getContentType());
                        mp = (Multipart) (((MimeMessage) message).getContent());
                       log.debug("got message content");
                        parts = mp.getCount();
                       log.debug("got message partcount=" + parts);
                        //  //log.debug("got MimeMessage, partcount=" + parts + ", content-type=" + mp.getContentType() + ", subject=" + subject);
                    } catch (ClassCastException ecc1) {
                        //////log.debug("avpi imp got null mp");


                    }
                    OrderScanInfoBean rb = new OrderScanInfoBean();
                    InputStream in = null;
                    String partName = "";
                    String partType = "";
                    for (int j = 0; j < parts; j++) {
                       log.debug("checking part " + j);

                        if (mp != null) {
                            MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(j);
                           log.debug("got message bodypart "+part.getContentType());

                            partType = part.getContentType();

                            if (part.getContentType().indexOf("binhex") >= 0) {
                                in = new BinHex4InputStream(part.getInputStream());
                                //////log.debug(((com.owd.BinHex4InputStream)in).getHeader());
                            } else {
                                in = part.getInputStream();
                            }
                            partName = part.getFileName();
                            if (partName == null) partName = "";
                            //////log.debug("got inputstream");
                         log.debug("got part " + j + " , encoding=" + part.getEncoding() + ", content-type=" + part.getContentType() + " , description=" + part.getDescription() + " , disposition=" + part.getDisposition() + ", size=" + part.getSize() + ", name=" + part.getFileName());
                        } else {
                            in = messages[0].getInputStream();

                        }

                        //log.debug("type:"+partType);
                        if (partType.toUpperCase().indexOf("TEXT/PLAIN")>=0 || partType.toUpperCase().indexOf("MULTIPART/ALTERNATIVE")>=0) {
                            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                            String line = "";
                            log.debug("got text part");
                            while ((line = bf.readLine()) != null) {
                                line = line.trim();
                                //log.debug("l:"+line);
                                if (line.startsWith("Order_Num")) {
                                    rb.setOrderId(line.substring((line.indexOf("=") + 1)));
                                }


                            }
                        }

                        if (partName.toUpperCase().endsWith(".PDF")) {
                            log.debug("Got PDF");
                            rb.setFile(in);
                        }

                    } //for each MIME part

                    log.debug("rb.id="+rb.getOrderId()+";pdf="+rb.getFile());
                    //Save scan to server
                    PreparedStatement ps = HibernateSession.getPreparedStatement("select order_id from owd_order where order_num=?");
                    ps.setString(1,rb.getOrderId());
                    ResultSet rs = ps.executeQuery();
                    if(rs.next())
                    {
                        rb.setOrderId(rs.getString(1));
                    }
                    rs.close();
                    HibernateSession.closeSession();
                    log.debug("rb.id="+rb.getOrderId());
                    OwdOrder order =null;
                    try
                    {
                        order = (OwdOrder) HibernateSession.currentSession().load((OwdOrder.class), new Integer(rb.getOrderId()));
                     if (order==null) {
                        throw new Exception("noOrderMatch");

                    }

                        if (!order.getOrderId().toString().equals(rb.getOrderId())) {
                        throw new Exception("noOrderMatch");

                    }
                    }catch(Exception exn)
                    {
                        throw new Exception("order ID invalid or not found");
                    }

                    Calendar cal = Calendar.getInstance();
                    DateFormat df = new SimpleDateFormat("yyyyMMdd");
                    DateFormat tf = new SimpleDateFormat("HH:mm:ss.SSS");

                    rb.setDate(df.format(cal.getTime()));
                    rb.setTime(tf.format(cal.getTime()));
                    newOrders = saveScan(rb, order);


                } catch (ObjectNotFoundException onf) {
                    forward(forwardAddress, "noop@owd.com", "Scan Error", "Order Id was unable to be found", messages[0], mailSession);
                    newOrders = 2;
                } catch (ClassCastException ecc) {
                    //messages[i].setFlag(Flags.Flag.DELETED,true);
                    ecc.printStackTrace();
                    StringBuffer sb = new StringBuffer();
                    String stamper = OWDUtilities.getSQLDateTimeForToday();
                    sb.append("\nException: " + ecc.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
                    ecc.printStackTrace();
                    Mailer.postMailMessage("Order Scan import error", sb.toString(), "casetracker@owd.com", "noop@owd.com");

                     } catch (SocketException ex) {

                }
                
                 catch (Exception ex) {

                        ex.printStackTrace();
                        StringBuffer sb = new StringBuffer();
                        String stamper = OWDUtilities.getSQLDateTimeForToday();
                        sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
                        ex.printStackTrace();
                        Vector emails = new Vector();
                        emails.add("casetracker@owd.com");
                        Mailer.postMailMessage("Order Scan import error", sb.toString(), emails, "noop@owd.com");

                }


                //Save results to database
                if (newOrders == 1) {
                    HibUtils.commit(HibernateSession.currentSession());
                    messages[0].setFlag(Flags.Flag.DELETED, true);
                }
                if (newOrders == 2) {
                    messages[0].setFlag(Flags.Flag.DELETED, true);
                }


            inbox.close(true);

            popStore.close();

            popStore.connect();
                        inbox = popStore.getDefaultFolder();
                        if (inbox == null)
                            throw new MessagingException("No default folder");

                        inbox = inbox.getFolder("INBOX");
                        if (inbox == null)
                            throw new MessagingException("Invalid folder");

               inbox.open(Folder.READ_WRITE);

                try
                {
         //   messages = {};
             count = inbox.getMessageCount();
            log.debug("Order PDFs2 got " + count + " messages for Scans...");
            if (count > 0)
                messages = inbox.getMessages();
                }catch(Throwable th)
                {
                    log.debug("Order PDF processor exception getting mailbox count - exiting message processing loop");
                    Mailer.postMailMessage("Order Scan import error", "Order PDF processor exception getting mailbox count - exiting message processing loop", "casetracker@owd.com", "noop@owd.com");
                    count=0;
                }

            }    //for each message


        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            ex.printStackTrace();
            Mailer.postMailMessage("Order Scan import error", sb.toString(), "casetracker@owd.com", "noop@owd.com");


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static int saveScan(OrderScanInfoBean rb, OwdOrder order) throws Exception {
        int success = 0;
        ScanManager.addScanToOwdOrder(order, rb.getFileByte(), rb.getFileName(), "");


        success = 1;
        return success;
    }


    public void forward(String to, String from, String subject, String content,
                        Message message, Session session) throws Exception {


        Message forward = new MimeMessage(session);


        forward.setFrom(new InternetAddress(from));
        forward.setSubject(subject);


        forward.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Please view Documents and rescan with proper Information\n\n" + content);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(message.getDataHandler());

// Add part to multi part
        multipart.addBodyPart(messageBodyPart);


        forward.setContent(multipart);
        Transport.send(forward);
        //folder.close(true);
        //store.close();
    }

}




