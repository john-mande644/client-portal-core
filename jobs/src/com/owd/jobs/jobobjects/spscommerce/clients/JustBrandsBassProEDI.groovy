package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient
import com.owd.jobs.jobobjects.spscommerce.beans.AddressInfo

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * Created by danny on 10/16/2018.
 */
class JustBrandsBassProEDI extends SPSCommerceBaseClient {
    {
        checkForFutureShip = true;
    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
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
        if (dropShipOrder) {
            order.template = "bassprodrop";
            order.group_name = "bassprods";
        } else {
            order.template = "bassprowarehouse";
        }
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
        //8768Y6 UPS accound
        //628753997 FedEx account

        if (dropShipOrder) {
            order.setThirdPartyBilling("348079425");
            order.setThirdPartyBillingContact("Bass Pro LLC", "Bass Pro LLC", "2500 E. Kearney", "", "Springfield", "MO", "65898", "4178735000");
        }
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        if (dropShipOrder) {
            return "FedEx Home Delivery"
        } else {
            return "LTL";
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
    def String getPackingMedium() {
        return 'CTN25';
    }

    @Override
    def AddressInfo getShipFromAddressInfo() {
        AddressInfo info = new AddressInfo();
        info.setAddressLocationNumber("DC7");

        return info;
    }

    @Override
    def void setDropShipFlag(Object po) {
        //Look for the words DROP SHIP in the NoteInformationField contents to deteremine dropship or stocking order
        println("hello")
        po.Header.Notes.each { note ->
            println(note.NoteInformationField.text());
            if (note.NoteInformationField.text().contains("DROP SHIP")) {
                dropShipOrder = true;

            }
        }
    }

    public String getShipByDate(Object po) {
        String s = "";
        po.Header.Date.each { date ->
            println(date.DateTimeQualifier1.text())
            if (date.DateTimeQualifier1.text().contains("010")) {
                println(date.Date1.text());
                s = date.Date1.text();
            }
        }
        return s;
    }

    @Override
    def int getVendorComplianceId(EdiSpsConfigdata config, Object po) {
        if (dropShipOrder)
            return 0;

        return config.getVendorComplianceFkey();
    }

    @Override
    void loadBusinessOrConsumerOrder(Order order) {
        if (!dropShipOrder) {
            order.business_order = true;
        }
    }

    @Override
    void injectWorkOrder(Order order, Object po) {
        if (!dropShipOrder) {
            if (isFutureShip) {
                order.injectWorkOrder("JustBrands/BassPro Future work order. Date: " + futureShipDate + " OrderNum: " + order.orderNum, "Please fulfill this order. \n" +
                        "\nPlease ensure all of the newest packaging for each SKU is sent on this order.\nPlease prepare this order with the following guidelines. Once prepared please assign back to the FS to get routing:\n" +
                        "Packing:\n" +
                        "-Only one Order and one SKU per case.\n" +
                        "-If Mixed SKU Cases are necessary the carton must be labeled “Mixed SKU Case” on all sides of the case in 2-inch lettering on a separate color label. Mixed carton labels can be typed up in Word and printed at the DC. The same SKU is not allowed to be scattered among multiple Mixed SKU Cases.\n" +
                        "-Packed case weight must not exceed 50 pounds.\n" +
                        "\n" +
                        "Labeling:\n" +
                        "-Position barcode of the  GS1-128 label on the lower right side, no closer than 1.25 inches from the edge of the bottom or the side.\n" +
                        "-GS1-128 label must be on the SMALLEST end of each carton on the right-hand corner.\n" +
                        "-Apply the GS1-128 Label on EVERY case\n" +
                        "-Case labels must face outward when stacked on the pallet.\n" +
                        "\n" +
                        "LTL Prep:\n" +
                        "-Pallets should not weigh more than 2,500 pounds.\n" +
                        "-Multiple Master Cases of the same SKU must be stacked together on the same pallet. If multiple SKUs must be shipped on one pallet, group same SKUs together.\n" +
                        "-Pallets should contain a Single Purchase Order.  If multiple PO’s must be shipped on one pallet, the individual PO’s must be grouped together.");

            } else {
                order.injectWorkOrder("JustBrands/BassPro work order. OrderNum: " + order.orderNum, "Please fulfill this order. \n" +
                        "\nPlease ensure all of the newest packaging for each SKU is sent on this order.\nPlease prepare this order with the following guidelines. Once prepared please assign back to the FS to get routing:\n" +
                        "Packing:\n" +
                        "-Only one Order and one SKU per case.\n" +
                        "-If Mixed SKU Cases are necessary the carton must be labeled “Mixed SKU Case” on all sides of the case in 2-inch lettering on a separate color label. Mixed carton labels can be typed up in Word and printed at the DC. The same SKU is not allowed to be scattered among multiple Mixed SKU Cases.\n" +
                        "-Packed case weight must not exceed 50 pounds.\n" +
                        "\n" +
                        "Labeling:\n" +
                        "-Position barcode of the  GS1-128 label on the lower right side, no closer than 1.25 inches from the edge of the bottom or the side.\n" +
                        "-GS1-128 label must be on the SMALLEST end of each carton on the right-hand corner.\n" +
                        "-Apply the GS1-128 Label on EVERY case\n" +
                        "-Case labels must face outward when stacked on the pallet.\n" +
                        "\n" +
                        "LTL Prep:\n" +
                        "-Pallets should not weigh more than 2,500 pounds.\n" +
                        "-Multiple Master Cases of the same SKU must be stacked together on the same pallet. If multiple SKUs must be shipped on one pallet, group same SKUs together.\n" +
                        "-Pallets should contain a Single Purchase Order.  If multiple PO’s must be shipped on one pallet, the individual PO’s must be grouped together.");
            }
        }
    }

    // case 888283 Inserts sku
    // case 1083829 Maps future ship date to warehouse notes
    @Override
    def void doFinalStuffBeforeOrderSave(Order order) {
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)

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
