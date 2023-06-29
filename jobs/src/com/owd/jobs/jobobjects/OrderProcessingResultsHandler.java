package com.owd.jobs.jobobjects;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;

/**
 * Created by stewartbuskirk1 on 9/28/15.
 */
public interface OrderProcessingResultsHandler {

    public void onOrderImportSuccess(Order order) throws Exception;

    public void onOrderImportDuplicate(Order order) throws Exception;

    public void onOrderImportFailure(Order order) throws Exception ;

    public void onOrderImportFailure(String message) throws Exception;

    public void onOrderImportBatchSuccess(String batchRef) throws Exception;

    public void onOrderImportBatchFailure(String batchRef, String message) throws Exception;
}
