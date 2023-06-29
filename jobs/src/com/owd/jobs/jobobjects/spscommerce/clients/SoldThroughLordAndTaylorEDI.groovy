package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

class SoldThroughLordAndTaylorEDI extends SPSCommerceBaseClient{
    {
        checkForFutureShip = true;
    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
    }

    @Override
    def boolean includeProductOrItemDescriptionFromFile() {
        return true;
    }

    @Override
    def String getScacCode(Object poData, String shipMethod) {
        return poData.Header.CarrierInformation.CarrierAlphaCode.text().trim()
    }

    @Override
    def String getFOBPayCode() {
        return 'CC';
    }

    @Override
    def boolean ignoreTheUOM() {
        return true;
    }

    @Override
    def String getRoutingSequenceCode() {
        return "B";
    }

    @Override
    void loadOrderTemplate(Order order) {
        order.template = "lordandtaylor";
        order.group_name = "Lord and Taylor";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
        if (order.getShippingMethodName().contains("UPS")) {
            order.companyOverride = "Lord and Taylor";
            order.setThirdPartyBilling("YX8771");
            order.setThirdPartyBillingContact("Lord & Taylor","","1735 Jersey Ave","","North Brunswick","NJ","08902","");
        }
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        if (method.equalsIgnoreCase("Domestic")) {
            return "UPS Ground";
        }

        return "UPS Ground"
    }

    @Override
    void loadLabelInfo(Order order) {
    }

    @Override
    def String getShipQtyUOM(Object poLineItem) {
        return "EA";
    }

    @Override
    String getItemStatusCode(Object poLineItem) {
        return "AC"
    }

    @Override
    void injectWorkOrder(Order order, Object po) {
    }
}
