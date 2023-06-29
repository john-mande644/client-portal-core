package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * Created by danny on 10/16/2018.
 */
class JustBrandsScheelsEDI extends SPSCommerceBaseClient {
    {
        outboundShipMap.put("UPS Ground", "SG");
        spsCodeToOWDMethodMap.put("SG", "UPS Ground");
        spsCodeToOWDMethodMap.put("ND", "UPS Next Day Air Saver")
        outboundShipMap.put("UPS Next Day Air Saver", "ND")
        spsCodeToOWDMethodMap.put("SE", "UPS 2nd Day Air")
        checkForFutureShip = true;
    }

    @Override
    def boolean ignoreTheUOM() {
        return true;
    }

    @Override
    def String getRoutingSequenceCode() {
        return "B";
    }

    @Override
    void loadOrderTemplate(Order order) {
        order.template = "scheels";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
        if (order.group_name.equalsIgnoreCase("Scheels")){
            order.setThirdPartyBilling("A03T88");
        }else{
            order.setThirdPartyBilling("E58611");
        }

    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        method = carrierInformation.ServiceLevelCodes.ServiceLevelCode.text();

        if (method.length() == 0) {
            return "UPS Ground";
        }

        if (spsCodeToOWDMethodMap.containsKey(method)) {
            return spsCodeToOWDMethodMap.get(method);
        }

        throw new Exception("Unable to determine ship method for " + method);
    }

    @Override
    void loadLabelInfo(Order order) {
    }

    @Override
    def String getShipQtyUOM(Object poLineItem) {
        return "EA";
    }

    @Override
    String getItemStatusCode(Object poLineItem) {
        return "AC"
    }

    @Override
    def String getScacCode(Object poData, String shipMethod) {
        return "HD";
    }

    @Override
    def String getStatusCode(String shipMethod) {
        if (shipMethod.contains("Ground")) {
            return "G2"
        }

        return "DS";
    }

    @Override
    def boolean includeVendorInShipmentHeader() {
        return true;
    }

    @Override
    def void setGroupName(Order order, Object po) {
        if (po.Header.OrderHeader.PurchaseOrderTypeCode.text().equals("SA")) {
            order.group_name = "scheels-stocking";
            // case 1134326 override third party billing for stocking orders
            order.setThirdPartyBilling("A03T88");
            order.addTag(TagUtilities.kVendorComplianceIDReference, "10");
        }
    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
    }

    @Override
    void injectWorkOrder(Order order, Object po) {
        if (isFutureShip) {
            order.injectWorkOrder("JustBrands/Scheels's future work order. Date: " + futureShipDate + " OrderNum: " + order.orderNum, "Please release this order in time for it to ship by " + futureShipDate + ". \n" +
                    ""
            );
        }
    }

    // case 888283 Inserts sku
    @Override
    def void doFinalStuffBeforeOrderSave(Order order) {
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)

        // case 1083829 Maps future ship date to warehouse notes
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
