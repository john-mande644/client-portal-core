package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

/**
 * Created by danny on 10/16/2018.
 */
class JustBrandsModellsEDI extends SPSCommerceBaseClient {
    {
        checkForFutureShip = true;
    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
    }

    @Override
    def  String getASNPurposeCode(String OrderType){
        if(OrderType.equalsIgnoreCase("SA")){
            return "00";
        }else{
            return "06";
        }
    }

    @Override
    void loadOrderTemplate(Order order){
        order.template = "Modells";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
        order.setThirdPartyBilling("771029213");
        order.setThirdPartyBillingContact("Modell's","","2100 International Parkway","","North Canton","OH","44720","8002756633");
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation){
        return "FedEx Home Delivery";
    }

    @Override
    def String getStatusCode(String shipMethod){
        return "DS";
    }
    def String getAcknowledgementType(int size){
        if(size>0){
            return "AE"
        }else {
            return "AK"
        }
    }
}
