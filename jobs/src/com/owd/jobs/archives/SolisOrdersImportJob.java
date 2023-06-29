package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.woocommerce.WooCommerceAPI;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Nov 17, 2005
 * Time: 11:36:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class SolisOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

        run();

    }



     public void internalExecute() {

             try {

                 WooCommerceAPI api = new WooCommerceAPI(530, "https://www.solisnutritionalblends.com/", "ck_689e749436cde23d431b91e6b5866c3e", "cs_31b8f5e4027527a6ed2059b590001c15","2");


                 log.debug(api.importOrders());

             }catch (Exception ex)
             {
                 try {
                     throw new LogableException(ex, ex.getMessage(),
                             "TS:"+Calendar.getInstance().getTimeInMillis(),
                             "530",
                             this.getClass().getName(),
                             LogableException.errorTypes.ORDER_IMPORT);
                 } catch (Exception mailex) {
                     mailex.printStackTrace();
                 }
             }


    }
}
