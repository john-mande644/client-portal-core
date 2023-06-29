package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import com.owd.jobs.jobobjects.bigcommerce.BigCommerceAPI;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/1/12
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class OneWorldStudiosJesperImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    static List<String> badCountryList = new ArrayList<String>();

    static {
      /*  badCountryList.add("AUSTRIA");
        badCountryList.add("BELGIUM");
        badCountryList.add("BULGARIA");
        badCountryList.add("CYPRUS");
        badCountryList.add("CZECH REPUBLIC");
        badCountryList.add("DENMARK");
        badCountryList.add("ESTONIA");
        badCountryList.add("FINLAND");
        badCountryList.add("FRANCE");
        badCountryList.add("GERMANY");
        badCountryList.add("GREECE");
        badCountryList.add("HUNGARY");
        badCountryList.add("IRELAND");
        badCountryList.add("ITALY");
        badCountryList.add("LATVIA");
        badCountryList.add("LITHUANIA");
        badCountryList.add("LUXEMBOURG");
        badCountryList.add("MALTA");
        badCountryList.add("NETHERLANDS");
        badCountryList.add("NORWAY");
        badCountryList.add("POLAND");
        badCountryList.add("PORTUGAL");
        badCountryList.add("ROMANIA");
        badCountryList.add("SLOVAKIA");
        badCountryList.add("SLOVENIA");
        badCountryList.add("SPAIN");
        badCountryList.add("SWEDEN");
        badCountryList.add("SWITZERLAND");
        badCountryList.add("UNITED KINGDOM");*/
    }
    public static void main(String[] args) {

        // CasetrackerAPI.createCasetrackerCase("Client ID (55) order tester received on hold3", "Unable to determine ship method for this order; address or items invalid for available ship method options - " +
        //         "attempt to correct address and assign to IT Work Orders if needed.", 5555);

        run();

        //   api.reportShipment(5156008, "9156901074187000034863", true);

    }


    public void internalExecute() {

        try {


            BigCommerceAPI api = new BigCommerceAPI("OneWorldDirect","3e7c87626e46898a10c93cb9c96d5020fc51cb05","https://store-d8r4sih.mybigcommerce.com/api/v2/","BCJ"){
                @Override
                public String getActualShipMethod(Order order, String shipmethod)throws Exception {

                   // order.order_refnum="BCJ"+order.order_refnum;

                    log.debug("BCJ import: original ship method : "+shipmethod);

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
                        }   else {
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
                        return order.getShippingAddress().isInternational()?"USPS First-Class Mail International":"USPS Priority Mail";
                    }
                    log.debug("returning "+shipmethod);

                    return shipmethod;

                }


                @Override
                public boolean addLineItem(Order order, String sku, String qty, String price, String linePrice, String title, String color, String size) throws Exception {

                    if(sku.equals("FFDVD1"))
                    {
                        sku = "FFDVD";
                    }
                    log.debug(order.getShippingAddress().getCountry().toUpperCase());
                    if(!(badCountryList.contains(order.getShippingAddress().getCountry().toUpperCase())))
                    {
                      if(LineItem.SKUExists(order.clientID, sku))
                      {
                         return  super.addLineItem(order, sku, qty, price, linePrice, title, color, size);
                      }  else
                      {
                              sku = getOtherFulfillmentSku()            ;
                              title = title+" / DELIVERED SEPARATELY"  ;
                          return  super.addLineItem(order, sku, qty, price, linePrice, title, color, size);

                      }
                    }

                    return false;
                }

                @Override
                public void doFinalStuffBeforeSavingOrder(Order order) throws Exception {



                    order.prt_pack_reqd=0;
                    order.prt_pick_reqd=1;

                }
            };

            api.setClientInfo(382, null);
            api.setFirstOrderId(0);
            api.shipMethodMap = new TreeMap<String, List<String>>();
          //  api.shipMethodMap.put("USPS Priority Mail (2_3 business days)".toUpperCase(),Arrays.asList("USPS Priority Mail"));
          //  api.shipMethodMap.put("USPS Priority Mail Express (1_2 business days)".toUpperCase(), Arrays.asList("USPS Priority Mail Express"));

             api.setOtherFulfillmentSku("SEPARATE");
            //  api.setFulfillmentServiceCode("stewart");

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
