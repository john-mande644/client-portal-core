/**
 * IOrderProcessing.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.owd.onestop;

public interface IOrderProcessing extends java.rmi.Remote {
    public com.owd.onestop.OrderDetailsResponse getOrderDetails(com.owd.onestop.OrderDetailsRequest orderDetailsRequest) throws java.rmi.RemoteException;
    public com.owd.onestop.OrderDetailsResponse getNextUnprocessedOrder(com.owd.onestop.UnprocessedOrderRequest unprocessedOrderRequest) throws java.rmi.RemoteException;
    public com.owd.onestop.MarkOrderAsRetrievedResponse markOrderAsRetrieved(com.owd.onestop.UpdateOrderStatusRequest updateOrderStatusRequest) throws java.rmi.RemoteException;
    public com.owd.onestop.SettlementOperationResponse postSettlement(com.owd.onestop.SettlementDetails settlementDetails) throws java.rmi.RemoteException;
    public com.owd.onestop.ReturnOperationResponse postReturns(com.owd.onestop.ReturnDetails returnDetails) throws java.rmi.RemoteException;
    public com.owd.onestop.InventoryOperationResponse inventoryIncrementAction(com.owd.onestop.InventoryDetails inventoryDetails) throws java.rmi.RemoteException;
}
