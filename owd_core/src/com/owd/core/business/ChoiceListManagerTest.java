package com.owd.core.business;

import com.owd.core.business.order.OrderUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Vector;

public class ChoiceListManagerTest {
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args) {
        //log.debug("Starting ChoiceListManagerTest...");
        //log.debug("Getting ChoiceListManager instance...");

        try {

                owdChoiceList ocl = OrderUtilities.getServiceList();

                //log.debug(listName + " List");
                Vector vals = ocl.getValues();
                for (int i = 0; i < vals.size(); i++) {
                    log.debug((String) vals.elementAt(i) + "::*::" + ocl.getRefForValue((String) vals.elementAt(i)));
                }



           // log.debug(ocl.getHTMLSelect(""));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
