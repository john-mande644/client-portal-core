package com.owd.jobs.clients;

import com.owd.core.csXml.OrderRater;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.LogableException;
import com.owd.core.*;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.batchimporters.RIEImportData;
import com.owd.jobs.jobobjects.woocommerce.WooCommerceAPI;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Nov 17, 2005
 * Time: 11:36:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class RIEOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

        run();

    }



     public void internalExecute() {

             try {

                 WooCommerceAPI api = new WooCommerceAPI(378, "https://www.rie.org/", "ck_cdec9a83766179c263bfd83ad1215038", "cs_f5ce034019301f2d8266776e9e5a6d8c","2"){
                     @Override
                     public void assignShipMethod(Order order, String shipmethod) {

                         try{
                             OrderRater rater = new OrderRater(order);
                             rater.setCalculateKitWeights(true);
                             List domStandardMethods;

                             List<String> alternateShipMethodList = new ArrayList<String>();
                             // alternateShipMethodList.add("TANDATA_USPS.USPS.SPCL");
                             alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                             // alternateShipMethodList.add("TANDATA_USPS.USPS.I_FIRST");
                             alternateShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
                             //  alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.STD");
                             alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.GND");
                             //   alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                             //   alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");
                             List<String> currMethods = new ArrayList<String>();

                             String moShipTranslated = null;
                             shipmethod = shipmethod.toUpperCase();
                             currMethods = alternateShipMethodList;

                             if(shipmethod.equals("UPS"))
                             {
                                 moShipTranslated = "UPS Ground";
                             }
                             if (shipmethod.contains("UPS ") || shipmethod.contains("UPS)") || shipmethod.contains("UNITED PARCEL SERVICE")) {
                                 //ups
                                 if (shipmethod.indexOf("NEXT DAY") >= 0) {
                                     moShipTranslated = "UPS Next Day Air Saver";
                                 } else if ((shipmethod.indexOf("GROUND") >= 0)) {
                                     moShipTranslated = "UPS Ground";
                                 } else if (shipmethod.indexOf("STANDARD") >= 0) {
                                     moShipTranslated = "UPS Standard Canada";
                                 } else if (shipmethod.indexOf("2ND DAY") >= 0 || shipmethod.contains("2 DAY")) {
                                     moShipTranslated = "UPS 2nd Day Air";
                                 } else if (shipmethod.indexOf("3 DAY") >= 0) {
                                     moShipTranslated = "UPS 3 Day Select";
                                 } else if (shipmethod.indexOf("WORLDWIDE EXPRESS PLUS") >= 0) {
                                     moShipTranslated = "UPS Worldwide Express Plus";
                                 } else if (shipmethod.indexOf("WORLDWIDE EXPRESS") >= 0) {
                                     moShipTranslated = "UPS Worldwide Express";
                                 }    else if (shipmethod.indexOf("WORLDWIDE SAVER") >= 0) {
                                     moShipTranslated = "UPS Worldwide Express Saver";
                                 }
                             }

                             if (shipmethod.startsWith("FDX") || shipmethod.startsWith("FEDEX")) {
                                 //          USPS GLOBAL EXPRESS - WEIGHT
                                 //todo
                             } else {
                                 //usps
                                 if ((shipmethod.indexOf("EXPRESS") >= 0 ) && order.getShippingAddress().isInternational()) {
                                     moShipTranslated = "USPS Priority Mail Express International";
                                 } else if (shipmethod.indexOf("EXPRESS") >= 0) {
                                     moShipTranslated = "USPS Priority Mail Express";              //
                                 } else if (shipmethod.indexOf("CLASS MAIL INT") >= 0 &&  order.getShippingAddress().isInternational()) {
                                     moShipTranslated = "USPS First-Class Mail International";
                                 } else if (shipmethod.indexOf("FIRST-CLASS") >= 0 || shipmethod.indexOf("FIRST CLASS") >= 0) {
                                     moShipTranslated = "USPS First-Class Mail";
                                 } else if (shipmethod.indexOf("PRIORITY") >= 0 && order.getShippingAddress().isInternational() ) {
                                     moShipTranslated = "USPS Priority Mail International";
                                 } else if (shipmethod.indexOf("PRIORITY") >= 0) {
                                     moShipTranslated = "USPS Priority Mail";
                                 } else if (shipmethod.indexOf("MEDIA") >= 0) {
                                     moShipTranslated = "USPS Media Mail Single-Piece";
                                 } else if (shipmethod.indexOf("PARCEL") >= 0) {
                                     moShipTranslated = "USPS Parcel Select Nonpresort";
                                 }   else if (shipmethod.indexOf("FREE") >= 0 || shipmethod.contains("SHIPPING") ) {
                                     currMethods = alternateShipMethodList;
                                 }
                             }

                             log.debug("moShiptranslated="+moShipTranslated);
                             if (moShipTranslated != null) {
                                 currMethods = new ArrayList<String>();
                                 currMethods.add(moShipTranslated);
                             }

                             if(!(OrderUtilities.orderRefnumExists(order.order_refnum, order.clientID))) {
                                 if (currMethods.size() > 0) {
                                     if (currMethods.size() == 1) {
                                         log.debug("returning " + currMethods.get(0));
                                         moShipTranslated = currMethods.get(0);

                                     } else {
                                         //  log.debug("returning "+RateShopper.rateShop(order, currMethods));
                                         moShipTranslated = RateShopper.rateShop(order, currMethods);
                                     }
                                 } else {

                                     moShipTranslated ="USPS Priority Mail";
                                 }
                                 order.getShippingInfo().setShipOptions(moShipTranslated, "Prepaid", "");

                             }

                         }catch (Exception ex)
                         {
                             try{
                                 order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
                             } catch(Exception exx){

                             }
                         }


                     }
                 };

                 //  println Integer.parseInt("W12345".substring(1))
                 //     api.markOrderShipped(4525)
                 //  println api.listProductSkus()
                 log.debug(api.importOrders());

             }catch (Exception ex)
             {
                 try {
                     throw new LogableException(ex, ex.getMessage(),
                             "TS:"+Calendar.getInstance().getTimeInMillis(),
                             "378",
                             this.getClass().getName(),
                             LogableException.errorTypes.ORDER_IMPORT);
                 } catch (Exception mailex) {
                     mailex.printStackTrace();
                 }
             }


    }
}
