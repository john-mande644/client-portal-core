package com.owd.jobs.jobobjects.spscommerce.clients


import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

class ROVRScheelsEDI extends SPSCommerceBaseClient {
    {
        checkForFutureShip = true;
        buyerPartNumIsSku = true;
    }

    @Override
    def String getScacCode(Object poData, String shipMethod){
        if(shipMethod.equalsIgnoreCase("FedEx Ground")){
            return "FEDEX GROUND";
        }
        if(shipMethod.equalsIgnoreCase("FedEx Home Delivery")){
            return "FEDEX HOME DELIVERY";
        }
        return poData.Header.CarrierInformation.CarrierAlphaCode.text().trim()
    }

    @Override
    def String getFOBPayCode(){
        return 'CC';
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
    void loadOrderTemplate(Order order) {
        order.template = "scheels";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
        if (order.getShipMethodName().contains("UPS")) {
            order.setThirdPartyBilling("E58611");
            order.setThirdPartyBillingContact("Scheels All Sports, Inc.","","4550 15th Ave S","","Fargo","ND","58103","6058457172");
        } else if (order.getShipMethodName().toUpperCase().contains("FEDEX")) {
            order.setThirdPartyBilling("792379370");
            order.setThirdPartyBillingContact("Scheels All Sports, Inc.","","4550 15th Ave S","","Fargo","ND","58103","6058457172");
        }
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception{
        if(null == method ||method.length()==0){
            return "UPS Ground";
        }
        Map<String,String> inboundShipMap = new TreeMap<>();
        inboundShipMap.put("UPSN_CG","UPS Ground");
        inboundShipMap.put("UNSP_CG","UPS Ground");
        inboundShipMap.put("UG","UPS Ground");
        inboundShipMap.put("UPS Ground","UPSG");
        inboundShipMap.put("UPSET_CG","UPS Ground");
        inboundShipMap.put("UX","UPS Ground");
        inboundShipMap.put("UPSN_ND","UPS Next Day Air");
        inboundShipMap.put("UPND","UPS Next Day Air");
        inboundShipMap.put("UR","UPS Next Day Air");
        inboundShipMap.put("UPS1","UPS Next Day Air");
        inboundShipMap.put("UPSN_NM","UPS Next Day Air");
        inboundShipMap.put("UNSP_ND","UPS Next Day Air")
        inboundShipMap.put("UPSN_PM","UPS Next Day Air Saver",);
        inboundShipMap.put("UPSET_ND","UPS Next Day Air");
        inboundShipMap.put("UZ","UPS Next Day Air");
        inboundShipMap.put("UPSN_ST","UPS SurePost");
        inboundShipMap.put("UNSP_SE","UPS 2nd Day Air")
        inboundShipMap.put("FEDEX HOME DELIVERY","FedEx Home Delivery")
        inboundShipMap.put("FEDEX GROUND","FedEx Ground")

        if(inboundShipMap.containsKey(method)){
            return inboundShipMap.get(method);
        }

        return "LTL";
    }
    @Override
    void loadLabelInfo(Order order){
      //  order.companyOverride = "DICK'S Sporting Goods";
      //  order.nameOverride = "Returns Department";
      //  order.address1Override = "7603 Trade Port Drive";
      //  order.cityOverride = "Louisville";
      //  order.stateOverride = "KY";
      //  order.zipOverride = "40258";


    }
    @Override
    void setFacilityCode(Order order){
        order.setFacilityCode("DC6");
    }

    @Override
    def String getShipQtyUOM(Object poLineItem){
        return "EA";
    }

    @Override String getItemStatusCode(Object poLineItem){
        return  "AC"
    }

    @Override
    def void setGroupName(Order order, Object po) {
        order.group_name = "scheels";
    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kPartialShip;
    }

    @Override
    def void doFinalStuffBeforeOrderSave(Order order){
    }

    @Override
    void injectWorkOrder(Order order, Object po){
        String subject = getWorkOrderSubject(order)
        String text = getWorkOrderText(order)

        order.injectWorkOrder(subject, text)
    }

    String getWorkOrderText(Order order) {
        return "Hello,\n\n" +
                "Please help prepare this Scheels LTL order for shipping. Please provide,\n" +
                "pallet count, dims and weight so we can route the shipment.\n\n" +
                "No overhang allowed - max pallet height: 96\" (48x40x96)\n\n" +
                "***Must print UCC-128 labels***\n\n" +
                "Refer to page 4 of the routing guide for label placement.\n\n" +
                "Packing slip must be attached to either the lead or last carton of a PO.\n\n" +
                "Let me know if you have any questions.\n\n" +
                "Thank you.\n\n" +
                "***250lbs - note for FS***\n\n";
    }

    String getWorkOrderSubject(Order order) {
        return "ROVR - Scheels - PO# " + order.getPONumber() + " / OWD# " + order.getOrderRefNum();
    }
}
