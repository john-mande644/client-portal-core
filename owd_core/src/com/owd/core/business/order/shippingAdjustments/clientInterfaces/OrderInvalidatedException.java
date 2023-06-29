package com.owd.core.business.order.shippingAdjustments.clientInterfaces;

/**
 * Created by IntelliJ IDEA.
 * User: StewartBuskirk
 * Date: Aug 19, 2010
 * Time: 11:38:26 AM
 * To change this template use File | Settings | File Templates.
 */
 public class OrderInvalidatedException extends Exception
    {
       public OrderInvalidatedException()
       {
           super();
       }
        public OrderInvalidatedException(String message)
       {
           super(message);
       }
    }
