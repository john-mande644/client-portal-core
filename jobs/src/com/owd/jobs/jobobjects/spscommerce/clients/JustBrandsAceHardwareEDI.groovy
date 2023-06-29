package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

class JustBrandsAceHardwareEDI extends SPSCommerceBaseClient{

    {
        checkForFutureShip = true;
        useGTIN = true;
    }

    @Override
    def boolean includeProductOrItemDescriptionFromFile(){
        return true;
    }

    @Override
    def String getScacCode(Object poData, String shipMethod){

        if(shipMethod.equalsIgnoreCase("FedEx 2Day")){
            return "FEDEX 2DAY";
        }
        if(shipMethod.equalsIgnoreCase("FedEx Home Delivery")){
            return "FEDEX HOME DELIVERY";
        }
        if(shipMethod.equalsIgnoreCase("FedEx Ground")){
            return "FEDEX GROUND";
        }
        if(shipMethod.equalsIgnoreCase("FedEx Standard Overnight")){
            return "FEDEX STD OVERNIGHT";
        }
        if(shipMethod.equalsIgnoreCase("UPS 2nd Day Air")){
            return "UPS 2DAY";
        }
        if(shipMethod.equalsIgnoreCase("UPS Ground")){
            return "UPS GROUND";
        }
        if(shipMethod.equalsIgnoreCase("UPS Next Day Air")){
            return "UPS OVERNIGHT";
        }
        if(shipMethod.equalsIgnoreCase("UPS Next Day Air Saver")){
            return "UPS OVERNIGHT";
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
    void loadOrderTemplate(Order order){
        order.setBusinessOrder(true);
    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception{
        if ( null == method ||method.length() == 0 ) {
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

        if(inboundShipMap.containsKey(method)){
            return inboundShipMap.get(method);
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
    void injectWorkOrder(Order order, Object po){
    }

    @Override
    def void setDropShipFlag(Object po){
        if(po.Meta.IsDropShip != null){
            if(po.Meta.IsDropShip.text().equals("true")){
                dropShipOrder = true;
            }
        }
    }

    // case 927111
    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
    }

    // case 888283 Inserts sku
    @Override
    def void doFinalStuffBeforeOrderSave(Order order){
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }
}