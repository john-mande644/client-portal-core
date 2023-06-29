package com.owd.jobs.archives;

import com.owd.core.business.order.Order;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.retailops.RetailOpsApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by danny on 8/30/2018.
 */
public class LTHJKTRetailOpsImportJob extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args){

run();

    }

    public void internalExecute() {


        RetailOpsApi api  = new RetailOpsApi(631, "1", "G0oDZxjz-yjPYLIwoIOO9Xjhx4h2EgjCqdbMZmn1O"){

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception {
              /*  order.address1Override="3791 MAIN ST";
                order.cityOverride="Philadelphia";
                order.stateOverride="PA";
                order.zipOverride="19127-2107";*/
                if(order.group_name.length()>0 && !order.group_name.equalsIgnoreCase("Shopify")){
                    order.business_order = true;
                }
                if(order.group_name.equalsIgnoreCase("Azalea") &&order.getShipMethodName().contains("UPS")){
                    order.setThirdPartyBilling("754V6R");
                }

            }
            @Override
            public String translateRetailOpsShipMethod(String retailOpsName, boolean hasLTLItems, Order order) throws Exception {
                Float weight = order.getEstimatedTotalLineItemWeight();
                if(retailOpsName.equalsIgnoreCase("3rd Party")){
                    //map based upon group name
                    if(order.group_name.equalsIgnoreCase("Azalea")){
                        return "UPS Ground";
                    }
                }

                if(retailOpsName.equalsIgnoreCase("Expedited")||retailOpsName.equalsIgnoreCase("Intl Expedited")){
                    return "International Expedited";
                }

                if (retailOpsName.equalsIgnoreCase("USPS Standard")) {
                    return "Ground";
                }

                if (retailOpsName.equalsIgnoreCase("USPS Priority Shipping")) {
                    return "Ground";
                }
                if (retailOpsName.equalsIgnoreCase("USPS First Class Mail")) {
                    return "Ground";
                }
               /* if (retailOpsName.equalsIgnoreCase("2nd Day Air") || retailOpsName.equalsIgnoreCase("FedEx 2nd Day Air") || retailOpsName.equalsIgnoreCase("AMZ - Second Day")) {
                    return "FedEx 2Day";
                }

                if (retailOpsName.equalsIgnoreCase("Next Day Air") || retailOpsName.equalsIgnoreCase("FedEx Next Day") || retailOpsName.equalsIgnoreCase("Next Day Air AM")) {
                    return "FedEx Standard Overnight";
                }

                if (retailOpsName.equalsIgnoreCase("UPS International")) {
                    return "UPS Expedited";
                }
                if (retailOpsName.equalsIgnoreCase("International")) {
                    if (order.getTotalOrderCost() > 300f) {
                        return "UPS Expedited";
                    }

                    if (weight < 1) {
                        return "USPS First-Class Mail International";
                    } else {
                        return "USPS Priority Mail International";
                    }

                }
                if (retailOpsName.equalsIgnoreCase("First Class International")) {
                    return "USPS First-Class Mail International";
                }

                if (retailOpsName.equalsIgnoreCase("Standard") || retailOpsName.equalsIgnoreCase("AMZ - Standard") || retailOpsName.equalsIgnoreCase("AMZ - Free Economy")) {
                    if (order.getShippingAddress().state.equalsIgnoreCase("AK") ||
                            order.getShippingAddress().state.equalsIgnoreCase("PR") ||
                            order.getShippingAddress().state.equalsIgnoreCase("GU") ||
                            order.getShippingAddress().state.equalsIgnoreCase("HI")) {
                        return "USPS Priority Mail";
                    }

                    if (order.getTotalOrderCost() >= 399f || weight >= 21f) {
                        log.debug("Weight: " + order.getEstimatedTotalLineItemWeight());
                        return "FedEx Ground";
                    }
                    if (weight >= 1) {
                        return "FedEx SmartPost Parcel Select";
                    }
                    if (weight < 1) {
                        return "USPS First-Class Mail";
                    }


                }*/





                return retailOpsName;
               // throw new Exception("Unable to get Valid Shipping method from : "+retailOpsName);
            }
        };

        //import orders
        api.setImportPrices(true);
        api.setChannelNametoGroupName(true);
        api.importCurrentOrders();
      //  api.getAsns(false);
        api.reportReceipts();
        //push inventory levels
        api.updateInventory();
    }

}
