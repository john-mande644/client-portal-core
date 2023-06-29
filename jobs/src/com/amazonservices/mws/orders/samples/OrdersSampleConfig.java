package com.amazonservices.mws.orders.samples;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;

import com.amazonservices.mws.orders.MarketplaceWebServiceOrdersConfig;
import com.amazonservices.mws.orders.model.MarketplaceIdList;

final public class OrdersSampleConfig {
private final static Logger log =  LogManager.getLogger();


    /************************************************************************
     * Set Access Key ID, Secret Acess Key ID, Seller ID, etc.
     ***********************************************************************/
    public static final String accessKeyId = "AKIAJ4T5DTEYPPIRYUCQ";
    public static final String secretAccessKey = "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";
    public static final String applicationName = "Amazon Downloader";
    public static final String applicationVersion = "1.0";

    public static final String sellerId = "A1XA37IUKCYG4J";

    private static List<String> marketplaceIdArrayList = new ArrayList<String>();
    public static MarketplaceIdList marketplaceIdList = null;


    public static MarketplaceWebServiceOrdersConfig config = new MarketplaceWebServiceOrdersConfig();

    static {
        /**************************************************************
         * Add marketplaceIds as required and set to the request object.
         **************************************************************/
         marketplaceIdArrayList.add("A1IUDNEJ3WCUOZ");
        // marketplaceIdArrayList.add("<MarketplaceID2>");
         marketplaceIdList = new MarketplaceIdList(marketplaceIdArrayList);

        /************************************************************************
         * Uncomment to set the appropriate MWS endpoint.
         ************************************************************************/
        // US
         config.setServiceURL("https://mws.amazonservices.com/Orders/2011-01-01");
        // UK
        // config.setServiceURL("https://mws.amazonservices.co.uk/Orders/2011-01-01");
        // Germany
        // config.setServiceURL("https://mws.amazonservices.de/Orders/2011-01-01");
        // France
        // config.setServiceURL("https://mws.amazonservices.fr/Orders/2011-01-01");
        // Italy
        // config.setServiceURL("https://mws.amazonservices.it/Orders/2011-01-01");
        // Japan
        // config.setServiceURL("https://mws.amazonservices.jp/Orders/2011-01-01");
        // China
        // config.setServiceURL("https://mws.amazonservices.com.cn/Orders/2011-01-01");
        // Canada
        // config.setServiceURL("https://mws.amazonservices.ca/Orders/2011-01-01");
        
    }

    /************************************************************************
     * You can also try advanced configuration options. Available options are:
     * 
     * - Signature Version - Proxy Host and Proxy Port - User Agent String to be
     * sent to Marketplace Web Service
     *************************************************************************/

}
