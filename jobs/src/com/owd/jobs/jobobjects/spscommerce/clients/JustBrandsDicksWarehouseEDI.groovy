package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.OrderTag
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient
import com.owd.jobs.jobobjects.spscommerce.beans.AddressInfo
import com.owd.jobs.jobobjects.spscommerce.beans.SPSReference

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * Created by danny on 10/16/2018.
 */
class JustBrandsDicksWarehouseEDI extends SPSCommerceBaseClient{
    {
        checkForFutureShip = true;
    }

    @Override
    def SPSReference getVendorComplianceShipmentNumberReference(Collection<OrderTag> tags) {
        String number = getVendorComplianceShipmentNumber(tags, TagUtilities.kEDIDicksASIDN)

        if(number != null && number.length() > 0)
        {
            SPSReference reference = new SPSReference();
            reference.setReferenceID(number);
            reference.setReferenceQual("BB");

            return reference;
        }

        return null;
    }

    @Override
    def AddressInfo getBuyingAddressInfo(Object poData){
        AddressInfo buyerInfo = new AddressInfo();

        String location = poData.LineItems.LineItem[0].QuantitiesSchedulesLocations.LocationQuantity.Location.text();
        if(location.length()>0){
            buyerInfo.setAddressTypeCode("BY");
            buyerInfo.setLocationCodeQualifier("92");
            buyerInfo.setAddressLocationNumber(location);
        }

        return  buyerInfo;
    }

    @Override
    def boolean includeOuterpackShipmentLine(){
        return true;
    }

    @Override
    def String getFOBPayCode(){
        return 'CC';
    }

    @Override
    def AddressInfo getShipFromAddressInfo(){
        AddressInfo info = new AddressInfo();
        info.setAddressLocationNumber("57811");
        info.setLocationCodeQualifier("92");

        return info;
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
        order.template = "dickswarehouse";
        order.setBusinessOrder(true);
    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
      //  order.setThirdPartyBilling("9V3W92");
       // order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");
    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
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
    void injectWorkOrder(Order order, Object po){
        if(isFutureShip){
            order.injectWorkOrder("JustBrands/Dick's Sporting Goods future work order. Date: "+futureShipDate+" OrderNum: " + order.orderNum, "Please fulfill this order. \n" +
                    "WARNING: This order requires specific labeling. Please see the most recent Dick's Sporting Goods Routing Guide for instructions."
            );
        }else{
            order.injectWorkOrder("JustBrands/Dick's Sporting Goods work order. OrderNum: " + order.orderNum, "Please fulfill this order. \n" +
                    "WARNING: This order requires specific labeling. Please see the most recent Dick's Sporting Goods Routing Guide for instructions."
            );
        }
    }

    // case 1083829 Maps future ship date to warehouse notes
    @Override
    def void doFinalStuffBeforeOrderSave(Order order) {
        if (warehouseNotes != "") {
            order.getShippingInfo().whse_notes = warehouseNotes;
        }
    }

    @Override
    void checkForFutureShipDate(Object po){
        po.Header.Date.each {date ->
            System.out.println(date.Date1.text());
            if(date.DateTimeQualifier1.text().equals("010")){
                warehouseNotes = "Do not ship before " + date.Date1.text();
                LocalDate now = LocalDate.now();
                LocalDate future = LocalDate.parse(date.Date1.text(), DateTimeFormatter.ISO_LOCAL_DATE);
                long days = ChronoUnit.DAYS.between(now,future);
                System.out.println(days);
                if(days>daysForFutureShip){
                    isFutureShip = true;
                    futureShipDate = date.Date1.text();
                }
            }
            if(date.DateTimeQualifier1.text().equals("037")&& !isFutureShip){
                warehouseNotes = "Do not ship before " + date.Date1.text();
                LocalDate now = LocalDate.now();
                LocalDate future = LocalDate.parse(date.Date1.text(), DateTimeFormatter.ISO_LOCAL_DATE);
                long days = ChronoUnit.DAYS.between(now,future);
                System.out.println(days);
                if(days>daysForFutureShip){
                    isFutureShip = true;
                    futureShipDate = date.Date1.text();
                }
            }
        }
    }
}
