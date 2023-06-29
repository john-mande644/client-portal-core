package com.owd.core.business.client;

import com.owd.core.business.order.Order;
import com.owd.core.csXml.OrderRater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Sep 17, 2009
 * Time: 4:04:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class PeaceHillPressClientPolicy extends DefaultClientPolicy implements ClientPolicy  {
private final static Logger log =  LogManager.getLogger();

       //call *after* filling in ship address and item information
    public String getOWDShipMethodForExternalShipMethodName(String remoteSourceKey, String remoteMethod, Order order)
    {
       if(remoteSourceKey.equals("magento"))
        {
            if(remoteMethod.toUpperCase().contains("FREE SHIPPING"))
            {
                try
                {
                OrderRater rater = new OrderRater(order);
                    rater.setCalculateKitWeights(true);
                    List<String> alternateShipMethodList = new ArrayList<String>();
                    alternateShipMethodList.add("TANDATA_USPS.USPS.FIRST");
                    alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                    alternateShipMethodList.add("TANDATA_USPS.USPS.I_FIRST");
                    alternateShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
                    alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.STD");
                    alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.GND");
                    alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                    alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");

                  return rater.selectBestWay(alternateShipMethodList);

                }catch(Exception ex)
                {
                    return "No valid rate returned";
                }
            }
        }
        return remoteMethod;

    }
      public String translateRemoteSkuToOwdSku(String remoteSourceKey, String remoteSku)
    {
        if(remoteSourceKey.equals("magento"))
        {

        }
        return remoteSku;
    }
}
