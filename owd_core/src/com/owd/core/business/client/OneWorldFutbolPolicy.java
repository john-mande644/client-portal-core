package com.owd.core.business.client;

import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Jul 20, 2010
 * Time: 2:26:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class OneWorldFutbolPolicy extends DefaultClientPolicy
{
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args)
    {
        try{
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    @Override
    public void saveNewOrder(Order order, boolean testing) throws Exception
    {
        
        if(!order.containsSKU("insert"))
        {
            order.addInsertItemIfAvailable("insert",1);
        }

        if(!order.containsSKU("insert 2"))
        {
            order.addInsertItemIfAvailable("insert 2",1);
        }

        super.saveNewOrder(order, testing);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
