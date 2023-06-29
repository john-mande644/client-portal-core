package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient
import com.owd.jobs.jobobjects.spscommerce.beans.AddressInfo
/**
 * Created by danny on 10/16/2018.
 */
class LawlessBeautySephoraEDI extends SPSCommerceBaseClient {
    {
        includeVendorPartNumInAck = true;
        includeBillOfLadingInHeader = true;
        includeOrderHeaderDepartment = true;
        includeDepositorOrderNumber = true
        includeApplicationId = true
        useAlternateNameForAddress = true;
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
    def boolean includeInnerpackShipmentLine(){
        return true;
    }

    @Override
    def boolean includeProductOrItemDescriptionFromFile(){
        return true;
    }

    @Override
    void loadOrderTemplate(Order order){
        order.template = "sephora";
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
    def String getShipQtyUOM(Object poLineItem){
        return "EA";
    }

    @Override String getItemStatusCode(Object poLineItem){
        return  "AC"
    }

    @Override
    def String getScacCode(Object poData, String shipMethod){
        if(shipMethod.contains("FedEx")){
            return "FDEG";
        }
        if(shipMethod.contains("UPS")){
            return "UPSC";
        }

        return "HD";
    }
    @Override
    def String getStatusCode(String shipMethod) {
        if(shipMethod.contains("Ground")) {
            return "G2"
        }

        return "DS";
    }
    @Override
    def boolean includeVendorInShipmentHeader(){
        return true;
    }

    @Override
    def AddressInfo getShipFromAddressInfo(){
        AddressInfo info = new AddressInfo();
        info.setAddressLocationNumber("DC6");
        info.setLocationCodeQualifier("TPL");
        return info;
    }

    @Override
    void injectWorkOrder(Order order, Object po){
        String ship = "";

        po.Header.Date.each {date ->
            System.out.println(date.Date1.text());
            if(date.DateTimeQualifier1.text().equals("037")){
                ship = date.Date1.text();
            }
        }
        String subject = getWorkOrderSubject(ship, order);
        String text = getWorkOrderText();
        order.injectWorkOrder(subject, text)
    }

    @Override
    def String loadBackorderRule(){
        return OrderXMLDoc.kHoldOrder;
    }

    @Override
    void  loadBusinessOrConsumerOrder(Order order){
        order.setBusinessOrder(true);
    }

    @Override
    void doFinalStuffBeforeOrderSave(Order order){

    }

    def String getWorkOrderSubject(String shipDate, Order order) {
        return "Lawless Sephora work order. Date: $shipDate OrderNum: $order.orderNum, PO: $order.po_num";
    }

    def String getWorkOrderText() {
        return "Hello,\n" +
                "\n" +
                " \n" +

                "Please pick and pack the associated Sephora orders following the shipping guidelines (links below). Do not ship until routing has been complete. Once routing has been completed, the FS will confirm next steps for shipping.\n" +
                "\n" +
                " \n" +

                "Once shipped, ensure that the UCC-128 (GS1-128) labels and packing slip are attached accordingly. Packing slip will be generated after\n" +
                "\n" +
                " \n" +

                "Reach out to your FS if you have any questions or concerns.\n" +
                "\n" +
                " \n" +

                "Thanks" +
                "\n"+
                "https://vendorguide.s3-us-west-2.amazonaws.com/Sephora/Sephora+Brand+Relations+Handbook+and+Routing+Guides_Ver2018.5.docx\n"+
                "https://vendorguide.s3-us-west-2.amazonaws.com/Sephora/OWD+-+Sephora+Packing+Instructions.docx\n"
    }

}
