package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

class JustBrandsRetailConceptsDropShipEDI extends SPSCommerceBaseClient{
    {
        checkForFutureShip = true;
        includeOWDDunsLocationNumberASN = true;
    }

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
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
        if (isDropShipOrder()) {
            order.template = "retailConcepts";
        }
    }
    @Override
    void loadThirdPartyBillingInfo(Order order){
        if (isDropShipOrder()) {
            order.setThirdPartyBilling("782720236");
            order.setThirdPartyBillingContact("JustBrand/Retail Concepts", "JustBrand/Retail Concepts", "10225 Mula Rd Ste 120", "", "Stafford", "TX", "77477", "");
        }
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception{
        if (!isDropShipOrder()) {
            return "LTL";
        }

        switch (carrierInformation.CarrierAlphaCode.text()) {
            case "FDEG":
            case "FDXS":
                return "FedEx Ground";
            case "FDEN":
                if (method.equalsIgnoreCase("FEDEX_EXPRESS_SAVER"))
                    return "FedEx Express Saver";
                return "FedEx 2Day";
            case "FDH":
                return "FedEx Home Delivery";
            case "FXFE":
                if (method.equalsIgnoreCase("priority_overnight"))
                    return "FedEx Priority Overnight";
                return "FedEx Standard Overnight";
            case "USPS":
                return "USPS Priority Mail";
        }

        throw new Exception("Unable to determine ship method for "+ method);
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
    void doFinalStuffBeforeOrderSave(Order order) {
        if (!isDropShipOrder()) {
            order.setGroupName("RetailConcepts-Stocking");
        }
    }
}
