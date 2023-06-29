package com.owd.core.business.client;

import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Feb 7, 2006
 * Time: 11:06:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class AlgaeCalClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

    @Override
    public void saveNewOrder(Order owdOrder, boolean testing) throws Exception {

        if (owdOrder.containsSKU("AlgaeCal Plus") || owdOrder.containsSKU("8-Pack AlgaeCal Plus") || owdOrder.containsSKU("Case of AlgaeCal 24 Btls"))
        {
            if (!owdOrder.containsSKU("Brochures-AlgaeCal"))
            {
                owdOrder.addInsertItemIfAvailable("Brochures-AlgaeCal", 1);
            }
        }
        if (owdOrder.containsSKU("The Kitchen Sink"))
        {     if (!owdOrder.containsSKU("Brochure - TKS"))
        {
            owdOrder.addInsertItemIfAvailable("Brochure - TKS", 1);
        }
        }
        if (owdOrder.containsSKU("CC147g") || owdOrder.containsSKU("CC120"))
        {     if (!owdOrder.containsSKU("Brochures-TB"))
        {
            owdOrder.addInsertItemIfAvailable("Brochure-TB", 1);
        }
        }
        // case 1002213 comment out rule
        /*if (owdOrder.containsSKU("Strontium") || owdOrder.containsSKU("Case of Strontium 24 Btls"))
        {
            if (!owdOrder.containsSKU("Brochures-AlgaeCal"))
            {
                owdOrder.addInsertItemIfAvailable("Brochures-AlgaeCal", 1);
            }
        }*/



        super.saveNewOrder(owdOrder, testing);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
