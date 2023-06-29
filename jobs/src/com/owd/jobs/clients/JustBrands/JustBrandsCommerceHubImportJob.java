package com.owd.jobs.clients.JustBrands;

import com.owd.LogableException;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.*;
import com.owd.jobs.jobobjects.commercehub.CommerceHubApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class JustBrandsCommerceHubImportJob extends OWDStatefulJob {
    private final static Logger log = LogManager.getLogger();


    public static void main(String[] args) throws Exception {
        //QVC
        //Dicks Sporting Goods
      /*  JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
        api.configure("justbrand.sftp.commercehub.com", "justbrand", "Terribly#Forev3r!Panic6", "dsg", "Just Brand Limited", "dsg", 626);

       api.retrieveOrderFiles();
        api.processOrderFiles();
        api.pushInventoryFile();*/

        run();

    }

    public void internalExecute() {


        try {

            //Bed Batch Beyond
            JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
            api.configure("justbrand.sftp.commercehub.com", "justbrand", "Terribly#Forev3r!Panic6", "bedbath", "Just Brand Limited", "bedbath", 626);

             api.retrieveOrderFiles();
             api.processOrderFiles();
            api.pushInventoryFile();

        } catch (Exception ex) {

            ex.printStackTrace();
            Exception exl = new LogableException(ex, "Generic order import error from BedBath:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "626", "BedBath", LogableException.errorTypes.ORDER_IMPORT);

        }
        try {

            //Macys
            JustBrandsMacysCommerceHubAPI api = new JustBrandsMacysCommerceHubAPI();
            api.configure("justbrand.sftp.commercehub.com", "justbrand", "Terribly#Forev3r!Panic6", "macys", "Just Brand Limited", "macys", 626);

            api.retrieveOrderFiles();
            api.processOrderFiles();
            api.pushInventoryFile();

        } catch (Exception ex) {

            ex.printStackTrace();
            Exception exl = new LogableException(ex, "Generic order import error from Macys:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "626", "Macys", LogableException.errorTypes.ORDER_IMPORT);

        }

    }


}
