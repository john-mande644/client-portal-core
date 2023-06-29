package com.owd.core.business.client;

import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 7, 2006
 * Time: 3:49:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class LifeSpanClientPolicy extends com.owd.core.business.client.DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

       public Order createInitializedOrder() {
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

      public void saveNewOrder(Order order, boolean testing) throws Exception {
        setSignatureRequired(order);
          if(order.containsSKU("EE") && (!(order.containsSKU("DFLYER"))))
          {
              //order.addInsertItemIfAvailable("DFLYER",1);
          }
          if( (!(order.containsSKU("BRO"))))
          {
              order.addInsertItemIfAvailable("BRO",1);
          }
        order.saveNewOrder(OrderUtilities.kRequirePayment, testing);
          if(order.order_refnum.startsWith("DP")&&order.getBillingContact().getEmail().contains("@")){
              sendCustomerEmailConfirmation(order);
          }

    }

      public String getOWDShipMethodForExternalShipMethodName(String remoteSourceKey, String remoteMethod, Order order) {
        if (remoteSourceKey.equalsIgnoreCase("magento")) {
            if (remoteMethod.toUpperCase().contains("FREE")) {
                try {
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

                } catch (Exception ex) {
                    return "No valid rate returned";
                }
            }
        }
        return remoteMethod;
    }

    public void sendCustomerEmailConfirmation(Order order) {

        try {

            if (order.getBillingContact().getEmail().indexOf("@") > 0) {

                StringBuffer sub = new StringBuffer();

                StringBuffer note = new StringBuffer();

                sub.append("New Xendurance Order Placed - Order #" + order.order_refnum);

                note.append("Thank you for placing your order through our App! Once your package sprints out of our warehouse, we will send you an email with a link to track your order. (Please note that tracking is not always available for International Orders once they leave the US.) \n" +
                        "\n" +
                        "When you receive your order please don’t forget to share your PR story on Instagram and Facebook with #xendurance!\n" +
                        "\n" +
                        "If you have any further questions about your order or the products, please contact us at support@xendurance.com <mailto:support@xendurance.com> or call 877-754-0436.\n" +
                        "\n" +
                        "Thanks very much for choosing Xendurance products! Please find your order confirmation below.");




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

                System.out.println(note.toString());

                Mailer.sendMail(sub.toString(), note.toString(),  emails, "support@xendurance.com");
            }


        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public static void main(String[] args){

        try{
        OwdOrder o = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,12063291);
                    Order order = new Order(o,false);
            order.addLineItem("NikeXL",1,30.00f,30.00f,"Nike XL shirt","","");
            LifeSpanClientPolicy lp = new LifeSpanClientPolicy();
            lp.sendCustomerEmailConfirmation(order);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

