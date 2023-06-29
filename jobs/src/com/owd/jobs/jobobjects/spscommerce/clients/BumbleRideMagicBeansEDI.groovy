package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

class BumbleRideMagicBeansEDI extends SPSCommerceBaseClient {
    {
        includeVendorPartNumInAck = true
        vendorPartNumIsSku = true
    }

    @Override
    void setGroupName(Order order, Object po) {
        order.group_name = "Magic Beans";
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
    boolean includeInnerpackShipmentLine() {
        return true
    }

    @Override
    boolean includeProductOrItemDescriptionFromFile() {
        return true
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
        if (order.getShippingMethodName().contains("UPS")) {
            order.setThirdPartyBilling("A25149")
            order.setThirdPartyBillingContact("Magic Beans", "Magic Beans", "1 Westinghouse Plaza", "Building K", "Hyde Park", "MA", "02136", "")
        }
    }

    @Override
    String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        return "UPS Ground"
    }

    @Override
    void loadLabelInfo(Order order) {}

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
        if (shipMethod.contains("FedEx")) {
            return "FDEG"
        }

        if (shipMethod.contains("UPS")) {
            return "UPSC"
        }

        return "HD"
    }

    @Override
    String getStatusCode(String shipMethod) {
        if (shipMethod.contains("Ground")) {
            return "G2"
        }
        return "DS"
    }

    @Override
    boolean includeVendorInShipmentHeader() {
        return true
    }
}
