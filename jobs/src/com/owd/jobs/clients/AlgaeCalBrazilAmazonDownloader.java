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



public class AlgaeCalBrazilAmazonDownloader extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    public static Map<String,AmazonConfig> configList = new TreeMap<String,AmazonConfig>();

    static {
         AmazonConfig config = new AmazonConfig("AKIAJGRY3I7GQ27ICZJA",
                "IF3KE9so7hKGRpKWO5+5Gp7gFdhGyj9k/hrlQnT1",
                "Amazon Downloader",
                "1.0",
                "A2RYPG8US83EIW","https://mws.amazonservices.com/");

        config.addMarketPlaceId("ATVPDKIKX0DER");

        configList.put("AMAZONCC",config);

    }
    @Override
    public void internalExecute() {

        for(String configName:configList.keySet()) {

            AmazonAPI fetcher = new AmazonAPI(configList.get(configName), 266) {

                @Override
                public void doFinalStuffBeforeSavingOrder(Order order) throws Exception {
                    order.template = "amazon";
                    if(order.containsSKU("CC147g"))
                    {
                        order.addLineItem("SCOOP", ""+order.getQuantityForSKU("CC147g"), "0.00", "0.00", "Scoop", "", "");
                    }
                }

                @Override
                public boolean addLineItem(Order order, String sku, String qty, String price, String linePrice, String title, String color, String size) throws Exception {

                    boolean isOwdItem = true;


                    order.addLineItem(sku,
                            qty,
                            price,
                            linePrice,
                            "",
                            color, size);

                    return isOwdItem;
                }


            };
            fetcher.setSource(configName);

            Map<String, List<String>> shipMap = new TreeMap<String, List<String>>();
            shipMap.put("STANDARD", Arrays.asList("TANDATA_USPS.USPS.FIRST", "TANDATA_USPS.USPS.PRIORITY",
                    "TANDATA_FEDEXFSMS.FEDEX.GND", "TANDATA_FEDEXFSMS.FEDEX.FHD", "TANDATA_USPS.USPS.I_FIRST", "TANDATA_USPS.USPS.I_PRIORITY"));
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
    }

    public static void main(String[] args)
    {
        run();
    }
}
