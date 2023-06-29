package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.SokoMacysCommerceHubAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

public class SokoMacysCommerceHubJob extends OWDStatefulJob {
    private final static Logger log = LogManager.getLogger();
    private final static int clientID = 636;
    private final static String vendorName = "Soko";

    public static void main(String[] args) throws Exception {
        // Add code here to run job manually
        run();
    }

    public void internalExecute() {

        try {
            SokoMacysCommerceHubAPI api = new SokoMacysCommerceHubAPI();
            api.configure("soko.sftp.commercehub.com", "soko", "Proper0Fuel3Organize$", "macys", vendorName, "macys", clientID);

            api.retrieveOrderFiles();
            api.processOrderFiles();
            api.pushInventoryFile();

        } catch (Exception ex) {

            ex.printStackTrace();
            Exception exl = new LogableException(ex, "Generic order import error from Soko Macys:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), String.valueOf(clientID), vendorName, LogableException.errorTypes.ORDER_IMPORT);
        }
    }
}
