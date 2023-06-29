/*
 * ShipPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public interface ShipPortType extends java.rmi.Remote {
    public com.owd.fedEx.ShipService.ProcessTagReply processTag(com.owd.fedEx.ShipService.ProcessTagRequest processTagRequest) throws java.rmi.RemoteException;
    public com.owd.fedEx.ShipService.ProcessShipmentReply processShipment(com.owd.fedEx.ShipService.ProcessShipmentRequest processShipmentRequest) throws java.rmi.RemoteException;
    public com.owd.fedEx.ShipService.ShipmentReply deleteTag(com.owd.fedEx.ShipService.DeleteTagRequest deleteTagRequest) throws java.rmi.RemoteException;
    public com.owd.fedEx.ShipService.ShipmentReply deleteShipment(com.owd.fedEx.ShipService.DeleteShipmentRequest deleteShipmentRequest) throws java.rmi.RemoteException;
    public com.owd.fedEx.ShipService.ShipmentReply validateShipment(com.owd.fedEx.ShipService.ValidateShipmentRequest validateShipmentRequest) throws java.rmi.RemoteException;
}
