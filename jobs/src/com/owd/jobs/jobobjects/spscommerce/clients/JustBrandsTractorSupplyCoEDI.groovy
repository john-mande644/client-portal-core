package com.owd.jobs.jobobjects.spscommerce.clients


import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
/**
 * Created by danny on 10/16/2018.
 */
class JustBrandsTractorSupplyCoEDI extends SPSCommerceBaseClient {
    {
        checkForFutureShip = true;
        vendorPartNumIsSku = true;
    }

    private static String DEFAULT_SHIP_METHOD = "UPS Ground";

    @Override
    def boolean ignoreTheUOM() {
        return true;
    }

    @Override
    def String getRoutingSequenceCode() {
        return "B";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
        if (order.getShipMethodName().contains("UPS")) {
            order.companyOverride = "TSC Distribution Center";
            order.address1Override = "100 Raines Drive";
            order.cityOverride = "Franklin";
            order.stateOverride = "KY";
            order.zipOverride = "42134";
            order.setThirdPartyBilling("87W6A8");
            order.setThirdPartyBillingContact("", "", "5401 Virginia Way", "", "Brentwood", "TN", "37027", "");

            order.getShippingAddress().setCompany_name("C/O Tractor Supply");
        }
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        method = carrierInformation.ServiceLevelCodes.ServiceLevelCode.text();

        if (method.length() == 0) {
            return DEFAULT_SHIP_METHOD;
        }

        if (spsCodeToOWDMethodMap.containsKey(method)) {
            return spsCodeToOWDMethodMap.get(method);
        } else {
            return DEFAULT_SHIP_METHOD;
        }
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
        return poData.Header.CarrierInformation.CarrierAlphaCode.text().trim()
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
        order.group_name = "tsc";
    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
    }

    @Override
    void injectWorkOrder(Order order, Object po) {
    }

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
