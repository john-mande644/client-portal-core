package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

class PopularHoldingsAmazonEDI extends SPSCommerceBaseClient {
    {
        checkForFutureShip = true;
        useEAN = true;
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
        // order.template = "amazon";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        if (null == method || method.length() == 0) {
            return "LTL";
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
        String ship = "";

        po.Header.Date.each {date ->
            if(date.DateTimeQualifier1.text().equals("037")){
                ship = date.Date1.text();
            }
        }
        String subject = getWorkOrderSubject((ship != "" ? ship : java.time.LocalDate.now().toString()), order);
        String text = getWorkOrderText();
        order.injectWorkOrder(subject, text)
    }

    def String getWorkOrderSubject(String shipDate, Order order) {
        return "Popular Holdings Amazon work order. Date: $shipDate OrderNum: $order.orderNum, PO: $order.po_num";
    }

    def String getWorkOrderText() {
        return "Please pack this order sing Amazon Vendor Compliance Rules.\n\nNo Product labels are needed. Please provide the Dims and Weights so we may generate the Carton Labels.\n";
    }
}
