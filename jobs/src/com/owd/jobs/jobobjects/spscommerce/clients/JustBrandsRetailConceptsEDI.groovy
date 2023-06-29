package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

class JustBrandsRetailConceptsEDI extends SPSCommerceBaseClient {
    {
        checkForFutureShip = true;
        includeOWDDunsLocationNumberASN = true;
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
        if (shipMethod.equalsIgnoreCase("FedEx Ground")) {
            return "FEDEX GROUND";
        }
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
        order.template = "retailConcepts";
        order.setBusinessOrder(true);
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        if (null == method || method.length() == 0) {
            return "UPS Ground";
        }
        Map<String, String> inboundShipMap = new TreeMap<>();

        inboundShipMap.put("UPSN_CG", "UPS Ground");
        inboundShipMap.put("UNSP_CG", "UPS Ground");
        inboundShipMap.put("UG", "UPS Ground");
        inboundShipMap.put("UPS Ground", "UPSG");
        inboundShipMap.put("UPSET_CG", "UPS Ground");
        inboundShipMap.put("UX", "UPS Ground");
        inboundShipMap.put("UPSN_ND", "UPS Next Day Air");
        inboundShipMap.put("UPND", "UPS Next Day Air");
        inboundShipMap.put("UR", "UPS Next Day Air");
        inboundShipMap.put("UPS1", "UPS Next Day Air");
        inboundShipMap.put("UPSN_NM", "UPS Next Day Air");
        inboundShipMap.put("UNSP_ND", "UPS Next Day Air")
        inboundShipMap.put("UPSN_PM", "UPS Next Day Air Saver",);
        inboundShipMap.put("UPSET_ND", "UPS Next Day Air");
        inboundShipMap.put("UZ", "UPS Next Day Air");
        inboundShipMap.put("UPSN_ST", "UPS SurePost");
        inboundShipMap.put("UNSP_SE", "UPS 2nd Day Air")

        if (inboundShipMap.containsKey(method)) {
            return inboundShipMap.get(method);
        }

        throw new Exception("Unable to determine ship method for " + method);
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

    @Override
    def void setDropShipFlag(Object po) {
        //Look for the words DROP SHIP in the NoteInformationField contents to deteremine dropship or stocking order
        println("hello")
        if (po.Meta.IsDropShip != null) {
            if (po.Meta.IsDropShip.text().equals("true")) {
                dropShipOrder = true;
            }
        }
    }

    // case 888283 Inserts sku
    @Override
    void doFinalStuffBeforeOrderSave(Order order) {
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }
}
