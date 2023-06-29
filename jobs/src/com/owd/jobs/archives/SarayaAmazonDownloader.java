package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.amazon.AmazonAPI;
import com.owd.jobs.jobobjects.amazon.AmazonConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/15/12
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */



public class SarayaAmazonDownloader extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    public static AmazonConfig config;

    static {
         config = new AmazonConfig("AKIAJYGHMG3CA3UBVAGA",
                "neKnjy+k0Q9i0BBpLBbYdvAZhYZeLu0/8NhQJd5X",
                "Amazon Downloader",
                "1.0",
                "AVMAALDQF459F","https://mws.amazonservices.com/");

        config.addMarketPlaceId("ATVPDKIKX0DER");
    }
    @Override
    public void internalExecute() {


        AmazonAPI fetcher = new AmazonAPI(config,444);
        Map<String,List<String>> shipMap = new TreeMap<String,List<String>>();

        shipMap.put("STANDARD", Arrays.asList("TANDATA_USPS.USPS.FIRST", "TANDATA_USPS.USPS.PRIORITY",
                "CONNECTSHIP_UPS.UPS.GND", "CONNECTSHIP_UPS.UPS.STD", "TANDATA_USPS.USPS.I_FIRST", "TANDATA_USPS.USPS.I_PRIORITY", "UPS.STDCAMX"));

        shipMap.put("2DAY", Arrays.asList("FDX.2DA", "UPS.2DA",
                "POS.EXP"));

        shipMap.put("2 DAY", Arrays.asList("FDX.2DA", "UPS.2DA",
                "POS.EXP"));

        shipMap.put("2-DAY", Arrays.asList("FDX.2DA", "UPS.2DA",
                "POS.EXP"));

        fetcher.setShipMethodMap(shipMap);
        DatatypeFactory df = null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            log.error(e.getMessage(), e);
        }

        /******************************************
         * Uncomment the desired fetchOrders call *
         ******************************************/

        /* Fetch orders for the last 24 hours GMT */
        XMLGregorianCalendar start1 = df
                .newXMLGregorianCalendar(new GregorianCalendar());

        Duration negativeOneDay = df.newDurationDayTime(false, 0, 288, 0, 0);
        start1.add(negativeOneDay);
        try {
          //      fetcher.getReportScheduleList();
         //    fetcher.queueNewUnshippedOrdersReport("5343448968");
         //   fetcher.listFeedRequests();
            fetcher.setStartCal(start1);
            fetcher.setEndCal(null);
            fetcher.importCurrentOrders();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }


    }

    public static void main(String[] args)
    {
        run();
    }
}
