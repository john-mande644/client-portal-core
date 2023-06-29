package com.owd.jobs.jobobjects.cuore;

import com.owd.LogableException;
import com.owd.core.*;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.csXml.OrderRater;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.clients.DogearedEmailSender;
import com.owd.jobs.jobobjects.*;
import ipworks.Htmlmailer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 27, 2005
 * Time: 10:57:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class CuoreOrderProcessor implements OrderProcessingResultsHandler {
    private final static Logger log = LogManager.getLogger();

    protected final int kClientID;
    protected  CuoreApiConnector api=null;

    protected String status = "";
    public final String orderType = "Cuore";


    static public void main(String[] args) throws Exception {

        CuoreOrderProcessor api = new CuoreOrderProcessor();

        api.sendEmailOrderConfirmation(new OrderStatus("9751967"),"ashley.eveland@gmail.comx");

    }


    private CuoreOrderProcessor() {
        kClientID = 0;
    }

    public CuoreOrderProcessor(int clientID, String key) {

        kClientID = clientID;
        api = new CuoreApiConnector(key) ;
    }

    public void onOrderImportSuccess(Order order) throws Exception {
         //all good!
        OrderStatus os =  new OrderStatus(order.orderID);
        sendEmailOrderConfirmation(os,null);

    }

    public void onOrderImportFailure(Order order) throws Exception {
        LogableException.logException(new Exception(), order.last_error+":"+order.last_payment_error,
                order.order_refnum,
                "" + kClientID,
                this.getClass().getName(),
                LogableException.errorTypes.ORDER_IMPORT);
    }

    public void onOrderImportFailure(String message) throws Exception {
        LogableException.logException(new Exception(), message,
                "GENERIC",
                "" + kClientID,
                this.getClass().getName(),
                LogableException.errorTypes.ORDER_IMPORT);
    }

    public void onOrderImportBatchSuccess(String batchReference) throws Exception {

    }

    public void onOrderImportDuplicate(Order order) throws Exception {

    }

    public void onOrderImportBatchFailure(String batchReference, String message) throws Exception {
        LogableException.logException(new Exception(), batchReference + "::" + message,
                "GENERIC",
                "" + kClientID,
                this.getClass().getName(),
                LogableException.errorTypes.ORDER_IMPORT);
    }

    public void importOrders() {

        try {
            for(int i=0;i<2;i++) {
                String orderData = api.retrieveLiveOrderDataFromCuore(i);
               log.debug("got order data:"+orderData );
                if(null != orderData) {
                    log.debug( "creating handler" );
                    DelimitedFileOrderHandler fileHandler = new DelimitedFileOrderHandler(this, kClientID, new CSVReader(new BufferedReader(new StringReader(orderData.replaceAll("<br/>","\r"))), false), "") {
                        int ksource_code  = 0;
                        int kdate_ordered  = 1;
                        int kbill_to_first_name  = 2;
                        int kbill_to_last_name  = 3;
                        int kbill_to_address_1  = 4;
                        int kbill_to_address_2  = 5;
                        int kbill_to_city  = 6;
                        int kbill_to_stateprovince  = 7;
                        int kbill_to_zip_code  = 8;
                        int kbill_to_country_code  = 9;
                        int kbill_to_telephone  = 10;
                        int kemail_address  = 11;
                        int kauth_code  = 12;
                        int kextended_transaction_data  = 13;
                        int korder_total  = 14;
                        int kspecial_ship_method  = 15;
                        int kship_to_first_name  = 16;
                        int kship_to_last_name  = 17;
                        int kship_to_add1  = 18;
                        int kship_to_add2  = 19;
                        int kship_to_city  = 20;
                        int kship_to_state  = 21;
                        int kship_to_zip  = 22;
                        int kship_to_country_code  = 23;
                        int korder_tax  = 24;
                        int korder_sh  = 25;
                        int kqty1  = 26;
                        int kprod1  = 27;
                        int kprodx1  = 28;
                        int kprodxx1  = 29;
                        int kprodxxx1  = 30;
                        int kqty2  = 31;
                        int kprod2  = 32;
                        int kprodx2  = 33;
                        int kprodxx2  = 34;
                        int kprodxxx2  = 35;
                        int kqty3  = 36;
                        int kprod3  = 37;
                        int kprodx3  = 38;
                        int kprodxx3  = 39;
                        int kprodxxx3  = 40;
                        int kqty4  = 41;
                        int kprod4  = 42;
                        int kprodx4  = 43;
                        int kprodxx4  = 44;
                        int kprodxxx4  = 45;
                        int kqty5  = 46;
                        int kprod5  = 47;
                        int kprodx5  = 48;
                        int kprodxx5  = 49;
                        int kprodxxx5 = 50;

                        @Override
                        public String getOrderReferenceForRow(int rowNumber, String defaultValue) {
                            return super.getOrderReferenceForRow(rowNumber, defaultValue);
                        }

                        @Override
                        public Order loadOrder(Order order, int orderIndex) throws Exception {
                            log.debug("in DH loadOrder");
                            if (orderIndex < 0 || orderIndex >= getOrderPositionList().size())
                                throw new Exception("Could not load order index " + orderIndex + "; did not match order list size of " + getOrderPositionList().size());

                            OrderData data = (OrderData) getOrderPositionList().get(orderIndex);
                            DelimitedReader rdr = getDataReader();

                            for (int row = data.rowIndexStart; row < (data.rowIndexStart + data.rowsForOrder); row++) {
                                //log.debug("DH loading row " + row);
                                if (row == data.rowIndexStart) {
                                    order.order_refnum = rdr.getStrValue(ksource_code, row, "");
                                    if (order.order_refnum.length() < 4) {
                                        throw new Exception("Client order reference was invalid or not found.");
                                    }

                                    order.getBillingContact().setName((rdr.getStrValue(kbill_to_first_name, row, "") + " " + rdr.getStrValue(kbill_to_last_name, row, "")).trim());

                                    order.getBillingContact().phone = rdr.getStrValue(kbill_to_telephone, row, "");

                                    if (order.getBillingAddress().company_name.trim().length() < 1) {
                                        order.getBillingAddress().company_name = ".";
                                    }

                                    order.getShippingContact().setName(rdr.getStrValue(kship_to_first_name, row, "") + " " + rdr.getStrValue(kship_to_last_name, row, ""));
                                    order.getShippingContact().email = rdr.getStrValue(kemail_address, row, "");
                                    order.getBillingContact().email = rdr.getStrValue(kemail_address, row, "");
                                    order.getShippingContact().phone = rdr.getStrValue(kbill_to_telephone, row, "");


                                    if (order.getShippingAddress().company_name.trim().length() < 1) {
                                        order.getShippingAddress().company_name = ".";
                                    }
                                    order.getBillingAddress().address_one = rdr.getStrValue(kbill_to_address_1, row, "");
                                    order.getBillingAddress().address_two = rdr.getStrValue(kbill_to_address_2, row, "");
                                    order.getBillingAddress().city = rdr.getStrValue(kbill_to_city, row, "");
                                    order.getBillingAddress().state = rdr.getStrValue(kbill_to_stateprovince, row, "");
                                    order.getBillingAddress().zip = rdr.getStrValue(kbill_to_zip_code, row, "");
                                    
                                    
                                    order.getShippingAddress().address_one = rdr.getStrValue(kship_to_add1, row, "");
                                    order.getShippingAddress().address_two = rdr.getStrValue(kship_to_add2, row, "");
                                    order.getShippingAddress().city = rdr.getStrValue(kship_to_city, row, "");
                                    order.getShippingAddress().state = rdr.getStrValue(kship_to_state, row, "");
                                    order.getShippingAddress().zip = rdr.getStrValue(kship_to_zip, row, "");

                                    if (order.getShippingAddress().state.equalsIgnoreCase("PUERTO RICO")) {
                                        order.getShippingAddress().state = "PR";
                                        order.getShippingAddress().country = "PUERTO RICO";
                                    }
                                    if (!CountryNames.exists(order.getBillingAddress().country)) {
                                        throw new Exception("Customer Country name not recognized: " + order.getBillingAddress().country);
                                    }
                                    if (!CountryNames.exists(order.getShippingAddress().country)) {
                                        throw new Exception("Shipping Country name not recognized: " + order.getShippingAddress().country);
                                    }
                                    order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                                    order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);


                                    float taxValue = rdr.getFloatValue(korder_tax, row, 0.00f);
                                    float shipValue = rdr.getFloatValue(korder_sh, row, 0.00f);
                                    float orderValue = rdr.getFloatValue(korder_total, row, 0.00f);
                                    float subtotalValue = orderValue - taxValue - shipValue;

                                    order.addTag("CUORE_TAX", "" + taxValue);
                                    order.addTag("CUORE_SH", "" + shipValue);
                                    order.addTag("CUORE_TOTAL", "" + orderValue);
                                    order.addTag("CUORE_SUBTOTAL", "" + subtotalValue);
                                    order.addTag("CUORE_AUTHCODE", "" + rdr.getStrValue(kauth_code, row, ""));
                                    order.addTag("CUORE_TRANSDATA", "" + rdr.getStrValue(kextended_transaction_data, row, ""));

                                    order.getShippingInfo().carr_service = rdr.getStrValue(kspecial_ship_method, row, "");

                                    order.is_future_ship = 1;
                                    order.order_type = orderType;
                                }

                                if (rdr.getStrValue(kprod1, row, "").trim().length() > 0 && !("RUSH SHIP".equals(rdr.getStrValue(kprod1, row, "").trim())) && !("SHIP".equals(rdr.getStrValue(kprod1, row, "").trim()))) {
                                    //product data collected for every row
                                    addLineItem(order,
                                            rdr.getStrValue(kprod1, row, "").trim(),
                                            rdr.getStrValue(kqty1, row, "").trim(),
                                            "0.00",
                                            "0.00",
                                            "",
                                            "", "", "");
                                }
                                if (rdr.getStrValue(kprod2, row, "").trim().length() > 0 && !("RUSH SHIP".equals(rdr.getStrValue(kprod2, row, "").trim())) && !("SHIP".equals(rdr.getStrValue(kprod2, row, "").trim()))) {
                                    //product data collected for every row
                                    addLineItem(order,
                                            rdr.getStrValue(kprod2, row, "").trim(),
                                            rdr.getStrValue(kqty2, row, "").trim(),
                                            "0.00",
                                            "0.00",
                                            "",
                                            "", "", "");
                                }
                                if (rdr.getStrValue(kprod3, row, "").trim().length() > 0 && !("RUSH SHIP".equals(rdr.getStrValue(kprod3, row, "").trim())) && !("SHIP".equals(rdr.getStrValue(kprod3, row, "").trim()))) {
                                    //product data collected for every row
                                    addLineItem(order,
                                            rdr.getStrValue(kprod3, row, "").trim(),
                                            rdr.getStrValue(kqty3, row, "").trim(),
                                            "0.00",
                                            "0.00",
                                            "",
                                            "", "", "");
                                }
                                if (rdr.getStrValue(kprod4, row, "").trim().length() > 0 && !("RUSH SHIP".equals(rdr.getStrValue(kprod4, row, "").trim())) && !("SHIP".equals(rdr.getStrValue(kprod4, row, "").trim()))) {
                                    //product data collected for every row
                                    addLineItem(order,
                                            rdr.getStrValue(kprod4, row, "").trim(),
                                            rdr.getStrValue(kqty4, row, "").trim(),
                                            "0.00",
                                            "0.00",
                                            "",
                                            "", "", "");
                                }
                                if (rdr.getStrValue(kprod5, row, "").trim().length() > 0 && !("RUSH SHIP".equals(rdr.getStrValue(kprod5, row, "").trim())) && !("SHIP".equals(rdr.getStrValue(kprod5, row, "").trim()))) {
                                    //product data collected for every row
                                    addLineItem(order,
                                            rdr.getStrValue(kprod5, row, "").trim(),
                                            rdr.getStrValue(kqty5, row, "").trim(),
                                            "0.00",
                                            "0.00",
                                            "",
                                            "", "", "");
                                }
                            }

                            return order;
                        }


                        @Override
                        public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice, String desc, String color, String size, String externalLineReference) throws Exception {
                            if (Float.parseFloat(unitPrice) < 0.00f) {
                                order.discount = order.discount + Float.parseFloat(unitPrice);
                            } else {
                                order.addLineItem(sku,
                                        qty,
                                        unitPrice,
                                        linePrice,
                                        desc,
                                        color, size);
                                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = externalLineReference;

                            }
                        }

                        @Override
                        public void translateShipMethod(Order order, String oldMethod) throws Exception {

                            if (oldMethod.toUpperCase().contains("RUS")) {
                                order.getShippingInfo().setShipOptions("UPS 2nd Day Air", "Prepaid", "");
                            } else {
                                order.getShippingInfo().setShipOptions("USPS First-Class Mail", "Prepaid", "");
                            }


                            log.debug("SELECTED:" + order.getShippingInfo().carr_service);
                        }
                    };

                    log.debug("processing orders");
                    fileHandler.processOrders();

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new LogableException(ex, ex.getMessage(),
                    "GENERIC",
                    "" + kClientID,
                    this.getClass().getName(),
                    LogableException.errorTypes.ORDER_IMPORT);
        }
    }


    public void sendEmailOrderConfirmation(OrderStatus order, String overrideEmail)
    {
          StringBuilder sb = new StringBuilder();
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        String subject = "Order confirmation for Order "+order.orderReference;



        sb.append("<HTML><IMG SRC=\"http://owdmailimage.s3.amazonaws.com/ptlogo.png\"><P><B>Congratulations and thank you for placing your order with Perhaps Tonight&reg;!</B><P>Provided below are your order details. You will receive another email once your order has shipped. In the meantime, should you have any questions, please feel free to contact Customer Service at 1-800-333-0206 (Monday - Friday 7am - 9pm CST) or by email at perhapstonightsupport@owd.com"+"</P>");
        

        sb.append("<P><B>Date: "+sdf.format(now)+"</B>\r\n\r\n</P>");

        sb.append("<B>Shipping address : </B><BR>");
                sb.append(order.shipping.shipContact.getName()+"<BR>");
                        sb.append(order.shipping.shipAddress.getAddress_one()+"<BR>");
                                sb.append(order.shipping.shipAddress.getCity()+"<BR>" );
                                        sb.append(order.shipping.shipAddress.getState()+" "+order.shipping.shipAddress.getZip()+"<BR>" );
                                                sb.append(order.shipping.shipAddress.getCountry()+"<BR><P>" );

        sb.append("<B>Billing address : </B><BR>");
        sb.append(order.billContact.getName()+"<BR>");
        sb.append(order.billAddress.getAddress_one()+"<BR>");
                sb.append(order.billAddress.getCity()+"<BR>" );
                        sb.append(order.billAddress.getState()+" "+order.billAddress.getZip()+"<BR>" );
                                sb.append(order.billAddress.getCountry()+"<BR><P>" );

        for(LineItem line:(Vector<LineItem>) order.items) {
           sb.append(line.quantity_request+"x "+line.client_part_no+"   "+line.description+"<BR>");

    }
        Map<String,String> tags = order.getTagMap();

        float subtotal = Float.parseFloat(tags.get("CUORE_SUBTOTAL"));
        float total = Float.parseFloat(tags.get("CUORE_TOTAL"));
        float shipping = Float.parseFloat(tags.get("CUORE_SH"));
        float tax = Float.parseFloat(tags.get("CUORE_TAX"));
        DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00 USD");

        sb.append("<P><B>Subtotal : "+moneyFormat.format(subtotal)+"</B><BR>");
        sb.append("<P><B>Shipping : "+moneyFormat.format(shipping)+"</B><BR>");
        sb.append("<P><B>Tax : "+moneyFormat.format(tax)+"</B><BR>");
        sb.append("<P><B>Total : "+moneyFormat.format(total)+"</B><BR>");




        String email =(overrideEmail==null?order.billContact.getEmail():overrideEmail);

        if(!(OWDUtilities.isValidEmailAddress(email)))
        {
            email = order.shipperContact.getEmail();

        }
        if(OWDUtilities.isValidEmailAddress(email))
        {
        Vector to = new Vector();
        to.add(email);
        log.debug(email);
            try {
                Mailer.sendHTMLMail(subject, "", sb.toString(), to.toArray(), null, null, "perhapstonightsupport@owd.com");
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

}
