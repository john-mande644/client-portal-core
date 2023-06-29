package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/1/12
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShowerPowerImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    static {
    }

    public static void main(String[] args) {

        // CasetrackerAPI.createCasetrackerCase("Client ID (55) order tester received on hold3", "Unable to determine ship method for this order; address or items invalid for available ship method options - " +
        //         "attempt to correct address and assign to IT Work Orders if needed.", 5555);

        run();
     //   log.debug(OWDUtilities.encryptData("502"));
        //   api.reportShipment(5156008, "9156901074187000034863", true);

    }


    public void internalExecute() {

        try {
            log.debug("starting ShowerPowerImportJob");
            ShopifyAPI api = new ShopifyAPI("1e7894ccc34315b9b4ace5fa71a6601c",
                    "74055aab1c5324d57c0f85da76c2d6ef", "shower-power.myshopify.com","") {

                @Override
                public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception {



                }


                @Override
                public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                    List<String> currMethods;


                        if (shipMethodMap.containsKey(oldMethod.toUpperCase())) {
                            return shipMethodMap.get(oldMethod.toUpperCase()).get(0);
                        }
                      currMethods  = Arrays.asList("TANDATA_USPS.USPS.FIRST",
                              "TANDATA_USPS.USPS.PRIORITY",
                              "TANDATA_FEDEXFSMS.FEDEX.SP_PS",
                              "TANDATA_FEDEXFSMS.FEDEX.SP_STD",
                            "CONNECTSHIP_UPS.UPS.GND",
                              "CONNECTSHIP_UPS.UPS.STD",
                              "TANDATA_USPS.USPS.I_FIRST", "TANDATA_USPS.USPS.I_PRIORITY", "TANDATA_FEDEXFSMS.FEDEX.GND");

                    return RateShopper.rateShop(order, currMethods);

                }
            };

            api.setClientInfo(545, null);
            api.shipMethodMap = new TreeMap<String, List<String>>();
        //    api.shipMethodMap.put("USPS Priority Mail (2-3 business days)".toUpperCase(), Arrays.asList("USPS Priority Mail"));
         //   api.shipMethodMap.put("USPS Priority Mail Express (1-2 business days)".toUpperCase(), Arrays.asList("USPS Priority Mail Express"));

            api.setOtherFulfillmentSku("SEPARATE");
         //   api.setFulfillmentServiceCode("stewart");

            api.importCurrentOrders(ShopifyAPI.kStatusTypePaid,false);
            api.importCurrentOrders(ShopifyAPI.kStatusTypePartialRefund,false);

        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {


            HibernateSession.closeSession();
        }

    }


    public void updateInventoryLevels() throws Exception {


    }

}
