package com.owd.jobs.archives;

import com.owd.core.business.order.Order;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.retailops.RetailOpsApi;

/**
 * Created by stewartbuskirk1 on 5/27/14.
 */
public class DotAndBoRetailOpsOrderImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args)
    {
        run();
    }


    @Override
    public void internalExecute() {
        RetailOpsApi api = new RetailOpsApi(525, "1", "5AzsAv46-q8P5opRPSugcg0dKKRNDzRWQ2BpUO1Mx");

        Order order = new Order("525");
        if(!(order.withinDateRange("2015-12-23","2016-1-3"))) {
            api.importCurrentOrders();
        }
       // api.getAsns(false);
        api.reportReceipts();
        api.updateInventory();

    }
}
