package com.owd.core.business.order;


public abstract class OrderImporter extends Object {


    public OrderImporter() {

        super();

    }


    public abstract void checkForOrders(String clientID);


}
