package com.owd.core.business.client;

import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.PackageRate;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.xml.OrderXMLDoc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 27, 2003
 * Time: 12:48:07 PM
 * To change this template use Options | File Templates.
 */
public class DefaultClientPolicy implements ClientPolicy {
private final static Logger log =  LogManager.getLogger();

    public Client getClient() {
        return client;
    }

    public void setSignatureRequired(String remoteSourceKey, Order order) 
    {
            order.getShippingInfo().ss_proof_delivery = "0";
    }
       public String getOWDShipMethodForExternalShipMethodName(String remoteSourceKey, String remoteMethod, Order order)
    {
       if(remoteSourceKey.equals("magento"))
        {
            if (remoteMethod.toUpperCase().contains("STANDARD") || remoteMethod.toUpperCase().contains("FREE")  || (order.clientID.equals("476") && remoteMethod.toUpperCase().contains("FLAT RATE"))) {

                try
                {
                OrderRater rater = new OrderRater(order);
                    rater.setCalculateKitWeights(true);
                    List<String> alternateShipMethodList = new ArrayList<String>();
                    alternateShipMethodList.add("TANDATA_USPS.USPS.FIRST");
                    alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                    alternateShipMethodList.add("TANDATA_USPS.USPS.I_FIRST");
                    alternateShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
                    alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.STD");
                    alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.GND");
                    alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                    alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");

                  return rater.selectBestWay(alternateShipMethodList);

                }catch(Exception ex)
                {
                    return "No valid rate returned";
                }
            }
        }
        return remoteMethod;

    }
       public  void sendCustomerEmailConfirmation(Order order) {
           
       }
    protected boolean manualMode = false;


    public String translateRemoteSkuToOwdSku(String remoteSourceKey, String remoteSku)
    {
        return remoteSku;
    }
    public void setSignatureRequired(Order order) throws Exception
    {
        if(order.getShippingInfo().ss_proof_delivery.equals("1")){
            log.debug("We have a proof, lets keep it shall we");
        }else{
            order.getShippingInfo().ss_proof_delivery = "0";
        }

    }
    public static void main (String args[]) throws Exception
    {
       Order order = new Order("390");
        order.addLineItem("400-FOURTH","1","1","1","1","1","1");
        order.getShippingAddress().address_one="123 main";
        order.getShippingAddress().city="yes";
        order.getShippingAddress().state="WA";
        order.getShippingAddress().zip="6148";
        order.getShippingAddress().country="AUSTRALIA";

        ClientPolicy policy = new DefaultClientPolicy();

        List<ShippingOption> options = policy.getShipOptions(order,0.00f);
        for (ShippingOption option:options)
        {
            log.debug(option);
        }
    }
    public void setManualEntryMode(boolean yes)
    {
        manualMode = yes;
    }
    public boolean hasManualEntryMode(){ return false;}

    public List getCustomOrderFields(Order order) {
        return new ArrayList();
    }

    public void handleCustomOrderFields(Order order, List fields) {

    }

    public void updateCustomOrderFields(Order order, HttpServletRequest request, List fields) {
        for (int i = 0; i < fields.size(); i++) {
            String val = request.getParameter(((CustomField) fields.get(i)).getFieldName());
            if (val != null) {
                ((CustomField) fields.get(i)).setCurrentValue(val);
            }
        }
    }

      public void addLineItemToOrder(Order order, String sku, String description, String price, String quantity, String longdesc) throws Exception {


        order.addLineItem(sku, quantity, price, "0.00", description, "", "",longdesc);


    }
   public void sendFlexPayDeclineEmail(OrderStatus order)
    {
       
    }

   public String getFlexPayStatement(Order order){ return "";}

