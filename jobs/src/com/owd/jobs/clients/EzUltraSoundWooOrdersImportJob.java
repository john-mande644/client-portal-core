package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.core.business.order.Order;
import com.owd.core.xml.OrderXMLDoc;
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
public class EzUltraSoundWooOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

        run();

    }



     public void internalExecute() {

             try {

                 WooCommerceAPI api = new WooCommerceAPI(432, "https://ezultrasound.com/", "ck_2b620107b2cbe9d174e73719fcf6505237adcc71", "cs_5682bf087095a13cb85b0def6ed108d70e8bcbdb","3",false){
                     @Override
                     public  void assignShipMethod(Order order, String shipMethod){

                         boolean isBestWay = false;
                         if(shipMethod.equals("Free Shipping")){
                             if(order.getShippingAddress().country.equals("USA")){
                                 shipMethod = "Ground";
                             }else{
                                 shipMethod = "International Standard";
                             }
                         }else if (shipMethod.toUpperCase().contains("EXPRESS") || shipMethod.toUpperCase().contains("EXPEDITED")) {
                             if(order.getShippingAddress().country.equals("USA")){
                                 shipMethod = "2 Day";
                             }else{
                                 shipMethod = "International Expedited";
                             }
                         }else{
                             if(order.getShippingAddress().country.equals("USA")){
                                 shipMethod = "Ground";
                             }else{
                                 shipMethod = "International Standard";
                             }
                         }



                        try {
                            order.getShippingInfo().setShipOptions(shipMethod, "Prepaid", "");
                        }catch (Exception e){

                        }


                     }
                     @Override
                     public void doFinalStuffBeforeSavingOrder(Order anOrder)  throws Exception
                     {

                         if (anOrder.getShippingAddress().isInternational()) {

                             anOrder.backorder_rule = OrderXMLDoc.kBackOrderAll;
                         }
                         if (!anOrder.containsSKU("returnslip")) {
                             anOrder.addInsertItemIfAvailable("returnslip", 1);
                         }
                         if (anOrder.containsSKU("ZZA1000")) {

                             anOrder.addLineItem("ChinaSample", ""+anOrder.getQuantityForSKU("ZZA1000"),"0.00","0.00","","","");

                         }
                         if (anOrder.containsSKU("US1000")) {

                           //  anOrder.addLineItem("Sombra4", ""+anOrder.getQuantityForSKU("US1000"),"0.00","0.00","","","");

                         }

                         if (anOrder.containsSKU("UCPro")) {

                           //  anOrder.addLineItem("Sombra4", ""+anOrder.getQuantityForSKU("UCPro"),"0.00","0.00","","","");

                         }

                         if (anOrder.containsSKU("UCPro") || anOrder.containsSKU("US1000") || anOrder.containsSKU("Sombra4") || anOrder.containsSKU("Sombra8")) {

                           //  anOrder.addInsertItemIfAvailable("Sombra-Brochure",1);

                         }

                         if (anOrder.containsSKU("UCPro") || anOrder.containsSKU("US1000")) {

                             anOrder.addInsertItemIfAvailable("User-Reference-Guide", 1);

                         }



                     }

                 };

                 log.debug(api.importOrdersPeaceHillPress());

             }catch (Exception ex)
             {
                 try {
                     throw new LogableException(ex, ex.getMessage(),
                             "TS:"+Calendar.getInstance().getTimeInMillis(),
                             "432",
                             this.getClass().getName(),
                             LogableException.errorTypes.ORDER_IMPORT);
                 } catch (Exception mailex) {
                     mailex.printStackTrace();
                 }
             }


    }
}
