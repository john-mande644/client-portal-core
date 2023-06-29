package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

/**
 * Created by danny on 10/16/2018.
 */
class JustBrandsCabelasEDI extends SPSCommerceBaseClient {
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
        order.template = "cabelas";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
        order.setThirdPartyBilling("149096787");
        order.setThirdPartyBillingContact("Cabela's","","One Cabela Drive","","Sidney","NE","69162","6058457172");
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

    @Override
    void injectWorkOrder(Order order, Object po){
        if(isFutureShip) {
            order.injectWorkOrder("JustBrands/Cabela's future work order. Date: "+futureShipDate+" OrderNum: " + order.orderNum, "Please release this order in time for it to ship by "+futureShipDate+". \n" +
                    ""
            );
        }

    }
}
