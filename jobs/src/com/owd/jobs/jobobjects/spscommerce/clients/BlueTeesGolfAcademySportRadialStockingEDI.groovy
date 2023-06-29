package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

class BlueTeesGolfAcademySportRadialStockingEDI extends SPSCommerceBaseClient {
    {
        checkForFutureShip = false
        vendorPartNumIsSku = true
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
    }

    @Override
    String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        method = carrierInformation.ServiceLevelCodes.ServiceLevelCode.text()

        if (method.length() == 0) {
            return "LTL"
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
        order.group_name = "Academy In-House"
        order.addTag(TagUtilities.kVendorComplianceIDReference, "10")
    }

    @Override
    String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll
    }

    @Override
    void injectWorkOrder(Order order, Object po) {
        String subject = getWorkOrderSubject(order)
        String text = getWorkOrderText(order)

        order.injectWorkOrder(subject, text)
    }

    @Override
    void doFinalStuffBeforeOrderSave(Order order) {
        order.facilityCode = "CLOSEST"
        order.facilityPolicy = "CLOSEST"
    }

    static String getWorkOrderText(Order order ) {
        return "Hello,\n\n" +
                "Please help prepare this order for shipping. Please provide pallet count, dims and weight so we can route the shipment.\n\n" +
                "Labels:\n" +
                "***Must print UCC-128 labels***\n\n" +
                "Conveyable cartons - The UCC-128 label should be placed on the longest side panel in the bottom right corner. The edge of the label should be at least 2in from the carton edge.\n" +
                "Labels should not be covered by tape\n\n" +
                "Pallet:\n" +
                "Pallet height for LTL shipments should be built to 96 inches. (Multiple PO's per pallet, shipping to the same DC, are acceptable to maximize pallet height)\n\n" +
                "Packing Slip:\n" +
                "Packing list inside/outside of the lead carton and label \"Lead Carton\" or \"Packing List Enclosed\"\n\n" +
                "Refer to routing guide for more information.\n\n" +
                "Bar Codes - All products must have a UPC barcode on the sellable unit.\n\n" +
                "Let me know if you have any questions.\n\n" +
                "Thank you";
    }

    static String getWorkOrderSubject(Order order) {
        return "BTG - Academy In-House - PO# " + order.getPONumber() + " / OWD# " + order.getOrderRefNum();
    }
}