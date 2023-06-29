package com.owd.core;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.ListIdentitiesResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.owd.core.OWDConstants.DefaultReturnAddress;
import static com.owd.core.OWDConstants.DefaultToAddress;


public class Mailer {
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args) {
        try {


           // log.debug(getMailProperties("owditadmin@owd.com"));
          //  postMailMessage("test","message","danny.nickels@gmail.com","dnickels@owd.com");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void postMailMessage(String subject, String message, String to, String from) {

        try {
            Mailer.sendMail(subject, message, to, from);
        } catch (Exception ex) {
            //log.debug("FAILED POSTMAILMESSAGE!!!");
            ex.printStackTrace();
        }

    }

    public static void postMailMessage(String subject, String message, Vector toAddresses, String from) throws com.owd.core.MailException {
        try {
            Mailer.sendMail(subject, message, (toAddresses.toArray()), null, null, from);
        } catch (Exception ex) {
            //log.debug("FAILED POSTMAILMESSAGE!!!");
            ex.printStackTrace();
        }
    }

    public static void postMailMessage(String subject, String message, String to, Vector ccAddresses, Vector bccAddresses, String from) throws Exception {

        try {

            Vector toVector = new Vector();
            toVector.addElement(to);

            Mailer.postMailMessage(subject, message, toVector, ccAddresses, bccAddresses, from);
        } catch (Exception ex) {
            //log.debug("FAILED POSTMAILMESSAGE!!!");
            ex.printStackTrace();
        }
    }

    public static void postMailMessage(String subject, String message, Vector toAddresses, Vector ccAddresses, Vector bccAddresses, String from) throws Exception {
        try {
            Object[] ccs = null;
            if (ccAddresses != null) {
                ccs = (Object[]) ccAddresses.toArray();
            }
            Object[] bccs = null;
            if (bccAddresses != null) {
                bccs = (Object[]) bccAddresses.toArray();
            }
            Mailer.sendMail(subject, message, toAddresses.toArray(), ccs, bccs, from);
        } catch (Exception ex) {
            //log.debug("FAILED POSTMAILMESSAGE!!!");
            ex.printStackTrace();
        }
    }

    public static void sendMail(String message) throws Exception {
        Session session = getMailSession(DefaultReturnAddress);

        // create a message
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(DefaultReturnAddress));
        InternetAddress[] address = {new InternetAddress(com.owd.core.OWDConstants.DefaultToAddress)};
        msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
        msg.setSubject("FTPMailer Message");

        // add the Multipart to the message
        if (message.startsWith("<")) {
            msg.setContent(message, "text/html");
        } else {
            msg.setText(message);
        }
        sendMimeEmailToServer(msg);


    }

    public static void sendMail(String subject, String message, String to, String from) throws Exception {
        String[] stray = {to};

        sendMail(subject, message, stray, null, null, from);
    }

    public static void sendMail(String subject, String message, String to, Object[] ccs, Object[] bccs, String from) throws Exception {
        String[] stray = {to};

        sendMail(subject, message, stray, ccs, bccs, from);
    }

    public static void sendMail(String subject, String message, Object[] toAddresses, String from) throws Exception {
        sendMail(subject, message, toAddresses, null, null, from);
    }

    public static void sendHTMLMail(String subject, String textMessage, String htmlMessage, Object[] toAddresses, Object[] ccs, Object[] bccs, String from) throws Exception {

        javax.mail.Session session = getMailSession(from);

        //log.debug("sending msg through " + props.get("mail.smtp.host"));

        // create a message
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] address = new InternetAddress[java.lang.reflect.Array.getLength(toAddresses)];
        for (int i = 0; i < java.lang.reflect.Array.getLength(toAddresses); i++) {
            try {
                address[i] = new InternetAddress((String) toAddresses[i]);
            } catch (AddressException ae) {
                address[i] = new InternetAddress(OWDConstants.DefaultToAddress);
            }
        }
        if (ccs != null) {
            InternetAddress[] ccaddress = new InternetAddress[java.lang.reflect.Array.getLength(ccs)];
            for (int i = 0; i < java.lang.reflect.Array.getLength(ccs); i++) {
                try {
                    ccaddress[i] = new InternetAddress((String) ccs[i]);
                } catch (AddressException ae) {
                    ccaddress[i] = new InternetAddress(OWDConstants.DefaultToAddress);
                }
            }

            msg.setRecipients(javax.mail.Message.RecipientType.CC, ccaddress);
        }
        if (bccs != null) {
            InternetAddress[] bccaddress = new InternetAddress[java.lang.reflect.Array.getLength(bccs)];
            for (int i = 0; i < java.lang.reflect.Array.getLength(bccs); i++) {
                try {
                    bccaddress[i] = new InternetAddress((String) bccs[i]);
                } catch (AddressException ae) {
                    bccaddress[i] = new InternetAddress(OWDConstants.DefaultToAddress);
                }
            }

            msg.setRecipients(javax.mail.Message.RecipientType.BCC, bccaddress);
        }
        msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
        msg.setSubject(subject);

        // add the Multipart to the message
        Multipart mp = new MimeMultipart("alternative");

        // add the Multipart to the message
        BodyPart msgBodyPart = new MimeBodyPart();
        msgBodyPart.setContent(textMessage, "text/plain");
        mp.addBodyPart(msgBodyPart);

        BodyPart webBodyPart = new MimeBodyPart();


        webBodyPart.setContent(htmlMessage, "text/html");
        mp.addBodyPart(webBodyPart);

        msg.setContent(mp);
        sendMimeEmailToServer(msg);


    }


    public static void sendMail(String subject, String message, Object[] toAddresses, Object[] ccs, Object[] bccs, String from) throws Exception {
        javax.mail.Session session = getMailSession(from);

        //log.debug("sending msg through " + props.get("mail.smtp.host"));

        // create a message
        MimeMessage msg = new MimeMessage(session);
        log.debug("from add:" + from);
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] address = new InternetAddress[java.lang.reflect.Array.getLength(toAddresses)];
        for (int i = 0; i < java.lang.reflect.Array.getLength(toAddresses); i++) {
            try {
                log.debug("to add:" + (String) toAddresses[i]);

                address[i] = new InternetAddress((String) toAddresses[i]);
            } catch (AddressException ae) {
                address[i] = new InternetAddress(DefaultToAddress);
            }
        }
        if (ccs != null) {
            log.debug("ccs add:" + ccs);
            InternetAddress[] ccaddress = new InternetAddress[java.lang.reflect.Array.getLength(ccs)];
            for (int i = 0; i < java.lang.reflect.Array.getLength(ccs); i++) {
                try {
                    ccaddress[i] = new InternetAddress((String) ccs[i]);
                } catch (AddressException ae) {
                    ccaddress[i] = new InternetAddress(OWDConstants.DefaultToAddress);
                }
            }

            msg.setRecipients(javax.mail.Message.RecipientType.CC, ccaddress);
        }
        if (bccs != null) {
            log.debug("bccs add:" + bccs);

            InternetAddress[] bccaddress = new InternetAddress[java.lang.reflect.Array.getLength(bccs)];
            for (int i = 0; i < java.lang.reflect.Array.getLength(bccs); i++) {
                try {
                    bccaddress[i] = new InternetAddress((String) bccs[i]);
                } catch (AddressException ae) {
                    bccaddress[i] = new InternetAddress(OWDConstants.DefaultToAddress);
                }
            }

            msg.setRecipients(javax.mail.Message.RecipientType.BCC, bccaddress);
        }
        msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
        msg.setSubject(subject);

        // add the Multipart to the message
        if (message.startsWith("<")) {
            msg.setContent(message, "text/html");
        } else {
            msg.setText(message);
        }
        sendMimeEmailToServer(msg);


    }

    public static boolean testingOnly = false;

    private static void sendMimeEmailToServer(MimeMessage msg) throws MessagingException {
        // set the Date: header

        try {

            msg.setSentDate(new Date());


            if (testingOnly) {
                log.debug("Mailing message [" + msg.getSubject() + "]");
                log.debug("To: " + arrayToString(msg.getRecipients(javax.mail.Message.RecipientType.TO)));
                log.debug("CC: " + arrayToString(msg.getRecipients(javax.mail.Message.RecipientType.CC)));
                log.debug("BCC: " + arrayToString(msg.getRecipients(javax.mail.Message.RecipientType.BCC)));
            } else {


                Transport.send(msg);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String arrayToString(Object[] data) {

        if (data == null) return "";
        StringBuffer text = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            text.append(">><<" + data[i].toString());
        }

        return text.toString();
    }

    static final String awsKey = "AKIAIY7XS3NJQSO6AAQA";
    static final String awsSecretKey="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";

    static AmazonSimpleEmailService ses = new AmazonSimpleEmailServiceClient(new AWSCredentials() {

        public String getAWSAccessKeyId() {
            return awsKey;
        }

        public String getAWSSecretKey() {
            return awsSecretKey;
        }
    });



    public static boolean validateEmail(String email) {
        try {
            new InternetAddress(email).validate();

        } catch (AddressException ex) {
            log.debug("Error : " + ex.getMessage());
            return false;
        }
        return true;
    }

    static List<String> sesDomains = null;
    static long lastSesUpdateTime = 0;


    /*
    Logic to send email via Amazon SES if domain of return address is verified; else internal.owd.com
     */
    public static synchronized Session getMailSession(String fromAddress) {

        Properties props = new Properties();
        Thread.currentThread().setContextClassLoader(Mailer.class.getClassLoader());
        try {
            if (sesDomains == null || (Calendar.getInstance().getTimeInMillis() - lastSesUpdateTime) > (1000L * 3600)) {
                ListIdentitiesResult identities = ses.listIdentities();
                List<String> doms = new ArrayList<String>();

                for (String domain : identities.getIdentities()) {
                    if (!(domain.contains("@"))) {
                        doms.add(domain);
                    }
                    sesDomains = doms;
                    lastSesUpdateTime = Calendar.getInstance().getTimeInMillis();
                    log.debug(sesDomains);
                }
            }

            if (validateEmail(fromAddress)) {
                for (String domain : sesDomains) {
                    if (fromAddress.substring(fromAddress.indexOf("@") + 1).equals(domain)) {
                        props.put("mail.transport.protocol", "smtp");
                        props.put("mail.smtp.port", "25");
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.starttls.enable", "true");
                        props.put("mail.smtp.starttls.required", "true");
                        props.put("mail.smtp.host", "email-smtp.us-east-1.amazonaws.com");
                        props.put("mail.smtp.user", "AKIAIY7XS3NJQSO6AAQA");
                        props.put("mail.smtp.password", "4jyZRZazQQBl6hPsASaOvxc6J7cJZwzaciW1sNIw");
                        props.put("mail.debug","true");
                        log.debug("using SES props for mail: "+props);

                    }
                }
            }

            if (props.size()<1){
                props = getDefaultMailProperties();
            }

        } catch (Exception ex) {
            log.debug("ERROR GETTING SES DOMAINS: " + ex.getMessage());
            props = getDefaultMailProperties();
        }

         if(props.size()>3)
         {

         return  javax.mail.Session.getInstance(props,new Authenticator() {

             @Override
             protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication( "AKIAIY7XS3NJQSO6AAQA","4jyZRZazQQBl6hPsASaOvxc6J7cJZwzaciW1sNIw");
             }

         });
         }else
         {
             return  javax.mail.Session.getDefaultInstance(props);
         }
    }


    private static Properties getDefaultMailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", OWDConstants.SMTPServer);

        return props;

    }

    public static void forwardMessage(String subject, javax.mail.Message forwardedMessage, String messageText, String to) throws Exception {

        javax.mail.Session session = getMailSession(OWDConstants.DefaultReturnAddress);


        // create a message
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(OWDConstants.DefaultReturnAddress));
        InternetAddress[] address = null;
        try {
            address = new InternetAddress[]{new InternetAddress(to)};
        } catch (Exception exa) {
            address = new InternetAddress[]{new InternetAddress("casetracker@owd.com")};
        }
        msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
        msg.setSubject(subject);

        // add the Multipart to the message

        msg.setText(messageText);
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setContent(forwardedMessage, "message/rfc822");


        MimeMultipart multipart = new MimeMultipart("mixed");

        // first part  (the html)
        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setContent(messageText, "text/plain");

        // add it
        multipart.addBodyPart(messageBodyPart);

        // second part (the image)

        // add it
        multipart.addBodyPart(mbp);

        // put everything together
        msg.setContent(multipart);

        sendMimeEmailToServer(msg);


    }

    public static void sendMail(String subject, String message, String to) throws Exception {
        javax.mail.Session session = getMailSession(OWDConstants.DefaultReturnAddress);

        // create a message
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(OWDConstants.DefaultReturnAddress));
        InternetAddress[] address = null;
        try {
            address = new InternetAddress[]{new InternetAddress(to)};
        } catch (Exception exa) {
            address = new InternetAddress[]{new InternetAddress(OWDConstants.DefaultToAddress)};
        }
        msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
        msg.setSubject(subject);

        // add the Multipart to the message
        if (message.startsWith("<")) {
            msg.setContent(message, "text/html");
        } else {
            msg.setText(message);
        }
        sendMimeEmailToServer(msg);


    }

    public static void clearPOPMailbox(String popServer, String user, String pass) throws Exception {
        ////////log.debug("clearing mailbox for "+user);
        for (int j = 0; j < 10000; j++) {
            try {

                Properties props = new Properties();
                props.put("mail.smtp.host", OWDConstants.SMTPServer);

                URLName url = new URLName("pop3", popServer, -1, "INBOX", user, pass);

                SimpleDateFormat sdf = new SimpleDateFormat
                        ("dd-MMM-yyyy HH:mm:ss", Locale.US);
     /*
     ** fix timezone in the SimpleDateFormat
     **  bug in JDK1.1
     */
                sdf.setCalendar(Calendar.getInstance());
     /*
     **  create a Date (no choice, parse returns a Date object)
     */
                Date d = sdf.parse("27-Feb-2006 00:00:00");

                javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props, null);
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

                javax.mail.Message[] messages = {};
                int count = inbox.getMessageCount();
                //log.debug("pop3 got "+count+" messages...");
                if (count > 0)
                    messages = inbox.getMessages();


                for (int i = 1; i < (messages.length > 500 ? 500 : messages.length); i++) {
                    System.out.print(".");
                    //log.debug(messages[i]);
                    //log.debug(messages[i].getSentDate());
                    try {
                        if (messages[i].getSentDate().getTime() < d.getTime()) {
                            System.out.print("y");
                            messages[i].setFlag(Flags.Flag.DELETED, true);
                        }
                        //log.debug("x");

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }


                inbox.close(true);


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        ////////log.debug("\nDone");
    }

    static public void sendMailWithAttachment(String subject, String message, String to, byte[] data, String attName, String MIMEtype, String htmlMessage) throws Exception {
        sendMailWithAttachment(subject, message, to, null, null, null, data, attName, MIMEtype, htmlMessage);

    }

    static public void sendMailWithAttachment(String subject, String message, String to, byte[] data, String attName, String MIMEtype) throws Exception {
        sendMailWithAttachment(subject, message, to, null, null, null, data, attName, MIMEtype, null);

    }

    static public void sendMailWithAttachment(String subject, String message, String to, Object[] ccs, byte[] data, String attName, String MIMEtype) throws Exception {
        sendMailWithAttachment(subject, message, to, ccs, null, null, data, attName, MIMEtype, null);

    }

    static public void sendMailWithAttachment(String subject, String message, String to, Object[] ccs, byte[] data, String attName, String MIMEtype, String htmlMessage) throws Exception {
        sendMailWithAttachment(subject, message, to, ccs, null, null, data, attName, MIMEtype, htmlMessage);

    }

    static public void sendMailWithAttachment(String subject, String message, String to, Object[] ccs, Object[] bccs, String returnEmail, byte[] data, String attName, String MIMEtype, String htmlMessage) throws Exception {


        javax.mail.Session session = getMailSession(returnEmail);


        // create a message
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(OWDConstants.DefaultReturnAddress));
        if (returnEmail != null) {
            try {
                msg.setFrom(new InternetAddress(returnEmail));
            } catch (Exception ef) {
            }
        }
        InternetAddress[] address = {new InternetAddress(to)};
        msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
        msg.setSubject(subject);

        if (ccs != null) {
            InternetAddress[] ccaddress = new InternetAddress[java.lang.reflect.Array.getLength(ccs)];
            for (int i = 0; i < java.lang.reflect.Array.getLength(ccs); i++) {
                try {
                    ccaddress[i] = new InternetAddress((String) (ccs[i]));
                } catch (AddressException ae) {
                    ccaddress[i] = new InternetAddress(OWDConstants.DefaultToAddress);
                }
            }

            msg.setRecipients(javax.mail.Message.RecipientType.CC, ccaddress);
        }

        // add the Multipart to the message


        javax.mail.internet.MimeBodyPart body = new javax.mail.internet.MimeBodyPart();

        Multipart mp = new MimeMultipart("alternative");

        // add the Multipart to the message
        BodyPart msgBodyPart = new MimeBodyPart();
        if (message.startsWith("<")) {
            msgBodyPart.setContent(message, "text/html");
            mp.addBodyPart(msgBodyPart);
        } else {
            msgBodyPart.setContent(message, "text/plain");
            mp.addBodyPart(msgBodyPart);
            // mp.addBodyPart(msgBodyPart);


        }


        BodyPart webBodyPart = new MimeBodyPart();

        if (htmlMessage != null) {
            webBodyPart.setContent(htmlMessage, "text/html");
            mp.addBodyPart(webBodyPart);

        }


        body.setContent(mp);


        javax.mail.internet.MimeBodyPart attachment = new javax.mail.internet.MimeBodyPart();

        DataSource dsrc =
                new ByteArrayDataSource(data,
                        null, attName);

        attachment.setDataHandler(new DataHandler(dsrc));
        attachment.setDisposition("attachment; filename=\"" +
                attName + "\"");
        attachment.setFileName(attName);


        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(body);
        multipart.addBodyPart(attachment);
        msg.setContent(multipart);

        // set the Date: header
        //msg.setSentDate(new Date());

        // send the message
        //log.debug("transport sending email");
        sendMimeEmailToServer(msg);


    }

    static public void sendMailWithMultipleAttachments(String subject, String message, String to, Object[] ccs, Object[] bccs, String returnEmail, List<Map<String, Object>> data) throws Exception {


        javax.mail.Session session = getMailSession(returnEmail);


        // create a message
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(OWDConstants.DefaultReturnAddress));
        if (returnEmail != null) {
            try {
                msg.setFrom(new InternetAddress(returnEmail));
            } catch (Exception ef) {
            }
        }
        InternetAddress[] address = {new InternetAddress(to)};
        msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
        msg.setSubject(subject);

        if (ccs != null) {
            InternetAddress[] ccaddress = new InternetAddress[java.lang.reflect.Array.getLength(ccs)];
            for (int i = 0; i < java.lang.reflect.Array.getLength(ccs); i++) {
                try {
                    ccaddress[i] = new InternetAddress((String) (ccs[i]));
                } catch (AddressException ae) {
                    ccaddress[i] = new InternetAddress(OWDConstants.DefaultToAddress);
                }
            }

            msg.setRecipients(javax.mail.Message.RecipientType.CC, ccaddress);
        }

        // add the Multipart to the message


        javax.mail.internet.MimeBodyPart body = new javax.mail.internet.MimeBodyPart();

        Multipart mp = new MimeMultipart("alternative");

        // add the Multipart to the message
        BodyPart msgBodyPart = new MimeBodyPart();
        if (message.startsWith("<")) {
            msgBodyPart.setContent(message, "text/html");
            mp.addBodyPart(msgBodyPart);
        } else {
            msgBodyPart.setContent(message, "text/plain");
            mp.addBodyPart(msgBodyPart);

        }


        body.setContent(mp);


        MimeMultipart multipart = new MimeMultipart("mixed");
        multipart.addBodyPart(body);

        for (Map<String, Object> attInfo : data) {
            javax.mail.internet.MimeBodyPart attachment = new javax.mail.internet.MimeBodyPart();

            DataSource dsrc =
                    new ByteArrayDataSource((byte[]) attInfo.get("bytes"),
                            null, (String) attInfo.get("mimetype"));

            attachment.setDataHandler(new DataHandler(dsrc));
            attachment.setDisposition("attachment; filename=\"" +
                    (String) attInfo.get("filename") + "\"");
            attachment.setFileName((String) attInfo.get("filename"));

            multipart.addBodyPart(attachment);
        }
        msg.setContent(multipart);

        // set the Date: header
        //msg.setSentDate(new Date());

        // send the message
        //log.debug("transport sending email");
        sendMimeEmailToServer(msg);


    }


    static public void sendHTMLMailWithEmbededPicDefunctDoNotUse(String subject, String text, Object[] to, Object[] ccs, Object[] bcc,
                                                  String htmlMessage, String from, BodyPart image) throws Exception {


        Session mailSession = getMailSession(from);
        //mailSession.setDebug(true);
        // Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(subject);
        message.setFrom(new InternetAddress(from));
        //set to address
        if (to != null) {
            InternetAddress[] toaddress = new InternetAddress[java.lang.reflect.Array.getLength(to)];
            for (int i = 0; i < java.lang.reflect.Array.getLength(to); i++) {
                try {
                    toaddress[i] = new InternetAddress((String) (to[i]));
                } catch (AddressException ae) {
                    toaddress[i] = new InternetAddress(OWDConstants.DefaultToAddress);
                }
            }

            message.setRecipients(javax.mail.Message.RecipientType.TO, toaddress);
        }

        //message.addRecipient(Message.RecipientType.TO, new InternetAddress("dnickels@owd.com"));

        //set cc addresses
        if (ccs != null) {
            InternetAddress[] ccaddress = new InternetAddress[java.lang.reflect.Array.getLength(ccs)];
            for (int i = 0; i < java.lang.reflect.Array.getLength(ccs); i++) {
                try {
                    ccaddress[i] = new InternetAddress((String) (ccs[i]));
                } catch (AddressException ae) {
                    //log.debug("Bad cc for html email");
                }
            }

            message.setRecipients(javax.mail.Message.RecipientType.CC, ccaddress);
        }
        //set bcc Address
        if (bcc != null) {
            InternetAddress[] bccaddress = new InternetAddress[java.lang.reflect.Array.getLength(bcc)];
            for (int i = 0; i < java.lang.reflect.Array.getLength(bcc); i++) {
                try {
                    bccaddress[i] = new InternetAddress((String) (bcc[i]));
                } catch (AddressException ae) {
                    //log.debug("Bad bcc for html email");
                }
            }

            message.setRecipients(javax.mail.Message.RecipientType.BCC, bccaddress);
        }

        //
        // This HTML mail have to 2 part, the BODY and the embedded image
        //
        MimeMultipart multipart = new MimeMultipart("related");

        // first part  (the html)
        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setContent(htmlMessage, "text/html");

        // add it
        multipart.addBodyPart(messageBodyPart);

        // second part (the image)

        // add it
        multipart.addBodyPart(image);

        // put everything together
        message.setContent(multipart);
        //log.debug("all done going to sent html pic email");
        //   transport.connect();
        sendMimeMessageToServerWithRecipients(message, message.getRecipients(Message.RecipientType.TO));
        if (ccs != null) {
            sendMimeMessageToServerWithRecipients(message, message.getRecipients(Message.RecipientType.CC));
        }
        if (bcc != null) {
            sendMimeMessageToServerWithRecipients(message, message.getRecipients(Message.RecipientType.BCC));
        }

        //    transport.close();


    }

    private static void sendMimeMessageToServerWithRecipients(MimeMessage msg, Address[] recipients) throws MessagingException {

        try {
            if (testingOnly) {
                //log.debug("Mailing message ["+msg.getSubject()+"]");
                //log.debug("To: "+arrayToString(msg.getRecipients(javax.mail.Message.RecipientType.TO)));
                //log.debug("CC: "+arrayToString(msg.getRecipients(javax.mail.Message.RecipientType.CC)));
                //log.debug("BCC: "+arrayToString(msg.getRecipients(javax.mail.Message.RecipientType.BCC)));
            } else {

                msg.setSentDate(new Date());
                Transport.send(msg, recipients);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static public void sendMailWithAttachments(String subject, String message, String to, Object[] ccs, byte[] data, String attName, byte[][] files, String[] names, String MIMEtype) throws Exception {
        sendMailWithAttachments(subject, message, to, ccs, data, attName, files, names, MIMEtype, null);
    }

    static public void sendMailWithAttachments(String subject, String message, String to, Object[] ccs, byte[] data, String attName, byte[][] files, String[] names, String MIMEtype, String htmlMessage) throws Exception {

        //log.debug("in attachments");
        javax.mail.Session session = getMailSession(OWDConstants.DefaultReturnAddress);


        // create a message
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(OWDConstants.DefaultReturnAddress));
        InternetAddress[] address = {new InternetAddress(to)};
        msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
        msg.setSubject(subject);

        if (ccs != null) {
            InternetAddress[] ccaddress = new InternetAddress[java.lang.reflect.Array.getLength(ccs)];
            for (int i = 0; i < java.lang.reflect.Array.getLength(ccs); i++) {
                try {
                    ccaddress[i] = new InternetAddress((String) (ccs[i]));
                } catch (AddressException ae) {
                    ccaddress[i] = new InternetAddress(OWDConstants.DefaultToAddress);
                }
            }

            msg.setRecipients(javax.mail.Message.RecipientType.CC, ccaddress);
        }

        // add the Multipart to the message


        javax.mail.internet.MimeBodyPart attachment = new javax.mail.internet.MimeBodyPart();

        DataSource dsrc =
                new ByteArrayDataSource(data,
                        null, attName);

        attachment.setDataHandler(new DataHandler(dsrc));
        attachment.setDisposition("attachment; filename=\"" +
                attName + "\"");
        attachment.setFileName(attName);


        javax.mail.internet.MimeBodyPart body = new javax.mail.internet.MimeBodyPart();

        Multipart mp = new MimeMultipart("alternative");

        // add the Multipart to the message
        BodyPart msgBodyPart = new MimeBodyPart();
        if (message.startsWith("<")) {
            msgBodyPart.setContent(message, "text/html");
            mp.addBodyPart(msgBodyPart);
        } else {
            msgBodyPart.setContent(message, "text/plain");
            mp.addBodyPart(msgBodyPart);
            // mp.addBodyPart(msgBodyPart);


        }


        BodyPart webBodyPart = new MimeBodyPart();

        if (htmlMessage != null) {
            webBodyPart.setContent(htmlMessage, "text/html");
            mp.addBodyPart(webBodyPart);

        }


        body.setContent(mp);


        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(body);
        multipart.addBodyPart(attachment);

        for (int i = 0; i < files.length; i++) {
            javax.mail.internet.MimeBodyPart attachment2 = new javax.mail.internet.MimeBodyPart();
            DataSource dsrc2 =
                    new ByteArrayDataSource(files[i],
                            null, names[i]);

            attachment2.setDataHandler(new DataHandler(dsrc2));
            attachment2.setDisposition("attachment; filename=\"" +
                    names[i] + "\"");
            attachment2.setFileName(names[i]);
            //log.debug("4");
            multipart.addBodyPart(attachment2);

        }
        msg.setContent(multipart);

        // set the Date: header
        //msg.setSentDate(new Date());

        // send the message
        //    //log.debug("transport sending email");
        sendMimeEmailToServer(msg);


    }

    static class ByteArrayDataSource
            implements DataSource {
        byte[] bytes;
        String contentType;
        String name;

        ByteArrayDataSource(byte[] bytes,
                            String contentType,
                            String name) {
            this.bytes = bytes;
            if (contentType == null)
                this.contentType = "application/octet-stream";
            else
                this.contentType = contentType;
            this.name = name;
        }

        public String getContentType() {
            return contentType;
        }

        public InputStream getInputStream() {
            // remove the final CR/LF
            return new ByteArrayInputStream(bytes);
        }

        public String getName() {
            return name;
        }

        public OutputStream getOutputStream()
                throws IOException {
            throw new FileNotFoundException();
        }
    }
}
