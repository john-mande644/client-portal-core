package com.owd.web.internal.warehouse.orderChanges;

import com.owd.hibernate.generated.OwdOrder;

/**
 * Created by danny on 2/5/2020.
 */
public class queueOrders {

    OwdOrder order;
    Integer queueId;
    String customerName;
    String client;


    public OwdOrder getOrder() {
        return order;
    }

    public void setOrder(OwdOrder order) {
        this.order = order;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
