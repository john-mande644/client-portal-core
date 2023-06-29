package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

/**
 * Created by danny on 10/16/2018.
 */
class JustBrandsHomeDepotEDI extends SPSCommerceBaseClient{

    @Override
    def String loadBackorderRule() {
        return OrderXMLDoc.kBackOrderAll;
    }

    @Override
    void loadOrderTemplate(Order order){
        order.template = "homedepot";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
        order.setThirdPartyBilling("975W8X");
        order.setThirdPartyBillingContact(" Home Depot Headquarters","","2455 Paces Ferry Rd.","","Atlanta","GA","30339","6058457172");
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation){
        return "UPS Ground";
    }

    @Override
    def String getShipQtyUOM(Object poLineItem){
        return "EA";
    }

    @Override String getItemStatusCode(Object poLineItem){
        return  "AC"
    }

    @Override
    void loadLabelInfo(Order order){
        order.companyOverride = "Homedepot.com";
        order.nameOverride = "Homedepot.com";
        order.address1Override = "3791 Main ST";
        order.cityOverride = "Philadelphia";
        order.stateOverride = "PA";
        order.zipOverride = "19127";
        order.phoneOverride = "800-430-3376"
    }

    // case 888283 Inserts sku
    @Override
    void doFinalStuffBeforeOrderSave(Order order){
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }
}
