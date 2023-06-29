package com.owd.jobs.jobobjects.spscommerce.clients


import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

class ROVRMooseJawEDI extends SPSCommerceBaseClient{

    {
        checkForFutureShip = true;
        vendorPartNumIsSku = true;
    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll
    }

    @Override
    def boolean includeProductOrItemDescriptionFromFile(){
        return true;
    }

    @Override
    def String getScacCode(Object poData, String shipMethod){
        if(shipMethod.equalsIgnoreCase("FedEx Ground")){
            return "FEDEX GROUND";
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
//        order.template = "moosejaw";
        order.setBusinessOrder(true);
    }
    @Override
    void loadThirdPartyBillingInfo(Order order){
    }
    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception{
        return "LTL";
    }
    @Override
    void loadLabelInfo(Order order){


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
        String subject = getWorkOrderSubject(order)
        String text = getWorkOrderText(order)

        order.injectWorkOrder(subject, text)
    }

    def String getWorkOrderText(Order order) {
        return "Please help prepare this Moosejaw LTL order for shipping. Please provide pallet count, dims and weight so we can schedule the pickup.\n\n" +
                "No overhang allowed - max pallet height: 52\" (48x40x52)\n" +
                "Grade A or GMA standard pallets allowed only*\n\n" +
                "***Must print UCC-128 labels***\n\n" +
                "Bar Codes - All products must have a UPC barcode on the sellable unit.\n\n" +
                "Packing slip must be attached to either the lead or last carton of a PO.\n\n" +
                "PO# " + order.getPONumber() + " / OWD# " + order.getOrderRefNum()
    }

    def String getWorkOrderSubject(Order order) {
        return "ROVR - Moosejaw - PO# " + order.getPONumber() + " / OWD# " + order.getOrderRefNum();
    }

    @Override
    def void setDropShipFlag(Object po){
        //Look for the words DROP SHIP in the NoteInformationField contents to deteremine dropship or stocking order
        if(po.Meta.IsDropShip != null){
            if(po.Meta.IsDropShip.text().equals("true")){
                dropShipOrder = true;
            }
        }

    }

    @Override
    def void doFinalStuffBeforeOrderSave(Order order){
    }
}
