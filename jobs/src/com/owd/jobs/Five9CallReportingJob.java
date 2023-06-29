package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateAdHocSession;
import com.owd.hibernate.HibernateClientReportsSession;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.jobobjects.five9.owd.Five9API;
import org.apache.commons.lang.StringEscapeUtils;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 9/3/12
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Five9CallReportingJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();



    public static void main(String[] args) throws Exception {

run();
    }


    public void internalExecute() {

        try{


        DelimitedReader orders = new CSVReader(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(Five9API.getCurrentCallLog(24).getBytes()))), false);

            if(orders.getRowCount()>1)
            {
                log.debug("processing five9 report of "+orders.getRowCount()+" rows");
                applyFive9Report(orders);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Vector emailsx = new Vector();
            emailsx.add("owditadmin@owd.com");
            try{
            Mailer.postMailMessage("OWD-Five9-CallLog Import Status Notification", ex.getMessage(), emailsx, "do-not-reply@owd.com");
        } catch (Exception exx) {
            exx.printStackTrace();

        }
        } finally {

            HibernateSession.closeSession();
          //  HibernateAdHocSession.closeSession();
        }

        String lastStatement="init";

        try{


            Five9API api = new Five9API();

            Map<String, List<String>> campByIdMap = api.getCampaignMapByClientId();
            for(String cid:campByIdMap.keySet())
            {
                for(String cname:campByIdMap.get(cid))
                {
                    PreparedStatement ps = HibernateSession.getPreparedStatement("if not exists (select * from five9_campaigns where client_fkey="+cid+" and campaign='"+cname+"' ) " +
                            "insert into five9_campaigns (dnis,scripts,queues,five9number,client_fkey,campaign,clientname) select '','','','',client_id,'"+cname+"',company_name from owd_client where client_id="+cid);
                    ps.executeUpdate();
                }
            }
            HibUtils.commit(HibernateSession.currentSession());

             lastStatement="exec f9_update_owd_cc_contact;";
        PreparedStatement ps1 = HibernateSession.getPreparedStatement("exec f9_update_owd_cc_contact;");
        ps1.executeUpdate();
        ps1.close();
        lastStatement="exec f9_update_owd_cc_contact;commit";

        HibUtils.commit(HibernateSession.currentSession());

        lastStatement="exec f9_update_owdreports_cc_contact;";

        HibernateClientReportsSession.closeSession();

        /*PreparedStatement ps2 = HibernateClientReportsSession.getPreparedStatement("exec f9_update_owdreports_cc_contact;");
        ps2.executeUpdate();
        ps2.close();
        lastStatement="exec f9_update_owdreports_cc_contact;commit";

        HibUtils.commit(HibernateClientReportsSession.currentSession());

        //smarterttrack emails
        lastStatement="exec update_smartertrack_cc_contact;";

        PreparedStatement ps3 = HibernateClientReportsSession.getPreparedStatement("exec update_smartertrack_cc_contact;");
        ps3.executeUpdate();
        ps3.close();
        lastStatement="exec update_smartertrack_cc_contact;commit";*/

        HibUtils.commit(HibernateClientReportsSession.currentSession());
        } catch (Exception ex) {
            ex.printStackTrace();
            Vector emailsx = new Vector();
            emailsx.add("owditadmin@owd.com");
            try{
                Mailer.postMailMessage("OWD-Five9-CallLog Import Status Notification", lastStatement+"\r\n"+ex.getMessage(), emailsx, "do-not-reply@owd.com");
            } catch (Exception exx) {
                exx.printStackTrace();

            }
        } finally {

            HibernateSession.closeSession();
           // HibernateAdHocSession.closeSession();
        }
        /*
        try {

            Folder inbox = null;
            int removeEmail = 0;

            Properties props = new Properties();
            props.put("mail.smtp.host", OWDConstants.SMTPServer);

            URLName url = new URLName("pop3", "secure.emailsrvr.com", -1, "INBOX", "five9calllogs@owd.com", "badhorse57601");

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

            if (messages.length > 0) {
                Message message1 = messages[0];
                try {
                    removeEmail = 0;

                    String subject = message1.getSubject();
                    log.debug("got message: " + subject);

                    MimeMessage message = (MimeMessage) message1;
                    //log.debug("got javax.mail.Message content=" + message.getContentType());
                    log.debug(message.getContent().toString());
                    //Check subject for no good orders
                    if (!(subject.contains("Call Log")) || message1.isSet(Flags.Flag.DELETED)) {

                    } else {
                        Multipart mp = (Multipart) (((MimeMessage) message).getContent());

                        int parts = mp.getCount();
                        log.debug("parts:" + parts);
                        for (int j = 0; j < parts; j++)

                        {
                            log.debug("getting message bodypart for part " + j);

                            try {
                                MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(j);
                                InputStream in = null;


                                String partName = part.getFileName();

                                if (partName == null) partName = "";

                                if (part.getContentType().indexOf("mixed") >= 0) {
                                    //////log.debug(((com.owd.BinHex4InputStream)in).getHeader());
                                    Multipart internalmp = (Multipart) part.getContent();
                                    int internalparts = internalmp.getCount();
                                    for (int k = 0; k < internalparts; k++) {
                                        MimeBodyPart internalpart = (MimeBodyPart) internalmp.getBodyPart(k);

                                        log.debug("got internal part " + k + " , encoding=" + internalpart.getEncoding() + ", content-type=" + internalpart.getContentType() + " , description=" + internalpart.getDescription() + " , disposition=" + internalpart.getDisposition() + ", size=" + internalpart.getSize() + ", name=" + internalpart.getFileName());
                                        if (internalpart.getFileName() != null) {
                                            if (internalpart.getFileName().toUpperCase().startsWith("CALL LOG")) {
                                                log.debug(">>>Found file: " + internalpart.getFileName());
                                                partName = internalpart.getFileName();
                                                in = internalpart.getInputStream();
                                                k = internalparts;
                                                j = parts;
                                            }
                                        }

                                    }
                                } else {

                                    in = part.getInputStream();
                                }
                                log.debug("got part " + j + " , encoding=" + part.getEncoding() + ", content-type=" + part.getContentType() + " , description=" + part.getDescription() + " , disposition=" + part.getDisposition() + ", size=" + part.getSize() + ", name=" + part.getFileName());
                                if (partName.startsWith("Call Log")) {
                                    log.debug("got match");
                                    BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                                    //   log.debug(""+bf.readLine());
                                    //     log.debug(""+bf.readLine());
                                    DelimitedReader orders = new CSVReader(bf, false);
                                    message1.setFlag(Flags.Flag.DELETED, true);
                                    inbox.close(true);


                                    applyFive9Report(orders);


                                }//end if fulfillment.csv
                                removeEmail = 1;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                removeEmail = 0;
                                Vector emailsx = new Vector();
                                emailsx.add("owditadmin@owd.com");
                                Mailer.postMailMessage("OWD-Five9-CallLog Import Status Notification", ex.getMessage(), emailsx, "do-not-reply@owd.com");

                            }

                        }
                        //If good order delete email

                        message1.setFlag(Flags.Flag.DELETED, true);
                        inbox.close(true);
                    }

                } catch (Exception exx) {
                    log.debug("bad message");
                    exx.printStackTrace();
                }
            }    //for each message


        } catch (Exception ex) {

            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            //  Mailer.postMailMessage("renu import error import error", sb.toString(), "casetracker@owd.com", "dnickels@owd.com");
        } finally {

            HibernateSession.closeSession();
            HibernateAdHocSession.closeSession();
        }
        */

    }

    private static void applyFive9Report(DelimitedReader orders) throws Exception {

         String lastStatement = "";
        try{
        Five9API api = new Five9API();
            lastStatement="getting maps from five9";
        Map<String, String> campMap = api.getCampaignMap();
        Map<String, List<String>> campByIdMap = api.getCampaignMapByClientId();


        StringBuffer sql = new StringBuffer();
        sql.append("insert into cc_five9_reports (");
        StringBuffer rowSql = new StringBuffer();


        Map<Integer, String> ivrColumnIndexNameMap = new TreeMap<Integer, String>();

        int callIdColIndex = 0;
        int campColIndex = 0;
        int dispositionIndex = 0;


        for (int orderRow = 0; orderRow < orders.getRowCount(); orderRow++)
        {
            log.debug("cking row "+orderRow);
            String callId = "XXX";
            String disposition = "";

            // log.debug("got cols=" + orders.getRowSize(orderRow) + " for row " + orderRow);
            if (orderRow == 0) {
                for (int c = 0; c < orders.getRowSize(orderRow); c++) {

                    if (orders.getStrValue(c, orderRow, "xxx").equals("CALL ID")) {
                        callIdColIndex = c;
                    }

                    if (orders.getStrValue(c, orderRow, "xxx").equals("CAMPAIGN")) {
                        campColIndex = c;
                    }
                    if(orders.getStrValue(c,orderRow,"xxx").equals("DISPOSITION")){
                        dispositionIndex = c;
                    }
                    if (isIvrVariable(orders.getStrValue(c, orderRow, "xxx"))) {
                        log.debug("Adding column info to ivrmap:" + c + "::" + orders.getStrValue(c, orderRow, "xxx"));
                        ivrColumnIndexNameMap.put(c, orders.getStrValue(c, orderRow, "xxx"));

                    } else {
                        sql.append((c == 0 ? "" : ",") + "[" + orders.getStrValue(c, orderRow, "xxx") + "]");
                    }
                     log.debug(c+":" + orders.getStrValue(c, orderRow, "xxx") + ":");

                }
                sql.append(",[ivr_vars]) VALUES(");

            } else {
                Map<String, String> ivrMap = new TreeMap<String, String>();

                rowSql = new StringBuffer();
                rowSql.append(sql);
                for (int c = 0; c < orders.getRowSize(0); c++) {

                   // log.debug(ivrColumnIndexNameMap);
                  //  log.debug("checking if ivrcolumns includes " + c + ": " + ivrColumnIndexNameMap.keySet().contains(c));
                    if (ivrColumnIndexNameMap.keySet().contains(c)) {
                        log.debug("got an IVR match on " + c);
                        //see if relevant to campaign

                        String varName = ivrColumnIndexNameMap.get(c);
                        log.debug("got var name "+varName);
                        if (campByIdMap.keySet().contains(getClientIdFromIvrVarName(varName))) {
                            //check if campaign name for call matches list for client id
                            String rowCamp = orders.getStrValue(campColIndex, orderRow, "");
                            log.debug("rowCamp="+rowCamp);
                            if (campByIdMap.get(getClientIdFromIvrVarName(varName)).contains(rowCamp)) {
                                log.debug("AAdding to ivr map--"+ivrColumnIndexNameMap.get(c)+"::"+orders.getStrValue(c, orderRow, ""));
                                ivrMap.put(ivrColumnIndexNameMap.get(c), orders.getStrValue(c, orderRow, "").replaceAll("'", ""));
                            }
                        }
                    } else {
                        rowSql.append((c == 0 ? "" : ",") + (c == callIdColIndex ? "" : "'") + orders.getStrValue(c, orderRow, "").replaceAll("'", "") + (c == callIdColIndex ? "" : "'"));
                         log.debug(c+":" + orders.getStrValue(c, orderRow, "xxx") + ":");
                        if (c == callIdColIndex) {
                            callId = orders.getStrValue(c, orderRow, "xxx");
                        }
                       if (c == dispositionIndex){
                           disposition = orders.getStrValue(c, orderRow, "");
                       }
                    }
                }
                log.debug("ivrmap:"+ivrMap);
                StringBuffer ivrXml = new StringBuffer();
                ivrXml.append("<ivr>");
                for (String key : ivrMap.keySet()) {
                    ivrXml.append("<" + getShortVarNameFromIvrVarName(key) + ">" + StringEscapeUtils.escapeXml(ivrMap.get(key)) + "</" + getShortVarNameFromIvrVarName(key) + ">");
                }
                ivrXml.append("</ivr>");
              //  log.debug(ivrXml);

                rowSql.append(",'" + ivrXml.toString() + "');");
                lastStatement="if not exists (select * from cc_five9_reports where [call id]= " + callId + ") " + rowSql.toString();

                PreparedStatement ps = HibernateSession.getPreparedStatement("if not exists (select * from cc_five9_reports where [call id]= " + callId + " and disposition = '"+disposition+"') " + rowSql.toString());
                try {
                    ps.executeUpdate();
                } catch(Exception ex) {
                       log.debug(ex.getMessage()) ;
                } finally {
                    ps.close();
                }
                HibUtils.commit(HibernateSession.currentSession());

            }


        }

            lastStatement="second log update commit";
        HibUtils.commit(HibernateSession.currentSession());


        }catch(Exception ex)
        {
            throw new Exception(ex.getMessage()+":"+lastStatement);
        }
    }

    public static void test() throws Exception {
        DelimitedReader orders = new CSVReader(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(Five9API.getCurrentCallLog(8).getBytes()))), false);
        applyFive9Report(orders);

    }

    private static boolean isIvrVariable(String input) {
        boolean output = false;

        if (input.contains(".") && input.startsWith("custom_ivr")) {
            output = true;
        }
        return output;
    }


    private static String getShortVarNameFromIvrVarName(String input) {
        String output = "";

        if (input.contains(".")) {
            input = input.substring(input.lastIndexOf(".") + 1);
            if (input.contains("_") && input.length()>(input.indexOf("_")+1)) {
                input = input.substring(input.indexOf("_")+1);
                if (input.length() > 0) {

                        output = input;

                }
            }
        }
        log.debug("cidfromvarname_out:"+output);

        return output;
    }
    private static String getClientIdFromIvrVarName(String input) {
        String output = "";

        log.debug("cidfromvarname_in:"+input);
        if (input.contains(".")) {
            input = input.substring(input.lastIndexOf(".") + 1);
            if (input.contains("_")) {
                input = input.substring(0, input.indexOf("_"));
                if (input.length() > 0) {
                    try {
                        int tester = Integer.parseInt(input);
                        output = input;

                    } catch (Exception ex) {

                    }
                }
            }
        }
        log.debug("cidfromvarname_out:"+output);

        return output;
    }

    private static String convertStringToValidColumnName(String input) {
        String output = input;

        if (input.contains(".")) {
            output = input.substring(input.lastIndexOf(".") + 1);
        }
        return output;
    }
}
