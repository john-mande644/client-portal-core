package com.owd.jobs.clients;

import com.owd.core.business.order.Order;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.amazon.AmazonAPI;
import com.owd.jobs.jobobjects.amazon.AmazonConfig;
import com.owd.jobs.jobobjects.clients.BigSexyLingerieOrderRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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


/*
Seller account identifiers for So Sexy Lingerie
Seller ID: 	A2IS2PV5RO9VR5
Marketplace ID: 	A2EUQ1WTGCTBG2 (Amazon.ca)
	ATVPDKIKX0DER (Amazon.com)
	A1AM78C64UM0Y8 (Amazon.com.mx)
Developer account identifier and credentials
Developer Account Number: 	6442-2007-6909
AWS Access Key ID: 	AKIAIRAU5SQTQVAKW7EQ
Secret Key: 	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
 */

public class BigSexyLingerieAmazonDownloader extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    public static Map<String,AmazonConfig> configList = new TreeMap<String,AmazonConfig>();

    static {

        //US webstore and marketplace
         AmazonConfig config = new AmazonConfig("AKIAIRAU5SQTQVAKW7EQ",
                "0L9TQmDiM//ITvFuoWYcUzggJcynHudSlL+CJcXb",
                "Amazon Downloader",
                "1.0",
                "A2IS2PV5RO9VR5","https://mws.amazonservices.com/");
        config.addMarketPlaceId("ATVPDKIKX0DER");
        configList.put("AMAZON",config);

     /*   //Amazon Canada
        config = new AmazonConfig("AKIAIRAU5SQTQVAKW7EQ",
                "0L9TQmDiM//ITvFuoWYcUzggJcynHudSlL+CJcXb",
                "Amazon Downloader",
                "1.0",
                "A2IS2PV5RO9VR5","https://mws.amazonservices.ca/");
        config.addMarketPlaceId("A2EUQ1WTGCTBG2");
        configList.put("AMAZONCA",config);

        //Amazon Mexico
        config = new AmazonConfig("AKIAIRAU5SQTQVAKW7EQ",
                "0L9TQmDiM//ITvFuoWYcUzggJcynHudSlL+CJcXb",
                "Amazon Downloader",
                "1.0",
                "A2IS2PV5RO9VR5","https://mws.amazonservices.com.mx/");
        config.addMarketPlaceId("A1AM78C64UM0Y8");
        configList.put("AMAZONMX",config);*/




    }
    @Override
    public void internalExecute() {

        for(String configName:configList.keySet())
        {

        AmazonAPI fetcher = new AmazonAPI(configList.get(configName),581)
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

            BigSexyLingerieOrderRules.apply(order);
          //  order.testing = true;
        }
        };
            fetcher.setSource(configName);


            Map<String,List<String>> shipMap = new TreeMap<String,List<String>>();

            if (configName.equals("AMAZONCA"))
            {

                shipMap.put("STANDARD", Arrays.asList( "OWD.1ST.LETTER","FDX.HD","FDX.GND","TANDATA_USPS.USPS.PRIORITY","OWD_USPS_I_FIRST",
                        "OWD_USPS_I_PRIORITY","TANDATA_FEDEXFSMS.FEDEX.IECO"));

                shipMap.put("EXPEDITED", Arrays.asList( "FDX.HD","FDX.GND","TANDATA_USPS.USPS.PRIORITY","OWD_USPS_I_FIRST",
                        "OWD_USPS_I_PRIORITY","TANDATA_FEDEXFSMS.FEDEX.IECO"));

            }   else  if (configName.equals("AMAZONMX"))
            {

                shipMap.put("STANDARD", Arrays.asList( "OWD.1ST.LETTER", "FDX.HD","FDX.GND","TANDATA_USPS.USPS.PRIORITY","OWD_USPS_I_FIRST",
                        "OWD_USPS_I_PRIORITY","TANDATA_FEDEXFSMS.FEDEX.IECO"));

                shipMap.put("EXPEDITED", Arrays.asList( "FDX.HD","FDX.GND","TANDATA_USPS.USPS.PRIORITY","OWD_USPS_I_FIRST",
                        "OWD_USPS_I_PRIORITY","TANDATA_FEDEXFSMS.FEDEX.IECO"));

            }   else // Amazon US
            {

                shipMap.put("STANDARD", Arrays.asList( "OWD.1ST.LETTER", "FDX.HD","FDX.GND","TANDATA_USPS.USPS.PRIORITY","OWD_USPS_I_FIRST",
                        "OWD_USPS_I_PRIORITY","TANDATA_FEDEXFSMS.FEDEX.IECO"));

                shipMap.put("EXPEDITED", Arrays.asList( "FDX.HD","FDX.GND","TANDATA_USPS.USPS.PRIORITY","OWD_USPS_I_FIRST",
                        "OWD_USPS_I_PRIORITY","TANDATA_FEDEXFSMS.FEDEX.IECO"));

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
        XMLGregorianCalendar start1 = df.newXMLGregorianCalendar(new GregorianCalendar());
        Duration negativeOneDay = df.newDurationDayTime(false, 0, 144, 0, 0);
        start1.add(negativeOneDay);
        try {
            fetcher.setEndCal(null);
            fetcher.setStartCal(start1);

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
