package com.owd.jobs.clients;

import com.owd.core.business.order.Order;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.retailops.RetailOpsApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 8/30/2018.
 */
public class JustBrandsRetailOpsImportJob extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args){

        run();
        /*
        RetailOpsApi api  = new RetailOpsApi(626, "1975", "u9OLdvwM-Nulz3XwDKWn7yqY1XRxL3HEzZ0yQ2LTH");
        List<String> emails = new ArrayList<>();
        emails.add("dnickels@owd.com");
        api.getAsns(false, emails, 3);*/

    }

    // case 888283 insert sku by group names
    public void insertSkus (Order order)  throws Exception {
        if(order.getGroupName().equalsIgnoreCase( "Shopify ActionHeat")||
                order.getGroupName() .equalsIgnoreCase("Aabaco Warming Store") ||
                order.getGroupName().equalsIgnoreCase("Aabaco Cooling Store")){
            order.addInsertItemIfAvailable("ah-retail-insert", 1);
        }

        if(order.getGroupName().equalsIgnoreCase("Menards") ||
                order.getGroupName().equalsIgnoreCase("Channel Advisor") ||
                order.getGroupName().equalsIgnoreCase("CA Merchant Fulfilled") ||
                order.getGroupName().equalsIgnoreCase("Customer Service")){
            order.addInsertItemIfAvailable("ah-dropship-insert", 1);
        }
    }

    public void internalExecute() {


        RetailOpsApi api  = new RetailOpsApi(626, "1975", "u9OLdvwM-Nulz3XwDKWn7yqY1XRxL3HEzZ0yQ2LTH"){

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception {
                order.address1Override="3791 MAIN ST";
                order.cityOverride="Philadelphia";
                order.stateOverride="PA";
                order.zipOverride="19127-2107";
                order.setOrderType("retailOps");
                insertSkus(order);

            }
            @Override
            public String translateRetailOpsShipMethod(String retailOpsName, boolean hasLTLItems, Order order) throws Exception{
                Float weight = order.getEstimatedTotalLineItemWeight();

                if(retailOpsName.equalsIgnoreCase("Canada-$65 Expedited")){
                    if(weight<1){
                        return "USPS First-Class Mail International";
                    }else{
                        return "USPS Priority Mail International";
                    }
                }

                if(retailOpsName.equalsIgnoreCase("FedEx Ground ")){
                    return "FedEx Ground";
                }

                if(retailOpsName.equalsIgnoreCase("USPS Priority Shipping")){
                    return "USPS Priority Mail";
                }
                if(retailOpsName.equalsIgnoreCase("USPS First Class Mail")){
                    return "USPS First-Class Mail";
                }
                if(retailOpsName.equalsIgnoreCase("2nd Day Air")||retailOpsName.equalsIgnoreCase("FedEx 2nd Day Air")||retailOpsName.equalsIgnoreCase("AMZ - Second Day")){
                    return "FedEx 2Day";
                }

                if(retailOpsName.equalsIgnoreCase("2nd Day")||retailOpsName.equalsIgnoreCase("FedEx 2nd Day")||retailOpsName.equalsIgnoreCase("AMZ - Second Day")){
                    return "FedEx 2Day";
                }

                if(retailOpsName.equalsIgnoreCase("Next Day Air")|| retailOpsName.equalsIgnoreCase("Next Day")||retailOpsName.equalsIgnoreCase("FedEx Next Day")||retailOpsName.equalsIgnoreCase("Next Day Air AM")){
                    return "FedEx Standard Overnight";
                }

                if(retailOpsName.equalsIgnoreCase("UPS International")){
                    return "UPS Expedited";
                }
                if(retailOpsName.equalsIgnoreCase("International")){
                    if(order.getTotalOrderCost()>300f){
                        return "UPS Expedited";
                    }

                    if(weight<1){
                        return "USPS First-Class Mail International";
                    }else{
                        return "USPS Priority Mail International";
                    }

                }
                if(retailOpsName.equalsIgnoreCase("Amazon-Canada")){
                    if(weight<1){
                        return "USPS First-Class Mail International";
                    }else{
                        return "USPS Priority Mail International";
                    }
                }
                if(retailOpsName.equalsIgnoreCase("First Class International")){
                    return  "USPS First-Class Mail International";
                }

                if(retailOpsName.equalsIgnoreCase("Standard")||retailOpsName.equalsIgnoreCase("AMZ - Standard")||retailOpsName.equalsIgnoreCase("AMZ - Free Economy")){
                    if(order.getShippingAddress() != null && order.getShippingAddress().state != null && (order.getShippingAddress().state.equalsIgnoreCase("AK")||
                            order.getShippingAddress().state.equalsIgnoreCase("PR")||
                            order.getShippingAddress().state.equalsIgnoreCase("GU")||
                            order.getShippingAddress().state.equalsIgnoreCase("HI"))){
                        return "USPS Priority Mail";
                    }

                    if(order.getTotalOrderCost()>=399f || weight>=21f){
                        log.debug("Weight: "+ order.getEstimatedTotalLineItemWeight());
                        return "FedEx Ground";
                    }
                    if(weight>=1){
                        return "FedEx SmartPost Parcel Select";
                    }
                    if(weight<1){
                        return "USPS First-Class Mail";
                    }



                }

                // case 900537
                if (retailOpsName.equalsIgnoreCase("AMZ - Standard")){
                    if (order.getIsUS()){
                        return "FedEx SmartPost Parcel Select";
                    }else {
                        return "USPS First-Class Mail International";
                    }
                }

                /**
                 * Sean Chen added on 02/27/2019
                 * Map AMZ- 3-Day to FedEx Ground.
                 */
                if(retailOpsName.equalsIgnoreCase("AMZ 3-Day")||
                        retailOpsName.equalsIgnoreCase("AMZ- 3-Day") ||
                        retailOpsName.equalsIgnoreCase("3-Day Select") ||
                        retailOpsName.equalsIgnoreCase("3 Day Select")){

                    // when shipping to these states, use FedEx ground
                    final String states = "PA;NJ;MD;DE;CT;RI;MA;NH;NY;VT;ME;VA;NC;SC;GA;FL;WV;OH;KY;TN;AL;MS;LA;AR;MO;IA;MN;MI;IL;WI;TX;OK;KS";
                    if(states.contains(order.getShippingAddress().state.toUpperCase())){
                        return "FedEx Ground";
                    } else{
                        return "FedEx Express Saver";
                    }
                }

                return retailOpsName;
                // throw new Exception("Unable to get Valid Shipping method from : "+retailOpsName);
            }
        };

        //import orders
        api.setImportPrices(true);
        api.setChannelNametoGroupName(true);
        api.importCurrentOrders();
        //push inventory levels
        List<String> emails = new ArrayList<>();
        // emails.add("dnickels@owd.com");
//        api.getAsns(false,emails,3);
//        api.reportReceipts();
        api.updateInventory();
    }

}
