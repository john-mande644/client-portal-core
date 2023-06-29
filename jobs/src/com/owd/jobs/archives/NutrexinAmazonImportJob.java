package com.owd.jobs.archives;

import com.owd.LogableException;
import com.owd.core.business.order.Order;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.amazon.AmazonAPI;
import com.owd.jobs.jobobjects.amazon.AmazonConfig;
import com.owd.jobs.jobobjects.amazon.AmazonFeeds;
import com.owd.jobs.jobobjects.bigcommerce.BigCommerceAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/1/12
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class NutrexinAmazonImportJob extends OWDStatefulJob {
    private final static Logger log = LogManager.getLogger();

    public static AmazonConfig config;

    /*
        Seller account identifiers for Nutrexin
Seller ID:	A1XLIIRZ32QO8B
Marketplace ID:	A2EUQ1WTGCTBG2 (Amazon.ca)
ATVPDKIKX0DER (Amazon.com)
Developer account identifier and credentials
Developer Account Number:	0509-6090-9732
AWS Access Key ID:	AKIAJEREC2RCUNRWVJCQ
Secret Key:	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
     */
    static {
        config = new AmazonConfig("AKIAJEREC2RCUNRWVJCQ",
                "YDFg02L87eiJ5D5ZoKQ8bLDf/dylgW2s8ldBH5I0",
                "Amazon Downloader",
                "1.0",
                "A1XLIIRZ32QO8B", "https://mws.amazonservices.com/");

        config.addMarketPlaceId("ATVPDKIKX0DER");
    }

    @Override
    public void internalExecute() {



        AmazonAPI fetcher = new AmazonAPI(config, 536);

        Map<String, List<String>> shipMap = new TreeMap<String, List<String>>();
        shipMap.put("STANDARD", Arrays.asList("TANDATA_USPS.USPS.FIRST", "TANDATA_USPS.USPS.PRIORITY",
                "TANDATA_FEDEXFSMS.FEDEX.GND", "TANDATA_FEDEXFSMS.FEDEX.FHD", "TANDATA_USPS.USPS.I_FIRST", "TANDATA_USPS.USPS.I_PRIORITY"));
        fetcher.setShipMethodMap(shipMap);


        /* Fetch orders for the last 24 hours GMT */

        try {
            fetcher.importCurrentOrders();
            fetcher.getProductData();



            Map<String, Integer> updateItems = new HashMap<String,Integer>();
            for(String sku:fetcher.getProductList())
            {
                log.debug("checking Amazon SKU "+sku);
                try {
                    OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku(fetcher.clientId + "", sku);
                    long oh = item.getOwdInventoryOh().getQtyOnHand();

                    if (oh < 0) {
                        oh = 0;
                    }
                    oh = ((Double)(Math.floor(((Long)oh).doubleValue()*0.80))).longValue();
                    updateItems.put(sku,(int)oh);
                    System.out.println("Added "+sku+":"+oh);
                }catch(Exception ex)
                {

                }
            }
            System.out.println(updateItems);
            AmazonFeeds.updateProductStockLevels(fetcher.aconfig,updateItems);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
           // throw ex;
        }


    }

    public static void main(String[] args) {
        run();
    }
}