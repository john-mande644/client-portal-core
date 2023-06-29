package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

/**
 * Created by danny on 10/16/2018.
 */
class BumbleRideWilliamsSanomaEDI extends SPSCommerceBaseClient{


    {
        includeVendorPartNumInAck = true;
       // useMappingForASN= true;
        vendorPartNumIsSku = true;
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
    def boolean includeInnerpackShipmentLine(){
        return true;
    }
    @Override
    def boolean includeProductOrItemDescriptionFromFile(){
        return true;
    }


    @Override
    void loadOrderTemplate(Order order){
        order.template = "potterybarn";
    }
    @Override
    void loadThirdPartyBillingInfo(Order order){
        if(order.getShippingMethodName().contains("FedEx")){
           // order.setThirdPartyBilling("628753997")
        }
        if(order.getShippingMethodName().contains("UPS")){
            order.setThirdPartyBilling("18941V")
        }
        //8768Y6 UPS accound
        //628753997 FedEx account

      //  order.setThirdPartyBilling("9V3W92");
      //  order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");


    }
    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception{

        if(carrierInformation.ServiceLevelCodes.ServiceLevelCode.text().equals("CG")){
            return "UPS Ground";
        }
        method = carrierInformation.CarrierAlphaCode.text();

        String routing = carrierInformation.CarrierRouting.text();

        if(method.equalsIgnoreCase("FDE")){
            if(routing.equalsIgnoreCase("NDS")){
                return "FedEx Standard Overnight"
            }
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
        if(shipMethod.contains("FedEx")){
            return "FDEG";
        }
        if(shipMethod.contains("UPS")){
            return "UPSC";
        }

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

}
