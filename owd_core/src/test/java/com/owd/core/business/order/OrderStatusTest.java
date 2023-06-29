package com.owd.core.business.order;

import static org.junit.Assert.*;

public class OrderStatusTest {

    public static void main(String[] args){
        OrderStatus os = new OrderStatus("17262960");
        System.out.println("Lines " + os.getLineCount());
        System.out.println("order_location " + os.order_location);
        System.out.println("OrderReference " + os.orderReference);
        System.out.println("Order id " + os.order_id);
        System.out.println("OrderStatus " + os.getLocation());
        System.out.println("OrderStatus " + os.getLocation());
        System.out.println("OrderStatus " + os.getLocation());
        System.out.println("OrderStatus " + os.getLocation());
    }
}