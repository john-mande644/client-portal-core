package com.owd.jobs.archives;

import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.bigcommerce.BigCommerceAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by danny on 3/13/2017.
 */
public class EightOunceAcmeBigCommerceOrdersImportJob extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args){

      // BigCommerceAPI api = new BigCommerceAPI("OneWorldDirect","42dc3a6d4c9fef0c976df9128de1fd347a6586f0","https://store-jydkvnsb31.mybigcommerce.com/api/v2/","BC");
       // api.syncInventory("471");

       /*  Username (Required)

        OneWorldDirect
        API Path

        https://store-rzmegynle7.mybigcommerce.com/api/v2/
        Additional information
        API Token

        1d82866b8d43ffd45e824754fa7f428cb9804634*/
    run();

}



    public void internalExecute() {

        try {


            BigCommerceAPI api = new BigCommerceAPI("OneWorldDirect","1d82866b8d43ffd45e824754fa7f428cb9804634","https://store-rzmegynle7.mybigcommerce.com/api/v2/","BC"){

                @Override
                public String getActualShipMethod(Order order, String shipmethod)throws Exception {

                    //  order.order_refnum="BC"+order.order_refnum;

                    log.debug("BC import: original ship method : "+shipmethod);

                    OrderRater rater = new OrderRater(order);
                    rater.setCalculateKitWeights(true);
                    List domStandardMethods;

                    domStandardMethods = Arrays.asList("TANDATA_USPS.USPS.FIRST", "TANDATA_USPS.USPS.PRIORITY", "TANDATA_USPS.USPS.I_FIRST", "TANDATA_USPS.USPS.I_PRIORITY");

                    List<String> currMethods = new ArrayList<String>();

                    String moShipTranslated = null;
                    shipmethod = shipmethod.toUpperCase();

                    if (shipmethod.contains("UPS ") || shipmethod.contains("UNITED PARCEL SERVICE")) {
                        //ups
                        if (shipmethod.indexOf("NEXT DAY") >= 0) {
                            if(shipmethod.contains("Saver")){
                                moShipTranslated = "UPS Next Day Air Saver";
                            }else if(shipmethod.contains("A.M.") || shipmethod.contains("am")){
                                moShipTranslated = "UPS Next Day Air A.M.";
                            }else{
                                moShipTranslated = "UPS Next Day Air";
                            }


                        } else if ((shipmethod.indexOf("GROUND") >= 0) && (shipmethod.indexOf("FREE")>=0)) {
                            moShipTranslated = "UPS Ground";
                        } else if (shipmethod.indexOf("GROUND") >= 0) {
                            moShipTranslated = "UPS Ground";
                        } else if (shipmethod.indexOf("STANDARD") >= 0) {
                            moShipTranslated = "UPS Standard Canada";
                        } else if (shipmethod.indexOf("2ND DAY") >= 0 || shipmethod.contains("2 DAY")) {
                            if(shipmethod.contains("A.M.") || shipmethod.contains("am")){
                                moShipTranslated = "UPS 2nd Day Air A.M.";
                            }else {
                                moShipTranslated = "UPS 2nd Day Air";
                            }
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
                        if(shipmethod.contains("HOME")){
                            moShipTranslated = "FedEx Home Delivery";
                        }
                        if(shipmethod.toUpperCase().contains("2 DAY")||shipmethod.contains("2DAY")){
                            moShipTranslated = "FedEx 2Day";
                        }
                        if(shipmethod.toUpperCase().contains("EXPRESS SAVER")){
                            moShipTranslated = "FedEx Express Saver";
                        }
                        if(shipmethod.toUpperCase().contains("GROUND")){
                            moShipTranslated = "FedEx Ground";
                        }

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



                }
            };

            api.setClientInfo(584, null);
           //  api.setFirstOrderId(16123);
            api.setCombineOptionsForSku(false);
            api.shipMethodMap = new TreeMap<String, List<String>>();

            //   api.setOtherFulfillmentSku("SEPARATE");

            api.importCurrentOrders() ;

            api.syncInventory("584");



        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {


            HibernateSession.closeSession();
        }

    }
}
