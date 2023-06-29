package com.owd.jobs.jobobjects.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Nov 6, 2007
 * Time: 11:56:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class DacorStoreUploadData extends OWDUploadOrdersData_1{
private final static Logger log =  LogManager.getLogger();
     public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice,String desc, String color, String size) throws Exception
    {
         //log.debug("adding li:"+sku+","+qty+","+unitPrice+","+
         //           linePrice+","+
         //           desc+","+
         //           color+","+ size);

        if(new Float(unitPrice).floatValue()<0.00f)
        {
             order.discount = order.discount+new Float(unitPrice).floatValue();
        }   else
        {
          /*  if(sku.toUpperCase().startsWith("UC")
                    || sku.toUpperCase().startsWith("TL")
                    || sku.toUpperCase().startsWith("IG")
                    || sku.toUpperCase().startsWith("L66"))
            {
                desc="Shipped Separately";
            }*/
        order.addLineItem(sku,
                    qty,
                    unitPrice,
                    linePrice,
                    desc,
                    color, size);
        }

}
}
