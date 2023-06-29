package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient


class BlueTeesGolfGolfWarehouseEDI extends SPSCommerceBaseClient{

    {
        checkForFutureShip = true;
        vendorPartNumIsSku=true;
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
        order.template = "tgw";
    }
    @Override
    void loadThirdPartyBillingInfo(Order order){

        order.setThirdPartyBilling("341884659");
        order.setThirdPartyBillingContact("","The Golf Warehouse","8908 East 34th Street North","","Wichita","KS","67226","");

    }
    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception{

        method = carrierInformation.ServiceLevelCodes.ServiceLevelCode.text();

        if(method.length()==0){
            return "LTL";
        }
        if (spsCodeToOWDMethodMap.containsKey(method)){
            return spsCodeToOWDMethodMap.get(method);
        }


        throw new Exception("Unable to determine ship method for "+ method);
    }
    @Override
    void loadLabelInfo(Order order){
        /*   order.companyOverride = "DICK'S Sporting Goods";
           order.nameOverride = "Returns Department";
           order.address1Override = "7603 Trade Port Drive";
           order.cityOverride = "Louisville";
           order.stateOverride = "KY";
           order.zipOverride = "40258";*/


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

    @Override
    def void setGroupName(Order order,Object po){
        if(po.Header.OrderHeader.PurchaseOrderTypeCode.text().equals("SA")){
            order.group_name =  "tgw";
            order.addTag(TagUtilities.kVendorComplianceIDReference, "10");

        }
    }
    @Override
    def String loadBackorderRule(){
        return OrderXMLDoc.kBackOrderAll;
    }

    @Override
    void injectWorkOrder(Order order, Object po){
        if(isFutureShip) {
            order.injectWorkOrder("BlueTeesGolf/Golf Warehouse future work order. Date: "+futureShipDate+" OrderNum: " + order.orderNum, "Please release this order in time for it to ship by "+futureShipDate+". \n" +
                    ""
            );
        } else {
            order.injectWorkOrder(
                    "Blue Tees Golf - Golf Warehouse - PO#"  + order.po_num + " // OWD - "+ order.order_refnum,
                    "Hello,\n\n"+
                    "Please pack through the pack station and apply UCC-128 labels.\n"+
                    "Do not mix SKUs in a carton.\n"+
                    "Attach the packing list to the outside of one of the cartons(in a clear plastic sleeve).\n"+
                    "Please review the routing guide."
            );
        }
    }

    // 1211115 - add DOM support for Blue Tees
    @Override
    def void doFinalStuffBeforeOrderSave(Order order){
        order.facilityCode = "CLOSEST";
        order.facilityPolicy = "CLOSEST";
    }


}
