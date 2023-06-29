package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.LogableException;
import com.owd.jobs.jobobjects.bigcommerce.BigCommerceAPI;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/1/12
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class MarineEssentialsBigCommerceImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

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

            BigCommerceAPI api = new BigCommerceAPI("owd","db007c2892cf915eda6e566792bbeff879a04ec0",
                    "https://shopping.marineessentials.com/api/v2/","BC"){

                @Override
                public String getActualShipMethod(Order order, String shipmethod)throws Exception {

                //    order.order_refnum="BC"+order.order_refnum;

                    log.debug("BC import: original ship method : "+shipmethod);

                    if(order.getShippingAddress().isInternational())
                    {
                       return "USPS Priority Mail International";
                    }   else{

                    return "USPS Priority Mail"; //per Ritchie
                    }

                   /* if(!(OrderUtilities.orderRefnumExists(order.order_refnum, order.clientID)))
                    {
                    if (currMethods.size() > 0) {
                        if (currMethods.size() == 1) {
                            log.debug("returning "+currMethods.get(0));
                           return  currMethods.get(0);
                        } else {
                          //  log.debug("returning "+RateShopper.rateShop(order, currMethods));
                            return RateShopper.rateShop(order, currMethods);
                        }
                    }
                    } else
                    {
                        return "USPS Priority Mail";
                    }
                    log.debug("returning "+shipmethod);

                    return shipmethod;
                    */
                }

                @Override
                public boolean addLineItem(Order order, String sku, String qty, String price, String linePrice, String title, String color, String size) throws Exception {

                    try{

                        if(sku.startsWith("MD-BR-4B"))
                        {
                            sku = "MD-BR-4B";
                        }

                        if(sku.startsWith("ESL-BR-4B"))
                        {
                            sku = "ESL-BR-4B";
                        }
                          return  super.addLineItem(order, sku.trim(), qty, price, linePrice, title, color, size);
                } catch(Exception ex) {
                        if (!(ex.getMessage().contains("Inventory SKU  is not valid"))) {
                            throw new LogableException(ex, "Unable to recognize SKU " + sku + " for BC order " + order.order_refnum,
                                    order.order_refnum,
                                    order.getClientID(),
                                    this.getClass().getName(),
                                    LogableException.errorTypes.ORDER_IMPORT);
                        }
                        return false;
//                    Mailer.sendMail("BigCommerce API order import failed for Marine Essentials", "Unable to recognize SKU "+sku+" for BC order "+order.order_refnum, "owditadmin@owd.com", "donotreply@owd.com");
//                    throw ex;
                }
                }

                @Override
                public void doFinalStuffBeforeSavingOrder(Order order) throws Exception {



                }
            };

            api.setClientInfo(494, null);
            api.setFirstOrderId(0);
            api.shipMethodMap = new TreeMap<String, List<String>>();

             api.setOtherFulfillmentSku("SEPARATE");

            api.importCurrentOrders() ;



        } catch (Exception ex) {

            throw new LogableException(ex.getMessage(),
                    "",
                    "494",
                    this.getClass().getCanonicalName(),
                    LogableException.errorTypes.UNDEFINED_ERROR);

        } finally {


            HibernateSession.closeSession();
        }

    }


    public void updateInventoryLevels() throws Exception {


    }

}
