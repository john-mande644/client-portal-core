package com.owd.jobs.jobobjects.spscommerce.clients

import com.owd.core.business.order.Order
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient

/**
 * Created by danny on 10/16/2018.
 */
class JustBrandsTargetEDI extends SPSCommerceBaseClient {
    {
        includeVendorPartNumInAck = true;
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
        order.template = "target";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
        if (order.getShippingMethodName().contains("FedEx")) {
            order.setThirdPartyBilling("628753997")
        }
        if (order.getShippingMethodName().contains("UPS")) {
            order.setThirdPartyBilling("8768Y6")
        }
    }

    @Override
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception {
        method = carrierInformation.CarrierAlphaCode.text();

        String routing = carrierInformation.CarrierRouting.text();
        String serviceLevel = carrierInformation.ServiceLevelCodes.ServiceLevelCode.text();

        if (method.equalsIgnoreCase("FDE")) {
            if (routing.equalsIgnoreCase("NDS")) {
                return "FedEx Standard Overnight"
            }
        }

        if (method.equalsIgnoreCase("FDEG")) {
            if (routing.equalsIgnoreCase("NS")) {

                if (serviceLevel.equalsIgnoreCase("G2")) {
                    return "FedEx Ground";
                }
            }

            if (routing.equalsIgnoreCase("HD")) {
                return "FedEx Home Delivery";
            }
        }

        if (method.equalsIgnoreCase("USPS")) {
            return "USPS First-Class Mail";
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
    def String getCarrierTransMethodCode(OwdOrder order) {
        return order.shipinfo.carrService.equals('LTL') ? 'M' : 'U';
    }

    // case 888283 Inserts sku
    @Override
    void doFinalStuffBeforeOrderSave(Order order) {
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }
}
