package com.owd.jobs.jobobjects.five9.owd;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.jobs.jobobjects.five9.*;
import com.sun.xml.ws.client.BindingProviderProperties;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 4/24/13
 * Time: 9:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class Five9API {
private final static Logger log =  LogManager.getLogger();

    static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

    static WsAdminV2 port;


    private Map<String, String> currCampaignMap = null;
    private Map<String, List<String>> currCampaignMapInverted = null;

    synchronized static WsAdminV2 getApi() {
        if(port==null)
        {
            WsAdminServiceV2 svc = new WsAdminServiceV2();
             port = svc.getWsAdminPortV2();


            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "sbuskirk");
            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "Five9API!");


            Map<String, Object> requestContext = ((BindingProvider)port).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 120000); // Timeout in millis
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 10000); // Timeout in millis
        }


        return port;
    }

    synchronized public Map<String, String> getCampaignMap() throws Exception {

        if (currCampaignMap == null) {
            WsAdminV2 api = getApi();
            currCampaignMap = new TreeMap<String, String>();


            List<Campaign> camps = api.getCampaigns(".*", CampaignType.INBOUND);
            for (Campaign c : camps) {
                if (c != null) {
                try {
                    log.debug("Campaign:" + c);



                        String desc = c.getDescription()+"";
                        if (desc.contains("(")) {
                            desc = desc.substring(desc.lastIndexOf("(") + 1);
                            if (desc.contains(")")) {
                                desc = desc.substring(0, desc.indexOf(")"));
                            }
                        }
                        currCampaignMap.put(c.getName(), "" + Integer.parseInt(desc));

                    }catch(NumberFormatException ex){
                        log.debug("Campaign has no client ID:" + c.getName() + ":" + c.getDescription());
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }

        return currCampaignMap;

    }

    synchronized public Map<String, List<String>> getCampaignMapByClientId() throws Exception {

        if (currCampaignMapInverted == null) {
            WsAdminV2 api = getApi();
            currCampaignMapInverted = new TreeMap<String, List<String>>();


            List<Campaign> camps = api.getCampaigns(".*", CampaignType.INBOUND);
            for (Campaign c : camps) {
                if(c!=null) {
                    String desc = c.getDescription();
                    if(desc==null) {
                        desc = "";
                    }
                    if (desc.contains("(")) {
                        desc = desc.substring(desc.lastIndexOf("(") + 1);
                        if (desc.contains(")")) {
                            desc = desc.substring(0, desc.indexOf(")"));
                        }
                    }
                    try {
                        String key = "" + Integer.parseInt(desc);


                        if (currCampaignMapInverted.containsKey(key)) {
                            currCampaignMapInverted.get(key).add(c.getName());
                        } else {
                            List<String> cnames = new ArrayList<String>();
                            cnames.add(c.getName());
                            currCampaignMapInverted.put(key, cnames);
                        }

                    } catch (NumberFormatException ex) {
                        log.debug("Campaign has no client ID:" + c.getName() + ":" + c.getDescription());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        return currCampaignMapInverted;

    }

    public static void main(String[] args) throws Exception {


       System.out.println(getCurrentEmailChat(36));
    }



    public static String getArbitraryCallLog(GregorianCalendar startDate, int hoursBack) throws Exception
    {
        WsAdminV2 api = getApi();



        ReportTimeCriteria rtc = new ReportTimeCriteria();
        CallLogReportCriteria clrc = new CallLogReportCriteria();

        DatatypeFactory df = null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }


        /******************************************
         * Uncomment the desired fetchOrders call *
         ******************************************/

        /* Fetch orders for the last 24 hours GMT */
        XMLGregorianCalendar start1 = df.newXMLGregorianCalendar(startDate);


        XMLGregorianCalendar endCal = df.newXMLGregorianCalendar(startDate);

        //+2 is due to central/pacific time zone difference

        Duration negativeOneDay = df.newDurationDayTime(false, 0, hoursBack+2, 0, 0);
        start1.add(negativeOneDay);
        rtc.setStart(start1);
         rtc.setEnd(endCal);

        log.debug("Start:"+sdf.format(start1.toGregorianCalendar().getTime()));
        log.debug("End:"+sdf.format(endCal.toGregorianCalendar().getTime()));


        CustomReportCriteria crc = new CustomReportCriteria();
        crc.setTime(rtc);


        String ref = api.runReport("APIReports", "CallLogTest", crc);
        int loops=0;
        while (loops<120 && api.isReportRunning(ref, 600)) {
            Thread.sleep(1000L);
            loops++;
        }
        if(loops>=120)
        {
            throw new Exception("Failed to get report in 120 seconds");
        }

        return api.getReportResultCsv(ref);

    }


    public static String getCurrentEmailChat(int hoursBack) throws Exception
    {
        WsAdminV2 api = getApi();



        ReportTimeCriteria rtc = new ReportTimeCriteria();
        CallLogReportCriteria clrc = new CallLogReportCriteria();

        DatatypeFactory df = null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }


        /******************************************
         * Uncomment the desired fetchOrders call *
         ******************************************/

        /* Fetch orders for the last 24 hours GMT */
        XMLGregorianCalendar start1 = df.newXMLGregorianCalendar(new GregorianCalendar());

        //+2 is due to central/pacific time zone difference

        Duration negativeOneDay = df.newDurationDayTime(false, 0, hoursBack+2, 0, 0);
        start1.add(negativeOneDay);
        rtc.setStart(start1);
        // rtc.setEnd

        CustomReportCriteria crc = new CustomReportCriteria();

        crc.setTime(rtc);


        String ref = api.runReport("APIReports", "EmailChatData", crc);
        int loops=0;
        while (loops<120 && api.isReportRunning(ref, 600)) {
            Thread.sleep(1000L);
            loops++;
        }
        if(loops>=120)
        {
            throw new Exception("Failed to get report in 120 seconds");
        }

        return api.getReportResultCsv(ref);

    }

    public static String getCurrentCallLog(int hoursBack) throws Exception
    {
        WsAdminV2 api = getApi();



        ReportTimeCriteria rtc = new ReportTimeCriteria();
        CallLogReportCriteria clrc = new CallLogReportCriteria();

        DatatypeFactory df = null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }


        /******************************************
         * Uncomment the desired fetchOrders call *
         ******************************************/

        /* Fetch orders for the last 24 hours GMT */
        XMLGregorianCalendar start1 = df.newXMLGregorianCalendar(new GregorianCalendar());

        //+2 is due to central/pacific time zone difference

        Duration negativeOneDay = df.newDurationDayTime(false, 0, hoursBack+2, 0, 0);
        start1.add(negativeOneDay);
        rtc.setStart(start1);
       // rtc.setEnd

        CustomReportCriteria crc = new CustomReportCriteria();
        crc.setTime(rtc);


        String ref = api.runReport("APIReports", "CallLogTest", crc);
        int loops=0;
        while (loops<120 && api.isReportRunning(ref, 600)) {
            Thread.sleep(1000L);
            loops++;
        }
        if(loops>=120)
        {
            throw new Exception("Failed to get report in 120 seconds");
        }

        return api.getReportResultCsv(ref);

    }

    public static String testReports(Date start, Date end) throws Exception {

        ReportTimeCriteria rtc = new ReportTimeCriteria();
        CallLogReportCriteria clrc = new CallLogReportCriteria();

        DatatypeFactory df = null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }


        /******************************************
         * Uncomment the desired fetchOrders call *
         ******************************************/

        /* Fetch orders for the last 24 hours GMT */

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(start);
        XMLGregorianCalendar start1 = df.newXMLGregorianCalendar(c);

        GregorianCalendar ce = new GregorianCalendar();
        ce.setTime(end);
        XMLGregorianCalendar end1 = df.newXMLGregorianCalendar(ce);

        rtc.setStart(start1);
        rtc.setEnd(end1);

        //  String results = port.getCallLogReportCsv(rtc,clrc);

        //log.debug(results);

        CustomReportCriteria crc = new CustomReportCriteria();
        crc.setTime(rtc);

        String ref = port.runReport("APIReports", "CallLogTest", crc);
        while (port.isReportRunning(ref, 600)) {
            Thread.sleep(1000L);
        }

        String reply = port.getReportResultCsv(ref);
        log.debug(reply);
        return reply;
    }
}
