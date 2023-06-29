package com.owd.jobs.archives;

import com.owd.LogableException;
import com.owd.core.business.order.Order;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.bigcommerce.BigCommerceAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/1/12
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class NutrexinBigCommerceImportJob extends OWDStatefulJob {
    private final static Logger log = LogManager.getLogger();

    public static void main(String[] args) {

        // CasetrackerAPI.createCasetrackerCase("Client ID (55) order tester received on hold3", "Unable to determine ship method for this order; address or items invalid for available ship method options - " +
        //         "attempt to correct address and assign to IT Work Orders if needed.", 5555);

        run();
        //   api.reportShipment(5156008, "9156901074187000034863", true);

    }


    public void internalExecute() {

        try {


            //    BigCommerceAPI api = new BigCommerceAPI("sean@marined3.com","70ff1b8c08af1d96f0d96034a87de44d41f36ad2",
            //          "https://store-80tpv44.mybigcommerce.com/api/v2/")

            BigCommerceAPI api = new BigCommerceAPI("owd", "dd33486fd21e5bafb5c1cd043d51c4603eeace22",
                    "https://www.nutrexinusa.com/api/v2/", "BC") {

                @Override
                public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                    //    order.order_refnum="BC"+order.order_refnum;

                    log.debug("BC import: original ship method : " + oldMethod);
                    if (oldMethod.toUpperCase().contains("FEDEX STANDARD OVERNIGHT")) {
                        return "FedEx Standard Overnight";
                    } else if (oldMethod.toUpperCase().contains("FEDEX 2 DAY")) {
                        return "FedEx 2Day";

                    } else if (oldMethod.toUpperCase().contains("FEDEX GROUND HOME DELIVERY")) {
                        return "FedEx Home Delivery";

                    } else if (oldMethod.toUpperCase().contains("USPS PRIORITY")) {
                        return "USPS Priority Mail";

                    } else {

                        return RateShopper.rateShop(order, Arrays.asList("TANDATA_USPS.USPS.FIRST",
                                "TANDATA_USPS.USPS.PRIORITY", "CONNECTSHIP_UPS.UPS.GND", "TANDATA_FEDEXFSMS.FEDEX.GND", "TANDATA_FEDEXFSMS.FEDEX.FHD",
                                "TANDATA_USPS.USPS.I_PRIORITY", "TANDATA_USPS.USPS.I_FIRST"));
                    }

                }

                @Override
                public boolean addLineItem(Order order, String sku, String qty, String price, String linePrice, String title, String color, String size) throws Exception {

                    try {

                        return super.addLineItem(order, sku.trim(), qty, price, linePrice, title, color, size);
                    } catch (Exception ex) {
                        throw new LogableException(ex, "Unable to recognize SKU " + sku + " for BC order " + order.order_refnum,
                                order.order_refnum,
                                order.getClientID(),
                                this.getClass().getName(),
                                LogableException.errorTypes.ORDER_IMPORT);
//                    Mailer.sendMail("BigCommerce API order import failed for Marine Essentials", "Unable to recognize SKU "+sku+" for BC order "+order.order_refnum, "owditadmin@owd.com", "donotreply@owd.com");
//                    throw ex;
                    }
                }

                @Override
                public void doFinalStuffBeforeSavingOrder(Order order) throws Exception {


                }
            };

            api.setClientInfo(536, null);
            api.setFirstOrderId(111);
            api.shipMethodMap = new TreeMap<String, List<String>>();

            api.setOtherFulfillmentSku("SEPARATE");

            api.importCurrentOrders();


        } catch (Exception ex) {

            throw new LogableException(ex.getMessage(),
                    "",
                    "536",
                    this.getClass().getCanonicalName(),
                    LogableException.errorTypes.UNDEFINED_ERROR);

        } finally {


            HibernateSession.closeSession();
        }

    }


    public void updateInventoryLevels() throws Exception {


    }

}
