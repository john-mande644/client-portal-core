package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.WebResource;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.LogableException;

import java.io.BufferedReader;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class LifespanDreamingCodeOrderImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception {

        run();


    }
    


    public void internalExecute() {




            try {

                WebResource server = new WebResource("https://commerce.dreamingcode.com/system/modules-ecomm/orders/export/custom_api/OWD/417-index.php", WebResource.kGETMethod);

                BufferedReader response = server.getResource();

                if(response == null)  //ignore connection errors
                {
                    throw new Exception("Unable to get response from lifespan server!");
                }

                StringBuffer sb = new StringBuffer();
                int line = 0;
                line = (int) response.read();
                while (line != -1) {
                    sb.append((char)line);
                    line = response.read();
                }
                if(sb.toString().length()>4 && (!(sb.toString().contains("No orders to export"))) && (sb.toString().contains("[ ERROR ]") || (!(sb.toString().contains("SENT"))))){
                    log.debug("RESPONSE:"+sb.toString());

                    LogableException l =    new LogableException(sb.toString(),
                            "TS:"+ Calendar.getInstance().getTimeInMillis(),
                            "320",
                            this.getClass().getName(),
                            LogableException.errorTypes.ORDER_IMPORT);}


            /* if(1==1){
                 throw new Exception("just testing");
             }*/


            } catch (Exception ex) {
                ex.printStackTrace();
                LogableException l =   new LogableException(ex, ex.getMessage(),
                        "TS:"+Calendar.getInstance().getTimeInMillis(),
                        "320",
                        this.getClass().getName(),
                        LogableException.errorTypes.ORDER_IMPORT);

            } finally {
                HibernateSession.closeSession();
            }

        }



    /*public class LifeSpanImportOrderCustomizer extends MagentoImportOrderCustomizer
    {


        @Override
        public void customizeOrder(MagentoRemoteService service, Order order, MagentoRemoteService.MagentoOrder mOrder) throws Exception {




        Map<Integer, String> groupIdNameMap =  service.getCustomerApi().getAllCustomerGroupsAsIdNameMap();

            if("0".equals(mOrder.order.get("customer_is_guest")))
            {
                int customer_group_id = Integer.parseInt(mOrder.order.get("customer_group_id"));
                order.getShippingInfo().comments = groupIdNameMap.get(customer_group_id).toUpperCase().trim();
                order.getShippingInfo().whse_notes = (mOrder.order.get("customer_firstname")+" "+mOrder.order.get("customer_lastname")).toUpperCase().trim();

            }

            if(order.getShippingInfo().comments.toUpperCase().contains("WHOLESALE"))
            {
                order.prt_pack_reqd=0;
                order.prt_pick_reqd=1;

            }



        }

    }*/


}
