package com.owd.jobs.clients;

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
public class FreelyYourPartnerInCareOrderImportJob extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args){

     //   BigCommerceAPI api = new BigCommerceAPI("OWD","86e1eefde2b027c1e2e283f645357615f157abbe","https://store-ryvwuu0w08.mybigcommerce.com/api/v2/","YPIC");
       // api.syncInventory("603");


     /*   USER: OWD

        API PATH: https://store-ryvwuu0w08.mybigcommerce.com/api/v2/

        API TOKEN: 86e1eefde2b027c1e2e283f645357615f157abbe*/
    run();

}



    public void internalExecute() {

        try {


            BigCommerceAPI api = new BigCommerceAPI("OWD","86e1eefde2b027c1e2e283f645357615f157abbe","https://store-ryvwuu0w08.mybigcommerce.com/api/v2/","YPIC"){

                @Override
                public String getActualShipMethod(Order order, String shipmethod)throws Exception {

                    //  order.order_refnum="BC"+order.order_refnum;

                    log.debug("BC import: original ship method : "+shipmethod);

                   if(shipmethod.equals("Free Shipping")){
                       shipmethod = "FedEx SmartPost Parcel Select";
                   }

                    return shipmethod;

                }

                @Override
                public boolean addLineItem(Order order, String sku, String qty, String price, String linePrice, String title, String color, String size) throws Exception {

                    try{

                        if(sku.equals("YPIC-DIABETES-BOXB")){
                            sku = "YPIC-DIABETES-BOX-B";
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



                }
            };

            api.getEndpoint().ignoreSSLIssues();
            api.setClientInfo(603, null);
             api.setFirstOrderId(109);
            api.shipMethodMap = new TreeMap<String, List<String>>();

            //   api.setOtherFulfillmentSku("SEPARATE");

            api.importCurrentOrders() ;


            api.syncInventory(api.clientId+"");
        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {


            HibernateSession.closeSession();
        }

    }
}
