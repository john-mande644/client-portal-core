package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

/**
 * Created by danny on 10/16/2018.
 */
class JustBrandsDicksEDI extends SPSCommerceBaseClient {
    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
    }

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
        order.template = "dicks";
    }
    @Override
    void loadThirdPartyBillingInfo(Order order){
        order.setThirdPartyBilling("716047598");
        order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","345 Court Street","","Coraopolis","PA","15108","6058457172");
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        Map<String,String> inboundShipMap = new TreeMap<>();

        inboundShipMap.put("UPSN_CG","FedEx Home Delivery");
        inboundShipMap.put("UNSP_CG","FedEx Home Delivery");
        inboundShipMap.put("UG","FedEx Home Delivery");
        inboundShipMap.put("FedEx Home Delivery","UPSG");
        inboundShipMap.put("UPSET_CG","FedEx Home Delivery");
        inboundShipMap.put("UX","FedEx Home Delivery");
        inboundShipMap.put("UPSN_ND","FedEx Standard Overnight");
        inboundShipMap.put("UPND","FedEx Standard Overnight");
        inboundShipMap.put("UR","FedEx Standard Overnight");
        inboundShipMap.put("UPS1","FedEx Standard Overnight");
        inboundShipMap.put("UPSN_NM","FedEx Standard Overnight");
        inboundShipMap.put("UNSP_ND","FedEx Standard Overnight")
        inboundShipMap.put("UPSN_PM","FedEx Standard Overnight",);
        inboundShipMap.put("UPSET_ND","FedEx Standard Overnight");
        inboundShipMap.put("UZ","FedEx Standard Overnight");
        inboundShipMap.put("UNSP_SE","FedEx 2Day")

        if(inboundShipMap.containsKey(method)){
            return inboundShipMap.get(method);
        }

        throw new Exception("Unable to determine ship method for "+ method);
    }
    @Override
    void loadLabelInfo(Order order){
        order.companyOverride = "DICK'S Sporting Goods";
        order.nameOverride = "Ecommerce Returns";
        order.address1Override = "7603 Trade Port Drive";
        order.cityOverride = "Louisville";
        order.stateOverride = "KY";
        order.zipOverride = "40258";
    }

    @Override
    def String getShipQtyUOM(Object poLineItem){
        return "EA";
    }

    @Override String getItemStatusCode(Object poLineItem){
        return  "AC"
    }

    // case 888283 Inserts sku
    @Override
    void doFinalStuffBeforeOrderSave(Order order){
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }
}
