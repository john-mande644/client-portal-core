package com.owd.core.business.client;

import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 25, 2007
 * Time: 3:19:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class RazoramaClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();


    public void sendNotificationMessage(Order order, String subject, String message) {


        try {


                StringBuffer sub = new StringBuffer();

                StringBuffer note = new StringBuffer();

                sub.append("OWD Manual Order Placed : " + order.order_refnum);

                note.append("\r\n\r\nYour Order Reference: " + order.order_refnum + "\r\n\r\n");




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


                String[] emails = new String[2];

                emails[0]="melanie@razorama.com";
                emails[1]="amy@razorama.com";

                Mailer.sendMail(sub.toString(), note.toString(),  emails, "do-not-reply@owd.com");



        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }


    public void saveNewOrder(Order order, boolean testing) throws Exception {

        if((order.total_product_cost+(-1*(Math.abs(order.discount))))>300.00)
                       {
                             order.getShippingInfo().ss_declared_value = "1";
                           order.getShippingInfo().declared_value = ""+(order.total_product_cost+(-1*(Math.abs(order.discount))));
                       }


        super.saveNewOrder(order, testing);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
