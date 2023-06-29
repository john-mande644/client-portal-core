package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

class BlueTeesGolfAcademySportRadialEDI extends SPSCommerceBaseClient {
    {
        checkForFutureShip = false
    }

    @Override
    boolean ignoreTheUOM() {
        return true
    }

    @Override
    String getRoutingSequenceCode() {
        return "B"
    }

    @Override
    void loadOrderTemplate(Order order) {
        order.template = "academysports"
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
        order.setThirdPartyBilling("329820912")
        order.setThirdPartyBillingContact("", "Academy, LTD", "1800 N. Mason Rd", "", "Katy", "TX", "77449", "")
    }

    @Override
    String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        method = carrierInformation.ServiceLevelCodes.ServiceLevelCode.text()

        if (method.length() == 0) {
            return "FedEx Home Delivery"
        }

        if (spsCodeToOWDMethodMap.containsKey(method)) {
            return spsCodeToOWDMethodMap.get(method)
        }

        throw new Exception("Unable to determine ship method for " + method)
    }

    @Override
    void loadLabelInfo(Order order) {
    }

    @Override
    String getShipQtyUOM(Object poLineItem) {
        return "EA"
    }

    @Override
    String getItemStatusCode(Object poLineItem) {
        return "AC"
    }

    @Override
    String getScacCode(Object poData, String shipMethod) {
        return "HD"
    }

    @Override
    String getStatusCode(String shipMethod) {
        return "DS"
    }

    @Override
    boolean includeVendorInShipmentHeader() {
        return true
    }

    @Override
    void setGroupName(Order order, Object po) {
        if (po.Header.OrderHeader.PurchaseOrderTypeCode.text().equals("SA")) {
            order.group_name = "academy"
            order.addTag(TagUtilities.kVendorComplianceIDReference, "10")
        }
    }

    @Override
    String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll
    }

    @Override
    void injectWorkOrder(Order order, Object po) {
    }

    @Override
    void doFinalStuffBeforeOrderSave(Order order) {
        order.facilityCode = "CLOSEST"
        order.facilityPolicy = "CLOSEST"
    }
}