package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

/**
 * Created by danny on 10/16/2018.
 */
class TushBabyBedBathBeyondEDI extends SPSCommerceBaseClient{
    {
       
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
    def String getOrderQty(Object poLineItem){
        return "0";
    }
    @Override
    def boolean ignoreTheUOM(){
        return true;
    }

    @Override
    void loadOrderTemplate(Order order){
      //  order.template = "bedbathbeyond";
    }
    @Override
    void loadThirdPartyBillingInfo(Order order){
        order.setThirdPartyBilling("910999869");
        order.setThirdPartyBillingContact("Bed Bath & Beyond","","1001 W. Middlesex Road","","Port Reading","NJ","07064","6058457172");

    }
    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation){
        return "FedEx Home Delivery";
    }
    @Override
    def String getShipQtyUOM(Object poLineItem){
        return "EA";
    }
    @Override String getItemStatusCode(Object poLineItem){
        return  "AC"
    }
    @Override
    def String getFOBPayCode(){
        return 'CC';
    }
    @Override
    void loadLabelInfo(Order order){
        order.companyOverride = "Bed Bath & Beyond";
        order.nameOverride = "Bed Bath & Beyond";
        order.address1Override = "1001 W. Middlesex Road";
        order.cityOverride = "Port Reading";
        order.stateOverride = "NJ";
        order.zipOverride = "07064";
      //  order.phoneOverride = "800-430-3376"


    }
    @Override
    void doFinalStuffBeforeOrderSave(Order order){
        //overload in custom class for vendor as needed
        order.setFacilityCode("DC6")
    }
}
