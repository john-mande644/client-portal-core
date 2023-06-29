package com.owd.jobs.jobobjects;

import com.owd.hibernate.generated.OwdOrder;

/**
 * Created by stewartbuskirk1 on 10/9/15.
 */
public interface OrderProcessingShipmentConfirmationHandler {
    public void reportShipment(OwdOrder order) throws Exception;
}