    public String getShipOptionName(String optionType, List shipOptions) {

        for (int i = 0; i < shipOptions.size(); i++) {
            if (((ShippingOption) shipOptions.get(i)).code.equals(optionType)) {
                return ((ShippingOption) shipOptions.get(i)).name;
            }
        }

        return "No ship method found for code " + optionType;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    protected Client client = null;

    public boolean allowUnitPriceEditing(Order order) {
        return true;
    }

    public boolean allowManualDiscountEntry(Order order) {
        return true;

    }

    public boolean allowShipPriceEditing(Order order) {
        return true;
    }

    public boolean allowSalesTaxEditing(Order order) {
        return true;
    }

    public void sendNotificationMessage(Order order, String subject, String message) {

    }

    public DefaultClientPolicy(Client client) {
        setClient(client);


    }

    public DefaultClientPolicy() {

    }

    public Order createInitializedOrder()  {

        Order order = null;

        try{
           order =  new Order("" + getClient().client_id);
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
        order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
        order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
        order.backorder_rule = OrderXMLDoc.kBackOrderAll;
        order.order_type = "Direct Entry";
        order.preserveShippingCosts = true;

        return order;

    }

    public float getSalesTaxValue(Order order) throws Exception {
        return 0.00f;
    }

    public String getShippingMethod(Order order, String shipType, List shipOptions) throws Exception {
        return getShipOptionForType(shipType, shipOptions).name;
    }

    public ShippingOption getShipOptionForType(String shipType, List shipOptions) throws Exception {
        for (int i = 0; i < shipOptions.size(); i++) {
            if (((ShippingOption) shipOptions.get(i)).code.equals(shipType)) {
                return ((ShippingOption) shipOptions.get(i));
            }
        }

        throw new Exception("Shipping Option " + shipType + " not found!");
    }

    public float getShippingCost(Order order, String shipType, List shipOptions) throws Exception {


        ShippingOption option = getShipOptionForType(shipType, shipOptions);

        return getShippingCost(order, option);

    }

    public float getShippingCost(Order order, ShippingOption shipOption) throws Exception {


        return OWDUtilities.roundFloat(shipOption.ratedCost);

    }

    public List getShipOptions(Order order, float defaultCost) {
        //log.debug("Getting shipment options");

        List options = new ArrayList();
        if (defaultCost < 0) defaultCost = 0.00f;


        try {

            OrderRater rater = new OrderRater(order);
            rater.setClientId(order.getClientID());
            rater.setCalculateKitWeights(true);
            String location = order.getFacilityCode();
            System.out.println(location);
            if(location.length()==0){
                location="DC1";
                System.out.println("Doing DC1 location");
            }
            rater.rate(location);


            java.text.DecimalFormat decform = new java.text.DecimalFormat("#,###,##0.00");

            for (PackageRate currShipment:rater.theResponse)
            {

                if (currShipment.getErrorCode()==0) {

                    double shiprate = currShipment.getFinalRate();


                    ShippingOption so = new ShippingOption();
                    so.name = currShipment.getMethodName();
                    so.code = currShipment.getMethodCode();
                    so.desc = "";
                    try {
                        so.ratedCost = (OWDUtilities.roundFloat((float)shiprate));
                        so.customerCost = getShippingCost(order, so);

                    } catch (Exception uex) {
                    }
                    if (so.customerCost >= 0  && (!(so.name.toUpperCase().indexOf("FEDEX") >= 0 && order.getShippingAddress().isPOAPO())) && (!(so.name.toUpperCase().indexOf("UPS") >= 0 && order.getShippingAddress().isPOAPO()))) {
                        options.add(so);
                    }
                }
            }
            ShippingOption so = new ShippingOption();
            so.name = "LTL";
            so.code = "TANDATA_LTL.LTL.LTL";

            ShippingOption so1 = new ShippingOption();
            so1.name = "Economy";
            so1.code = "COM_OWD_FLATRATE_ECONOMY";
            options.add(so1);
            ShippingOption so2 = new ShippingOption();
            so2.name = "Ground";
            so2.code = "COM_OWD_FLATRATE_GROUND";
            options.add(so2);
            ShippingOption so3 = new ShippingOption();
            so3.name = "Standard Priority";
            so3.code = "COM_OWD_FLATRATE_STANDARD_GROUND";
            options.add(so3);
            ShippingOption so4 = new ShippingOption();
            so4.name = "2 Day";
            so4.code = "COM_OWD_FLATRATE_2DA";
            options.add(so4);
            ShippingOption so5 = new ShippingOption();
            so5.name = "Overnight";
            so5.code = "COM_OWD_FLATRATE_NDA";
            options.add(so5);
            ShippingOption so6 = new ShippingOption();
            so6.name = "International Economy";
            so6.code = "COM_OWD_FLATRATE_INTL_ECONOMY";
            options.add(so6);
            ShippingOption so7 = new ShippingOption();
            so7.name = "International Standard";
            so7.code = "COM_OWD_FLATRATE_INTL_STND";
            options.add(so7);
            ShippingOption so8 = new ShippingOption();
            so8.name = "International Expedited";
            so8.code = "COM_OWD_FLATRATE_INTL_EXPD";
            options.add(so8);
            ShippingOption so9 = new ShippingOption();
            so9.name = "International Priority DDP";
            so9.code = "COM_OWD_FLATRATE_INTL_PRIDDP";
            options.add(so9);

            ShippingOption so10 = new ShippingOption();
            so10.name = "International Priority DDU";
            so10.code = "COM_OWD_FLATRATE_INTL_PRIDDU";
            options.add(so10);

            ShippingOption so11 = new ShippingOption();
            so11.name = "Media Mail";
            so11.code = "COM_OWD_FLATRATE_MM";
            options.add(so11);

            ShippingOption so12 = new ShippingOption();
            so12.name = "Passport Priority DDU";
            so12.code = "CONNECTSHIP_GLOBAL.APC.PRIDDUDC";
            options.add(so12);

            ShippingOption so13 = new ShippingOption();
            so13.name = "Passport Priority DDP";
            so13.code = "CONNECTSHIP_GLOBAL.APC.PRIDDPDC";
            options.add(so13);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return options;

    }

    public Map getPaymentOptions() throws Exception {
        Map aMap = new TreeMap();
        if (client.pp_proc != null && client.pp_proc.indexOf("payment") > 0) {
            aMap.put("CC", "Credit/Debit Card");
        }
        if(null!=client.do_echeck){
        if(client.do_echeck.equals("1") && client.check_proc.indexOf("payment")>0){
            aMap.put("ECK","E-Check");
        }
        }
        aMap.put("CK", "Check/Mail (Hold for Release)");
        aMap.put("FREE", "No charge - ship without payment");
        aMap.put("Custom","Custom (Put Value in Credit Card Account)");

        return aMap;
    }

    public void addLineItemToOrder(Order order, String sku, String description, String price, String quantity) throws Exception {


        order.addLineItem(sku, quantity, price, "0.00", description, "", "");


    }

    public void updateLineItemsAfterItemChange(Order anOrder) {

    }

    public void saveNewOrder(Order order, boolean testing) throws Exception {
        setSignatureRequired(order);
        order.saveNewOrder(OrderUtilities.kRequirePayment, testing);

    }




}
