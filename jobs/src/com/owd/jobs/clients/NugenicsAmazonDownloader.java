package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;
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



public class NugenicsAmazonDownloader extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    public static Map<String,AmazonConfig> configList = new TreeMap<String,AmazonConfig>();

    static {

        //US webstore and marketplace
         AmazonConfig config = new AmazonConfig("AKIAIIFUNYYFO62O6YPQ",
                "qfCtYkkuzRtcPoPTsEd7y/tDmXABbB37FgKxLx/i",
                "Amazon Downloader",
                "1.0",
                "AKR91MEWBO283","https://mws.amazonservices.com/");
        config.addMarketPlaceId("ATVPDKIKX0DER");
        configList.put("AMAZON",config);

        //Amazon UK
           config = new AmazonConfig("AKIAIUK5BKY7WGWEE37Q",
                "7W0v0UWPfJwTNYFfOxlvbzHLu0AqxWf4c+rjcv4j",
                "Amazon Downloader",
                "1.0",
                "A3TVQPSEEX9JPJ","https://mws-eu.amazonservices.com/");
        config.addMarketPlaceId("A1F83G8C2ARO7P");
        configList.put("AMAZONUK",config);

        //Amazon Canada
        config = new AmazonConfig("AKIAI5RPT24PNKYLIF7Q",
                "cnWrvA+gr+EBEa7d9H7fHpX15IOLkRUPiTWcbD4j",
                "Amazon Downloader",
                "1.0",
                "A1T7ZJXU4BMPPA","https://mws.amazonservices.ca/");
        config.addMarketPlaceId("A2EUQ1WTGCTBG2");
        configList.put("AMAZONCA",config);

        config = new AmazonConfig("AKIAIUK5BKY7WGWEE37Q",
                "7W0v0UWPfJwTNYFfOxlvbzHLu0AqxWf4c+rjcv4j",
                "Amazon Downloader",
                "1.0",
                "A3TVQPSEEX9JPJ","https://mws-eu.amazonservices.com/");
        config.addMarketPlaceId("A13V1IB3VIYZZH");
        configList.put("AMAZONFR",config);

        config = new AmazonConfig("AKIAIUK5BKY7WGWEE37Q",
                "7W0v0UWPfJwTNYFfOxlvbzHLu0AqxWf4c+rjcv4j",
                "Amazon Downloader",
                "1.0",
                "A3TVQPSEEX9JPJ","https://mws-eu.amazonservices.com/");
        config.addMarketPlaceId("A1PA6795UKMFR9");
        configList.put("AMAZONDE",config);



    }
    @Override
    public void internalExecute() {

        for(String configName:configList.keySet())
        {

        AmazonAPI fetcher = new AmazonAPI(configList.get(configName),494)
        {


         @Override
         public boolean addLineItem(Order order, String sku, String qty, String price, String linePrice, String title, String color, String size) throws Exception {

             boolean isOwdItem = true;


             order.addLineItem(sku,
                     qty,
                     price,
                     linePrice,
                     title,
                     color, size);

             return isOwdItem;
         }



        @Override
        public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
        {
            order.addInsertItemIfAvailable("INS-RFRG",1);

        }
        };
            fetcher.setSource(configName);


            Map<String,List<String>> shipMap = new TreeMap<String,List<String>>();

            if(configName.equals("AMAZONUK") || configName.equals("AMAZONFR")|| configName.equals("AMAZONDE"))
            {

                shipMap.put("STANDARD", Arrays.asList("TANDATA_USPS.USPS.PRIORITY",
                        "TANDATA_FEDEXFSMS.FEDEX.IECO"));

                shipMap.put("EXPEDITED", Arrays.asList( "FDX.2DA","TANDATA_FEDEXFSMS.FEDEX.IPRI"));
            }   else if (configName.equals("AMAZONCA"))
            {

                shipMap.put("STANDARD", Arrays.asList("TANDATA_USPS.USPS.PRIORITY",
                         "TANDATA_FEDEXFSMS.FEDEX.IPRI"));

                shipMap.put("EXPEDITED", Arrays.asList( "FDX.2DA","TANDATA_FEDEXFSMS.FEDEX.IPRI"));
            }   else
            {

        shipMap.put("STANDARD", Arrays.asList("TANDATA_USPS.USPS.PRIORITY",
                "FDX.GND",   "TANDATA_FEDEXFSMS.FEDEX.IECO"));

        shipMap.put("EXPEDITED", Arrays.asList(
                "TANDATA_FEDEXFSMS.FEDEX.IPRI", "FDX.2DA"));

            }
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
        Duration negativeOneDay = df.newDurationDayTime(false, 0, 144, 0, 0);
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
    }

    public static void main(String[] args) throws Exception
    {
        run();

    }
}
