package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient
import com.owd.jobs.jobobjects.spscommerce.beans.AddressInfo
import com.owd.jobs.jobobjects.spscommerce.beans.SPSReference


class JustBrandsCampingWorldStockingEDI extends SPSCommerceBaseClient{
    {
        checkForFutureShip = true;
        vendorPartNumIsSku = true;
    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
    }

    @Override
    def String getScacCode(Object poData, String shipMethod){
        if(shipMethod.equalsIgnoreCase("FedEx Ground") || shipMethod.equalsIgnoreCase("FedEx Home Delivery")){
            return "FDEG";
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
        order.template = "campingworldstocking";
        order.setBusinessOrder(true);
    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
        if (dropShipOrder)
        {
            order.setThirdPartyBilling("909138442");
            order.setThirdPartyBillingContact("GANDER TP VENDOR TO CUSTOMER", "GANDER TP VENDOR TO CUSTOMER", "111 RED BANKS RD", "", "GREENVILLE", "NC", "27858", "");
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
        inboundShipMap.put("UNSP_SE","UPS 2nd Day Air");
        inboundShipMap.put("FEDEX GROUND","FedEx Ground");
        inboundShipMap.put("FEDEX HOME DELIVERY","FedEx Home Delivery");

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

    @Override
    def void doFinalStuffBeforeOrderSave(Order order){
        if(dropShipOrder){
            order.template = "campingworld";
            order.setBusinessOrder(false);
            order.setGroupName("campingworld")

        }else{
            //non dropship orders need ucc128 labels
            order.addTag(TagUtilities.kVendorComplianceIDReference, "12");
            order.setGroupName("campingworldstocking")
        }

        // case 888283 Inserts sku
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }
}
