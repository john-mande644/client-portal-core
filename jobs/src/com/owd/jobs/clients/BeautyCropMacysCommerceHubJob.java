package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.BeautyCropMacysCommerceHubAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

public class BeautyCropMacysCommerceHubJob extends OWDStatefulJob {
    private final static Logger log = LogManager.getLogger();
    private final static int clientID = 696;
    private final static String vendorName = "BeautyCrop";

    public static void main(String[] args) throws Exception {
        // Add code here to run job manually
        run();
    }

    public void internalExecute() {

        try {
            BeautyCropMacysCommerceHubAPI api = new BeautyCropMacysCommerceHubAPI();
            api.configure("cropbeauty.sftp.commercehub.com", "cropbeauty", "High1y2Singer#Stable!", "macys", vendorName, "macys", clientID);

            api.retrieveOrderFiles();
            api.processOrderFiles();
            api.pushInventoryFile();

        } catch (Exception ex) {

            ex.printStackTrace();
            Exception exl = new LogableException(ex, "Generic order import error from BeautyCrop Macys:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), String.valueOf(clientID), vendorName, LogableException.errorTypes.ORDER_IMPORT);
        }
    }
}
