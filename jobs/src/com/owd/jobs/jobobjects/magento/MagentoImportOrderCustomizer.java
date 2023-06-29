package com.owd.jobs.jobobjects.magento;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.Order;
import com.owd.jobs.jobobjects.magento.owd.MagentoRemoteService;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 4/3/11
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class MagentoImportOrderCustomizer {
private final static Logger log =  LogManager.getLogger();



public void customizeOrder(MagentoRemoteService service, Order order, MagentoRemoteService.MagentoOrder mo) throws Exception {


}

public void handleLineItemWithOptions(MagentoRemoteService service, Order order, MagentoRemoteService.MagentoOrder mo, ClientPolicy policy, String realSku, Map item) throws Exception
{

    //default behavior is to ignore options
    log.debug("IGNORING OPTIONS EVEN THOUGH OPTIONS WERE FOUND");
    policy.addLineItemToOrder(order, realSku, "", (String) item.get("price"), ((String) item.get("qty_ordered")).substring(0, ((String) item.get("qty_ordered")).indexOf(".")));


}


}
