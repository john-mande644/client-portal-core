package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient


class BlueTeesGolfScheelsEDI extends SPSCommerceBaseClient{

    {
        outboundShipMap.put("UPS Ground", "SG");
        spsCodeToOWDMethodMap.put("SG", "UPS Ground");
        spsCodeToOWDMethodMap.put("ND", "UPS Next Day Air Saver")
        outboundShipMap.put("UPS Next Day Air Saver","ND")
        spsCodeToOWDMethodMap.put("SE","UPS 2nd Day Air")
        checkForFutureShip = true;

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
        order.template = "scheels";
    }
    @Override
    void loadThirdPartyBillingInfo(Order order){
        //8768Y6 UPS accound
        //628753997 FedEx account

        order.setThirdPartyBilling("E58611");
        order.setThirdPartyBillingContact("Scheels All Sports, Inc.","","4550 15th Ave S","","Fargo","ND","58103","6058457172");

    }
    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception{

        method = carrierInformation.ServiceLevelCodes.ServiceLevelCode.text();

        if(method.length()==0){
            return "UPS Ground";
        }
        if (spsCodeToOWDMethodMap.containsKey(method)){
            return spsCodeToOWDMethodMap.get(method);
        }


        throw new Exception("Unable to determine ship method for "+ method);
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
    def void setGroupName(Order order,Object po){
        if(po.Header.OrderHeader.PurchaseOrderTypeCode.text().equals("SA")){
            order.group_name =  "scheels-stocking";
            order.addTag(TagUtilities.kVendorComplianceIDReference, "10");

        }
    }
    @Override
    def String loadBackorderRule(){
        return OrderXMLDoc.kBackOrderAll;
    }

    @Override
    void injectWorkOrder(Order order, Object po){
        if(isFutureShip) {
            order.injectWorkOrder("BlueTeesGolf/Scheels's future work order. Date: "+futureShipDate+" OrderNum: " + order.orderNum, "Please release this order in time for it to ship by "+futureShipDate+". \n" +
                    ""
            );

        }

    }

    // 1211115 - add DOM support for Blue Tees
    @Override
    def void doFinalStuffBeforeOrderSave(Order order){
        order.facilityCode = "CLOSEST";
        order.facilityPolicy = "CLOSEST";
    }


}
