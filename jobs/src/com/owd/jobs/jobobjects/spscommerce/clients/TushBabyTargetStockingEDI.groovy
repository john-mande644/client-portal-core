package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

/**
 * Created by danny on 10/16/2018.
 */
class TushBabyTargetStockingEDI extends SPSCommerceBaseClient{


    {
        includeVendorPartNumInAck = true;
        includeAllPhysicalDetails = true;
        forceSkuMapUsage = true;
    }
   /* @Override
    def  String getASNPurposeCode(String OrderType){
        if(OrderType.equalsIgnoreCase("SA")){
            return "00";
        }else{
            return "06";
        }
    }*/

    @Override
    def boolean ignoreTheUOM(){
        return true;
    }
    @Override
    def String getRoutingSequenceCode(){
        return "B";
    }


    @Override
    void loadOrderTemplate(Order order){
        //order.template = "targetStocking";
    }
    @Override
    void loadThirdPartyBillingInfo(Order order){
       /* if(order.getShippingMethodName().contains("FedEx")){
            order.setThirdPartyBilling("628753997")
        }
        if(order.getShippingMethodName().contains("UPS")){
            order.setThirdPartyBilling("8768Y6")
        }*/


    }
    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception{

        return "LTL";
        /*method = carrierInformation.CarrierAlphaCode.text();

        String routing = carrierInformation.CarrierRouting.text();
        String serviceLevel = carrierInformation.ServiceLevelCodes.ServiceLevelCode.text();

        if(method.equalsIgnoreCase("FDE")){
            if(routing.equalsIgnoreCase("NDS")){
                return "FedEx Standard Overnight"
            }
        }
        if(method.equalsIgnoreCase("FDEG")){
            if(routing.equalsIgnoreCase("NS")){

                if(serviceLevel.equalsIgnoreCase("G2")){
                    return "FedEx Ground";
                }
            }
            if(routing.equalsIgnoreCase("HD")){
                return "FedEx Home Delivery";
            }
        }*/

/*

        if (spsCodeToOWDMethodMap.containsKey(method)){
            return spsCodeToOWDMethodMap.get(method);
        }*/


       // throw new Exception("Unable to determine ship method for "+ method);
    }
    @Override
    void loadLabelInfo(Order order){
     /*   order.companyOverride = "DICK'S Sporting Goods";
        order.nameOverride = "Returns Department";
        order.address1Override = "7603 Trade Port Drive";
        order.cityOverride = "Louisville";
        order.stateOverride = "KY";
        order.zipOverride = "40258";*/


    }
    @Override
    def String getShipQtyUOM(Object poLineItem){
        return "EA";
    }
    @Override String getItemStatusCode(Object poLineItem){
        return  "AC"
    }
    @Override
    def String getScacCode(Object poData, String shipMethod){
        return "HD";
    }
    @Override
    def String getStatusCode(String shipMethod){
        if(shipMethod.contains("Ground")){
            return "G2"
        }
        return "DS";
    }
    @Override
    def boolean includeVendorInShipmentHeader(){
        return true;
    }
    @Override
    void doFinalStuffBeforeOrderSave(Order order)
    {
        //overload in custom class for vendor as needed
        order.setFacilityCode("DC6")
        order.setLTLHold();
    }

    @Override
    void injectWorkOrder(Order order, Object po){

            order.injectWorkOrder("TUSHBABY - TARGET LTL ORDER# " + order.orderNum, "Please prepare Order Reference : " +
                    order.clientOrderReference + " according to Target's domestic shipment guidelines. Shipping Labels will be provided shortly. \n" +
                    "Pack 16 units per sku/carton for TushBaby all SKUS with the following exception \n" +
                    "***8 units per carton of the Extension belts SKU(s)*** \n" +
                    "Must palletize all orders 48\" minimum pallet height. \n" +
                    "(preferably 8 ctns per pallet - IF it's within 48\") \n" +
                    "Must shrink wrap and apply pallet flag.");

    }
}
