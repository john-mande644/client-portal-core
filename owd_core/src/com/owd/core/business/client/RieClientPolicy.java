package com.owd.core.business.client;

import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 5/2/11
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class RieClientPolicy extends com.owd.core.business.client.DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

     @Override
    public String getOWDShipMethodForExternalShipMethodName(String remoteSourceKey, String remoteMethod, Order order) {
        if (remoteSourceKey.equalsIgnoreCase("paypal")) {
                try {

                      if(remoteMethod.equalsIgnoreCase("UPS Next Day Air"))
                      {
                         return "UPS Next Day Air Saver";
                      } else

  if(remoteMethod.equalsIgnoreCase("UPS Second Day Air"))
                      {
                         return "UPS 2nd Day Air";
                      } else
  if(remoteMethod.equalsIgnoreCase("UPS Ground"))
                      {
                         return "UPS Ground";
                      } else
  if(remoteMethod.equalsIgnoreCase("UPS Worldwide Express"))
                      {
                         return "UPS Worldwide Express";
                      } else
  if(remoteMethod.equalsIgnoreCase("UPS Worldwide Expedited"))
                      {
                         return "UPS Worldwide Expedited";
                      } else
  if(remoteMethod.equalsIgnoreCase("UPS Standard"))
                      {
                         return "UPS Standard Canada";
                      } else
  if(remoteMethod.equalsIgnoreCase("UPS Three-Day Select"))
                      {
                         return "UPS 3 Day Select";
                      } else
  if(remoteMethod.equalsIgnoreCase("UPS Worldwide Express Plus"))
                      {
                         return "UPS Worldwide Express Plus";
                      }


                    return remoteMethod;

                } catch (Exception ex) {
                    return "No valid rate returned";
                }

        }
        return remoteMethod;
    }
}
