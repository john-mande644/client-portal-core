package com.owd.jobs.archives;

import com.owd.LogableException;
import com.owd.core.business.order.Order;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import com.owd.jobs.jobobjects.woocommerce.WooCommerceAPI;
import com.owd.jobs.jobobjects.woocommerce.WooCommerceWordPressAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Nov 17, 2005
 * Time: 11:36:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class WeAreHandsomeWooOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

        run();

    }



     public void internalExecute() {

             try {

                 WooCommerceWordPressAPI api = new WooCommerceWordPressAPI(600, "https://wearehandsome.com/", "ck_fdd87e9d3eb125fdc21f1f35e7921f83529bcf71", "cs_b52ddcce0ddcab0a921c323418dc170eb6f7b229","1",true){
                     @Override
                     public  void assignShipMethod(Order order, String shipMethod){
                         System.out.println("In handsome shipMethod: " + shipMethod );
                         boolean isBestWay = false;
                         if(shipMethod.equals("Free Shipping")){
                             if(order.getShippingAddress().country.equals("USA")){
                                 shipMethod = "FedEx SmartPost Standard Mail";
                             }else{
                                 shipMethod = "USPS First-Class Mail International";
                             }
                         }else if (shipMethod.toUpperCase().contains("INTERNATIONAL") || shipMethod.toUpperCase().contains("AUSTRALIAN".toUpperCase())||order.getShippingInfo().shipAddress.country.equalsIgnoreCase("AUSTRALIA")) {
                            if(shipMethod.contains("USPS")){
                                shipMethod = "USPS First-Class Mail International";
                            }else {
                                shipMethod = "DHL Express Worldwide nondoc";
                            }
                         } else if ((shipMethod.toUpperCase().contains("SMART POST"))) {
                             shipMethod = "FedEx SmartPost Standard Mail";
                         } else if(order.getShippingAddress().country.equals("USA")){
                             if(shipMethod.contains("USPS")){
                                 shipMethod = "USPS First-Class Mail";
                             }else {
                                 shipMethod = "FedEx SmartPost Standard Mail";
                             }
                         }else{
                             if(shipMethod.contains("USPS")){
                                 shipMethod = "USPS First-Class Mail International";
                             }else {
                                 shipMethod = "DHL Express Worldwide nondoc";
                             }
                         }


                         try {
                             if (!(order.orderRefnumExists(order.order_refnum))) {
                                 if (isBestWay) {
                                     List<String> alternateShipMethodList = new ArrayList<String>();
                                     alternateShipMethodList.add("TANDATA_USPS.USPS.FIRST");
                                     alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                                     alternateShipMethodList.add("TANDATA_USPS.USPS.I_FIRST");
                                     alternateShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
                                     alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                                     alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");
                                     alternateShipMethodList.add("CONNECTSHIP_DHL.DHL.WPX");

                                     shipMethod = RateShopper.rateShop(order, alternateShipMethodList);
                                 }
                                 order.getShippingInfo().setShipOptions(shipMethod, "Prepaid", "");

                             } else {
                                 order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
                             }
                         }catch(Exception e){
                             try{
                                 order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
                             } catch(Exception exx){

                             }
                         }


                     }
                     @Override
                    public void doFinalStuffBeforeSavingOrder(Order order) throws Exception{
                         order.addInsertItemIfAvailable("WAHBOXB1",1);
                     }
                 };

                 log.debug(api.importOrders());

             }catch (Exception ex)
             {
                 try {
                     throw new LogableException(ex, ex.getMessage(),
                             "TS:"+Calendar.getInstance().getTimeInMillis(),
                             "600",
                             this.getClass().getName(),
                             LogableException.errorTypes.ORDER_IMPORT);
                 } catch (Exception mailex) {
                     mailex.printStackTrace();
                 }
             }


    }
}
