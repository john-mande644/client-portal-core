package com.owd.jobs.jobobjects.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;
import com.owd.jobs.jobobjects.utilities.RateShopper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Nov 6, 2007
 * Time: 11:56:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class AQAudioUploadData extends OWDUploadOrdersData_1{
private final static Logger log =  LogManager.getLogger();

    public String oldShipMethod = "";

    @Override
    public String translateShipMethod (String oldMethod)
    {
        oldShipMethod = oldMethod;
        return "UPS Ground";

    }

    public String getActualShipMethod(Order order, String oldMethod) throws Exception {



        List<String> currMethods = Arrays.asList("TANDATA_USPS.USPS.PRIORITY",
                "CONNECTSHIP_UPS.UPS.GND","TANDATA_FEDEXFSMS.FEDEX.GND","TANDATA_FEDEXFSMS.FEDEX.FHD", "CONNECTSHIP_UPS.UPS.STD", "TANDATA_USPS.USPS.I_PRIORITY", "UPS.STDCAMX");

        return         RateShopper.rateShop(order, currMethods);

    }
}
