package com.owd.core.business.client;

import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.payment.FinancialTransaction;
import com.owd.core.xml.OrderXMLDoc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 27, 2003
 * Time: 12:48:07 PM
 * To change this template use Options | File Templates.
 */
public class CopiaCesarClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();
    public Client getClient() {
        return client;
    }

      public static String kDogName = "kDogName";
    public static String kDogBreed = "kDogBreed";
    public static String kEmailOptin = "kEmailOptin";

      protected boolean manualMode = false;


    public void setManualEntryMode(boolean yes)
    {
        manualMode = yes;
    }

    public List getCustomOrderFields(Order order) {
              List fieldList = new ArrayList();
        CustomField petField = new CustomField();
        petField.setDisplayType(CustomField.displayTypeText);
        petField.setCurrentValue("");
        petField.setDisplayName("(Optional) Pet's Name?");
        petField.setFieldName(kDogName);
        petField.setDescription("");
        fieldList.add(petField);
        CustomField breedField = new CustomField();
        breedField.setDisplayType(CustomField.displayTypeText);
        breedField.setDisplayName("(Optional) What breed of dog?");
        breedField.setFieldName(kDogBreed);
        breedField.setDescription("");

        fieldList.add(breedField);

        CustomField catalogField = new CustomField();
        catalogField.setDisplayType(CustomField.displayTypeCheckbox);
        catalogField.setCurrentValue("0");
        catalogField.setDefaultValue("1");
        catalogField.setDisplayName("Subscribe to Email Newsletter?");
        catalogField.setFieldName(kEmailOptin);
        catalogField.setDescription("");

        fieldList.add(catalogField);

        return fieldList;
    }

    public void sendFlexPayDeclineEmail(OrderStatus order)
       {
           try
           {
           StringBuffer sb = new StringBuffer();
           sb.append("Dear Customer,\n\n");
           sb.append("I attempted to charge your credit card for the due payment on your flexible payments plan for your Cesar Millan DVD set today, and the card declined.\n");
           sb.append("Please call 1-866-402-2087 or respond to this email promptly to provide another card or method of payment for the product that was shipped to you.\n");
           sb.append("Thank you,\nCustomer Service");
          // String[] bccs = new String[2];
          // bccs[0]  = "healthfx@owd.com";
       //	bccs[1]  = "noop@owd.com";
           Mailer.sendMail("Re: Cesar Millan Order "+order.orderReference,sb.toString(),"\""+order.billContact.getName()+"\" <"+order.billContact.getEmail()+">",null,null,"orders@CesarMillanInc.com");

           }catch(Exception ex){ex.printStackTrace();}
       }

        public boolean hasManualEntryMode(){ return false;}
    public void handleCustomOrderFields(Order order, List fields) {
           order.getShippingInfo().whse_notes = "";
           order.getShippingInfo().comments = "";

                    for (int i = 0; i < fields.size(); i++) {
            CustomField cf = ((CustomField) fields.get(i));

            if (cf.getFieldName().equals(kDogName)) {
                order.getShippingInfo().comments = "/PETNAME:"+cf.getCurrentValue();
            }


            if (cf.getFieldName().equals(kEmailOptin)) {
                if ("1".equals(cf.getCurrentValue()))
                {
                   order.getShippingInfo().whse_notes = "Newsletter opt-in";
                }else
                {
                    order.getShippingInfo().whse_notes = "";
                }
            }


            if (cf.getFieldName().equals(kDogBreed)) {

                 order.getShippingInfo().comments = order.getShippingInfo().comments+"/DOGBREED:"+cf.getCurrentValue();

            }
        }

    }

    public void updateCustomOrderFields(Order order, HttpServletRequest request, List fields) {
        for (int i = 0; i < fields.size(); i++) {
            String val = request.getParameter(((CustomField) fields.get(i)).getFieldName());
            if (val != null) {
                ((CustomField) fields.get(i)).setCurrentValue(val);
            }
        }
    }


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
        return false;
    }

    public boolean allowManualDiscountEntry(Order order) {
        return false;

    }

    public boolean allowShipPriceEditing(Order order) {
        return false;
    }

    public boolean allowSalesTaxEditing(Order order) {
        return false;
    }

    public void sendNotificationMessage(Order order, String subject, String message) {
         this.sendCustomerEmailConfirmation(order);

    }


   public String getFlexPayStatement(Order order){
       return "FlexPay Statement: Today's payment will be the order total minus 49.98. Your credit card will be billed 24.99 in 30 days, and then 24.99 thirty days after that to complete your order.";
   }

     public void sendCustomerEmailConfirmation(Order order) {

        try {

            if (order.getBillingContact().getEmail().indexOf("@") > 0) {

                StringBuffer sub = new StringBuffer();

                StringBuffer note = new StringBuffer();

                sub.append("Receipt of Purchase for Order " + order.order_refnum);

                note.append("\r\n\r\nYour Order Reference: " + order.order_refnum + "\r\n\r\nThank you for ordering from Cesar Millan, Inc!  Cesar thanks you for supporting his message of strengthening the connection between humans and dogs.\r\n" +
                        "\r\n" +
                        "Please visit our website, www.cesarmillaninc.com, to check out new products and get additional information.\r\n" +
                        "\r\n" +
                        "Want more tips from Cesar?  Sign up for the Cesar Millan newsletter, http://cesarmillaninc.com/contact.php.  Receive monthly news from the Dog Whisperer himself, including valuable information for you and your dog, upcoming events, and more.\r\n"+
                        "\r\n" +
                        "If you have questions or problems with your order, please call customer service at (866) 402-2087.");




                java.text.DecimalFormat decform = new java.text.DecimalFormat("#,###,##0.00");


                note.append("\r\n\r\nItems Ordered: \r\n");

                note.append(OWDUtilities.padRight("Item", 20) + OWDUtilities.padRight("Ordered", 8) + OWDUtilities.padRight("Line Total", 11) + OWDUtilities.padRight("Description", 42) + "\r\n");

                for (int i = 0; i < 81; i++) {
                    note.append("=");
                }

                note.append("\r\n");


                for (int i = 0; i < order.skuList.size(); i++) {

                    LineItem item = (LineItem) order.skuList.elementAt(i);

                    String description = item.description;

                    if (description == null) description = "";


                    StringTokenizer st = new StringTokenizer(description, " ");

                    int currentLen = 0;

                    StringBuffer currLine = new StringBuffer();

                    note.append(OWDUtilities.padRight(item.client_part_no, 20) + OWDUtilities.padRight("" + (item.quantity_request + item.quantity_backordered), 8) + OWDUtilities.padRight(decform.format(item.total_price), 11));

                    while (st.hasMoreTokens()) {

                        String word = st.nextToken() + " ";

                        if ((currentLen + word.length()) > 32) {

                            note.append(OWDUtilities.padRight(currLine.toString(), 42) + "\r\n" + OWDUtilities.padRight("", 39));

                            currentLen = 0;

                            currLine.setLength(0);

                        }

                        currLine.append(word);

                        currentLen += word.length();

                    }

                    note.append(OWDUtilities.padRight(currLine.toString(), 42) + "\r\n");

                }



                //	note.append("\r\n"+(item.quantity_request+item.quantity_backordered)+" "+item.description+" "+item.client_part_no+" @ "+item.sku_price+" "+item.total_price);

                for (int i = 0; i < 81; i++) {
                    note.append("=");
                }

                note.append("\r\n Product Subtotal: " + decform.format(order.total_product_cost) + "\r\n");

                note.append(" Discount: " + decform.format(order.discount) + "\r\n");

                note.append(" Sales Tax: " + decform.format(order.total_tax_cost) + "\r\n");

                note.append(" Shipping: " + decform.format(order.total_shipping_cost) + "\r\n");

                note.append(" Grand Total: " + decform.format(order.total_order_cost) + "\r\n");


                note.append("\r\n\r\nBilled To: ");

                note.append("\r\n" + order.getBillingContact().getName());

                if (order.getBillingAddress().company_name.length() > 1)
                    note.append("\r\n").append(order.getBillingAddress().company_name);

                note.append("\r\n").append(order.getBillingAddress().address_one);

                if (order.getBillingAddress().address_two.length() > 0) note.append("\r\n" + order.getBillingAddress().address_two);

                note.append("\r\n" + order.getBillingAddress().city + " " + order.getBillingAddress().state + "  " + order.getBillingAddress().zip);

                note.append("\r\n" + order.getBillingAddress().country);


                note.append("\r\n\r\nDeliver To: (via " + order.getShippingInfo().carr_service + " )");

                note.append("\r\n" + order.getShippingContact().getName());

                if (order.getShippingAddress().company_name.length() > 1) note.append("\r\n" + order.getShippingAddress().company_name);

                note.append("\r\n" + order.getShippingAddress().address_one);

                if (order.getShippingAddress().address_two.length() > 0) note.append("\r\n" + order.getShippingAddress().address_two);

                note.append("\r\n" + order.getShippingAddress().city + " " + order.getShippingAddress().state + "  " + order.getShippingAddress().zip);

                note.append("\r\n" + order.getShippingAddress().country);


                String[] emails = new String[1];

                emails[0]=order.getBillingContact().getEmail();

                Mailer.sendMail(sub.toString(), note.toString(),  emails, "orders@CesarMillanInc.com");
            }


        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public CopiaCesarClientPolicy(Client client) {
        setClient(client);


    }

    public CopiaCesarClientPolicy() {

    }

    public Order createInitializedOrder() {
        Order order = null;

        try{
            new Order("" + getClient().client_id);
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }        order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
        order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
        order.backorder_rule = OrderXMLDoc.kBackOrderAll;
        order.order_type = "Direct Entry";

        return order;

    }

    public float getSalesTaxValue(Order order) throws Exception {
         order.tax_pct = (float) 0.00f;
        order.shipping_taxable = true;

        if ("CA".equalsIgnoreCase(order.getShippingAddress().state) || "California".equalsIgnoreCase(order.getShippingAddress().state)) {
            order.tax_pct = (float) 0.085000;
            return (float) 0.085000 * (order.total_shipping_cost + order.total_product_cost);
        }
        if ("SD".equalsIgnoreCase(order.getShippingAddress().state) || "South Dakota".equalsIgnoreCase(order.getShippingAddress().state)) {
            order.tax_pct = (float) 0.060000;
            return (float) 0.060000 * (order.total_shipping_cost + order.total_product_cost);
        }

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


        double sh = 0.00d;

        long setUnits = order.getQuantityForSKU("0102NT-BS1");
        long bookUnits = order.getQuantityForSKU("2003HC-CW1");


        if (setUnits > 0) {
            sh += 7.95d;
        }
        if (setUnits > 1) {
            sh += (setUnits-1)*2.65d;
        }


        sh += ((bookUnits) * (3.50d));


        return new Double(sh).floatValue();
        //0101NT-PTD

    }

    public float getShippingCost(Order order, ShippingOption shipOption) throws Exception {




        double sh = 0.00d;

        long setUnits = order.getQuantityForSKU("0102NT-BS1");
        long bookUnits = order.getQuantityForSKU("2003HC-CW1");


        if (setUnits > 0) {
            sh += 7.95d;
        }
        if (setUnits > 1) {
            sh += (setUnits-1)*2.65d;
        }


        sh += ((bookUnits) * (3.50d));


        return new Double(sh).floatValue();
        //0101NT-PTD


    }


    public Map getPaymentOptions() throws Exception {
        Map aMap = new TreeMap();
        if (client.pp_proc != null && client.pp_proc.indexOf("payment") > 0) {
    //    aMap.put("FLEX", "FlexPay - three CC payments");
            aMap.put("CC", "Credit/Debit Card");
        }  else
        {
           aMap.put("CK", "Check/Mail (Hold for Release)");
        }
       //

        return aMap;
    }

    public void addLineItemToOrder(Order order, String sku, String description, String price, String quantity) throws Exception {


        order.addLineItem(sku, quantity, price, "0.00", description, "", "");
        order.deleteLineItemForSKU("0101NT-PTD");
        if(order.getQuantityForSKU("0102NT-BS1")>0)
        {
            order.addLineItem("0101NT-PTD",quantity,"0.00","0.00","","","");
        }



    }

    public void updateLineItemsAfterItemChange(Order anOrder) {
        try
        {
        anOrder.deleteLineItemForSKU("0101NT-PTD");
            if(anOrder.getQuantityForSKU("0102NT-BS1")>0)
            {
                anOrder.addLineItem("0101NT-PTD",""+anOrder.getQuantityForSKU("0102NT-BS1"),"0.00","0.00","","","");
            }
        }catch(Exception ex){}
    }

    public void saveNewOrder(Order order, boolean testing) throws Exception {


        boolean isFlexPay = false;

        try
        {
        if(order.bill_cc_type.equals("FLEX"))
        {
            isFlexPay = true;
            order.bill_cc_type="CC";
            order.discount = (24.99f*2.0f);
            order.recalculateBalance();


        }
        if(order.getQuantityForSKU("0102NT-BS1")<1 && order.getQuantityForSKU("2003HC-CW1")<1)
        {
            throw new Exception ("No valid SKUs in order! Order must include a book or DVD set SKU");
        }



        String ref = order.saveNewOrder(OrderUtilities.kRequirePayment, testing);

            if(ref == null || (!(order.completed)))
  				{
  					throw new Exception("Your order could not be processed because "+order.last_error);
  				}

        if(isFlexPay)
        {
            doFlexPayFollowup(order,24.99f,30,2);
        }
        if(ref != null)
        {
          //  sendNotificationMessage(order,"","");
        }
        }catch(Exception ex)
        {
               if(isFlexPay)
        {

            order.bill_cc_type="FLEX";
            order.discount = 0.00f;
            order.recalculateBalance();
        }

            order.last_error=ex.getMessage();
        }
    }

        public void doFlexPayFollowup(Order order, float paymentAmount, int daysApart, int numberPayments)
                {
                    //log.debug("doing flexpay");
                    java.sql.Connection cxn = null;
                    List oldTrans = new ArrayList();
                    //log.debug("getting old transactions ");
                    try
                    {
                        cxn = ConnectionManager.getConnection();
                        oldTrans = FinancialTransaction.getTransactionsForOrder(cxn,order.getID());


                        FinancialTransaction firstTrans = null;
                        ////log.debug("got old transactions count="+oldTrans.size());
                        if(oldTrans.size()>0)
                        {
                            Iterator it = oldTrans.iterator();
                            while(it.hasNext())
                            {
                                FinancialTransaction trans = (FinancialTransaction) it.next();
                                if((!(trans.isProcessed())) && trans.isFutureCharge())
                                {
                                    trans.dbdelete();
                                }else
                                {
                                    if(firstTrans == null && (!(trans.isVoid())) && trans.isProcessed())
                                        firstTrans = trans;
                                }

                            }

                            if(firstTrans != null)
                            {

                                GregorianCalendar today = new GregorianCalendar();
                                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.US);

                                for(int i=0;i<numberPayments;i++)
                                {
                                     today.add(Calendar.DATE,daysApart);

                                     //log.debug("cloning "+(i+1));

                                     FinancialTransaction ft1 = firstTrans.cloneForFutureCharge(paymentAmount,formatter.format(today.getTime())+" 00:00:00");

                                  }

                        }

                        }
                    }catch(Exception ex)
                    {
                        ex.printStackTrace();
                        //throw ex;
                    }finally
                    {
                        try{cxn.close();}catch(Exception ex){}
                    }
                }


}
