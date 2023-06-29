package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

/**
 * Created by danny on 10/16/2018.
 */
class JustBrandsOnyxEDI extends SPSCommerceBaseClient{
    {
        outboundShipMap.put("UPS Ground", "SG");
        spsCodeToOWDMethodMap.put("SG", "UPS Ground");
        outboundShipMap.put("FedEx Ground","SG");
        vendorPartNumIsSku = true
    }

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
        order.template = "onyx";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
        if(order.shipMethodName.toUpperCase().contains("FEDEX")) {
            order.setThirdPartyBilling("325428201");
        }
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        method = carrierInformation.CarrierRouting.text();

        if(method.equalsIgnoreCase("UPS Standard")){
            return "UPS Standard Canada";
        }
        if(method.equalsIgnoreCase("USPS Priority Mail")){
            return "USPS Priority Mail";
        }
        if(method.equalsIgnoreCase("USPS Priority")){
            return "USPS Priority Mail";
        }
        if(method.equalsIgnoreCase("UPS Ground")){
            return "UPS Ground";
        }
        if(method.equalsIgnoreCase("FedEx Ground")){
            return "FedEx Ground";
        }
        // Case: Fedex Home Delivery where second "e" is not capitalized
        if(method.equalsIgnoreCase("Fedex Home Delivery")){
            return "FedEx Home Delivery";
        }

        if (spsCodeToOWDMethodMap.containsKey(method)){
            return spsCodeToOWDMethodMap.get(method);
        }

        throw new Exception("Unable to determine ship method for "+ method);
    }

    @Override
    void loadLabelInfo(Order order){
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

    // case 888283 Inserts sku
    @Override
    def void doFinalStuffBeforeOrderSave(Order order){
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }
}
