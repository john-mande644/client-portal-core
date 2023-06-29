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
 * Created by danny on 2/11/2021.
 */
class BlueTeesGolfDicksWarehouseEDI extends SPSCommerceBaseClient{

    {
        checkForFutureShip = true;
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
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception{
        /*if(null == method ||method.length()==0){
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
        throw new Exception("Unable to determine ship method for "+ method);*/
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
        if(isFutureShip){
            order.injectWorkOrder("BlueTees/Dick's Sporting Goods future work order. Date: "+futureShipDate+" OrderNum: " + order.orderNum, "Please fulfill this order. \n" +
                    "WARNING: This order requires specific labeling. Please see the most recent Dick's Sporting Goods Routing Guide for instructions."
            );
        }else{
            order.injectWorkOrder("BlueTees/Dick's Sporting Goods work order. OrderNum: " + order.orderNum, "Please fulfill this order. \n" +
                    "WARNING: This order requires specific labeling. Please see the most recent Dick's Sporting Goods Routing Guide for instructions."
            );
        }
    }

    // 1211115 - add DOM support for Blue Tees
    // 1083829 - check for future ship date
    @Override
    def void doFinalStuffBeforeOrderSave(Order order){
        order.facilityCode = "CLOSEST";
        order.facilityPolicy = "CLOSEST";

        if (warehouseNotes != "") {
            order.getShippingInfo().whse_notes = warehouseNotes;
        }
    }

    // 1083829 - check for future ship date
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
