package com.owd.jobs.templates;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.core.xml.XMLUtils;
import com.owd.jobs.OWDStatefulJob;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 7, 2006
 * Time: 2:52:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class SampleShopsiteDownloadJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();



    final String url = "https://secure2.yourhost.com/shop-bin/ss/db_xml.cgi?startdate=";
    final String method = "GET";
    final String username = "kennyrogers";
    final String password = "e9x55h2";
    final String delimiter = ",";

    public static String client_id = "342"; //Dynamarketing


    public static void main(String[] args) throws Exception{

           SampleShopsiteDownloadJob jobber = new SampleShopsiteDownloadJob();
          // jobber.execute(new OWDMockJobExecutionContext(jobber));

       }
 public void internalExecute() {


        int days_before_today = 30;

                int day=0;
        int month=0;
        int year=0;


        try
        {

                    GregorianCalendar today = new GregorianCalendar();



                    for(int i=0; i<days_before_today; i++)
                    {
                        day = today.get(Calendar.DAY_OF_MONTH);
                        month = today.get(Calendar.MONTH);
                        year = today.get(Calendar.YEAR);
    //log.debug("Getting url: "+url+(1+month)+"/"+day+"/"+year);
                        WebResource client = new WebResource(url+(month+1)+"/"+day+"/"+year, method, username, password);
                        testImport(client); //imports(client);

                        today.add(Calendar.DATE,-1);
                    }


        }catch(Exception ex)
        {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: "+ex.getMessage()+"\n\nStacktrace ID: "+stamper+"\n\n");
            ex.printStackTrace();

        }catch(Throwable ext)
        {

                    //log.debug("\nThrowable: "+ext);
        }
    }


    private void testImport(WebResource resource) throws Exception
    {
        BufferedReader response = resource.getResource();
        StringBuffer sb = new StringBuffer();


                int line = 0;

                line = (int)response.read();

                while (line != -1)

                {

                    //Uncomment the next block if displaying result in HTML


/*
					if((char)line == '>')

						sb.append("&gt;\n");

					else if ((char)line == '<')

						sb.append("&lt;");

					else
*/


                    sb.append((char)line);



                    line = response.read();

                }



                //log.debug(":::API RESPONSE BEGIN:::");

                //log.debug(sb.toString());

                //log.debug(":::API RESPONSE END:::");

                org.w3c.dom.Document doc = null;

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            javax.xml.parsers.DocumentBuilder  builder = factory.newDocumentBuilder();
            doc = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));

            Element root = doc.getDocumentElement();

            root.normalize();

            //log.debug(root.getTagName());

        NodeList orderNodes = root.getElementsByTagName("Order");
        if(orderNodes != null)
        {
                        //log.debug(orderNodes.getLength()+" orders found!");
                        for(int i=0;i<orderNodes.getLength();i++)
                        {
                            Element orderNode = ((Element)orderNodes.item(i));
                            String orderRef = "SSKR"+XMLUtils.getNodeValue(orderNode,"OrderNumber");
                            //log.debug(orderRef);

                            if(OrderUtilities.orderRefnumExists(orderRef,client_id))
                            {
                                //log.debug("Order Ref No. "+orderRef+" has been imported for Dynamarketing.\n");
                            }else
                            {
                                Order order = new Order(client_id);//DM's client id


                                order.order_refnum = orderRef;
                                NodeList billingNodes = orderNode.getElementsByTagName("Billing");
                                //log.debug("got billing nodes");

                                //log.debug("11"); order.getBillingContact().name = XMLUtils.getNodeValue((Element)billingNodes.item(0),"FullName");
                                //log.debug("21x");    order.getBillingContact().email = XMLUtils.getNodeValue((Element)billingNodes.item(0),"Email");
                                //log.debug("31");    order.getBillingAddress().company_name = XMLUtils.getNodeValue((Element)billingNodes.item(0),"Company");
                                //log.debug("51");    order.getBillingContact().phone = XMLUtils.getNodeValue((Element)billingNodes.item(0),"Phone");
                                //log.debug("61x");    order.getBillingAddress().address_one = XMLUtils.getNodeValue((Element)(((Element)billingNodes.item(0)).getElementsByTagName("Address").item(0)),"Street1");
                                //log.debug("71");    order.getBillingAddress().address_two = XMLUtils.getNodeValue((Element)(((Element)billingNodes.item(0)).getElementsByTagName("Address").item(0)),"Street2");
                                //log.debug("81x");    order.getBillingAddress().city = XMLUtils.getNodeValue((Element)(((Element)billingNodes.item(0)).getElementsByTagName("Address").item(0)),"City");
                                //log.debug("91a");    order.getBillingAddress().state = XMLUtils.getNodeValue((Element)(((Element)billingNodes.item(0)).getElementsByTagName("Address").item(0)),"State");
                                //log.debug("101x");    order.getBillingAddress().zip = XMLUtils.getNodeValue((Element)(((Element)billingNodes.item(0)).getElementsByTagName("Address").item(0)),"Code");
                                //log.debug("111c");    order.getBillingAddress().country = XMLUtils.getNodeValue((Element)(((Element)billingNodes.item(0)).getElementsByTagName("Address").item(0)),"Country");

                                NodeList shippingNodes = orderNode.getElementsByTagName("Shipping");
                                order.getShippingContact().setName(XMLUtils.getNodeValue((Element)shippingNodes.item(0),"FullName"));
                                order.getShippingAddress().company_name = XMLUtils.getNodeValue((Element)shippingNodes.item(0),"Company");
                                order.getShippingContact().phone = XMLUtils.getNodeValue((Element)shippingNodes.item(0),"Phone");
                                order.getShippingAddress().address_one = XMLUtils.getNodeValue((Element)(((Element)shippingNodes.item(0)).getElementsByTagName("Address").item(0)),"Street1");
                                order.getShippingAddress().address_two = XMLUtils.getNodeValue((Element)(((Element)shippingNodes.item(0)).getElementsByTagName("Address").item(0)),"Street2");
                                order.getShippingAddress().city = XMLUtils.getNodeValue((Element)(((Element)shippingNodes.item(0)).getElementsByTagName("Address").item(0)),"City");
                                order.getShippingAddress().state = XMLUtils.getNodeValue((Element)(((Element)shippingNodes.item(0)).getElementsByTagName("Address").item(0)),"State");
                                order.getShippingAddress().zip = XMLUtils.getNodeValue((Element)(((Element)shippingNodes.item(0)).getElementsByTagName("Address").item(0)),"Code");
                                order.getShippingAddress().country = XMLUtils.getNodeValue((Element)(((Element)shippingNodes.item(0)).getElementsByTagName("Address").item(0)),"Country");

                              if(order.getShippingAddress().country.trim().equals(""))
                              {
                                order.getShippingAddress().country="USA"; 
                              }
                              //log.debug("121");
                            NodeList productNodes = orderNode.getElementsByTagName("Product");
                             //log.debug("131");
                            //    NodeList productNodes = ((Element)productsNode.item(0)).getElementsByTagName("Product");
                            if(productNodes==null)
                            {
                                //log.debug("No products found!");
                            }else{
                                //log.debug(productNodes.getLength()+" SKUs found!");

                        for(int p=0;p<productNodes.getLength();p++)
                        {

                            order.addLineItem(	XMLUtils.getNodeValue((Element)productNodes.item(p),"SKU"),
                                                XMLUtils.getNodeValue((Element)productNodes.item(p),"Quantity"),
                                                XMLUtils.getNodeValue((Element)productNodes.item(p),"ItemPrice"),
                                                "",
                                                XMLUtils.getNodeValue((Element)productNodes.item(p),"Name"),
                                                "",
                                                ""
                                                );
                        }


                          }
                        //totals/tax/shipping method
                        NodeList totalsNodes = orderNode.getElementsByTagName("Totals");
                        String testStr = XMLUtils.getNodeValue((Element)(((Element)totalsNodes.item(0)).getElementsByTagName("Tax").item(0)),"TaxAmount");
                        order.total_tax_cost =      OWDUtilities.roundFloat(new Float(("NULL".equalsIgnoreCase(testStr)?"0.00":testStr)).floatValue());
                        testStr = XMLUtils.getNodeValue((Element)(((Element)totalsNodes.item(0)).getElementsByTagName("ShippingTotal").item(0)),"Total");
                        order.total_shipping_cost = OWDUtilities.roundFloat(new Float(("NULL".equalsIgnoreCase(testStr)?"0.00":testStr)).floatValue());
                        String shipDescription = XMLUtils.getNodeValue((Element)(((Element)totalsNodes.item(0)).getElementsByTagName("ShippingTotal").item(0)),"Description");
                        order.po_num = "Ship Method: "+shipDescription;
                        //log.debug("********"+shipDescription);

                            order.getShippingInfo().setShipOptions("USPS Priority Mail","Prepaid","");





                        //payment types
                        NodeList paymentNodes = orderNode.getElementsByTagName("Payment");
                        NodeList ccNodes = ((Element)paymentNodes.item(0)).getElementsByTagName("CreditCard");
                        if(ccNodes.getLength() >= 1)
                        {
                            //paid for via CC - release immediately if possible
                            order.po_num=order.po_num+" : Payment Type-CC";
                        //log.debug("got CC payment!");
                        order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged; //no payment processing
                    //	order.recalculateBalance();
                    //	order.is_paid=1;
                    //	order.paid_amount = order.total_order_cost;
                    //	order.recalculateBalance();
                    //	order.bill_cc_type="CK";
                        order.is_future_ship=0;

                        }else
                        {
                        //CK
                        order.po_num=order.po_num+" : Payment Type-CK";
                            order.is_future_ship=1;
                            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged; //no payment processing
                            order.bill_cc_type="CK";
                        }
                        //backorder rule
                        order.order_type="ShopSite Download";
                        order.backorder_rule = OrderXMLDoc.kBackOrderAll;
                        order.ship_call_date =  OWDUtilities.getSQLDateForToday();
                        order.is_gift="1";
                        order.gift_message="Thanks for ordering - we appreciate your business!";

                    //   order.is_future_ship=1;
                       String reference = order.saveNewOrder(OrderUtilities.kHoldForPayment,false);
                       // //log.debug("Order saved as: "+reference);




                            }
                        }


        }else
        {
            //log.debug("no orders found!");
        }



    }

}
