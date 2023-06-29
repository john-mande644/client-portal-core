package com.owd.jobs.jobobjects.amazon;

import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentServiceAsyncClient;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentServiceClient;
import com.amazonservices.mws.merchantfulfillment._2015_06_01.MWSMerchantFulfillmentServiceConfig;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceConfig;
import com.amazonaws.mws.model.IdList;
import com.amazonservices.mws.orders.model.MarketplaceIdList;

import java.util.ArrayList;
import java.util.List;

public class AmazonConfig  implements Comparable {
private final static Logger log =  LogManager.getLogger();


    /************************************************************************
     * Set Access Key ID, Secret Acess Key ID, Seller ID, etc.
     ***********************************************************************/
    public   String accessKeyId = "AKIAJ4T5DTEYPPIRYUCQ";
    public   String secretAccessKey = "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";
    public   String applicationName = "Amazon Downloader";
    public   String applicationVersion = "1.0";
    public   String serviceUrl = "https://mws.amazonservices.com";

    public   String sellerId = "A1XA37IUKCYG4J";

    private  List<String> marketplaceIdArrayList = new ArrayList<String>();
    public  MarketplaceIdList marketplaceIdList = null;
    public IdList marketplaceIdListAsIdList = null;

    public MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmazonConfig that = (AmazonConfig) o;

        if (!accessKeyId.equals(that.accessKeyId)) return false;
        if (!applicationName.equals(that.applicationName)) return false;
        if (!applicationVersion.equals(that.applicationVersion)) return false;
        if (!config.equals(that.config)) return false;
        if (!marketplaceIdArrayList.equals(that.marketplaceIdArrayList)) return false;
        if (!secretAccessKey.equals(that.secretAccessKey)) return false;
        if (!sellerId.equals(that.sellerId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accessKeyId.hashCode();
        result = 31 * result + secretAccessKey.hashCode();
        result = 31 * result + applicationName.hashCode();
        result = 31 * result + applicationVersion.hashCode();
        result = 31 * result + sellerId.hashCode();
        result = 31 * result + marketplaceIdArrayList.hashCode();
        result = 31 * result + config.hashCode();
        return result;
    }

    public  AmazonConfig(String accessKey, String secretAccessKey, String appName, String appVersion, String sellerId, String serviceEndpoint)
    {
        accessKeyId = accessKey;
        this.secretAccessKey = secretAccessKey;
        applicationName = appName;
        applicationVersion = appVersion;

        this.sellerId = sellerId;
        marketplaceIdList = new MarketplaceIdList(marketplaceIdArrayList);
        marketplaceIdListAsIdList = new IdList(marketplaceIdArrayList);
        serviceUrl = serviceEndpoint;

        config.setServiceURL(serviceEndpoint);

    }
    public  void addMarketPlaceId(String id)
    {
        if(!(marketplaceIdArrayList.contains(id)))
        {
            marketplaceIdArrayList.add(id);
        }
        // marketplaceIdArrayList.add("<MarketplaceID2>");
        marketplaceIdList = new MarketplaceIdList(marketplaceIdArrayList);
    }


    /** The client, lazy initialized. Async client is also a sync client. */
    private  MWSMerchantFulfillmentServiceAsyncClient client = null;

    public  MWSMerchantFulfillmentServiceClient getClient() {
        return getAsyncClient();
    }

    /**
     * Get an async client connection ready to use.
     *
     * @return A ready to use client connection.
     */
    public  synchronized MWSMerchantFulfillmentServiceAsyncClient getAsyncClient() {
        if (client==null) {
            MWSMerchantFulfillmentServiceConfig configm = new MWSMerchantFulfillmentServiceConfig();
            configm.setServiceURL(config.getServiceURL());
            // Set other client connection configurations here.
            client = new MWSMerchantFulfillmentServiceAsyncClient(accessKeyId, secretAccessKey,
                    applicationName, applicationVersion, configm, null);
        }
        return client;
    }
    public MarketplaceWebServiceClient getMarketplaceWebService()
    {
        return new MarketplaceWebServiceClient(
            accessKeyId, secretAccessKey, applicationName, applicationVersion, config);
    }

    @Override
    public int compareTo(Object o) {

        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return 0;

        boolean same = this.hashCode() == (o.hashCode());
        return same ? 0: this.hashCode()-o.hashCode();
    }


    /************************************************************************
     * You can also try advanced configuration options. Available options are:
     * 
     * - Signature Version - Proxy Host and Proxy Port - User Agent String to be
     * sent to Marketplace Web Service
     *************************************************************************/


}
