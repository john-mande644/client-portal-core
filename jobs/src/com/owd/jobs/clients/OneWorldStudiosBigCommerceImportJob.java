package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import com.owd.jobs.jobobjects.bigcommerce.BigCommerceAPI;

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
public class OneWorldStudiosBigCommerceImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args) {

        // CasetrackerAPI.createCasetrackerCase("Client ID (55) order tester received on hold3", "Unable to determine ship method for this order; address or items invalid for available ship method options - " +
        //         "attempt to correct address and assign to IT Work Orders if needed.", 5555);

        run();
        //   api.reportShipment(5156008, "9156901074187000034863", true);

    }


    public void internalExecute() {

        try {


            BigCommerceAPI api = new BigCommerceAPI("admin","52e9c1961343ce275d1d4ac6be86b58c43de7ded","https://www.choppertown.com/api/v2/","BC"){

                @Override
                public String getActualShipMethod(Order order, String shipmethod)throws Exception {

                  //  order.order_refnum="BC"+order.order_refnum;

                    log.debug("BC import: original ship method : "+shipmethod);
                    if(shipmethod.equals("USPS (First Class Package International Service)")){
                        return "USPS First-Class Mail International";
                    }

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
                        if(order.getShippingAddress().isUS()) {
                            return "USPS Priority Mail";
                        }else{
                            return "USPS First-Class Mail International";
                        }
                    }
                    log.debug("returning "+shipmethod);

                    return shipmethod;

                }

                @Override
                public boolean addLineItem(Order order, String sku, String qty, String price, String linePrice, String title, String color, String size) throws Exception {

                    try{

                        if(sku.toUpperCase().equals("BT1000-3XL"))
                        {
                            sku = "BT1000xxxl";
                        }
                        if(sku.toUpperCase().equals("B1000BL-3XL"))
                        {
                            sku = "B1000BLxxxl";
                        }
                    if(sku.equals("FFDVD1"))
                    {
                        sku = "FFDVD";
                    }

                    log.debug(order.getShippingAddress().getCountry().toUpperCase());

                          return  super.addLineItem(order, sku, qty, price, linePrice, title, color, size);
                }catch(Exception ex)
                {

                  //  Mailer.sendMail("BigCommerce API order import failed for One World Studios", "Unable to recognize SKU "+sku+" for BC order "+order.order_refnum, "owditadmin@owd.com", "donotreply@owd.com");

                    throw ex;
                }
                }

                @Override
                public void doFinalStuffBeforeSavingOrder(Order order) throws Exception {

                      if(!order.containsSKU("Postcard-BT1")) {
                          order.addInsertItemIfAvailable("Postcard-BT1",1);
                      }

                }
            };

            api.setClientInfo(382, null);
            api.setFirstOrderId(33100);
            api.shipMethodMap = new TreeMap<String, List<String>>();
            api.setIgnoreSkuError(true);
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
