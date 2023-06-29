package com.owd.jobs.clients;

import com.owd.core.business.order.Order;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.OWDInternalAPI.ApiUtils;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.util.Arrays;
import java.util.List;

public class ElevenHonoreInventorySyncJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
        run();
    }

    @Override
    public void internalExecute() {

        log.debug("starting");

        if(ApiUtils.ElevenHonoreInventorySync()){
            log.debug("Start Successful");
        }else{
            log.debug("Start Failed");
        }
    }
}
