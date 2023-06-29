package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

class ROVRCampingWorldStockingEDI extends SPSCommerceBaseClient{

    {
        checkForFutureShip = true;
        vendorPartNumIsSku = true;


    }

    @Override
    def String getScacCode(Object poData, String shipMethod){
        if(shipMethod.equalsIgnoreCase("FedEx Ground")){
            return "FEDEX GROUND";
        }
        //nboundShipMap.put("FEDEX HOME DELIVERY","FedEx Home Delivery");
        if(shipMethod.equalsIgnoreCase("FedEx Home Delivery")){
            return "FEDEX HOME DELIVERY";
        }
        return poData.Header.CarrierInformation.CarrierAlphaCode.text().trim()
    }

   /* @Override
    def  String getASNPurposeCode(String OrderType){
        if(OrderType.equalsIgnoreCase("SA")){
            return "00";
        }else{
            return "06";
        }
    }*/

    /*@Override
    def SPSReference getShipmentId(String orderNum){
        SPSReference reference = new SPSReference();
        reference.setReferenceID(orderNum);
        reference.setReferenceQual("BB");
        return reference;
    }*/

   /* @Override
    def AddressInfo getBuyingAddressInfo(Object poData){
        AddressInfo buyerInfo = new AddressInfo();

        String location = poData.LineItems.LineItem[0].QuantitiesSchedulesLocations.LocationQuantity.Location.text();
        if(location.length()>0){
            buyerInfo.setAddressTypeCode("BY");
            buyerInfo.setLocationCodeQualifier("92");
            buyerInfo.setAddressLocationNumber(location);
        }

        return  buyerInfo;
    }*/

   /* @Override
    def boolean includeOuterpackShipmentLine(){
        return true;
    }*/

    @Override
    def String getFOBPayCode(){
        return 'CC';
    }

   /* @Override
    def AddressInfo getShipFromAddressInfo(){
        AddressInfo info = new AddressInfo();
        info.setAddressLocationNumber("57811");
        info.setLocationCodeQualifier("92");
        return info;
    }*/

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
        //order.template = "campingworldstocking";
        order.setBusinessOrder(true);
    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
        order.setThirdPartyBilling("278463419");
        order.setThirdPartyBillingContact("Camping World", "Camping World", "111 Red Banks Rd.", "",
                "Greenville", "NC", "27858", "8006263636");
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
        throw new Exception("Unable to determine ship method for "+ method);
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
    void injectWorkOrder(Order order, Object po){
        //Todo get work order text

      /*  if(isFutureShip){
            order.injectWorkOrder("JustBrands/Dick's Sporting Goods future work order. Date: "+futureShipDate+" OrderNum: " + order.orderNum, "Please fulfill this order. \n" +
                    "WARNING: This order requires specific labeling. Please see the most recent Dick's Sporting Goods Routing Guide for instructions."
            );
        }else{
            order.injectWorkOrder("JustBrands/Dick's Sporting Goods work order. OrderNum: " + order.orderNum, "Please fulfill this order. \n" +
                    "WARNING: This order requires specific labeling. Please see the most recent Dick's Sporting Goods Routing Guide for instructions."
            );
        }*/
    }
    @Override
    def void setDropShipFlag(Object po){
        //Look for the words DROP SHIP in the NoteInformationField contents to deteremine dropship or stocking order
        println ("hello")
        if(po.Meta.IsDropShip != null){
            if(po.Meta.IsDropShip.text().equals("true")){
                dropShipOrder = true;
            }
        }

    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
    }

    @Override
    def void doFinalStuffBeforeOrderSave(Order order){
        if(dropShipOrder){
//            order.template = "campingworld";
            order.setBusinessOrder(false);
            order.setGroupName("campingworld")

        }else{
            //non dropship orders need ucc128 labels
            order.addTag(TagUtilities.kVendorComplianceIDReference, "12");
            order.setGroupName("campingworldstocking")
        }
    }
}
