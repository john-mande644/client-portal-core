package com.owd.jobs.clients.JustBrands;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsLowesCommerceHubAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Sean
 * Date: Jun 15, 2021
 * To change this template use File | Settings | File Templates.
 */
public class JustBrandsCommerceHubLowesJob extends OWDStatefulJob {
    private final static Logger log = LogManager.getLogger();


    public static void main(String[] args) throws Exception {
        run();
    }

    public void internalExecute() {

        //Lowes
        try {
            JustBrandsLowesCommerceHubAPI api = new JustBrandsLowesCommerceHubAPI();
            api.configure("justbrand.sftp.commercehub.com", "justbrand", "Terribly#Forev3r!Panic6", "lowes", "Just " +
                    "Brand Limited", "lowes", 626);

            api.retrieveOrderFiles();
            api.processOrderFiles();
            api.pushInventoryFile();

        } catch (Exception ex) {

            ex.printStackTrace();
            Exception exl = new LogableException(ex, "Generic order import error from Lowes:" + ex.getMessage(),
                    "TS:" + Calendar.getInstance().getTimeInMillis(), "626", "Lowes",
                    LogableException.errorTypes.ORDER_IMPORT);

        }

    }


}
