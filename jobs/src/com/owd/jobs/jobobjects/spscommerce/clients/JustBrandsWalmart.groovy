package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient


class JustBrandsWalmartEDI extends SPSCommerceBaseClient {
    {
        includeBillOfLadingInHeader = true;
        buyerPartNumIsSku = true;
    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll
    }

    @Override
    void setGroupName(Order order, Object po) {
        order.group_name = "Walmart-stocking";
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
        order.template = "walmartStocking";
        order.setBusinessOrder(true);
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        return "LTL"
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
    def String getScacCode(Object poData, String shipMethod) {
        return "HD";
    }

    @Override
    def String getStatusCode(String shipMethod) {
        if (shipMethod.contains("Ground")) {
            return "G2"
        }
        return "DS";
    }

    @Override
    def boolean includeVendorInShipmentHeader() {
        return true;
    }

    @Override
    def String getCarrierTransMethodCode(OwdOrder order) {
        return order.shipinfo.carrService.equals('LTL') || order.shipinfo.carrService.equalsIgnoreCase('FedEx Collect') ? 'M' : 'U';
    }

    @Override
    void doFinalStuffBeforeOrderSave(Order order) {
        order.holdNewOrder();

    }

    @Override
    def String getFOBPayCode() {
        return 'CC';
    }

    @Override
    void injectWorkOrder(Order order, Object po) {
        order.injectWorkOrder(
                "JustBrand - WalmartStocking OrderNum: " + order.orderNum,
                "Hello,\n" +
                        "Please pack through the pack station and apply UCC-128 labels. Provide carton weight, dims, and carton count by PO.\n" +
                        "Please use attached routing guide for instructions on how to apply labels, create pallets, and load shipments.\n" +
                        "*Do not mix SKUs on the same cartons*\n" +
                        "**OK to mix POs going to the same DC on the same pallets**\n" +
                        "**Do not mix POs/Pallets when loading in the truck**\n" +
                        "PO#: " + order.po_num + "/ OWD#" + order.orderNum + " / Customer Name: " + order
                        .getShippingContact().getName()
                        + " \n" +
                        "Thank you\n"
        );
    }
}
