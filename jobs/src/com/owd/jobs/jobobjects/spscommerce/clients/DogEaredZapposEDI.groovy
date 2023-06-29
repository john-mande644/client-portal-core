package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.hibernate.generated.OrderTag
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient
import com.owd.jobs.jobobjects.spscommerce.beans.AddressInfo
import com.owd.jobs.jobobjects.spscommerce.beans.SPSReference

/**
 * Created by danny on 10/16/2018.
 */
class DogEaredZapposEDI extends SPSCommerceBaseClient{

    {
        checkForFutureShip = true;
        includeAllPhysicalDetails = true;
        includeOrderHeaderDepartment = true;
        useShipToAddressAlternateName = true;
        includeBillOfLadingInHeader = true;
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
    void setFacilityCode(Order order){
        order.setFacilityCode("DC1");
    }


    /*@Override
    void loadOrderTemplate(Order order){
        order.template = "zappos";
    }*/

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
        return "HD";
    }
    @Override
    def String getStatusCode(String shipMethod){
        if(shipMethod.contains("Ground")){
            return "G2"
        }
        return "DS";
    }
    @Override
    def boolean includeVendorInShipmentHeader(){
        return true;
    }

    @Override def String getPackingMedium(){
        return 'CTN25';
    }
    @Override
    def AddressInfo getShipFromAddressInfo(){
        AddressInfo info = new AddressInfo();
        info.setAddressLocationNumber("DC1");
        // info.setLocationCodeQualifier("92");
        return info;
    }

    @Override
    def void setDropShipFlag(Object po){

    }

    public String getShipByDate(Object po){
        String s = "";
        po.Header.Date.each { date ->
            println(date.DateTimeQualifier1.text())
            if(date.DateTimeQualifier1.text().contains("010")){
                println(date.Date1.text());
                s=date.Date1.text();
            }
        }
        return s;
    }


    @Override
    def int getVendorComplianceId(EdiSpsConfigdata config, Object po){
        //return 0 if it is a drop ship order.
        if(dropShipOrder) return 0;
        return config.getVendorComplianceFkey();
    }

    @Override
    void  loadBusinessOrConsumerOrder(Order order){
        if(!dropShipOrder){
            order.business_order = true;

        }
    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
    }

    @Override
    void injectWorkOrder(Order order, Object po) {

    }

    @Override
    def SPSReference getVendorComplianceShipmentNumberReference(Collection<OrderTag> tags) {

        String number = getVendorComplianceShipmentNumber(tags, TagUtilities.kEDIZapposDN)
        if(number != null && number.length() > 0)
        {
            SPSReference reference = new SPSReference();
            reference.setReferenceID(number);
            reference.setReferenceQual("BX");
            return reference;
        }
        return null;
    }

}
