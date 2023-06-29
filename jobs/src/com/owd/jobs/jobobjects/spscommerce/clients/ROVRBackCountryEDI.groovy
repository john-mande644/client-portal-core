package com.owd.jobs.jobobjects.spscommerce.clients


import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

class ROVRBackCountryEDI extends SPSCommerceBaseClient{

    {
        checkForFutureShip = true;
        includeOrderQtyUOMShipmentLine = true;

        //vendorPartNumIsSku = true;


    }

    @Override
    def String getOrderQtyUOM(Object poLineItem){
        return "EA";
    }

    @Override
    def String getScacCode(Object poData, String shipMethod){
        if(shipMethod.equalsIgnoreCase("FedEx Ground")){
            return "FEDEX GROUND";
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
    def String getCarrierTransMethodCode(OwdOrder order){
        return "M";
    }

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
//        order.template = "backcountry";
        order.setBusinessOrder(true);
    }
    @Override
    void loadThirdPartyBillingInfo(Order order){
      //  order.setThirdPartyBilling("9V3W92");
       // order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");


    }
    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception{
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
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
    }

    @Override
    void injectWorkOrder(Order order, Object po){

            order.injectWorkOrder(
                    "Backcountry Orders LTL Order " + order.orderNum,
                    "Hello,\n\n" +
                    "Please help prepare this LTL order for shipping. Please provide pallet count, dims and weight so" +
                    " we can schedule the pick-up.\n\n"+
                    "***Please stack 6 cartons per pallet***\n" +
                    "**Must print and apply UCC-128 labels from the system***\n\n" +
                    "Please include a packing slip with this shipment. The packing slip will be clearly marked on the lead carton." +
                    "ROVR ref# "+ order.orderRefNum+" // OWD# "+ order.orderNum+". \n" +
                    "Let me know if you have any questions\n" +
                    "Thank you.\n"

            );

    }
    @Override
    def void setDropShipFlag(Object po){
        //Look for the words DROP SHIP in the NoteInformationField contents to deteremine dropship or stocking order
       /* println ("hello")
        if(po.Meta.IsDropShip != null){
            if(po.Meta.IsDropShip.text().equals("true")){
                dropShipOrder = true;
            }
        }*/

    }

    @Override
    def String getOrderQty(Object poLineItem){
        return poLineItem.OrderQty?.text().trim();
    }

    @Override
    def void doFinalStuffBeforeOrderSave(Order order){
        /*if(dropShipOrder){
            order.template = "campingworld";
            order.setBusinessOrder(false);
            order.setGroupName("campingworld")

        }else{
            //non dropship orders need ucc128 labels
            order.addTag(TagUtilities.kVendorComplianceIDReference, "12");
            order.setGroupName("campingworldstocking")
        }*/
    }
}
