package com.owd.jobs.archives;

import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.bigcommerce.BigCommerceAPI;
import com.owd.jobs.jobobjects.clients.BigSexyLingerieOrderRules;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
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
public class SoSexyBigCommerceImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args) {

        // CasetrackerAPI.createCasetrackerCase("Client ID (55) order tester received on hold3", "Unable to determine ship method for this order; address or items invalid for available ship method options - " +
        //         "attempt to correct address and assign to IT Work Orders if needed.", 5555);

        run();
        //   api.reportShipment(5156008, "9156901074187000034863", true);

    }


    public void internalExecute() {

        try {


            BigCommerceAPI api = new BigCommerceAPI("owd","a3ab0ad10bdfc477c33f06933dedd201b2900006","https://www.sosexylingerie.com/api/v2/","BC"){

                @Override
                public String getActualShipMethod(Order order, String shipmethod)throws Exception {

                  //  order.order_refnum="BC"+order.order_refnum;

                    log.debug("BC import: original ship method : "+shipmethod);

                    OrderRater rater = new OrderRater(order);
                    rater.setCalculateKitWeights(true);
                    List domStandardMethods;

                    domStandardMethods = Arrays.asList("TANDATA_USPS.USPS.FIRST", "TANDATA_USPS.USPS.PRIORITY","TANDATA_USPS.USPS.I_FIRST", "TANDATA_USPS.USPS.I_PRIORITY");

                    List<String> currMethods = new ArrayList<String>();

                    String moShipTranslated = null;
                    shipmethod = shipmethod.toUpperCase();

                    if (shipmethod.contains("UPS ") || shipmethod.contains("UNITED PARCEL SERVICE")) {
                        //ups
                        if (shipmethod.indexOf("NEXT DAY") >= 0) {
                            moShipTranslated = "UPS Next Day Air Saver";
                        } else if ((shipmethod.indexOf("GROUND") >= 0) && (shipmethod.indexOf("FREE")>=0)) {
                            moShipTranslated = "UPS Ground";
                        } else if (shipmethod.indexOf("GROUND") >= 0) {
                            moShipTranslated = "UPS Ground";
                        } else if (shipmethod.indexOf("STANDARD") >= 0) {
                            moShipTranslated = "UPS Standard Canada";
                        } else if (shipmethod.indexOf("2ND DAY") >= 0 || shipmethod.contains("2 DAY")) {
                            moShipTranslated = "UPS 2nd Day Air";
                        } else if (shipmethod.indexOf("3 DAY") >= 0) {
                            moShipTranslated = "UPS 3 Day Select";
                        } else if (shipmethod.indexOf("WORLDWIDE EXPRESS SAVER") >= 0) {
                            moShipTranslated = "UPS Worldwide Express Saver";
                        } else if (shipmethod.indexOf("WORLDWIDE EXPRESS EXPEDITED") >= 0) {
                            moShipTranslated = "UPS Worldwide Expedited";
                        }
                    }

                    if (shipmethod.startsWith("FDX") || shipmethod.startsWith("FEDEX")) {
                        //          USPS GLOBAL EXPRESS - WEIGHT
                        //todo
                    } else {
                        //usps
                        if ((shipmethod.indexOf("GLOBAL EXPRESS") >= 0 || shipmethod.indexOf("EXPRESS MAIL") >= 0) && order.getShippingAddress().isInternational()) {
                            moShipTranslated = "USPS Priority Mail Express International";
                        } else if (shipmethod.indexOf("EXPRESS MAIL") >= 0) {
                            moShipTranslated = "USPS Priority Mail Express";              //
                        } else if (shipmethod.indexOf("CLASS MAIL INT") >= 0 &&  order.getShippingAddress().isInternational()) {
                            moShipTranslated = "USPS First-Class Mail International";
                        } else if (shipmethod.indexOf("FIRST-CLASS") >= 0 || shipmethod.indexOf("FIRST CLASS") >= 0) {
                            moShipTranslated = "USPS First-Class Mail";
                        } else if (shipmethod.indexOf("PRIORITY MAIL") >= 0 && order.getShippingAddress().isInternational() ) {
                            moShipTranslated = "USPS Priority Mail International";
                        } else if (shipmethod.indexOf("PRIORITY MAIL") >= 0) {
                            moShipTranslated = "USPS Priority Mail";
                        } else if (shipmethod.indexOf("MEDIA MAIL") >= 0) {
                            moShipTranslated = "USPS Media Mail Single-Piece";
                        } else if (shipmethod.indexOf("PARCEL POST") >= 0) {
                            moShipTranslated = "USPS Parcel Select Nonpresort";
                        }   else if (shipmethod.indexOf("FREE") >= 0 || shipmethod.indexOf("FIXED SHIPPING") >= 0 ) {
                            currMethods = domStandardMethods;
                        }
                    }

                    log.debug("moShiptranslated="+moShipTranslated);
                    if (moShipTranslated != null) {
                        currMethods = new ArrayList<String>();
                        currMethods.add(moShipTranslated);
                    }
                    if(!(OrderUtilities.orderRefnumExists(order.order_refnum, order.clientID)))
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

                }

                @Override
                public boolean addLineItem(Order order, String sku, String qty, String price, String linePrice, String title, String color, String size) throws Exception {

                    try{

                          return  super.addLineItem(order, sku, qty, price, linePrice, title, color, size);
                }catch(Exception ex)
                {


                    throw ex;
                }
                }

                @Override
                public void doFinalStuffBeforeSavingOrder(Order order) throws Exception {

                    BigSexyLingerieOrderRules.apply(order);

                }
            };

            api.setClientInfo(581, null);
            api.setFirstOrderId(336);
            api.shipMethodMap = new TreeMap<String, List<String>>();

             api.setOtherFulfillmentSku("SEPARATE");

            api.importCurrentOrders() ;



        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {


            HibernateSession.closeSession();
        }

    }


    public void updateInventoryLevels() throws Exception {


    }

}
