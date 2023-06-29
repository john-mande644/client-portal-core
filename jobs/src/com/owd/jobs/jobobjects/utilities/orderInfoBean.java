package com.owd.jobs.jobobjects.utilities;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.business.order.Order;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Apr 23, 2007
 * Time: 10:39:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class orderInfoBean {
private final static Logger log =  LogManager.getLogger();
    private String BillingFirstName;
    private String BillingLastName;
    private String BillingCompany;
    private String BillingAddress1;
    private String BillingAddress2;
    private String BillingCity;
    private String BillingState;
    private String BillingZip;
    private String BillingCountry;
    private String BillingEmail;
    private String BillingPhone1;
    private String BillingPhone2;
    private String BillingFax;
    private String ShiptoBillingAddress = "";
    private String ShippingFirstName;
    private String ShippingLastName;
    private String ShippingCompany;
    private String ShippingAddress1;
    private String ShippingAddress2;
    private String ShippingCity;
    private String ShippingState;
    private String ShippingZip;
    private String ShippingCountry;
    private String ShippingEmail;
    private String ShippingPhone1;
    private String ShippingPhone2;
    private String ShippingFax;
    private String SubTotal;
    private String ShippingandHandling;
    private String ShippingMethod;
    private String Tax;
    private String GrandTotal;
    private String OrderComments;
    private String OrderDate;
    private List<lineItemBean> Items;
    private String orderRef;
    private String customerNumber;
    private String clientOrderNum;
    private String insuranceRequested;

    private String isGift;
    private String giftMessage;

    private String orderType = "Automatic Import";

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getInsuranceRequested() {
        return insuranceRequested;
    }

    public void setInsuranceRequested(String insuranceRequested) {
        this.insuranceRequested = insuranceRequested;
    }

    public String getGift() {
        return isGift;
    }

    public void setGift(String gift) {
        isGift = gift;
    }

    public String getGiftMessage() {
        return giftMessage;
    }

    public void setGiftMessage(String giftMessage) {
        this.giftMessage = giftMessage;
    }

   

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getClientOrderNum() {
        return clientOrderNum;
    }

    public void setClientOrderNum(String clientOrderNum) {
        this.clientOrderNum = clientOrderNum;
    }

    public String getBillingFirstName() {
        return BillingFirstName;
    }

    public void setBillingFirstName(String billingFirstName) {
        BillingFirstName = billingFirstName;
    }

    public String getBillingLastName() {
        return BillingLastName;
    }

    public void setBillingLastName(String billingLastName) {
        BillingLastName = billingLastName;
    }

    public String getBillingCompany() {
        return BillingCompany;
    }

    public void setBillingCompany(String billingCompany) {
        BillingCompany = billingCompany;
    }

    public String getBillingAddress1() {
        return BillingAddress1;
    }

    public void setBillingAddress1(String billingAddress1) {
        BillingAddress1 = billingAddress1;
    }

    public String getBillingAddress2() {
        return BillingAddress2;
    }

    public void setBillingAddress2(String billingAddress2) {
        BillingAddress2 = billingAddress2;
    }

    public String getBillingCity() {
        return BillingCity;
    }

    public void setBillingCity(String billingCity) {
        BillingCity = billingCity;
    }

    public String getBillingState() {
        return BillingState;
    }

    public void setBillingState(String billingState) {
        BillingState = billingState;
    }

    public String getBillingZip() {
        return BillingZip;
    }

    public void setBillingZip(String billingZip) {
        BillingZip = billingZip;
    }

    public String getBillingCountry() {
        return BillingCountry;
    }

    public void setBillingCountry(String billingCountry) throws Exception {
        BillingCountry = getCountry(billingCountry);
    }

    public String getBillingEmail() {
        return BillingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        BillingEmail = billingEmail;
    }

    public String getBillingPhone1() {
        return BillingPhone1;
    }

    public void setBillingPhone1(String billingPhone1) {
        BillingPhone1 = billingPhone1;
    }

    public String getBillingPhone2() {
        return BillingPhone2;
    }

    public void setBillingPhone2(String billingPhone2) {
        BillingPhone2 = billingPhone2;
    }

    public String getBillingFax() {
        return BillingFax;
    }

    public void setBillingFax(String billingFax) {
        BillingFax = billingFax;
    }

    public String getShiptoBillingAddress() {
        return ShiptoBillingAddress;
    }

    public void setShiptoBillingAddress(String shiptoBillingAddress) {
        ShiptoBillingAddress = shiptoBillingAddress;
    }

    public String getShippingFirstName() {
        return ShippingFirstName;
    }

    public void setShippingFirstName(String shippingFirstName) {
        ShippingFirstName = shippingFirstName;
    }

    public String getShippingLastName() {
        return ShippingLastName;
    }

    public void setShippingLastName(String shippingLastName) {
        ShippingLastName = shippingLastName;
    }

    public String getShippingCompany() {
        return ShippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        ShippingCompany = shippingCompany;
    }

    public String getShippingAddress1() {
        return ShippingAddress1;
    }

    public void setShippingAddress1(String shippingAddress1) {
        ShippingAddress1 = shippingAddress1;
    }

    public String getShippingAddress2() {
        return ShippingAddress2;
    }

    public void setShippingAddress2(String shippingAddress2) {
        ShippingAddress2 = shippingAddress2;
    }

    public String getShippingCity() {
        return ShippingCity;
    }

    public void setShippingCity(String shippingCity) {
        ShippingCity = shippingCity;
    }

    public String getShippingState() {
        return ShippingState;
    }

    public void setShippingState(String shippingState) {
        ShippingState = shippingState;
    }

    public String getShippingZip() {
        return ShippingZip;
    }

    public void setShippingZip(String shippingZip) {
        ShippingZip = shippingZip;
    }

    public String getShippingCountry() {
        return ShippingCountry;
    }

    public void setShippingCountry(String shippingCountry) throws Exception {
        ShippingCountry = getCountry(shippingCountry);
    }

    public String getShippingEmail() {
        return ShippingEmail;
    }

    public void setShippingEmail(String shippingEmail) {
        ShippingEmail = shippingEmail;
    }

    public String getShippingPhone1() {
        return ShippingPhone1;
    }

    public void setShippingPhone1(String shippingPhone1) {
        ShippingPhone1 = shippingPhone1;
    }

    public String getShippingPhone2() {
        return ShippingPhone2;
    }

    public void setShippingPhone2(String shippingPhone2) {
        ShippingPhone2 = shippingPhone2;
    }

    public String getShippingFax() {
        return ShippingFax;
    }

    public void setShippingFax(String shippingFax) {
        ShippingFax = shippingFax;
    }

    public String getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(String subTotal) {
        SubTotal = subTotal;
    }

    public String getShippingandHandling() {
        return ShippingandHandling;
    }

    public void setShippingandHandling(String shippingandHandling) {
        ShippingandHandling = shippingandHandling;
    }

    public String getShippingMethod() {
        return ShippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        ShippingMethod = shippingMethod;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public String getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        GrandTotal = grandTotal;
    }

    public String getOrderComments() {
        return OrderComments;
    }

    public void setOrderComments(String orderComments) {
        OrderComments = orderComments;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public List getItems() {
        return Items;
    }

    public void setItems(List items) {
        this.Items = items;
    }

    public void copyShipToBill() {
        BillingFirstName = getValue(ShippingFirstName);
        BillingLastName = getValue(ShippingLastName);
        BillingCompany = getValue(ShippingCompany);
        BillingAddress1 = getValue(ShippingAddress1);
        BillingAddress2 = getValue(ShippingAddress2);
        BillingCity = getValue(ShippingCity);
        BillingState = getValue(ShippingState);
        BillingZip = getValue(ShippingZip);
        BillingCountry = getValue(ShippingCountry);
        BillingEmail = getValue(ShippingEmail);
        BillingPhone1 = getValue(ShippingPhone1);
        BillingPhone2 = getValue(ShippingPhone2);
        BillingFax = getValue(ShippingFax);


    }
     public void copyBillToShip() {
        ShippingFirstName = getValue(BillingFirstName);
        ShippingLastName = getValue(BillingLastName);
        ShippingCompany = getValue(BillingCompany);
        ShippingAddress1 = getValue(BillingAddress1);
        ShippingAddress2 = getValue(BillingAddress2);
        ShippingCity = getValue(BillingCity);
        ShippingState = getValue(BillingState);
        ShippingZip = getValue(BillingZip);
        ShippingCountry = getValue(BillingCountry);
        ShippingEmail = getValue(BillingEmail);
        ShippingPhone1 = getValue(BillingPhone1);
        ShippingPhone2 = getValue(BillingPhone2);
        ShippingFax = getValue(BillingFax);


    }

     public void printBilling() {
        log.debug("BillingFirstName: " + getValue(BillingFirstName));
        log.debug("BillingLastName: " + getValue(BillingLastName));
        log.debug("BillingCompany: " + getValue(BillingCompany));
        log.debug("BillingAddress1: " + getValue(BillingAddress1));
        log.debug("BillingAddress2: " + getValue(BillingAddress2));
        log.debug("BillingCity: " + getValue(BillingCity));
        log.debug("BillingState: " + getValue(BillingState));
        log.debug("BillingZip: " + getValue(BillingZip));
        log.debug("BillingCountry: " + getValue(BillingCountry));
        log.debug("BillingEmail: " + getValue(BillingEmail));
        log.debug("BillingPhone1: " + getValue(BillingPhone1));
        log.debug("BillingPhone2: " + getValue(BillingPhone2));
        log.debug("BillingFax: " + getValue(BillingFax));


    }

    public void printItems() {
        for(lineItemBean line:Items)
        {
            log.debug("Item:"+line.getInventory_num()
            +" / "+line.getQuanity()+" / "+line.getPrice()+" / "+line.getDesc());
        }
        log.debug("BillingFirstName: " + getValue(BillingFirstName));
        log.debug("BillingLastName: " + getValue(BillingLastName));
        log.debug("BillingCompany: " + getValue(BillingCompany));
        log.debug("BillingAddress1: " + getValue(BillingAddress1));
        log.debug("BillingAddress2: " + getValue(BillingAddress2));
        log.debug("BillingCity: " + getValue(BillingCity));
        log.debug("BillingState: " + getValue(BillingState));
        log.debug("BillingZip: " + getValue(BillingZip));
        log.debug("BillingCountry: " + getValue(BillingCountry));
        log.debug("BillingEmail: " + getValue(BillingEmail));
        log.debug("BillingPhone1: " + getValue(BillingPhone1));
        log.debug("BillingPhone2: " + getValue(BillingPhone2));
        log.debug("BillingFax: " + getValue(BillingFax));


    }

     public void printShipping() {
        log.debug("ShippingFirstName: " + getValue(ShippingFirstName));
        log.debug("ShippingLastName: " + getValue(ShippingLastName));
        log.debug("ShippingCompany: " + getValue(ShippingCompany));
        log.debug("ShippingAddress1: " + getValue(ShippingAddress1));
        log.debug("ShippingAddress2: " + getValue(ShippingAddress2));
        log.debug("ShippingCity: " + getValue(ShippingCity));
        log.debug("ShippingState: " + getValue(ShippingState));
        log.debug("ShippingZip: " + getValue(ShippingZip));
        log.debug("ShippingCountry: " + getValue(ShippingCountry));
        log.debug("ShippingEmail: " + getValue(ShippingEmail));
        log.debug("ShippingPhone1: " + getValue(ShippingPhone1));
        log.debug("ShippingPhone2: " + getValue(ShippingPhone2));
        log.debug("ShippingFax: " + getValue(ShippingFax));

    }

    public void printMisc(){
      log.debug("SubTotal: " + getValue(SubTotal));
      log.debug("ShippingandHandling: " + getValue(ShippingandHandling));
      log.debug("ShippingMethod: " + getValue(ShippingMethod));
      log.debug("Tax: " + getValue(Tax));
      log.debug("GrandTotal: " + getValue(GrandTotal));
      log.debug("OrderComments: " + getValue(OrderComments));
      log.debug("OrderDate: " + getValue(OrderDate));
      log.debug("orderRef: " + getValue(orderRef));
      log.debug("CustomerNumber: " + getValue(customerNumber));
      log.debug("isGift: " + getValue(isGift));
      log.debug("giftMessage: " + getValue(giftMessage));

    }

    public void printAll(){
      printBilling();
      printShipping();
      printMisc();
        printItems();
    }

  


    public void loadOwdOrder(Order o) throws Exception {
        o.getBillingContact().setName(getValue(BillingFirstName) + " " + getValue(BillingLastName));
        o.getBillingContact().email = getValue(BillingEmail);
        o.getBillingContact().phone = getValue(BillingPhone1);

        o.getBillingAddress().company_name = getValue(BillingCompany);
        if (o.getBillingAddress().company_name.trim().length() < 1) o.getBillingAddress().company_name = ".";
        o.getBillingAddress().address_one = getValue(BillingAddress1);
        o.getBillingAddress().address_two = getValue(BillingAddress2);
        o.getBillingAddress().city = getValue(BillingCity);
        o.getBillingAddress().state = getValue(BillingState);
        o.getBillingAddress().zip = getValue(BillingZip);
        o.getBillingAddress().country = getValue(BillingCountry);


        o.getShippingContact().setName(getValue(ShippingFirstName) + " " + getValue(ShippingLastName));
        o.getShippingContact().email = getValue(ShippingEmail);
        o.getShippingContact().phone = getValue(ShippingPhone1);

        o.getShippingAddress().company_name = getValue(ShippingCompany);
        if (o.getShippingAddress().company_name.trim().length() < 1) o.getShippingAddress().company_name = ".";

        o.getShippingAddress().address_one = getValue(ShippingAddress1);
        o.getShippingAddress().address_two = getValue(ShippingAddress2);
        o.getShippingAddress().city = getValue(ShippingCity);
        o.getShippingAddress().state = getValue(ShippingState);
        o.getShippingAddress().zip = getValue(ShippingZip);
        o.getShippingAddress().country = getValue(ShippingCountry);

        if (!CountryNames.exists(o.getBillingAddress().country)) {
            throw new Exception("Customer Country name not recognized: " + o.getBillingAddress().country);
        }
        if (!CountryNames.exists(o.getShippingAddress().country)) {
            throw new Exception("Shipping Country name not recognized: " + o.getShippingAddress().country);
        }
        o.getShippingAddress().country = CountryNames.getCountryName(o.getShippingAddress().country);
        o.getBillingAddress().country = CountryNames.getCountryName(o.getBillingAddress().country);
                                    //log.debug("1");
        o.total_shipping_cost = Float.parseFloat(getValue(ShippingandHandling, "0"));
        //log.debug("2");
        o.total_tax_cost = Float.parseFloat(getValue(Tax, "0"));
        //log.debug("3");
        //o.discount = -1 * Math.abs(rdr.getFloatValue(ko_Discount, row, 0.00f));
        o.getShippingInfo().comments = getValue(OrderComments);
        //o.getShippingInfo().whse_notes = kWarehouse_Notes;

        //  o.getShippingInfo().ss_saturday = kShip_Handling_Fee, row, "0").equals("1") ? "1" : "0";
        //  float cod_charge = OWDUtilities.roundFloat(rdr.getFloatValue(kCOD_Shipment, row, 0.00f));
        //  if (cod_charge > 0.00f) {
        //      o.getShippingInfo().cod_charge = "" + cod_charge;
        //      o.getShippingInfo().ss_cod = "1";
        //  }

        //  o.total_shipping_cost = rdr.getFloatValue(kShip_Handling_Fee, row, 0.00f);


        //bill to client account
        log.debug("Shipping Code:"+getValue(ShippingMethod));
        try
        {
            o.getShippingInfo().setShipOptions(getValue(ShippingMethod), "Prepaid", "");
        }catch(Exception exx)
        {
            exx.printStackTrace();
          //  throw exx;
        }
        o.order_refnum = getOrderRef();

        o.is_paid = 1;

       // Iterator it = Items.iterator();
        log.debug("lines:"+Items.size());
       for(lineItemBean lb:Items) {
          //  lineItemBean lb = (lineItemBean) it.next();
           if(lb!=null)
           {
            log.debug("lb:"+lb);

                   double discount = Double.parseDouble(lb.getPrice().trim());
                   if(discount<0)
                   {
                       o.discount += discount;
                   }  else
                   {

            o.addLineItem(lb.getInventory_num(),
                    lb.getQuanity().trim(),
                    lb.getPrice().trim(),
                    "0.00",
                    lb.getDesc().trim(),
                    "", "");
                   }
           }
        }

        o.recalculateBalance();
         if(!(getValue(insuranceRequested,"").equals("")))
        {
           o.getShippingInfo().declared_value=""+o.total_product_cost;
            o.getShippingInfo().ss_declared_value="1";
        }

        o.order_type = getOrderType();
    }

    public String getValue(String s) {
        if (null == s) return "";
        return s;
    }

    public String getValue(String s, String d) {
        if (null == s) return d;
        return s;
    }

    public static String getCountry(String s) throws Exception {
        s=s.replaceAll("US Virgin Islands","VIRGIN ISLANDS, U.S.");
        
        if (!CountryNames.exists(s)) {
            throw new Exception("Country name not recognized: " + s);
        }

        return CountryNames.getCountryName(s);


    }
    public static void main(String[] args){
        try{
        //log.debug(getCountry("China"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
